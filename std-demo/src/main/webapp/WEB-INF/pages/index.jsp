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
                                             <!-- style="overflow-x: auto; overflow-y: auto; height: 660px; width:100%;" -->
                                            <div>
                                            <table  id="sample_1" class="table table-striped table-bordered table-hover order-column" height="80px">
                                                <thead>
                                                    <tr>
                                                    	<th>地址标准化编码</th>
                                                        <th>区县</th>
                                                        <th>街道</th>
                                                        <th>居委</th>
                                                        <th>路弄</th>
                                                        <th>号</th>
                                                        <th>室</th>
                                                    </tr>
                                                </thead>
                                                <tbody id="tb">
                                                		<td id="bm"></td>
                                                		<td id="qx"></td>
                                                        <td id="jd"></td>
                                                        <td id="jw"></td>
                                                        <td id="ln"></td>
                                                        <td id="h"></td>
                                                        <td id="s"></td>
                                                </tbody>
                                            </table>
                                            </div>
                                        </div>
                                    </div>
					<div class="row" id="homeDetail" style="display:none">
                        <div class="col-md-12">
                            <!-- BEGIN PORTLET-->
                            <div class="portlet light bordered">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <i class="icon-home font-dark"></i>
                                        <span class="caption-subject font-dark sbold uppercase">小区详情</span>
                                    </div>
                      
                                </div>
                                <div class="portlet-body">
                                  
                                    <div class="row">
                                        <div class="col-md-6">
                                            <h4>地图</h4>
                                            这里显示地图</div>
                                        <div class="col-md-4">
                                            <ul class="nav nav-tabs" id="map-keyword">
                                                <li class="active">
                                                    <a href="#tab_1" data-keyword="地铁|公交" data-toggle="tab">交通</a>
                                                </li>
                                                <li>
                                                    <a href="#tab_2" data-toggle="tab">教育</a>
                                                </li>
                                                <li>
                                                    <a href="#tab_3" data-toggle="tab">医疗</a>
                                                </li>
                                                <li>
                                                    <a href="#tab_4" data-toggle="tab">购物</a>
                                                </li>
                                                <li>
                                                    <a href="#tab_5" data-toggle="tab">生活</a>
                                                </li>
                                            </ul>
                                            <div style="dispaly:block">星级</div>
                                            <div class="clearfix">
                                                <a href="javascript:;" class="btn default active"> 0.5km </a>
                                                <a href="javascript:;" class="btn default"> 1km </a>
                                                <a href="javascript:;" class="btn default"> 1.5km </a>
                                                <a href="javascript:;" class="btn default"> 2km </a>
                                                <a href="javascript:;" class="btn default"> 2.5km </a>
                                            </div>
                                            <div class="tab-content">
                                                <div class="tab-pane active" id="tab_1">
                                                    <div class="row" style="display:block">
                                                        <div class="col-md-4">
                                                            <h5 align="center" id="poiKind"></h5>
                                                    </div>
                                                    <div class="row">
                                                        </div>
                                                        <div class="col-md-4">
                                                            <h5 align="center">其他</h5>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-md-3">
                                            <h4>小区信息</h4>
                                            这里显示小区详情，嵌套表格</div>
                                    </div>
                                    <div class="row">
                                    	<div class="col-md-12">
                        					<h4>小区价格走势</h4>
                        					这里显示小区价格走势
                        				</div>
                                 	</div>
                                </div>
                            </div>
                            <!-- END PORTLET-->
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
        	$("#tb").empty();
            for(var i = 0;i < data.length; i++) {
				var html = "<tr><td id='bm"+i+"'></td><td id='qx"+i+"'></td><td id='jd"+i+"'></td><td id='jw"+i+"'></td><td id='ln"+i+"'></td><td id='h"+i+"'></td><td id='s"+i+"'></td></tr>";
				$("#tb").append(html);
				$("#qx"+i).html(data[i].qx);
            	$("#jd"+i).html(data[i].jd);
            	$("#jw"+i).html(data[i].jw);
            	$("#ln"+i).html(data[i].ln);
            	$("#h"+i).html(data[i].h);
            	$("#s"+i).html(data[i].s);
            	$("#bm"+i).html(data[i].bm);
			}
            $("#poiKind").html(data[0].poiList[0].poiKind);
			
            if(data[0].f=="1") {
            	$("#ts").html("地址标准化成功").css("color","green");
            	$("#homeDetail").css('display','block');
            } else if(data[0].f=="2") {
            	$("#ts").html("区县不对应").css("color","red");
            	$("#homeDetail").css('display','none');
            } else if(data[0].f=="3") {
            	$("#ts").html("地址不存在").css("color","red");
            	$("#homeDetail").css('display','none');
            } else if(data[0].f=="4") {
            	$("#ts").html("路弄未找到").css("color","red");
            	$("#homeDetail").css('display','none');
            } else if(data[0].f=="5") {
            	$("#ts").html("道路不存在").css("color","red");
            	$("#homeDetail").css('display','none');
            } else if(data[0].f=="6") {
            	$("#ts").html("房间号未找到").css("color","red");
            	$("#homeDetail").css('display','none');
            } else if(data[0].f=="7") {
            	$("#ts").html("楼栋未找到").css("color","red");
            	$("#homeDetail").css('display','none');
            }
        }
    });
	}
}
</script>
</html>