<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.jpa.TruckDaoJpa" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.jpa.CityDaoJpa" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.jpa.DriverDaoJpa" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.jpa.OrderDaoJpa" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.impl.*" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.CityService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.DriverService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.OrderService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.TruckService" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.Set" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.*" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
</head>
<body>
<c:import url="/navbar.jspf">
    <c:param name="activeTab" value="Orders"/>
</c:import>
<div class="container">
    <%
        List<City> cities = (List<City>) session.getAttribute("cities");
        List<RoutePoint> route = (List<RoutePoint>) session.getAttribute("route");
        List<Truck> trucks = (List<Truck>) session.getAttribute("trucks");
        List<Driver> drivers = (List<Driver>) session.getAttribute("drivers");
        List<Cargo> loadedCargos = (List<Cargo>) session.getAttribute("loadedCargos");
        Order order = (Order) session.getAttribute("order");

        session.setAttribute("isTruckAssigned", false);
        boolean isValidRoute = Boolean.valueOf((String) session.getAttribute("isValidRoute"));
        String getTrucksButtonVisisbility = isValidRoute ? "" : "invisible";
        session.setAttribute("isShiftFormed", false);

        if (order != null) {
            if (order.getAssignedTruck() != null) {
                session.setAttribute("isTruckAssigned", true);
            }
            if (session.getAttribute("isTruckAssigned").equals(true))
                if (order.getAssignedDrivers() != null)
                    if (order.getAssignedDrivers().size() == order.getAssignedTruck().getShiftSize()) {
                        session.setAttribute("isShiftFormed", true);
                    }
        }
    %>
    <h2>Add Order</h2>
    <c:if test="${!isRouteFormed}">
        <h3>Add cargos for delivery:</h3>
        <form class="form-inline" method="post" action="${pageContext.request.contextPath}/Order">
            <div class="form-group">
                <label class="sr-only" for="pointType">Point type</label>
                <select class="form-control" id="pointType" disabled>
                    <option>Load</option>
                </select>
                <input type="hidden" name="pointType" value="LOAD"/>
            </div>
            <div class="form-group">
                <span style="margin: 0 10px;">in</span>
            </div>
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
            <button type="submit" class="btn btn-primary" name="action" value="addLoadPoint">Add</button>
            <c:if test="${not empty cargoNameException}">
                <div class="validationError">
                    <span class="text-danger">Cargo name should consist only of latin letters.</span>
                </div>
            </c:if>
            <c:if test="${not empty cargoWeightOutOfRangeException}">
                <div class="validationError">
                    <span class="text-danger">Cargo weight should be between 1 and 40000.</span>
                </div>
            </c:if>
        </form>

        <c:choose>
            <c:when test="${not empty loadedCargos}">
                <form class="form-inline" method="post" action="${pageContext.request.contextPath}/Order">
                    <div class="form-group">
                        <select class="form-control" disabled>
                            <option>Unload</option>
                        </select>
                    </div>
                    <span style="margin: 0 10px;">cargo</span>
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
                    <span style="margin: 0 10px;">in</span>
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
            </c:when>
            <c:otherwise>
                <h4 class="text-warning">No cargos to unload!</h4>
            </c:otherwise>
        </c:choose>
    </c:if>
    <c:choose>
        <c:when test="${not empty route}">
            <h3>Current route:</h3>
            <ul>
                <c:forEach var="i" begin="0" end="${route.size()-1}">
                    <li>
                        <c:out value="${route.get(i)}"/>
                    </li>
                </c:forEach>
            </ul>
            <c:if test="${!isValidRoute}">
                <h4 class="text-warning">Not all cargos are unloaded!</h4>
            </c:if>
        </c:when>
        <c:otherwise>
            <h4 class="text-warning">No route points yet!</h4>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${isValidRoute && empty order.getAssignedTruck() && !isRouteFormed}">
            <form class="form-inline" method="post" action="${pageContext.request.contextPath}/Order">
                <button type="submit" class="btn btn-primary" name="action" value="getTrucks">Get Available Trucks
                </button>
                <span class="text-warning"
                      style="margin-left: 10px;">After pushing this button, you won't be able to add more routepoints.</span>
            </form>
        </c:when>
        <c:when test="${isValidRoute && not empty order.getAssignedTruck()}">
            <h3>Assigned truck:</h3>
            <p style="margin-left: 10px;">${order.getAssignedTruck().toString()}</p>
        </c:when>
    </c:choose>

    <c:choose>
        <c:when test="${not empty trucks && trucks.size() > 0 && empty order.getAssignedTruck()}">
            <h3>Available trucks:</h3>
            <form class="form-inline" method="post" action="${pageContext.request.contextPath}/Order">
                <div class="form-group">
                    <label class="sr-only" for="truckToAssign">Choose truck</label>
                    <select class="form-control" id="truckToAssign" name="truckToAssign">
                        <c:forEach var="i" begin="0" end="${trucks.size()-1}">
                            <c:if test="${isTruckAssigned}">
                                <c:set var="selected"
                                       value="${order.getAssignedTruck().getId()==trucks.get(i).getId() ? 'selected' : ''}"/>
                            </c:if>
                            <option value="${trucks.get(i).getId()}" ${selected}>
                                    ${trucks.get(i).getRegNumber()}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <button type="submit" class="btn btn-primary" name="action" value="assignTruck">Assign</button>
            </form>
        </c:when>
    </c:choose>
    <c:set var="shiftSize" value="${order.getAssignedTruck().getShiftSize()}"/>
    <c:set var="numOfAssignedDrivers" value="${order.getAssignedDrivers().size()}"/>
    <c:if test="${isTruckAssigned && shiftSize != numOfAssignedDrivers}">
        <h3>Available drivers:</h3>
        <!-- <h2>Assign order.getAssignedTruck().getShiftSize() Drivers:</h2> -->
        <form class="form-inline" method="post" action="${pageContext.request.contextPath}/Order">
            <div class="form-group">
                <label class="sr-only" for="driverToAssign">Choose driver(s)</label>
                <select class="form-control" id="driverToAssign" name="driverToAssign">
                    <c:forEach items="${drivers}" var="driver" varStatus="loop">
                        <option value="${driver.getId()}${" "}${loop.index.toString()}">${driver}</option>
                    </c:forEach>
                </select>
            </div>
            <button type="submit" class="btn btn-primary" name="action" value="assignDriver">Assign</button>
            <span class="text-warning"
                  style="margin-left: 10px;">Shift size is ${shiftSize}! Assign ${shiftSize-numOfAssignedDrivers} more driver(s).</span>
        </form>
    </c:if>
    <c:if test="${order.getAssignedDrivers() != null && order.getAssignedDrivers().size() > 0}">
        <h3>List of assigned drivers:</h3>
        <ol>
            <c:forEach items="${order.getAssignedDrivers()}" var="driver">
                <li>${driver}</li>
            </c:forEach>
        </ol>
    </c:if>
    <c:if test="${isShiftFormed}">
        <form class="form-inline" method="post" action="${pageContext.request.contextPath}/Order">
            <button type="submit" class="btn btn-primary btn-success" name="action" value="create">Create Order
            </button>
        </form>
    </c:if>
</div>
<jsp:include page="/footer.jspf"/>
</body>
</html>
