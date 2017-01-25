<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/include/top.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var="pageTitle1" value="主页"/>
<c:set var="pageTitle2" value="小区搜索"/>
<%@include file="/WEB-INF/pages/include/header.jsp" %>
<head>
    <title>小区搜索</title>
</head>
<body>
<body>
<div class="container">
    <div class="input-group input-group-lg">
        <input id="in" type="text" class="form-control" placeholder="请输入小区名称" value="文化花园">
        <span class="input-group-btn">
            <button class="btn green" type="button" onclick="query($('#in').val())">开始查找</button>
        </span>
    </div>
    <div style="display: none" id="table1">
        <table id="residence" class="table table-striped table-bordered table-hover order-column" height="80px">
            <thead>
            <tr>
                <th>小区id</th>
                <th>小区名称</th>
                <th>小区地址</th>
                <th>楼栋数</th>
                <th>户数</th>
                <th>容积率</th>
                <th>绿化率</th>
                <th>小区类型</th>
                <th>小区别名</th>
                <th>占地面积</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="tb">
            <td id="id"></td>
            <td id="name"></td>
            <td id="addr"></td>
            <td id="buildingCount"></td>
            <td id="houseCount"></td>
            <td id="vp"></td>
            <td id="gp"></td>
            <td id="residenceType"></td>
            <td id="aliases"></td>
            <td id="area"></td>
            <td id="do"></td>
            </tbody>
        </table>
    </div>

    <div style="display: none" id="table2">
        <table id="build" class="table table-striped table-bordered table-hover order-column" height="80px">
            <thead>
            <tr>
                <th>楼栋id</th>
                <th>楼栋号</th>
                <th>户数</th>
                <th>总楼层</th>
                <th>更新时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="tb1">
            <td id="id1"></td>
            <td id="buildingNo"></td>
            <td id="houseCount1"></td>
            <td id="totalFloor"></td>
            <td id="updateTime1"></td>
            </tbody>
        </table>
        <div >
            <button type="button" class="btn btn-primary" onclick="query($('#in').val())">返回小区搜索列表</button>
            <ul class="pager" style="position: relative;left: 36.5%;top: -61px;">
                <li>第 <input id="page" style="width:30px;" value="1"> 页</li>
                <li> 共 <input id="totalPage" style="width:30px;"> 页</li>
                <li><a href="javascript:void(0)" onclick="reducePage();">上一页</a></li>
                <li><a href="javascript:void(0)" onclick="addPage();">下一页</a></li>
            </ul>
        </div>
    </div>


    <div style="text-align: center;position: relative;top: 220px;"><img src="${ctx}/static/img/viewmag-256.png"
                                                                        id="img"></div>
    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">温馨提示</h4>
                </div>
                <div class="modal-body">该功能暂未开放</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</div>
</body>
<%@include file="/WEB-INF/pages/include/bottom.jsp" %>
</body>
<script type="text/javascript">

    var startPage = 1;
    var totalPage = 0;
    var residenceId = "";

    $(function () {
        $('#myModal').modal('hide');
    })

    function query(keyword) {
        if (keyword == "") {
            bootbox.alert("關鍵字不能爲空，請重新輸入！！");
            return;
        } else {
            $("#table1").show();
            $("#img").hide();
            $("#table2").hide();
            $.ajax({
                url: '${ctx}/query.do?',
                type: "Post",
                data: {"keyword": keyword},
                success: function (data) {
                    $("#tb").empty();
                    for (var i = 0; i < data.length; i++) {
                        var html = "<tr><td id='id" + i + "'></td>" +
                                "<td id='name" + i + "'></td>" +
                                "<td id='addr" + i + "'></td>" +
                                "<td id='buildingCount" + i + "'></td>" +
                                "<td id='houseCount" + i + "'></td>" +
                                "<td id='vp" + i + "'></td>" +
                                "<td id='gp" + i + "'></td>" +
                                "<td id='residenceType" + i + "'></td>" +
                                "<td id='aliases" + i + "'></td>" +
                                "<td id='area" + i + "'></td>" +
                                "<td id= " + i + "><a href='${ctx}/residenceDetail.do?residenceId="+data[i].id+"'>小区详情</a>  <a href='javascript:void(0)' onclick='build(" + data[i].id + ")'>楼栋列表</a> </td></tr>";
                        $("#tb").append(html);
                        $("#id" + i).html(data[i].id);
                        $("#name" + i).html(data[i].residenceName);
                        $("#addr" + i).html(data[i].residenceAddr);
                        $("#buildingCount" + i).html(data[i].buildingCount);
                        $("#houseCount" + i).html(data[i].houseCount);
                        $("#vp" + i).html(data[i].vp);
                        $("#gp" + i).html(data[i].gp);
                        $("#residenceType" + i).html(data[i].residenceTypeId);
                        $("#area" + i).html(data[i].area);
                        $("#aliases" + i).html(data[i].aliases);
                    }
                }
            })
        }
    }

    function build(id) {
        $("#table1").hide();
        $("#table2").show();
        $("#img").hide();
        residenceId = id;
        $.ajax({
            url: '${ctx}/build.do',
            type: "Post",
            data: {"id": residenceId},
            success: function (object) {
                var data = object.data;
                if (null == data || data.length == 0) {
                    bootbox.alert("未找到楼栋数据！！");
                    return;
                }
                var  t_Page = object.totalPage;
                $("#totalPage").val(t_Page);
                totalPage = t_Page;
                $("#tb1").empty();
                for (var i = 0; i < data.length; i++) {
                    var html = "<tr><td id='id1" + i + "'></td>" +
                            "<td id='buildingNo" + i + "'></td>" +
                            "<td id='houseCount1" + i + "'></td>" +
                            "<td id='totalFloor" + i + "'></td>" +
                            "<td id='updateTime1" + i + "'></td>" +
                            "<td ><a href='javascript:void(0)' onclick='showBuilding(" + data[i].id + ");'>楼栋详情</a></td></tr>";
                    $("#tb1").append(html);
                    $("#id1" + i).html(data[i].id);
                    $("#buildingNo" + i).html(data[i].buildingNo);
                    $("#houseCount1" + i).html(data[i].houseCount);
                    $("#totalFloor" + i).html(data[i].totalFloor);
                    $("#updateTime1" + i).html(data[i].updateTime1);
                }
            }
        })
        $("#page").val(startPage);
    }
    function showBuilding(buildingId) {
        if(buildingId == ""){
            return;
        }
        location.href ='building.do?buildingId='+buildingId;
    }
    function show() {
        $('#myModal').modal({
            keyboard: true
        })
    }
    function reducePage() {
        if (startPage == 1) {
            bootbox.alert("已是最前一页！！");
            return;
        }
        $.ajax({
            url: '${ctx}/build.do?',
            type: "Post",
            data: {"id": residenceId, "page": startPage - 1},
            success: function (Object) {
                var data = Object.data;
                if (null == data || data.length == 0) {
                    bootbox.alert("未找到楼栋数据！！");
                    return;
                }
                $("#tb1").empty();
                for (var i = 0; i < data.length; i++) {
                    var html = "<tr><td id='id1" + i + "'></td>" +
                            "<td id='buildingNo" + i + "'></td>" +
                            "<td id='houseCount1" + i + "'></td>" +
                            "<td id='totalFloor" + i + "'></td>" +
                            "<td id='updateTime1" + i + "'></td>" +
                            "<td ><a href='javascript:void(0)' onclick='showBuilding(" + data[i].id + ");'>楼栋详情</a></td></tr>";
                    $("#tb1").append(html);
                    $("#id1" + i).html(data[i].id);
                    $("#buildingNo" + i).html(data[i].buildingNo);
                    $("#houseCount1" + i).html(data[i].houseCount);
                    $("#totalFloor" + i).html(data[i].totalFloor);
                    $("#updateTime1" + i).html(data[i].updateTime1);
                }
            }
        })
        startPage = startPage - 1;
        $("#page").val(startPage);
    }
    function addPage() {
        if (startPage == totalPage) {
            bootbox.alert("已是最后一页！！");
            return;
        }
        $.ajax({
            url: '${ctx}/build.do?',
            type: "Post",
            data: {"id": residenceId, "page": startPage + 1},
            success: function (Object) {
                var data = Object.data;
                if (null == data || data.length == 0) {
                    bootbox.alert("未找到楼栋数据！！");
                }
                $("#tb1").empty();
                for (var i = 0; i < data.length; i++) {
                    var html = "<tr><td id='id1" + i + "'></td>" +
                            "<td id='buildingNo" + i + "'></td>" +
                            "<td id='houseCount1" + i + "'></td>" +
                            "<td id='totalFloor" + i + "'></td>" +
                            "<td id='updateTime1" + i + "'></td>" +
                            "<td ><a href='javascript:void(0)' onclick='showBuilding(" + data[i].id + ");'>楼栋详情</a></td></tr>";
                    $("#tb1").append(html);
                    $("#id1" + i).html(data[i].id);
                    $("#buildingNo" + i).html(data[i].buildingNo);
                    $("#houseCount1" + i).html(data[i].houseCount);
                    $("#totalFloor" + i).html(data[i].totalFloor);
                    $("#updateTime1" + i).html(data[i].updateTime1);
                }
            }
        })
        startPage = startPage + 1;
        $("#page").val(startPage);
    }

</script>
</html>
