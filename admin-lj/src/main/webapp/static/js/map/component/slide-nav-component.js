/**
 * Created by colin on 16/8/16.
 */


Vue.component('slide-nav-component', {
    vuex: {
        getters: {
            siteType: function(state) { return state.searchParams.siteType}
        },
        actions: {
            setVisited: actions.setVisited,
            setSiteType: actions.setSiteType,
            clickLevel1Action: actions.clickLevel1,
            clickLevel2Action: actions.clickLevel2,
            clickLevel3Action: actions.clickLevel3
        }
    },
    created: function(){
        setTimeout(function(){
            commonService.ajaxGetBase('/api/v4/online/dict/city/info', null, null, function(res){
                //区域/板块
                var dList = res.data.info[0].district.map(function(d){
                    var normalizedDistrictList = commonService.normalizeItem(d, 'district');
                    normalizedDistrictList.children = commonService.normalizePlateList(d);

                    return normalizedDistrictList;
                });
                dList.unshift({type: 'city', dataId: headerParameters.cityCode, name: '全部'});
                this.districtList = dList;

                //地铁/地铁站
                var sList = res.data.info[0].subwayList.map(function(line){
                    var normalizedLineList = commonService.normalizeItem(line, 'line');
                    var childrenStop = (line.stopList || []).map(function(stop){ return commonService.normalizeItem(stop, 'stop') });
                    childrenStop.unshift({type: 'line', dataId: line.lineId, name: '全部'});
                    normalizedLineList.children = childrenStop;

                    return normalizedLineList;
                });
                this.subwayList = sList;
            }.bind(this));
        }.bind(this), 1000);
    },
    template: '<div class="side-bar" id="sidebarWrap">\
                        <a href="javascript:;"><div class="side-bar__item side-bar__item-quyu"\
                                 @mouseover="mouseoverQuyu()" @mouseout="mouseoutLevel()" @click="clickQuyu()" :class="{\'side-bar__item--active\': isActiveQuyu()}">区域\
                        </div></a>\
                        <a href="javascript:;"><div class="side-bar__item icon_nav_district left-nav-item-ditie" \
                                 @mouseover="mouseoverDitie()" @mouseout="mouseoutLevel()" @click="clickDitie()" :class="{\'side-bar__item--active\': isActiveDitie()}">\
                                 <svg class="icon-left-nav-ditie"><use xlink:href="/static/css/map/map-icon.svg#icon-ditie"></use></svg>\
                                 地铁\
                        </div></a>\
                        <div class="side-bar__level1" :class="{\'gio_district\': isActiveQuyu(), \'gio_line\': isActiveDitie()}" id="districtWrap" \
                            :style="{display: showLevel2 && \'block\' || \'none\'}" @mouseover="mouseoverLevel(2)" @mouseout="mouseoutLevel()">\
                                <a href="javascript:;" class="side-bar__level1-item" v-for="d in datasource" @click="clickLevel2(d)" @mouseover="mouseoverLevel2(d)"\
                                    :class="{\'side-bar__level1-item--selected\': level2Selected && level2Selected.dataId == d.dataId, \'side-bar__level1-item--active\': level2Mouseovered && level2Mouseovered.dataId == d.dataId}"\
                                    gahref="{{d.isAll ? \'district-nolimit\' : d.dataId}}">{{d.name}}</a>\
                        </div>\
                        <div class="side-bar__level2" :class="{\'gio_plate\': isActiveQuyu(), \'gio_stop\': isActiveDitie()}" id="plateWrap" \
                                v-show="showLevel3 && level2Mouseovered.children.length > 0" @mouseover="mouseoverLevel(3)" @mouseout="mouseoutLevel()">\
                                <!-- 地铁站 -->\
                            <div class="side-bar__level2-item" v-for="item in level2Mouseovered.children" v-if="isMouseoverDitie()">\
                                <p class="side-bar__level2-item-sublist">\
                                    <a href="javascript:;" gahref="{{item.isAll ? \'plate-nolimit\' : item.bizcircle_quanpin}}" @click="clickLevel3(item)"\
                                         :class="{\'side-bar__level2-item--selected\': level3Selected && level3Selected.dataId == item.dataId}">{{item.name}}</a>\
                                </p>\
                            </div>\
                            <!-- 板块 -->\
                            <div class="side-bar__level2-item" v-for="group in level2Mouseovered.children" v-if="!isMouseoverDitie()">\
                                <span class="side-bar__level2-item-letter" v-if="group.firstLetter">{{group.firstLetter}}</span>\
                                <p class="side-bar__level2-item-sublist">\
                                    <a href="javascript:;" class="side-bar__level2-item-subitem" gahref="{{plate.isAll ? \'plate-nolimit\' : plate.bizcircle_quanpin}}" @click="clickLevel3(plate)"\
                                        v-for="plate in group.plateList" :class="{\'side-bar__level2-item--selected\': level3Selected && level3Selected.dataId == plate.dataId}">{{plate.name}}</a>\
                                </p>\
                            </div>\
                        </div>\
                    </div>',
    data: function(){
        return {
            districtList: null,             //区域列表
            subwayList: null,               //地铁列表
            mouseoveredSiteType: null,
            level2Selected: null,            //选中区域|地铁线
            level3Selected: null,            //选中板块|地铁站
            level2Mouseovered: null,         //移上去的区域|地铁线
            level2Chidren: null,             //当前区域|地铁线下的板块|地铁站列表

            showLevel1: true,		//站点: 区域|地铁
            showLevel2: false,		//区域|地铁线
            showLevel3: false		//板块|地铁站
        };
    },
    methods: {
        clickQuyu: function(){
            this.setSiteType(commonService.SITE_TYPE_QUYU);
            this.clickLevel1Action({dataId: headerParameters.cityCode, type: 'city'});
        },
        clickDitie: function(){
            this.setSiteType(commonService.SITE_TYPE_DITIE);
            this.clickLevel1Action({dataId: headerParameters.cityCode, type: 'city'});
        },
        mouseoverQuyu: function(){
            this.mouseoverLevel(1);
            this.mouseoveredSiteType = commonService.SITE_TYPE_QUYU;
        },
        mouseoverDitie: function(){
            this.mouseoverLevel(1);
            this.mouseoveredSiteType = commonService.SITE_TYPE_DITIE;
        },
        mouseoverLevel: function(level){
            var nextLevel = level < 3 ? (level + 1) : 3;
            for(var i=1; i<=nextLevel; i++){
                this['showLevel' + i] = true;
            }
        },
        mouseoutLevel: function(){
            this.showLevel1 = this.showLevel2 = this.showLevel3 = false;
        },
        mouseoverLevel2: function(item){
            this.level2Mouseovered = item;
            console.log(this.level2Mouseovered);
        },
        clickLevel2: function(item){
            this.level2Selected = item;
            this.setSiteType(this.mouseoveredSiteType);
            this.mouseoutLevel();

            this.clickLevel2Action(item);

            //设置当前区域为访问过
            if(this.siteType === commonService.SITE_TYPE_QUYU) {
                this.setVisited([item.district_quanpin]);
            }
        },
        clickLevel3: function(item){		//点击板块
            this.level3Selected = item;
            this.level2Selected = this.level2Mouseovered;
            this.setSiteType(this.mouseoveredSiteType);

            var level2Selected = this.level2Selected;
            this.clickLevel3Action($.extend({parentDataId: level2Selected.dataId}, item));

            this.mouseoutLevel();

            //设置当前区域和板块为访问过
            if(this.siteType === commonService.SITE_TYPE_QUYU){
                this.setVisited([this.level2Selected.district_quanpin, this.level3Selected.bizcircle_quanpin]);
            }
        },
        isMouseoverDitie: function(){
            return this.mouseoveredSiteType === commonService.SITE_TYPE_DITIE;
        },
        isDitie: function(){
            return this.siteType === commonService.SITE_TYPE_DITIE;
        },
        isActiveQuyu: function(){
            if(this.mouseoveredSiteType === commonService.SITE_TYPE_DITIE && this.showLevel1){
                return false;
            }
            if(this.mouseoveredSiteType === commonService.SITE_TYPE_QUYU && this.showLevel1){
                return true;
            }
            return this.siteType === commonService.SITE_TYPE_QUYU;
        },
        isActiveDitie: function(){
            if(this.mouseoveredSiteType === commonService.SITE_TYPE_QUYU && this.showLevel1){
                return false;
            }
            if(this.mouseoveredSiteType === commonService.SITE_TYPE_DITIE && this.showLevel1){
                return true;
            }
            return this.siteType === commonService.SITE_TYPE_DITIE;
        }
    },
    computed: {
        datasource: function(){
            if(this.mouseoveredSiteType === commonService.SITE_TYPE_DITIE){
                return this.subwayList;
            }
            return this.districtList;
        }
    }
});