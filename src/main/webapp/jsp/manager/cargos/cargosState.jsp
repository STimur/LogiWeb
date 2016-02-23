<%@ page import="com.tsystems.javaschool.timber.logiweb.service.OrderService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.jpa.OrderDaoJpa" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.RoutePoint" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.Driver" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.impl.OrderServiceImpl" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.Cargo" %><%--
  Created by IntelliJ IDEA.
  User: tims
  Date: 2/17/2016
  Time: 10:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cargos State Page</title>
    <link rel="stylesheet" href="../../css/bootstrap/flatly.css">
    <link rel="stylesheet" href="/css/logiweb.css">
</head>
<body>
<jsp:include page="/navbar.jspf"/>
<div class="container">
    <% List<Cargo> cargos = (List<Cargo>) request.getAttribute("cargos");
        if (cargos == null) { %>
    <p>You are not allowed to get pure <b>cargosState.jsp</b> without passing the controller!</p>
    <% } %>
    <h2>Cargos State Table</h2>
    <p>Current cargos:</p>
    <table class="table table-bordered table-striped table-hover">
        <tr>
            <th>Id</th>
            <th>Ready</th>
        </tr>
        <% for (Cargo cargo : cargos) { %>
        <tr>
            <td><%= cargo.getId() %></td>
            <td><%= cargo.getState().toString() %></td>
        </tr>
        <% } %>
    </table>
</div>
<jsp:include page="/footer.jspf"/>
</body>
</html>
