<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Trucks Page</title>
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/flatly.css" />">
<link rel="stylesheet" href="<c:url value="/resources/css/logiweb.css" />">
<script src="<c:url value="/resources/js/jquery-2.2.3.js"/>"
        type="text/javascript"></script>
<body>
<c:import url="../../navbar.jsp">
    <c:param name="activeTab" value="Trucks"/>
</c:import>
<div class="container">
    <h2>Trucks</h2>
    <div class="text-right">
        <form method="post" action="${pageContext.request.contextPath}/trucks/add">
            <button type="submit" class="btn btn-primary btn-success" name="action" value="add">Add Truck</button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
    <table id="trucks-table" class="table middleAligned table-bordered table-striped table-hover">
        <tr>
            <th>ID</th>
            <th>RegNumber</th>
            <th>ShiftSize</th>
            <th>Capacity</th>
            <th>State</th>
        </tr>
    </table>
</div>
<jsp:include page="../../footer.jsp"/>
<script>
    jQuery(document).ready(function ($) {
        loadTrucks();
    });

    function loadTrucks() {
        $.ajax({
            dataType: 'json',
            url: "/logiweb/trucks-js/list",
            timeout: 100000,
            success: function (data) {
                console.log("SUCCESS: ", data);
                display(data);
            },
            error: function (e) {
                console.log("ERROR: ", e);
                alert(e);
            },
            done: function (e) {
                console.log("DONE");
            }
        });

    }

    function display(data) {
        $.each(data, function (key, truck) {
            var tableRow = "" +
                            "<tr><td>" + truck.id + "</td>" +
                            "<td>" + truck.regNumber + "</td>" +
                            "<td>" + truck.shiftSize + "</td>" +
                            "<td>" + truck.capacity + "</td>" +
                            "<td>" + truck.state + "</td></tr>"
                    ;
            $('#trucks-table').append(tableRow);
        });
    }
</script>
</body>
</html>
