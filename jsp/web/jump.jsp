<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/24
  Time: 10:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>jump</title>
</head>
<body>
    <%--客户端跳转--%>
    <%--<% response.sendRedirect("hello.jsp"); %>--%>

    <%--服务器端跳转--%>
    <%--<% request.getRequestDispatcher("hello.jsp").forward(request,response); %>--%>
    <jsp:forward page="hello.jsp"/>
</body>
</html>
