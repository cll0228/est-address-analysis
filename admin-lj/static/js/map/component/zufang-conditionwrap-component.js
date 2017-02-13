/**
 * Created by colin on 16/8/11.
 */


Vue.component('zufang-conditionwrap-component', {
    template:  '<single-condition-component :datasource="conditionMap[\'z\']" key="z" alias="租金" gahrefval="sale-price-nolimit" class="gio_price" style="margin-left: 15px; width: 120px;"></single-condition-component>\
                <single-condition-component :datasource="conditionMap[\'a\']" key="a" alias="面积" gahrefval="area-nolimit" class="gio_area"></single-condition-component>\
                <single-condition-component :datasource="conditionMap[\'l\']" key="l" alias="户型" gahrefval="room-nolimit" class="gio_room"></single-condition-component>\
                <!--begin: 筛选条件：更多-->\
                <div class="c-filterbox ml_10" @mouseover="helper.showMoreOptions=true" @mouseout="helper.showMoreOptions=false">\
                    <div class="c-filterbox__selector">\
                        <p class="c-filterbox__selector-text">更多</p>\
                        <i class="iconfont c-filterbox__selector-icon" :class="{\'icon-arrow-down\': !isMouseover, \'icon-arrow-up\':isMouseover}"></i>\
                    </div>\
                    <ul class="c-filterbox__multilist" v-show="helper.showMoreOptions">\
                        <div class="c-filterbox__multilist-item">\
                            <p class="c-filterbox__multilist-item-label">朝向</p>\
                            <single-condition-component :datasource="conditionMap[\'f\']" key="f" gahrefval="face-nolimit" class="gio_face c-filterbox--large"></single-condition-component>\
                        </div>\
                        <div class="c-filterbox__multilist-item">\
                            <span class="c-filterbox__multilist-item-label">楼层</span>\
                            <single-condition-component :datasource="conditionMap[\'c\']" key="c" gahrefval="floor-nolimit" class="gio_floor c-filterbox--large"></single-condition-component>\
                        </div>\
                        <div class="c-filterbox__multilist-item">\
                            <span class="c-filterbox__multilist-item-label">装修</span>\
                            <single-condition-component :datasource="conditionMap[\'x\']" key="x" gahrefval="decoration-nolimit"  class="gio_decoration c-filterbox--large"></single-condition-component>\
                        </div>\
                        <div class="c-filterbox__multilist-item">\
                            <span class="c-filterbox__multilist-item-label">品牌</span>\
                            <single-condition-component :datasource="conditionMap[\'n\']" key="n" gahrefval="brand-nolimit" class="gio_brand c-filterbox--large"></single-condition-component>\
                        </div>\
                        <div class="c-filterbox__multilist-item">\
                            <span class="c-filterbox__multilist-item-label">类型</span>\
                            <single-condition-component :datasource="conditionMap[\'i\']" key="i" gahrefval="rentType-nolimit" class="gio_rent_type c-filterbox--large"></single-condition-component>\
                        </div>\
                        <div class="c-filterbox__multilist-item">\
                            <span class="c-filterbox__multilist-item-label">特色标签</span>\
                            <single-condition-component :datasource="conditionMap[\'t\']" key="t" gahrefval="lable-nolimit" class="gio_label c-filterbox--large"></single-condition-component>\
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