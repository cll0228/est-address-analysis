/**
 * Created by colin on 16/8/9.
 */
Vue.component('ditie-map-component', {
    vuex: {
        getters: {
            activeVillageId: function(state) { return state.activeVillageId; },
            visitedMap: function(state) { return state.visitedMap; },
            searchParams: commonService.getSearchParams,
            mainInfo: function(state) { return state.mainInfo; },
            breadCrumb: function(state) { return state.breadCrumb; }
        },
        actions: {
            setSiteType: actions.setSiteType,
            clickStop: actions.clickStop,
            clickVillage: actions.clickVillage,
            setVisited: actions.setVisited,
            setActiveVillageId: actions.setActiveVillageId
        }
    },
    props: ['houseType', 'isFullScreen'],
    template: '<div>\
                    <div class="zoom-tool" style="position: fixed">\
                        <a id="bzoom" class="zoom-tool__item icon-plus" @click="magnify(map)" :class="{disabled: map.getZoom() >= 19}"></a>\
                        <a id="szoom" class="zoom-tool__item icon-minus" @click="shrink(map)" :class="{disabled: map.getZoom() <= 11}"></a>\
                    </div>\
                </div>',
    data: function(){
        return {
            STOP_SCOPE_ZOOM: 16,
            MIN_LINE_ZOOM: 15,
            MIN_VILLAGE_ZOOM: 16,
            map: null,
            isInitMap: false
        }
    },
    watch: {
        'isFullScreen': function(){
            console.log('ditie');
            mapService.repaint(this.map);
        }
    },
    methods:{
        isZufang: function(){
            return this.houseType === 'zufang';
        },
        shrink: mapService.shrink,
        magnify: mapService.magnify,
        centerAndZoomMap: function(lng, lat, currentType){
            var zoom = this.level2Scale(currentType);
            if(this.isInitMap){
                this.map.centerAndZoom(new BMap.Point(lng, lat), zoom);
                return;
            }

            this.map = mapService.initializeMap(new BMap.Point(lng, lat), zoom, function(zoom, isDrag){
                this.setMapDataByMapAction(isDrag);
            }.bind(this));

            this.isInitMap = true;
        },
        searchMapData: function(mainInfo){
            if(!mainInfo){
                this.centerAndZoomMap(headerParameters.cityCoordinate.longitude, headerParameters.cityCoordinate.latitude, 'line');
                return;
            }

            if(mainInfo.currentType === 'line') {
                this.centerAndZoomMap(headerParameters.cityCoordinate.longitude, headerParameters.cityCoordinate.latitude, 'line');     //放到至默认的缩放层级12
            }else{
                this.centerAndZoomMap(mainInfo.longitude, mainInfo.latitude, mainInfo.type);
            }
            this.setMapDataByNonMapAction(mainInfo);
        },
        setMapDataByMapAction: function(isDrag){                  //入口来自缩放或拖动
            var mainInfo = commonService.parseBreadCrumb(this.breadCrumb, 'line');
            this.setMapData(mainInfo, false, isDrag);           //画地铁线时, 无需使用viewport
        },
        setMapDataByNonMapAction: function(mainInfo){       //入口来自用户点击, 当显示地铁线时, viewport为true
            this.setMapData(mainInfo, true);
        },
        setMapData: function(mainInfo, isViewport, isDrag){
            var me = this;

            if(!isDrag){
                this.map.clearOverlays();
            }

            //加载地铁线以及地铁站信息
            drawLineAndStops(mainInfo, isViewport);

            //加载地铁站范围
            setTimeout(function(){
                this.drawStopScope();
            }.bind(this), 300);


            //加载地图数据
            loadVillages();

            //画地铁线
            function drawLineAndStops(lineInfo, isViewport) {
                if (!lineInfo || lineInfo.currentType !== 'line' || me.map.getZoom() > me.MIN_LINE_ZOOM) return;


                mapService.drawLine(me.map, lineInfo.showName, isViewport, function(){
                    drawStop();
                });

                //特殊处理17号线
                if(lineInfo.showName == '17号线'){
                    drawStop(function(stopListPoints){
                        mapService.drawMetroLine(me.map, stopListPoints);
                        if(isViewport) me.map.setViewport(stopListPoints, {zoomFactor: 0});
                    });
                }

                function drawStop(loadedStopCallback){
                    //加载地铁站以及地图数据
                    var params = {
                        type: lineInfo.type,
                        dataId: lineInfo.dataId,
                        showType: 'list',
                        limit_offset: 0,
                        limit_count: 2000
                    };
                    params.siteType = me.searchParams.siteType;

                    commonService.ajaxGetBase(commonService.getListMapResultUrl(me.houseType), commonService.getFilters(me.searchParams), params, function (res) {
                        var list = res.dataList || [],
                            pathList = [];
                        for (var i = 0, len = list.length; i < len; i++) {
                            mapService.drawStop(me.map, list[i], function (item) {
                                me.clickStop({dataId: item.dataId, type: item.currentType});
                            });
                            pathList.push(new BMap.Point(list[i].longitude, list[i].latitude));
                        }

                        if(loadedStopCallback) loadedStopCallback(pathList);
                    });
                }
            };

            //加载小区数据
            function loadVillages(){
                if(me.map.getZoom() < me.STOP_SCOPE_ZOOM) return;

                //设置选中的小区id
                me.setActiveVillageId(me.mainInfo.dataId);

                var params = mapService.buildMapSearchParams(me.map, 'village');
                params.siteType = me.searchParams.siteType;
                commonService.ajaxGetBase(commonService.getListMapResultUrl(me.houseType), commonService.getFilters(me.searchParams), params, function(res){

                    //当前小区高亮时, 即使房源数为0, 也显示
                    if (mainInfo.currentType === "village") {
                        var hasActivedVillage = (res.dataList || []).some(function (item) {
                            return item.dataId == mainInfo.dataId;
                        });
                        if (!hasActivedVillage) {
                            res.dataList.push(mainInfo);
                        }
                    }

                    (res.dataList || []).forEach(function(item){
                        if(mapService.getOverlayByDataId(item.dataId).length === 0){
                            mapService.addVillageOverlay(me, item, function(){
                                me.clickVillage({dataId: item.dataId, type: item.currentType});
                                me.setActiveVillageId(item.dataId);
                                me.setVisited([item.dataId]);
                                me.$root.isShowLeft = true;
                            });
                        }
                    });
                });
            }
        },
        drawStopScope: function (){
            this.map.getOverlays().forEach(function(overlay){
                if(overlay.isStopScope){
                    this.map.removeOverlay(overlay);
                }
            }.bind(this));


            var stop = commonService.parseBreadCrumb(this.breadCrumb, 'stop');
            if(this.map.getZoom() !== this.STOP_SCOPE_ZOOM || !stop || mapService.getOverlayByDataId(stop.dataId).length > 0) return;

            if(stop){    //显示地铁站范围的层级为16
                var d = 1200,
                    g = this.searchParams.g;
                if(g === 'g1'){
                    d = 500;
                }else if(g === 'g2'){
                    d = 800;
                }

                mapService.drawStopScope(this.map, stop, d);
            }
        },
        level2Scale: function(level){	//层级 => 缩放比例(地图比例尺与气泡层级对应关系（默认值）)
            if(level === 'line'){
                return 12;
            }
            if(level === 'stop'){
                return 14;
            }
            if(level === 'village'){
                return 16;
            }
        }
    },
    events: {
        'loadDitieMapData': function(mainInfo){
            this.searchMapData(mainInfo);
        }
    }
});