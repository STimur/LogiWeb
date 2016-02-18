<%@ page import="com.tsystems.javaschool.timber.logiweb.service.OrderService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.OrderDao" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.RoutePoint" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.Driver" %><%--
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
    <link rel="stylesheet" href="css/bootstrap/flatly.css">
</head>
<body>
<div id="ordersTable" class="container">
    <%
        OrderService orderService = new OrderService(new OrderDao());

        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("deleteOrder")) {
                int id = Integer.valueOf(request.getParameter("id"));
                orderService.delete(id);
            }
        }
        List<Order> orders = orderService.findAll();
    %>

    <h2>Orders Table</h2>
    <p>Current orders:</p>
    <div class="text-right">
        <a href="/addOrder.jsp" class="btn btn-primary">New Order</a>
    </div>
    <table class="table table-bordered table-striped table-hover">
        <thead>
        <tr>
            <th>Id</th>
            <th>Ready</th>
            <th>Route</th>
            <th>Truck</th>
            <th>Drivers</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <% for (Order order : orders) { %>
        <tr>
            <td><%= order.getId() %>
            </td>
            <td><%= order.isFinished() %>
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
            <td>
                <form class="form-inline" method="post" action="/orders.jsp">
                    <button type="submit" class="btn btn-primary btn-danger" name="action" value="deleteOrder">Remove
                    </button>
                    <input type="hidden" name="id" value="<%=order.getId()%>"/>
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
