/**
 * Created by colin on 16/7/25.
 */

/***********************
 条件筛选
 ************************/
Vue.component('single-condition-component', {
    template: '<div class="c-filterbox ml_10" @mouseover="isMouseover=true" @mouseout="isMouseover=false">\
                    <div class="c-filterbox__selector">\
                        <p class="c-filterbox__selector-text" :class="{\'c-filterbox__selector-text--selected\': searchParams[key] != null}">{{searchParams[key] === null && alias ? alias : getTextByCode(searchParams[key], datasource)}}</p>\
                        <i class="c-filterbox__selector-icon" :class="{\'icon-arrow-down\': !isMouseover, \'icon-arrow-up\':isMouseover}"></i>\
                    </div>\
                    <ul class="c-filterbox__list" :class="{\'c-filterbox__list--show\': isMouseover}" v-show="showOptions">\
                        <li gahref="{{item.code ? item.code : gahrefval}}" class="c-filterbox__item" :class="{\'c-filterbox__item--selected\': searchParams[key] == item.code}" \
                        v-for="item in datasource" @click="selectItem(item)">{{item.codeDesc}}</li>\
                    </ul>\
                </div>',
    props: ['key', 'datasource', 'alias', 'gahrefval'],
    vuex: {
        getters: { searchParams: commonService.getSearchParams },
        actions: {
            selectFilter: actions.selectFilter
        }
    },
    data: function(){ return { showOptions: true, isMouseover: false }; },
    methods: {
        getTextByCode: commonService.getTextByCode,
        selectItem: function(item){
            this.showOptions = false;
            setTimeout(function(){
                this.showOptions = true;
            }.bind(this), 500);
            this.selectFilter({key: this.key, value: item.code});
        }
    }
});