<%--
  Created by IntelliJ IDEA.
  User: kass
  Date: 2019/11/14
  Time: 17:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=utf-8"%>
<% String message= (String)request.getAttribute("message"); %>
<html>
<head>
    <title>主页</title>
</head>
<body>
    error:${message}
    <a></a>
</body>
</html>
