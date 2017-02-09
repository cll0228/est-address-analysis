<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/include/top.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var="pageTitle1" value="人工编辑"/>
<c:set var="pageTitle2" value="人工地址拆分列表"/>
<%@include file="/WEB-INF/pages/include/header.jsp" %>
<head>
    <title>小区搜索</title>
</head>
<body>
<body>
<div class="container">

    <div class="row" id="radarPrice">
        <div class="col-md-7">
            <!-- BEGIN PORTLET-->
            <div class="portlet light bordered">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="icon-home font-dark"></i>
                        <span class="caption-subject font-dark sbold uppercase">人工拆分</span>
                    </div>

                </div>
                <br class="portlet-body">

                <div class="row">
                    <div class="col-md-12">
                        <div class="table-scrollable">
                            <table class="table table-striped table-hover">
                                <tbody>
                                <tr>
                                    <th>地址来源-服务器</th>
                                    <td id="server">192.168.0.201</td>
                                </tr>
                                <tr>
                                    <th>地址来源-数据库</th>
                                    <td id="data">${dataName}</td>
                                </tr>
                                <tr>
                                    <th>地址来源-表</th>
                                    <td id="table">${tableName}</td>
                                </tr>
                                <tr>
                                    <th>地址来源-ID</th>
                                    <td id="id">${analyMatchDto.addressId}</td>
                                </tr>
                                <tr>
                                    <th>所在任务ID</th>
                                    <td id="taskId">${id}</td>
                                </tr>
                                <tr>
                                    <th>所在任务名称</th>
                                    <td id="taskName">${analyMatchDto.name}</td>
                                </tr>
                                <tr>
                                    <th>原始地址</th>
                                    <td id="address">${analyMatchDto.address}</td>
                                </tr>
                                <tr>
                                    <th>是否可拆分</th>
                                    <c:if test="${analyMatchDto.ifAnalySis == 10}">
                                        <td id="ifAnal">是</td>
                                    </c:if>
                                    <c:if test="${analyMatchDto.ifAnalySis == 20}">
                                        <td id="ifAnal">否</td>
                                    </c:if>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <!-- END PORTLET-->
                    </div>
                </div>
                <span class="label label-info">请修改拆分结果</span></br></br>
                <div class="">
                    <div class="form-horizontal" role="form">
                        <div class="form-group">
                            <label for="roadLane" class="col-sm-2 control-label"><span
                                    class="required"> * </span>路弄</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="roadLane">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="building" class="col-sm-2 control-label">楼栋</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="building">
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="house" class="col-sm-2 control-label">房间</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="house">
                            </div>
                            <div class="col-sm-3">
                                <button type="submit" class="btn btn-default" id="submit">提交</button>
                            </div>
                        </div>

                    </div>
                </div>
            </div>

        </div>

    </div>
    <!-- END PORTLET-->
</div>

</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">温馨提示</h4>
            </div>
            <div class="modal-body">必填项不能为空！！</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>

</body>
<%@include file="/WEB-INF/pages/include/bottom.jsp" %>
</body>
<script type="text/javascript">
    $(function () {
        $("#submit").click(function () {
            var roadLane = $("#roadLane").val();
            if ("" == roadLane || null == roadLane) {
                $('#myModal').modal({
                    keyboard: true
                });
                return;
            }
            bootbox.alert("该功能尚未开放！");
        });

        $("#roadLane").val('${analyMatchDto.roadLane}');
        $("#building").val('${analyMatchDto.building}');
        $("#house").val('${analyMatchDto.house}');
    })
</script>
</html>
