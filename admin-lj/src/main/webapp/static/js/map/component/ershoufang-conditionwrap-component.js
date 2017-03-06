/**
 * Created by colin on 16/8/11.
 */


Vue.component('ershoufang-conditionwrap-component', {
    vuex: {
        getters: {
            searchParams: commonService.getSearchParams
        }
    },
    template: '<single-condition-component :datasource="conditionMap[\'a\']" key="a" alias="高清智能用户数" gahrefval="sale-price-nolimit"  style="margin-left: 15px;"></single-condition-component>\
                <single-condition-component :datasource="conditionMap[\'l\']" key="l" alias="小区规模" gahrefval="area-nolimit" class="gio_area"></single-condition-component>\
                <single-condition-component :datasource="conditionMap[\'p\']" key="p" alias="小区均价" gahrefval="room-nolimit" class="gio_room"></single-condition-component>\
                <!--begin: 筛选条件：更多-->\
                <div class="c-filterbox ml_10" @mouseover="helper.showMoreOptions=true" @mouseout="helper.showMoreOptions=false">\
                    <div class="c-filterbox__selector">\
                        <p class="c-filterbox__selector-text">小区特征</p>\
                        <i class="iconfont c-filterbox__selector-icon" :class="{\'icon-arrow-down\': !isMouseover, \'icon-arrow-up\':isMouseover}"></i>\
                    </div>\
                                    <ul class="c-filterbox__multilist" v-show="helper.showMoreOptions">\
                                        <div class="c-filterbox__multilist-item" >\
                                            <single-condition-component :datasource="conditionMap[\'t\']" key="t" gahrefval="face-nolimit" class="gio_face c-filterbox--large"></single-condition-component>\
                                        </div>\
                                        <div class="c-filterbox__multilist-item" >\
                                            <single-condition-component :datasource="conditionMap[\'c\']" key="c" gahrefval="face-nolimit" class="gio_face c-filterbox--large"></single-condition-component>\
                                    </ul>\
                    </ul>\
                </div>',
    data: function () {
        return {
            helper: {
                showMoreOptions: false,
                residenceProperty: false,
                showHouseValue: false,
                showOcnBill: false
            }
        }
    },

    props: ['conditionMap']
});