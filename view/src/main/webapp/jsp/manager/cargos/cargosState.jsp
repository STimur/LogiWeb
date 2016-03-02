<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.interfaces.OrderService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.OrderDaoJpa" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.persistence.entity.Order" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.persistence.entity.RoutePoint" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.persistence.entity.Driver" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.impl.OrderServiceImpl" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.persistence.entity.Cargo" %><%--
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
</head>
<body>
<c:import url="/navbar.jspf">
    <c:param name="activeTab" value="Cargos"/>
</c:import>
<div class="container">
    <% List<Cargo> cargos = (List<Cargo>) request.getAttribute("cargos");
        if (cargos == null) { %>
    <p>You are not allowed to get pure <b>cargosState.jsp</b> without passing the controller!</p>
    <% } %>
    <h2>Cargos State</h2>
    <table id="cargoStatesTable" class="table table-bordered table-striped table-hover">
        <tr>
            <th class="col-md-5">Id</th>
            <th class="col-md-7">Ready</th>
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
