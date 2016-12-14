<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/include/top.jsp" %>
<%-- 需要在head标签中编写代码，请写在此处--%>
</head>
<c:set var="pageTitle1" value="主页" />
<c:set var="pageTitle2" value="地址标准化" />
<%@include file="/WEB-INF/pages/include/header.jsp" %>
<div style="height: 800px;">
<div class="form-group">
    <!-- <div class="m-heading-1 border-green m-bordered">
                        <h3>地址标准化</h3>
                    </div> -->
    <div class="input-group input-group-lg">
        <input id="in" type="text" class="form-control" placeholder="请输入要标准化的地址">
        <span class="input-group-btn">
            <button class="btn green" type="button" onclick="analysis()">开始标准化地址</button>
        </span>
    </div>
    <!-- /input-group -->
</div>

<div class="clearfix">
                                        <div class="panel panel-success">
                                            <!-- Default panel contents -->
                                            <div class="panel-heading">
                                                <h3 class="panel-title">地址标准化结果</h3>
                                            </div>
                                            <div class="panel-body">
                                                <table height="30px">
                                                	<tr>
                                                		<td id="ts"></td>
                                                	</tr>
                                                </table>
                                            </div>
                                            <!-- Table -->
                                            <table class="table" height="80px">
                                                <thead>
                                                    <tr>
                                                    	<th width="14%">地址标准化编码</th>
                                                        <th width="14%">区县</th>
                                                        <th width="14%">街道</th>
                                                        <th width="22%">居委</th>
                                                        <th width="14%">路弄</th>
                                                        <th width="10%">号</th>
                                                        <th width="10%">室</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="tb">
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>

</div>
<%@include file="/WEB-INF/pages/include/bottom.jsp" %>
</body>
<script type="text/javascript">
function analysis(){
	var address = $("#in").val();
	if(address=="") {
		bootbox.alert("地址不能为空，请重新输入");
	} else {
		$.ajax({
        url: '${ctx}/analysis?address=' + address,
        type: "GET",
        //data: param,
        error: function (jqXHR, textStatus, errorThrown) {
            var ex = {};
            try {
                ex = JSON.parse(jqXHR.responseText);
            } catch (e) {
                //ex.exception = e;
            }
        },
        success: function (data) {
        	var html = "";
            for(var i = 0;i < data.size(); i++) {
				html += "<tr><td id='bm"+i+"'></td><td id='qx"+i+"'></td><td id='jd"+i+"'></td><td id='jw"+i+"'></td><td id='ln"+i+"'></td><td id='h"+i+"'></td><td id='s"+i+"'></td></tr>";
				$("#qx"+i).html(data[i].qx);
            	$("#jd"+i).html(data[i].jd);
            	$("#jw"+i).html(data[i].jw);
            	$("#ln"+i).html(data[i].ln);
            	$("#h"+i).html(data[i].h);
            	$("#s"+i).html(data[i].s);
            	$("#bm"+i).html(data[i].bm);
			}
            if(data.f=="1") {
            	$("#ts").html("地址标准化成功").css("color","green");
            } else if(data.f=="2") {
            	$("#ts").html("区县不对应").css("color","red");
            } else if(data.f=="3") {
            	$("#ts").html("地址不存在").css("color","red");
            } else if(data.f=="4") {
            	$("#ts").html("未查到资源").css("color","red");
            } else if(data.f=="5") {
            	$("#ts").html("道路不存在").css("color","red");
            } else if(data.f=="6") {
            	$("#ts").html("房间号未找到").css("color","red");
            } else if(data.f=="7") {
            	$("#ts").html("楼栋未找到").css("color","red");
            }
        }
    });
	}
}
</script>
</html>