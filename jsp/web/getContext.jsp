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
    <title>getContext</title>
</head>
<body>
<p>getContext</p>
<%--pageContext--%>
<p>pageContext 无法在当前页面访问</p>
<%= pageContext.getAttribute("context")%>
<br><br>
<%--requestContext --%>
<p>requestContext 可以在当前页面访问</p>
<%= request.getAttribute("request")%>
<br><br>
<%--requestContext与服务端跳转 --%>
<p>requestContext与服务端跳转 可以在当前页面访问</p>
<%= request.getAttribute("request-server")%>
<br><br>
<%--requestContext与客户端跳转 --%>
<p>requestContext与客户端跳转 会产生多于一次的 request 无法在当前页面访问</p>
<%= request.getAttribute("request-client")%>
<br><br>
<%--sessionContext--%>
<p>sessionContext可以在当前页面访问</p>
<%= session.getAttribute("sessions")%>
<%--applicationContext--%>
<p>application可以在当前页面访问</p>
<%= application.getAttribute("application")%>
</body>
</html>
