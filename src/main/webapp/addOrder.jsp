<%@ page import="com.tsystems.javaschool.timber.logiweb.service.TruckService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.TruckDao" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.service.CityService" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.dao.CityDao" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.*" %><%--
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
        static List<RoutePoint> route = new ArrayList<RoutePoint>();
        static List<City> cities = cityService.findAll();
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
            <input type="text" class="form-control" id="cargoWeight" name="cargoWeight" placeholder="Enter cargo weight">
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
                    route.get(route.size()-1).setNextRoutePoint(point);
                route.add(point);
            }
    %>
    <h1>OOOHHH YEAAA!!! We have <%= route.size()%> points alrdy!</h1>
    <% } else {
        route.clear();
    %>
    <h1>NOOOOOOO!!!!!</h1>
    <% } %>
</div>

</body>
</html>
