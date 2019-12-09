document.write('<link href="/js/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css">');
document.write('<link href="/js/bootstrap/css/bootstrapValidator.min.css" rel="stylesheet" type="text/css">');
document.write('<script src="/js/jquery/jquery.min.js"  type="text/javascript"></script>');
document.write('<script src="/js/bootstrap/js/bootstrap.min.js"   type="text/javascript"></script>');
document.write('<script src="/js/bootstrap/js/bootstrapValidator.js"   type="text/javascript"></script>');
function getRootPath(){
    var strFullPath=window.document.location.href;
    var strPath=window.document.location.pathname;
    var pos=strFullPath.indexOf(strPath);
    var prePath=strFullPath.substring(0,pos);
    var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
    return(prePath+postPath);
}
function resolvingDate(date){
//date是传入的时间
    var d = new Date(date);

    var month = (d.getMonth() + 1) < 10 ? '0'+(d.getMonth() + 1) : (d.getMonth() + 1);
    var day = d.getDate()<10 ? '0'+d.getDate() : d.getDate();
    var hours = d.getHours()<10 ? '0'+d.getHours() : d.getHours();
    var min = d.getMinutes()<10 ? '0'+d.getMinutes() : d.getMinutes();
    var sec = d.getSeconds()<10 ? '0'+d.getSeconds() : d.getSeconds();

    var times=d.getFullYear() + '-' + month + '-' + day + ' ' + hours + ':' + min ;
      //  + ':' + sec;
    return times
}
/*
* 时间格式化工具
* 把Long类型的1527672756454日期还原yyyy-MM-dd 00:00:00格式日期
*/
function datetimeFormat(longTypeDate){
    var dateTypeDate = "";
    var date = new Date();
    date.setTime(longTypeDate);
    dateTypeDate += date.getFullYear();   //年
    dateTypeDate += "-" + getMonth(date); //月
    dateTypeDate += "-" + getDay(date);   //日
    dateTypeDate += " " + getHours(date);   //时
    dateTypeDate += ":" + getMinutes(date);     //分
    dateTypeDate += ":" + getSeconds(date);     //分
    return dateTypeDate;
}
/*
 * 时间格式化工具
 * 把Long类型的1527672756454日期还原yyyy-MM-dd格式日期
 */
function dateFormat(longTypeDate){
    var dateTypeDate = "";
    var date = new Date();
    date.setTime(longTypeDate);
    dateTypeDate += date.getFullYear();   //年
    dateTypeDate += "-" + getMonth(date); //月
    dateTypeDate += "-" + getDay(date);   //日
    return dateTypeDate;
}
//返回 01-12 的月份值
function getMonth(date){
    var month = "";
    month = date.getMonth() + 1; //getMonth()得到的月份是0-11
    if(month<10){
        month = "0" + month;
    }
    return month;
}
//返回01-30的日期
function getDay(date){
    var day = "";
    day = date.getDate();
    if(day<10){
        day = "0" + day;
    }
    return day;
}
//小时
function getHours(date){
    var hours = "";
    hours = date.getHours();
    if(hours<10){
        hours = "0" + hours;
    }
    return hours;
}
//分
function getMinutes(date){
    var minute = "";
    minute = date.getMinutes();
    if(minute<10){
        minute = "0" + minute;
    }
    return minute;
}
//秒
function getSeconds(date){
    var second = "";
    second = date.getSeconds();
    if(second<10){
        second = "0" + second;
    }
    return second;
}

//字符串去空格
function removeBlank(str){
    if (""== str || null == str)
        return "";
    str   =   str.replace(/^\s+|\s+$/g,"");
    return str;
}