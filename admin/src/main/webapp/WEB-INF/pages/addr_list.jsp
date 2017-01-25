<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/include/top.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var="pageTitle1" value="列表"/>
<c:set var="pageTitle2" value="人工地址拆分列表"/>
<%@include file="/WEB-INF/pages/include/header.jsp" %>
<head>
    <title>人工地址拆分列表</title>
</head>
<body>
<body>
<div class="container">

    <div style="display: block" id="table1">
        <table id="residence" class="table table-striped table-bordered table-hover order-column" height="80px">
            <thead>
            <tr>
                <th>小区id</th>
                <th>地址</th>
                <th>路弄</th>
                <th>楼栋号</th>
                <th>房间号</th>
                <th>解析版本</th>
                <th>解析时间</th>
                <th>状态</th>
                <th>操作</th>

            </tr>
            </thead>
            <tbody id="tb">
            <td id="id"></td>
            <td id="address"></td>
            <td id="roadLane"></td>
            <td id="buildingNo"></td>
            <td id="houseNo"></td>
            <td id="parsedVersion"></td>
            <td id="parsedTime"></td>
            <td id="status"></td>
            <td id="do"><a href="#"></a></td>
            </tbody>
        </table>
    </div>




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
    $(function () {
        $.ajax({
            url: '${ctx}/addrQuery.do?',
            type: "get",
//            data: {"keyword": keyword},
            success: function (data) {
                $("#tb").empty();
                for (var i = 0; i < data.length; i++) {
                    var html = "<tr><td id='id" + i + "'></td>" +
                            "<td id='address" + i + "'></td>" +
                            "<td id='roadLane" + i + "'></td>" +
                            "<td id='buildingNo" + i + "'></td>" +
                            "<td id='houseNo" + i + "'></td>" +
                            "<td id='parsedVersion" + i + "'></td>" +
                            "<td id='parsedTime" + i + "'></td>" +
                            "<td id='status" + i + "'></td>" +
                            "<td id= " + i + "><a href='${ctx}/parsed.do?addressId="+data[i].id+"'>人工解析</a>  </td></tr>";
                    $("#tb").append(html);
                    $("#id" + i).html(data[i].id);
                    $("#address" + i).html(data[i].address);
                    $("#roadLane" + i).html(data[i].roadLane);
                    $("#buildingNo" + i).html(data[i].buildingNo);
                    $("#houseNo" + i).html(data[i].houseNo);
                    $("#parsedVersion" + i).html(data[i].parsedVersion);
                    $("#parsedTime" + i).html(data[i].parsedTime);
                    $("#status" + i).html(data[i].status);
                }
            }
        })
    });
</script>
</html>
