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
    <title>Edit Driver</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
</head>
<body>
<c:import url="/WEB-INF/views/navbar.jspf">
    <c:param name="activeTab" value="Drivers"/>
</c:import>
<div class="container">
    <h2>Edit Driver</h2>
    <form id="editDriverForm" method="post" action="${pageContext.request.contextPath}/Driver">
        <fieldset class="form-group">
            <label for="name">Driver name</label>
            <input type="text" class="form-control" id="name" name="name"
                   placeholder="Enter driver name"
                   value="${not empty driverValidationException ? driverValidationException.getNameValidationUnit().getInputValue() : driverToEdit.getName()}">
            <c:if test="${not empty driverValidationException && !driverValidationException.getNameValidationUnit().isValid()}">
                <div class="validationError">
                    <span class="text-danger">Name should consist only of latin letters.</span>
                </div>
            </c:if>
        </fieldset>
        <fieldset class="form-group">
            <label for="surname">Driver surname</label>
            <input type="text" class="form-control" id="surname" name="surname"
                   placeholder="Enter driver surname"
                   value="${not empty driverValidationException ? driverValidationException.getSurnameValidationUnit().getInputValue() : driverToEdit.getSurname()}">
            <c:if test="${not empty driverValidationException && !driverValidationException.getSurnameValidationUnit().isValid()}">
                <div class="validationError">
                    <span class="text-danger">Surname should consist only of latin letters.</span>
                </div>
            </c:if>
        </fieldset>
        <fieldset class="form-group">
            <label for="hoursWorkedThisMonth">Hours of work in this month</label>
            <input type="text" class="form-control" id="hoursWorkedThisMonth"
                   name="hoursWorkedThisMonth" placeholder="How much hours of work driver had this month?"
                   value="${not empty driverValidationException ? driverValidationException.getHoursOfWorkValidationUnit().getInputValue() : driverToEdit.getHoursWorkedThisMonth()}">
            <c:if test="${not empty driverValidationException && !driverValidationException.getHoursOfWorkValidationUnit().isValid()}">
                <div class="validationError">
                    <span class="text-danger">Hours of work should be between 0 and 176.</span>
                </div>
            </c:if>
        </fieldset>
        <fieldset class="form-group">
            <label for="state">Driver state</label>
            <select class="form-control" id="state" name="state">
                <c:set var="driverState" value="${driverToEdit.getState()}"/>
                <c:set var="selectRestState" value="${(driverState eq 'REST') ? 'selected' : ''}"/>
                <c:set var="selectShiftState" value="${(driverState eq 'SHIFT') ? 'selected' : ''}"/>
                <c:set var="selectDriveState" value="${(driverState eq 'DRIVE') ? 'selected' : ''}"/>
                <option value="DRIVE" ${selectDriveState}>Driving</option>
                <option value="REST" ${selectRestState}>On rest</option>
                <option value="SHIFT" ${selectShiftState}>On shift</option>
            </select>
        </fieldset>
        <fieldset class="form-group">
            <label for="city">City</label>
            <select class="form-control" id="city" name="cityId">
                <c:set var="driverCityId" value="${driverToEdit.getCurrentCity().getId()}"/>
                <c:forEach items="${cities}" var="city">
                    <c:set var="selected" value="${(driverCityId == city.getId()) ? 'selected' : ''}"/>
                    <option value="${city.getId()}" ${selected}>
                            ${city.getName()}
                    </option>
                </c:forEach>
            </select>
        </fieldset>
        <button type="submit" class="btn btn-success" name="action" value="update">Save Changes</button>
        <input type="hidden" name="id" value="${driverToEdit.getId()}">
    </form>
</div>
<jsp:include page="/WEB-INF/views/footer.jspf"/>
</body>
</html>
