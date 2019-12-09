<%--
  Created by IntelliJ IDEA.
  User: kass
  Date: 2019/11/14
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="include.jsp"%>
<html>
<head>
    <title>主页</title>
<%--    <link rel="stylesheet" href="/css/fileshow.css"/>--%>
</head>
<body>

<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">文件系统</a>
        </div>
        <div>
            <form class="navbar-form navbar-left" role="search">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-default">搜索</button>
            </form>
            <button type="button" class="btn btn-default navbar-btn" data-toggle="modal" data-target="#myModal">
                上传文件
            </button>
            <button type="button" class="btn btn-default navbar-btn" style="float: right;">
                安全退出
            </button>
        </div>
    </div>
</nav>

<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">我的照片</h3>
    </div>
    <div class="panel-body">

        <div class="container" id="container">
<%--            <img src="cinqueterre.jpg" class="img-responsive" alt="Cinque Terre" width="304" height="236">--%>
        </div>

    </div>
</div>

<div class="panel panel-success">
    <div class="panel-heading">
        <h3 class="panel-title">我的文件</h3>
    </div>
    <div class="panel-body">
        这是一个基本的面板
    </div>
</div>
<%--<div style="float:right;margin-right: 20px;"><a href="/user/logout">退出</a></div>--%>
<%--<div>搜索</div>--%>
<%--<div>--%>

<%--    <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">--%>
<%--        上传文件--%>
<%--    </button>--%>
<%--</div>--%>



<%--<div id="album">--%>
<%--    <p></p>--%>
<%--</div>--%>

<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title" id="myModalLabel">
                    上传文件
                </h4>
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
            </div>
            <div class="modal-body">
                <form id="dialog-form" action="<%=path%>/upload" method="post" enctype="multipart/form-data">
                    <input type="file" name="file" id="filepath"/>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="but-upload">
                    确认上传
                </button>
                <button type="button" class="btn btn-default" data-dismiss="modal">
                    取消
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<script>
    /** 显示文件 **/
    function initMyFilesData(){
        $.post("<%=path%>/getMyfiles",{},function(data){
            console.log(data);
            var files = data.files;
            var img  = null;
            var l = files.length;
            for(var i=0;i<l;i++){
                // img = "<img src='"+files[i]+"' alt=''>";
                var path = files[i];
                // if(path.sub)
                // if(files[i])
                img = "<img src='"+files[i]+"' class='img-responsive' alt='Cinque Terre' width='304' height='236'>";
                $("#container").append(img);
            }
        }, 'json');
    }
    $(function(){
        initMyFilesData();
    });
    /** 文件上传 **/
    $("#but-upload").click(function () {
        var filePath = $("#filepath").val();
        //正则表达式获取文件名，不带后缀.
        var name = filePath.replace(/^.+?\\([^\\]+?)(\.[^\.\\]*?)?$/gi, "$1");

        //正则表达式获取后缀
        var suffix = filePath.replace(/.+\./, "");

        var fileName = name + "." + suffix;
        if ($.trim(filePath) == "") {
            alert("请选择需要上传的文件！");
            return;
        }
        var formData = new FormData($("#dialog-form")[0]);//获取表单中的文件
        $.ajax({
            url:"<%=path%>/upload",//后台的接口地址
            type:"post",//post请求方式
            data:formData,//参数
            cache: false,
            processData: false,
            contentType: false,
            success:function (data) {
                alert(data.msg);
            },error:function () {
                alert("操作失败~");
            }
        })
        $("#filepath").replaceWith('<input id="UploadFile" type="file"/>');
    });
</script>
<script>
    /*旋转分散*/
    // window.onload = function() {
    //     var album = document.getElementById('album'),
    //         aImg = document.querySelectorAll('img');
    //     for (var i = 0; i < aImg.length; i++) {
    //         // 图片旋转分散 36°
    //         aImg[i].style.transform = 'rotateY(' + i * 360 / aImg.length + 'deg) translateZ(300px)';
    //         aImg[i].style.transition = 'transform 1s ' + (aImg.length - i) * 0.1 + 's';
    //     }
    //     var lastX = 0, // 前一次的坐标X
    //         lastY = 0,
    //         nowX = 0, // 当前的坐标X
    //         nowY = 0,
    //         desX = 0,
    //         desY = 0,
    //         rotX = -30,
    //         rotY = 0,
    //         timer; // 时间间隔
    //     document.onmousedown = function(e) {
    //         var e = e || event;
    //         lastX = e.clientX;
    //         lastY = e.clientY;
    //         this.onmousemove = function(e) {
    //             var e = e || event;
    //             nowX = e.clientX;
    //             nowY = e.clientY;
    //             desX = nowX - lastX;
    //             desY = nowY - lastY;
    //             // 更新album的旋转角度，拖拽越快-> des变化大 -> roY变化大 -> 旋转快
    //             rotX -= desY * 0.1;
    //             rotY += desX * 0.2;
    //             album.style.transform = 'rotateX(' + rotX + 'deg) rotateY(' + rotY + 'deg)';
    //             lastX = nowX;
    //             lastY = nowY;
    //         }
    //         document.onmouseup = function() {
    //             this.onmousemove = this.onmouseup = null;
    //             timer = setInterval(function() {
    //                 desX *= 0.95;
    //                 desY *= 0.95;
    //                 rotX -= desY * 0.1;
    //                 rotY += desX * 0.2;
    //                 album.style.transform = 'rotateX(' + rotX + 'deg) rotateY(' + rotY + 'deg)';
    //
    //                 if (Math.abs(desX) < 0.5 && Math.abs(desY) < 0.5) {
    //                     clearInterval(timer);
    //                 }
    //             }, 13)
    //         }
    //         // 阻止默认行为
    //         return false;
    //     }
    // }
</script>
</body>
</html>
