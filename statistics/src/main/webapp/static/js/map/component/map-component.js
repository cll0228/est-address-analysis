/**
 * Created by colin on 16/7/25.
 */

(function() {
    Vue.component('map-component', {
        vuex: {
            getters: {
                activeVillageId: function (state) {
                    return state.activeVillageId;
                },
                visitedMap: function (state) {
                    return state.visitedMap;
                },
                searchParams: commonService.getSearchParams,
                mainInfo: function (state) {
                    return state.mainInfo;
                }
            },
            actions: {
                clickCircle: actions.clickCircle,
                clickVillage: actions.clickVillage,
                setVisited: actions.setVisited,
                setActiveVillageId: actions.setActiveVillageId
            }
        },
        props: ['houseType', 'isFullScreen'],
        template: '<div>\
                    <div class="zoom-tool">\
                        <a id="bzoom" class="zoom-tool__item icon-plus" @click="magnify(map)" :class="{\'zoom-tool__item--disabled\': map.getZoom() >= 19}"></a>\
                        <a id="szoom" class="zoom-tool__item icon-minus" @click="shrink(map)" :class="{\'zoom-tool__item--disabled\': map.getZoom() <= 11}"></a>\
                    </div>\
                </div>',
        data: function () {
            return {
                map: null,
                isInitMap: false,
                outlineCache: {},       //缓存轮廓信息, 访问过的轮廓都会缓存下来
                outlineStatus: {}       //标识当前覆盖物的状态: mouseover或mouseout
            }
        },
        watch: {
          'isFullScreen': function(){
              console.log('quyu');
              mapService.repaint(this.map);
          }
        },
        methods: {
            isZufang: function () {
                return this.houseType === 'zufang';
            },
            shrink: mapService.shrink,
            magnify: mapService.magnify,
            centerAndZoomMap: function (lng, lat, currentType) {
                //设置地图中心点
                if (this.isInitMap) {
                    this.map.centerAndZoom(new BMap.Point(lng, lat), commonService.level2Scale(currentType));
                    return;
                }

                //初始化地图
                this.map = mapService.initializeMap(new BMap.Point(lng, lat), commonService.level2Scale(currentType), function (zoom, isDrag) {
                    this.setMapData(commonService.scale2Level(zoom), isDrag);
                }.bind(this));

                this.isInitMap = true;
            },
            searchMapData: function (lng, lat, currentType) {	    //入口来自用户点击或搜索
                this.centerAndZoomMap(lng, lat, currentType);
                this.setMapData(currentType);
            },
            setMapData: function (currentType, isDrag) {
                if (!isDrag) {
                    this.map.clearOverlays();
                }

                var me = this,
                    mainInfo = me.mainInfo;

                if (!mainInfo) return;


                //构建地图查询参数
                var params = mapService.buildMapSearchParams(me.map, currentType);

                //处理关键字搜索
                if (this.searchParams.query) {
                    if (mainInfo.currentType === 'district') {
                        params.districtSpelling = mainInfo.dataId;
                    }
                    if (mainInfo.currentType === 'plate') {
                        params.plateSpelling = mainInfo.dataId;
                    }
                }
                if (mainInfo.currentType === 'village') {
                    this.setActiveVillageId(mainInfo.dataId);
                }
                params.siteType = me.searchParams.siteType;


                //获取数据
                commonService.ajaxGetBase(commonService.getListMapResultUrl(me.houseType), commonService.getFilters(me.searchParams), params, function (res) {

                    //如果小区处在高亮状态, 则显示0套房源的小区
                    if (currentType === "village"){
                        var hasActivedVillage = (res.dataList || []).some(function(item){
                            return item.dataId == mainInfo.dataId;
                        });
                        if(!hasActivedVillage) {
                            res.dataList.push(mainInfo);
                        }
                    }

                    (res.dataList || []).forEach(function (item) {

                        var dataId = item.dataId,
                            currentType = item.currentType;

                        if (isExistOverlay(dataId) === false && isSameLevel(me.map.getZoom(), currentType)) {
                            if (currentType === "village") {      //小区
                                mapService.addVillageOverlay(me, item, function () {
                                    me.setActiveVillageId(dataId);
                                    me.setVisited([dataId]);
                                    me.clickVillage({dataId: dataId, type: currentType});
                                    me.$root.isShowLeft = true;
                                });
                            } else {              //区域或板块
                                var overlayContainer = mapService.addDistrictOrPlateOverlay(me, item, currentType, function () {
                                    me.setVisited([dataId]);
                                    me.clickCircle({dataId: dataId, type: currentType});
                                });

                                addOrRemoveOutline(overlayContainer, dataId, currentType);
                            }
                        }
                    });
                });

                function isExistOverlay(dataId) {
                    return mapService.getOverlayByDataId(dataId).length > 0;
                }

                function isSameLevel(mapZoom, currentType) {
                    return commonService.scale2Level(mapZoom) === currentType;
                }

                //添加或删除轮廓范围
                function addOrRemoveOutline(overlayContainer, dataId, currentType) {
                    overlayContainer.hover(
                        function () {
                            me.outlineStatus[dataId] = true;
                            mapService.addOutLine(me.map, currentType, dataId, !me.outlineStatus[dataId]);
                        },
                        function () {
                            me.outlineStatus[dataId] = false;
                            mapService.clearOutLine(me.map);
                        }
                    );
                }
            }
        },
        events: {
            'mouseoverListItem': function (dataId, currentType) {         //mouseover到列表项
                this.outlineStatus[dataId] = true;
                if (currentType !== 'village') {
                    mapService.addOutLine(this.map, currentType, dataId, !this.outlineStatus[dataId]);
                }
                mapService.getOverlayByDataId(dataId).addClass('map-wrapper__district-overlay--active');
            },
            'mouseoutListItem': function (dataId) {               //mouseout出列表项
                this.outlineStatus[dataId] = false;
                mapService.clearOutLine(this.map);
                mapService.getOverlayByDataId(dataId).removeClass('map-wrapper__district-overlay--active');
            },
            'loadQuyuMapData': function(lg, lat, type){
                this.searchMapData(lg, lat, type);
            }
        }
    });
}())