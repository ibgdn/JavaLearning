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
<p>setContext</p>
<%--pageContext--%>
<p>pageContext 可以在当前页面访问</p>
<% pageContext.setAttribute("context", "pageContext");%>
<%= pageContext.getAttribute("context")%>
<br><br>
<%--requestContext --%>
<p>requestContext 可以在当前页面访问</p>
<% request.setAttribute("request","requestContext");%>
<%= request.getAttribute("request")%>
<%--下边的示例有客户端跳转会产生多于一次的 request 无法在 getContext.jsp 页面访问--%>
<br><br>
<%--requestContext与服务端跳转 --%>
<% request.setAttribute("request-server","服务端跳转"); %>
<%--<jsp:forward page="getContext.jsp"/>--%>    <%--放开此行会导致下边的示例无法在 getContext.jsp 页面取值，
                                                    注释掉会变成客户端跳转，无法在 getContext.jsp 取值--%>
<br><br>
<%--requestContext与客户端跳转 --%>
<% request.setAttribute("request-client","requestContext与客户端跳转");
//    response.sendRedirect("getContext.jsp");  // 放开此行会导致下边的示例无法在 getContext.jsp 页面取值
%>
<%-- sessionContext --%>
<% session.setAttribute("sessions","sessionContext");
//    response.sendRedirect("getContext.jsp");  // // 放开此行会导致下边的示例无法在 getContext.jsp 页面取值
%>
<br><br>
<%--applicationContext--%>
<% application.setAttribute("application","applicationContext");
    System.out.println(application == request.getServletContext());
    response.sendRedirect("getContext.jsp");%>
</body>
</html>
