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
    <title>Orders Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
</head>
<body>
<c:import url="/jspf/navbar.jspf">
    <c:param name="activeTab" value="Orders"/>
</c:import>
<div class="container">
    <h2>Orders</h2>
    <div class="text-right">
        <form method="post" action="${pageContext.request.contextPath}/Order">
            <button type="submit" class="btn btn-primary btn-success" name="action" value="add">Add Order</button>
        </form>
    </div>
    <table id="ordersTable" class="table table-bordered table-striped table-hover">
        <tr>
            <th>Id</th>
            <th>Ready</th>
            <th>Route</th>
            <th>Truck</th>
            <th>Drivers</th>
            <th>Actions</th>
        </tr>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.getId()}
                </td>
                <td>${(order.isFinished()) ? "Yes" : "No"}
                </td>
                <td>
                    <ol>
                        <c:forEach items="${order.formRouteAsList()}" var="routePoint">
                            <li>${routePoint}</li>
                        </c:forEach>
                    </ol>
                </td>
                <td>${order.getAssignedTruck().getRegNumber()}</td>
                <td>
                    <ol>
                        <c:forEach items="${order.getAssignedDrivers()}" var="driver">
                            <li>${driver}</li>
                        </c:forEach>
                    </ol>
                </td>
                <td class="buttonsCell">
                    <form class="form-inline" method="post" action="${pageContext.request.contextPath}/Order">
                        <button type="submit" class="btn btn-primary btn-danger" name="action" value="deleteOrder">
                            Remove
                        </button>
                        <input type="hidden" name="id" value="${order.getId()}"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
<jsp:include page="/jspf/footer.jspf"/>
</body>
</html>
