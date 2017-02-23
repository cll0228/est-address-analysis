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
//        	commonService.ajaxGetBase('/api/v4/online/dict/city/info', null, null, function(res){
            commonService.ajaxGetBase2('/district.do', null, null, function(res){
                //区域/板块
                var dList = res.list.map(function(d){
//        		var dList = res.data.info[0].district.map(function(d){
                    var normalizedDistrictList = commonService.normalizeItem(d, 'district');
                    normalizedDistrictList.children = commonService.normalizePlateList(d);
                    for (var k = 1,length = normalizedDistrictList.children.length;k < length; k++) {
                    	normalizedDistrictList.children[k].children = commonService.normalizeNeighborhoodList(d.townList[k-1]);
                    	}
                    return normalizedDistrictList;
                });
                dList.unshift({type: 'city', dataId: headerParameters.cityCode, name: '全部'});
                this.districtList = dList;

                //地铁/地铁站
                /*var sList = res.data.info[0].subwayList.map(function(line){
                    var normalizedLineList = commonService.normalizeItem(line, 'line');
                    var childrenStop = (line.stopList || []).map(function(stop){ return commonService.normalizeItem(stop, 'stop') });
                    childrenStop.unshift({type: 'line', dataId: line.lineId, name: '全部'});
                    normalizedLineList.children = childrenStop;

                    return normalizedLineList;
                });
                this.subwayList = sList;*/
                var result = [];
                
/*                result.unshift({firstLetter: null, type: 'jidinghe', dataId: 5, name: '标清'});
                result.unshift({firstLetter: null, type: 'jidinghe', dataId: 4, name: '高清无EOC'});
                result.unshift({firstLetter: null, type: 'jidinghe', dataId: 3, name: '高清+EOC'});*/
                result.unshift({firstLetter: null, type: 'jidinghe', dataId: 2, name: '高清机顶盒+EOC'});
                result.unshift({firstLetter: null, type: 'jidinghe', dataId: 1, name: '智能机顶盒'});
                result.unshift({firstLetter: null, type: 'jidinghe', dataId: 0, name: '全部'});
                this.subwayList = result;
            }.bind(this));
        }.bind(this), 1000);
    },
    template: '<div class="side-bar" id="sidebarWrap">\
        <a href="javascript:;"><div class="side-bar__item side-bar__item-quyu"\
                 @mouseover="mouseoverQuyu()" @mouseout="mouseoutLevel()" @click="clickQuyu()" :class="{\'side-bar__item--active\': isActiveQuyu()}">区县\
        </div></a>\
    	<a href="javascript:;"><div class="side-bar__item side-bar__item-jidinghe" \
        		 @mouseover="mouseoverDitie()" @mouseout="mouseoutLevel()" @click="clickDitie()" :class="{\'side-bar__item--active\': isActiveDitie()}">机顶盒\
		</div></a>\
    	<!-- 区县 -->\
        <div class="side-bar__level1" :class="{\'gio_district\': isActiveQuyu(), \'gio_line\': isActiveDitie()}" id="districtWrap" \
            :style="{display: showLevel2 && \'block\' || \'none\'}" @mouseover="mouseoverLevel(2)" @mouseout="mouseoutLevel()">\
                <a href="javascript:;" class="side-bar__level1-item" v-for="d in datasource" @click="clickLevel3(d,1)" @mouseover="mouseoverLevel2(d)"\
                    :class="{\'side-bar__level1-item--selected\': level2Selected && level2Selected.dataId == d.dataId, \'side-bar__level1-item--active\': level2Mouseovered && level2Mouseovered.dataId == d.dataId}"\
                    gahref="{{d.isAll ? \'district-nolimit\' : d.dataId}}">{{d.name}}</a>\
        </div>\
        <div class="side-bar__level2" :class="{\'gio_plate\': isActiveQuyu(), \'gio_stop\': isActiveDitie()}" id="plateWrap" \
                v-show="showLevel3 && level2Mouseovered.children.length > 0" @mouseover="mouseoverLevel(3)" @mouseout="mouseoutLevel()">\
            <!-- 板块 -->\
            <div class="side-bar__level2-item" v-for="group in level2Mouseovered.children" v-if="!isMouseoverDitie()">\
                <span class="side-bar__level2-item-letter" v-if="group.firstLetter">{{group.firstLetter}}</span>\
                <p class="side-bar__level2-item-sublist">\
                    <a href="javascript:;" class="side-bar__level2-item-subitem" gahref="{{group.isAll ? \'group-nolimit\' : group.dataId}}" @mouseover="mouseoverLevel3(group)" @click="clickLevel3(group,2)"\
                         :class="{\'side-bar__level2-item--selected\': level3Selected && level3Selected.dataId == group.dataId}">{{group.name}}</a>\
                </p>\
            </div>\
        </div>\
    	<div class="side-bar__level3" :class="{\'gio_plate\': isActiveQuyu(), \'gio_stop\': isActiveDitie()}" id="neighborhoodWrap" \
		        v-show="showLevel4 && level3Mouseovered.children.length > 0" @mouseover="mouseoverLevel(4)" @mouseout="mouseoutLevel()">\
		    <!-- 板块 -->\
		    <div class="side-bar__level3-item" v-for="group in level3Mouseovered.children" v-if="!isMouseoverDitie()">\
		        <span class="side-bar__level3-item-letter" v-if="group.firstLetter">{{group.firstLetter}}</span>\
		        <p class="side-bar__level3-item-sublist">\
		            <a href="javascript:;" class="side-bar__level3-item-subitem" gahref="{{group.isAll ? \'group-nolimit\' : group.dataId}}" @click="clickLevel3(group,3)"\
		                :class="{\'side-bar__level3-item--selected\': level4Selected && level4Selected.dataId == group.dataId}">{{group.name}}</a>\
		        </p>\
		    </div>\
		</div>\
    </div>',
    data: function(){
        return {
            districtList: null,             //区域列表
            subwayList: null,               //地铁列表
            mouseoveredSiteType: null,
            level2Selected: null,            //选中区县|地铁线
            level3Selected: null,            //选中街道|地铁站
            level4Selected: null,            //选中居委|地铁站
            level2Mouseovered: null,         //移上去的区域|地铁线
            level2Chidren: null,             //当前区域|地铁线下的板块|地铁站列表
            level3Mouseovered: null,         //移上去的街道|地铁线
            level3Chidren: null,             //当前区域|地铁线下的板块|地铁站列表
            div: null,						 //当前选择层级

            showLevel1: true,		//站点: 区域|地铁
            showLevel2: false,		//区县|机顶盒
            showLevel3: false,		//街道|地铁站
            showLevel4: false		//居委|地铁站
        };
    },
    methods: {
        clickQuyu: function(){
            this.setSiteType(commonService.SITE_TYPE_QUYU);
            this.clickLevel1Action({dataId: headerParameters.cityCode, type: '1'});
        },
        clickDitie: function(){
            this.setSiteType(commonService.SITE_TYPE_DITIE);
            this.clickLevel1Action({dataId: headerParameters.cityCode, type: '1'});
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
            var nextLevel = level < 4 ? (level + 1) : 4;
            for(var i=1; i<=nextLevel; i++){
                this['showLevel' + i] = true;
            }
        },
        mouseoutLevel: function(){
            this.showLevel1 = this.showLevel2 = this.showLevel3 = this.showLevel4 = false;
        },
        mouseoverLevel2: function(item){
            this.level2Mouseovered = item;
            console.log(this.level2Mouseovered);
        },
        mouseoverLevel3: function(item){
            this.level3Mouseovered = item;
            console.log(this.level3Mouseovered);
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
        clickLevel3: function(item,div){		//点击板块
        	if(div=="1"&&item.name=="全部") {
        		this.setSiteType(this.mouseoveredSiteType);
        	} else if((div=="1"&&item.name!="全部")||(div=="2"&&item.name=="全部")) {
        		this.setSiteType("jiedao");
        	} else if((div=="2"&&item.name!="全部")||(div=="3"&&item.name=="全部")) {
        		this.setSiteType("juwei");
        	} else if(div=="3"&&item.name!="全部") {
        		this.setSiteType("xiaoqu");
        	}
        	
        	
        	this.clickLevel1Action({dataId: item.dataId, type: div});

            this.mouseoutLevel();
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