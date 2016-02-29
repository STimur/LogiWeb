<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.jpa.CityDaoJpa" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.CityService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.City" %>
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
    <title>Add Truck</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
</head>
<body>
<%!
    static CityService cityService = new CityServiceImpl(new CityDaoJpa(City.class));
    static List<City> cities = cityService.findAll();
%>
<c:import url="/navbar.jspf">
    <c:param name="activeTab" value="Trucks"/>
</c:import>
<div class="container">
    <h2>Add Truck</h2>
    <form method="post" action="${pageContext.request.contextPath}/Truck">
        <fieldset class="form-group">
            <label for="regNumber">Truck registration number</label>
            <input type="text" class="form-control" id="regNumber" name="regNumber"
                   placeholder="Enter truck registration number">
            <c:if test="${not empty plateNumberFormatException}">
                <div class="validationError">
                    <span class="text-danger">Plate number must be 2 latin letters followed by 5 numbers, e.g. &quot;AB12345&quot;</span>
                </div>
            </c:if>
        </fieldset>
        <fieldset class="form-group">
            <label for="shiftSize">Shift size</label>
            <input type="text" class="form-control" id="shiftSize" name="shiftSize"
                   placeholder="Enter truck shift size">
            <c:if test="${not empty shiftSizeOutOfRangeException}">
                <div class="validationError">
                    <span class="text-danger">Shift size should be a number between 1 and 4</span>
                </div>
            </c:if>
        </fieldset>
        <fieldset class="form-group">
            <label for="capacity">Capacity</label>
            <input type="text" class="form-control" id="capacity" name="capacity" placeholder="Enter truck capacity">
            <c:if test="${not empty capacityOutOfRangeException}">
                <div class="validationError">
                    <span class="text-danger">Capacity should be a number between 10 and 40</span>
                </div>
            </c:if>
        </fieldset>
        <fieldset class="form-group">
            <label for="state">Truck state</label>
            <select class="form-control" id="state" name="state">
                <option value="OK">OK</option>
                <option value="BROKEN">Broken</option>
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
        <button type="submit" class="btn btn-success" name="action" value="create">Add Truck</button>
    </form>
</div>
<jsp:include page="/footer.jspf"/>
</body>
</html>
