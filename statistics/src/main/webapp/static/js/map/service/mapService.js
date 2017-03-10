/**
 * Created by colin on 16/8/9.
 */

(function(win){
    var MIN_ZOOM = 11,
        MAX_ZOOM = 19,
        outlineCache = {};

    win.mapService = {
        initializeMap: function(center, zoom, callback){
            var map = new BMap.Map("mapWrap", {minZoom: MIN_ZOOM, maxZoom: MAX_ZOOM, enableMapClick:false});

            map.centerAndZoom(center, zoom);
            map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT, offset: new BMap.Size(10,10)}));
            map.enableScrollWheelZoom();		//设置可滚轮缩放

            //拖动
            map.addEventListener('dragend', function(){
                map.setCenter(map.getCenter());
                callback(map.getZoom(), true);
            });

            //缩放地图
            map.addEventListener('zoomend', function(){
                callback(map.getZoom());
            });

            //缩放窗口
            $(window).resize(function(){
                this.repaint(map);
                callback(map.getZoom(), true);
            }.bind(this));

            return map;
        },
        repaint: function(map){
            if(!map) return;
            map.checkResize();
            map.setCenter(map.getCenter());
        },
        addOutLine: function(map, level, dataId, isMousedout){	//添加轮廓
            //from cache
            var cacheKey = level + '' + dataId,
                cachedData = outlineCache[cacheKey];
            if(cachedData){
                return addOutLineInfo(cachedData);
            }

            // from server
            commonService.ajaxGetBase('/api/v4/online/house/map/border', null, { type : level, dataId : dataId }, function(res){
                outlineCache[cacheKey] = res.dataList;
                if(isMousedout) { return; }     //数据返回时, 已经mouseout了, 则不添加outline

                addOutLineInfo(res.dataList);
            }.bind(this));


            //添加 轮廓图数据
            function addOutLineInfo(dataList){
                var points = (dataList || []).map(function(item){
                    return new BMap.Point(item.longitude, item.latitude);
                });
                var polygon = new BMap.Polygon(points, {strokeColor: "#dd2424", fillColor: "",strokeWeight: 3, strokeOpacity: 0.75});  //创建多边形
                polygon.isOutLine = true;
                map.addOverlay(polygon); //增加多边形
            }
        },
        clearOutLine: function(map){	//删除轮廓
            map.getOverlays().forEach(function(overlay){
                if(overlay.isOutLine){
                    map.removeOverlay(overlay);
                }
            });
        },
        shrink: function(map){      //放大地图
            if(map.getZoom() > MIN_ZOOM){
                map.setZoom(map.getZoom() - 1);
            }
        },
        magnify: function(map){     //缩小地图
            if(map.getZoom() < MAX_ZOOM){
                map.setZoom(map.getZoom() + 1);
            }
        },
        drawStopScope: function(map, mainInfo, scopeVal){
            //画地铁站范围
            var center = new BMap.Point(mainInfo.longitude, mainInfo.latitude),
                circle = new BMap.Circle(center, scopeVal, {
                fillColor: "#00AE66",
                fillOpacity: 0.1,
                strokeWeight: 2 ,
                strokeStyle: 'dashed',
                strokeColor: '#39AC6A',
                strokeOpacity: 1
            });
            circle.isStopScope = true;
            map.addOverlay(circle);

            //画地铁站中心
            var html = '<div class="stop-center" data-id="' + mainInfo.dataId + '"><div class="stop-center__icon-wrapper"><svg class="stop-center__icon"><use xlink:href="/static/css/map/map-icon.svg#icon-ditie"></use></svg></div>' +
                    mainInfo.showName  + '</div>',
                richMarker = new BMapLib.RichMarker(html,  center, {"anchor" : new BMap.Size(-(mainInfo.showName.length * 14 + 20) / 2, -15)});
            richMarker.isStopScope = true;
            map.addOverlay(richMarker);
            $(richMarker._container).css({"background":  "transparent", "zIndex": 10});
        },
        drawLine: function(map, lineName, isViewport, callback){
            var ditieLine = new BMap.BusLineSearch(map, {
                onGetBusListComplete: function(result){
                    if(result) {
                        ditieLine.getBusLine(result.getBusListItem(0)); //获取第一个公交列表显示到map上
                    }
                },
                onGetBusLineComplete: function(line){
                    //画地铁线
                    win.mapService.drawMetroLine(map, line.getPath());

                    //viewport: 只有当从搜索或点击某条地铁线进入, isViewport才为true, 表示要把整条地铁线放入viewport中
                    if(isViewport){
                        map.setViewport(map.getViewport(line.getPath(), {zoomFactor: 0}));
                    }

                    if(callback){
                        callback();
                    }
                }
            });
            ditieLine.setLocation(headerParameters.cityName);
            ditieLine.getBusList(lineName);
        },
        drawStop: function(map, item, callback){
            var html = '<div class="stop-wrapper__stop js_stop"></div>' +
                        '<div class="stop-wrapper__stop-tips js_stopTips' + (item.saleTotal > 0 ? '' : ' stop-wrapper__stop-tips--zero') + '">' +
                        '<span class="stop-wrapper__stop-name">' + item.showName + '</span>' +
                        '<span class="total-count js_totalCount">' + item.saleTotal + '</span>套' +
                    '</div>',
                marker = new BMapLib.RichMarker(html,  new BMap.Point(item.longitude, item.latitude), {"anchor": new BMap.Size(-8, -8)});

            map.addOverlay(marker);
            $(marker._container).css('background', 'transparent').addClass('stop-wrapper js_stopWrapper');
            $(marker._container).click(function(){
                if(callback) callback(item);
            });
        },
        drawMetroLine: function(map, path){
            var polyline = new BMap.Polyline(path, {strokeColor: "#39AC6A", fillColor: "#39AC6A", strokeWeight: 3, strokeOpacity: 1});  //创建多边形
            map.addOverlay(polyline);
        },
        setViewport: function(map, path){
            var viewport = map.getViewport(path, {zoomFactor: 0});
            map.setViewport(viewport);
        },
        buildMapSearchParams: function(map, level){
            var mapRegion = map.getBounds();	//根据地图坐标搜索数据

            return {
                type : level,
                minLatitude : mapRegion.Ll.lat,  //左上角纬度
                maxLatitude : mapRegion.ul.lat,  //右下角纬度
                minLongitude : mapRegion.Ll.lng, //左上角经度
                maxLongitude : mapRegion.ul.lng  //右下角经度
            };
        },
        addDistrictOrPlateOverlay: function(mapComponent, node, currentType, clickCallback){
            var html = '<div data-id="' + node.dataId + '" class="district-overlay"><p>' + node.showName + (mapComponent.isZufang() ? '' : '<br/>' + commonService.formatPrice(node.saleAvgPrice)) + '</p><p class="map-overlay__total">'+node.saleTotal+'套</p></div>';
            if(currentType === 'plate'){
                html = '<div data-id="'+ node.dataId +'" class="plate-overlay">' + node.showName + (mapComponent.isZufang() ? '' : '<br/>' + commonService.formatPrice(node.saleAvgPrice)) + '<br/>'+node.saleTotal+'套</div>';

            }
            var center = new BMap.Point(node.longitude, node.latitude),
                anchor = new BMap.Size(-30,-25),
                overlayContainer = this._addOverlayBase(mapComponent, node.dataId, html, center, anchor);

            //租房
            if(mapComponent.isZufang()){
                overlayContainer.addClass('zufang');
            }

            //节点是否访问过
            if(mapComponent.visitedMap[node.dataId]){
                overlayContainer.addClass('visited');
            }

            overlayContainer.click(function(){
                if(clickCallback){ clickCallback(); }
            });

            return overlayContainer;
        },
        addVillageOverlay: function(mapComponent, node, clickCallback){
            var center = new BMap.Point(node.longitude, node.latitude),
                html = '<div data-id="' + node.dataId + '" class="estate-overlay ' + (mapComponent.isZufang() ? 'zufang' : '') + ' js_villageLabel">' +
                            '<div class="estate-overlay__count js_estateOverlayCount">' + (node.saleTotal || 0) + '套</div>' +
                            '<div class="estate-overlay__show-name">' +
                                '<a class="js_LinkXiaoqu" href="/xiaoqu/' + node.dataId + '.html" target="_blank">' + node.showName + '</a>' +
                                ( mapComponent.isZufang() || node.saleAvgPrice == 0.0 ? '' : ('<span class="ml_10">' + commonService.formatPrice(node.saleAvgPrice) +' </span>')) +
                            '</div>'
                        '</div>',
                anchor = new BMap.Size(-25, -40);

            //添加覆盖物
            var overlayContainer = this._addOverlayBase(mapComponent, node.dataId, html, center, anchor);
            if(node.dataId === mapComponent.activeVillageId){
                setActive(overlayContainer);
            }

            //点击小区左侧均价标签 － 重新查询列表，地图不变，被点击小区高亮
            overlayContainer.find(".js_estateOverlayCount").click(function() {
                //设置为访问过
                overlayContainer.addClass('visited');

                //设置为active状态
                setActive(overlayContainer);

                if(clickCallback) clickCallback();
            });

            //点击链接跳转
            overlayContainer.find(".js_LinkXiaoqu").click(function(){
                $("#link").attr("href", $(this).attr("href"));
                document.getElementById("link").click();
            });

            function setActive(overlayContainer){
                mapService.getMapRoot().find('.js_villageLabel').removeClass('active').closest('.map-overlay').removeClass('map-overlay--active');
                overlayContainer.addClass('active').closest('.map-overlay').addClass('map-overlay--active');
            }
        },
        getMapRoot: function(){
            return $('#mapWrap');
        },
        getOverlayByDataId: function(dataId){
            return $('#mapWrap').find('[data-id=' + dataId + ']');
        },
        _addOverlayBase: function(mapComponent, dataId, html, center, anchor){
            var richMarker = new BMapLib.RichMarker(html, center, {"anchor" : anchor});
            mapComponent.map.addOverlay(richMarker);

            var overlayTop = $(richMarker._container);
            overlayTop.css("background", "transparent").addClass('map-overlay');

            return  overlayTop.find('[data-id]');
        }
    };
}(window));