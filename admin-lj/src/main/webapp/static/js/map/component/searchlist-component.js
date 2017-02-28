/**
 * Created by colin on 16/7/25.
 */

Vue.component('searchlist-component', {
    vuex: {
        getters: {
            searchParams: commonService.getSearchParams,
            mainInfo: function(state) { return state.mainInfo; },
            breadCrumb: function(state) { return state.breadCrumb; }
        },
        actions: {
            clickListItem: actions.clickListItem,
            clearAllCondition: actions.clearAllCondition,
            clickBreadCrumb: actions.clickBreadCrumb,
            sortByField: actions.sortBy,
            setMainInfo: actions.setMainInfo,
            setBreadCrumb: actions.setBreadCrumb
        }
    },
    props: ['houseType'],
    template: '#listContainer-template',
    created: function(){
        this.TAG_MAPPING = {
            //租房
            'is_rent_subway_house': {className: 'search-list__info-item-tag--label_subway', text: '地铁'},
            'is_rent_key': {className: 'search-list__info-item-tag--label_rent_key', text: '随时看房'},
            'is_rent_yangtai': {className: 'search-list__info-item-tag--label_rent_yangtai', text: '独立阳台'},
            'is_rent_weishengjian': {className: 'search-list__info-item-tag--label_rent_weishengjian', text: '独卫'},

            //二手房
            'is_subway_house': {className:  'search-list__info-item-tag--label_subway', text: '地铁'},
            'is_school_house': {className:  'search-list__info-item-tag--label_school', text: '学区'},
            'is_five_sole': {className:  'search-list__info-item-tag--label_taxfree_sole', text: '满五唯一'},
            'is_five_year': {className:  'search-list__info-item-tag--label_taxfree5', text: '满五'},
            'is_two_year': {className:  'search-list__info-item-tag--label_taxfree2', text: '满二'},
            'is_quick_acting': {className:  'search-list__info-item-tag--label_only', text: '独家'},
            'is_key': {className:  'search-list__info-item-tag--label_haskey', text: '钥匙'}
        };
    },
    data: function(){
        return {
            isLoadingList: false,
            TAG_MAPPING: null,		//房源的tag
            searchResults: null		//头部信息下的列表
        }
    },
    methods: {
        displayBreadCrumb: function(){
            var b = this.breadCrumb || [],
                len = b.length;
            return b.filter(function(item, i){
                return item && item.showName && i < (len - 1);
            });
        },
        startWith: function(showDataId){
            if((showDataId.indexOf("3101") == 0 || showDataId == "310230") && showDataId.length == 6){
                this.searchParams.type = 1
            }else if((showDataId.indexOf("3101") == 0 || showDataId.indexOf("310230")) && showDataId.length == 9){
                this.searchParams.type = 2
            }else if((showDataId.indexOf("3101") == 0 || showDataId.indexOf("310230")) && showDataId.length == 12){
                this.searchParams.type = 3
            } else {
                this.searchParams.type = 4
            }
        },
        loadMain: function(){
            var searchParams = this.searchParams;

            var params = {};

            //包含查询关键字的情况
            if(searchParams.query){
                if(commonService.getIsDidSearch()){		//选择过查询关键字
                    params = { query: searchParams.query };
                }else{		//没有选择过查询关键字: 从二手房列表中跳过来, 自动提示下拉.  并把搜索范围定位到市的层级
                    params = commonService.getDefaultRoot();
                }
            }else{
                $('.c-filterbox__list').css({display:'block'});
                if(searchParams.type == null){
                    this.startWith(searchParams.dataId);
                }
                params.type = searchParams.type || 'city';
                params.dataId =  searchParams.dataId || headerParameters.cityCode;
                if(searchParams.lineId){
                    params.lineId = searchParams.lineId;
                }
                if(searchParams.stopId){
                    params.stopId = searchParams.stopId;
                }
            }
            if(params.type=="4"||(params.type=="2"&&searchParams.siteType=="xiaoqu")) {
//            if(params.type=="4") {
            	return;
            }
            if(params.type=="0"||params.type=="city"||(params.type=="1"&&params.dataId=="sh")) {
            	this.searchParams.siteType = "quyu";
            	params.siteType = "quyu";
        	} else if(params.type=="1"&&(params.dataId+"").length==6) {
        		this.searchParams.siteType = "jiedao";
        		params.siteType = "jiedao";
        	} else if(params.type=="2") {
        		this.searchParams.siteType = "juwei";
        		params.siteType = "juwei";
        	} else if(params.type=="3") {
        		this.searchParams.siteType = "xiaoqu";
        		params.siteType = "xiaoqu";
        	}
            params.siteType = this.searchParams.siteType;
//            return commonService.ajaxGetBase2(commonService.getBaseInfoUrl(this.houseType), null, params, function(res){
            return commonService.ajaxGetBase4('/listSearch.do', null, params, function(res){
            	this.searchResults = this._normalizeSearchResult("All", res);
            	this.setMainInfo("all");
            	this.searchResults = null;
            	if(res.status=="1") {
            		//加载列表
                    this.loadChildren();
            	} else {
            		//加载列表
                    this.loadChildren2(params.dataId,params.type);
            	}
            }.bind(this));
        },
        loadChildren: function(){
            var dataId = this.mainInfo.dataId,
                currentType = this.mainInfo.currentType,
                siteType = this.searchParams.siteType,
                params = {
                    siteType: siteType,
                    type: this.mainInfo.type,
                    dataId: dataId,
                    showType: 'list',
                    limit_offset: 0,
                    limit_count: 2000
                },
                filters = commonService.getFilters(this.searchParams);

            //加载房源列表
            if(currentType === 'village'){
                this._loadHouseList(dataId, currentType);
                return;
            }

            var me = this;
            this.isLoadingList = true;
//            commonService.ajaxGetBase(commonService.getListMapResultUrl(this.houseType), filters, params, function(res){
            commonService.ajaxGetBase2('/list.do', null, null, function(res){
                //alert("aaa")
            	if(this.searchParams.siteType==null) {
            		this.searchParams.siteType="quyu";
            	}
                this.searchResults = this._normalizeSearchResult(currentType, res);
                this.isLoadingList = false;
            }.bind(this)).fail(function(){ me.isLoadingList = false; })
        },
        loadChildren2: function(dataId,type){
            var dataId = dataId,
                currentType = this.mainInfo.currentType,
                siteType = this.searchParams.siteType,
                keyId = this.searchParams.keyId;
                keyType = this.searchParams.keyType;
                keyword = this.searchParams.query,
                params = {
                    siteType: siteType,
                    type: type,
                    dataId: dataId,
                    keyword:keyword,
                    keyId:keyId,
                    keyType:keyType,
                    showType: 'list',
                    limit_offset: 0,
                    limit_count: 2000
                },
                filters = commonService.getFilters(this.searchParams);

            //加载房源列表
            if(currentType === 'village'){
                this._loadHouseList(dataId, currentType);
                return;
            }

            var me = this;
            this.isLoadingList = true;
            
//            commonService.ajaxGetBase(commonService.getListMapResultUrl(this.houseType), filters, params, function(res){
//            commonService.ajaxGetBase4('/listSearch.do', null, params, function(res){
            commonService.ajaxGetBase4('/searchKeyword.do', filters, params, function(res){
                if(res.siteType == "xiaoqu" || res.siteType == "xiaoqu"){
                    this.searchParams.siteType = "xiaoqu";
                }
                if(res.siteType == "quyu"){
                    this.searchParams.siteType = "quyu";
                }
                if(res.siteType == "jiedao"){
                    this.searchParams.siteType = "jiedao";
                }
                if(res.siteType == "juwei"){
                    this.searchParams.siteType = "juwei";
                }
                if(res.siteType == "jieguo"){
                    this.searchParams.siteType = "jieguo";
                }
                //用户点搜索
                if(params.keyType == null){
                    this.startWith(res.dataList[0].dataId);
                    params.keyType = this.searchParams.type;
                }
                if(params.keyType == 1){
                    showMapDistrict(res.dataList);
                }
                if(params.keyType == 2 || params.type == 1){
                    showMapTown(res.dataList);
                }
                if(params.keyType == 3 || params.type == 2){
                    showMapNeibarHood(res.dataList);
                }
                if(params.keyType == 4 || params.keyType == 5 || params.type == 3){
                    showMapResidence(res.dataList)
                }
                this.searchResults = this._normalizeSearchResult(currentType, res);
                this.isLoadingList = false;
            }.bind(this)).fail(function(){ me.isLoadingList = false; })
        },
        mouseoverItem: function(item){      //添加轮廓
            this.$root.$broadcast('mouseoverListItem', item.dataId, item.currentType);
        },
        mouseoutItem: function(item){       //删除轮廓
            this.$root.$broadcast('mouseoutListItem', item.dataId);
        },
        getResultTag: function(tag){
            return this.TAG_MAPPING[tag] || null;
        },
        sortBy: function(sortBy){
            this.sortByField(sortBy);
            this._loadHouseList(this.mainInfo.dataId, this.mainInfo.currentType);
        },
        _normalizeSearchResult: function(currentType, res){
            var isLoadHouseList = currentType === 'village';
            var list = isLoadHouseList ? res.data.list : res.dataList;

            return {
                count: res.dataList.length,
                list: list,
                isHouseList: isLoadHouseList
            };
        },
        _loadHouseList: function(dataId, type){
            var params = $.extend({}, {
                limit_offset : 1,  //页号
                limit_count : 100 //每页记录数
            }, {community_id: dataId});

            var me = this;
            this.isLoadingList = true;
            if(this.searchParams.s){
                params.s = this.searchParams.s;
            }
            return commonService.ajaxGetBase(commonService.getSearchHouseUrl(this.houseType), commonService.getFilters(this.searchParams), params, function(res){
                console.log(res);

                this.searchResults = this._normalizeSearchResult(type, res);

                this.isLoadingList = false;
            }.bind(this)).fail(function(){ me.isLoadingList = false; });
        }
    },
    computed: {
        displayBreadCrumb: function(){
            var b = this.breadCrumb || [],
                len = b.length;
            return b.filter(function(item, i){
                return item && item.showName && i < (len - 1);
            });
        }
    }
});