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
    <title>Driver Get Job Info</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
</head>
<body>
<c:import url="/navbar.jspf">
    <c:param name="activeTab" value="GetJobInfo"/>
</c:import>
<div class="container">
    <h2>Enter driver personal number</h2>
    <form id="getJobInfoForm" method="post" action="${pageContext.request.contextPath}/Driver">
        <fieldset class="form-group">
            <input type="text" class="form-control" id="id" name="id"
                   placeholder="Enter driver personal number">
            <c:if test="${not empty driverIdNotNumberException}">
                <div class="validationError">
                    <span class="text-danger">Personal number should be more or equal to 1.</span>
                </div>
            </c:if>
        </fieldset>
        <button type="submit" class="btn btn-success" name="action" value="getJobInfo">Get Job Info</button>
    </form>
</div>
<jsp:include page="/footer.jspf"/>
</body>
</html>
