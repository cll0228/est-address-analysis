<%@ page import="java.util.Map" %>
<%@ page import="com.lezhi.address.admin.pojo.OfBuilding" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/include/top.jsp" %>
<!DOCTYPE html>
<html lang="en">
<c:set var="pageTitle1" value="主页"/>
<c:set var="pageTitle2" value="楼栋"/>
<%@include file="/WEB-INF/pages/include/header.jsp" %>
<head>
    <title>楼栋详情</title>
    <style type="text/css">
        #coordinate {
            position: relative;
            width: 718px;
            margin: 15px auto;
            height: 50px;
        }
        #coordinate tr {
            height: 10px;
         }
    </style>
</head>

<body>
<% OfBuilding result = (OfBuilding)request.getAttribute("buildingInfo");
    String residenceName = result.getResidenceName();
    String buildingNo = result.getBuildingNo();
    Integer buildingId = result.getId();
    Integer houseCount = result.getHouseCount();
    Integer totalFloor = result.getTotalFloor();
    String baiduLon = result.getBaiduLon();
    String baiduLat = result.getBaiduLat();
    Integer residenceId = result.getResidenceId();
    Date updateTime = result.getUpdateTime();
%>

    <div class="row" id="radarPrice" style="display:block">
        <div class="col-md-12">
            <!-- BEGIN PORTLET-->
            <div class="portlet light bordered">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="icon-home font-dark"></i>
                        <span class="caption-subject font-dark sbold uppercase">楼栋信息</span>
                    </div>

                </div>
                <div class="portlet-body">
                    <div class="row">
                        <div class="col-md-4">
                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <tbody>
                                    <tr>
                                        <th width="30%"> 小区名称 </th>
                                        <td id="residenceName"><%=residenceName%></td>
                                    </tr>
                                    <tr>
                                        <th> 楼栋号 </th>
                                        <td id="buildingNo"><%=buildingNo%></td>
                                    </tr>
                                    <tr>
                                        <th> 楼栋Id </th>
                                        <td id="buildingId"><%=buildingId%></td>
                                    </tr>
                                    <tr>
                                        <th> 房屋数量 </th>
                                        <td id="houseCount"><%=houseCount==null?"":houseCount%></td>
                                    </tr>
                                    <tr>
                                        <th> 总楼层 </th>
                                        <td id="totalFloor"><%=(totalFloor==null?"":totalFloor)%></td>
                                    </tr>
                                    <tr>
                                        <th> 更新时间 </th>
                                        <td id="updateTime"><%=(updateTime==null?"":updateTime)%></td>
                                    </tr>
                                    <tr>
                                        <td id="edit" colspan="2"><input id="editBuilding" type="button" value="编辑楼栋"/></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div >
                                <button type="button" class="btn btn-primary" id="building_bk" onclick="javascript :window.history.back();">返回楼栋列表</button>
                            </div>
                            <!-- END PORTLET-->
                        </div>
                        <div class="col-md-8">
                            <div id="map" style="height: 380px;width: 710px;border: 10px;"></div>
                            <div id="coordinate">
                                <table>
                                    <tr>
                                        <th> 楼栋经纬度： </th>
                                        <td id="baiduLon"><%=baiduLon==null?"":baiduLon+","%></td>
                                        <td id="baiduLat"><%=baiduLat==null?"":baiduLat%></td>
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
            </div>
        </div>
    </div>

    <!-- 弹出框 -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">×</span></button>
                    <h4 class="modal-title" id="myModalLabel">编辑楼栋</h4>
                </div>
                <div class="modal-body">

                    <div class="form-group">
                        <label for="txt_buildingNo">楼栋号</label>
                        <input type="text" name="txt_buildingNo" class="form-control" id="txt_buildingNo" placeholder="正整数">
                    </div>
                    <div class="form-group">
                        <label for="txt_houseCount">房屋数量</label>
                        <input type="text" name="txt_houseCount" class="form-control" id="txt_houseCount" placeholder="正整数">
                    </div>
                    <div class="form-group">
                        <label for="txt_totalFloor">总楼层</label>
                        <input type="text" name="txt_tatolFloor" class="form-control" id="txt_totalFloor" placeholder="正整数">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                    <button type="button" id="btn_submit" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>保存</button>
                </div>
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
    <%@include file="/WEB-INF/pages/include/bottom.jsp" %>
</body>

<script type="text/javascript"
        src="http://api.map.baidu.com/api?v=2.0&ak=5ibDwRtW0ic8CacALvMkxt8tMtBEYyvc"></script>
<script type="text/javascript">

    var building_marker;

    $(function(){
        initMap();//加載地圖
        building_marker.enableDragging();
//        var aa=request.getAttribute("buildingInfo");

       /* $("#editCoordinate").click(function () {
            bootbox.alert("请拖动楼栋图标编辑楼栋");
            building_marker.enableDragging();
        });*/




    })
    var map = null ;
    var point = "";
    var map_center_lon = "";
    var map_center_lan = "";
    var config_map = {
        scale: 20   //比例尺，默认20m
    };

    function initMap() {
        //加载地图
        map = new BMap.Map("map");
        var baiduLon = <%=baiduLon%>;
        var baiduLat = <%=baiduLat%>;
//        map.centerAndZoom(point,config_map.scale);
        map.setMapStyle({
            styleJson: [
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
        map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
        map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
        //        map.enableScrollWheelZoom(true);
        if(baiduLon != null && baiduLat != null){
            var point = new BMap.Point(<%=baiduLon%>,<%=baiduLat%>);
            var myIcon_i = new BMap.Icon("${ctx}/static/img/aroundPos.png", new BMap.Size(30, 70));
             building_marker = new BMap.Marker(point, {icon: myIcon_i});  // 创建标注
            building_marker.addEventListener("dragging",function () {
                var position = building_marker.getPosition();
                $("#lon_lat").html(position.lng+","+position.lat);
            });
//            building_marker.enableDragging();
            $("#lon_lat").html(point.lng+","+point.lat);
        }

        map.addOverlay(building_marker);
        // 显示边界
        var residenceId = <%=residenceId%>;
        //单击获取点击的经纬度
        map.addEventListener("click",function(e){
            alert(e.point.lng + "," + e.point.lat);
        });
        initResidenceBoundary(residenceId);

        $("#reset").click(function () {
            map.removeOverlay(building_marker);
            building_marker = new BMap.Marker(point, {icon: myIcon_i});
            building_marker.addEventListener("dragging",function () {
                var position = building_marker.getPosition();
                $("#lon_lat").html(point.lng+","+point.lat);
            });
            $("#lon_lat").html(point.lng+","+point.lat);
            map.addOverlay(building_marker);
            map.centerAndZoom(point,config_map.scale);
        });
    }

    var array ;
    var residence_lat = "";
    var residence_lon = "";
    // 显示小区边界
    function initResidenceBoundary(residenceId) {
        array = new Array;
        $.ajax({
            url: '${ctx}/boundary.do?',
            type: "POST",
            data:{"residenceId":residenceId},
            success: function (data) {
                $.each(data, function (i, item) {
                    residence_lat = item.resiLat;
                    residence_lon = item.resiLon;
                    map_center_lon = item.baiduLon;
                    map_center_lan = item.baiduLat;
                    array[i] = new BMap.Point(item.baiduLon, item.baiduLat);
                });
                //添加边界
                var  polygon = new BMap.Polygon(array, {
                    strokeColor: "red",
                    strokeWeight: 2,
                    strokeOpacity: 0.5,
                    fillColor: "none"
                });
                point = new BMap.Point(map_center_lon, map_center_lan);
                map.centerAndZoom(new BMap.Point(residence_lon, residence_lat),config_map.scale);
                map.addOverlay(polygon);
            }
        })
    }
/*    var buildingId = <%=buildingId%>;

    $("#building_bk").click(function (){
        location.href ='toSearch.do?buildingId='+buildingId;
    });*/

    $("#editBuilding").click(function () {
        $("#txt_buildingNo").val(document.getElementById("buildingNo").innerText.replace("号",""));
        document.getElementById("txt_buildingNo").disabled= "disabled";
        $("#txt_houseCount").val(document.getElementById("houseCount").innerText);
        $("#txt_totalFloor").val(document.getElementById("totalFloor").innerText);
        $("#myModalLabel").text("编辑楼栋");
//        $('#myModal').modal('show');
/*        $("#myModal").modal().css({
            "margin-top": function () {
                return - ($(this).height() / 2);
            }
        });*/
        $('#myModal').modal({
            backdrop: false,
            show: true
        })
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
    toastr.options.positionClass = 'toast-top-center';
    $("#btn_submit").click(function(){
        var oldBuildingNo = document.getElementById("buildingNo").innerText.replace("号","");
        var buildingNo = $("#txt_buildingNo").val();
        var houseCount = $("#txt_houseCount").val();
        var totalFloor = $("#txt_totalFloor").val();
        if(!isPInt(buildingNo)){
            toastr.warning("楼栋号请输入正整数！");
            return;
        }if(!isPInt(houseCount)){
            toastr.warning("房屋数量请输入正整数！");
            return;
        }if(!isPInt(totalFloor)){
            toastr.warning("总楼层请输入正整数！");
            return;
        }if(buildingNo == oldBuildingNo && houseCount == $("#houseCount").html() && totalFloor == $("#totalFloor").html()){
            toastr.warning("楼栋信息未改变，请重新输入！");
            return;
        }
        Ewin.confirm({ message: "确认要更新楼栋数据吗？" }).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                url: '${ctx}/changeBuildingInfo.do?',
                type: "POST",
                data: {"buildingId":buildingId,"buildingNo":buildingNo,"houseCount":houseCount,"totalFloor":totalFloor},
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
    //正整数校验
    function isPInt(str) {
        var g = /^[1-9]*[1-9][0-9]*$/;
        return g.test(str);
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

</script>
</html>
