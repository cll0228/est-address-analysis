<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/include/top.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var="pageTitle1" value="列表"/>
<c:set var="pageTitle2" value="解析任务管理"/>
<%@include file="/WEB-INF/pages/include/header.jsp" %>
<head>
    <title>数据源服务器管理</title>
</head>
<body>
<body>
<div class="container">
    <p align="center" style="font-size:30px"><b>数据源服务器管理</b></p>
    <br>
    <div align="right">
        <input type="button" onclick="addServer()" value="添加服务器" />
    </div>
    <br>
    <div style="display: block" id="table1">
        <table id="residence" class="table table-striped table-bordered table-hover order-column" height="80px">
            <thead>
            <tr>
                <th>服务器IP</th>
                <th>用户名</th>
                <th>密码</th>
                <th>别名</th>
                <th>添加人员</th>
                <th>添加时间</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="tb">
                <td id="serverIp"></td>
                <td id="userName"></td>
                <td id="password"></td>
                <td id="alias"></td>
                <td id="addStaff"></td>
                <td id="createTime"></td>
                <td id="operate"></td>
            </tbody>
        </table>
    </div>

    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title">添加服务器</h4>
                </div>
                <form action="#" id="form_datasource_add" class="form-horizontal">
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="control-label col-md-4">
                                <label for="newServerIp">服务器IP<span class="required"> * </span></label>
                            </label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <i class="fa" style="z-index:999"></i>
                                    <input type="text" name="newServerIp" class="form-control" id="newServerIp">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-4">
                                <label for="newUserName">用户名<span class="required"> * </span></label>
                            </label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <i class="fa" style="z-index:999"></i>
                                    <input type="text" name="newUserName" class="form-control" id="newUserName">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-4">
                                <label for="newPassword">密码<span class="required"> * </span></label>
                            </label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <i class="fa" style="z-index:999"></i>
                                    <input type="text" name="newPassword" class="form-control" id="newPassword">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-4">
                                <label for="newAlias">别名</label>
                            </label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                <input type="text" name="newAlias" class="form-control" id="newAlias">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                        <button type="button" id="btn_submit" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-floppy-disk"></span>保存</button>

                            <%--<button type="button" class="btn green" id="btn_submit">提交</button>--%>
                            <%--<button type="button" data-dismiss="modal" class="btn dark btn-outline">取消</button>--%>
                    </div>
                </form>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>

    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal1" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                    <h4 class="modal-title">编辑服务器</h4>
                </div>
                <form action="#" id="form_datasource_edit" class="form-horizontal">
                    <div class="modal-body">
                        <div class="form-group">
                            <label class="control-label col-md-4">
                                <label for="sip">服务器IP<span class="required"> * </span></label>
                            </label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <i class="fa" style="z-index:999"></i>
                                    <input type="text" name="sip" class="form-control" id="sip">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-4">
                                <label for="uname">用户名<span class="required"> * </span></label>
                            </label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <i class="fa" style="z-index:999"></i>
                                    <input type="text" name="uname" class="form-control" id="uname">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-4">
                                <label for="pword">密码<span class="required"> * </span></label>
                            </label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <i class="fa" style="z-index:999"></i>
                                    <input type="text" name="pword" class="form-control" id="pword">
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-4">
                                <label for="bieming">别名</label>
                            </label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <input type="text" name="bieming" class="form-control" id="bieming">
                                </div>
                            </div>
                        </div>
                        <div class="form-group" style="display:none">
                            <label class="control-label col-md-4">
                                <label for="id">id</label>
                            </label>
                            <div class="col-md-6">
                                <div class="input-icon right">
                                    <input type="text" name="id" class="form-control" id="id">
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span>关闭</button>
                        <button type="button" id="btn_submit1" class="btn btn-primary" data-dismiss="modal"><span class="glyphicon glyphicon-floppy-disk"></span>保存</button>
                    </div>
                </form>
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
            url: '${ctx}/getDataSourceList.do?',
            type: "get",
            success: function (data) {
                $("#tb").empty();
                for (var i = 0; i < data.length; i++) {
                    var html = "<tr><td id='serverIp" + i + "'></td>" +
                            "<td id='userName" + i + "'></td>" +
                            "<td id='password" + i + "'></td>" +
                            "<td id='alias" + i + "'></td>" +
                            "<td id='addStaff" + i + "'></td>" +
                            "<td id='createTime" + i + "'></td>" +
                            "<td id= 'operate" + i + "'><a href='javascript:void(0)' onclick=editServer('" + data[i].id + "','" + data[i].serverIp + "','" + data[i].userName + "','" + data[i].password + "','" + data[i].alias + "');>编辑  </a><a href='javascript:void(0)'>删除</a></td></tr>";
                    $("#tb").append(html);
                    $("#serverIp" + i).html(data[i].serverIp);
                    $("#userName" + i).html(data[i].userName);
                    $("#password" + i).html(data[i].password);
                    $("#alias" + i).html(data[i].alias);
                    $("#addStaff" + i).html(data[i].addStaff);
                    $("#createTime" + i).html(data[i].createTime);
                }
            }
        })
    });

    toastr.options.positionClass = 'toast-top-center';
    function addServer(){
        $('#myModal').modal({
            backdrop: false,
            show: true
        })
    }

    $("#btn_submit").click(function(){
        if (!$("#form_datasource_add").valid()) {
            return;
        }

        Ewin.confirm({ message: "确认要添加服务器数据吗？" }).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                url: '${ctx}/addServer.do?',
                type: "POST",
                data: $('#form_datasource_add').serialize(),
                success: function (data) {
                    if (data.status == "success") {
                        toastr.success('提交数据成功');
                        $("#tb").empty();
                        var serverList = data.dbServerList;
                        for (var i = 0; i < serverList.length; i++) {
                            var html = "<tr><td id='serverIp" + i + "'></td>" +
                                    "<td id='userName" + i + "'></td>" +
                                    "<td id='password" + i + "'></td>" +
                                    "<td id='alias" + i + "'></td>" +
                                    "<td id='addStaff" + i + "'></td>" +
                                    "<td id='createTime" + i + "'></td>" +
                                    "<td id= 'operate" + i + "'><a href='javascript:void(0)' onclick=editServer('" + serverList.id + "','" + serverList.serverIp + "','" + serverList.userName + "','" + serverList.password + "','" + serverList.alias + "');>编辑  </a><a href='javascript:void(0)'>删除</a></td></tr>";
                            $("#tb").append(html);
                            $("#serverIp" + i).html(serverList.serverIp);
                            $("#userName" + i).html(serverList.userName);
                            $("#password" + i).html(serverList.password);
                            $("#alias" + i).html(serverList.alias);
                            $("#addStaff" + i).html(serverList.addStaff);
                            $("#createTime" + i).html(serverList.createTime);
                        }
                    }

                },
                error: function () {
                    toastr.error('Error');
                },
                complete: function () {

                }

            });
        });
    });

    function editServer(id,serverIp,userName,password,alias){
        $("#sip").val(serverIp);
        $("#uname").val(userName);
        $("#pword").val(password);
        $("#bieming").val(alias);
        $("#id").val(id);
        $('#myModal1').modal({
            backdrop: false,
            show: true
        })
    }

    $("#btn_submit1").click(function(){
        if (!$("#form_datasource_edit").valid()) {
            return;
        }

        Ewin.confirm({ message: "确认要更新服务器信息吗？" }).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                url: '${ctx}/editServer.do?',
                type: "POST",
                data: $('#form_datasource_edit').serialize(),
                success: function (data) {
                    if (data.status == "success") {
                        alert("OK!");

                    }

                },
                error: function () {
                    toastr.error('Error');
                },
                complete: function () {

                }

            });
        });
    });

    function isNull( str ){
        if ( str == "" ) return true;
        var regu = "^[ ]+$";
        var re = new RegExp(regu);
        return re.test(str);
    }
</script>
</html>
