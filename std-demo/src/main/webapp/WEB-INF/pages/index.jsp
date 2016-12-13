<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/include/top.jsp" %>
<%-- 需要在head标签中编写代码，请写在此处--%>
</head>
<c:set var="pageTitle1" value="主页" />
<c:set var="pageTitle2" value="地址解析" />
<%@include file="/WEB-INF/pages/include/header.jsp" %>
<div style="height: 800px;">
<div class="form-group">
    <div class="m-heading-1 border-green m-bordered">
                        <h3>地址解析规则</h3>
                        <p> 待定 </p>
                    </div>
    <div class="input-group input-group-lg">
        <input id="in" type="text" class="form-control" placeholder="请输入要解析的地址">
        <span class="input-group-btn">
            <button class="btn green" type="button" onclick="analysis()">开始解析</button>
        </span>
    </div>
    <!-- /input-group -->
</div>
</div>
<%@include file="/WEB-INF/pages/include/bottom.jsp" %>
</body>
<<script type="text/javascript">
function analysis(){
	var address = $("#in").val();
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
            alert(data.aaa);
        }
    });
	
}
</script>
</html>