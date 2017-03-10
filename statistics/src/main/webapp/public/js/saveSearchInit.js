/*********************************
 * 顶部搜索 － 已保存搜索 － 初始化
 *********************************/
var saveSearchDataInit = {
    ershoufang : {},
    xinfang : {},
    zufang : {},
    xuequ : {},
    xiaoqu : {}
}

function initSaveSearch(channel){
    var channel = channel==undefined ? "" : channel;
    var saveSearch_local = localStorageFunObj.getLocalStorage("data_saveSearch"); //本地存储的搜索条件
    if(headerParameters.loginUserInfo.code == 1 && headerParameters.loginUserInfo.data && headerParameters.loginUserInfo.data.token) {//已登录，有token
        if(!headerParameters.loginUserInfo.data.isAgent) {//用户（非经纪人）
            if (saveSearch_local) {//本地有保存搜索条件记录，则传回服务器，清空本地记录(所有频道)
                var list = [];
                //$.each(saveSearch_local, function(index, node){
                list.push({url: saveSearch_local[0].url, channel: saveSearch_local[0].channel});//本地只允许存储一条，存入服务端时只取一条，多余的抛弃，避免恶意搭车
                //});
                var postData = {
                    sh_access_token : headerParameters.loginUserInfo.data.token,
                    cityCode : headerParameters.cityCode,
                    channel : channel,
                    client : "pc",
                    dataCount : 20,
                    list: JSON.stringify(list).replace(/\"/g, '\'')    //数据格式："[{url:'xxx', title:'xxxx', channel:'xxx'}, {url:'xxx', title:'xxxx', channel:'xxx'},...]"，title非必须
                };
                var access_token = '7poanTTBCymmgE0FOn1oKp';
                $.ajax({
                    type: "post",
                    url: headerParameters.apihost + '/api/v4/online/search/saveUserSearch?access_token=' + access_token + "&v=" + Number(new Date()),
                    data: postData,
                    dataType: "json",
                    success: function (response, status) {
                        if (response.status == 'ok') {
                            localStorageFunObj.removeLocalStorage("data_saveSearch");//清除本地存储的数据

                            //从服务端获取已保存搜索数据(暂时只开放二手房频道)
                            if (channel == "ershoufang") {
                                $.each(response.data, function (index, node) {
                                    saveSearchDataInit[channel][node.id] = node.filter;
                                    node.filter = node.filter.split(",");
                                });

                                setSaveSearchUI(channel, {
                                    login: true,
                                    total: response.total,
                                    list: response.data
                                }, true);
                            }
                        }
                    },
                    error: function (response, status) {
                        console.log(response);
                    }
                });
            }
            else {
                //从服务端获取已保存搜索数据(暂时只开放二手房频道)
                if (channel == "ershoufang") {
                    getSaveSearchDataFromServer(channel);//从服务端获取已保存搜索数据
                }
            }
        }
    }
    else{//未登录
        //本地有保存搜索条件记录，读取本地记录中当前页面对应的频道初始化页面(暂时只开放二手房频道)
        if(channel == "ershoufang") {
            var saveSearch_local_byChannel = [];
            if (saveSearch_local) {
                $.each(saveSearch_local, function (index, node) {
                    if (node.channel == channel) {
                        saveSearch_local_byChannel.push({
                            id: node.id,
                            url: "/" + node.url,
                            filter: node.filter,
                            type: node.type
                        });
                    }
                });
            }
            setSaveSearchUI(channel, {total: saveSearch_local_byChannel.length, list: saveSearch_local_byChannel});
        }
    }
}

//搜索条件超长处理（最大占位不超过430px，个数最多不超过6个）
function filterShortCut(data){
    $.each(data, function(index, node){
        var listPx = 0;
        node.filterShortCut = [];

        $.each(node.filter, function(i, str){//求字符长度（按全角字符算，一个全角等于两个半角）
            var tempLen = 0;
            for(var j=0; j<str.length; j++){
                charCode = str.charCodeAt(j);
                tempLen += (charCode >= 0 && charCode <= 128) ? 0.5 : 1;
            }
            listPx += tempLen*13+2*6+4; //每个标签长度累加
            if(listPx >= 430){
                node.hasMore = true;
                return false;
            }
            else{
                node.filterShortCut.push(str);
            }
        });
    });
}
//顶部搜索区域已保存搜索UI初始化：
function setSaveSearchUI(channel, data, z){
    //输入框右侧的提示链接生成
    if(data.total != 0){
        $("#saveSearchTotal").show().find("span").text(data.total);

        filterShortCut(data.list);//搜索条件超长处理

        //“已保存搜索”弹层内容生成
        $("#"+channel+" .js_saveSearch").setTemplateElement("template_saveSearchList");
        $("#"+channel+" .js_saveSearch").processTemplate(data);
    }
    else{
        $("#saveSearchTotal").hide();
    }
}

//从服务端获取已保存搜索数据
function getSaveSearchDataFromServer(channel, callback, opts){
    if(!headerParameters.loginUserInfo.data || headerParameters.loginUserInfo.data && !headerParameters.loginUserInfo.data.token) return false;//如果获取不到token，则不发请求

    var callback = callback || null;
    var opts = opts || null;
    //从服务端获取用户保存的搜索条件，并初始化页面
    var access_token = '7poanTTBCymmgE0FOn1oKp';
    var postData = {
        sh_access_token : headerParameters.loginUserInfo.data.token,
        cityCode : headerParameters.cityCode,
        channel : channel,
        dataCount : 20,
        client : 'pc'
    }
    $.ajax({
        type : "get",
        url : headerParameters.apihost + '/api/v4/online/search/getLastUserSearch?access_token='+access_token+"&v="+Number(new Date()),
        data : postData,
        dataType : "json",
        success: function(response,status){
            if(response.status == 'ok'){
                if(response.data.length != 0){

                    $.each(response.data, function(index, node){
                        if(!(opts && opts.isAppendData)){
                            saveSearchDataInit[channel][node.id] = node.filter;
                        }
                        node.filter = node.filter.split(",");
                    });

                    if(callback){
                        callback(response);
                    }
                }
                if(!(opts && opts.isAppendData)) {
                    setSaveSearchUI(channel, {
                        login: true,
                        total: response.total,
                        list: response.data
                    });
                }
            }
        },
        error : function(response,status){
            console.log(response);
        }
    });
}

//已保存搜索［删除］链接点击事件 － UI调整
function delSaveSearchFilter(searchId, channel){
    if($("#"+channel+" .filter_del").length == 1){//只有一条记录，则直接隐藏弹层和n条已保存搜索链接
        $("#"+channel+" div.list").empty();
        $("#saveSearchTotal").hide();//n条已保存搜索链接隐藏
        $("body").trigger("click");//关闭弹层
        $("#btn_saveSearch").removeClass("disabled");
    }
    else{//有多条记录，则折叠收起被删除条目，n条已保存搜索链接上的数值更新
        var delItem = $("#searchId_"+searchId);
        var delItemLine = delItem.next("li.line").length == 1 ? delItem.next("li.line") : delItem.prev("li.line");
        delItem.slideUp(); //条目收起
        window.setTimeout(function(){
            delItem.remove();//条目及对应的分隔线清理
            delItemLine.remove();
            $("#saveSearchTotal span").text(parseInt($("#saveSearchTotal span").text())-1); //n条已保存搜索链接数值更新
        }, 300);
    }
}

$(function(){
    //［n条已保存搜索］链接点击事件
    $(".txt-serach").on("click", "#saveSearchTotal", function(e){
        stopBubble(e);
        if($("#lianjia-header").attr("scheme")=="home"){//首页
            var channel = $("a.js_menu_tab.check").attr("actdata");
        }
        else{//内页
            var channel = $("#channelChked").attr("actdata");
        }

        $("#saveSearchTotal").addClass("open");
        $("#suggest-cont, #hot-sug ul").css({
            "display" : "none"
        });
        $("#hot-sug, #"+channel+", #"+channel+" .js_saveSearch").css({
            "display" : "block"
        });
        $("#"+channel+" .js_history, #"+channel+" .js_hot").css({
            "display" : "none"
        });
    });

    //已保存搜索［删除］链接点击事件 － 数据处理
    $("#hot-sug div.js_saveSearch").on("click", ".filter_del", function(){
        if($("#lianjia-header").attr("scheme")=="home"){//首页
            var channel = $(".js_menu_tab.check").attr("actdata");
        }
        else{//内页
            var channel = $("#channelChked").attr("actdata");
        }

        var searchId = $(this).attr("searchid");
        if($(this).attr("type") == "local"){//本地删除
            var saveSearch_local = localStorageFunObj.getLocalStorage("data_saveSearch");
            var i;
            $.each(saveSearch_local, function(index, node){
                if(node.id == searchId){
                    i = index;
                }
            });
            if(i != null){
                saveSearch_local.splice(i,1);
            }
            if(saveSearch_local.length > 0){
                localStorageFunObj.setLocalStorage("data_saveSearch", saveSearch_local);
            }
            else{
                localStorageFunObj.removeLocalStorage("data_saveSearch");
            }
            delSaveSearchFilter(searchId, channel);
        }
        else{//服务端删除
            if(headerParameters.loginUserInfo.data && headerParameters.loginUserInfo.data.token){
                var access_token = '7poanTTBCymmgE0FOn1oKp';
                var postData = {
                    sh_access_token : headerParameters.loginUserInfo.data.token,
                    searchId : searchId
                }
                $.ajax({
                    type : "post",
                    url : headerParameters.apihost + '/api/v4/online/deleteUserSearch?access_token='+access_token+"&v="+Number(new Date()),
                    data : postData,
                    dataType : "json",
                    success: function(response,status){
                        if(response.status == 'ok'){
                            delete saveSearchDataInit[channel][searchId]; //中间变量数组里对应的值清除
                            preloadSaveSearch(channel, parseInt($("#saveSearchTotal").text())-1);//预加载处理
                            delSaveSearchFilter(searchId, channel);//UI交互
                        }
                    },
                    error : function(response,status){
                        console.log(response);
                    }
                });
            }
        }
    });

    function preloadSaveSearch(channel, total){
        //已保存搜索弹层只显示最新的10条，实际请求时请求20条，当删除使条数少于等于15条时，再预加载5条
        var filterListCount = 0;
        for(var key in saveSearchDataInit[channel]){
            filterListCount += 1;
        }
        if(filterListCount <= 15){
            if(total <= 15) return false;

            getSaveSearchDataFromServer(channel, function(response){
                response.data.splice(0, filterListCount);//实际请求的仍旧是前20条，比对后取不重复的部分补足20条
                $.each(response.data, function(index, node){
                    saveSearchDataInit[channel][node.id] = node.filter.toString();
                });
                filterShortCut(response.data);//搜索条件超长处理

                //“已保存搜索”弹层生成
                $("body").append('<div style="display:none" id="templateCacheWrap"></div>');
                window.setTimeout(function(){
                    $("#templateCacheWrap").setTemplateElement("template_saveSearchPreload");
                    $("#templateCacheWrap").processTemplate({list:response.data});
                    $("#"+channel+" .js_saveSearch .list").append($("#templateCacheWrap").html());
                    window.setTimeout(function(){
                        $("#templateCacheWrap").remove();
                    },0);
                },0);
            }, {isAppendData:true});
        }
    }
});

