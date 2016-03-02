<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.CityDaoJpa" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.interfaces.CityService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.persistence.entity.City" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.impl.CityServiceImpl" %><%--
  Created by IntelliJ IDEA.
  User: tims
  Date: 2/19/2016
  Time: 2:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Driver</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
</head>
<body>
<%!
    static CityService cityService = new CityServiceImpl(new CityDaoJpa(City.class));
    static List<City> cities = cityService.findAll();
%>
<c:import url="/navbar.jspf">
    <c:param name="activeTab" value="Drivers"/>
</c:import>
<div class="container">
    <h2>Add Driver</h2>
    <form id="addDriverForm" method="post" action="${pageContext.request.contextPath}/Driver">
        <fieldset class="form-group">
            <label for="name">Driver name</label>
            <input type="text" class="form-control" id="name" name="name"
                   placeholder="Enter driver name">
            <c:if test="${not empty notNameException}">
                <div class="validationError">
                    <span class="text-danger">Name should consist only of latin letters.</span>
                </div>
            </c:if>
        </fieldset>
        <fieldset class="form-group">
            <label for="surname">Driver surname</label>
            <input type="text" class="form-control" id="surname" name="surname"
                   placeholder="Enter driver surname">
            <c:if test="${not empty notSurnameException}">
                <div class="validationError">
                    <span class="text-danger">Surname should consist only of latin letters.</span>
                </div>
            </c:if>
        </fieldset>
        <fieldset class="form-group">
            <label for="hoursWorkedThisMonth">Hours of work in this month</label>
            <input type="text" class="form-control" id="hoursWorkedThisMonth"
                   name="hoursWorkedThisMonth" placeholder="How much hours of work driver had this month?">
            <c:if test="${not empty hoursWorkedOutOfRangeException}">
                <div class="validationError">
                    <span class="text-danger">Hours of work should be between 0 and 176.</span>
                </div>
            </c:if>
        </fieldset>
        <fieldset class="form-group">
            <label for="state">Driver state</label>
            <select class="form-control" id="state" name="state">
                <option value="DRIVE" selected>Driving</option>
                <option value="REST">On rest</option>
                <option value="SHIFT">On shift</option>
            </select>
        </fieldset>
        <fieldset class="form-group">
            <label for="city">City</label>
            <select class="form-control" id="city" name="cityId">
                <% for (City city : cities) { %>
                <option value="<%= city.getId()%>">
                    <%= city.getName() %>
                </option>
                <% } %>
            </select>
        </fieldset>
        <button type="submit" class="btn btn-success" name="action" value="create">Add Driver</button>
    </form>
</div>
<jsp:include page="/footer.jspf"/>
</body>
</html>
