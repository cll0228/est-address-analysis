/**
 * Created by colin on 16/8/15.
 */


Vue.component('tolist-component', {
    vuex: {
        getters: {
            breadCrumb: function(state) { return state.breadCrumb; },
            mainInfo: function(state) { return state.mainInfo; },
            searchParams: commonService.getSearchParams
        }
    },
    template: '<a target="_blank" href="javascript:;" id="gotoListMode" class="to-list" @click="toList()">\
                    <em class="iconfont icon-map-list" style="margin-right: 4px;"></em>列表找房</a>\
                </a>',
    props: ['houseType'],
    methods: {
        toList: function() {
            //构建到列表页的链接
            var baseUrl = '';
            if(this.searchParams.siteType === commonService.SITE_TYPE_DITIE){
                baseUrl = this.houseType === commonService.ERSHOUFANG ? '/ditiefang/' : '/ditiezufang/';
            }else{
                baseUrl = this.houseType === commonService.ERSHOUFANG ? '/ershoufang/' : '/zufang/';
            }

            var block = '',
                m = this.mainInfo;

            if(this.searchParams.siteType === commonService.SITE_TYPE_DITIE){       //地铁找房
                if(this.searchParams.type === 'line' || this.searchParams.type === 'stop' || this.searchParams.query){
                    var lineInfo = commonService.parseBreadCrumb(this.breadCrumb, 'line'),
                        stopInfo = commonService.parseBreadCrumb(this.breadCrumb, 'stop');
                    if(lineInfo){
                        block = 'li' + lineInfo.dataId;
                    }
                    if(stopInfo){        //地铁站
                        block += 's' + stopInfo.dataId;
                    }
                    if(block){
                        block += '/';
                    }
                }
            }else{                              //区域找房
                if(m && (m.currentType == "district" || m.currentType == "plate")){
                    block = m.dataId + "/";
                }
            }

            //小区m
            var community = m && m.currentType === 'village' ? ('q' + m.dataId) : '';

            //查询条件
            var filter = commonService.buildFilterParams(this.searchParams);

            //排序
            var sort = this.searchParams.s ? this.searchParams.s : '';

            var url = baseUrl + block + filter + sort + community;

            $("#gotoListMode").attr("href", url).click();
        }
    }
});