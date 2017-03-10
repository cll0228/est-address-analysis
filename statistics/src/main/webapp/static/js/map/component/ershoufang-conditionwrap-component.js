/**
 * Created by colin on 16/8/11.
 */


Vue.component('ershoufang-conditionwrap-component', {
    vuex: {
        getters: {
            searchParams: commonService.getSearchParams
        }
    },
    template:  '<single-condition-component :datasource="conditionMap[\'p\']" key="p" alias="售价" gahrefval="sale-price-nolimit" class="gio_price" style="margin-left: 15px;"></single-condition-component>\
                <single-condition-component :datasource="conditionMap[\'a\']" key="a" alias="面积" gahrefval="area-nolimit" class="gio_area"></single-condition-component>\
                <single-condition-component :datasource="conditionMap[\'l\']" key="l" alias="户型" gahrefval="room-nolimit" class="gio_room"></single-condition-component>\
                <!--begin: 筛选条件：更多-->\
                <div class="c-filterbox ml_10" @mouseover="helper.showMoreOptions=true" @mouseout="helper.showMoreOptions=false">\
                    <div class="c-filterbox__selector">\
                        <p class="c-filterbox__selector-text">更多</p>\
                        <i class="iconfont c-filterbox__selector-icon" :class="{\'icon-arrow-down\': !isMouseover, \'icon-arrow-up\':isMouseover}"></i>\
                    </div>\
                    <ul class="c-filterbox__multilist" v-show="helper.showMoreOptions">\
                        <div class="c-filterbox__multilist-item" v-if="searchParams.siteType === \'ditie\'">\
                            <span class="c-filterbox__multilist-item-label">距离</span>\
                            <single-condition-component :datasource="conditionMap[\'g\']" key="g" gahrefval="distance-nolimit" class="gio_distance c-filterbox--large"></single-condition-component>\
                        </div>\
                        <div class="c-filterbox__multilist-item">\
                            <span class="c-filterbox__multilist-item-label">朝向</span>\
                            <single-condition-component :datasource="conditionMap[\'f\']" key="f" gahrefval="face-nolimit" class="gio_face c-filterbox--large"></single-condition-component>\
                        </div>\
                        <div class="c-filterbox__multilist-item">\
                            <span class="c-filterbox__multilist-item-label">房龄</span>\
                            <single-condition-component :datasource="conditionMap[\'y\']" key="y" gahrefval="house-age-nolimit" class="gio_house_age c-filterbox--large"></single-condition-component>\
                        </div>\
                        <div class="c-filterbox__multilist-item">\
                            <span class="c-filterbox__multilist-item-label">楼层</span>\
                            <single-condition-component :datasource="conditionMap[\'c\']" key="c" gahrefval="floor-nolimit" class="gio_floor c-filterbox--large"></single-condition-component>\
                        </div>\
                        <div class="c-filterbox__multilist-item">\
                            <span class="c-filterbox__multilist-item-label">房本年限</span>\
                            <single-condition-component :datasource="conditionMap[\'u\']" key="u" gahrefval="fangben-nolimit" class="gio_fangben c-filterbox--large"></single-condition-component>\
                        </div>\
                        <div class="c-filterbox__multilist-item">\
                            <span class="c-filterbox__multilist-item-label">装修</span>\
                            <single-condition-component :datasource="conditionMap[\'x\']" key="x" gahrefval="decoration-nolimit"  class="gio_decoration c-filterbox--large"></single-condition-component>\
                        </div>\
                        <div class="c-filterbox__multilist-item">\
                            <span class="c-filterbox__multilist-item-label">类型</span>\
                            <single-condition-component :datasource="conditionMap[\'o\']" key="o" gahrefval="houseTypeCode-nolimit"  class="gio_houseTypeCode c-filterbox--large"></single-condition-component>\
                        </div>\
                        <div class="c-filterbox__multilist-item">\
                            <span class="c-filterbox__multilist-item-label">标签</span>\
                            <single-condition-component :datasource="conditionMap[\'v\']" key="v" gahrefval="lable-nolimit" class="gio_label c-filterbox--large"></single-condition-component>\
                        </div>\
                    </ul>\
                </div>',
    data: function(){
        return {
            helper: {
                showMoreOptions: false
            }
        }
    },
    props: ['conditionMap']
});