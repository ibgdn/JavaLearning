<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/7/24
  Time: 9:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--在页面中使用JSTL需要在jsp中 通过指令进行设置--%>
<%--prefix="c" 表示后续的标签使用都会以<c: 开头--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%--fmt 标签常用来进行格式化，其中fmt:formatNumber用于格式化数字使用之前要加上--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<html>
  <head>
    <title>index</title>
  </head>
  <body>
  c:set——<c:set var="cname" value="${'cvalue'}" scope="request"/><br>
  <%--相当于--%>
  request.setAttribute——<% request.setAttribute("rname","rvalue");%><br>
  <br>
  c:out——<c:out value="${cname}"/><br>
  <%--相当于--%>
  request.getAttribute——<%= request.getAttribute("rname")%><br>
  <br>
  c:remove——<c:remove var="cname" scope="request"/><br>
  <%--相当于--%>
  request.removeAttribute——<% request.removeAttribute("rname");%><br>
  <br>
  c:out——<c:out value="${cname}"/><br>
  <%--相当于--%>
  request.getAttribute——<%= request.getAttribute("rname")%><br>
  <hr>

  <c:set var="hl" value="${5}" scope="request"/>
  <c:if test="${hl<10}"><p>马上要挂掉了</p></c:if>
  <c:set var="hl" value="${20}" scope="request"/>
  <c:if test="${!(hl<10)}"><p>又抢救回来了</p></c:if>
  <% pageContext.setAttribute("weapon",null);
      pageContext.setAttribute("lastWords","");
      pageContext.setAttribute("inventory",new ArrayList<String>());%>
  <c:if test="${empty weapon}"><p>没有武器</p></c:if>
  <c:if test="${empty lastWords}"><p>没有遗言</p></c:if>
  <c:if test="${empty inventory}"><p>物品栏为空</p></c:if>
  <hr>
  <c:choose>
      <c:when test="${hp>10}"><p>已经抢救</p></c:when>
      <c:otherwise><p>急需抢救</p></c:otherwise>
  </c:choose>
  <hr>
  <% List<String> humans = new ArrayList<>();
        humans.add("Sykes"); humans.add("Sweeney"); humans.add("Sutton");
        request.setAttribute("humans",humans);%>
  <c:out value="使用jsp中的for循环来遍历List"/>
  <table width="200px" align="center" border="1" cellspacing="0">
      <tr><td>编号</td><td>姓名</td></tr>
      <% int i = 0; for(String h : humans){ i++; %>
      <tr><td><%=i%></td><td><%=h%></td></tr>
      <%}%>
  </table>
  <br>
  <c:out value="使用JSTL中的c:forEach 循环来遍历List"/>
  <table width="200px" align="center" border="1" cellspacing="0">
      <tr><td>编号</td><td>姓名</td></tr>
      <c:forEach items="${humans}" var="hj" varStatus="st">
          <tr><td><c:out value="${st.count}"/></td><td><c:out value="${hj}"/></td></tr>
      </c:forEach>
  </table>
  <hr>
  <p>拆分字符串</p>
  <c:set var="names" value="Sykes,Sweeney|Sutton" scope="request"/>
  <c:forTokens items="${names}" delims=",|" var="name">
      <c:out value="${name}"/><br>
  </c:forTokens>
  <hr>
    <c:set var="money" value="888.8888"/>
    <c:set var="pi" value="3.141592653589323433383279502"/>
    最多两个小数点：<fmt:formatNumber value="${money}" maxFractionDigits="2"/><br>
    最少二十位个小数点：<fmt:formatNumber value="${pi}" minFractionDigits="20"/>
  <hr>
    <% Date now = new Date();
        pageContext.setAttribute("now",now);%>
    完整日期：<fmt:formatDate value="${now}" pattern="G yyyy年MM月dd日 E"/><br>
    完整时间：<fmt:formatDate value="${now}" pattern="a HH:mm:ss.S z"/><br>
    常见格式：<fmt:formatDate value="${now}" pattern="yyyy-MM-dd HH:mm:ss"/>
  <hr>
  </body>
</html>
