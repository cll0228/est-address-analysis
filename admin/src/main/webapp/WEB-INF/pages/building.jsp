<%@ page import="java.util.Map" %>
<%@ page import="com.lezhi.address.admin.pojo.OfBuilding" %>
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
            margin: 12px auto;
            height: 50px;
        }
        #coordinate tr {
            height: 30px;
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
                                        <td id="houseCount"><%=houseCount%></td>
                                    </tr>
                                    <tr>
                                        <th> 总楼层 </th>
                                        <td id="totalFloor"><%=(totalFloor==null?"":totalFloor)%></td>
                                    </tr>
                                    <tr>
                                        <td id="edit" colspan="2"><input id="editBuilding" type="button" value="编辑楼栋"/></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <div >
                                <button type="button" class="btn btn-primary" onclick="javascript :window.history.back();">返回楼栋列表</button>
                            </div>
                            <!-- END PORTLET-->
                        </div>
                        <div class="col-md-8">
                            <div id="map" style="height: 350px;width: 710px;border: 10px;"></div>
                            <div id="coordinate">
                                <table>
                                    <tr>
                                        <th> 楼栋经纬度 </th>
                                        <td id="baiduLon"><%=baiduLon==null?"":baiduLon+","%></td>
                                        <td id="baiduLat"><%=baiduLat==null?"":baiduLat%></td>
                                    </tr>
                                    <tr>
                                        <td><input type="button" value="编辑经纬度"/></td>
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
    <%@include file="/WEB-INF/pages/include/bottom.jsp" %>
</body>

<script type="text/javascript"
        src="http://api.map.baidu.com/api?v=2.0&ak=5ibDwRtW0ic8CacALvMkxt8tMtBEYyvc"></script>
<script type="text/javascript">
    $(function(){
        initMap();//加載地圖
//        var aa=request.getAttribute("buildingInfo");

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
            var marker2_i = new BMap.Marker(point, {icon: myIcon_i});  // 创建标注
        }
        map.addOverlay(marker2_i);
        // 显示边界
        var residenceId = <%=residenceId%>;
        //单击获取点击的经纬度
        map.addEventListener("click",function(e){
            alert(e.point.lng + "," + e.point.lat);
        });
        initResidenceBoundary(residenceId);
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

    $("#editBuilding").click(function () {
        $("#txt_buildingNo").val(document.getElementById("buildingNo").innerText.replace("号",""));
        $("#txt_houseCount").val(document.getElementById("houseCount").innerText);
        $("#txt_totalFloor").val(document.getElementById("totalFloor").innerText);
        $("#myModalLabel").text("编辑楼栋");
        $('#myModal').modal();
    });
    toastr.options.positionClass = 'toast-bottom-center';
    $("#btn_submit").click(function(){
        var buildingId = <%=buildingId%>;
        var buildingNo = $("#txt_buildingNo").val();
        var houseCount = $("#txt_houseCount").val();
        var totalFloor = $("#txt_totalFloor").val();
        if(!isPInt(buildingNo)){
            toastr.warning("楼栋号请输入正整数！");
        }if(!isPInt(houseCount)){
            toastr.warning("房屋数量请输入正整数！");
        }if(!isPInt(totalFloor)){
            toastr.warning("总楼层请输入正整数！");
        }
        Ewin.confirm({ message: "确认要删除选择的数据吗？" }).on(function (e) {
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
                        $("#houseCount").html(data.buildingInfo.houseCount);
                        $("#totalFloor").html(data.buildingInfo.totalFloor);
                        $("#baiduLon").html(data.buildingInfo.baiduLon+",");
                        $("#baiduLat").html(data.buildingInfo.baiduLat);
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


    (function ($) {

        window.Ewin = function () {
            var html = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
                    '<div class="modal-dialog modal-sm">' +
                    '<div class="modal-content">' +
                    '<div class="modal-header">' +
                    '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>' +
                    '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
                    '</div>' +
                    '<div class="modal-body">' +
                    '<p>[Message]</p>' +
                    '</div>' +
                    '<div class="modal-footer">' +
                    '<button type="button" class="btn btn-default cancel" data-dismiss="modal">[BtnCancel]</button>' +
                    '<button type="button" class="btn btn-primary ok" data-dismiss="modal">[BtnOk]</button>' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>';


            var dialogdHtml = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
                    '<div class="modal-dialog">' +
                    '<div class="modal-content">' +
                    '<div class="modal-header">' +
                    '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>' +
                    '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
                    '</div>' +
                    '<div class="modal-body">' +
                    '</div>' +
                    '</div>' +
                    '</div>' +
                    '</div>';
            var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
            var generateId = function () {
                var date = new Date();
                return 'mdl' + date.valueOf();
            }
            var init = function (options) {
                options = $.extend({}, {
                    title: "操作提示",
                    message: "提示内容",
                    btnok: "确定",
                    btncl: "取消",
                    width: 200,
                    auto: false
                }, options || {});
                var modalId = generateId();
                var content = html.replace(reg, function (node, key) {
                    return {
                        Id: modalId,
                        Title: options.title,
                        Message: options.message,
                        BtnOk: options.btnok,
                        BtnCancel: options.btncl
                    }[key];
                });
                $('body').append(content);
                $('#' + modalId).modal({
                    width: options.width,
                    backdrop: 'static'
                });
                $('#' + modalId).on('hide.bs.modal', function (e) {
                    $('body').find('#' + modalId).remove();
                });
                return modalId;
            }

            return {
                alert: function (options) {
                    if (typeof options == 'string') {
                        options = {
                            message: options
                        };
                    }
                    var id = init(options);
                    var modal = $('#' + id);
                    modal.find('.ok').removeClass('btn-success').addClass('btn-primary');
                    modal.find('.cancel').hide();

                    return {
                        id: id,
                        on: function (callback) {
                            if (callback && callback instanceof Function) {
                                modal.find('.ok').click(function () { callback(true); });
                            }
                        },
                        hide: function (callback) {
                            if (callback && callback instanceof Function) {
                                modal.on('hide.bs.modal', function (e) {
                                    callback(e);
                                });
                            }
                        }
                    };
                },
                confirm: function (options) {
                    var id = init(options);
                    var modal = $('#' + id);
                    modal.find('.ok').removeClass('btn-primary').addClass('btn-success');
                    modal.find('.cancel').show();
                    return {
                        id: id,
                        on: function (callback) {
                            if (callback && callback instanceof Function) {
                                modal.find('.ok').click(function () { callback(true); });
                                modal.find('.cancel').click(function () { callback(false); });
                            }
                        },
                        hide: function (callback) {
                            if (callback && callback instanceof Function) {
                                modal.on('hide.bs.modal', function (e) {
                                    callback(e);
                                });
                            }
                        }
                    };
                },
                dialog: function (options) {
                    options = $.extend({}, {
                        title: 'title',
                        url: '',
                        width: 800,
                        height: 550,
                        onReady: function () { },
                        onShown: function (e) { }
                    }, options || {});
                    var modalId = generateId();

                    var content = dialogdHtml.replace(reg, function (node, key) {
                        return {
                            Id: modalId,
                            Title: options.title
                        }[key];
                    });
                    $('body').append(content);
                    var target = $('#' + modalId);
                    target.find('.modal-body').load(options.url);
                    if (options.onReady())
                        options.onReady.call(target);
                    target.modal();
                    target.on('shown.bs.modal', function (e) {
                        if (options.onReady(e))
                            options.onReady.call(target, e);
                    });
                    target.on('hide.bs.modal', function (e) {
                        $('body').find(target).remove();
                    });
                }
            }
        }();
    })(jQuery);
</script>
</html>