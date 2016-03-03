<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.CityDaoJpa" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.interfaces.CityService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.persistence.entity.City" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.persistence.entity.Truck" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.interfaces.TruckService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.TruckDaoJpa" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.impl.CityServiceImpl" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.util.Services" %>
<%--
  Created by IntelliJ IDEA.
  User: tims
  Date: 2/19/2016
  Time: 2:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Truck</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
</head>
<body>
<%!
    static CityService cityService = Services.getCityService();
    static List<City> cities = cityService.findAll();
%>
<c:import url="/navbar.jspf">
    <c:param name="activeTab" value="Trucks"/>
</c:import>
<div class="container">
    <h2>Edit Truck</h2>
    <%
        Truck truck = (Truck) request.getAttribute("truckToEdit");
        String selectOkState;
        String selectBrokenState;
        if (truck.getState().equals("OK")) {
            selectOkState = "selected";
            selectBrokenState = "";
        } else {
            selectOkState = "";
            selectBrokenState = "selected";
        }
    %>
    <form id="editTruckForm" method="post" action="${pageContext.request.contextPath}/Truck">
        <fieldset class="form-group">
            <label for="regNumber">Truck registration number</label>
            <input type="text" class="form-control" id="regNumber" name="regNumber"
                   placeholder="Enter truck registration number"
                   value="${not empty truckValidationException ? truckValidationException.getPlateNumberValidationUnit().getInputValue() : truckToEdit.getRegNumber()}">
            <c:if test="${not empty truckValidationException && !truckValidationException.getPlateNumberValidationUnit().isValid()}">
                <div class="validationError">
                    <span class="text-danger">Enter 2 letters plus 5 numbers, e.g. &quot;AB12345&quot;</span>
                </div>
            </c:if>
        </fieldset>
        <fieldset class="form-group">
            <label for="shiftSize">Shift size</label>
            <input type="text" class="form-control" id="shiftSize" name="shiftSize"
                   placeholder="Enter truck shift size"
                   value="${not empty truckValidationException ? truckValidationException.getShiftSizeValidationUnit().getInputValue() : truckToEdit.getShiftSize()}">
            <c:if test="${not empty truckValidationException && !truckValidationException.getShiftSizeValidationUnit().isValid()}">
                <div class="validationError">
                    <span class="text-danger">Shift size should be a number between 1 and 4</span>
                </div>
            </c:if>
        </fieldset>
        <fieldset class="form-group">
            <label for="capacity">Capacity</label>
            <input type="text" class="form-control" id="capacity"
                   name="capacity" placeholder="Enter truck capacity"
                   value="${not empty truckValidationException ? truckValidationException.getCapacityValidationUnit().getInputValue() : truckToEdit.getCapacity()}">
            <c:if test="${not empty truckValidationException && !truckValidationException.getCapacityValidationUnit().isValid()}">
                <div class="validationError">
                    <span class="text-danger">Should be a number between 10 and 40</span>
                </div>
            </c:if>
        </fieldset>
        <fieldset class="form-group">
            <label for="state">Truck state</label>
            <select class="form-control" id="state" name="state">
                <option value="OK" <%=selectOkState%>>OK</option>
                <option value="BROKEN" <%=selectBrokenState%>>Broken</option>
            </select>
        </fieldset>
        <fieldset class="form-group">
            <label for="city">City</label>
            <select class="form-control" id="city" name="cityId">
                <%
                    int truckCityId = truck.getCity().getId();
                    for (City city : cities) {
                        String selected = (truckCityId == city.getId()) ? "selected" : "";
                %>
                <option value="<%= city.getId()%>" <%=selected%>>
                    <%= city.getName() %>
                </option>
                <% } %>
            </select>
        </fieldset>
        <button type="submit" class="btn btn-success" name="action" value="update">Save Changes</button>
        <input type="hidden" name="id" value="<%=truck.getId()%>">
    </form>
</div>
<jsp:include page="/footer.jspf"/>
</body>
</html>
