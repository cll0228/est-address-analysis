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
    <div>
        地址&nbsp;<input type="text" id="name" />&nbsp;
        解析结果&nbsp;<select  style="width: 100px; height: 26px;">
        <option value="all">全部</option>
        <option value="notStart">未开始</option>
        <option value="success">成功</option>
        <option value="lose">失效</option>
    </select>&nbsp;
        匹配结果&nbsp;<select  style="width: 100px; height: 26px;">
        <option value="all">全部</option>
        <option value="notStart">未开始</option>
        <option value="success">成功</option>
        <option value="lose">失效</option>
    </select>&nbsp;
        <input type="button" value="查找" />
    </div>
    <div style="display: block" id="table1">
        <table id="residence" class="table table-striped table-bordered table-hover order-column" height="80px">
            <thead>
            <tr>
                <th>地址ID</th>
                <th>解析任务ID</th>
                <th>所在数据库</th>
                <th>所在表</th>
                <th>原地址</th>
                <th>是否解析成功</th>
                <th>解析时间</th>
                <th>路弄</th>
                <th>楼栋</th>
                <th>房屋</th>
                <th>是否匹配成功</th>
                <th>匹配时间</th>
                <th>操作</th>

            </tr>
            </thead>
            <tbody id="tb">
            <td id="addressId"></td>
            <td id="analTaskId"></td>
            <td id="data"></td>
            <td id="table"></td>
            <td id="address"></td>
            <td id="ifSuccess"></td>
            <td id="analTime"></td>
            <td id="roadLane"></td>
            <td id="building"></td>
            <td id="house"></td>
            <td id="ifMatch"></td>
            <td id="matchTime"></td>
            <td id="do"><a href="#"></a></td>
            </tbody>
        </table>
        <div >
            <button type="button" class="btn btn-primary" onclick="query($('#in').val())">返回任务管理页面</button>
            <ul class="pager" style="position: relative;left: 36.5%;top: -61px;">
                <li>第 <input id="page" style="width:30px;" value="1"> 页</li>
                <li> 共 <input id="totalPage" style="width:30px;"> 页</li>
                <li><a href="javascript:void(0)" onclick="reducePage();">上一页</a></li>
                <li><a href="javascript:void(0)" onclick="addPage();">下一页</a></li>
            </ul>
        </div>
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
                    var html = "<tr><td id='addressId" + i + "'></td>" +
                            "<td id='analTaskId" + i + "'></td>" +
                            "<td id='data" + i + "'></td>" +
                            "<td id='table" + i + "'></td>" +
                            "<td id='address" + i + "'></td>" +
                            "<td id='ifSuccess" + i + "'></td>" +
                            "<td id='analTime" + i + "'></td>" +
                            "<td id='roadLane" + i + "'></td>" +
                            "<td id='building" + i + "'></td>" +
                            "<td id='house" + i + "'></td>" +
                            "<td id='ifMatch" + i + "'></td>" +
                            "<td id='matchTime" + i + "'></td>" +
                            "<td id= " + i + "><a href='javascript:void(0)' onclick='toEdit(" + data[i].id + ");'>手动解析</a>&nbsp;<a href='#'>执行匹配</a>  </td></tr>";
                    $("#tb").append(html);
                    $("#addressId" + i).html(data[i].id);
                    $("#analTaskId" + i).html(data[i].address);
                    $("#data" + i).html(data[i].roadLane);
                    $("#table" + i).html(data[i].buildingNo);
                    $("#address" + i).html(data[i].houseNo);
                    $("#ifSuccess" + i).html(data[i].parsedVersion);
                    $("#analTime" + i).html(data[i].parsedTime);
                    $("#roadLane" + i).html(data[i].status);
                    $("#building" + i).html(data[i].status);
                    $("#house" + i).html(data[i].status);
                    $("#ifMatch" + i).html(data[i].status);
                    $("#matchTime" + i).html(data[i].status);
                }
            }
        })
    });

    function toEdit(id) {
        location.href = "${ctx}/editAddr.do?"
    }
</script>
</html>
