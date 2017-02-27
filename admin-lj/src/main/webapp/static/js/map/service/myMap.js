var map = null;
var boundary = null;
var flag = 1;
var com_param = false;
$(function () {
    map = new BMap.Map("mapWrap", {minZoom: 11, maxZoom: 19, enableMapClick: false});
    var center = new BMap.Point("121.464427", "31.228894");
    map.centerAndZoom(center, 12);
    map.addControl(new BMap.ScaleControl({anchor: BMAP_ANCHOR_BOTTOM_RIGHT, offset: new BMap.Size(10, 10)}));
    map.disableScrollWheelZoom();
    //加載行政區域
    initDistrictInfo();
});


function initDistrictInfo(districtId) {
    map.clearOverlays();
    //请求区域中心点坐标
    $.ajax({
        type: "get",
        url: "./districtcentertude.do",
        data: {"districtId": districtId},
        dataType: "json",
        success: function (data) {
            var center = new BMap.Point("121.464427", "31.228894");
            map.centerAndZoom(center, 12);
            $.each(data, function (i, item) {
                var point_dis_i = new BMap.Point(item.centerLongitude, item.centerLatitude);
                //添加园圈
                var html = '<div id="' + item.districtId + '" onclick="getTown(' + item.districtId + ',' + item.centerLongitude + ',' + item.centerLatitude + ');" onmouseout="clearOutLine();" onmouseover="getJABoundary(' + item.districtId + ');" class="district-overlay">' + item.district + '<br/>' + item.hdUserNum + '户</p></div>';
                var anchor = new BMap.Size(-30, -25);
                var richMarker_i = new BMapLib.RichMarker(html, point_dis_i, {"anchor": anchor});
                map.addOverlay(richMarker_i);
                var overlayTop = $(richMarker_i._container);
                overlayTop.css("background", "transparent").addClass('map-overlay');
            })
        },
        beforeSend: function () {
            com_param = false;
        },
        complete: function () {
            com_param = true;
        }
    });
}

function getTown(districtId, lon, lat, townId, ifchangZoom) {
    flag = 0;
    showTown(districtId, lon, lat, townId, ifchangZoom);
}

function showTown(districtId, lon, lat, townId, ifchangZoom) {
    map.clearOverlays();
    $.ajax({
        type: "get",
        url: "./showtown.do",
        data: {"districtId": districtId, "townId": townId},
        success: function (data) {
            if (null != lon && lat != null) {
                var ceterPoint = new BMap.Point(lon, lat);
                map.centerAndZoom(ceterPoint, 14);
            }
            $.each(data, function (i, item) {
                cen_lon = item.cenLon;
                cen_lat = item.cenLat;
                if (ifchangZoom == "noChangeZoom") {
                    map.setZoom(14);
                } else {
                    if (lon == null) {
                        map.centerAndZoom(new BMap.Point(cen_lon, cen_lat), 14);
                    }
                }
                var point_dis_i = new BMap.Point(item.longitude, item.latitude);
                //添加园圈
                var html = '<div id="' + item.townId + '" onclick="showneighborhood(' + item.townId + ',' + item.longitude + ',' + item.latitude + ');" class="plate-overlay"><p>' + item.townName + '</p><p class="map-overlay__total">' + item.hdUserNum + '户</p></div>';
                var anchor = new BMap.Size(-30, -25);
                var richMarker_i = new BMapLib.RichMarker(html, point_dis_i, {"anchor": anchor});
                map.addOverlay(richMarker_i);
                var overlayTop = $(richMarker_i._container);
                overlayTop.css("background", "transparent").addClass('map-overlay');
            })

        },
        beforeSend: function () {
            com_param = false;
        },
        complete: function () {
            com_param = true;
        }
    });
}

function getJuWei(townId, lon, lat, neighborhoodId) {
    showneighborhood(townId, lon, lat, neighborhoodId, "noChangeZoom");
}

var cen_lon;
var cen_lat;

function showneighborhood(townId, lon, lat, neighborhoodId, ifchangZoom) {

    $.ajax({
        type: "get",
        url: "./neighborhood.do",
        data: {"townId": townId, "neighborhoodId": neighborhoodId},
        success: function (data) {
            map.clearOverlays();
            if (null != lon && lat != null) {
                var ceterPoint = new BMap.Point(lon, lat);
                map.centerAndZoom(ceterPoint, 16);
            }
            $.each(data, function (i, item) {
                cen_lon = item.cenLon;
                cen_lat = item.cenLat;
                if (ifchangZoom == "noChangeZoom") {
                    map.setZoom(16);
                } else {
                    if (lon == null) {
                        map.centerAndZoom(new BMap.Point(cen_lon, cen_lat), 16);
                    }
                }
                var point_dis_i = new BMap.Point(item.lng, item.lat);
                //添加园圈
                var html = '<div id="' + item.neighborhoodId + '" onclick="getResidence(' + item.neighborhoodId + ',' + item.lng + ',' + item.lat + ');" class="plate-overlay"><p>' + item.neighborhoodName + '</p><p class="map-overlay__total">' + item.hdUserNum + '户</p></div>';
                var anchor = new BMap.Size(-30, -25);
                var richMarker_i = new BMapLib.RichMarker(html, point_dis_i, {"anchor": anchor});
                map.addOverlay(richMarker_i);
                var overlayTop = $(richMarker_i._container);
                overlayTop.css("background", "transparent").addClass('map-overlay');
            })

        },
        beforeSend: function () {
            com_param = false;
        },
        complete: function () {
            com_param = true;
        }
    });
}

function getResidence(neighborhoodId, lon, lat, residenceId, ifchangeZoom) {
    flag = 0;
    showResidenceInfo(neighborhoodId, lon, lat, residenceId, ifchangeZoom);
}

function showResidenceInfo(neighborhoodId, lon, lat, residenceId, ifchangeZoom) {
    map.clearOverlays();
    $.ajax({
        type: "get",
        url: "./getResidence.do",
        data: {"neighborhoodId": neighborhoodId, "residenceId": residenceId},
        success: function (data) {
            $.each(data, function (i, item) {
                var long = item.lon;
                if (null != item.lon) {
                    var bus_lat = item.lat;
                    var bus_lon = item.lon;
                    var bus_point_i = new BMap.Point(bus_lon, bus_lat);
                    map.centerAndZoom(bus_point_i, 17);
                }
                var myIcon_i = new BMap.Icon("./static/img/point.png", new BMap.Size(30, 70));
                var marker2_i = new BMap.Marker(bus_point_i, {icon: myIcon_i});  // 创建标注

                //点击事件，显示文本内容
                var opts_i = {
                    position: bus_point_i,    // 指定文本标注所在的地理位置
                    offset: new BMap.Size(7, -25, 30, 30)    //设置文本偏移量 右  下
                }
                var infoWindow_i = new BMap.InfoWindow("小区名称：" + item.residenceName + '</br>' + "高清机顶盒用户数：" + item.hdUserNum, opts_i);  // 创建信息窗口对象
                //标签
                var label_i = new BMap.Label(i + 1, {offset: new BMap.Size(7, 3)});
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
        beforeSend: function () {
            com_param = false;
        },
        complete: function () {
            com_param = true;
        }
    });

}

function clearOutLine() {
    map.getOverlays().forEach(function (overlay) {
        if (overlay.isOutLine) {
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
    if (com_param) {
        var a_zoom = map.getZoom();
        if (a_zoom == 12) {
            map.setZoom(14);
            map.clearOverlays();
            showTown(null,null,null,null,"noChangeZoom");
            return;
        }
        if (a_zoom == 14) {
            map.setZoom(16);
            map.clearOverlays();
            showneighborhood();
            return;
        }
        if (a_zoom == 16) {
            map.setZoom(17);
            map.clearOverlays();
            showResidenceInfo();
            return;
        }
        if (a_zoom == 17) {
            return;
        }
    }
}

function reduceMapZoomSize() {
    if (com_param) {
        var a_zoom = map.getZoom();
        if (a_zoom == 16) {
            map.setZoom(14);
            showTown(null,null,null,null,"noChangeZoom");
            return;
        }
        if (a_zoom == 14) {
            map.setZoom(12);
            initDistrictInfo();
            return;

        }
        if (a_zoom == 17) {
            map.setZoom(16);
            showneighborhood();
            return;

        }
        if (a_zoom == 12) {
            return;
        }
    }
}

function ifNUll(filters) {

    if (filters.t == null && filters.o == null && filters.c == null && filters.p == null && filters.a == null && filters.l == null && filters.f == null && filters.g == null) {
        return true;
    } else return false;
}

function showMapDistrict(dataList) {
    map.clearOverlays();
    var center = new BMap.Point("121.464427", "31.228894");
    map.centerAndZoom(center, 12);
    map.clearOverlays();
    map.disableScrollWheelZoom();
    for (var i = 0; i < dataList.length; i++) {
        var item = dataList[i];
        var point_dis_i = new BMap.Point(item.longitude, item.latitude);
        //添加园圈
        var html = '<div id="' + item.dataId + '" onclick="getTown(' + item.dataId + ',' + item.longitude + ',' + item.latitude + ');" onmouseout="clearOutLine();" onmouseover="getJABoundary(' + item.dataId + ');" class="district-overlay">' + item.showName + '<br/>' + item.households + '户</p></div>';
        var anchor = new BMap.Size(-30, -25);
        var richMarker_i = new BMapLib.RichMarker(html, point_dis_i, {"anchor": anchor});
        map.addOverlay(richMarker_i);
        var overlayTop = $(richMarker_i._container);
        overlayTop.css("background", "transparent").addClass('map-overlay');
    }
}

function showMapTown(dataList) {
    map.clearOverlays();
    map.disableScrollWheelZoom();
    for(var i=0;i<dataList.length;i++){
        var item = dataList[i];
        if(null == item.longitude){continue;}
        cen_lon = item.longitude;
        cen_lat = item.latitude;
        map.centerAndZoom(new BMap.Point(cen_lon, cen_lat), 14);
        var point_dis_i = new BMap.Point(item.longitude, item.latitude);
        //添加园圈
        var html = '<div id="' + item.dataId + '" onclick="getJuWei(' + item.dataId + ',' + item.longitude + ',' + item.latitude + ');" class="plate-overlay"><p>' + item.showName + '</p><p class="map-overlay__total">' + item.households + '户</p></div>';
        var anchor = new BMap.Size(-30, -25);
        var richMarker_i = new BMapLib.RichMarker(html, point_dis_i, {"anchor": anchor});
        map.addOverlay(richMarker_i);
        var overlayTop = $(richMarker_i._container);
        overlayTop.css("background", "transparent").addClass('map-overlay');
    }
}

function showMapNeibarHood(dataList) {
    map.clearOverlays();
    map.disableScrollWheelZoom();
    for (var i = 0; i < dataList.length; i++) {
        var item = dataList[i];
        if (null == item.longitude) continue;
        cen_lon = item.longitude;
        cen_lat = item.latitude;
        map.centerAndZoom(new BMap.Point(cen_lon, cen_lat), 16);
        var point_dis_i = new BMap.Point(item.longitude, item.latitude);
        //添加园圈
        var html = '<div id="' + item.dataId + '" onclick="getResidence(' + item.dataId + ',' + item.longitude + ',' + item.latitude + ');" class="plate-overlay"><p>' + item.showName + '</p><p class="map-overlay__total">' + item.households + '户</p></div>';
        var anchor = new BMap.Size(-30, -25);
        var richMarker_i = new BMapLib.RichMarker(html, point_dis_i, {"anchor": anchor});
        map.addOverlay(richMarker_i);
        var overlayTop = $(richMarker_i._container);
        overlayTop.css("background", "transparent").addClass('map-overlay');
    }
}

function showMapResidence(dataList) {
    map.clearOverlays();
    map.disableScrollWheelZoom();
    for (var i = 0; i < dataList.length; i++) {
        var item = dataList[i];
        if (null == item.latitude) {
            continue;
        }
        var bus_lat = item.latitude;
        var bus_lon = item.longitude;
        var bus_point_i = new BMap.Point(bus_lon, bus_lat);
        map.centerAndZoom(bus_point_i, 17);
        var myIcon_i = new BMap.Icon("./static/img/point.png", new BMap.Size(30, 70));
        var marker2_i = new BMap.Marker(bus_point_i, {icon: myIcon_i});  // 创建标注
        //点击事件，显示文本内容
        var opts_i = {
            position: bus_point_i,    // 指定文本标注所在的地理位置
            offset: new BMap.Size(7, -25, 30, 30)    //设置文本偏移量 右  下
        }
        var infoWindow_i = new BMap.InfoWindow("小区名称：" + item.showName + '</br>' + "高清机顶盒用户数：" + item.households, opts_i);  // 创建信息窗口对象
        //标签
        var label_i = new BMap.Label(i + 1, {offset: new BMap.Size(7, 3)});
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
    }
}

function showMap(dataId) {
    var type = getIdType(dataId);
    if (type == 4) {
        openResidenceInfoWindow(dataId);
        return;
    }
    if (type == 1) {
        getJABoundary(dataId);
    }
}
function hideMap() {
    clearOutLine();
}

function getIdType(showDataId) {
    if ((showDataId.indexOf("3101") == 0 || showDataId == "310230") && showDataId.length == 6) {
        return 1;
    } else if ((showDataId.indexOf("3101") == 0 || showDataId.indexOf("310230")) && showDataId.length == 9) {
        return 2;
    } else if ((showDataId.indexOf("3101") == 0 || showDataId.indexOf("310230")) && showDataId.length == 12) {
        return 3;
    } else {
        return 4;
    }
}

function openResidenceInfoWindow(dataId) {
    $.ajax({
        type: "get",
        url: "./getResidence.do",
        data: {"neighborhoodId": null, "residenceId": dataId},
        success: function (data) {
            $.each(data, function (i, item) {
                if (null == item.lon) {
                    return;
                }
                var bus_lat = item.lat;
                var bus_lon = item.lon;
                var bus_point_i = new BMap.Point(bus_lon, bus_lat);
                //点击事件，显示文本内容
                var opts_i = {
                    position: bus_point_i,    // 指定文本标注所在的地理位置
                    offset: new BMap.Size(7, -25, 30, 30)    //设置文本偏移量 右  下
                }
                var infoWindow_i = new BMap.InfoWindow("小区名称：" + item.residenceName + '</br>' + "高清机顶盒用户数：" + item.hdUserNum, opts_i);  // 创建信息窗口对象
                //标签
                var label_i = new BMap.Label(i + 1, {offset: new BMap.Size(7, 3)});
                label_i.setStyle({
                    color: "white",
                    fontSize: "10px",
                    backgroundColor: "0.05",
                    border: "0",
                    fontFamily: "微软雅黑"
                });
                map.openInfoWindow(infoWindow_i, bus_point_i); //开启信息窗口
            })
        },
        beforeSend: function () {
            com_param = false;
        },
        complete: function () {
            com_param = true;
        }
    });
}