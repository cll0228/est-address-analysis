<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/include/top.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var="pageTitle1" value="列表"/>
<c:set var="pageTitle2" value="新建任务"/>
<%@include file="/WEB-INF/pages/include/header.jsp" %>
<head>
    <title>新建任务</title>
</head>
<body>
<body>
<div class="container">
    <p align="center" style="font-size:30px"><b>新建任务</b></p>
    <br>
    <div class="row" id="radarPrice">
        <div class="col-md-12">
            <div class="portlet light form-fit bordered">
                <div class="portlet-body form">
                    <form action="" class="form-horizontal form-row-seperated">
                        <div class="form-body">
                            <div class="form-group">
                                <label class="control-label col-md-4">数据服务器</label>
                                <div class="col-md-4">
                                    <select id="slist" class="bs-select form-control" style="width:320px;">
                                    </select>
                           <%--         <c:forEach items="${list}" var="list">
                                        <tr>
                                            <td>${list}</td>
                                        </tr>
                                    </c:forEach>--%>
                                </div>
                                <div class="col-md-4">
                                    <input type="button" onclick="addServer()" value="添加服务器">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4">表名</label>
                                <div class="col-md-4">
                                    <input type="text" class="form-control input-large" id="tableName">
                                </div>
                                <div class="col-md-4">
                                    <span>约定，表有ID字段，整型，主键</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4">地址列</label>
                                <div class="col-md-4">
                                    <input type="text" class="form-control input-large" id="addressColumn">
                                </div>
                                <div class="col-md-4">
                                    <span>如address，类型字符串</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4">名称</label>
                                <div class="col-md-4">
                                    <input type="text" class="form-control input-large" id="taskName">
                                </div>
                                <div class="col-md-4">
                                    <span>设置一个有意义，好记的名字来识别任务</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4"></label>
                                <div class="col-md-4">
                                    <input type="checkbox"/>与标准库匹配
                                </div>
                                <div class="col-md-4">
                                    <span></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-4"></label>
                                <div class="col-md-4">
                                    <input type="button" value="开始执行">
                                </div>
                                <div class="col-md-4">
                                    <span></span>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
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
                                <label >数据库类型</label>
                            </label>
                            <div class="col-md-6">
                                <label for="newMysql">mysql</label>
                                <input type="radio" name="dbtype" id="newMysql" checked>
                                <label for="newMssql">mssql</label>
                                <input type="radio" name="dbtype" id="newMssql">
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
</div>
</body>
<%@include file="/WEB-INF/pages/include/bottom.jsp" %>
</body>
<script type="text/javascript">
    $(function () {
        $.ajax({
            url: '${ctx}/getNewTaskInfo.do?',
            type: "get",
            success: function (data) {
//                $("#tableName").val(data.tableName);
//                $("#addressColumn").val(data.addressColumn);
//                $("#taskName").val(data.taskName);
                var selectid=document.getElementById("slist");
                selectid.options.length=0;
                for(var i=0;i<data.serverList.length;i++){
                    selectid[i]=new Option(data.serverList[i],i);
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
        var type;
        if(document.getElementById("newMysql").checked == true){
            type = 1;
        }
        if(document.getElementById("newMssql").checked == true){
            type = 2;
        }

        Ewin.confirm({ message: "确认要添加服务器数据吗？" }).on(function (e) {
            if (!e) {
                return;
            }
            $.ajax({
                url: '${ctx}/addNewServer.do?',
                type: "POST",
                data: {"server":$("#newServerIp").val(),"type": type,"username":$("#newUserName").val(),"password":$("#newPassword").val(),"alias":$("#newAlias").val()},
                success: function (data) {
                    if (data.status == "success") {
                        toastr.success('数据提交成功');
                        $("#slist").empty();
                        var selectid=document.getElementById("slist");
                        selectid.options.length=0;
                        for(var i=0;i<data.serverList.length;i++){
                            selectid[i]=new Option(data.serverList[i],i);
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

</script>
</html>
