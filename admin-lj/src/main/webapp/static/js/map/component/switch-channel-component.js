/**
 * Created by colin on 16/7/21.
 */

Vue.component('switch-channel-component', {
    template: '<a href="javascript:;">\
                <div class="c-switch-channel" @mouseover="isShowPop=true" @mouseout="isShowPop=false">\
					<span class="c-switch-channel__host">{{dictionary[houseType].name}}</span>\
					<ul class="c-switch-channel__list" v-show="isShowPop">\
						<li v-for="(key, value) in dictionary" @click="jump(key)" class="c-switch-channel__item" :class="{\'c-switch-channel__item--active\': houseType === key}" gahref="{{value.gahref}}" v-if="value.isShow">{{value.name}}</li>\
					</ul>\
				</div></a>',
    props: ['houseType'],
    data: function(){
        return {
            dictionary: {
                'ershoufang': {gahref: 'channel_ershoufang', name: '二手房', isShow: true},
                'zufang': {gahref: 'channel_zufang', name: '租房', isShow: headerParameters.cityCode === 'sh'}
            },
            isShowPop: false
        };
    },
    methods: {
        jump: function(code){
            this.isShowPop = false;
            if(code === this.houseType) return;

            if(code === 'zufang'){
                window.location = '/zufangditu';
            }else{
                window.location = '/ditu';
            }
        }
    }
});