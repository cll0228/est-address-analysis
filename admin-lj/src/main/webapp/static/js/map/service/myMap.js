var map = null;
var boundary = null;
var flag = 1;
var com_param = false;
$(function () {
    map = new BMap.Map("mapWrap", {minZoom: 11, maxZoom: 19, enableMapClick: false});
    var center = new BMap.Point("121.464427", "31.228894");
    map.centerAndZoom(center, 12);
    map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT, offset: new BMap.Size(10, 10)}));
    map.enableScrollWheelZoom();
    map.addEventListener("zoomend", function () {
        var center = map.getCenter();
        var scale = map.getZoom();
        if (scale == 14) {
            map.clearOverlays();
            map.disableScrollWheelZoom();
            if(flag == 1)showTown(null,null,null,null,"noChangeZoom");
            return;
        }
        if(scale == 12){
            map.clearOverlays();
            if(flag == 1)initDistrictInfo();

        }
        if ( scale == 16 ) {
            map.clearOverlays();
            if(flag == 1)showneighborhood(null,null,null,null,"noChangeZoom");
            map.disableScrollWheelZoom();
            return;
        }
        if(scale == 18){
            map.clearOverlays();
            if(flag == 1)showResidenceInfo();
            return;
        }
    });
    //加載行政區域
    initDistrictInfo();

    //监控地图级别
    map.addEventListener("zoomend",function () {

    });
});


function initDistrictInfo(districtId) {
    map.clearOverlays();
    //请求区域中心点坐标
    $.ajax({
        type: "get",
        url: "./districtcentertude.do",
        data:{"districtId":districtId},
        dataType: "json",
        success: function (data) {
            $.each(data, function (i, item) {
                var point_dis_i = new BMap.Point(item.centerLongitude, item.centerLatitude);
                //添加园圈
                var html = '<div id="' + item.districtId + '" onclick="getTown(' + item.districtId + ','+item.centerLongitude+','+item.centerLatitude+');" onmouseout="clearOutLine();" onmouseover="getJABoundary('+item.districtId+');" class="district-overlay">' + item.district + '<br/>' + item.hdUserNum + '户</p></div>';
                var anchor = new BMap.Size(-30, -25);
                var richMarker_i = new BMapLib.RichMarker(html, point_dis_i, {"anchor": anchor});
                map.addOverlay(richMarker_i);
                var overlayTop = $(richMarker_i._container);
                overlayTop.css("background", "transparent").addClass('map-overlay');
            })
        },
        beforeSend:function () {
            com_param = false;
        },
        complete:function () {
            com_param = true;
        }
    });
}

function getTown(districtId,lon,lat,townId,ifchangZoom){
    flag = 0;
    showTown(districtId,lon,lat,townId,ifchangZoom);
}

function showTown(districtId,lon,lat,townId,ifchangZoom){
    map.clearOverlays();
    $.ajax({
        type: "get",
        url: "./showtown.do",
        data:{"districtId":districtId,"townId":townId},
        success: function (data) {
            if(null != lon && lat !=null ){
                var ceterPoint = new BMap.Point(lon, lat);
                map.centerAndZoom(ceterPoint, 14);
            }
            $.each(data, function (i, item) {
                cen_lon = item.cenLon;
                cen_lat = item.cenLat;
                if(ifchangZoom == "noChangeZoom"){
                    map.setZoom(14);
                }else{
                    if(lon == null){
                        map.centerAndZoom(new BMap.Point(cen_lon, cen_lat),14);
                    }
                }
                var point_dis_i = new BMap.Point(item.longitude, item.latitude);
                //添加园圈
                var html = '<div id="' + item.townId + '" onclick="getJuWei('+item.townId+','+item.longitude+','+item.latitude+');" class="plate-overlay"><p>' + item.townName + '</p><p class="map-overlay__total">' + item.hdUserNum + '户</p></div>';
                var anchor = new BMap.Size(-30, -25);
                var richMarker_i = new BMapLib.RichMarker(html, point_dis_i, {"anchor": anchor});
                map.addOverlay(richMarker_i);
                var overlayTop = $(richMarker_i._container);
                overlayTop.css("background", "transparent").addClass('map-overlay');
            })

        },
        beforeSend:function () {
            com_param = false;
        },
        complete:function () {
            com_param = true;
        }
    });
}

function getJuWei(townId,lon,lat,neighborhoodId,ifchangZoom){
    flag = 0;
    showneighborhood(townId,lon,lat,neighborhoodId,ifchangZoom);
}

var cen_lon;
var cen_lat;

function showneighborhood(townId,lon,lat,neighborhoodId,ifchangZoom) {

    $.ajax({
        type: "get",
        url: "./neighborhood.do",
        data:{"townId":townId,"neighborhoodId":neighborhoodId},
        success: function (data) {
            map.clearOverlays();
            if(null != lon && lat !=null ){
                var ceterPoint = new BMap.Point(lon, lat);
                map.centerAndZoom(ceterPoint, 16);
            }
            $.each(data, function (i, item) {
                cen_lon = item.cenLon;
                cen_lat = item.cenLat;
                if(ifchangZoom == "noChangeZoom"){
                    map.setZoom(16);
                }else{
                    if(lon == null){
                        map.centerAndZoom(new BMap.Point(cen_lon, cen_lat),16);
                    }
                }
                var point_dis_i = new BMap.Point(item.lng, item.lat);
                //添加园圈
                var html = '<div id="' + item.neighborhoodId + '" onclick="getResidence('+item.neighborhoodId+','+item.lng+','+item.lat+');" class="plate-overlay"><p>' + item.neighborhoodName + '</p><p class="map-overlay__total">' + item.hdUserNum + '户</p></div>';
                var anchor = new BMap.Size(-30, -25);
                var richMarker_i = new BMapLib.RichMarker(html, point_dis_i, {"anchor": anchor});
                map.addOverlay(richMarker_i);
                var overlayTop = $(richMarker_i._container);
                overlayTop.css("background", "transparent").addClass('map-overlay');
            })

        },
        beforeSend:function () {
            com_param = false;
        },
        complete:function () {
            com_param = true;
        }
    });
}

function getResidence(neighborhoodId,lon,lat,residenceId,ifchangeZoom) {
    flag = 0;
    showResidenceInfo(neighborhoodId,lon,lat,residenceId,ifchangeZoom);
}

function showResidenceInfo(neighborhoodId,lon,lat,residenceId,ifchangeZoom) {
    map.clearOverlays();
    $.ajax({
        type: "get",
        url: "./getResidence.do",
        data:{"neighborhoodId":neighborhoodId,"residenceId":residenceId},
        success: function (data) {
            $.each(data, function (i, item) {
                var bus_lat = item.lat;
                var bus_lon = item.lon;
                var bus_point_i = new BMap.Point(bus_lon, bus_lat);
                map.centerAndZoom(bus_point_i, 18);
                var myIcon_i = new BMap.Icon("./static/img/point.png", new BMap.Size(30, 70));
                var marker2_i = new BMap.Marker(bus_point_i, {icon: myIcon_i});  // 创建标注

                //点击事件，显示文本内容
                var opts_i = {
                    position: bus_point_i,    // 指定文本标注所在的地理位置
                    title: item.residenceName,
                    offset: new BMap.Size(7, -25, 30, 30)    //设置文本偏移量 右  下
                }
                var infoWindow_i = new BMap.InfoWindow("地址：" + item.residenceAddr+'</br>'+"高清机顶盒用户数："+item.hdUserNum, opts_i);  // 创建信息窗口对象
                //标签
                var label_i = new BMap.Label(i+1,{offset:new BMap.Size(7,3)});
                label_i.setStyle({
                    color: "white",
                    fontSize: "10px",
                    backgroundColor: "0.05",
                    border: "0",
                    fontFamily: "微软雅黑"
                });
                marker2_i.setLabel(label_i);
                //圖標點擊事件
                marker2_i.addEventListener("mouseover", function () {
                    map.openInfoWindow(infoWindow_i, bus_point_i); //开启信息窗口
                });
                map.addOverlay(marker2_i);
            })
        },
        beforeSend:function () {
            com_param = false;
        },
        complete:function () {
            com_param = true;
        }
    });

}

function clearOutLine() {
    map.getOverlays().forEach(function(overlay){
        if(overlay.isOutLine){
            map.removeOverlay(overlay);
        }
    });
}

function getJABoundary(district) {
    $.ajax({
        type: "get",
        url: "./districtboundaydata/" + district + ".do",
        success: function (data) {
            addOutLineInfo(data);
        }
    });
}

function addOutLineInfo(dataList) {
    var points = (dataList || []).map(function (item) {
        return new BMap.Point(item.centerLongitude, item.centerLatitude);
    });
    var polygon = new BMap.Polygon(points, {
        strokeColor: "#dd2424",
        fillColor: "",
        strokeWeight: 2,
        strokeOpacity: 0.75,
    });  //创建多边形
    boundary = polygon;
    polygon.isOutLine = true;
    map.addOverlay(polygon); //增加多边形
    return polygon;
}

function addMapZoomSize() {
    if(com_param){
        flag = 1;
        var a_zoom = map.getZoom();
        if (a_zoom == 12) {
            map.setZoom(14);
            map.clearOverlays();
            return;
        }
        if (a_zoom == 14) {
            map.setZoom(16);
            map.clearOverlays();
            return;
        }
        if(a_zoom == 16){
            map.setZoom(18);
            map.clearOverlays();
            return;
        }
        if(a_zoom == 18){
            return;
        }
    }
}

function reduceMapZoomSize() {
    if(com_param){
        flag = 1;
        var a_zoom = map.getZoom();
        if(a_zoom == 16){
            map.setZoom(14);
            return;
        }
        if (a_zoom == 14) {
            map.setZoom(12);
            return;

        }
        if (a_zoom == 18) {
            map.setZoom(16);
            return;

        }
        if(a_zoom == 12){
            return;
        }
    }
}