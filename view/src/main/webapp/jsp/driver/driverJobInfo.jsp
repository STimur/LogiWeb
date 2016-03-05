<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.persistence.entity.Order" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.persistence.entity.Driver" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.persistence.entity.Truck" %>
<%@ page import="com.tsystems.javaschool.timber.logiweb.persistence.entity.RoutePoint" %>
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
    <c:choose>
        <c:when test="${empty driver}">
            <h2>Sorry, no driver with such personal number in database!</h2>
        </c:when>
        <c:otherwise>
            <h2>Driver Job Info</h2>
            <p><b>Personal number:</b> ${driver.getId()}</p>
            <p><b>Coworkers personal numbers:</b></p>
            <ul>
                <c:choose>
                    <c:when test="${not empty driver.getOrder()}">
                        <c:set var="codrivers" value="${driver.getOrder().getAssignedDrivers()}"/>
                        <c:forEach items="${codrivers}" var="codriver">
                            <c:if test="${codriver.getId() != driver.getId()}">
                                <li>${codriver.getId()} ${codriver.toString()}</li>
                            </c:if>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <span class="text-warning">no order, so there is no coworkers</span>
                    </c:otherwise>
                </c:choose>
            </ul>
            <p><b>Truck registration number:</b>
                <c:set var="truck" value="${driver.getCurrentTruck()}" />
                <c:choose>
                    <c:when test="${not empty truck}">
                        ${truck.getRegNumber()}
                    </c:when>
                    <c:otherwise>
                        <span class="text-warning">no order, so there is no assigned truck</span>
                    </c:otherwise>
                </c:choose>
            </p>
            <p><b>Order number:</b>
                <c:set var="order" value="${driver.getOrder()}" />
                <c:choose>
                    <c:when test="${not empty order}">
                        ${order.getId()}
                    </c:when>
                    <c:otherwise>
                        <span class="text-warning">no assigned order</span>
                    </c:otherwise>
                </c:choose>
            </p>
            <p><b> Route points:</b></p>
            <ol>
                <c:choose>
                    <c:when test="${not empty order}">
                        <c:forEach items="${order.formRouteAsList()}" var="routePoint">
                            <li>${routePoint}</li>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <span class="text-warning">no assigned order, so there is no route</span>
                    </c:otherwise>
                </c:choose>
            </ol>
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="/footer.jspf"/>
</body>
</html>