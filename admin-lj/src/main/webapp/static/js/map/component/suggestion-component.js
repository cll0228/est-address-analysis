/**
 * Created by colin on 16/7/26.
 */


/***********************
 关键字搜索
 ************************/
Vue.component('suggestion-component', {
    vuex: {
        getters: { searchParams: commonService.getSearchParams},
        actions: {
            doSearch: actions.doSearch
        }
    },
    watch: {
        'searchParams.query': function(val){
            if(!val){ this.inputKeyword = ''; }
        }
    },
    template: '<div class="c-searchbox ml_10">\
                    <div id="suggest-cont" class="suggest-wrap" style="width: 500px; display: none;">\
                        <ul style="width: 100%;" class="ui-autocomplete ui-front ui-menu ui-widget ui-widget-content" id="suggestion"></ul>\
                    </div>\
                    <input type="text" class="c-searchbox__input" name="keyword" id="keyword-box" placeholder="请输入区县、街道、居委、道路或小区地址"\
                        maxlength="50" autocomplete="off" popdiv="suggestion" v-model="inputKeyword" @click="handleClickAndFocus()" @focus="handleClickAndFocus()" @keyup="handleKeyup()" @keyup.13="searchKeyword()">\
                        <i class="icon-cancel c-searchbox__icon-cancel" @click="handleCancel()" v-show="keyword"></i>\
                    <button class="c-searchbox__button" type="button" @click="searchKeyword()"><i class="icon-message"></i></button>\
                </div>',
    props: ['houseType'],
    created: function(){
        var me = this;
        setTimeout(function(){
            $('#keyword-box').suggestion({
                //urlOrData: '/headerMind.json?cityCode=' + headerParameters.cityCode,
                urlOrData: headerParameters.quxianhost+'/search.do?cityCode=' + headerParameters.cityCode,
                dataKey: 'list',
                setRequestParams: function(requstParams){
                    requstParams.keyword = $.trim($('#keyword-box').val()).replace(/[:./#%?\\]/g,'');
                    requstParams.pageType = me.houseType === commonService.ERSHOUFANG ? 'ditu' : 'zufangditu';
                },
                selectCallback: function(item){	//选中选项， 执行回调
                    me.searchKeyword(item);
                    me.$emit('selectItem');
                },
                crossDomain : false
            });
            me.inputKeyword = me.searchParams.query;

            //触发显示自动提示下拉框
            setTimeout(function(){
                if(!commonService.getIsDidSearch()){
                    $('#keyword-box').keyup();
                }
            }, 200);
        }, 100);
    },
    data: function(){
        return {
            inputKeyword: '',
            helper: {
                isShowSuggestPop: false,
                isShowCancel: false
            }
        };
    },
    computed: {
        keyword: function(){ return $.trim(this.inputKeyword); }
    },
    methods: {
        searchKeyword: function(selectedItem){
            commonService.setIsDidSearch(true);
            if(!this.keyword){	//如果输入空格, 则去掉空格
                this.emptyKeyword();
            }

            var params = {};
            if(selectedItem){       //从下拉中选择
                //params.keyword = selectedItem.showName;
                params.keyword = selectedItem.name;
                params.siteType = (selectedItem.type === 'line' || selectedItem.type === 'stop') ? commonService.SITE_TYPE_DITIE : commonService.SITE_TYPE_QUYU;
                params.keyType = selectedItem.type;
                params.keyId = selectedItem.id;
                if(selectedItem.type == 5){
                    $('.c-filterbox__list').css({display:'none'});
                } else{
                    $('.c-filterbox__list').css({display:'block'});
                }
                if(selectedItem.type == 5 || selectedItem.type == 4){
                    params.siteType = "xiaoqu";
                }
                if(selectedItem.type == 1){
                    params.siteType = "quyu";
                }
                if(selectedItem.type == 2){
                    params.siteType = "jiedao";
                }
                if(selectedItem.type == 3){
                    params.siteType = "juwei";
                }
            }else{                  //直接按回车或点击搜索按钮
                params.keyword = this.keyword;
                params.siteType = commonService.SITE_TYPE_QUYU;
                params.keyType = null;
                params.keyId = null;
            }

            var t = setTimeout(function(){
                this.doSearch(params);
            }.bind(this), 50);

            //处理自动完成, 下拉用上下箭头选择数据后, 按回车, 此时会触发两次searchKeyword方法,
            //一次是@keyup.13="searchKeyword()", 另一次是suggestion.js的调用的selectCallback
            //解决方案是: 把第一次的请求cancel掉, 只发第二次请求
            this.$on('selectItem', function(){
                if(!selectedItem){      //只cancel直接回车@keyup.13="searchKeyword()的请求
                    clearTimeout(t);
                }
            });
        },
        emptyKeyword: function(){
            this.inputKeyword = '';
        },
        handleKeyup: function(){
            if(!this.keyword){
                $("#suggest-cont").hide();
            }
        },
        handleClickAndFocus: function(){
            if(this.keyword && $("#suggestion li").length !=0 ){
                $("#suggest-cont, #suggestion").css("display","block");
            }
        },
        handleCancel: function(){
            this.inputKeyword = '';
        }
    }
});