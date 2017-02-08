<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/include/top.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var="pageTitle1" value="列表"/>
<c:set var="pageTitle2" value="解析任务管理"/>
<%@include file="/WEB-INF/pages/include/header.jsp" %>
<head>
    <title>解析任务管理列表</title>
</head>
<body>
<body>
<div class="container">
    <div>
        名称&nbsp;<input type="text" id="name" />&nbsp;
        开始时间&nbsp;<input type="text" class="sang_Calender" id="stime"/>&nbsp;
        结束时间&nbsp;<input type="text" class="sang_Calender" id="etime"/>&nbsp;
        <input type="button" value="查找" />
    </div>
    <br>
    <div style="display: block" id="table1">
        <table id="residence" class="table table-striped table-bordered table-hover order-column" height="80px">
            <thead>
            <tr>
                <th>ID</th>
                <th>数据源服务器</th>
                <th>数据表</th>
                <th>地址列</th>
                <th>总量</th>
                <th>成功数量</th>
                <th>失败数量</th>
                <th>进度</th>
                <th>状态</th>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>操作人员</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="tb" align="middle">
                <td id="id"></td>
                <td id="dataSourceServer"></td>
                <td id="dataTable"></td>
                <td id="addressColumn"></td>
                <td id="totalNum"></td>
                <td id="successNum"></td>
                <td id="failNum"></td>
                <td id="schedule"></td>
                <td id="status"></td>
                <td id="startTime"></td>
                <td id="endTime"></td>
                <td id="operator"></td>
                <td id="operate"></td>
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
            url: '${ctx}/getAnalysisTask.do?',
            type: "get",
//            data: {"keyword": keyword},
            success: function (data) {
                $("#tb").empty();
                for (var i = 0; i < data.length; i++) {
                    var html = "<tr align='center'><td id='id" + i + "'></td>" +
                            "<td id='dataSourceServer" + i + "'></td>" +
                            "<td id='dataTable" + i + "'></td>" +
                            "<td id='addressColumn" + i + "'></td>" +
                            "<td id='totalCount" + i + "'></td>" +
                            "<td id='successCount" + i + "'></td>" +
                            "<td id='failCount" + i + "'><input type='button' value='查看'></td>" +
                            "<td id='schedule" + i + "'></td>" +
                            "<td id='status" + i + "'></td>" +
                            "<td id='startTime" + i + "'></td>" +
                            "<td id='finishTime" + i + "'></td>" +
                            "<td id='operator" + i + "'></td>" +
                            "<td id= 'operate" + i + "'><a>执行</a><br><a>结束</a><br><a>查看匹配任务</a><br><a>新建匹配任务<a/><br><a>查看地址列表</a></td></tr>";
//                            "<td id= 'operate" + i + "'><input type='button' value='执行'><br><input type='button' value='结束'><br><input type='button' value='查看匹配任务'><br><input type='button' value='新建匹配任务'><br><input type='button' value='查看地址列表'></td></tr>";
                    $("#tb").append(html);
                    $("#id" + i).html(data[i].id);
                    $("#dataSourceServer" + i).html(data[i].dataSourceServer);
                    $("#dataTable" + i).html(data[i].dataTable);
                    $("#addressColumn" + i).html(data[i].addressColumn);
                    $("#totalCount" + i).html(data[i].totalCount);
                    $("#successCount" + i).html(data[i].successCount);
                    $("#failCount" + i).html(data[i].failCount);
                    $("#schedule" + i).html(data[i].schedule);
                    $("#status" + i).html(data[i].status);
                    $("#startTime" + i).html(data[i].startTime);
                    $("#finishTime" + i).html(data[i].finishTime);
                    $("#operator" + i).html(data[i].operator);
                }
            }
        })
    });
</script>
</html>
