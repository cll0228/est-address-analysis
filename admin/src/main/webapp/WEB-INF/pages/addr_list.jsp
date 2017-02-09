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
        地址&nbsp;<input type="text" id="addrLike"/>&nbsp;
        解析结果&nbsp;<select style="width: 100px; height: 26px;" id="analyStatus">
        <option value="40">全部</option>
        <option value="30">未开始</option>
        <option value="20">成功</option>
        <option value="10">失效</option>
    </select>&nbsp;
        匹配结果&nbsp;<select style="width: 100px; height: 26px;" id="matchStatus">
        <option value="40">全部</option>
        <option value="30">未开始</option>
        <option value="20">成功</option>
        <option value="10">失效</option>
    </select>&nbsp;
        <input type="button" value="查找" onclick="query();"/>
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
                <th>解析状态</th>
                <th>解析时间</th>
                <th>路弄</th>
                <th>楼栋</th>
                <th>房屋</th>
                <th>匹配状态</th>
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
            <td id="ifAnalySis"></td>
            <td id="analTime"></td>
            <td id="roadLane"></td>
            <td id="building"></td>
            <td id="house"></td>
            <td id="ifMatch"></td>
            <td id="matchTime"></td>
            <td id="do"><a href="#"></a></td>
            </tbody>
        </table>
        <div>
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
    var name = null;//所在任务名称
    var id = null;//所在任务id
    var totalPage = null;
    var startPage = 1;
    var dataName = null;
    var tableName = null;
    $(function () {
        $.ajax({
            url: '${ctx}/addrQuery.do?analySisTaskId=' + 1,
            type: "get",
//            data: {"keyword": keyword},
            success: function (data) {
                $("#tb").empty();
                for (var i = 0; i < data.length; i++) {
                    name = data[i].name;
                    id = data[i].id;
                    totalPage = data[i].totalPage;
                    dataName =  data[i].dataName;
                    tableName =  data[i].tableName;
                    var html = "<tr><td id='addressId" + i + "'></td>" +
                            "<td id='analTaskId" + i + "'></td>" +
                            "<td id='dataName" + i + "'></td>" +
                            "<td id='table_" + i + "'></td>" +
                            "<td id='address" + i + "' width='170;'></td>" +
                            "<td id='ifAnalySis" + i + "'></td>" +
                            "<td id='analTime" + i + "'></td>" +
                            "<td id='roadLane" + i + "' width='100'></td>" +
                            "<td id='building" + i + "'></td>" +
                            "<td id='house" + i + "'></td>" +
                            "<td id='ifMatch" + i + "'></td>" +
                            "<td id='matchTime" + i + "'></td>" +
                            "<td id= '" + i + "' width='150'><a href='javascript:void(0)' onclick='toEdit(" + data[i].addressId + ");'>手动解析</a>&nbsp;<a href='#'>执行匹配</a>  </td></tr>";
                    $("#tb").append(html);
                    $("#addressId" + i).html(data[i].addressId);
                    $("#analTaskId" + i).html(data[i].analTaskId);
                    $("#dataName" + i).html(data[i].dataName);
                    $("#table_" + i).html(data[i].tableName);
                    $("#address" + i).html(data[i].address);
                    if (data[i].ifAnalySis == 10) {
                        $("#ifAnalySis" + i).html("成功");
                    } else {
                        $("#ifAnalySis" + i).html("失败");
                    }
                    $("#analTime" + i).html(data[i].analTime);
                    $("#roadLane" + i).html(data[i].roadLane);
                    $("#building" + i).html(data[i].building);
                    $("#house" + i).html(data[i].house);
                    $("#ifMatch" + i).html(data[i].ifMatch);
                    $("#matchTime" + i).html(data[i].matchTime);
                }
                //总页数
                $("#totalPage").val(totalPage);
            }
        })

    });

    function toEdit(addressId) {
        var dataName = $("#dataName0").html();
        var tabelName = $("#table_0").html();
        location.href = "${ctx}/editAddr.do?addressId=" + addressId + "&dataName=" + dataName + "&tableName=" + tabelName + "&name=" + name + "&id=" + id;
    }

    function addPage() {
        if (startPage == totalPage) {
            bootbox.alert("已是最后一页！！");
            return;
        }
        startPage = startPage + 1;
        $("#page").val(startPage);
        $.ajax({
            url: '${ctx}/addrQuery.do?analySisTaskId=' + 1 + "&page=" + startPage,
            type: "get",
//            data: {"keyword": keyword},
            success: function (data) {
                $("#tb").empty();
                for (var i = 0; i < data.length; i++) {
                    name = data[i].name;
                    id = data[i].id;
                    totalPage = data[i].totalPage;
                    var html = "<tr><td id='addressId" + i + "'></td>" +
                            "<td id='analTaskId" + i + "'></td>" +
                            "<td id='dataName" + i + "'></td>" +
                            "<td id='table_" + i + "'></td>" +
                            "<td id='address" + i + "' width='170;'></td>" +
                            "<td id='ifAnalySis" + i + "'></td>" +
                            "<td id='analTime" + i + "'></td>" +
                            "<td id='roadLane" + i + "' width='100'></td>" +
                            "<td id='building" + i + "'></td>" +
                            "<td id='house" + i + "'></td>" +
                            "<td id='ifMatch" + i + "'></td>" +
                            "<td id='matchTime" + i + "'></td>" +
                            "<td id= '" + i + "' width='150'><a href='javascript:void(0)' onclick='toEdit(" + data[i].addressId + ");'>手动解析</a>&nbsp;<a href='#'>执行匹配</a>  </td></tr>";
                    $("#tb").append(html);
                    $("#addressId" + i).html(data[i].addressId);
                    $("#analTaskId" + i).html(data[i].analTaskId);
                    $("#dataName" + i).html(data[i].dataName);
                    $("#table_" + i).html(data[i].tableName);
                    $("#address" + i).html(data[i].address);
                    if (data[i].ifAnalySis == 10) {
                        $("#ifAnalySis" + i).html("成功");
                    } else {
                        $("#ifAnalySis" + i).html("失败");
                    }
                    $("#analTime" + i).html(data[i].analTime);
                    $("#roadLane" + i).html(data[i].roadLane);
                    $("#building" + i).html(data[i].building);
                    $("#house" + i).html(data[i].house);
                    $("#ifMatch" + i).html(data[i].ifMatch);
                    $("#matchTime" + i).html(data[i].matchTime);
                }
            }
        })
    }

    function reducePage() {
        if (startPage == 1) {
            bootbox.alert("已是最前一页！！");
            return;
        }
        startPage = startPage - 1;
        $("#page").val(startPage);
        $.ajax({
            url: '${ctx}/addrQuery.do?analySisTaskId=' + 1 + "&page=" + startPage,
            type: "get",
//            data: {"keyword": keyword},
            success: function (data) {
                $("#tb").empty();
                for (var i = 0; i < data.length; i++) {
                    name = data[i].name;
                    id = data[i].id;
                    totalPage = data[i].totalPage;
                    var html = "<tr><td id='addressId" + i + "'></td>" +
                            "<td id='analTaskId" + i + "'></td>" +
                            "<td id='dataName" + i + "'></td>" +
                            "<td id='table_" + i + "'></td>" +
                            "<td id='address" + i + "' width='170;'></td>" +
                            "<td id='ifAnalySis" + i + "'></td>" +
                            "<td id='analTime" + i + "'></td>" +
                            "<td id='roadLane" + i + "' width='100'></td>" +
                            "<td id='building" + i + "'></td>" +
                            "<td id='house" + i + "'></td>" +
                            "<td id='ifMatch" + i + "'></td>" +
                            "<td id='matchTime" + i + "'></td>" +
                            "<td id= '" + i + "' width='150'><a href='javascript:void(0)' onclick='toEdit(" + data[i].addressId + ");'>手动解析</a>&nbsp;<a href='#'>执行匹配</a>  </td></tr>";
                    $("#tb").append(html);
                    $("#addressId" + i).html(data[i].addressId);
                    $("#analTaskId" + i).html(data[i].analTaskId);
                    $("#dataName" + i).html(data[i].dataName);
                    $("#table_" + i).html(data[i].tableName);
                    $("#address" + i).html(data[i].address);
                    if (data[i].ifAnalySis == 10) {
                        $("#ifAnalySis" + i).html("成功");
                    } else {
                        $("#ifAnalySis" + i).html("失败");
                    }
                    $("#analTime" + i).html(data[i].analTime);
                    $("#roadLane" + i).html(data[i].roadLane);
                    $("#building" + i).html(data[i].building);
                    $("#house" + i).html(data[i].house);
                    $("#ifMatch" + i).html(data[i].ifMatch);
                    $("#matchTime" + i).html(data[i].matchTime);
                }
            }
        })
    }

    function query() {
        var analyStatus = $("#analyStatus").val();
        var matchStatus = $("#matchStatus").val();
        var addrLike = $("#addrLike").val();
        if("" == addrLike){
            bootbox.alert("请输入查找的地址！！");
            return;
        }

    }
</script>
</html>
