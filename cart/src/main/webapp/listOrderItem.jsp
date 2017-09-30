<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/9/30
  Time: 11:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>listOrderItem</title>
</head>
<body>
    <h1 align="center" >购物车</h1>
    <table align="center" border="1" cellspacing="0">
        <tr>
            <td>商品名称</td>
            <td>单价</td>
            <td>数量</td>
            <td>合计</td>
        </tr>
        <c:forEach var="orderItem" items="${ois}" varStatus="st">
            <tr>
                <td>${orderItem.product.id}</td>
                <td>${orderItem.product.price}</td>
                <td>${orderItem.num}</td>
                <td>${orderItem.product.price * orderItem.num}</td>
            </tr>
        </c:forEach>
        <c:if test="${!empty ois}">
            <tr>
                <td colspan="4" align="right">
                    <a href="createOrder">生成订单</a>
                </td>
            </tr>
        </c:if>
    </table>
</body>
</html>
