<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--
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
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/flatly.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/logiweb.css" />">
</head>
<body>
<c:import url="../../navbar.jsp">
    <c:param name="activeTab" value="Orders"/>
</c:import>
<div class="container">
    <h2>Add Order</h2>
    <c:if test="${!isRouteFormed}">
        <h3>Add cargos for delivery:</h3>
        <form class="form-inline" method="post" action="${pageContext.request.contextPath}/orders/add-load-point">
            <div class="form-group">
                <label class="sr-only" for="pointType">Point type</label>
                <select class="form-control" id="pointType" disabled>
                    <option>Load</option>
                </select>
                <input type="hidden" name="pointType" value="LOAD"/>
            </div>
            <div class="form-group">
                <span style="margin: 0 10px;">in</span>
            </div>
            <div class="form-group">
                <label class="sr-only" for="pointCity">Choose city</label>
                <select class="form-control" id="pointCity" name="pointCity">
                    <c:forEach items="${cities}" var="city">
                        <option value="${city.getId()}">
                                ${city.getName()}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label class="sr-only" for="cargoName">Enter cargo name</label>
                <input type="text" class="form-control" id="cargoName" name="cargoName" placeholder="Enter cargo name"
                       value="${cargoValidationException.getNameValidationUnit().getInputValue()}">
            </div>
            <div class="form-group">
                <label class="sr-only" for="cargoWeight">Enter cargo weight</label>
                <input type="text" class="form-control" id="cargoWeight" name="cargoWeight"
                       placeholder="Enter cargo weight"
                       value="${cargoValidationException.getWeightValidationUnit().getInputValue()}">
            </div>
            <button type="submit" class="btn btn-primary" name="action" value="addLoadPoint">Add</button>
            <c:if test="${not empty cargoValidationException && !cargoValidationException.getNameValidationUnit().isValid()}">
                <div class="validationError">
                    <span class="text-danger">Cargo name should consist only of latin letters.</span>
                </div>
            </c:if>
            <c:if test="${not empty cargoValidationException && !cargoValidationException.getWeightValidationUnit().isValid()}">
                <div class="validationError">
                    <span class="text-danger">Cargo weight should be between 1 and 40000.</span>
                </div>
            </c:if>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

        <c:choose>
            <c:when test="${not empty loadedCargos}">
                <form class="form-inline" method="post"
                      action="${pageContext.request.contextPath}/orders/add-unload-point">
                    <div class="form-group">
                        <select class="form-control" disabled>
                            <option>Unload</option>
                        </select>
                    </div>
                    <span style="margin: 0 10px;">cargo</span>
                    <div class="form-group">
                        <select class="form-control" id="cargo" name="cargoToUnload">
                            <c:forEach items="${loadedCargos}" var="loadedCargo" varStatus="loop">
                                <option value="${loadedCargo.getId()} ${loop.index}">
                                        ${loadedCargo}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <span style="margin: 0 10px;">in</span>
                    <div class="form-group">
                        <label class="sr-only" for="unloadPointCity">Choose city</label>
                        <select class="form-control" id="unloadPointCity" name="unloadPointCityId">
                            <c:forEach items="${cities}" var="city">
                                <option value="${city.getId()}">
                                        ${city.getName()}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary" name="action" value="addUnloadPoint">Add</button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </c:when>
            <c:otherwise>
                <h4 class="text-warning">No cargos to unload!</h4>
            </c:otherwise>
        </c:choose>
    </c:if>
    <c:choose>
        <c:when test="${not empty route}">
            <h3>Current route:</h3>
            <ul>
                <c:forEach var="i" begin="0" end="${route.size()-1}">
                    <li>
                        <c:out value="${route.get(i)}"/>
                    </li>
                </c:forEach>
            </ul>
            <c:if test="${!isValidRoute}">
                <h4 class="text-warning">Not all cargos are unloaded!</h4>
            </c:if>
        </c:when>
        <c:otherwise>
            <h4 class="text-warning">No route points yet!</h4>
        </c:otherwise>
    </c:choose>
    <c:choose>
        <c:when test="${isValidRoute && empty order.getAssignedTruck() && !isRouteFormed}">
            <form class="form-inline" method="post"
                  action="${pageContext.request.contextPath}/orders/get-available-trucks">
                <button type="submit" class="btn btn-primary" name="action" value="getTrucks">Get Available Trucks
                </button>
                <span class="text-warning"
                      style="margin-left: 10px;">After pushing this button, you won't be able to add more routepoints.</span>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>
        </c:when>
        <c:when test="${isValidRoute && not empty order.getAssignedTruck()}">
            <h3>Assigned truck:</h3>
            <p style="margin-left: 10px;">${order.getAssignedTruck().toString()}</p>
        </c:when>
    </c:choose>

    <c:set var="shiftSize" value="${order.getAssignedTruck().getShiftSize()}"/>
    <c:set var="numOfAvailableDrivers" value="${drivers.size()}"/>
    <c:set var="numOfAssignedDrivers" value="${order.getAssignedDrivers().size()}"/>

    <c:if test="${isRouteFormed}">
        <c:choose>
            <c:when test="${not empty trucks && trucks.size() > 0 && !isTruckAssigned}">
                <h3>Available trucks:</h3>
                <form class="form-inline" method="post" action="${pageContext.request.contextPath}/orders/assign-truck">
                    <div class="form-group">
                        <label class="sr-only" for="truckToAssign">Choose truck</label>
                        <select class="form-control" id="truckToAssign" name="truckToAssign">
                            <c:forEach var="i" begin="0" end="${trucks.size()-1}">
                                <c:if test="${isTruckAssigned}">
                                    <c:set var="selected"
                                           value="${order.getAssignedTruck().getId()==trucks.get(i).getId() ? 'selected' : ''}"/>
                                </c:if>
                                <option value="${trucks.get(i).getId()}" ${selected}>
                                        ${trucks.get(i).getRegNumber()}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    <button type="submit" class="btn btn-primary" name="action" value="assignTruck">Assign</button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </c:when>
            <c:when test="${empty trucks}">
                <c:choose>
                    <c:when test="${order.calcMaxLoad() > 40000}">
                        <h4 class="text-warning">Suggested route needs a truck with capacity more than 40 tons.</h4>
                        <h4 class="text-warning">Max capacity of truck is 40 tons.</h4>
                        <h4 class="text-warning">Recreate your order and make your order less heavier.</h4>
                    </c:when>
                    <c:otherwise>
                        <h4 class="text-warning">Sorry, all trucks are on orders.</h4>
                        <h4 class="text-warning">We will inform you as soon as free trucks will appear.</h4>
                    </c:otherwise>
                </c:choose>
            </c:when>
        </c:choose>
    </c:if>

    <c:if test="${isTruckAssigned}">
        <c:choose>
            <c:when test="${numOfAvailableDrivers + numOfAssignedDrivers >= shiftSize}">
                <c:if test="${shiftSize != numOfAssignedDrivers}">
                    <h3>Available drivers:</h3>
                    <!-- <h2>Assign order.getAssignedTruck().getShiftSize() Drivers:</h2> -->
                    <form class="form-inline" method="post"
                          action="${pageContext.request.contextPath}/orders/assign-driver">
                        <div class="form-group">
                            <label class="sr-only" for="driverToAssign">Choose driver(s)</label>
                            <select class="form-control" id="driverToAssign" name="driverToAssign">
                                <c:forEach items="${drivers}" var="driver" varStatus="loop">
                                    <option value="${driver.getId()}${" "}${loop.index.toString()}">${driver}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-primary" name="action" value="assignDriver">Assign</button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <span class="text-warning"
                              style="margin-left: 10px;">Shift size is ${shiftSize}! Assign ${shiftSize-numOfAssignedDrivers} more driver(s).</span>
                    </form>
                </c:if>
                <c:if test="${order.getAssignedDrivers() != null && order.getAssignedDrivers().size() > 0}">
                    <h3>List of assigned drivers:</h3>
                    <ol>
                        <c:forEach items="${order.getAssignedDrivers()}" var="driver">
                            <li>${driver}</li>
                        </c:forEach>
                    </ol>
                </c:if>
                <c:if test="${isShiftFormed}">
                    <form class="form-inline" method="post" action="${pageContext.request.contextPath}/orders/create">
                        <button type="submit" class="btn btn-primary btn-success" name="action" value="create">Create
                            Order
                        </button>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </c:if>
            </c:when>
            <c:otherwise>
                <h4 class="text-warning">Sorry, not enough drivers for this truck.</h4>
                <h4 class="text-warning">Recreate order and choose another truck if available.</h4>
                <h4 class="text-warning">If not we will inform you as soon as drivers will appear.</h4>
            </c:otherwise>
        </c:choose>
    </c:if>
</div>
<jsp:include page="../../footer.jsp"/>
</body>
</html>
