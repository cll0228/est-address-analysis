var userInfo = {
    getUserInfo:function(callback){//异步获取用户信息
        $.ajax({
            url : headerParameters.uchost + '/login/getLoginUserInfo.json?ref=' + encodeURIComponent(window.location.href),
            dataType: "jsonp",
            jsonp: 'callback',
            success: function(response,status){
                userInfo.buildUserInfo(response); //构建用户信息（在导航条右侧）

                headerParameters.loginUserInfo = response;//全局变量存储登录及用户信息

                if(headerParameters.hasSaveSearch){//如果是有保存搜索功能的站点，则初始化已保存搜索
                    //检查本地local有没有saveSearch数据，有则提交到服务端，成功后执行：
                    //请求指定频道已保存搜索的数据，并初始化页面相关内容
                    var channel = $("#hidden_saveSearchChannel").val();//要获取已保存搜索的频道名称
                    initSaveSearch(channel);//已保存搜索初始化
                }

                if(headerParameters.hasFixBar){//如果有右侧悬浮侧边栏，则初始化
                    if(window.footerNamespace && window.footerNamespace.initToolsBar){
                        window.footerNamespace.initToolsBar($('#side-bar-tools-placeholder'), window.headerParameters, response);
                    }else{
                        userInfo.fixBarInit();
                    }
                }

                //重构后的微聊
                if(response.code == 1){     //登录状态
                    var intv = setInterval(function(){
                        if(window.weiliaoScope){
                            window.weiliaoScope.init(response.data);
                            clearInterval(intv);
                        }
                    }, 10);
                }

                //微聊老版本代码
                //userInfo.weiliao_init(response);  //weiliao初始化

                if(callback) callback();
            },
            error : function(response,status){
                console.log(status);
            }
        });
    },
    getUserNews : function(){//异步获取用户消息
        $.ajax({
            url : headerParameters.uchost + '/api/msg/getUserMsg.json',
            type : "get",
            dataType: "jsonp",
            jsonp: 'callback',
            success: function(response){
                userInfo.buildUserNews(response);
            }
        });
    },
    buildUserNews : function(newsInfo){//构建用户消息
        if(newsInfo.data.length == 0) return false;

        var newsTypeList = {
            "community_new_house_source" : {
                t1 : "你关注的小区有 ",
                t2 : " 条新上"
            },
            "deal" : {
                t1 : "你关注的房源有 ",
                t2 : " 条成交"
            },
            "price_changed" : {
                t1 : "你关注的房源有 ",
                t2 : " 条变价"
            },
            "search_new" : {
                t1 : "你保存的搜索条件有 ",
                t2 : " 条变动"
            },
            "on_answer_insert_concern" : {
                t1 : "您关注的问题有 ",
                t2 : " 条回复"
            },
            "on_answer_insert" : {
                t1 : "您提问的问题有 ",
                t2 : " 条回复"
            }
        }
        var userNewsHtml = '<ul><li class="s-li"><span></span></li>';
        $.each(newsInfo.data, function(index, node){
            userNewsHtml += '<li><a href="'+node.url+'">'+newsTypeList[node.typeName].t1 + '<i>'+(node.count>99?"99+":node.count)+'</i>' + newsTypeList[node.typeName].t2+'</li>';
        });
        userNewsHtml += '</ul>';
        $("#userNews").html(userNewsHtml);

        if(newsInfo.totalCount != 0){
            $("#login_bubble_tip").text(newsInfo.totalCount>99?"99+":newsInfo.totalCount).css({"display":"inline-block"});
        }
    },
    buildUserInfo:function (loginUserInfo){//构建用户信息
        $.removeCookie('lianjia_userId');//清除cookie中记录的用户ID
        $.removeCookie('lianjia_agentId');//清除cookie中记录的经纪人ID
        if(loginUserInfo.code == -1){//未登录
            $("#loginUrl").attr("href", loginUserInfo.data.loginUrl);
            $("#loginUrl").click(function(){
                $("#dialog, #loginOverlay").css({
                    "display" : "block"
                });
                return false;
            });

            $("#regUrl").attr("href", loginUserInfo.data.regUrl);
        }
        else{//已登录
            var html = '';
            if(loginUserInfo.data.isAgent){//经纪人
                html += '<a href="'+headerParameters.agenthost+'" gahref="header_user_center"><span class="user-name">'+loginUserInfo.data.userName+'</span></a>';
                $.cookie('lianjia_agentId', loginUserInfo.data.agentId, { path: "/"});//cookie中记录经纪人ID
            }
            else{//用户
                html += '<a href="'+headerParameters.uchost+'" gahref="header_user_center"><span class="user-name">'+loginUserInfo.data.userName+'</span><span class="login_bubble_tip" id="login_bubble_tip"></span></a>';
                if(!($("#lianjia-header").attr("msgOff") == "true")){//在头容器上配置msgOff="true"，则不请求和显示消息
                    userInfo.getUserNews(); //获取消息
                }
                $.cookie('lianjia_userId', loginUserInfo.data.uid, { path: "/"});//cookie中记录用户ID
            }
            html += '<span class="welcome"><a class="reg" href="'+loginUserInfo.data.logoutUrl+'">退出</a></span>';
            $("#userInfo").html(html);
        }
    },
    fixBarInit:function(){//右侧悬浮侧边栏初始化
        /***********************************
         侧边栏（右） －  我关注的房源
         ***********************************/
        if(headerParameters.loginUserInfo.code != -1 && headerParameters.loginUserInfo.data.isAgent){//经纪人已登录 － 隐藏”我关注的房源“入口
            $("#ul_myfav, #line_myfav").remove();
        }
        else{//经纪人未登录
            $("#link_myFav").click(function(){
                if(headerParameters.loginUserInfo.code == -1) {//未登录 － 弹登录弹层
                    $("#dialog, #loginOverlay").css({"display" : "block"});
                }
                else{//用户已登录 - 跳转到个人中心－我关注的房源
                    window.location = headerParameters.uchost + "/favor/house";
                }
            });
        }

        /***********************************
         侧边栏（右） －  反馈
         ***********************************/
            //侧边栏 － 反馈按钮点击事件
        $("#feedback").click(function(){
            $(".feedback-box, #feedbackOverlay").css({
                "display" : "block"
            });
        });
		
		$('.js_feedbackTab span').click(function(){
            var type = $(this).attr('tab-type');

            $('.js_feedbackTab span').removeClass('check');
            $(this).addClass('check');
            $('.js_tabBox').hide();
            $('#feedback_'+type).show();
        });
		
        //反馈弹层 － 内容输入框值变化时检查清理验证报错信息
        $("#feedbackContent").change(function(){
            if($.trim($("#feedbackContent").val()) != ""){
                $("#erro-tips").hide();
            }
        });
        //反馈弹层 － 关闭按钮点击事件
        $('#feedbackPopClose').click(function(){
            closeFeedbackPop();
        });
        //关闭反馈弹窗
        function closeFeedbackPop(){
            $(".feedback-box, #feedbackOverlay").css({
                "display" : "none"
            });
        }
        //反馈弹层 － 提交反馈按钮点击事件
        $("#btn_feedbackSubmit").click(function(){
            if(feedbackDoing == true) return false;//防重复提交
            var btnSelf = $(this);

            if($.trim($("#feedbackContent").val()) == ""){
                $("#erro-tips").show();
                btnSelf.removeClass("disabled");
                return false;
            }
            feedbackDoing = true;

            btnSelf.addClass("disabled");
            var data = "content=" + encodeURIComponent($.trim($("#feedbackContent").val())) +
                "&contact=" + encodeURIComponent($.trim($("#feedbackContact").val())) +
                "&cityCode=" + headerParameters.cityCode +
                "&isAgent=" + (headerParameters.loginUserInfo.data.isAgent ? "1" : "0") + "&client=web";
            if(headerParameters.loginUserInfo.code == 1){//已登录
                if(headerParameters.loginUserInfo.data.isAgent){//经纪人
                    data += "&userId=" + headerParameters.loginUserInfo.data.agentId;
                }else{
                    data += "&userId=" + headerParameters.loginUserInfo.data.uid;
                }
            }

            $.ajax({
                type : "post",
                url : headerParameters.apihost + '/api/v4/online/support/feedback?access_token=7poanTTBCymmgE0FOn1oKp&'+data+'&v='+Number(new Date()),
                //data : data,
                dataType : "json",
                success: function(response,status) {

                    feedbackDoing = false;
                    btnSelf.removeClass("disabled");
                    if (response.status == "ok") {
                        $("#feedback_content,.js_feedbackTab").hide();
                        $("#feedback_success").show();

                        window.setTimeout(function () {
                            closeFeedbackPop();
                        }, 3000);
                    }
                },
                error : function(response, ajaxOptions, thrownError){
                    feedbackDoing = false;
                    btnSelf.removeClass("disabled");
                    console.log(response)
                }
            });
        });
    },
    weiliao_init:function(userInfo){     //weiliao初始化
        try{
            weiliao.weiliao_init(userInfo);
        }
        catch(err){
            //在此处理错误
        }
    }
}
userInfo.getUserInfo(callback_getUserInfo);//获取、构建用户信息
//说明：callback_getUserInfo是在引用该文件的页面里赋值的函数，即获取用户信息后的回调函数（定义是在common.js中定义为null）
