/**
 * Created by colin on 16/8/29.
 */


(function(headerParameters, init){

    //版本号
    var now = new Date(),
        versionNo = now.getFullYear() + '' + (now.getMonth() + 1) + '' + now.getDate();

    /********上海、苏州*******/

    //ubt
    var ubtScript=document.createElement("script");
    ubtScript.src= headerParameters.publichost + "/public/js/ljubt.min.js?v=" + versionNo, document.getElementsByTagName("head")[0].appendChild(ubtScript);

    //GA统计
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
            (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
        m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script', headerParameters.publichost + '/public/js/analytics.js','ga');

    ga('create', 'UA-73846703-1', 'auto');
    ga('send', 'pageview');

    //新增账号, pc和wap公用
    ga('create', 'UA-83726672-1', 'auto', {'name': 'u'});
    ga('u.send', 'pageview');

    if(headerParameters.cityCode == "su"){//苏州单独用的GA账户
        ga('create', 'UA-85639643-1', 'auto', {'name': 'su'});
        ga('su.send', 'pageview');
    }

    //Google Tag Manager
    $('body').append('<iframe src="//www.googletagmanager.com/ns.html?id=GTM-TK48MZ" height="0" width="0" style="display:none;visibility:hidden"></iframe>');
    (function(w,d,s,l,i){
            w[l]=w[l]||[];
            w[l].push({'gtm.start': new Date().getTime(),event:'gtm.js'});
            var f=d.getElementsByTagName(s)[0],
                j=d.createElement(s),
                dl=l!='dataLayer'?'&l='+l:'';
            j.async=true;
            //原地址: //www.googletagmanager.com/gtm.js
            j.src= 'http://www.googletagmanager.com/gtm.js?id='+i+dl;
            f.parentNode.insertBefore(j,f);
    })(window,document,'script','dataLayer','GTM-TK48MZ');

    

    //非产品环境, 其他监控工具不记录监控
    if(headerParameters.env !== 'production'){
        return;
    }

    //北京监控
    window.__UDL_CONFIG={"pid":"lianjiaweb"};
    var ulogScript=document.createElement("script");
    ulogScript.src="http://dig.lianjia.com/lianjiaUlog.js",document.getElementsByTagName("head")[0].appendChild(ulogScript);


    //growingIO统计
    var _vds = _vds || [];
    _vds.push(['enableHT', true]);
    window._vds = _vds;
    (function(){
        _vds.push(['setAccountId', '970bc0baee7301fa']);
        
        //记录用户ID
        if($.cookie("lianjia_userId")){
            _vds.push(['setCS1', 'userid', $.cookie("lianjia_userId")]);
        }
        if($.cookie("lianjia_agentId")){
            _vds.push(['setCS1', 'userid', $.cookie("lianjia_agentId")]);
            _vds.push(['setCS3', 'agentid', $.cookie("lianjia_agentId")]);
        }
        if($.cookie("ubta")){
            var ubtArray = $.cookie("ubta").split(".");
            if(ubtArray && ubtArray[1]){
                var ubthid = ubtArray[1];
                if(ubthid){
                    _vds.push(['setCS4', 'ubthid', ubthid]);
                }
            }
        }

        //记录页面业务参数
        //页面存放业务参数的数据格式示例：
        // var init = {
        //     gio : {
        //         "setPageGroup" : "二手房", 
        //         "setPS1" : "sh12123123"  //房源ID
        //     }
        // }
        if(init && init.gio){
            for(var key in init.gio){
                _vds.push([key, init.gio[key]]);
            }
        }

        (function() {
            var vds = document.createElement('script');
            vds.type='text/javascript';
            vds.async = true;
            vds.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'dn-growing.qbox.me/vds.js';
            var s = document.getElementsByTagName('script')[0];
            s.parentNode.insertBefore(vds, s);
        })();
    })();
}(window.headerParameters, window.init));