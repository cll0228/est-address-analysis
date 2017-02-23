/**
 * Created by colin on 16/7/22.
 */

(function(w){
    w.actions = {
        register: function(methodName, actionName){
            this[methodName] = function(store, data){
                store.dispatch(actionName, data);
            }
        }
    };

    actions.register('setSiteType', 'SET_SITE_TYPE');

    //初始化
    actions.register('initSearchParams', 'INIT_SEARCH_PARAMS');

    //suggestion
    actions.register('doSearch', 'DO_SEARCH');

    //filter
    actions.register('selectFilter', 'SELECT_FILTER');

    //quyu-zhaofang
    actions.register('clickLevel1', 'CLICK_LEVEL1');
    actions.register('clickLevel2', 'CLICK_LEVEL2');
    actions.register('clickLevel3', 'CLICK_LEVEL3');

    //search-list
    actions.register('clickListItem', 'CLICK_LIST_ITEM');
    actions.register('clickBreadCrumb', 'CLICK_BREADCRUMB');
    actions.register('setMainInfo', 'SET_MAIN_INFO');
    actions.register('setBreadCrumb', 'SET_BREAD_CRUMB');
    actions.register('sortBy', 'SORT_BY');

    //filter-bar
    actions.register('removeCondition', 'REMOVE_CONDITION');
    actions.register('clearAllCondition', 'CLEAR_ALL_CONDITION');

    //map
    actions.register('clickCircle', 'CLICK_CIRCLE');
    actions.register('clickVillage', 'CLICK_VILLAGE');
    actions.register('clickStop', 'CLICK_STOP');
    actions.register('setVisited', 'SET_VISITED');
    actions.register('setActiveVillageId', 'SET_ACTIVE_VILLAGEID');


    var state = {
        dictionary: {
            districtList: null,
            subwayList: null
        },
        activeVillageId: null,      //高亮的小区id
        visitedMap: {},             //标识地图覆盖物, 是否被访问(点击)过
        mainInfo: null,             //加载的主信息
        breadCrumb: null,           //面包屑信息
        searchParams: {
            siteType: commonService.SITE_TYPE_QUYU,
            query: null,
            dataId: null,
            keyType:null,
            keyId:null,
            type: null,
            lineId: null,       //只在地铁找房中存在
            stopId: null,       //只在地铁找房中存在

            s: null     //排序
        }
    };
    $.extend(state.searchParams, commonService.getEmptyFilters());

    w.mutations = {

        'INIT_SEARCH_PARAMS': function(state, data){
            $.extend(state.searchParams, data);
        },
        'SET_SITE_TYPE': function(state, siteType){
            state.searchParams.siteType = siteType ? siteType : commonService.SITE_TYPE_QUYU;
        },

        'DO_SEARCH': function(state, params){
            state.searchParams.siteType = params.siteType;
            setDitieLineIfDitie(state.searchParams, null, null);

            if(params.keyword){
                state.searchParams.dataId = null;
                state.searchParams.type = null;
                state.searchParams.query = params.keyword;
                state.searchParams.keyType = params.keyType;
                state.searchParams.keyId = params.keyId;
            }else{
                //清空搜索条件
                $.extend(state.searchParams, commonService.getInitialState());
            }
        },

        'SELECT_FILTER': function(state, data){
            state.searchParams[data.key] = data.value;

            if(state.searchParams.z){		//选择的出租价格与自定义价格互斥
                state.searchParams.k = null;
            }

            if(state.searchParams.p){		//选择的出售价格与自定义价格互斥
                state.searchParams.b = null;
            }

            if(state.searchParams.a){		//选择的面积与自定义面积互斥
                state.searchParams.m = null;
            }
        },


        'REMOVE_CONDITION': function(state, key){
            state.searchParams[key] = null;
        },
        'CLEAR_ALL_CONDITION': function(state){
            $.extend(state.searchParams, commonService.getInitialState());
        },


        'CLICK_LEVEL1': function(state, params){
            updateSearchParams(state.searchParams, params.dataId, params.type);

            setDitieLineIfDitie(state.searchParams, null, null);
        },
        'CLICK_LEVEL2': function(state, params) {
            updateSearchParams(state.searchParams, params.dataId, params.type);

            setDitieLineIfDitie(state.searchParams, params.dataId, null);
        },
        'CLICK_LEVEL3': function(state, params) {
            updateSearchParams(state.searchParams, params.dataId, params.type);

            setDitieLineIfDitie(state.searchParams, params.parentDataId, params.dataId);
        },


        'CLICK_LIST_ITEM': function(state, clickedItem){
            updateSearchParams(state.searchParams, clickedItem.dataId, clickedItem.div);

            setLineIdAndStopIdIfDitie(state.searchParams, state.breadCrumb, clickedItem);
        },
        'CLICK_BREADCRUMB': function(state, clickedItem){
            updateSearchParams(state.searchParams, clickedItem.dataId, clickedItem.currentType);

            setLineIdAndStopIdIfDitie(state.searchParams, state.breadCrumb, clickedItem);
        },
        'SORT_BY': function(state, sortByVal){
            state.searchParams.s = sortByVal;
        },
        'SET_MAIN_INFO': function(state, mainInfo){
            state.mainInfo = mainInfo;
        },
        'SET_BREAD_CRUMB': function(state, breadCrumb){
            state.breadCrumb = breadCrumb;
        },


        'CLICK_CIRCLE': function(state, params){
            updateSearchParams(state.searchParams, params.dataId, params.type);
        },
        'CLICK_VILLAGE': function(state, params){
            updateSearchParams(state.searchParams, params.dataId, params.type);
        },
        'CLICK_STOP': function(state, params){
            updateSearchParams(state.searchParams, params.dataId, params.type);

            setLineIdAndStopIdIfDitie(state.searchParams, state.breadCrumb, {dataId: params.dataId, currentType: params.type});
        },

        'SET_VISITED': function(state, dataIds){
            (dataIds || []).forEach(function(dataId){
                state.visitedMap[dataId] = true;
            });
        },
        'SET_ACTIVE_VILLAGEID': function(state, dataId){
            state.activeVillageId = dataId;
        }
    };

    function updateSearchParams(searchParams, dataId, type){
        searchParams.query = null;
        searchParams.dataId = dataId;
        searchParams.type = type;
    }



    //创建store
    w.store = new Vuex.Store({
        state: state,
        mutations: w.mutations
    });


    //处理点击列表中的地铁线, 地铁站, 小区
    function setLineIdAndStopIdIfDitie(searchParams, breadCrumb, clickedItem){
        if(searchParams.siteType !== commonService.SITE_TYPE_DITIE){
            return;
        }

        if(clickedItem.currentType === 'line'){
            setDitieLineIfDitie(searchParams, clickedItem.dataId, null);
        }else if(clickedItem.currentType === 'stop'){
            var lineInfo = commonService.parseBreadCrumb(breadCrumb, 'line'),
                lineId = lineInfo ? lineInfo.dataId : null;
            setDitieLineIfDitie(searchParams, lineId, clickedItem.dataId);
        }else if(clickedItem.currentType === 'village'){
            var lineInfo = commonService.parseBreadCrumb(breadCrumb, 'line'),
                stopInfo = commonService.parseBreadCrumb(breadCrumb, 'stop'),
                lineId = lineInfo ? lineInfo.dataId : null,
                stopId = stopInfo ? stopInfo.dataId: null;

            setDitieLineIfDitie(searchParams, lineId, stopId);
        }else{
            setDitieLineIfDitie(searchParams, null, null);
        }
    }

    //处理点击hover到地铁的弹层中的地铁线或地铁站
    function setDitieLineIfDitie(searchParams, lineId, stopId){
        if(searchParams.siteType !== commonService.SITE_TYPE_DITIE){
            delete searchParams.lineId;
            delete searchParams.stopId;
            return;
        }

        if(lineId){
            searchParams.lineId = lineId;
        }else {
            delete searchParams.lineId;
        }

        if(stopId){
            searchParams.stopId = stopId;
        }else {
            delete searchParams.stopId;
        }
    }

}(window));