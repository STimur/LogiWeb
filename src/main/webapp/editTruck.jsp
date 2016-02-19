<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.jpa.CityDaoJpa" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.CityService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.City" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.Truck" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.TruckService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.jpa.TruckDaoJpa" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.impl.CityServiceImpl" %>
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
    <link rel="stylesheet" href="/css/bootstrap/flatly.css">
    <link rel="stylesheet" href="/css/logiweb.css">
</head>
<body>
<%!
    static CityService cityService = new CityServiceImpl(new CityDaoJpa(City.class));
    static List<City> cities = cityService.findAll();
%>
<jsp:include page="/navbar.jspf"/>
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
    <form method="post" action="/Truck">
        <fieldset class="form-group">
            <label for="regNumber">Truck registration number</label>
            <input type="text" class="form-control" id="regNumber" name="regNumber"
                   placeholder="Enter truck registration number" value="<%=truck.getRegNumber()%>">
        </fieldset>
        <fieldset class="form-group">
            <label for="shiftSize">Shift size</label>
            <input type="text" class="form-control" id="shiftSize" name="shiftSize"
                   placeholder="Enter truck shift size" value="<%=truck.getShiftSize()%>">
        </fieldset>
        <fieldset class="form-group">
            <label for="capacity">Capacity</label>
            <input type="text" class="form-control" id="capacity"
                   name="capacity" placeholder="Enter truck capacity" value="<%=truck.getCapacity()%>">
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
</body>
</html>
