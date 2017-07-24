<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/24
  Time: 11:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>setContext</title>
</head>
<body>
<% pageContext.setAttribute("context", "con");%>
<%= pageContext.getAttribute("context")%>
<br><br>
<% request.setAttribute("request","req");%>
<%= request.getAttribute("request")%>
<jsp:forward page="getContext.jsp"/>

</body>
</html>
