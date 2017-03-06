﻿<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge" />
		<meta http-equiv="Cache-Control" content="no-transform" />
		<meta http-equiv="Cache-Control" content="no-siteapp" />
		<meta name="format-detection" content="telephone=no" />
        <meta http-equiv="pragma" content="no-cache"/>
		<link href="${ctx}/public/img/favicon.ico" type="image/x-icon" rel=icon>
		<link href="${ctx}/public/img/favicon.ico" type="image/x-icon" rel="shortcut icon">
		<title>运营平台</title>

		<link rel="stylesheet" href="${ctx}/public/css/common.css?v=1486395054054">
		<link rel="stylesheet" href="${ctx}/static/css/map/font.css?v=1486395054054">


		<link rel="stylesheet" href="${ctx}/static/css/map/style.css?v=1486395054054" />
		<script>
			var headerParameters = {
				env : 'production',
				publichost : '',
				statichost : 'http://localhost',
				apihost : 'http://soa.dooioo.com',
				quxianhost : 'http://localhost:9568/',
				cityCode : 'sh',
				cityName: '上海',
				cityCoordinate : {//城市中心坐标
					longitude : '121.479659',
					latitude : '31.238092'
				},
				hasSaveSearch : true //是否支持登录后把缓存在本地local中的“已保存搜索”数据存入服务端功能
			};
			var houseType = 'ershoufang';
		</script>
	</head>

	<body>
		<svg style="display: none" version="1.1" xmlns="http://www.w3.org/2000/svg">
			<symbol id="icon-ditie" viewBox="0 0 20 20">
				<path id="XMLID_1688_" d="M17.2,0H2.8C1.8,0,1,0.8,1,1.8V11v1v4.2c0,1,0.8,1.8,1.8,1.8H4v0.5C4,19.3,4.7,20,5.5,20S7,19.3,7,18.5V18
					h6v0.5c0,0.8,0.7,1.5,1.5,1.5s1.5-0.7,1.5-1.5V18h1.2c1,0,1.8-0.8,1.8-1.8V12v-1V1.8C19,0.8,18.2,0,17.2,0z M5.5,16
					C4.7,16,4,15.3,4,14.5S4.7,13,5.5,13S7,13.7,7,14.5S6.3,16,5.5,16z M14.5,16c-0.8,0-1.5-0.7-1.5-1.5s0.7-1.5,1.5-1.5
					s1.5,0.7,1.5,1.5S15.3,16,14.5,16z M18,11H2V1.8C2,1.3,2.3,1,2.8,1h14.5C17.7,1,18,1.3,18,1.8V11z"></path>
			</symbol>
		</svg>

		<div id="lianjia">
			<!-- 头部 -->
			<div class="map-header" id="headerWrap">
				<!-- logo  -->
				<a class="map-header__logo" href="" style="background:none" gahref="header_logo">海上人家运营平台</a>

				<!-- 自动完成搜索 -->
				<switch-channel-component house-type="ershoufang"></switch-channel-component>
				<suggestion-component v-ref:suggestioncomponent house-type="ershoufang"></suggestion-component>

				<!-- 筛选条件 -->
				<ershoufang-conditionwrap-component :condition-map="conditionMap" style="margin-left: 10px;"></ershoufang-conditionwrap-component>
			</div>


			<!-- 内容 -->
			<div class="map-content">
				<!-- 跳转到列表 -->
				<tolist-component house-type="ershoufang"></tolist-component>

				<!-- 区域|地铁找房 -->
				<slide-nav-component v-show="isShowLeft"></slide-nav-component>

				<!-- 搜索结果列 -->
				<searchlist-component v-ref:searchlistcomponent house-type="ershoufang" v-show="isShowLeft"></searchlist-component>

        <!-- 右侧地图 -->
        <div class="map-wrapper">
            <div class="zoom-tool">
                <a id="bzoom" class="zoom-tool__item icon-plus" onclick="addMapZoomSize();"></a>
                <a id="szoom" class="zoom-tool__item icon-minus" onclick="reduceMapZoomSize();"></a>
            </div>
            <filter-bar-component :condition-map="conditionMap" house-type="ershoufang"></filter-bar-component>

            <div class="map-wrapper__switch-hand" @click="isShowLeft=!isShowLeft">
                <i :class="{'icon-arrow-left': isShowLeft, 'icon-arrow-right': !isShowLeft}"></i>
            </div>
            <div id="mapWrap" style="height: 100%"></div>
            <ditie-map-component v-ref:ditiemapcomponent house-type="ershoufang"
                                 v-if="searchParams.siteType === 'ditie'"
                                 :is-full-screen="isShowLeft"></ditie-map-component>
            <map-component v-ref:mapcomponent house-type="ershoufang" v-if="searchParams.siteType === 'quyu'"
                           :is-full-screen="isShowLeft"></map-component>
        </div>
    </div>
</div>
</div>

		<!--begin: 虚拟DOM，用于解决地图上小区气泡中小区名的链接点击事件实现-->
		<a href="" target="_blank" id="link" style="display:none"></a>
		<!--end: 虚拟DOM，用于解决地图上小区气泡中小区名的链接点击事件实现-->

		<!-- 搜索列表模版 -->
		<template id="listContainer-template" style="display: none;">
				<div class="search-list">

					<!-- 顶部信息 -->
					<div id="listHeaderWrap" v-show="mainInfo && searchResults.list.length > 0">
						<h1 class="search-list__main-info">
							<i class="search-list__position-icon"></i>
							<div class="search-list__main-show-name" title="{{mainInfo.showName}}">
								<!-- {{searchParams.showName}} -->{{searchParams.showName}}
								<p class="search-list__breadcrumb">
									<!-- <span v-for="item in displayBreadCrumb">
										<a href="javascript:;" @click="clickBreadCrumb(item)" gahref="crumb_{{item.dataId}}" >{{item.showName}}</a>
										<span class="search-list__gt" v-if="$index != displayBreadCrumb.length - 1">&gt;</span>
									</span> -->
									<span v-if="searchParams.siteType == 'jiedao'">
										{{searchParams.cityName}}
									</span>
									<span v-if="searchParams.siteType == 'juwei'">
										{{searchParams.cityName}}
										<span class="search-list__gt" v-if="$index != displayBreadCrumb.length - 1">&gt;</span>
										{{searchParams.districtName}}
									</span>
									<span v-if="searchParams.siteType == 'xiaoqu'">
										{{searchParams.cityName}}
										<span class="search-list__gt" v-if="$index != displayBreadCrumb.length - 1">&gt;</span>
										{{searchParams.districtName}}
										<span class="search-list__gt" v-if="$index != displayBreadCrumb.length - 1">&gt;</span>
										{{searchParams.townNname}}
									</span>
								</p>
							</div>
							<span class="search-list__main-price" v-if="searchParams.siteType === 'quyu'">
								<span v-if="mainInfo.saleAvgPrice == 0">暂无数据</span>
								<span v-if="mainInfo.saleAvgPrice > 0" style="color: #e63636">{{mainInfo.saleAvgPrice}}<span style="font-size: 14px; font-weight: normal;">元/平</span></span>
							</span>
						</h1>

						<h2 class="search-list__list-header">
							<span v-if="mainInfo.currentType != 'village'">
								为您找到 <span class="search-list__stat-num">{{searchResults.list.length}}</span>
								<span v-if="searchParams.siteType == 'quyu'">个行政区</span>
								<span v-if="searchParams.siteType == 'jiedao'">个街道</span>
								<span v-if="searchParams.siteType == 'juwei'">个居委</span>
								<span v-if="searchParams.siteType == 'xiaoqu'">个小区</span>
								<span v-if="searchParams.siteType == 'jieguo'">个结果</span>
								<!-- 区域找房 -->
								<!-- <span v-if="mainInfo.currentType == 'city' && searchParams.siteType === 'quyu'">个行政区</span>
								<span v-if="mainInfo.currentType == 'district'">个板块</span> -->

								<!-- 地铁找房 -->
								<!-- <span v-if="mainInfo.currentType == 'city' && searchParams.siteType === 'ditie'">条线路</span>
								<span v-if="mainInfo.currentType == 'line'">个站点</span>

								<span v-if="mainInfo.currentType == 'plate' || mainInfo.currentType == 'stop'">个小区</span>
								， -->
							</span>
							<!-- <span>共 <span class="search-list__stat-num">{{searchResults.count}}</span> 套<span v-if="searchParams.siteType === 'ditie'">近地铁的</span>在售房源</span>

							<div class="search-list__sort-container" v-if="mainInfo.currentType == 'village'">
								<a class="search-list__sort-item" href="javascript:;" gahref="default-order" @click="sortBy()" :class="{'search-list__sort-item--active': !searchParams.s}">默认</a>
								<a class="search-list__sort-item" href="javascript:;" gahref="s7" @click="sortBy('s7')" :class="{'search-list__sort-item--active': searchParams.s == 's7'}">最新</a>
								<a class="search-list__sort-item" href="javascript:;" gahref="s1" @click="sortBy('s1')" :class="{'search-list__sort-item--active': searchParams.s == 's1'}">总价<i class="icon-up" gahref="s1"></i></a>
							</div> -->
						</h2>
					</div>

					<!-- 搜索列表 -->
					<div class="search-list__list" id="listResultWrap" v-show="searchResults.list.length > 0">
						<!-- 房源卡片列表 -->
						<ul v-if="searchResults.isHouseList">
							<a href="ershoufang/sh{{item.houseSellId}}.html" target="_blank" gahref="results_click_order_{{$index + 1 }}" v-for="item in searchResults.list">
								<li class="search-list__list-prop-item" data-id="{{item.dataId}}" gahref="results_click_order_{{$index + 1}}">
									<div class="search-list__thumb">
										<img width="124" height="124" :src="item.mainPhotoUrl ? item.mainPhotoUrl : '${ctx}/static/img/new-version/default_block.png'" gahref="results_click_order_{{$index + 1}}"
											 onerror="this.src='${ctx}/static/img/new-version/default_block.png'; this.onerror=null;" title="{{item.title}}">
										<div class="global__video-icon global__video-icon--small global__video-icon--right" v-if="item.videoDisplay" ></div>
									</div>
									<div class="search-list__info" gahref="results_click_order_{{$index + 1}}">
										<p class="search-list__info-title" gahref="results_click_order_{{$index + 1}}">{{item.title}}</p>
										<div class="search-list__info-item" gahref="results_click_order_{{$index + 1}}">
											<p class="search-list__info-item-base" gahref="results_click_order_{{$index + 1}}">{{item.room}}室{{item.hall}}厅 {{item.acreage}}平 {{item.face}}</p>
											<p class="search-list__info-item-price" gahref="results_click_order_{{$index + 1}}">{{item.showPrice}}<span class="unit" gahref="results_click_order_{{$index + 1}}">万</span></p>
										</div>
										<p class="search-list__info-item" gahref="results_click_order_{{$index + 1}}">{{item.propertyName}}</p>
										<p class="search-list__info-item-tag-wrapper" gahref="results_click_order_{{$index + 1}}">
											<span class="search-list__info-item-tag {{getResultTag(tag).className}}" v-for="tag in item.tags" v-show="getResultTag(tag)">
												{{getResultTag(tag).text}}
											</span>
										</p>
									</div>
								</li>
							</a>
						</ul>

						<!-- 非房源卡片列表 -->
						<ul v-if="!searchResults.isHouseList">
							<!-- <li class="search-list__list-item" gahref="{{item.dataId}}" v-for="item in searchResults.list"
								@click="clickListItem(item)" @mouseover="mouseoverItem(item)" @mouseout="mouseoutItem(item)" v-if="item.saleTotal > 0">
								<span class="search-list__show-name" title="{{item.showName}}" gahref="{{item.dataId}}">{{item.showName}}</span>
								<span class="search-list__price" gahref="{{item.dataId}}"><span v-if="searchParams.siteType !== 'ditie'">{{item.saleAvgPrice | formatPrice}}</span></span>
								<span class="search-list__separator" gahref="{{item.dataId}}"><span v-if="searchParams.siteType !== 'ditie'">|</span></span>
								<i class="search-list__arrow icon-arrow-right" gahref="{{item.dataId}}"></i>
								<span class="search-list__count" gahref="{{item.dataId}}">{{item.saleTotal}} 套</span>
							</li> -->
							<ul v-if="!searchResults.isHouseList">
							<li class="search-list__list-item">
								<span class="search-list__show-name" title="区域">区域</span>
								<span class="search-list__show-name-title"><span>户数</span></span>
								<span class="search-list__count">占比</span>
							</li>
							<li class="search-list__list-item" gahref="{{item.dataId}}" v-for="item in searchResults.list" @click="clickListItem(item)">
								<span class="search-list__show-name" title="{{item.showName}}" gahref="{{item.dataId}}" onmouseover="showMap('{{item.dataId}}')" onmouseout="hideMap();">{{item.showName}}</span>
								<span class="search-list__price" gahref="{{item.dataId}}"><span>{{item.households}}户</span></span>
								<span class="search-list__count" gahref="{{item.dataId}}">{{item.proportion}}%</span>
							</li>
						</ul>
						</ul>
					</div>


					<!--begin: 搜索无结果 -->
					<div class="noresult-wrapper" id="listWrapNoResult" v-show="!searchResults.count">
						<p class="noresult-wrapper__icon">
							呣，没有找到相关内容。
							<br />
							<a href="javascript:;" class="noresult-wrapper__clear-conditions" id="clearConditions" gahref="filters_empty" @click="clearAllCondition()">
								<i class="icon-remove" gahref="filters_empty"></i>
								清除全部条件
							</a>
						</p>
					</div>
					<!--end: 搜索无结果 -->

					<svg xmlns="http://www.w3.org/2000/svg" style="position:absolute;width:100px;height:60px;left: 50%;top:50%; -webkit-transform: translate(-50%,-50%); transform: translate(-50%,-50%);" v-if="isLoadingList">
						<circle cx="10" cy="50" r="0" stroke="none" fill="#009de8">
							<animate attributeName="r" values="0;10;0" keyTimes="0;.5;1" begin="0s" dur="1.4s" repeatCount="indefinite"></animate>
						</circle>
						<circle cx="40" cy="50" r="0" stroke="none" fill="#009de8">
							<animate attributeName="r" values="0;10;0" keyTimes="0;.5;1" begin=".2s" dur="1.4s" repeatCount="indefinite"></circle>
						</circle>
						<circle cx="80" cy="50" r="0" stroke="none" fill="#009de8">
							<animate attributeName="r" values="0;10;0" keyTimes="0;.5;1" begin=".5s" dur="1.4s" repeatCount="indefinite"></animate>
						</circle>
					</svg>
				</div>
			</div>
		</template>

		<script>
			//判断浏览器
			function checkBrowserIsLegal(){
				var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
				var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
				var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器

				var legalBrowser = true;

				if (isIE) {
					var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
					reIE.test(userAgent);
					var fIEVersion = parseFloat(RegExp["$1"]);

					legalBrowser = fIEVersion > 8.0;//如果小于版本ie8
				}

				return legalBrowser;
			}
			//阻止冒泡
			function stopBubble(e){
				if (e && e.stopPropagation ){//not IE
					e.stopPropagation();
				}
				else{//IE
					window.event.cancelBubble = true;
				}
				if(window.ubtApi && window.ubtApi.loggerEvent){
					window.ubtApi.loggerEvent(e);
				}
			}
			function callback_getUserInfo() {}
		</script>
		<script src="${ctx}/public/js/jquery/jquery.min.js"></script>
		<script src="${ctx}/public/js/jquery/jquery.cookie.js" defer></script>
		<script src="${ctx}/static/js/common.js"></script>
		<script src="${ctx}/public/js/saveSearchInit.js"></script>
		<script src="${ctx}/static/js/lib/vue.min.js"></script>
		<script src="${ctx}/static/js/lib/vuex.min.js"></script>

<!-- webpack begin -->
<script type="text/javascript" src="${ctx}/static/js/suggestion.js"></script>
<script type="text/javascript" src="${ctx}/static/js/map/service/commonService.js"></script>
<script type="text/javascript" src="${ctx}/static/js/map/vuex/store.js"></script>
<script type="text/javascript" src="${ctx}/static/js/map/component/slide-nav-component.js"></script>
<script type="text/javascript" src="${ctx}/static/js/map/component/switch-channel-component.js"></script>
<script type="text/javascript" src="${ctx}/static/js/map/component/suggestion-component.js"></script>
<script type="text/javascript" src="${ctx}/static/js/map/component/single-condition-component.js"></script>
<script type="text/javascript" src="${ctx}/static/js/map/component/ershoufang-conditionwrap-component.js"></script>
<script type="text/javascript" src="${ctx}/static/js/map/component/zufang-conditionwrap-component.js"></script>
<script type="text/javascript" src="${ctx}/static/js/map/component/searchlist-component.js"></script>
<script type="text/javascript" src="${ctx}/static/js/map/component/map-component.js"></script>
<script type="text/javascript" src="${ctx}/static/js/map/component/ditie-map-component.js"></script>
<script type="text/javascript" src="${ctx}/static/js/map/component/filter-bar-component.js"></script>
<script type="text/javascript" src="${ctx}/static/js/map/component/tolist-component.js"></script>
<script type="text/javascript" src="${ctx}/static/js/map/main.js"></script>
<script type="text/javascript" src="${ctx}/static/js/map/service/myMap.js"></script>
<!-- webpack end-->

<script src="http://api.map.baidu.com/api?v=2.0&ak=C106a48023d9606dcdad761cbc070095"></script>
<script src="http://api.map.baidu.com/library/RichMarker/1.2/src/RichMarker_min.js"></script>

</body>

</html>
