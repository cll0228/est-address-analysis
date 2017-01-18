<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/WEB-INF/pages/include/top.jsp" %>
<%-- 需要在head标签中编写代码，请写在此处--%>
    <style type="text/css">
        /* star */
        #star {
            position: relative;
            width: 600px;
            margin: 10px auto;
            height: 20px;
        }

        #star ul {
            margin: -28px 85px;
        }

        #star li {
            float: left;
            width: 24px;
            cursor: pointer;
            text-indent: -9999px;
            background: url(${ctx}/static/img/star.png) no-repeat;
        }

        #star strong {
            color: #f60;
            padding-left: 10px;
        }

        #star li.on {
            background-position: 0 -28px;
        }
        #table1 tbody td {
            font-size:12px;
            padding-left:30px;
        }
        #table1 tbody th {
            font-size:12px;
            font-weight:normal;
            width: 80px;
        }
        hr {
            border: 0;
            border-top: 0px solid #eee;
            border-bottom:0;
        }
        .loading{
			width:160px;
			height:56px;
			position: absolute;
			top:50%;
			left:44.5%;
			line-height:56px;
			color:#fff;
			padding-left:60px;
			font-size:15px;
			background: #000 url(${ctx}/static/img/loader.gif) no-repeat 10px 50%;
			opacity: 0.7;
			z-index:9999;
			-moz-border-radius:20px;
			-webkit-border-radius:20px;
			border-radius:20px;
			filter:progid:DXImageTransform.Microsoft.Alpha(opacity=70);
		}
    </style>
</head>
<c:set var="pageTitle1" value="主页"/>
<c:set var="pageTitle2" value="地址标准化"/>
<%@include file="/WEB-INF/pages/include/header.jsp" %>
<div style="100%;">
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
    <!-- /input-group -->


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
                <table id="sample_1" class="table table-striped table-bordered table-hover order-column" height="80px" style="word-break:break-all; word-wrap:break-all;">
                    <thead>
                    <tr>
                        <th>统一标准地址编号</th>
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
    
    
    
    
    
    <div class="row" id="radarPrice" style="display:none">
        <div class="col-md-12">
            <!-- BEGIN PORTLET-->
            <div class="portlet light bordered">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="icon-home font-dark"></i>
                        <span class="caption-subject font-dark sbold uppercase">房屋信息</span>
                    </div>

                </div>
                <div class="portlet-body">

                    <div class="row">
                    	<div class="col-md-6">
                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <tbody>
                                    <tr>
                                        <th width="30%"> 小区名称 </th>
                                        <td id="residenceName"></td>
                                    </tr>
                                    <tr>
                                        <th> 小区地址 </th>
                                        <td id="residenceAddr"></td>
                                    </tr>
                                    <tr>
                                        <th> 小区类型 </th>
                                        <td id="propertyType"></td>
                                    </tr>
                                    <tr>
                                        <th> 总楼栋数 </th>
                                        <td id="buildingCount"></td>
                                    </tr>
                                    <tr>
                                        <th> 总户数 </th>
                                        <td id="houseCount"></td>
                                    </tr>
                                    <tr>
                                        <th> 竣工年份 </th>
                                        <td id="accomplishDate2"></td>
                                    </tr>
                                    <tr>
                                        <th> 容积率 </th>
                                        <td id="vp"></td>
                                    </tr>
                                    <tr>
                                        <th> 绿化率 </th>
                                        <td id="gp"></td>
                                    </tr>
                                    <tr>
                                        <th> 总建筑面积 </th>
                                        <td id="totalArea"></td>
                                    </tr>
                                    <tr>
                                        <th> 小区别名 </th>
                                        <td id="aliases"></td>
                                    </tr>
                                    <tr>
                                        <th> 小区房型 </th>
                                        <td id="houseType"></td>
                                    </tr>
                                    <!-- <tr>
                                        <th> 小区四至 </th>
                                        <td id="fourTo"></td>
                                    </tr>-->
                                    </tbody>
                                </table>
                            </div>
                            <!-- END PORTLET-->
                        </div>
                    
                        <div class="col-md-6">
                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <tbody>
                                    <tr id="jinditie">
                                        <th width="30%"> 近地铁 <img src="${ctx}/static/img/sh_metro.jpg" height="22px" width="22px"/></th>
                                        <td id="metro"></td>
                                    </tr>
                                    <tr id="jinxuequ">
                                        <th> 近学区 <img src="${ctx}/static/img/graduation.png" height="22px" width="22px"/></th>
                                        <td id="school"></td>
                                    </tr>
                                    <tr>
                                        <th> 面积 </th>
                                        <td id="area"></td>
                                    </tr>
                                    <tr>
                                        <th> 朝向 </th>
                                        <td id="towards"></td>
                                    </tr>
                                    <tr>
                                        <th> 房型 </th>
                                        <td id="roomType"></td>
                                    </tr>
                                    <tr>
                                        <th> 竣工年份</th>
                                        <td id="accomplishDate"></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        
                    </div>
                </div>
            </div>
        </div>
        <!-- END PORTLET-->
    </div>
    
    
    
    
    <div id="static" class="modal fade" tabindex="-1" data-backdrop="static" data-keyboard="false">
                                        <div id="loading" class="loading">处理中...</div>
                                    </div>
                                    
    <div id="initPage" class="modal fade" tabindex="-1" data-backdrop="initPage" data-keyboard="false">
                                        <div id="loading" class="loading">初始化...</div>
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
                        <div class="col-md-8" id="map" style="height: 450px;size: 350px;border: 10px;" >
                        </div>
                        <div class="col-md-4" id="poi-area">
                            <ul class="nav nav-tabs" id="map-keyword">
                                <li id="one1" class="active">
                                    <a href="#tab_1" onclick="setTab('one',1,'')" data-toggle="tab">交通</a>
                                </li>
                                <li id="one2">
                                    <a href="#tab_2" onclick="setTab('one',2,'')" data-toggle="tab">教育</a>
                                </li>
                                <li id="one3">
                                    <a href="#tab_3" onclick="setTab('one',3,'')" data-toggle="tab">医疗</a>
                                </li>
                                <li id="one4">
                                    <a href="#tab_4" onclick="setTab('one',4,'')" data-toggle="tab">购物</a>
                                </li>
                                <li id="one5">
                                    <a href="#tab_5" onclick="setTab('one',5,'')" data-toggle="tab">生活</a>
                                </li>
                                <li id="one6">
                                    <a href="#tab_6" onclick="setTab('one',6,'')" data-toggle="tab">休闲</a>
                                </li>
                                <li id="one7">
                                    <a href="#tab_7" onclick="setTab('one',7,'')" data-toggle="tab">健身</a>
                                </li>
                                <li id="one8">
                                    <a href="#tab_8" onclick="setTab('one',8,'')" data-toggle="tab">公园</a>
                                </li>
                                <li id="one9">
                                    <a href="#tab_9" onclick="setTab('one',9,'')" data-toggle="tab">餐饮</a>
                                </li>
                            </ul>
                            <%--<div>
                                <table>
                                    <tr>
                                        <td><h3 id="tab_star_info"></h3></td>
                                        <td>--%>
                                            <div id="star">
                                                <h5 id="tab_star_info"></h5>
                                                <ul style="list-style:none">
                                                    <li id="star1">
                                                        <a href="javascript:;">1</a>
                                                    </li>
                                                    <li id="star2">
                                                        <a href="javascript:;">2</a>
                                                    </li>
                                                    <li id="star3">
                                                        <a href="javascript:;">3</a>
                                                    </li>
                                                    <li id="star4">
                                                        <a href="javascript:;">4</a>
                                                    </li>
                                                    <li id="star5">
                                                        <a href="javascript:;">5</a>
                                                    </li>
                                                </ul>
                                            </div>
                                        <%--</td>
                                    </tr>
                                </table>
                            </div>--%>
                            <div class="clearfix" id="radius">
                                <a href="javascript:;" class="btn green-meadow" onclick="setTab('two',1, 500)" class="btn default" id="two1" value="500"> 0.5km </a>
                                <a href="javascript:;" id="two2" onclick="setTab('two',2, 1000)" class="btn default" value="1000"> 1.0km </a>
                                <a href="javascript:;" id="two3" onclick="setTab('two',3, 1500)" class="btn default" value="1500"> 1.5km </a>
                                <a href="javascript:;" id="two4" onclick="setTab('two',4, 2000)" class="btn default" value="2000"> 2.0km </a>
                                <a href="javascript:;" id="two5" onclick="setTab('two',5, 2500)" class="btn default" value="2500"> 2.5km </a>
                            </div>
                            <HR align="center" width="300px;" color="#987cb9SIZE=1">
                            <div class="tab-content" style="width:350px; height:310px; overflow:scroll;">
                                <table id="table1" class="table table-bordered table-hover">
                                    <th id="poiKind" style="font-size:15px;"></th>
                                    <tbody id="tb1">
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        
                    </div>
                </div>
            </div>
        </div>
        <!-- END PORTLET-->
    </div>
    
    
    <div class="row" id="assDetail" style="display:none">
        <div class="col-md-12">
            <!-- BEGIN PORTLET-->
            <div class="portlet light bordered">
                <div class="portlet-title">
                    <div class="caption">
                        <i class="icon-home font-dark"></i>
                        <span class="caption-subject font-dark sbold uppercase">估价走势</span>
                    </div>

                </div>
                <div class="portlet-body">
					<div class="row">
                                    	<div class="col-md-12" id="main" style="height:400px;">
                        				</div>
                                 	</div>
                <div class="row" id="radarPic" style="display:none">
                	<div class="col-md-5">
                	<div>
                        <span><font size="4" color="#333" style="font-weight:bold" face="微软雅黑">房屋价格信息</font></span>
                    </div>
                            <div class="table-scrollable">
                                <table class="table table-bordered table-hover">
                                    <tbody>
                                    <tr>
                                        <th> 此套房屋评估单价 </th>
                                        <td id="assUnitPrice"></td>
                                    </tr>
                                    <tr>
                                        <th> 此套房屋评估总价 </th>
                                        <td id="assTotalPrice"></td>
                                    </tr>
                                    <tr>
                                        <th> 近一年二手房交易量 </th>
                                        <td id="assHouseDealNum"></td>
                                    </tr>
                                    <tr>
                                        <th> 近一年二手房交易均价 </th>
                                        <td id="assHouseDealPirce"></td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                	<div class="col-md-7">
                            <div id="mainRadar" style="height:400px;">
                        				</div>
                        </div>
                </div>	
                </div>
            </div>
        </div>
        <!-- END PORTLET-->
    </div>
    
</div>


<%@include file="/WEB-INF/pages/include/bottom.jsp" %>
</body>
<script type="text/javascript"
        src="http://api.map.baidu.com/api?v=2.0&ak=5ibDwRtW0ic8CacALvMkxt8tMtBEYyvc"></script>
<script type="text/javascript">
/*    $(document).ready(function(){
        $('body').append("<div style='display:block;width:100%; margin:0 auto;position:fixed;left:0;top:0;bottom: 0;z-index: 111;opacity: 0.5;' id='loading'><a class='mui-active' style='left: 50%;position: absolute;top:50%'><span class='mui-spinner'></span><p style='margin-left: -10px;'>发送中...</p></a></div>")
    })
    $(document).ajaxStart(function(){
        $("#loading").show();
    })
    $(document).ajaxComplete(function(){
        $("#loading").hide();
    })*/
    $(function(){
    	$("#initPage").modal("show");
        initMap();//加載地圖
        setTimeout("$('#initPage').modal('hide')",2000);
    })
    var map = null ;
    var roadLan = "";
    var point = "";
    var map_center_lon = "";
    var map_center_lan = "";
    var config_map = {
        scale: 18   //比例尺，默认20m
    };

    function analysis() {
        map.clearOverlays();
        var address = $("#in").val();
        if (address == "") {
            bootbox.alert("地址不能为空，请重新输入");
        } else {
        $("#static").modal("show");
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
                	$("#static").modal("hide");
                    $("#tb").empty();
                    for (var i = 0; i < data.length; i++) {
                        var html = "<tr><td id='bm" + i + "'></td><td id='qx" + i + "'></td><td id='jd" + i + "'></td><td id='jw" + i + "'></td><td id='ln" + i + "'></td><td id='h" + i + "'></td><td id='s" + i + "'></td></tr>";
                        $("#tb").append(html);
                        $("#qx" + i).html(data[i].qx);
                        $("#jd" + i).html(data[i].jd);
                        $("#jw" + i).html(data[i].jw);
                        $("#ln" + i).html(data[i].ln);
                        $("#h" + i).html(data[i].h);
                        $("#s" + i).html(data[i].s);
                        $("#bm" + i).html(data[i].bm);
                    }
                    
                    //清理数据
                        $("#residenceName").html("");
                        $("#aliases").html("");
                        $("#residenceAddr").html("");
                        $("#propertyType").html("");
                        //$("#fourTo").html("");
                        $("#accomplishDate").html("");
                        $("#vp").html("");
                        $("#gp").html("");
                        $("#totalArea").html("");
                        $("#buildingCount").html("");
                        $("#houseCount").html("");
                        $("#houseType").html("");
        				$("#area").html("");
        				$("#towards").html("");
        				$("#roomType").html("");
        				$("#accomplishDate2").html("");
        				$("#assTotalPrice").html("");
        				$("#assUnitPrice").html("");
        				$("#assHouseDealNum").html("");
        				$("#assHouseDealPirce").html("");
        				$("#metro").html("");
        				$("#school").html("");
                    
                    roadLan = data[0].ln;
                    if (data[0].f == "1") {
                        $("#ts").html("地址标准化成功").css("color", "green");
                        $("#residenceName").html(data[0].detail.residenceName);
                        $("#aliases").html(data[0].detail.aliases);
                        $("#residenceAddr").html(data[0].detail.residenceAddr);
                        $("#propertyType").html(data[0].detail.propertyType);
                        //$("#fourTo").html(data[0].detail.fourTo);
                        $("#accomplishDate").html(data[0].accomplishDate);
                        $("#vp").html(data[0].detail.vp);
                        if(data[0].detail.houseCount!=null) {
                        	$("#gp").html(data[0].detail.gp*100+"%");
                        }
                        $("#totalArea").html(data[0].detail.totalArea+"m²");
                        $("#buildingCount").html(data[0].detail.buildingCount);
                        if(data[0].detail.houseCount==0) {
                        	$("#houseCount").html("暂无数据");
                        } else {
                        	$("#houseCount").html(data[0].detail.houseCount);
                        }
                        
                        $("#houseType").html(data[0].detail.houseType);
                        $("#assDetail").css('display', 'block');
                        $("#homeDetail").css('display', 'block');
                        $("#radarPrice").css('display', 'block');
                        initResidenceBoundary($("#ln0").html());//加載小區邊界
                        initBus(data[0].poiList);
                        // 初始化echarts实例
        				var myChart = echarts.init(document.getElementById('main'));
        				
        				
        				if(data[0].metroDistance!=null) {
        		$("#jinditie").show(); 
        	} else {
        		$("#jinditie").hide(); 
        	}
        	if(data[0].school!=null) {
        		$("#jinxuequ").show(); 
        	} else {
        		$("#jinxuequ").hide(); 
        	}
        				

        /* // 指定图表的配置项和数据
        var option = {
            title: {
                text: '小区价格走势'
            },
            tooltip: {},
            xAxis: {
                data: data[0].b
            },
            yAxis: {},
            series: [{
                name: '单价',
                type: 'bar',
                data: data[0].a
            }]
        }; */

		/*option = {
		    title: {
		        text: '二手房价格走势图',
		        left: 'center'
		    },
		    tooltip: {
		        trigger: 'item',
		        formatter: '{a} <br/>{b} : {c}'
		    },
		    legend: {
		        left: 'left',
		        data: ['二手房挂牌均价']//, '二手房交易均价'
		    },
		    xAxis: {
		        type: 'category',
		        splitLine: {show: false},
		        data: data[0].b
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    yAxis: {
        		type : 'value',
        		axisLabel : {
            		formatter: '{value}元'
        		},
        		splitNumber:10
    		},
		    series: [
		        {
		            name: '二手房挂牌均价',
		            type: 'line',
		            data: data[0].g
		        },
		        {
		            name: '二手房交易均价',
		            type: 'line',
		            data: data[0].a
		        }
		    ]
		};*/
		
		
		option = {
    title: {
        text: '二手房价格走势图'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['二手房挂牌均价']
    },
    xAxis:  {
        type: 'category',
        boundaryGap: false,
        data: data[0].b
    },
    yAxis: {
        type : 'value',
        min: 10000,
        axisLabel : {
        	formatter: function(value) 
				{ 
					return value/1000+'k'; 
				} 
        	},
        splitNumber:10
    },
    series: [
        {
		    name: '二手房挂牌均价',
		    type: 'line',
		    data: data[0].g
		}
    ]
};

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        
        if(data[0].d=="true") {
        	$("#radarPic").css('display', 'block');
        	$("#area").html(data[0].area+"m²");
        	$("#towards").html(data[0].towards);
        	$("#roomType").html(data[0].roomType);
        	$("#accomplishDate2").html(data[0].accomplishDate);
        	$("#assTotalPrice").html(data[0].assTotalPrice+"万元");
        	$("#assUnitPrice").html(data[0].assUnitPrice+"元/平");
        	$("#assHouseDealNum").html(data[0].assHouseDealNum+"套");
        	$("#assHouseDealPirce").html(data[0].assHouseDealPirce+"元/平");
        	$("#metro").html(data[0].metroDistance);
        	$("#school").html(data[0].school);
        } else {
        	$("#radarPic").css('display', 'none');
        }
        
        var radar = echarts.init(document.getElementById('mainRadar'));
        
        /*option2 = {
		    title: {
		        text: '周边配套评分'
		    },
		    tooltip: {},
		    radar: {
		        // shape: 'circle',
		        indicator: [
		           { name: '交通', max: 100},
		           { name: '医疗', max: 100},
		           { name: '购物', max: 100},
		           { name: '教育', max: 100},
		           { name: '生活', max: 100},
		           { name: '休闲', max: 100},
		           { name: '公园', max: 100},
		           { name: '餐饮', max: 100},
		           { name: '健身', max: 100}
		        ]
		    },
		    series: [{
		        //name: '预算 vs 开销（Budget vs spending）',
		        type: 'radar',
		        // areaStyle: {normal: {}},
		        data : [
		            {
		                value : data[0].c,
		                name : '配套评分（Supporting Score）'
		            }
		        ]
		    }]
		};*/
        
        option2 = {
    title : {
        text: '周边配套评分',
        x:'center'
    },
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        x : 'center',
        y : 'bottom',
        data:['交通','医疗','购物','教育','生活','休闲','公园','餐饮','健身']
    },
    toolbox: {
        show : true,
        feature : {
            saveAsImage : {show: true},
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            
            magicType : {
                show: true,
                type: ['pie', 'funnel']
            },
            restore : {show: true}
        }
    },
    calculable : true,
    series : [
        {
            name:'配套评分',
            type:'pie',
            radius : [30, 110],
            center : ['50%', '50%'],
            roseType : 'area',
            data:[
                {value:data[0].c[0], name:'交通'},
                {value:data[0].c[1], name:'医疗'},
                {value:data[0].c[2], name:'购物'},
                {value:data[0].c[3], name:'教育'},
                {value:data[0].c[4], name:'生活'},
                {value:data[0].c[5], name:'休闲'},
                {value:data[0].c[6], name:'公园'},
                {value:data[0].c[7], name:'餐饮'},
                {value:data[0].c[8], name:'健身'}
            ]
        }
    ]
};
        
        // 使用刚指定的配置项和数据显示图表。
        radar.setOption(option2);
                        // poi初始化
                        initPoiInfo();
                        // poi展示
                        poiInfo(data);
        
                    } else if (data[0].f == "2") {
                        $("#ts").html("区县不对应").css("color", "red");
                        $("#homeDetaill").css('display', 'none');
                        $("#assDetail").css('display', 'none');
                        $("#radarPrice").css('display', 'none');
                    } else if (data[0].f == "3") {
                        $("#ts").html("地址不存在").css("color", "red");
                        $("#homeDetail").css('display', 'none');
                        $("#assDetail").css('display', 'none');
                        $("#radarPrice").css('display', 'none');
                    } else if (data[0].f == "4") {
                        $("#ts").html("路弄未找到").css("color", "red");
                        $("#homeDetail").css('display', 'none');
                        $("#assDetail").css('display', 'none');
                        $("#radarPrice").css('display', 'none');
                    } else if (data[0].f == "5") {
                        $("#ts").html("道路不存在").css("color", "red");
                        $("#homeDetail").css('display', 'none');
                        $("#assDetail").css('display', 'none');
                        $("#radarPrice").css('display', 'none');
                    } else if (data[0].f == "6") {
                        $("#ts").html("房间号未找到").css("color", "red");
                        $("#homeDetail").css('display', 'none');
                        $("#assDetail").css('display', 'none');
                        $("#radarPrice").css('display', 'none');
                    } else if (data[0].f == "7") {
                        $("#ts").html("楼栋未找到").css("color", "red");
                        $("#homeDetail").css('display', 'none');
                        $("#assDetail").css('display', 'none');
                        $("#radarPrice").css('display', 'none');
                    }
                }
            });
        }
    }

    var poi_list = null;
    function initPoiInfo(){
        for(var i=1; i<=5; i++){
            var value1 = document.getElementById("two"+i).className;
            if(value1=='btn green-meadow'){
                document.getElementById("two"+i).className = "btn default";
            }
        }
        for(var i=1; i<=9; i++){
            var tabName2 = document.getElementById("one"+i).className;
            if(tabName2=='active'){
                document.getElementById("one"+i).className = "";
            }
        }
        document.getElementById("two1").className="btn green-meadow";
        document.getElementById("one1").className="active";
    }

    function poiInfo(data){
        var oStar = document.getElementById("star");
        var aLi = oStar.getElementsByTagName("li");
        var i = iScore = iStar = 0;
        // 评分
        iScore = data[0].score;
        for(var i=1; i<=9; i++){
            var tabName = document.getElementById("one"+i).className
            if(tabName=='active'){
                if(i == 1){
                    $("#tab_star_info").html("交通配套星级评分:");
                }
                if(i==2){
                    $("#tab_star_info").html("教育配套星级评分:");
                }
                if(i==3){
                    $("#tab_star_info").html("医疗配套星级评分:");
                }
                if(i==4){
                    $("#tab_star_info").html("购物配套星级评分:");
                }
                if(i==5){
                    $("#tab_star_info").html("生活配套星级评分:");
                }
                if(i==6){
                    $("#tab_star_info").html("休闲配套星级评分:");
                }
                if(i==7){
                    $("#tab_star_info").html("健身配套星级评分:");
                }
                if(i==8){
                    $("#tab_star_info").html("公园配套星级评分:");
                }
                if(i==9){
                    $("#tab_star_info").html("餐饮配套星级评分:");
                }
                break;
            }
        }
        for (i = 0; i < aLi.length; i++)
            aLi[i].className = i < iScore ? "on" : "";
        // poi距离
        $("#table1").empty();
        var category;
        var sameNum;
        for(var i = 0; i<data[0].poiList.length; i++) {
            poi_list = data[0].poiList;
            var poiCategoryName = data[0].poiList[i].categoryName
            if (i == 0) {
                var kindHtml = "<th id='poiKind" + i + "' style='font-size:15px;'></th>";
                $("#table1").append(kindHtml);
                $("#poiKind" + i).html(poiCategoryName);
                var tbodyHtml = "<tbody id='tb" + i + "'></tbody>";
                $("#table1").append(tbodyHtml);
                var poiHtml = "<tr><td id='poiName" + i + "' onclick='poi_map($(this).html());'></td><th id='distance" + i + "'></th></tr>"
                $("#tb" + i).append(poiHtml);
                $("#poiName" + i).html(data[0].poiList[i].poiName);
                $("#distance" + i).html(data[0].poiList[i].distance + "米");
                category = poiCategoryName;
                sameNum = i;
            } else {
                if (poiCategoryName == category) {
                    var poiHtml = "<tr><td id='poiName" + i + "' onclick='poi_map($(this).html());'></td><th id='distance" + i + "'></th></tr>"
                    $("#tb" + sameNum).append(poiHtml);
                    $("#poiName" + i).html(data[0].poiList[i].poiName);
                    $("#distance" + i).html(data[0].poiList[i].distance + "米");
                } else {
                    var notSameKindHtml = "<th id='poiKind" + i + "' style='font-size:15px;'></th>";
                    $("#table1").append(notSameKindHtml);
                    $("#poiKind" + i).html(poiCategoryName);
                    var notSameTbodyHtml = "<tbody id='tb" + i + "'></tbody>";
                    $("#table1").append(notSameTbodyHtml);
                    var notSamePoiHtml = "<tr><td id='poiName" + i + "' onclick='poi_map($(this).html());'></td><th id='distance" + i + "'></th></tr>"
                    $("#tb" + i).append(notSamePoiHtml);
                    $("#poiName" + i).html(data[0].poiList[i].poiName);
                    $("#distance" + i).html(data[0].poiList[i].distance + "米");
                    category = poiCategoryName;
                    sameNum = i;
                }
            }
        }
    }

    function setTab(name,cursel,r){
        if(r != null || r != ''){
            var categoryName;
            var center = map.getCenter();
            if(r == 500){
                map.centerAndZoom(center,18);
            }
            if(r == 1000){
                map.centerAndZoom(center,17);
            }
            if(r == 1500){
                map.centerAndZoom(center,16);
            }
            if(r == 2000){
                map.centerAndZoom(center,15);
            }
            if(r == 2500){
                map.centerAndZoom(center,14);
            }
        }
        if(name == 'two'){
            for(var i=1; i<=5; i++){
                var value = document.getElementById("two"+i).className;
                if(value=='btn green-meadow'){
                    document.getElementById("two"+i).className = "btn default";
                }
            }
            document.getElementById("two"+cursel).className="btn green-meadow";
//            banjing = $("#two"+cursel).attr("value");
            for(var i=1; i<=9; i++) {
                var tabName = document.getElementById("one" + i).className;
                if (tabName == 'active') {
                    categoryName = $("#one" + i + " a").html();
                    break;
                }
            }
            getPois(roadLan,categoryName,r);
        } else if(name == 'one'){
            categoryName = $("#one" + cursel + " a").html();
            for(var i=1; i<=9; i++){
                var tabName = document.getElementById("two"+i).className;
                if(tabName=='btn green-meadow'){
                    r = $("#two"+i).attr("value");
                    break;
                }
            }
            getPois(roadLan,categoryName,r);
        }
    }

    function getPois(roadLan, categoryName, r) {
        map.clearOverlays();
        initResidenceBoundary(roadLan,1);
        $.ajax({
            url: '${ctx}/poiDetail',
            type: "POST",
            data:{"roadLane":roadLan,"r":r,"categoryName":categoryName},
            success: function (data) {
                poiInfo(data);
                $.each(data, function (i, item) {
                    var poiList = item.poiList;
                        initBus(poiList);
                });
            }
        })
    }

    function initMap() {
        //加载地图
        map = new BMap.Map("map");
        map.setMapStyle({
            styleJson:[
                {
                    "featureType": "poi",
                    "elementType": "geometry.stroke",
                    "stylers": {
                        "color": "#000000",
                        "hue": "#ffffff",
                        "lightness": 71,
                        "saturation": 93
                    }
                }
            ]
        });
        var top_left_control = new BMap.ScaleControl({anchor: BMAP_ANCHOR_TOP_LEFT});// 左上角，添加比例尺
        var top_left_navigation = new BMap.NavigationControl();  //左上角，添加默认缩放平移控件
        map.addControl(top_left_control);
        map.addControl(top_left_navigation);
//        map.enableScrollWheelZoom(true);
    }

    var array ;


    var residence_lat = "";
    var residence_lon = "";
    function initResidenceBoundary(roadLan,num) {
        array = new Array;
        $.ajax({
            url: '${ctx}/boundary?roadLan=' + roadLan,
            type: "GET",
            success: function (data) {
                $.each(data, function (i, item) {
                     residence_lat = item.resiLat;
                     residence_lon = item.resiLon;
                    map_center_lon = item.baiduLon;
                    map_center_lan = item.baiduLat;
                    array[i] = new BMap.Point(item.baiduLon, item.baiduLat);
                });
                //添加边界
                var  polygon = new BMap.Polygon(array, {
                    strokeColor: "red",
                    strokeWeight: 2,
                    strokeOpacity: 0.5,
                    fillColor: "none"
                });
                point = new BMap.Point(map_center_lon, map_center_lan);
                map.addOverlay(polygon);
                if($("#h0").html()=='' ){
                    if(null == num){
                        map.centerAndZoom(new BMap.Point(residence_lon, residence_lat),config_map.scale);
                    }else{
                        map.centerAndZoom(new BMap.Point(residence_lon, residence_lat),map.getZoom());
                    }
                }else{
                    //加載樓棟
                    if(null == num){
                        initBuilding(roadLan,$("#h0").html());
                    }else {
                        initBuilding(roadLan,$("#h0").html(),1);
                    }
                }

            }
        })
    }

    var buildingPoint = "";
    var buildingLat = "";
    var buildingLon = "";

    //加载楼栋信息
    function initBuilding(roadLan,buildingNo,num) {
        if(buildingNo == ""){
            return;
        }
        $.ajax({
            url: '${ctx}/building',
            type: "POST",
            data:{"roadLan":roadLan,"buildingNo":buildingNo},
            success: function (data) {
                //中心点
                buildingLon = data.baiduLon;
                buildingLat = data.baiduLat;
                buildingPoint = new BMap.Point(buildingLon, buildingLat);
                if(null == num){
                    map.centerAndZoom(buildingPoint,config_map.scale);
                }else{
                    map.centerAndZoom(buildingPoint,map.getZoom());
                }
                //圖標
                var myIcon = new BMap.Icon("${ctx}/static/img/point-building.png", new BMap.Size(30,70));
                var marker2 = new BMap.Marker(buildingPoint,{icon:myIcon});  // 创建标注
                map.addOverlay(marker2);
                //文本标注
                var opts = {
                    position: buildingPoint,    // 指定文本标注所在的地理位置
                    title:$("#ln0").html(),
                    offset: new BMap.Size(7, -25, 30, 30)    //设置文本偏移量 右  下
                }
                var infoWindow = new BMap.InfoWindow(data.buildingNo +",总楼层："+data.totalFloor+"，总户数："+data.houseCount, opts);  // 创建信息窗口对象
//                map.openInfoWindow(infoWindow,buildingPoint); //开启信息窗口
                //圖標點擊事件
                marker2.addEventListener("click",function(){
                    map.openInfoWindow(infoWindow,buildingPoint); //开启信息窗口

                });
                /*
                function ComplexCustomOverlay(point, text){
                    this._point = point;
                    this._text = text;
                }
                ComplexCustomOverlay.prototype = new BMap.Overlay();
                ComplexCustomOverlay.prototype.initialize = function(map){
                    this._map = map;
                    var div = this._div = document.createElement("div");
                    div.style.position = "absolute";
                    div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
                    div.style.backgroundColor = "white";
                    div.style.border = "1px solid #BC3B3A";
                    div.style.color = "#000000";
                    div.style.height = "24px";
                    div.style.padding = "2px";
                    div.style.lineHeight = "18px";
                    div.style.whiteSpace = "nowrap";
                    div.style.MozUserSelect = "none";
                    div.style.fontSize = "12px"
                    var span = this._span = document.createElement("span");
                    div.appendChild(span);
                    span.appendChild(document.createTextNode(this._text));
                    var that = this;

                    var arrow = this._arrow = document.createElement("div");
                    arrow.style.background = "url(http://map.baidu.com/fwmap/upload/r/map/fwmap/static/house/images/label.png) no-repeat";
                    arrow.style.position = "absolute";
                    arrow.style.width = "11px";
                    arrow.style.height = "10px";
                    arrow.style.top = "22px";
                    arrow.style.left = "10px";
                    arrow.style.overflow = "hidden";
                    div.appendChild(arrow);
                    map.getPanes().labelPane.appendChild(div);

                    return div;
                }
                ComplexCustomOverlay.prototype.draw = function(){
                    var map = this._map;
                    var pixel = map.pointToOverlayPixel(this._point);
                    this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
                    this._div.style.top  = pixel.y - 30 + "px";
                }
                var txt =data.buildingNo +",总楼层："+data.totalFloor+"，总户数："+data.houseCount;
                var myCompOverlay = new ComplexCustomOverlay(buildingPoint, txt);
                map.addOverlay(myCompOverlay);*/

            }
        })
    }

    //加載交通信息
    function initBus(data) {
        $.each(data, function (i, item) {
            var bus_lat = item.baiduLat;
            var bus_lon = item.baiduLon;
            var bus_point_i = new BMap.Point(bus_lon, bus_lat);
            var myIcon_i = new BMap.Icon("${ctx}/static/img/aroundPos.png", new BMap.Size(30, 70));
            var marker2_i = new BMap.Marker(bus_point_i, {icon: myIcon_i});  // 创建标注

            //点击事件，显示文本内容
            var opts_i = {
                position: bus_point_i,    // 指定文本标注所在的地理位置
                title: item.poiName,
                offset: new BMap.Size(7, -25, 30, 30)    //设置文本偏移量 右  下
            }
            var infoWindow_i = new BMap.InfoWindow("地址：" + item.poiAddress, opts_i);  // 创建信息窗口对象
            //标签
            var label_i = new BMap.Label(i+1,{offset:new BMap.Size(7,3)});
            label_i.setStyle({
                color: "white",
                fontSize: "10px",
                backgroundColor: "0.05",
                border: "0",
                fontFamily: "微软雅黑"
            });
            marker2_i.setLabel(label_i);
            //圖標點擊事件
            marker2_i.addEventListener("click", function () {
                map.openInfoWindow(infoWindow_i, bus_point_i); //开启信息窗口
            });
            map.addOverlay(marker2_i);
        })
    }

       function poi_map(name){
           for(var i = 0; i< poi_list.length; i++){
             var item = poi_list[i];
               if(name == item.poiName){
                   var bus_lat = item.baiduLat;
                   var bus_lon = item.baiduLon;
                   var bus_point_i = new BMap.Point(bus_lon, bus_lat);
                   var myIcon_i = new BMap.Icon("${ctx}/static/img/aroundPos.png", new BMap.Size(30, 70));
                   var marker2_i = new BMap.Marker(bus_point_i, {icon: myIcon_i});  // 创建标注

                   //点击事件，显示文本内容
                   var opts_i = {
                       position: bus_point_i,    // 指定文本标注所在的地理位置
                       title: item.poiName,
                       offset: new BMap.Size(7, -25, 30, 30)    //设置文本偏移量 右  下
                   }
                   var infoWindow_i = new BMap.InfoWindow("地址：" + item.poiAddress, opts_i);  // 创建信息窗口对象
                   //标签
                   var label_i = new BMap.Label(i+1,{offset:new BMap.Size(7,3)});
                   label_i.setStyle({
                       color: "white",
                       fontSize: "10px",
                       backgroundColor: "0.05",
                       border: "0",
                       fontFamily: "微软雅黑"
                   });
                   marker2_i.setLabel(label_i);
                   //圖標點擊事件
                   map.openInfoWindow(infoWindow_i, bus_point_i); //开启信息窗口
//                   map.centerAndZoom(bus_point_i,map.getZoom());
               }
           }
       }
</script>
</html>