<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Drivers Page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
<body>
<c:import url="/navbar.jspf">
    <c:param name="activeTab" value="GetJobInfo"/>
</c:import>
<div class="container">
    <% Driver driver = (Driver) request.getAttribute("driver");
        if (driver == null) { %>
    <h2>Sorry, no driver with such personal number in database!</h2>
    <% } else {%>
    <h2>Driver Job Info</h2>
    <p><b>Personal number:</b> <%=driver.getId()%>
    </p>
    <p><b>Coworkers personal numbers:</b></p>
    <ul>
        <%
            Order order = driver.getOrder();
            if (order != null) {
                List<Driver> codrivers = driver.getOrder().getAssignedDrivers();
                for (Driver codriver : codrivers) {
                    if (codriver.getId() != driver.getId()) {
        %>
        <li><%=codriver.getId() + " " + codriver.toString()%>
        </li>
        <% }}} else { %>
        <span class="text-warning">no order, so there is no coworkers</span>
        <% } %>
    </ul>
    <p><b>Truck registration number:</b>
        <%
            Truck truck = driver.getCurrentTruck();
            if (truck != null) {%>
        <%=truck.getRegNumber()%>
        <% } else { %>
        <span class="text-warning">no order, so there is no assigned truck</span>
        <% } %>
    </p>
    <p><b>Order number:</b>
        <% if (order != null) { %>
        <%=driver.getOrder().getId()%>
        <% } else { %>
        <span class="text-warning">no assigned order</span>
        <% } %>
    </p>
    <p><b> Route points:</b></p>
    <ol>
        <%
            if (order != null) {
                RoutePoint currentPoint = driver.getOrder().getRoute();
                while (currentPoint != null) { %>
        <li><%=currentPoint.toString()%>
        </li>
        <% currentPoint = currentPoint.getNextRoutePoint();
        } %>
        <% } else { %>
        <span class="text-warning">no assigned order, so there is no route</span>
        <% } %>
    </ol>
    <% } %>
</div>
<jsp:include page="/footer.jspf"/>
</body>
</html>