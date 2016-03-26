<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: tims
  Date: 2/19/2016
  Time: 2:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Truck</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/flatly.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/logiweb.css" />">
</head>
<body>
<c:import url="../../navbar.jsp">
    <c:param name="activeTab" value="Trucks"/>
</c:import>
<div class="container">
    <h2>Edit Truck</h2>
    <form id="editTruckForm" method="post" action="${pageContext.request.contextPath}/trucks/update">
        <fieldset class="form-group">
            <label for="regNumber">Truck registration number</label>
            <input type="text" class="form-control" id="regNumber" name="regNumber"
                   placeholder="Enter truck registration number"
                   value="${not empty truckValidationException ? truckValidationException.getPlateNumberValidationUnit().getInputValue() : truckToEdit.getRegNumber()}">
            <c:if test="${not empty truckValidationException && !truckValidationException.getPlateNumberValidationUnit().isValid()}">
                <div class="validationError">
                    <span class="text-danger">Enter 2 letters plus 5 numbers, e.g. &quot;AB12345&quot;</span>
                </div>
            </c:if>
        </fieldset>
        <fieldset class="form-group">
            <label for="shiftSize">Shift size</label>
            <input type="text" class="form-control" id="shiftSize" name="shiftSize"
                   placeholder="Enter truck shift size"
                   value="${not empty truckValidationException ? truckValidationException.getShiftSizeValidationUnit().getInputValue() : truckToEdit.getShiftSize()}">
            <c:if test="${not empty truckValidationException && !truckValidationException.getShiftSizeValidationUnit().isValid()}">
                <div class="validationError">
                    <span class="text-danger">Shift size should be a number between 1 and 4</span>
                </div>
            </c:if>
        </fieldset>
        <fieldset class="form-group">
            <label for="capacity">Capacity</label>
            <input type="text" class="form-control" id="capacity"
                   name="capacity" placeholder="Enter truck capacity"
                   value="${not empty truckValidationException ? truckValidationException.getCapacityValidationUnit().getInputValue() : truckToEdit.getCapacity()}">
            <c:if test="${not empty truckValidationException && !truckValidationException.getCapacityValidationUnit().isValid()}">
                <div class="validationError">
                    <span class="text-danger">Should be a number between 10 and 40</span>
                </div>
            </c:if>
        </fieldset>
        <fieldset class="form-group">
            <label for="state">Truck state</label>
            <select class="form-control" id="state" name="state">
                <c:set var="truckState" value="${truckToEdit.getState()}"/>
                <c:set var="selectOkState" value="${(truckState eq 'OK') ? 'selected' : ''}"/>
                <c:set var="selectBrokenState" value="${(truckState eq 'BROKEN') ? 'selected' : ''}"/>
                <option value="OK" ${selectOkState}>OK</option>
                <option value="BROKEN" ${selectBrokenState}>Broken</option>
            </select>
        </fieldset>
        <fieldset class="form-group">
            <label for="city">City</label>
            <select class="form-control" id="city" name="cityId">
                <c:set var="truckCityId" value="${truckToEdit.getCity().getId()}"/>
                <c:forEach items="${cities}" var="city">
                    <c:set var="selected" value="${(truckCityId == city.getId()) ? 'selected' : ''}"/>
                    <option value="${city.getId()}" ${selected}>
                            ${city.getName()}
                    </option>
                </c:forEach>
            </select>
        </fieldset>
        <button type="submit" class="btn btn-success" name="action" value="update">Save Changes</button>
        <input type="hidden" name="id" value="${truckToEdit.getId()}">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
<jsp:include page="../../footer.jsp"/>
</body>
</html>
