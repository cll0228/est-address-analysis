/*********************************
 底层方法
 **********************************/
//处理ie8下不小心写了console时报错的情况
window.console = window.console || (function(){
    var c = {}; c.log = c.warn = c.debug = c.info = c.error = c.time = c.dir = c.profile
        = c.clear = c.exception = c.trace = c.assert = function(){};
    return c;
})();

//判断浏览器
function checkBrowserIsLegal(){
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isOpera = userAgent.indexOf("Opera") > -1; //判断是否Opera浏览器
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera; //判断是否IE浏览器

    var legalBrowser = true;

    if (isIE) {
        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);

        legalBrowser = fIEVersion > 8.0;//如果小于版本ie8
    }

    return legalBrowser;
}

//返回url中指定参数名地应的值
function getParamValueFromUrlByKey(key){
    var searchStr = window.location.search.replace("?", "");
    searchList = searchStr.split(",");
    var params = {};
    $.each(searchList, function(index, node){
        var tempArr = node.split("=");
        params[tempArr[0]] = tempArr[1];
    });
    return params[key];
}

/***公共的需要调用的对于localstorage的处理***/
var localStorageFunObj = {
    getLocalStorage:function(name){//获取localstorage的某个值
        try{
            var localstorageInfo = window.localStorage[name];
            if(!localstorageInfo){
                return null;
            }
            return JSON.parse(window.localStorage[name]);
        }catch(e){
            return null;
        }
    },
    setLocalStorage:function(name,obj){//设置localstorage的某个值
        try{
            window.localStorage[name] = JSON.stringify(obj);
        }catch(e){
            return ;
        }
    },
    removeLocalStorage:function(name){
        try{
            window.localStorage.removeItem(name);
        }catch(e){
            return ;
        }
    }
};

//阻止冒泡
function stopBubble(e){
    if (e && e.stopPropagation ){//not IE
        e.stopPropagation();
    }
    else{//IE
        window.event.cancelBubble = true;
    }

    if(window.ubtApi && window.ubtApi.loggerEvent){
        window.ubtApi.loggerEvent(e);
    }
}


/************************************
 业务、界面相关方法
 *************************************/
//筛选条件 － 范围输入框与确定按钮的联动（上下限同时存在且为数字则显示按钮）
function rangeSubmitCtrl(min, max){
    $("input[name="+min+"], input[name="+max+"]").keyup(function(){
        var min_ok = $.trim($("input[name="+min+"]").val()) != "" && !isNaN($.trim($("input[name="+min+"]").val())*1);
        var max_ok = $.trim($("input[name="+max+"]").val()) != "" && !isNaN($.trim($("input[name="+max+"]").val())*1);
        
        var btn_confirm = $(this).parents("div.custom").eq(0).find("input[type='button'].ok");
        if(min_ok || max_ok){
            btn_confirm.css({"display":"inline-block"});
        }
        else{
            btn_confirm.css({"display":"none"});
        }
    });
}

/*app下载相关的 二维码处理
 * 参数dom的属性上 图片宽、高、以及位置标志source
 * */
function buildDownAppQrcode(self){
    if(self.css('position') != 'absolute'){//本身没有定位
        self.css('position','relative');
    }

    var str = self.attr('source'),
        ewmImgW = Number(self.attr('ewmimgw')) ? Number(self.attr('ewmimgw')) : 119,
        ewmImgH = Number(self.attr('ewmimgh')) ? Number(self.attr('ewmimgh')) : 119;

    var hostObj = {
        productionUrl: 'http://m.sh.lianjia.com/client/',
        testUrl:       'http://192.168.3.85:8010/client/',
        developmentUrl:'http://local.lianjia.net:8010/client/',
        integrationUrl:'http://192.168.3.85:8010/client/',
    };

    var env = window.headerParameters && window.headerParameters.env ? window.headerParameters.env : '';
    var url = hostObj[env+'Url'] + "?source=" + str; //s=wem 来源：二维码
    var qrcodeOptions = {
        render: "canvas",
        width: ewmImgW,
        height:ewmImgH,
        text: toUtf8(url)
    };
    if(!checkBrowserIsLegal()){
        qrcodeOptions.render = "table";
    }
    self.qrcode(qrcodeOptions);

    //动态插入的logo图
    var logoImgW = parseInt(ewmImgW / 3),logoImgH = parseInt(ewmImgH / 3);
    if(self.find('.js_qrCodeIcon').length <= 0){
        self.append('<img class="js_qrCodeIcon" src="' + headerParameters.publichost + '/public/img/qrCodeIcon.png"  />');
        var bW = ewmImgW <= 70 ? 1 : ( ewmImgW <= 100 ? 2 : 3);
        self.find('.js_qrCodeIcon').css({
            borderWidth:bW+'px',
            borderRadius: parseInt(logoImgW / 5 ) + 'px',
            borderStyle: 'solid',
            borderColor: '#fff',
            background: '#fff',
            width:logoImgW + 'px',
            height:logoImgH + 'px',
            position:'absolute',
            top:'50%',
            left:'50%',
            marginTop:'-' + (logoImgW/2 + bW) + 'px',
            marginLeft:'-' + (logoImgH/2 + bW) + 'px'
        });
    }

}

//var loginDoing = false;//登录弹层－防重复提交中间变量
var feedbackDoing = false;//反馈弹层－防重复提交中间变量
var callback_getUserInfo = null;
$(function(){
    //切换 实景图模式/户型图模式
    $("#lshow, #hshow").click(function(){
        if($(this).parents(".modeshows").eq(0).hasClass("modeshow")) return false;

        //切换链接高亮处理
        $(".modeshows").removeClass("modeshow");
        $(this).parent(".modeshows").addClass("modeshow");
        var type = $(this).attr("data-type");

        //列表图片切换
        $(".pic-panel").find("img:first").each(function(){
            $(this).attr("src", $(this).attr("data-img-"+type));
            $(this).attr("onerror", "this.src='"+headerParameters.statichost+"/static/img/new-version/default_block.png'; this.onerror=null");
            $(this).attr("data-original", $(this).attr("data-img-"+type));
        });

        $.cookie('houseThumbType', $(this).attr("id"), { path: "/"}); //将当前的头图模式写入cookie
    });

    //图片延迟加载（lazyload）
    if($('.lj-lazy').length > 0){
        $('.lj-lazy').lazyload();
    }

    /*//footer - 链接 － tab切换
    $(".lianjia-link-box .tab").on("mouseenter", "span", function(){
        //tab切换
        $(".lianjia-link-box .tab span.hover").removeClass("hover");
        $(this).addClass("hover");

        //list切换
        var index = $(".lianjia-link-box .tab span").index($(this));
        $(".lianjia-link-box .link-list div").css({
            "display" : 'none'
        });
        $(".lianjia-link-box .link-list div").eq(index).css({
            "display" : 'block'
        });
    });


    //fix-right - hover - tips
    $(".fix-right-v2 ul").on("mouseenter", "li", function(){
        var index = $(".fix-right-v2 ul li").index($(this));

        //显示
        $(".fix-right-v2 ul li span.popup").eq(index).css({
            opacity : 0,
            display : 'block',
            right : '48px'
        }).animate({
            opacity : 1,
            right : '38px'
        }, 300);
    }).on("mouseleave", "li", function(){
        $(".fix-right-v2 ul li span.popup").css({
            opacity : 0,
            display : 'none',
            right : '48px'
        });
    });

    //fix-right - go top
    function resetGotop(){
        var h = $(window).height(),
            t = $(document).scrollTop();
        if(t>h){
            $("#gotop").show();
            $(".fix-right .tips,.fix-right .has-ask").show();
        }
        else{
            $("#gotop").hide();
            $(".fix-right .tips,.fix-right .has-ask").hide();
        }
    }
    resetGotop();
    $("#gotop").click(function(){
        $("body").css({
            scrollTop : 0
        });
    });

    $(window).scroll(function(e){
        resetGotop();
    });*/


    //login
    /*$("#con_login_user").on("click", "#login-user-btn", function(){
        if(loginDoing == true) return false;

        loginDoing = true;
        var btnSelf = $(this);
        btnSelf.addClass("disabled");
        $("#user-login-show-error").hide();
        if($.trim($("#user_name").val()) == "" || $.trim($("#user_password").val()) == ""){
            btnSelf.removeClass("disabled");
            return false;
        }

        var requestParams = {
            username: $.trim($("#user_name").val()),
            password: $.trim($("#user_password").val()),
            verifycode: ""
        };

        if($("#remember_login").prop("checked") == true){//下次自动登
            requestParams.remember = 1;
        }

        lianjiaCasManager.login(requestParams,
            function(e) {
                //登录成功
                window.location.reload();
            },
            function() {
                //登录失败
                loginDoing = false;
                btnSelf.removeClass("disabled");
                $("#user-login-show-error").css({
                    'display': 'list-item'
                }).show();
                console.info("执行失败");
            }
        );
    });

    //登录窗口关闭
    $("#dialog i.close").click(function(){
        $("#dialog, #loginOverlay").css({//登录弹层
            "display" : "none"
        });
    });*/

    //生成非编码售价、均价、面积的链接
    function getRangeInputValue(name, isMax){//上限为空时，设置为1亿
        var v = $("[name='" + name + "']").val();
        if(isMax && v == "") v = 100000000;
        return Math.round(v);
    }

    $(".inputCode").click(function(){
        var codeType = $(this).attr("codeType");
        var minValue = 0;
        var maxValue = 100000000;
        switch(codeType){
            case "b" ://售价
            case "e" ://均价
            case "k" ://租金
                minValue = getRangeInputValue('min_price');
                maxValue = getRangeInputValue('max_price', true);
                break;
            case "m" ://面积
                minValue = getRangeInputValue('min_area');
                maxValue = getRangeInputValue('max_area', true);
                break;
            default: ;
        }
        if(isNaN(minValue) || minValue > 100000000) minValue = 0;
        if(isNaN(maxValue) || maxValue > 100000000) maxValue = 100000000;
        var newUrl = pagePrefix + "/" + makeUrl(codeType, minValue, maxValue);
        location.href = newUrl;
    });
	
	//生成非编码售价、均价、面积的链接
	var makeUrl = function(type, minValue, maxValue){
		var nowUrls = nowUrl.split("/");
		var returnUrls = [];
		if(nowUrl.search("/") != -1){//有区域、板块
			returnUrls[0] = nowUrls[0];
			returnUrls[1] = nowUrls[1];
		}else{
			returnUrls[1] = nowUrls[0];
		}
		
		//编码
		var code = type + minValue + "to" + maxValue;
		code = code.replace(/\./g,"_");
		//替换或加入编码
		if(returnUrls[1] == null || returnUrls[1] == ""){
			returnUrls[1] = code;
		}else{
			var codes = returnUrls[1].match(/(rs[\s\S]*)|((b|m|e|k)[0-9]+([_][0-9]+){0,1}to[0-9]+([_][0-9]+){0,1})|([a-z][0-9]{1,})/g);
			for(var i = 0; i < codes.length; i++){
				var first = codes[i].charAt(0);
				if(type == "b"){//售价
					if(first == "p" || first == "b"){
						codes[i] = "";
					}
				}else if(type == "e"){//均价
					if(first == "j" || first == "e"){
						codes[i] = "";
					}
				}else if(type == "k"){//租金
					if(first == "z" || first == "k"){
						codes[i] = "";
					}
				}else{//面积
					if(first == "a" || first == "m"){
						codes[i] = "";
					}
				}
				//改为第一页
				if(first == "d"){
					codes[i] = "d1";
				}
			}
				if(codes[codes.length - 1].search(/rs/) != -1){
					codes.push(codes[codes.length - 1]);
					codes[codes.length - 2] = code;
				}else{
					codes.push(code);
				}
			returnUrls[1] = codes.join("");
		}
		//拼接返回的链接
		var returnUrl = "";
		if(returnUrls[0] != null && returnUrls[0] != ""){
			returnUrl = returnUrls[0] + "/";
		}
		if(returnUrls[1] != null && returnUrls[1] != ""){
			returnUrl = returnUrl + returnUrls[1];
		}
		return returnUrl;
	}

    ////界面上已经存在的dom 直接初始化
    //$('.js_downAppEwmStatic_img').each(function(){
    //    var self = $(this);
    //    buildDownAppQrcode(self);
    //});
    
    //点击详情链接时发送UBT日志
	$("a[name=selectDetail]").click(function(){
		try {
			var searchType = ubtData.searchType,
				searchKeyword = ubtData.searchKeyword,
				resultNum = ubtData.resultNum,
				selectResult = $(this).attr("key"),
				selectIndex = $(this).closest("li").index() + 1;
			ubtApi.trackSearch(searchType, searchKeyword, resultNum, selectResult, selectIndex);
		}catch(e){
			console.error(e);
		}
	});
	
	$("#suggestion").on("click",".actSelect",function(){
		var searchType = "headerSearch",
			searchKeyword = $("#keyword-box").val(),
			resultNum = 0,
			selectResult = $(this).attr("key"),
			selectIndex = $(this).closest("li").index() + 1;
		//ubtApi.trackSearch(searchType, searchKeyword, resultNum, selectResult, selectIndex);
    });

    $('#oldclose').click(function(){
        $("#old").hide();
    });
});