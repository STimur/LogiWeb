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
    <title>Orders State Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
</head>
<body>
<c:import url="/navbar.jspf">
    <c:param name="activeTab" value="OrdersState"/>
</c:import>
<div class="container">
    <% List<Order> orders = (List<Order>) request.getAttribute("orders");
        if (orders == null) { %>
    <p>You are not allowed to get pure <b>ordersState.jsp</b> without passing the controller!</p>
    <% } %>
    <h2>Orders State</h2>
    <table id="ordersStateTable" class="table table-bordered table-striped table-hover">
        <tr>
            <th class="col-md-5">Id</th>
            <th class="col-md-7">Ready</th>
        </tr>
        <% for (Order order : orders) { %>
        <tr>
            <td><%= order.getId() %></td>
            <td><%= (order.isFinished()) ? "Yes" : "No"%></td>
        </tr>
        <% } %>
    </table>
</div>
<jsp:include page="/footer.jspf"/>
</body>
</html>
