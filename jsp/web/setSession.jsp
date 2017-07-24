<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/24
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>setSession</title>
</head>
<body>
<%session.setAttribute("session", "team");%>
<a href="getSession.jsp">跳转到获取 Session 的页面</a>
</body>
</html>