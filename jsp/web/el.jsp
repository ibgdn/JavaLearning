<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/26
  Time: 14:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.jsp.bean.Human"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<html>
<head>
    <title>el</title>
</head>
<body>
    <c:set var="name" value="${'value'}" scope="request"/>
    通过c标签取值：<c:out value="${name}"/><br>
    通过el表达式取值：${name}
    <br><br><hr>
    <%--EL会按照从小到大的优先级顺序获取 pageContext > request > session > application --%>
    <c:set var="cname" value="${'pageContext'}" scope="page"/>
    <c:set var="cname" value="${'request'}" scope="request"/>
    <c:set var="cname" value="${'session'}" scope="session"/>
    <c:set var="cname" value="${'application'}" scope="application"/>
    el 表达式获取 cname 值：${cname}
    <br><br><hr>
    <%--javaBean--%>
    <% Human human = new Human();
        human.setName("jp");
        human.setAge(22);
        request.setAttribute("human",human); %>
    姓名：${human.name}<br>
    年龄：${human.age}
    <br><br><hr>
    <% List<String> humans = new ArrayList<>();
        humans.add("Sykes"); humans.add("Sweeney"); humans.add("Sutton");
        request.setAttribute("humans",humans);%>
    <table width="200px" align="center" border="1" cellspacing="0">
        <tr><td>编号</td><td>姓名</td></tr>
        <c:forEach items="${humans}" var="h" varStatus="st">
        <tr><td>${st.count}</td><td>${h}</td></tr>
        </c:forEach>
    </table>
    <br><br><hr>
    <%--获取请求参数 请求地址：localhost:8080/el.jsp?name=sv--%>
    ${param.name}
    <br><br><hr>
    <% request.setAttribute("number",22);%>
    c:if
    <c:if test="${number>10}">more than ten.</c:if>
    <c:if test="${number<10}">less than ten.</c:if><br>
    c:choose
    <c:choose>
        <c:when test="${number>10}">more than ten.</c:when>
        <c:otherwise>less than ten.</c:otherwise>
    </c:choose><br>
    el
    ${number ge 10 ? "more than ten.":"less than ten."}
</body>
</html>
