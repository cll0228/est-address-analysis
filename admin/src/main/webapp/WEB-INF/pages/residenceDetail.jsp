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
                                        <td id="modifyTime"><fmt:formatDate value="${ofResidences.modifyTime}" pattern="已于yyyy年MM月dd日HH点mm分检查更新" /></td>
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
                                                    <h4 class="modal-title">小区信息修改</h4>
                                                </div>
                                                <form action="#" id="form_sample_2" class="form-horizontal">
                                                <div class="modal-body">
                                            <div class="form-group" style="display:none">
                                                <label class="control-label col-md-4">小区id
                                                    <span class="required"> * </span>
                                                </label>
                                                <div class="col-md-6">
                                                    <div class="input-icon right">
                                                        <i class="fa" style="z-index:999"></i>
                                                        <input type="text" class="form-control" name="xqid" value="${ofResidences.id}"/></div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-4">小区名
                                                    <span class="required"> * </span>
                                                </label>
                                                <div class="col-md-6">
                                                    <div class="input-icon right">
                                                        <i class="fa" style="z-index:999"></i>
                                                        <input type="text" class="form-control" id="xqm" name="xqm" value="${ofResidences.residenceName}"/></div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-4">小区地址 
                                                    <span class="required"> * </span>
                                                </label>
                                                <div class="col-md-6">
                                                    <div class="input-icon right">
                                                        <i class="fa" style="z-index:999"></i>
                                                        <input type="text" class="form-control" name="xqdz" value="${ofResidences.residenceAddr}"/> </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-4">所属区县 
                                                    <span class="required"> * </span>
                                                </label>
                                                <div class="col-md-6">
                                                    <div class="input-icon right">
                                                        <i class="fa" style="z-index:999"></i>
                                                        <input type="text" class="form-control" name="ssqx" value="${ofResidences.districtNname}"/> </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-4">所属板块
                                                    <span class="required"> * </span>
                                                </label>
                                                <div class="col-md-6">
                                                    <div class="input-icon right">
                                                        <i class="fa" style="z-index:999"></i>
                                                        <input type="text" class="form-control" name="ssbk" value="${ofResidences.townName}"/> </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-4">总楼栋数
                                                </label>
                                                <div class="col-md-6">
                                                    <div class="input-icon right">
                                                        <i class="fa" style="z-index:999"></i>
                                                        <input type="text" class="form-control" name="zlds" value="${ofResidences.buildingCount}"/> </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-4">总房屋数
                                                </label>
                                                <div class="col-md-6">
                                                    <div class="input-icon right">
                                                        <i class="fa" style="z-index:999"></i>
                                                        <input type="text" class="form-control" name="zfws" value="${ofResidences.houseCount}"/> </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-4">绿化率
                                                </label>
                                                <div class="col-md-6">
                                                    <div class="input-icon right">
                                                        <i class="fa" style="z-index:999"></i>
                                                        <input type="text" class="form-control" name="lhl" value="${ofResidences.gp}"/> </div>
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label class="control-label col-md-4">容积率
                                                </label>
                                                <div class="col-md-6">
                                                    <div class="input-icon right">
                                                        <i class="fa" style="z-index:999"></i>
                                                        <input type="text" class="form-control" name="rjl" value="${ofResidences.vp}"/> </div>
                                                </div>
                                            </div>
                                                    
                                                    
                                                    
                                                        <!-- <div class="row">
                                                            <table class="table table-bordered table-hover" style="width:95%;margin-left:20px">
                                    							<tbody>
                                    								<tr>
                                        								<th width="13%"> 小区名 <span class="required"> * </span></th>
                                        								<td width="30%">
                                        									<div class="input-icon right">
                                                        					<i class="fa"></i>
                                                        					<input type="text" class="form-control" name="xqm" /></div>
                                        								</td>
                                        								<th width="13%"> 小区地址 <span class="required"> * </span></th>
                                        								<td width="30%">
                                        									<div class="input-icon right">
                                                        					<i class="fa"></i>
                                                        					<input type="text" class="form-control" name="xqdz" /></div>
                                        								</td>
                                    								</tr>
                                    								<tr>
                                        								<th> 所属区县 <span class="required"> * </span></th>
                                        								<td>
                                        									<div class="input-icon right">
                                                        					<i class="fa"></i>
                                                        					<input type="text" class="form-control" name="ssqx" /></div>
                                        								</td>
                                        								<th> 所属板块 <span class="required"> * </span></th>
                                        								<td>
                                        									<div class="input-icon right">
                                                        					<i class="fa"></i>
                                                        					<input type="text" class="form-control" name="ssbk" /></div>
                                        								</td>
                                    								</tr>
                                    								<tr>
                                        								<th> 总楼栋数 </th>
                                        								<td>
                                        									<div class="input-icon right">
                                                        					<i class="fa"></i>
                                                        					<input type="text" class="form-control" name="zlds" /></div>
                                        								</td>
                                        								<th> 总房屋数 </th>
                                        								<td>
                                        									<div class="input-icon right">
                                                        					<i class="fa"></i>
                                                        					<input type="text" class="form-control" name="zfws" /></div>
                                        								</td>
                                    								</tr>
                                    								<tr>
                                        								<th> 绿化率 </th>
                                        								<td>
                                        									<div class="input-icon right">
                                                        					<i class="fa"></i>
                                                        					<input type="text" class="form-control" name="lhl" /></div>
                                        								</td>
                                        								<th> 容积率 </th>
                                        								<td>
                                        									<div class="input-icon right">
                                                        					<i class="fa"></i>
                                                        					<input type="text" class="form-control" name="rjl" /></div>
                                        								</td>
                                    								</tr>
                                    							</tbody>
                                							</table>
                                                        </div> -->
                                                    </div>
                                                
                                                <div class="modal-footer">
                                                	<button type="button" class="btn green" onClick="detailModify()">提交</button>
                                                	<button type="button" data-dismiss="modal" class="btn dark btn-outline">取消</button>
                                                </div>
												</form>                                                
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

    var resi_marker ;
    
    function detailModify() {
            $.ajax({
                url: '${ctx}/detailModify.do?',
                type: "Post",
                data: $('#form_sample_2').serialize(),
                success: function (data) {
                	if (data.status == true) {
            			$('#modifyDetail').modal('hide');
            			$('#residenceName').text($('#xqm').val());
            			var modifyHtml = "已于"+data.modifyTime+"检查更新";
            			$('#modifyTime').append(modifyHtml);
                    	setTimeout("bootbox.alert('更新成功!')",100);
        			} else {
        				$('#modifyDetail').modal('hide');
                    	setTimeout("bootbox.alert('更新失败,请稍后再试!')",100);
        			}
                	
                }
            })
    }
    
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
        map.centerAndZoom(center,17);
//        map.enableScrollWheelZoom(true);
        //小区中心点
            var point = new BMap.Point(${ofResidences.lon},${ofResidences.lat});
            var myIcon_i = new BMap.Icon("${ctx}/static/img/aroundPos.png", new BMap.Size(30, 70));
        resi_marker = new BMap.Marker(point, {icon: myIcon_i});  // 创建标注
        resi_marker.addEventListener("dragging",function () {
                var position = building_marker.getPosition();
                $("#lon_lat").html(position.lng+","+position.lat);
            })
        map.addOverlay(resi_marker);
        //小区边界
        var array = ${ofResidences.residenceBoundaries};
        var polArray = new Array;
        if(null != array){
            for(var i = 0;i < array.length;i++){
                polArray[i] = new BMap.Point(array[i].baiduLon, array[i].baiduLat);
            }
            //添加边界
            var  polygon = new BMap.Polygon(polArray, {
                strokeColor: "red",
                strokeWeight: 2,
                strokeOpacity: 0.5,
                fillColor: "none"
            });
            map.addOverlay(polygon);
        }
    }
    
</script>
</html>
