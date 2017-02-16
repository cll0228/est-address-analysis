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
                commonService.ajaxGetBase3('getSearchCond.do', function (res) {
                    var data = res,
                        tmp = {};

                    tmp.a = buildSingleDictionary(data['userScale']);      //用户规模
                    tmp.l = buildSingleDictionary(data['residenceScale']);    //小区规模
                    tmp.p = buildSingleDictionary(data['residenceAvg']);   //小区均价

                    tmp.t = mybuildSingleDictionary(data['residenceKind'],1);//小区分类
                    tmp.b = mybuildSingleDictionary(data['residenceUserProportion'],2);//小区用户占比
                    tmp.g = mybuildSingleDictionary(data['estateTotalValue'],3);   //不动产估值

                    tmp.o = mybuildSingleDictionary(data['billActDegree'],4);   //账单活跃度
                    tmp.f = mybuildSingleDictionary(data['ifSubIncrement'],5);   //是否订阅增值节目



                    this.conditionMap = tmp;
                }.bind(this));

                function buildSpecial(key, list) {
                    return buildSingleDictionary(list.filter(function (item1) {
                        return item1.key === key;
                    }));
                }

            }

            function buildSingleDictionary(list){
                var result = (list || []).map(function(item) { return {code: item.urldata, codeDesc: item.text}; });
                result.unshift({code: null, codeDesc: '不限'});
                return result;
            }

            function mybuildSingleDictionary(list,kind){
                var result = (list || []).map(function(item) { return {code: item.urldata, codeDesc: item.text}; });
                if(kind == 1){
                    result.unshift({code: null, codeDesc: '小区分类'});
                }else if(kind == 2){
                    result.unshift({code: null, codeDesc: '小区用户占比'});
                }else if(kind == 3){
                    result.unshift({code: null, codeDesc: '不动产估值'});
                }else if(kind == 4){
                    result.unshift({code: null, codeDesc: '账单活跃度'});
                }else if(kind == 5){
                    result.unshift({code: null, codeDesc: '是否订阅增值节目'});
                }else {
                    result.unshift({code: null, codeDesc: '未知'});
                }

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