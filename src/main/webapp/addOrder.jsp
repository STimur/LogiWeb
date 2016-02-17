<%@ page import="com.tsystems.javaschool.timber.logiweb.service.TruckService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.TruckDao" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.CityService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.CityDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.*" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.DriverService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.DriverDao" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.OrderService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.OrderDao" %>
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
    <link rel="stylesheet" href="css/bootstrap/flatly.css">
</head>
<body>
<div id="addOrderContainer" class="container">
    <%!
        static CityService cityService = new CityService(new CityDao());
        static TruckService truckService = new TruckService(new TruckDao());
        static DriverService driverService = new DriverService(new DriverDao());
        static OrderService orderService = new OrderService(new OrderDao());
        static List<RoutePoint> route = new ArrayList<RoutePoint>();
        static List<City> cities = cityService.findAll();
        List<Truck> trucks;
        List<Driver> drivers;
        Order order;

        String getTrucksVisibility() {
            // TODO implement check of all BL here
            // absolutely must have even number of points
            int numOfPoints = route.size();
            if (numOfPoints > 0 && numOfPoints % 2 == 0)
                return "";
            return "invisible";
        }

        boolean isTruckAssigned() {
            if (order != null)
                if (order.getAssignedTruck() != null)
                    return true;
            return false;
        }

        boolean isShiftFormed() {
            if (order != null && order.getAssignedTruck() != null)
                if (order.getAssignedDrivers() != null)
                    if (order.getAssignedDrivers().size() == order.getAssignedTruck().getShiftSize())
                        return true;
            return false;
        }
    %>
    <h2>Add Order Page</h2>
    <form class="form-inline" method="post" action="/addOrder.jsp">
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
        <button type="submit" class="btn btn-primary" name="action" value="addPoint">Add</button>
    </form>
    <%
        // only one action can be GET/POST at a time
        // so better to handle them in one place
        String action = request.getParameter("action");
        if (action != null) {
            if (action.equals("addPoint")) {
                String name = request.getParameter("cargoName");
                int weight = Integer.valueOf(request.getParameter("cargoWeight"));
                Cargo cargo = new Cargo(name, weight);
                int cityId = Integer.valueOf(request.getParameter("pointCity"));
                RoutePointType pointType = RoutePointType.valueOf(request.getParameter("pointType"));
                City city = cityService.findById(cityId);
                RoutePoint point = new RoutePoint(city, cargo, pointType);
                if (route.size() > 0)
                    route.get(route.size() - 1).setNextRoutePoint(point);
                route.add(point);
            }
            if (action.equals("getTrucks")) {
                order = new Order();
                order.setRoute(route.get(0));
                trucks = truckService.getSuitableTrucksForOrder(order);
            }
            if (action.equals("assignTruck")) {
                int truckId = Integer.valueOf(request.getParameter("truckForOrder"));
                Truck truck = truckService.findById(truckId);
                order.setAssignedTruck(truck);
                drivers = driverService.getSuitableDriversForOrder(order);
            }
            if (action.equals("assignDriver")) {
                String[] params = request.getParameter("driverForOrder").split(" ");
                int driverId = Integer.valueOf(params[0]);
                int driverIndex = Integer.valueOf(params[1]);
                Driver driver = driverService.findById(driverId);
                if (order.getAssignedDrivers() == null)
                    order.setAssignedDrivers(new ArrayList<Driver>());
                order.getAssignedDrivers().add(driver);
                drivers.remove(driverIndex);
            }
            if (action.equals("createOrder")) {
                //TODO debug creation of all involved entities into DB
                orderService.create(order); %>
                <c:redirect url="orders.jsp" />
            <% } %>
    <h1>OOOHHH YEAAA!!! We have <%= route.size()%> points alrdy!</h1>
    <% } else {
        route.clear();
    %>
    <h1>NOOOOOOO!!!!!</h1>
    <% } %>
    <form class="form-inline <%=getTrucksVisibility()%>" method="post" action="/addOrder.jsp">
        <button type="submit" class="btn btn-primary" name="action" value="getTrucks">Get Available Trucks</button>
    </form>

    <% if (trucks != null) { %>
    <h2>Assign Truck:</h2>
    <form class="form-inline" method="post" action="/addOrder.jsp">
        <div class="form-group">
            <label class="sr-only" for="truckForOrder">Choose city</label>
            <select class="form-control" id="truckForOrder" name="truckForOrder">
                <% for (Truck truck : trucks) { %>
                <option value="<%= truck.getId()%>">
                    <%= truck.getRegNumber() %>
                </option>
                <% } %>
            </select>
        </div>
        <button type="submit" class="btn btn-primary" name="action" value="assignTruck">Assign</button>
    </form>
    <% } %>
    <% if (isTruckAssigned()) { %>
    <h2>Assign <%=order.getAssignedTruck().getShiftSize()%> Drivers:</h2>
    <form class="form-inline" method="post" action="/addOrder.jsp">
        <div class="form-group">
            <label class="sr-only" for="driverForOrder">Choose city</label>
            <select class="form-control" id="driverForOrder" name="driverForOrder">
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
    <% } %>
    <% if (isShiftFormed()) { %>
    <form class="form-inline" method="post" action="/addOrder.jsp">
        <button type="submit" class="btn btn-primary btn-success" name="action" value="createOrder">Create Order</button>
    </form>
    <% } %>

</div>

</body>
</html>
