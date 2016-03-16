<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header ${param.activeTab eq "Home" ? 'active' : ''}">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/home">LogiWeb</a>
        </div>
        <c:if test="${not empty pageContext.request.userPrincipal}">
            <ul class="nav navbar-nav">
                <c:choose>
                    <c:when test="${pageContext.request.isUserInRole('manager')}">
                        <li ${param.activeTab eq "Trucks" ? 'class="active"' : ''}>
                            <a href="${pageContext.request.contextPath}/trucks">Trucks</a>
                        </li>
                        <li ${param.activeTab eq "Drivers" ? 'class="active"' : ''}>
                            <a href="${pageContext.request.contextPath}/drivers">Drivers</a>
                        </li>
                        <li ${param.activeTab eq "Orders" ? 'class="active"' : ''}>
                            <a href="${pageContext.request.contextPath}/orders">Orders</a></li>
                        <li ${param.activeTab eq "OrdersState" ? 'class="active"' : ''}>
                            <a href="${pageContext.request.contextPath}/orders-state">Orders state</a></li>
                        <li ${param.activeTab eq "Cargos" ? 'class="active"' : ''}>
                            <a href="${pageContext.request.contextPath}/cargos-state">Cargos state</a></li>
                    </c:when>
                    <c:when test="${pageContext.request.isUserInRole('driver')}">
                        <li ${param.activeTab eq "GetJobInfo" ? 'class="active"' : ''}>
                            <a href="${pageContext.request.contextPath}/drivers/get-job-info">Get Job
                                Info</a>
                        </li>
                    </c:when>
                </c:choose>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <c:url value="/logout" var="logoutProcessingUrl"/>
                    <form action="${logoutProcessingUrl}" method="post">
                        <input type="submit" value="Logout"/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </li>
                <%--<li><a href="${pageContext.request.contextPath}/Logout">Logout</a></li>--%>
            </ul>
        </c:if>
    </div>
</nav>
