<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/include/top.jsp" %>
<%-- 需要在head标签中编写代码，请写在此处--%>
</head>
<c:set var="pageTitle1" value="主页"/>
<c:set var="pageTitle2" value="地址标准化"/>
<%@include file="/WEB-INF/pages/include/header.jsp" %>
<div style="height: 800px;">
    <div class="form-group">
        <!-- <div class="m-heading-1 border-green m-bordered">
                            <h3>地址标准化</h3>
                        </div> -->
        <div class="input-group input-group-lg">
            <input id="in" type="text" class="form-control" placeholder="请输入要标准化的地址" value="长峰馨园2号">
            <span class="input-group-btn">
            <button class="btn green" type="button" onclick="analysis()">开始标准化地址</button>
        </span>
        </div>
        <!-- /input-group -->
    </div>
    <!-- /input-group -->
</div>

    <div class="clearfix">
        <div class="panel panel-success">
            <!-- Default panel contents -->
            <div class="panel-heading">
                <h3 class="panel-title">地址标准化结果</h3>
            </div>
            <div class="panel-body">
                <table height="30px">
                    <tr>
                        <td id="ts"></td>
                    </tr>
                </table>
            </div>
            <!-- Table -->
            <!-- style="overflow-x: auto; overflow-y: auto; height: 660px; width:100%;" -->
            <div>
                <table id="sample_1" class="table table-striped table-bordered table-hover order-column" height="80px">
                    <thead>
                    <tr>
                        <th>地址标准化编码</th>
                        <th>区县</th>
                        <th>街道</th>
                        <th>居委</th>
                        <th>路弄</th>
                        <th>号</th>
                        <th>室</th>
                    </tr>
                    </thead>
                    <tbody id="tb">
                    <td id="bm"></td>
                    <td id="qx"></td>
                    <td id="jd"></td>
                    <td id="jw"></td>
                    <td id="ln"></td>
                    <td id="h"></td>
                    <td id="s"></td>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="row" id="homeDetail" style="display:none">
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
                        <div class="col-md-6" id="map" style="height: 300px;size: 350px">
                            这里显示地图
                        </div>
                        <div class="col-md-3">
                            <h4>打分</h4>
                            这里显示打分
                        </div>
                        <div class="col-md-3">
                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <tbody>
                                    <tr>
                                        <th width="30%"> 小区名称</th>
                                        <td id="residenceName"></td>
                                    </tr>
                                    <tr>
                                        <th> 小区别名</th>
                                        <td id="aliases"></td>
                                    </tr>
                                    <tr>
                                        <th> 小区地址</th>
                                        <td id="residenceAddr"></td>
                                    </tr>
                                    <tr>
                                        <th> 小区物业类型</th>
                                        <td id="propertyType"></td>
                                    </tr>
                                    <tr>
                                        <th> 小区四至</th>
                                        <td id="fourTo"></td>
                                    </tr>
                                    <tr>
                                        <th> 竣工日期</th>
                                        <td id="accomplishDate"></td>
                                    </tr>
                                    <tr>
                                        <th> 容积率</th>
                                        <td id="vp"></td>
                                    </tr>
                                    <tr>
                                        <th> 绿化率</th>
                                        <td id="gp"></td>
                                    </tr>
                                    <tr>
                                        <th> 总建筑面积</th>
                                        <td id="totalArea"></td>
                                    </tr>
                                    <tr>
                                        <th> 总楼栋数</th>
                                        <td id="buildingCount"></td>
                                    </tr>
                                    <tr>
                                        <th> 总房屋数</th>
                                        <td id="houseCount"></td>
                                    </tr>
                                    <tr>
                                        <th> 小区房型</th>
                                        <td id="houseType"></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                            <!-- END PORTLET-->
                        </div>
                    </div>
                </div>
                <div class="row">
                                    	<div class="col-md-12" id="main" style="height:400px;">
                        				</div>
                                 	</div>
            </div>
        </div>
        <!-- END PORTLET-->
    </div>
</div>


</div>


<%@include file="/WEB-INF/pages/include/bottom.jsp" %>
</body>
<script type="text/javascript"
        src="http://api.map.baidu.com/api?v=2.0&ak=5ibDwRtW0ic8CacALvMkxt8tMtBEYyvc"></script>
<script type="text/javascript">


    var map;
    var roadLan;
    function analysis() {
        initMap();//加載地圖
        var address = $("#in").val();
        if (address == "") {
            bootbox.alert("地址不能为空，请重新输入");
        } else {
            $.ajax({
                url: '${ctx}/analysis?address=' + address,
                type: "GET",
                //data: param,
                error: function (jqXHR, textStatus, errorThrown) {
                    var ex = {};
                    try {
                        ex = JSON.parse(jqXHR.responseText);
                    } catch (e) {
                        //ex.exception = e;
                    }
                },
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


                    if (data[0].f == "1") {
                        $("#ts").html("地址标准化成功").css("color", "green");
                        $("#residenceName").html(data[0].detail.residenceName);
                        $("#aliases").html(data[0].detail.aliases);
                        $("#residenceAddr").html(data[0].detail.residenceAddr);
                        $("#propertyType").html(data[0].detail.propertyType);
                        $("#fourTo").html(data[0].detail.fourTo);
                        $("#accomplishDate").html(data[0].detail.accomplishDate);
                        $("#vp").html(data[0].detail.vp);
                        $("#gp").html(data[0].detail.gp);
                        $("#totalArea").html(data[0].detail.totalArea);
                        $("#buildingCount").html(data[0].detail.buildingCount);
                        $("#houseCount").html(data[0].detail.houseCount);
                        $("#houseType").html(data[0].detail.houseType);
                        $("#homeDetail").css('display', 'block');
                        //加載小區邊界
                        initResidenceBoundary($("#ln0").html());//加載小區邊界
                        if($("#h0").html()!=''){
                            initBuilding($("#ln0").html(),$("#h0").html());
                        }//加載小區邊界
						// 初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '小区价格走势'
            },
            tooltip: {},
            xAxis: {
                data: data[0].b
            },
            yAxis: {},
            series: [{
                name: '单价',
                type: 'bar',
                data: data[0].a
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);

                        //加載小區邊界
                        initResidenceBoundary(residenceId);//加載小區邊界
                    } else if (data[0].f == "2") {
                        $("#ts").html("区县不对应").css("color", "red");
                        $("#homeDetail").css('display', 'none');
                    } else if (data[0].f == "3") {
                        $("#ts").html("地址不存在").css("color", "red");
                        $("#homeDetail").css('display', 'none');
                    } else if (data[0].f == "4") {
                        $("#ts").html("路弄未找到").css("color", "red");
                        $("#homeDetail").css('display', 'none');
                    } else if (data[0].f == "5") {
                        $("#ts").html("道路不存在").css("color", "red");
                        $("#homeDetail").css('display', 'none');
                    } else if (data[0].f == "6") {
                        $("#ts").html("房间号未找到").css("color", "red");
                        $("#homeDetail").css('display', 'none');
                    } else if (data[0].f == "7") {
                        $("#ts").html("楼栋未找到").css("color", "red");
                        $("#homeDetail").css('display', 'none');
                    }
                }
            });
        }

    }
    var config_map = {
        scale: 19    //比例尺，默认5km
    };
    function initMap() {
        //加载地图
         map_center_lon = 121.495008;
         map_center_lan = 31.215454;
         point = new BMap.Point(map_center_lon, map_center_lan);
        map = new BMap.Map("map");
        map.setMapStyle({style: 'light'});
        map.centerAndZoom(point, config_map.scale);
        var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
        var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
        map.addControl(top_left_control);
        map.addControl(top_left_navigation);
    }

    var array = new Array;
    function initResidenceBoundary(roadLan) {
        $.ajax({
            url: '${ctx}/boundary?roadLan=' + roadLan,
            type: "GET",
            success: function (data) {
                $.each(data, function (i, item) {
                    array[i] = new BMap.Point(item.baiduLon, item.baiduLat);
                });
                //添加边界
                var  polygon = new BMap.Polygon(array, {
                    strokeColor: "red",
                    strokeWeight: 2,
                    strokeOpacity: 0.5,
                    fillColor: "none"
                });
                map.addOverlay(polygon);
            }
        })
    }

    //加载楼栋信息
    function initBuilding(roadLan,buildingNo) {
        $.ajax({
            url: '${ctx}/building',
            type: "POST",
            data:{"roadLan":roadLan,"buildingNo":buildingNo},
            success: function (data) {
                //中心点
//                map_center_lon = data.lon;
//                map_center_lan = data.lat;
                map_center_lon="121.495008";
                map_center_lan = "31.215454";
                point = new BMap.Point(map_center_lon, map_center_lan);
                map.centerAndZoom(point, config_map.scale);
                //文本标注
                function ComplexCustomOverlay(point, text, mouseoverText){
                    this._point = point;
                    this._text = text;
                    this._overText = mouseoverText;
                }
                ComplexCustomOverlay.prototype = new BMap.Overlay();
                ComplexCustomOverlay.prototype.initialize = function(map){
                    this._map = map;
                    var div = this._div = document.createElement("div");
                    div.style.position = "absolute";
                    div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
                    div.style.backgroundColor = "#EE5D5B";
                    div.style.border = "1px solid #BC3B3A";
                    div.style.color = "white";
                    div.style.height = "24px";
                    div.style.padding = "2px";
                    div.style.lineHeight = "18px";
                    div.style.whiteSpace = "nowrap";
                    div.style.MozUserSelect = "none";
                    div.style.fontSize = "12px"
                    var span = this._span = document.createElement("span");
                    div.appendChild(span);
                    span.appendChild(document.createTextNode(this._text));
                    var that = this;

                    var arrow = this._arrow = document.createElement("div");
                    arrow.style.background = "url(http://map.baidu.com/fwmap/upload/r/map/fwmap/static/house/images/label.png) no-repeat";
                    arrow.style.position = "absolute";
                    arrow.style.width = "11px";
                    arrow.style.height = "10px";
                    arrow.style.top = "22px";
                    arrow.style.left = "10px";
                    arrow.style.overflow = "hidden";
                    div.appendChild(arrow);
                    map.getPanes().labelPane.appendChild(div);

                    return div;
                }
                ComplexCustomOverlay.prototype.draw = function(){
                    var map = this._map;
                    var pixel = map.pointToOverlayPixel(this._point);
                    this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
                    this._div.style.top  = pixel.y - 30 + "px";
                }
                var txt =data.buildingNo +",总楼层："+data.totalFloor+"，总房屋数："+data.houseCount;

                var myCompOverlay = new ComplexCustomOverlay(point, txt);

                map.addOverlay(myCompOverlay);
            }
        })
    }
    var map_center_lon = 121.505583;
    var map_center_lan = 31.218542;
    var point;
</script>
</html>