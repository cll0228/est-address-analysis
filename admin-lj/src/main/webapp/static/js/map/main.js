/**
 * Created by colin on 16/8/11.
 */

(function(){
    Vue.filter('formatPrice', commonService.formatPrice);

    var rootVue = new Vue({
        store: store,				//根组件中注入store
        vuex: {
            getters: { searchParams: commonService.getSearchParams},
            actions: {
                initSearchParams: actions.initSearchParams
            }
        },
        el: '#lianjia',
        created: function(){
            setTimeout(function(){
                var urlParams = commonService.parseUrlParams();
                this.initSearchParams($.isEmptyObject(urlParams) ? commonService.getInitialState() : urlParams);
            }.bind(this), 50);


            //加载字典
            if (window.houseType === commonService.ERSHOUFANG) {
                commonService.ajaxGetBase('/api/v4/online/dict/solrCode/info', null, {range: 'ditu'}, function (res) {
                    var data = res.data.info,
                        tmp = {};

                    tmp.p = buildSingleDictionary(data['prices']);      //售价
                    tmp.a = buildSingleDictionary(data['acreages']);    //面积
                    tmp.l = buildSingleDictionary(data['roomCount']);   //

                    tmp.g = buildSingleDictionary(data['subway_distance']);     //距离
                    tmp.f = buildSingleDictionary(data['orientation']);     //朝向
                    tmp.y = buildSingleDictionary(data['house_year']);      //房龄
                    tmp.c = buildSingleDictionary(data['floor_level']);     //楼层
                    tmp.o = buildSingleDictionary(data['houseTypeCode']);        //房屋类型
                    tmp.x = buildSingleDictionary(data['decoration']);        //装修

                    tmp.u = buildSpecial('u', data['tags']);        //房本年限
                    tmp.v = buildSpecial('v', data['tags']);        //标签

                    this.conditionMap = tmp;
                }.bind(this));

                function buildSpecial(key, list) {
                    return buildSingleDictionary(list.filter(function (item1) {
                        return item1.key === key;
                    }));
                }

            } else if (window.houseType === commonService.ZUFANG) {
                commonService.ajaxGetBase('/api/v4/online/dict/solrCode/info', null, {range: 'zufangditu'}, function (res) {
                    var data = res.data.info,
                        tmp = {};

                    tmp.z = buildSingleDictionary(data['prices']);
                    tmp.a = buildSingleDictionary(data['acreages']);
                    tmp.l = buildSingleDictionary(data['roomCount']);
                    tmp.f = buildSingleDictionary(data['orientation']);
                    tmp.c = buildSingleDictionary(data['floor_level']);
                    tmp.t = buildSingleDictionary(data['tags']);
                    tmp.x = buildSingleDictionary(data['decoration']);        //装修
                    tmp.n = buildSingleDictionary(data['brand']);        //品牌
                    tmp.i = buildSingleDictionary(data['rentType']);        //类型

                    this.conditionMap = tmp;

                }.bind(this));
            }

            function buildSingleDictionary(list){
                var result = (list || []).map(function(item) { return {code: item.urldata, codeDesc: item.text}; });
                result.unshift({code: null, codeDesc: '不限'});
                return result;
            }
        },
        data: {
            conditionMap: null,
            isShowLeft: true
        }
    });

    var isFirstLoad = true;
    rootVue.$store.subscribe(function(mutation, state){
        if(mutation.type === 'INIT_SEARCH_PARAMS' ||
            mutation.type === 'DO_SEARCH' ||			//关键字查询
            mutation.type === 'SELECT_FILTER' ||		//变更筛选条件
            mutation.type === 'CLICK_LEVEL1' ||			//点击区域找房
            mutation.type === 'CLICK_LEVEL2' ||			//点击区域
            mutation.type === 'CLICK_LEVEL3' ||			//点击板块
            mutation.type === 'CLICK_LIST_ITEM' ||		//点击列表项
            mutation.type === 'REMOVE_CONDITION' ||		//删除过滤条件
            mutation.type === 'CLEAR_ALL_CONDITION'	|| 	//清除过滤条件
            mutation.type === 'CLICK_BREADCRUMB' ||		//点击面包屑
            mutation.type === 'CLICK_CIRCLE'	||		//点击地图圆圈
            mutation.type === 'CLICK_STOP'				//点击地图地铁站
        ){
            //加载搜索列表
            rootVue.$refs.searchlistcomponent.loadMain().then(function(res){
                //加载地图
                var mainInfo = res.data,
                    initLng = headerParameters.cityCoordinate.longitude,
                    initLat = headerParameters.cityCoordinate.latitude,
                    intv = null;

                if(isFirstLoad){        //首次加载, 用定时器轮询判断百度地图是否加载. 主要为性能考虑, 不希望百度地图阻塞页面其他组件的渲染
                    intv = setInterval(function(){
                        if(window.BMap){        //百度地图api已加载
                            clearInterval(intv);
                            isFirstLoad = false;
                            loadMap();
                        }
                    }, 5);
                }else{
                    loadMap();
                }

                function loadMap(){
                    if(state.searchParams.siteType === commonService.SITE_TYPE_QUYU){		//区域
                        if(!mainInfo){
                            rootVue.$broadcast('loadQuyuMapData', initLng, initLat, 'district');
                        }else{
                            rootVue.$broadcast('loadQuyuMapData', mainInfo.longitude, mainInfo.latitude, mainInfo.type);
                        }
                    }else if(state.searchParams.siteType === commonService.SITE_TYPE_DITIE){	//地铁
                        rootVue.$broadcast('loadDitieMapData', mainInfo);
                    }
                }
            });
        }

        //点击地图中的小区, 无需加载地图数据
        if(mutation.type === 'CLICK_VILLAGE'){
            rootVue.$refs.searchlistcomponent.loadMain();
        }

        setTimeout(commonService.computeListHeight, 10);

        //model变化, 设置url参数
        commonService.replaceState2History(state.searchParams);
    });
    $(window).resize(commonService.computeListHeight);
}());