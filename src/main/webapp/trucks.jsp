<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.Truck" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.City" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Orders Page</title>
<link rel="stylesheet" href="/css/bootstrap/flatly.css">
<link rel="stylesheet" href="/css/logiweb.css">
<body>
<jsp:include page="/navbar.jspf"/>
<div class="container">
    <% List<Truck> trucks = (List<Truck>) request.getAttribute("trucks");
        if (trucks == null) { %>
    <p>You are not allowed to get pure <b>trucks.jsp</b> without passing the controller!</p>
    <% } else { %>
    <h2>Trucks</h2>
    <div class="text-right">
        <a href="/addTruck.jsp" class="btn btn-primary btn-success">Add Truck</a>
    </div>
    <table class="table table-bordered table-striped table-hover">
        <tr>
            <th>ID</th>
            <th>RegNumber</th>
            <th>ShiftSize</th>
            <th>Capacity</th>
            <th>State</th>
            <th>CurrentCity</th>
            <th>Actions</th>
        </tr>
        <% for (Truck truck : trucks) { %>
        <tr>
            <td>
                <%= truck.getId() %>
            </td>
            <td>
                <%= truck.getRegNumber() %>
            </td>
            <td>
                <%= truck.getShiftSize() %>
            </td>
            <td>
                <%= truck.getCapacity() %>
            </td>
            <td>
                <%= truck.getState() %>
            </td>
            <%
                City city = truck.getCity();
                String cityName;
                if (city == null)
                    cityName = "no city";
                else
                    cityName = city.getName();
            %>
            <td>
                <%= cityName %>
            </td>
            <td>
                <form class="form-inline" method="post" action="/Truck">
                    <button type="submit" class="btn btn-primary btn-primary" name="action" value="update">Update
                    </button>
                    <input type="hidden" name="id" value="<%=truck.getId()%>"/>
                </form>
                <form class="form-inline" method="post" action="/Truck">
                    <button type="submit" class="btn btn-primary btn-danger" name="action" value="delete">Remove
                    </button>
                    <input type="hidden" name="id" value="<%=truck.getId()%>"/>
                </form>
            </td>
        </tr>
        <% }} %>
    </table>
</div>
</body>
</html>
