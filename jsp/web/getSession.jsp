<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/24
  Time: 10:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>getSession</title>
</head>
<body>
<% String value = (String) session.getAttribute("session");%>
session 中的 value ：<%=value%>
</body>
</html>