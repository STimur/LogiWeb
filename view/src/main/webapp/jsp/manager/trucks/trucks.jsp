<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.Truck" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.City" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Trucks Page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
<body>
<c:import url="/navbar.jspf">
    <c:param name="activeTab" value="Trucks"/>
</c:import>
<div class="container">
    <% List<Truck> trucks = (List<Truck>) request.getAttribute("trucks");
        if (trucks == null) { %>
    <p>You are not allowed to get pure <b>trucks.jsp</b> without passing the controller!</p>
    <% } else { %>
    <h2>Trucks</h2>
    <div class="text-right">
        <a href="${pageContext.request.contextPath}/jsp/manager/trucks/addTruck.jsp"
           class="btn btn-primary btn-success">Add Truck</a>
    </div>
    <table class="table middleAligned table-bordered table-striped table-hover">
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
            <td class="buttonsCell">
                <div class="row">
                    <div class="col-md-6 editButton">
                        <form class="form-inline" method="post" action="${pageContext.request.contextPath}/Truck">
                            <button type="submit" class="btn btn-primary btn-primary" name="action" value="edit">Edit
                            </button>
                            <input type="hidden" name="id" value="<%=truck.getId()%>"/>
                        </form>
                    </div>
                    <div class="col-md-6 removeButton">
                        <form class="form-inline" method="post" action="${pageContext.request.contextPath}/Truck">
                            <button type="submit" class="btn btn-primary btn-danger" name="action" value="delete">Remove
                            </button>
                            <input type="hidden" name="id" value="<%=truck.getId()%>"/>
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
