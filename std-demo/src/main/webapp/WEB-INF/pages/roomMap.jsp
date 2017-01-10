<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>aa</title>
    <script src="${ctx}/static/js/jquery.min.js"></script>
</head>
<body>
<c:if test="${empty keywords}">
    <div style="text-align: center;padding-top: 180px;">
        <img src="${ctx}/static/img/viewmag-256.png">
    </div>
</c:if>

<c:if test="${not empty keywords and empty residenceModels}">
    <div style="text-align: center;padding-top: 180px;">
        <!--    <img src="${ctx}/static/img/nothing.gif">-->
        <span>( 「 「 ) ~~~→ 楼栋未找到。</span>
    </div>
</c:if>

<c:forEach items="${residenceModels}" var="r">
    <div class="myhr"></div>
    <p name="rid_${r.residenceId}" id="rid_${r.residenceId}"><span class="simpleTitle">小区:</span>:${r.name}&nbsp;
    </p>
    <p><span>楼栋位图</span>&nbsp;&nbsp;</p>
    <div style="clear: both">
        <c:forEach items="${r.buildingBitMaps}" var="be" varStatus="s">
            <div class="building-map" style="color: ${be.exists ? 'green' : 'red'};">
                <c:if test="${not be.exists}">
                    <a class="addbuilding" href="#" id="b_${r.residenceId}_${be.buildingName}"
                       onclick="addBuilding(${r.residenceId}, '${be.buildingName}','添加此楼');return false">${be.buildingName}</a>
                </c:if>
                <c:if test="${be.exists}">
                    <a href="#b_${be.buildingId}"
                       id="b_${r.residenceId}_${be.buildingName}">${s.index + r.minBuilding}</a>
                </c:if>
            </div>
        </c:forEach>
        <div style="clear:both;"></div>
    </div>
    <c:forEach items="${r.buildings}" var="b">
        <table class="table1" border="1" cellpadding="1" cellspacing="1" name="b_${b.id}" id="b_${b.id}">
            <thead>
            <tr>
                <td colspan="100">
                    <div class="cell">
                        <c:set var="string2" value="${fn:split(roomNo, ',')}"/>
                        <div class="cellLeft" style="width: 60%;">
                                <span id="building" style="text-indent: 4px" class="glyphicon glyphicon glyphicon-th"
                                      aria-hidden="true">${b.name}&nbsp;${b.totalFloor}F</span>
                        </div>
                        <div class="cellRight">
                            <a class="plus" href="#"
                               onclick="layer.alert('以列表的形式，详细地显示此楼栋的全部信息，一目了然。');return false">数据列表</a>
                        </div>
                    </div>
                </td>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${b.floors}" var="f">
                <c:if test="${f.real}">
                    <tr>
                        <c:forEach items="${f.rooms}" var="r">
                            <c:if test="${r.real}">
                                <td ondblclick="viewDetail(${r.id},'${r.oriAddress}','${r.src}');"
                                    class="${b.id}_${r.name}" id="${r.id}_t">
                                    <c:choose>
                                        <c:when test="${r.status eq 10 or r.status eq 20 or r.status eq 30}">
                                            <div class="cell">
                                                <div class="cellTop" style="height: 60%;">
                                                    <c:choose>
                                                        <c:when test="${r.status eq 10}">
                                                            <c:set var="clr1" value="#57a957"/>
                                                        </c:when>
                                                        <c:when test="${r.status eq 20}">
                                                            <c:set var="clr1" value="#985f0d"/>
                                                        </c:when>
                                                        <c:when test="${r.status eq 30}">
                                                            <c:set var="clr1" value="#2f96b4"/>
                                                        </c:when>
                                                    </c:choose>

                                                    <span style="color: ${clr1}; " class="${b.id}_${r.name}_1"
                                                          id="${r.id}_c">${r.name}</span>
                                                    <a class="plus" id="${b.id}_${r.name}_2"
                                                       style="font-size: large;display: none;" href="#"
                                                       onclick="addRoom(${b.id},${r.name});return false;"
                                                       title="添加此房屋">+</a>
                                                    <div class="btn-group">
                                                        <button class="btn btn-default btn-xs dropdown-toggle"
                                                                type="button"
                                                                data-toggle="dropdown" aria-haspopup="true"
                                                                aria-expanded="false">
                                                        <span class="glyphicon glyphicon glyphicon-pencil"
                                                              aria-hidden="true"></span>
                                                        </button>
                                                        <ul class="dropdown-menu">
                                                            <li><a href="#" class="${b.id}_${r.name}_01"
                                                                   onclick="editArea(${r.id},$('.${b.id}_${r.name}_3')[0].innerText,'${b.id}_${r.name}_3');return false;">修改面积</a>
                                                            </li>
                                                            <li><a href="#" class="${b.id}_${r.name}_02"
                                                                   onclick="delRoom(${r.id});return false;">删除</a>
                                                            </li>
                                                            <li role="separator" class="divider"></li>
                                                            <li><a href="#" class="${b.id}_${r.name}_03"
                                                                   onclick="approve(${r.id},'${b.id}_${r.name}');return false;">锁定</a>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                                <div class="cellBottom" style="height: 40%;">
                                                        <span style="color: #1b1b1b;font-size: 8px"><i
                                                                class="${b.id}_${r.name}_3" id="${r.id}_a">${r.area}</i></span>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:when test="${r.status == 40}">
                                            <div class="cell">
                                                <div class="cellTop" style="height: 60%;">
                                                    <span style="color:#000;">${r.name}</span>
                                                    <span title="房屋已经锁定" class="glyphicon glyphicon glyphicon-lock"
                                                          aria-hidden="true"></span>
                                                </div>
                                                <div class="cellBottom" style="height: 40%;">
                                                        <span style="color: #1b1b1b;font-size: 8px"><i
                                                                class="${b.id}_${r.name}_3"
                                                                id="${r.id}">${r.area}</i></span>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            [[!数据错误!]]
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </c:if>
                            <c:if test="${not r.real}">
                                <td class="${b.id}_${r.name}">
                                    <div class="cell">
                                        <div class="cellTop" style="height: 60%;">
                                            <span class="${b.id}_${r.name}_1" style="color: red; ">${r.name}</span>
                                            <a class="plus" id="${b.id}_${r.name}_2" style="font-size: large"
                                               href="javascript:void(0)" onclick="addRoom(${b.id},${r.name});"
                                               title="添加此房屋">+</a>

                                            <div class="btn-group" style="display:none;">
                                                <button class="btn btn-default btn-xs dropdown-toggle" type="button"
                                                        data-toggle="dropdown" aria-haspopup="true"
                                                        aria-expanded="false">
                                                        <span class="glyphicon glyphicon glyphicon-pencil"
                                                              aria-hidden="true"></span>
                                                </button>
                                                <ul class="dropdown-menu">
                                                    <li><a href="javascript:void(0)"
                                                           class="${b.id}_${r.name}_01">修改面积</a>
                                                    </li>
                                                    <li><a href="javascript:void(0)"
                                                           class="${b.id}_${r.name}_02">删除</a>
                                                    </li>
                                                    <li role="separator" class="divider"></li>
                                                    <li><a href="javascript:void(0)"
                                                           class="${b.id}_${r.name}_03">锁定</a>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                        <div class="cellBottom" style="height: 40%;">
                                                <span style="color: #1b1b1b;font-size: 8px"><i
                                                        class="${b.id}_${r.name}_3"></i></span>
                                        </div>
                                    </div>
                                </td>
                            </c:if>
                        </c:forEach>
                        <td style="text-align: center;font-size: 30px; width: 40px;min-width: 40px;">
                            <a class="plus" href="#" onclick="addNewRoom(${b.id});return false;"
                               title="在此楼层添加房屋">+</a>
                        </td>
                    </tr>
                </c:if>
                <c:if test="${not f.real}">
                    <tr>
                        <td colspan="100" class="unreal">
                            <span style="color: #e9322d;">${f.name}</span>
                            <a class="plus" style="font-size: large" href="#"
                               onclick="addNewRoom(${b.id});return false;" title="在此楼层添加房屋">+</a>
                            <!--
                            <a class="plus" href="#" onclick="addRoom();return false;">在此楼层创建房屋</a>-->
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
            <tr id="s_${b.id}">
                <td colspan="100" class="suggestion">
                    建议:无
                        <%--
                        <s>建议:补全3楼 <a href="#">接受</a></s>
                        --%>
                </td>
            </tr>
            </tbody>
        </table>
    </c:forEach>
</c:forEach>
</div>

<script>

    var arr = new Array;

    $(function () {
        var height = $("body").height() + 30;
        window.parent.resizeFrame(height);
        var roomNo = window.parent.roomNo;
        var buildNo = window.parent.building;
        var building = new Array;
        building = $(".cellLeft");
        for (var i = 0; i < building.length; i++) {
            var div = building[i];
            var text = $(div).text();
            arr = text.trim().split('号');
            for (var j = 0; j < arr.length; j++) {
                if (arr[j] + "号" == buildNo) {
                    $(div).attr("class", "text-success");
                    $(div).attr("size", "4");
                }
            }
        }

    });

</script>
<script type="text/css">

</script>

</body>
<link href="${ctx}/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="${ctx}/static/css/roomMap.css" rel="stylesheet"/>
<link href="${ctx}/bootstrap/css/bootstrap-responsive.css" rel="stylesheet"/>
<link href="${ctx}/layer/skin/layer.css" rel="stylesheet"/>
</html>
