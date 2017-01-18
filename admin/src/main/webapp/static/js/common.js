function showdate(n) {
    var uom = new Date();
    uom.setDate(uom.getDate() + n);
    uom = uom.getFullYear() + "-" + (uom.getMonth() + 1) + "-" + uom.getDate();
    return uom;
}

function StringToDate(DateStr) {


    var converted = Date.parse(DateStr);

    var myDate = new Date(converted);

    if (isNaN(myDate)) {

        //var delimCahar = DateStr.indexOf('/')!=-1?'/':'-';

        var arys = DateStr.split('-');

        myDate = new Date(arys[0], --arys[1], arys[2]);

    }

    return myDate;

}

Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt));
    fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function delHours(date, value) {
    date.setHours(date.getHours() - value);
    return date;
}

