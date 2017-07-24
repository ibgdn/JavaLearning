<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/24
  Time: 10:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.Cookie" %>
<html>
<head>
    <title>setCookie</title>
</head>
<body>
    <% Cookie cookie = new Cookie("name","Green");
        cookie.setMaxAge(20);
        cookie.setPath("127.0.0.1");
        response.addCookie(cookie); %>
<a href="getCookie.jsp">跳转到获取 Cookie 的页面</a>
</body>
</html>
