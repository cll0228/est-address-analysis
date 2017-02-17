/**
 * Created by colin on 16/7/25.
 */

Vue.component('filter-bar-component', {
    vuex: {
        getters: { searchParams: commonService.getSearchParams},
        actions: {
            removeCondition: actions.removeCondition,
            clearAllCondition: actions.clearAllCondition
        }
    },
    template: '<div class="filter-bar" v-if="filterList.length > 0">\
                    <span class="filter-bar__label">已选择</span>\
                    <div class="filter-bar__value">\
                        <span v-for="item in filterList">{{item.text}}\
                            <a href="javascript:;" @click="removeCondition(item.key)" class="filter-bar__close-filter" gahref="tooltip_filters_delete_item">\
                            <svg><use xlink:href="/admin-lj/static/css/map/map-icon.svg#icon-close"></use></svg>\
                            </a>\
                        </span>\
                        <a href="javascript:;" @click="clearAllCondition" class="filter-bar__clear-filter" gahref="tooltip_filters_empty">\
                            <svg><use xlink:href="/admin-lj/static/css/map/map-icon.svg#icon-delete"></use></svg>\
                            <span style="text-decoration: underline;">清空</span>\
                        </a>\
                    </div>\
                </div>',
    props: ['houseType', 'conditionMap'],
    computed: {
        filterList: function() {
            var result = [];

            //关键字
            if(this.searchParams.query){
                result.push(commonService.buildFilterBarItem('query', this.searchParams.query, this.searchParams.query));
            }

            //过滤条件
            var fs = commonService.getFilters(this.searchParams);
            for (var k in fs) {

                if (fs[k]) {
                    result.push(commonService.buildFilterBarItem(k, fs[k], commonService.getTextByCode(fs[k], this.conditionMap[k])));
                }
            }
            return result;
        }
    }
});
