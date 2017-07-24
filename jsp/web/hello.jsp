<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/24
  Time: 9:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head>
    <title>你好——hello</title>
</head>
<body>
你好 JSP<br>
<%= LocalDateTime.now().toString()%><br><br>
<% List<String> words = new ArrayList<>();
    words.add("The");
    words.add("day");
    words.add("is");
    words.add("a");
    words.add("great");
    words.add("day."); %>

<table width="200px" align="center" border="1" cellspacing="0">
    <%for (String w : words) {%>
    <tr>
        <td><%=w%>
        </td>
    </tr>
    <%}%>
</table>

<br><br>
<%@include file="footer.jsp" %>
<br><br>
<jsp:include page="footer.jsp">
    <jsp:param name="year" value="2017"/>
</jsp:include>
</body>
</html>
