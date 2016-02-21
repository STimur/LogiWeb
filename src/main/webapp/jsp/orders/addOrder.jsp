<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.jpa.TruckDaoJpa" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.jpa.CityDaoJpa" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.*" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.jpa.DriverDaoJpa" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.jpa.OrderDaoJpa" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.impl.*" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.CityService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.DriverService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.OrderService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.TruckService" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Set" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
  Created by IntelliJ IDEA.
  User: tims
  Date: 2/17/2016
  Time: 4:13 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Order Page</title>
    <link rel="stylesheet" href="/css/bootstrap/flatly.css">
    <link rel="stylesheet" href="/css/logiweb.css">
</head>
<body>
<jsp:include page="/navbar.jspf"/>
<div class="container">
    <%
        List<City> cities = (List<City>) request.getAttribute("cities");
        List<RoutePoint> route = (List<RoutePoint>) request.getAttribute("route");
        List<Truck> trucks = (List<Truck>) request.getAttribute("trucks");
        List<Driver> drivers = (List<Driver>) request.getAttribute("drivers");
        List<Cargo> loadedCargos = (List<Cargo>) request.getAttribute("loadedCargos");
        Order order = (Order) request.getAttribute("order");

        boolean isTruckAssigned = false;
        boolean isValidRoute = Boolean.valueOf((String) request.getAttribute("isValidRoute"));
        String getTrucksButtonVisisbility = isValidRoute ? "" : "invisible";
        boolean isShiftFormed = false;

        if (order != null) {
            if (order.getAssignedTruck() != null)
                isTruckAssigned = true;
            if (isTruckAssigned)
                if (order.getAssignedDrivers() != null)
                    if (order.getAssignedDrivers().size() == order.getAssignedTruck().getShiftSize())
                        isShiftFormed = true;
        }
    %>
    <h2>Add Order Page</h2>
    <form class="form-inline" method="post" action="/Order">
        <div class="form-group">
            <label class="sr-only" for="pointCity">Choose city</label>
            <select class="form-control" id="pointCity" name="pointCity">
                <% for (City city : cities) { %>
                <option value="<%= city.getId()%>">
                    <%= city.getName() %>
                </option>
                <% } %>
            </select>
        </div>
        <div class="form-group">
            <label class="sr-only" for="cargoName">Enter cargo name</label>
            <input type="text" class="form-control" id="cargoName" name="cargoName" placeholder="Enter cargo name">
        </div>
        <div class="form-group">
            <label class="sr-only" for="cargoWeight">Enter cargo weight</label>
            <input type="text" class="form-control" id="cargoWeight" name="cargoWeight"
                   placeholder="Enter cargo weight">
        </div>
        <div class="form-group">
            <label class="sr-only" for="pointType">Point type</label>
            <select class="form-control" id="pointType" name="pointType">
                <option value="LOAD">Load</option>
                <option value="UNLOAD">Unload</option>
            </select>
        </div>
        <button type="submit" class="btn btn-primary" name="action" value="addLoadPoint">Add</button>
    </form>
    <% if (loadedCargos != null) { %>
    <form class="form-inline" method="post" action="/Order">
        <div class="form-group">
            <label>Unload Cargo</label>
        </div>
        <div class="form-group">
            <select class="form-control" id="cargo" name="cargoToUnload">
                <%
                    int size = loadedCargos.size();
                    Cargo loadedCargo;
                    for (int i = 0; i < size; i++) {
                        loadedCargo = loadedCargos.get(i);
                %>
                <option value="<%= loadedCargo.getId()%> <%=i%>">
                    <%= loadedCargo.toString() %>
                </option>
                <% } %>
            </select>
        </div>
        <div class="form-group">
            <label>in</label>
        </div>
        <div class="form-group">
            <label class="sr-only" for="unloadPointCity">Choose city</label>
            <select class="form-control" id="unloadPointCity" name="unloadPointCityId">
                <% for (City city : cities) { %>
                <option value="<%= city.getId()%>">
                    <%= city.getName() %>
                </option>
                <% } %>
            </select>
        </div>
        <button type="submit" class="btn btn-primary" name="action" value="addUnloadPoint">Add</button>
    </form>
    <% }
        if (route != null) { %>
    <h1>Order route consists of <%= route.size()%> points:</h1>
    <ul>
        <% for (RoutePoint routePoint : route) { %>
        <li><%=routePoint.toString()%>
        </li>
        <% } %>
    </ul>
    <% } else { %>
    <h1 class="text-warning">No route points yet!</h1>
    <% } %>
    <form class="form-inline <%=getTrucksButtonVisisbility%>" method="post" action="/Order">
        <button type="submit" class="btn btn-primary" name="action" value="getTrucks">Get Available Trucks</button>
    </form>

    <% if (trucks != null) { %>
    <h2>Assign Truck:</h2>
    <form class="form-inline" method="post" action="/Order">
        <div class="form-group">
            <label class="sr-only" for="truckToAssign">Choose truck</label>
            <select class="form-control" id="truckToAssign" name="truckToAssign">
                <%
                    String selected = "";
                    for (Truck truck : trucks) {
                        if (isTruckAssigned) {
                            selected = (order.getAssignedTruck().getId() == truck.getId()) ? "selected" : "";
                        } %>
                <option value="<%= truck.getId()%>" <%=truck.getId()%> <%=selected%>>
                    <%= truck.getRegNumber() %>
                </option>
                <% } %>
            </select>
        </div>
        <button type="submit" class="btn btn-primary" name="action" value="assignTruck">Assign</button>
    </form>
    <% } %>
    <% if (isTruckAssigned) { %>
    <h2>Assign <%=order.getAssignedTruck().getShiftSize()%> Drivers:</h2>
    <form class="form-inline" method="post" action="/Order">
        <div class="form-group">
            <label class="sr-only" for="driverToAssign">Choose driver(s)</label>
            <select class="form-control" id="driverToAssign" name="driverToAssign">
                <%
                    int size = drivers.size();
                    for (int i = 0; i < size; i++) { %>
                <option value="<%=drivers.get(i).getId() + " " + i%>">
                    <%= drivers.get(i).toString() %>
                </option>
                <% } %>
            </select>
        </div>
        <button type="submit" class="btn btn-primary" name="action" value="assignDriver">Assign</button>
    </form>
    <% if (order.getAssignedDrivers().size() > 0) { %>
    <h2>List of assigned drivers:</h2>
    <ol>
        <% for (Driver driver : order.getAssignedDrivers()) { %>
        <li><%=driver.toString()%></li>
        <% } %>
    </ol>
    <% }} %>
    <% if (isShiftFormed) { %>
    <form class="form-inline" method="post" action="/Order">
        <button type="submit" class="btn btn-primary btn-success" name="action" value="create">Create Order
        </button>
    </form>
    <% } %>

</div>
<jsp:include page="/footer.jspf"/>
</body>
</html>
