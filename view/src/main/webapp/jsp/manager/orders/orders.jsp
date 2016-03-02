<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.interfaces.OrderService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.jpa.OrderDaoJpa" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.RoutePoint" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.Driver" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.impl.OrderServiceImpl" %><%--
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
<c:import url="/navbar.jspf">
    <c:param name="activeTab" value="Orders"/>
</c:import>
<div class="container">
    <%
        OrderService orderService = new OrderServiceImpl(new OrderDaoJpa(Order.class));

        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("deleteOrder")) {
                int id = Integer.valueOf(request.getParameter("id"));
                orderService.delete(id);
            }
        }
        List<Order> orders = orderService.findAll();
    %>

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
        <% for (Order order : orders) { %>
        <tr>
            <td><%= order.getId() %>
            </td>
            <td><%= (order.isFinished()) ? "Yes" : "No"%>
            </td>
            <td>
                <ol>
                    <%
                        RoutePoint currentPoint = order.getRoute();
                        while (currentPoint != null) {
                    %>
                    <li><%= currentPoint %>
                    </li>
                    <% currentPoint = currentPoint.getNextRoutePoint();
                    } %>
                </ol>
            </td>
            <td><%= order.getAssignedTruck().getRegNumber() %>
            </td>
            <td>
                <ol>
                    <% for (Driver driver : order.getAssignedDrivers()) { %>
                    <li><%= driver %>
                    </li>
                    <% } %>
                </ol>
            </td>
            <td class="buttonsCell">
                <form class="form-inline" method="post" action="${pageContext.request.contextPath}/Order">
                    <button type="submit" class="btn btn-primary btn-danger" name="action" value="deleteOrder">Remove
                    </button>
                    <input type="hidden" name="id" value="<%=order.getId()%>"/>
                </form>
            </td>
        </tr>
        <% } %>
    </table>
</div>
<jsp:include page="/footer.jspf"/>
</body>
</html>
