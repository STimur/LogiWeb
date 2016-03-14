<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Login Page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
<body>
<c:import url="navbar.jsp"/>
<div class="container col-md-2 col-md-offset-5">
    <h2>Login Page</h2>
    <c:url value="/login" var="loginProcessingUrl"/>
    <form action="${loginProcessingUrl}" method="post">
        <!-- use param.error assuming FormLoginConfigurer#failureUrl contains the query parameter error -->
        <c:if test="${param.error != null}">
            <div>
                Failed to login.
                <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                    Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                </c:if>
            </div>
        </c:if>
        <!-- the configured LogoutConfigurer#logoutSuccessUrl is /login?logout and contains the query param logout -->
        <c:if test="${param.logout != null}">
            <div>
                You have been logged out.
            </div>
        </c:if>
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" id="username" name="username" value="manager">
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" name="password" value="manager">
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button type="submit" class="btn btn-default">Login</button>
    </form>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
