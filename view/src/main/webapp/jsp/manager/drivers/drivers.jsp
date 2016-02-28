<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.Truck" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.City" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.Driver" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Drivers Page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
<body>
<c:import url="/navbar.jspf">
    <c:param name="activeTab" value="Drivers"/>
</c:import>
<div class="container">
    <% List<Driver> drivers = (List<Driver>) request.getAttribute("drivers");
        if (drivers == null) { %>
    <p>You are not allowed to get pure <b>trucks.jsp</b> without passing the controller!</p>
    <% } else { %>
    <h2>Drivers</h2>
    <div class="text-right">
        <a href="${pageContext.request.contextPath}/jsp/manager/drivers/addDriver.jsp"
           class="btn btn-primary btn-success">Add Driver</a>
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
        <% for (Driver driver : drivers) { %>
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
            <td class="buttonsCell">
                <div class="row">
                    <div class="col-md-6 editButton">
                        <form class="form-inline" method="post" action="${pageContext.request.contextPath}/Driver">
                            <button type="submit" class="btn btn-primary btn-primary" name="action" value="edit">Edit
                            </button>
                            <input type="hidden" name="id" value="<%=driver.getId()%>"/>
                        </form>
                    </div>
                    <div class="col-md-6 removeButton">
                        <form class="form-inline" method="post" action="${pageContext.request.contextPath}/Driver">
                            <button type="submit" class="btn btn-primary btn-danger" name="action" value="delete">Remove
                            </button>
                            <input type="hidden" name="id" value="<%=driver.getId()%>"/>
                        </form>
                    </div>
                </div>
            </td>
        </tr>
        <% }
        } %>
    </table>
</div>
<jsp:include page="/footer.jspf"/>
</body>
</html>
