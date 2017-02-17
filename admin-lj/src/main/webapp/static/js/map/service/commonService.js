/**
 * Created by colin on 16/7/25.
 */
/*
 组件的公共服务
 */
window.commonService = {
    'SITE_TYPE_DITIE': 'ditie',
    'SITE_TYPE_QUYU': 'quyu',
    'ERSHOUFANG': 'ershoufang',
    'ZUFANG': 'zufang',
    getIsDidSearch: function(){
        return sessionStorage['isDidSearch'];
    },
    setIsDidSearch: function(isDidSearch){
        sessionStorage['isDidSearch'] = isDidSearch;
    },
    //ajax获取数据底层方法
    ajaxGetBase: function(url, urlParams, data, callback){
        var p = this.parseNotEmptyFields(urlParams);
        return $.ajax({
            type: "get",
            url : headerParameters.apihost + url + '?access_token=7poanTTBCymmgE0FOn1oKp&client=pc' + ($.isEmptyObject(p) ? '' : '&' + $.param(p)),
            dataType : "json",
            data : $.extend({cityCode : headerParameters.cityCode}, data || {}),
            success: function(res){
                if(res.status == "ok" && callback){
                    callback(res);
                }
            },
            error: function(response,status){
                console.log(status);
            }
        });
    },
    ajaxGetBase3: function(url,callback){
        return $.ajax({
            type: "get",
            url : url,
            success: function(res){
                // if(res.status == "ok" && callback){
                    callback(res);
                // }
            },
            error: function(response,status){
                console.log(status);
            }
        });
    },
    //ajax获取列表数据方法
    ajaxGetBase2: function(url, urlParams, data, callback){
        var p = this.parseNotEmptyFields(urlParams);
        return $.ajax({
            type: "get",
            url : headerParameters.quxianhost + url,
            dataType : "json",
            success: function(res){
            	callback(res);
            },
            error: function(response,status){
                console.log(status);
            }
        });
    },
    level2Scale: function(level){	//层级 => 缩放比例(地图比例尺与气泡层级对应关系（默认值）)
        if(level === 'district' || level === 'line'){
            return 12;
        }
        if(level === 'plate' || level === 'stop'){
            return 14;
        }
        if(level === 'village'){
            return 17;
        }
    },
    scale2Level: function(scale){	//缩放比例 => 层级
        if(scale === 11 || scale === 12 || scale === 13){
            return 'district';
        }
        if(scale === 14 || scale === 15){
            return 'plate';
        }
        if(scale === 16 || scale === 17 || scale === 18 || scale === 19){
            return 'village';
        }
    },
    getFilters: function (searchParams){
        var filters = this.getEmptyFilters();

        for(var key in filters){
            if(searchParams[key]){
                filters[key] = searchParams[key];
            }
        }
        return filters;
    },
    replaceState2History: function (searchParams){
        var strParams = $.param(commonService.parseNotEmptyFields(searchParams));
        history.replaceState(searchParams, '', location.href.split("?")[0] + (strParams ? '?' + strParams : '' ));
    },
    parseNotEmptyFields: function(source){
        var p = {};

        if(!source) return p;
        for(var key in source){
            if(source[key]){
                p[key] = source[key];
            }
        }
        return p;
    },
    parseUrlParams: function(){     //解析查询参数, 初始化状态
        var paramPairs = window.location.search.substring(1).split('&'),
            urlParams = {};
        paramPairs.forEach(function(pair){
            var keyValuePair = pair.split('='),
                key = keyValuePair[0],
                value = keyValuePair[1];
            if(keyValuePair.length == 2 && value){
                if(urlParams[key]){
                    urlParams[key] = decodeURIComponent(urlParams[key] + ',' + value);
                }else{
                    urlParams[key] = decodeURIComponent(value);
                }
            }
        });

        return urlParams;
    },
    getInitialState: function (){	//初始化状态
        var initialState = {
            query: null,	//关键字搜索
            dataId: headerParameters.cityCode,
            type: 'city',

            lineId: null,
            stopId: null,

            s: null	//排序
        };

        return $.extend(initialState, this.getEmptyFilters());
    },
    getDefaultRoot: function(){
        return 	{
            dataId: headerParameters.cityCode,
            type: 'city'
        };
    },
    getEmptyFilters: function(){
        var filters = {
            t: null,	//小区分类
            o: null,	//账单活跃度
            c: null,	//小区用户占比
            p: null,    //小区均价
            a: null,	//用户规模
            l: null,	//小區規模;
            f: null,	//是否订阅增值节目
            g: null     //不动产估值
        };

        return $.extend({}, filters);
    },
    formatPrice: function (price){
        if(!price) return;

        var p = (Math.round(price / 1000) / 10).toFixed(1); //均价：元->万元
        return (p == 0.0) ? '-' : (p + '万');
    },
    getTextByCode: function(code, datasource){
        for(var i= 0,len=datasource.length; i<len; i++){
            if(datasource[i].code == code){
                return datasource[i].codeDesc;
            }
        }
    },
    getSearchParams: function (state){  //state: 解析查询参数
        return state.searchParams;
    },
    parseCustomFilter: function(params, key, suffix){       //如自定义价格,面积等
        var val = params[key],
            result = null;
        if(val){
            if(val && val.indexOf('to') !== -1) {
                var pair = val.slice(1).split('to');
                result = this.buildFilterBarItem(key, val, pair[0] + '-' + pair[1] + suffix);
            }
        }
        return result;
    },
    buildFilterBarItem: function(key, val, text){
        return {
            key: key,
            code: val,
            text: text
        }
    },
    getBaseInfoUrl: function(houseType){
        if(houseType === this.ERSHOUFANG){
            return '/api/v4/online/house/ershoufang/getBaseInfo';
        }
        return '/api/v4/online/house/rent/getBaseInfo';
    },
    getListMapResultUrl: function(houseType){
        if(houseType === this.ERSHOUFANG){
            return '/api/v4/online/house/ershoufang/listMapResult';
        }
        return '/api/v4/online/house/rent/listMapResult';
    },
    getSearchHouseUrl: function(houseType){
        if(houseType === this.ERSHOUFANG){
            return '/api/v4/online/house/ershoufang/search';
        }
        return '/api/v4/online/rent/zufang/search';
    },
    normalizePlateList: function(currentDistrict){  //重新生成标准化格式的板块列表
        var group = null,
            result = [];
//        (currentDistrict.bizcircle || []).forEach(function(plate){
        (currentDistrict.townList || []).forEach(function(plate){
            var normalizedPlate = this.normalizeItem(plate, 'plate');

            /*if(plate.firstLetter){
                group = {
                    firstLetter: plate.firstLetter,
                    plateList: [normalizedPlate]
                };
                result.push(group);
            }else{
                if(group && group.plateList){
                    group.plateList.push(normalizedPlate);
                }
            }*/
            /*group = {
                    plateList: [normalizedPlate]
                };*/
                result.push(normalizedPlate);
        }.bind(this));

        result.unshift({firstLetter: null, type: 'district', dataId: currentDistrict.districtId, name: '全部'});
        return result.concat([]);
    },
    normalizeNeighborhoodList: function(currentDistrict){  //重新生成标准化格式的板块列表
        var group = null,
            result = [];
//        (currentDistrict.bizcircle || []).forEach(function(plate){
        (currentDistrict.neighborhoodList || []).forEach(function(neighborhood){
            var normalizedNeighborhood = this.normalizeItem(neighborhood, 'neighborhood');

            /*if(plate.firstLetter){
                group = {
                    firstLetter: plate.firstLetter,
                    plateList: [normalizedPlate]
                };
                result.push(group);
            }else{
                if(group && group.plateList){
                    group.plateList.push(normalizedPlate);
                }
            }*/
            /*group = {
            		neighborhoodList: [normalizedNeighborhood]
                };*/
                result.push(normalizedNeighborhood);
        }.bind(this));

        result.unshift({firstLetter: null, type: 'neighborhoodList', dataId: currentDistrict.townId, name: '全部'});
        return result.concat([]);
    },
    normalizeItem: function (item, tp){
        var type = tp,
            dataId,
            name;

        if(type === 'district'){
            dataId = item.districtId;
            name = item.districtName;
//            dataId = item.district_quanpin;
//            name = item.district_name;
        }else if(type === 'plate'){
//            dataId = item.bizcircle_quanpin;
//            name = item.bizcircle_name;
        	dataId = item.townId;
            name = item.townName;
        }else if(type === 'neighborhood'){
//          dataId = item.bizcircle_quanpin;
//          name = item.bizcircle_name;
        	dataId = item.neighborhoodId;
        	name = item.neighborhoodName;
        }else if(type === 'line'){
            dataId = item.lineId;
            name = item.lineName;
        }else if(type === 'stop'){
            dataId = item.stopId;
            name = item.stopName;
        }

        return {
            type: type,
            dataId: dataId,
            name: name
        };
    },
    parseBreadCrumb: function(breadCrumb, type){      //从面包屑中解析出地铁线或地铁站
        if(!breadCrumb) return null;

        for(var i= 0, len=breadCrumb.length; i<len; i++){
            if(breadCrumb[i] && breadCrumb[i].currentType === type){
                return breadCrumb[i];
            }
        }
        return null;
    },
    buildFilterParams: function(searchParams){      //构建url中的查询参数
        var fs = this.getFilters(searchParams),
            results = [],
            val;

        for(var key in fs){
            val = fs[key];
            if(val){
                if(val.indexOf(',') !== -1){        //一个key多个value的情况, 如租房地图找房的"特色标签"在地图找房中为单选, 在列表页中为多选
                    val.split(',').forEach(function(childVal){
                        results.push(childVal);
                    });
                }else{
                    results.push(val);
                }
            }
        }
        return results.join('');
    },
    computeListHeight: function (){
        $('#listResultWrap').css('max-height', ($(window).height() - $('#listHeaderWrap').outerHeight() - 75) + 'px');
    }
};