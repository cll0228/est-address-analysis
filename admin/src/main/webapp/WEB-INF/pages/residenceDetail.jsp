<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/include/top.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var="pageTitle1" value="主页"/>
<c:set var="pageTitle2" value="地址标准化"/>
<%@include file="/WEB-INF/pages/include/header.jsp" %>
<head>
    <title>小区搜索</title>
</head>
<body>
<body>
<div class="container">
    
    <div class="row" id="radarPrice">
        <div class="col-md-12">
            <!-- BEGIN PORTLET-->
            <div class="portlet light bordered">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="icon-home font-dark"></i>
                        <span class="caption-subject font-dark sbold uppercase">小区详情</span>
                    </div>

                </div>
                <div class="portlet-body">

                    <div class="row">
                    	<div class="col-md-7">
                            <div class="table-scrollable">
                                <table class="table table-striped table-hover">
                                    <tbody>
                                    <tr style="display:none">
                                        <th> 小区地址 </th>
                                        <td id="id">${ofResidences.id}</td>
                                        <td></td>
                                    </tr>
                                    <tr style="display:none">
                                        <th> 经度 </th>
                                        <td id="lon">${ofResidences.lon}</td>
                                        <td></td>
                                    </tr>
                                    <tr style="display:none">
                                        <th> 纬度 </th>
                                        <td id="lat">${ofResidences.lat}</td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <th width="30%"> 小区名称 </th>
                                        <td id="residenceName">${ofResidences.residenceName}</td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <th> 小区别名 </th>
                                        <td id="aliases">${ofResidences.aliases}</td>
                                        <td></td>
                                    </tr>
                                    <tr>
                                        <th> 小区地址 </th>
                                        <td id="residenceAddr">${ofResidences.residenceAddr}</td>
                                        <td></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <!-- END PORTLET-->
                            <div class="row">
                            	<div class="col-md-6">
                            		<table class="table table-bordered table-hover">
                                    <tbody>
                                    <tr>
                                        <th width="30%"> 子小区 </th>
                                        <td id="childName"></td>
                                    </tr>
                                    <tr>
                                        <th> 子小区别名 </th>
                                        <td id="childAliases"></td>
                                    </tr>
                                    <tr>
                                        <th> 子小区地址 </th>
                                        <td id="chlidResidenceAddr"></td>
                                    </tr>
                                    </tbody>
                                </table>
                            	</div>
                            	<div class="col-md-6">
                            		<table class="table table-bordered table-hover">
                                    <tbody>
                                    <tr>
                                        <th width="30%"> 子小区 </th>
                                        <td id="childName2"></td>
                                    </tr>
                                    <tr>
                                        <th> 子小区别名 </th>
                                        <td id="childAliases2"></td>
                                    </tr>
                                    <tr>
                                        <th> 子小区地址 </th>
                                        <td id="chlidResidenceAddr2"></td>
                                    </tr>
                                    </tbody>
                                </table>
                            	</div>
                         <div class="col-md-4">
                        	<button class="btn btn-success" type="button" onclick="window.history.back()"><i class="fa fa-arrow-left"></i> 返回上一页</button>
                        </div>
                        <div class="col-md-4">
                    		<button class="btn btn-success" type="button" data-toggle="modal" href="#modifyDetail"><i class="fa fa-file-o"></i> 修改小区信息</button>
                        </div>
                        <div class="col-md-4">
                        	<button class="btn btn-success" type="button"><i class="fa fa-plus"></i> 添加子小区</button>
                        </div>
                            </div>
                        </div>
                    	
                        <div class="col-md-5" id="map" style="height: 350px;size: 350px;border: 10px;" >
                        </div>
                        
                    </div>
                    
                    
                </div>
            </div>
        </div>
        <!-- END PORTLET-->
    </div>
    
</div>


									<div id="modifyDetail" class="modal fade" tabindex="-1" aria-hidden="true">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                                                    <h4 class="modal-title">Responsive & Scrollable</h4>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="scroller" style="height:300px" data-always-visible="1" data-rail-visible1="1">
                                                        <div class="row">
                                                            <table class="table table-bordered table-hover">
                                    							<tbody>
                                    								<tr>
                                        								<th width="50%"> 小区名 </th>
                                        								<td><input type="text"></td>
                                        								<th> 小区地址 </th>
                                        								<td id=""></td>
                                    								</tr>
                                    								<tr>
                                        								<th> 子小区别名 </th>
                                        								<td id="childAliases2"></td>
                                    								</tr>
                                    								<tr>
                                        								<th> 子小区地址 </th>
                                        								<td id="chlidResidenceAddr2"></td>
                                    								</tr>
                                    							</tbody>
                                							</table>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" data-dismiss="modal" class="btn dark btn-outline">Close</button>
                                                    <button type="button" class="btn green">Save changes</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
</body>
<%@include file="/WEB-INF/pages/include/bottom.jsp" %>
</body>
<script type="text/javascript"
        src="http://api.map.baidu.com/api?v=2.0&ak=5ibDwRtW0ic8CacALvMkxt8tMtBEYyvc"></script>
<script type="text/javascript">
	$(function(){
        initMap();//加載地圖
    })
    
    function initMap() {
        //加载地图
        map = new BMap.Map("map");
        map.setMapStyle({
            styleJson:[
                {
                    "featureType": "poi",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#000000",
                        "hue": "#ffffff",
                        "lightness": 71,
                        "saturation": 93
                    }
                }
            ]
        });
        var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
        var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
        map.addControl(top_left_control);
        map.addControl(top_left_navigation);
        var center = new BMap.Point($("#lon").text(), $("#lat").text());
        map.centerAndZoom(center,18);
//        map.enableScrollWheelZoom(true);
    }
    
    function query(keyword) {
        if (keyword == "") {
            bootbox.alert("關鍵字不能爲空，請重新輸入！！");
        } else {
            $.ajax({
                url: '${ctx}/analysis?address=' + address,
                type: "GET",
                success: function (data) {
                    $("#tb").empty();
                    for (var i = 0; i < data.length; i++) {
                        var html = "<tr><td id='bm" + i + "'></td><td id='qx" + i + "'></td><td id='jd" + i + "'></td><td id='jw" + i + "'></td><td id='ln" + i + "'></td><td id='h" + i + "'></td><td id='s" + i + "'></td></tr>";
                        $("#tb").append(html);
                        $("#qx" + i).html(data[i].qx);
                        $("#jd" + i).html(data[i].jd);
                        $("#jw" + i).html(data[i].jw);
                        $("#ln" + i).html(data[i].ln);
                        $("#h" + i).html(data[i].h);
                        $("#s" + i).html(data[i].s);
                        $("#bm" + i).html(data[i].bm);
                    }
                    }
            })
        }
    }
</script>
</html>
