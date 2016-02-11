<%@ page import="com.tsystems.javaschool.timber.logiweb.entity.Truck" %>
<%@ page import="java.util.List" %>
<html>
<body>
<% List<Truck> trucks = (List<Truck>)request.getAttribute("trucks");
    if (trucks == null) { %>
<h2>No trucks in list</h2>
<% } else { %>
<h2>Here are our trucks!</h2>
<table>
    <tr>
        <th>ID</th>
        <th>RegNumber</th>
        <th>ShiftSize</th>
        <th>Capacity</th>
        <th>State</th>
        <th>CurrentCity</th>
    </tr>
    <% for(Truck truck: trucks) { %>
    <tr>
        <td><%= truck.getId() %></td>
        <td><%= truck.getRegNumber() %></td>
        <td><%= truck.getShiftSize() %></td>
        <td><%= truck.getCapacity() %></td>
        <td><%= truck.getState() %></td>
        <td><%= truck.getCity().getName() %></td>
    </tr>
    <% }} %>
</table>
</body>
</html>
