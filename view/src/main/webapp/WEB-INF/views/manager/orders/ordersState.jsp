<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: tims
  Date: 2/17/2016
  Time: 10:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders State Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
</head>
<body>
<c:import url="/jspf/navbar.jspf">
    <c:param name="activeTab" value="OrdersState"/>
</c:import>
<div class="container">
    <c:choose>
        <c:when test="${empty orders}">
            <p>You are <b>not logged in</b> and not allowed to get orders state.</p>
        </c:when>
        <c:otherwise>
            <h2>Orders State</h2>
            <table id="ordersStateTable" class="table table-bordered table-striped table-hover">
                <tr>
                    <th class="col-md-5">Id</th>
                    <th class="col-md-7">Ready</th>
                </tr>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>${order.getId()}</td>
                        <td>${(order.isFinished()) ? "Yes" : "No"}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="/jspf/footer.jspf"/>
</body>
</html>
