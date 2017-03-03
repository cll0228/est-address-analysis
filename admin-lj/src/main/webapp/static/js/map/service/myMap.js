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
    //拖拽结束触发事件
    map.addEventListener("dragend",function () {
        var zoom =  map.getZoom();
        var center = map.getCenter();
        if(zoom == 14){
            showTown(null,null,null,center);
        }
        if(zoom == 17){
            showneighborhood(null,null,null,center);
        }
        if(zoom == 19){
            showResidenceInfo(null,null,null,null,center);
        }
    });
    //加載行政區域
   // initDistrictInfo();
    //不能按-
    $("#szoom").attr("class"," zoom-tool__item icon-minus zoom-tool__item--disabled");
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
                var html = '<div id="' + item.districtId + '" onclick="getTown(' + item.districtId + ');" onmouseout="clearOutLine();" onmouseover="getJABoundary(' + item.districtId + ');" class="district-overlay">' + item.district + '<br/>' + item.hdUserNum + '户</p></div>';
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

function getTown(districtId, townId) {
    $("#szoom").attr("class","zoom-tool__item icon-minus");
    showTown(districtId, townId, "chang",null);
}

function showTown(districtId, townId, ifchangZoom,center) {
    if(null == center){
        center = map.getCenter();
    }
        var lng = center.lng;
        var lat = center.lat;
    if("chang" == ifchangZoom){
        lat = null;
        lng = null;
    }
    $.ajax({
        type: "get",
        url: "./showtown.do",
        data: {"districtId": districtId, "townId": townId,"lng":lng,"lat":lat},
        success: function (data) {
            if("chang" == ifchangZoom){
                map.clearOverlays();
                map.centerAndZoom(new BMap.Point(data[0].cenLon, data[0].cenLat),14);
            }
            $.each(data, function (i, item) {
                var point_dis_i = new BMap.Point(item.longitude, item.latitude);
                //添加园圈
                var html = '<div id="' + item.townId + '" onclick="getJuWei(' + item.townId + ');" class="plate-overlay"><p>' + item.townName + '</p><p class="map-overlay__total">' + item.hdUserNum + '户</p></div>';
                var anchor = new BMap.Size(-30, -25);
                var richMarker_i = new BMapLib.RichMarker(html, point_dis_i, {"anchor": anchor});
                richMarker_i.Name = item.townId;
                if(ifadd(richMarker_i)){
                    map.addOverlay(richMarker_i);
                    var overlayTop = $(richMarker_i._container);
                    overlayTop.css("background", "transparent").addClass('map-overlay');
                }
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

function getJuWei(townId,  neighborhoodId) {
    showneighborhood(townId,  neighborhoodId, "chang",null);
}

var cen_lon;
var cen_lat;

function showneighborhood(townId,  neighborhoodId, ifchangZoom,center) {
    if(null == center){
        center = map.getCenter();
    }
    var lng = center.lng;
    var lat = center.lat;
    if("chang" == ifchangZoom){
        lat = null;
        lng = null;
        map.clearOverlays();
        map.setZoom(17);
    }
    $.ajax({
        type: "get",
        url: "./neighborhood.do",
        data: {"townId": townId, "neighborhoodId": neighborhoodId,"lng":lng,"lat":lat},
        success: function (data) {
            if("chang" == ifchangZoom){
                map.centerAndZoom(new BMap.Point(data[0].lng, data[0].lat),17);
            }
            $.each(data, function (i, item) {
                var point_dis_i = new BMap.Point(item.lng, item.lat);
                //添加园圈
                var html = '<div id="' + item.neighborhoodId + '" onclick="getResidence(' + item.neighborhoodId + ');" class="plate-overlay"><p>' + item.neighborhoodName + '</p><p class="map-overlay__total">' + item.hdUserNum + '户</p></div>';
                var anchor = new BMap.Size(-30, -25);
                var richMarker_i = new BMapLib.RichMarker(html, point_dis_i, {"anchor": anchor});
                richMarker_i.Name = item.neighborhoodId;
                if(ifadd(richMarker_i)){
                    map.addOverlay(richMarker_i);
                    var overlayTop = $(richMarker_i._container);
                    overlayTop.css("background", "transparent").addClass('map-overlay');
                }
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

function getResidence(neighborhoodId,  residenceId, ifchangeZoom) {
    flag = 0;
    showResidenceInfo(neighborhoodId,  residenceId, "chang",null);
}

function showResidenceInfo(neighborhoodId, residenceId, ifchangZoom,center) {
    $("#bzoom").attr("class","zoom-tool__item icon-plus zoom-tool__item--disabled");
    if(null == center){
        center = map.getCenter();
    }
    var lng = center.lng;
    var lat = center.lat;
    if("chang" == ifchangZoom){
        lat = null;
        lng = null;
    }
    $.ajax({
        type: "get",
        url: "./getResidence.do",
        data: {"neighborhoodId": neighborhoodId, "residenceId": residenceId,"lng":lng,"lat":lat},
        success: function (data) {
            if("chang" == ifchangZoom){
                map.clearOverlays();
                map.centerAndZoom(new BMap.Point(data[0].lon, data[0].lat),19);
            }
            $.each(data, function (i, item) {
               var bus_point_i = new BMap.Point(item.lon,item.lat);
                var html = '<div class="estate-overlay__count js_estateOverlayCount">' + item.hdUserNum + '户</div>';
                var anchor = new BMap.Size(-42, -28);
                var richMarker_i = new BMapLib.RichMarker(html, bus_point_i, {"anchor": anchor});
                //点击事件，显示文本内容
                var opts_i = {
                    position: bus_point_i,    // 指定文本标注所在的地理位置
                    offset: new BMap.Size(7, -25, 30, 30)    //设置文本偏移量 右  下
                }
                var infoWindow_i = new BMap.InfoWindow("小区名称：" + item.residenceName + '</br>' + "高清+智能总户数：" + item.hdUserNum+'</br>'+"小区总户数："+item.houseCount+'</br>'+"占比："+item.rate+'%', opts_i);  // 创建信息窗口对象
                //圖標點擊事件
                richMarker_i.addEventListener("mouseover", function () {
                    map.openInfoWindow(infoWindow_i, bus_point_i); //开启信息窗口
                });
                richMarker_i.Name = item.residenceId;
                if(ifadd(richMarker_i)){
                    map.addOverlay(richMarker_i);
                }
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
            $("#szoom").attr("class","zoom-tool__item icon-minus");
            map.setZoom(14);
            map.clearOverlays();
            showTown();
            return;
        }
        if (a_zoom == 14) {
            map.setZoom(17);
            map.clearOverlays();
            showneighborhood();
            return;
        }
        if (a_zoom == 17) {
            $("#bzoom").attr("class","zoom-tool__item icon-plus zoom-tool__item--disabled");
            map.setZoom(19);
            map.clearOverlays();
            showResidenceInfo();
            return;
        }
        if (a_zoom == 19) {
            return;
        }
    }
}

function reduceMapZoomSize() {
    if (com_param) {
        var a_zoom = map.getZoom();
        if (a_zoom == 17) {
            map.clearOverlays();
            map.setZoom(14);
            showTown();
            return;
        }
        if (a_zoom == 14) {
            $("#szoom").attr("class","zoom-tool__item icon-minus zoom-tool__item--disabled");
            map.clearOverlays();
            map.setZoom(12);
            initDistrictInfo();
            return;
        }
        if (a_zoom == 19) {
            $("#bzoom").attr("class","zoom-tool__item icon-plus");
            map.clearOverlays();
            map.setZoom(17);
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
    if(null != map){
        map.clearOverlays();
    }
    var center = new BMap.Point("121.464427", "31.228894");
    map.centerAndZoom(center, 12);
    map.clearOverlays();
    map.disableScrollWheelZoom();
    for (var i = 0; i < dataList.length; i++) {
        var item = dataList[i];
        var point_dis_i = new BMap.Point(item.longitude, item.latitude);
        //添加园圈
        var html = '<div id="' + item.dataId + '" onclick="getTown(' + item.dataId + ',' + item.longitude + ',' + item.latitude + ');"  onmouseout="clearOutLine();" onmouseover="getJABoundary(' + item.dataId + ');" class="district-overlay">' + item.showName + '<br/>' + item.households + '户</p></div>';
        var anchor = new BMap.Size(-30, -25);
        var richMarker_i = new BMapLib.RichMarker(html, point_dis_i, {"anchor": anchor});
        map.addOverlay(richMarker_i);
        var overlayTop = $(richMarker_i._container);
        overlayTop.css("background", "transparent").addClass('map-overlay');
    }
}

function showMapTown(dataList) {
    $("#szoom").attr("class","zoom-tool__item icon-minus");
    if(null != map){
        map.clearOverlays();
    }
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
        richMarker_i.Name = item.dataId;
        if(ifadd(richMarker_i)){
            map.addOverlay(richMarker_i);
            var overlayTop = $(richMarker_i._container);
            overlayTop.css("background", "transparent").addClass('map-overlay');
        }
    }
}

function showMapNeibarHood(dataList) {
    if(null != map){
        map.clearOverlays();
    }
    map.disableScrollWheelZoom();
    for (var i = 0; i < dataList.length; i++) {
        var item = dataList[i];
        if (null == item.longitude) continue;
        cen_lon = item.longitude;
        cen_lat = item.latitude;
        map.centerAndZoom(new BMap.Point(cen_lon, cen_lat), 17);
        var point_dis_i = new BMap.Point(item.longitude, item.latitude);
        //添加园圈
        var html = '<div id="' + item.dataId + '" onclick="getResidence(' + item.dataId + ',' + item.longitude + ',' + item.latitude + ');" class="plate-overlay"><p>' + item.showName + '</p><p class="map-overlay__total">' + item.households + '户</p></div>';
        var anchor = new BMap.Size(-30, -25);
        var richMarker_i = new BMapLib.RichMarker(html, point_dis_i, {"anchor": anchor});
        richMarker_i.Name = item.dataId;
        if(ifadd(richMarker_i)){
            map.addOverlay(richMarker_i);
            var overlayTop = $(richMarker_i._container);
            overlayTop.css("background", "transparent").addClass('map-overlay');
        }
    }
}

function showMapResidence(dataList) {
    $("#bzoom").attr("class","zoom-tool__item icon-plus zoom-tool__item--disabled");
    if(null != map){
        map.clearOverlays();
    }
    map.disableScrollWheelZoom();
    for (var i = 0; i < dataList.length; i++) {
        var item = dataList[i];
        if (null == item.latitude) {
            continue;
        }
        var bus_lat = item.latitude;
        var bus_lon = item.longitude;
        var bus_p_i = new BMap.Point(bus_lon,bus_lat);
        map.centerAndZoom(bus_p_i, 19);
        var html = '<div class="estate-overlay__count js_estateOverlayCount">' + item.households + '户</div>';
        var anchor = new BMap.Size(-42, -28);
        var rich_i = new BMapLib.RichMarker(html, bus_p_i, {"anchor": anchor});
        //点击事件，显示文本内容
        var opts_i = {
            position: bus_p_i,    // 指定文本标注所在的地理位置
            offset: new BMap.Size(7, -25, 30, 30)    //设置文本偏移量 右  下
        }
        var infoW_i = new BMap.InfoWindow("小区名称：" + item.showName + '</br>' + "高清+智能总户数：" + item.households+'</br>'+"小区总户数："+item.houseCount+'</br>'+"占比："+item.proportion+'%', opts_i);  // 创建信息窗口对象
        //圖標點擊事件
        rich_i.addEventListener("mouseover", function () {
            map.openInfoWindow(infoW_i, bus_p_i); //开启信息窗口
        });
        rich_i.Name = item.dataId;
        if(ifadd(rich_i)){
            map.addOverlay(rich_i);
        }
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
                var infoWindow_i = new BMap.InfoWindow("小区名称：" + item.residenceName + '</br>' + "高清+智能总户数：" + item.hdUserNum+'</br>'+"小区总户数："+item.houseCount+'</br>'+"占比："+item.rate+'%', opts_i);  // 创建信息窗口对象
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

function ifadd(obj) {
    var flag = true;
    map.getOverlays().forEach(function (overlay) {
        if (overlay.Name == obj.Name) {
           flag = false;
        }
    });
    return flag;
}
function clearAllOver() {
    map.clearOverlays();
}

function showMapDistrict(dataList) {
    for(var i = 0;i<dataList.length;i++){
        var item = dataList[i];
        var point_dis_i = new BMap.Point(item.longitude, item.latitude);
        //添加园圈
        var html = '<div id="' + item.dataId + '" onclick="getTown(' + item.dataId + ');" onmouseout="clearOutLine();" onmouseover="getJABoundary(' + item.dataId + ');" class="district-overlay">' + item.showName + '<br/>' + item.households + '户</p></div>';
        var anchor = new BMap.Size(-30, -25);
        var richMarker_i = new BMapLib.RichMarker(html, point_dis_i, {"anchor": anchor});
        map.addOverlay(richMarker_i);
        var overlayTop = $(richMarker_i._container);
        overlayTop.css("background", "transparent").addClass('map-overlay');
    }
}