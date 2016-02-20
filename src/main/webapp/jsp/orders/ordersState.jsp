<%@ page import="com.tsystems.javaschool.timber.logiweb.service.OrderService" %>
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
    <title>Orders State Page</title>
    <link rel="stylesheet" href="../../css/bootstrap/flatly.css">
    <link rel="stylesheet" href="/css/logiweb.css">
</head>
<body>
<jsp:include page="/navbar.jspf"/>
<div class="container">
    <% List<Order> orders = (List<Order>) request.getAttribute("orders");
        if (orders == null) { %>
    <p>You are not allowed to get pure <b>ordersState.jsp</b> without passing the controller!</p>
    <% } %>
    <h2>Orders State Table</h2>
    <p>Current orders:</p>
    <table class="table table-bordered table-striped table-hover">
        <thead>
        <tr>
            <th>Id</th>
            <th>Ready</th>
        </tr>
        </thead>
        <tbody>
        <% for (Order order : orders) { %>
        <tr>
            <td><%= order.getId() %></td>
            <td><%= (order.isFinished()) ? "Yes" : "No"%></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
