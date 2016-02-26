<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.Truck" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.City" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.Driver" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Drivers Page</title>
<link rel="stylesheet" href="/css/bootstrap/flatly.css">
<link rel="stylesheet" href="/css/logiweb.css">
<body>
<jsp:include page="/navbar.jspf"/>
<div class="container">
    <% List<Driver> drivers = (List<Driver>) request.getAttribute("drivers");
        if (drivers == null) { %>
    <p>You are not allowed to get pure <b>trucks.jsp</b> without passing the controller!</p>
    <% } else { %>
    <h2>Drivers</h2>
    <div class="text-right">
        <a href="/jsp/manager/drivers/addDriver.jsp" class="btn btn-primary btn-success">Add Driver</a>
    </div>
    <table class="table table-bordered table-striped table-hover">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Surname</th>
            <th>Hours of Work this month</th>
            <th>State</th>
            <th>Current city</th>
            <th>Actions</th>
        </tr>
        <% for (Driver driver: drivers) { %>
        <tr>
            <td>
                <%= driver.getId() %>
            </td>
            <td>
                <%= driver.getName() %>
            </td>
            <td>
                <%= driver.getSurname() %>
            </td>
            <td>
                <%= driver.getHoursWorkedThisMonth() %>
            </td>
            <td>
                <%= driver.getState() %>
            </td>
            <%
                City city = driver.getCurrentCity();
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
                <form class="form-inline" method="post" action="/Driver">
                    <button type="submit" class="btn btn-primary btn-primary" name="action" value="edit">Edit
                    </button>
                    <input type="hidden" name="id" value="<%=driver.getId()%>"/>
                </form>
                <form class="form-inline" method="post" action="/Driver">
                    <button type="submit" class="btn btn-primary btn-danger" name="action" value="delete">Remove
                    </button>
                    <input type="hidden" name="id" value="<%=driver.getId()%>"/>
                </form>
            </td>
        </tr>
        <% }} %>
    </table>
</div>
<jsp:include page="/footer.jspf"/>
</body>
</html>
