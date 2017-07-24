<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/24
  Time: 10:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>getCookie</title>
</head>
<body>
    <% Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie c : cookies){
                out.println(c.getName() + ":" + c.getValue() + "<br>");
            }
        }
    %>
</body>
</html>
