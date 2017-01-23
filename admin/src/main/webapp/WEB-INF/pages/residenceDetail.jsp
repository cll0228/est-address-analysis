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
                        <div class="col-md-4" style="height:10px">
                        </div>
                        <div class="col-md-12">
                            <div id="coordinate">
                                <table>
                                    <tr>
                                        <th> 楼栋经纬度： </th>
                                        <td id="baiduLon">${ofResidences.lon}</td>
                                        <td id="baiduLat">${ofResidences.lat}</td>
                                        <th> &nbsp;&nbsp;实时经纬度： </th>
                                        <td id="lon_lat"></td>
                                    </tr>
                                    <tr>
                                        <th></th>
                                        <td></td>
                                        <td></td>
                                        <th colspan="2"><span style="color:red;"> &nbsp;&nbsp;(可拖动地图楼栋图标显示实时经纬度)</span></th>
                                    </tr>
                                    <tr>
                                        <td><input type="button" id="editCoordinate" value="编辑经纬度"/></td>
                                        <td>&nbsp;&nbsp; <input type="button" value="重置" id="reset"/></td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                            </div>

                        </div>

                        <div class="col-md-5" id="map" style="height: 370px;size: 350px;border: 10px;" >

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
                                            <div class="form-group">
                                                <label class="control-label col-md-4">小区名
                                                    <span class="required"> * </span>
                                                </label>
                                                <div class="col-md-6">
                                                    <div class="input-icon right">
                                                        <i class="fa" style="z-index:999"></i>
                                                        <input type="text" class="form-control" id="xqmid" name="xqm" value="${ofResidences.residenceName}"/></div>
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
                                                    
                                                    
                                                    
                                                    </div>
                                                
                                                <div class="modal-footer">
                                                	<button type="button" class="btn green" onClick="detailModify()">提交</button>
                                                	<button type="button" data-dismiss="modal" class="btn dark btn-outline">取消</button>
                                                </div>
												</form>                                                
                                        </div>
                                        </div>
                                    </div>
                                    
                                    
                                    
                                    
                                    <!-- 编辑经纬度弹出框 -->
    <div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <h4 class="modal-title" id="myModalLabel1">编辑经纬度</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="old_lon">原经度</label>
                        <input type="text" name="old_lon" class="form-control" id="old_lon">
                    </div>
                    <div class="form-group">
                        <label for="old_lat">原维度</label>
                        <input type="text" name="old_lat" class="form-control" id="old_lat">
                    </div>
                    <div class="form-group">
                        <label for="new_lon">实时经度</label>
                        <input type="text" name="new_lon" class="form-control" id="new_lon" placeholder="保留小数点后六位">
                    </div>
                    <div class="form-group">
                        <label for="new_lat">实时维度</label>
                        <input type="text" name="new_lat" class="form-control" id="new_lat" placeholder="保留小数点后六位">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    <button type="button" id="btn_submit1" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
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

	Date.prototype.pattern=function(fmt) {           
    var o = {           
    "M+" : this.getMonth()+1, //月份           
    "d+" : this.getDate(), //日           
    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时           
    "H+" : this.getHours(), //小时           
    "m+" : this.getMinutes(), //分           
    "s+" : this.getSeconds(), //秒           
    "q+" : Math.floor((this.getMonth()+3)/3), //季度           
    "S" : this.getMilliseconds() //毫秒           
    };           
    var week = {           
    "0" : "/u65e5",           
    "1" : "/u4e00",           
    "2" : "/u4e8c",           
    "3" : "/u4e09",           
    "4" : "/u56db",           
    "5" : "/u4e94",           
    "6" : "/u516d"          
    };           
    if(/(y+)/.test(fmt)){           
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));           
    }           
    if(/(E+)/.test(fmt)){           
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);           
    }           
    for(var k in o){           
        if(new RegExp("("+ k +")").test(fmt)){           
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));           
        }           
    }           
    return fmt;           
} 
	//经度校验
    function isLon(str) {
        var g = /^-?(?:(?:180(?:\.0{1,6})?)|(?:(?:(?:1[0-7]\d)|(?:[1-9]?\d))(?:\.\d{1,6})?))$/;
        return g.test(str);
    }
    //维度校验
    function isLat(str) {
        var g = /^-?(?:90(?:\.0{1,6})?|(?:[1-8]?\d(?:\.\d{1,6})?))$/;
        return g.test(str);
    }
	
	
	$("#btn_submit1").click(function(){
        var new_lon = $("#new_lon").val();
        var new_lat = $("#new_lat").val();
        if(!isLon(new_lon)){
            toastr.warning("经度不符合规范！");
            return;
        }if(!isLat(new_lat)){
            toastr.warning("维度不符合规范！");
            return;
        }if(new_lat == $("#old_lat").val() && new_lon == $("#old_lon").val()){
            toastr.warning("经纬度未改变！");
            return;
        }

        Ewin.confirm({ message: "确认要更新楼栋经纬度坐标吗？" }).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                url: '${ctx}/changeBuildingCoordinate.do?',
                type: "POST",
                data: {"buildingId":buildingId,"newLon":new_lon,"newLat":new_lat},
                success: function (data) {
                    if (data.status == "success") {
                        toastr.success('提交数据成功');
                        $("#residenceName").html(data.buildingInfo.residenceName);
                        $("#buildingId").html(data.buildingInfo.id);
                        $("#buildingNo").html(data.buildingInfo.buildingNo);
                        $("#houseCount").html(data.buildingInfo.houseCount==null?"":data.buildingInfo.houseCount+",");
                        $("#totalFloor").html(data.buildingInfo.totalFloor==null?"":data.buildingInfo.totalFloor+",");
                        $("#updateTime").html(data.buildingInfo.updateTime==null?"":data.buildingInfo.updateTime+",");
                        $("#baiduLon").html(data.buildingInfo.baiduLon==null?"":data.buildingInfo.baiduLon+",");
                        $("#baiduLat").html(data.buildingInfo.baiduLat==null?"":data.buildingInfo.baiduLat+",");
                        initMap();//加載地圖
                    }
                },
                error: function () {
                    toastr.error('Error');
                },
                complete: function () {

                }

            });
        });
    });
	
	
	
	$("#editCoordinate").click(function () {
        var newCoordinate = $("#lon_lat").html();
        var strs= new Array(); //定义一数组
        strs=newCoordinate.split(","); //字符分割
        $("#old_lon").val(document.getElementById("baiduLon").innerText.replace(",",""));
        $("#old_lat").val(document.getElementById("baiduLat").innerText);
        document.getElementById("old_lon").disabled= "disabled";
        document.getElementById("old_lat").disabled= "disabled";
        $("#new_lon").val(strs[0]);
        $("#new_lat").val(strs[1]);
        $("#myModalLabel1").text("编辑楼栋坐标");
//        $('#myModal1').modal('show');
        $('#myModal1').modal({
            backdrop: false
        })
    });

    var resi_marker ;
    
    function detailModify() {
    		if (! $("#form_sample_2").valid()) {
        		return;
     		}
            $.ajax({
                url: '${ctx}/detailModify.do?',
                type: "Post",
                data: $('#form_sample_2').serialize(),
                success: function (data) {
                	if (data.status == true) {
            			$('#modifyDetail').modal('hide');
            			$('#residenceName').text($('#xqmid').val());
            			var date = new Date(data.modifyTime);
            			var trs = date.pattern("yyyy年MM月dd日HH点mm分");
            			var modifyHtml = "已于"+trs+"检查更新";
            			$('#modifyTime').html(modifyHtml);
                    	setTimeout("bootbox.alert('更新成功!')",100);
        			} else {
        				$('#modifyDetail').modal('hide');
                    	setTimeout("bootbox.alert('更新失败,请稍后再试!')",100);
        			}
                	
                }
            })
    }
    
    var map = null ;
    var point = "";
    var map_center_lon = "";
    var map_center_lan = "";
    var config_map = {
        scale: 17   //比例尺，默认20m
    };
    
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
                var position = resi_marker.getPosition();
                $("#lon_lat").html(position.lng+","+position.lat);
            })
        resi_marker.enableDragging();
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
        
        $("#reset").click(function () {
        	map.removeOverlay(resi_marker);
            resi_marker = new BMap.Marker(point, {icon: myIcon_i});
            resi_marker.addEventListener("dragging",function () {
                var position = resi_marker.getPosition();
                $("#lon_lat").html(position.lng+","+position.lat);
            });
            $("#lon_lat").html(point.lng+","+point.lat);
            map.addOverlay(resi_marker);
            map.centerAndZoom(point,config_map.scale);
            resi_marker.enableDragging();
        });
    }
    
</script>
</html>
