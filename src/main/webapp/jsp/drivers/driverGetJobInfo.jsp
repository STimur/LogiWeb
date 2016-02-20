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
    <title>Driver Get Job Info</title>
    <link rel="stylesheet" href="../../css/bootstrap/flatly.css">
    <link rel="stylesheet" href="/css/logiweb.css">
</head>
<body>
<jsp:include page="/navbar.jspf"/>
<div class="container col-md-2 col-md-offset-5">
    <h2>Add Truck</h2>
    <form method="post" action="/Driver">
        <fieldset class="form-group">
            <label for="id">Truck registration number</label>
            <input type="text" class="form-control" id="id" name="id"
                   placeholder="Enter driver personal number">
        </fieldset>
        <button type="submit" class="btn btn-success" name="action" value="getJobInfo">Get Job Info</button>
    </form>
</div>
</body>
</html>
