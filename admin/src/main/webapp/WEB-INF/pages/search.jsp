<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/include/top.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<c:set var="pageTitle1" value="主页"/>
<c:set var="pageTitle2" value="地址标准化"/>
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
                <th>物业类型</th>
                <th>小区别名</th>
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
            <td id="do"></td>
            </tbody>
        </table>
    </div>
    <div style="text-align: center;position: relative;top: 220px;"><img src="${ctx}/static/img/viewmag-256.png" id="img"></div>

</div>
</body>
<%@include file="/WEB-INF/pages/include/bottom.jsp" %>
</body>
<script type="text/javascript">
    function query(keyword) {
        if (keyword == "") {
            bootbox.alert("關鍵字不能爲空，請重新輸入！！");
        } else {
            $("#table1").show();
            $("#img").hide();
            $.ajax({
                url: '${ctx}/query.do?',
                type: "Post",
                data:{"keyword":keyword},
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
                                "<td id='do" + i + "'><a href='#'>小区详情</a>  <a href='#'>楼栋详情</a></td></tr>";
                        $("#tb").append(html);
                        $("#id" + i).html(data[i].id);
                        $("#name" + i).html(data[i].residenceName);
                        $("#addr" + i).html(data[i].residenceAddr);
                        $("#buildingCount" + i).html(data[i].buildingCount);
                        $("#houseCount" + i).html(data[i].houseCount);
                        $("#vp" + i).html(data[i].vp);
                        $("#gp" + i).html(data[i].gp);
                        $("#residenceType" + i).html(data[i].residenceTypeId);
                        $("#aliases" + i).html(data[i].aliases);
                    }
                    }
            })
        }
    }
</script>
</html>
