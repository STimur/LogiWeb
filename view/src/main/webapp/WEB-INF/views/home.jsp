<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Home Page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
<body>
<c:import url="navbar.jsp">
    <c:param name="activeTab" value="Home"/>
</c:import>
<div class="container">
    <h2>Home Page</h2>
    <%--<c:set var="username" value="${pageContext.request.getUserPrincipal().getName()}"/>
    <c:choose>
        <c:when test="${pageContext.request.isUserInRole('manager')}">
            <c:set var="userrole" value="manager"/>
        </c:when>
        <c:otherwise>
            <c:set var="userrole" value="driver"/>
        </c:otherwise>
    </c:choose>
    <p>Hello, <b>${username}</b>! You authorized as <span class="text-info">${userrole}</span>.--%>
    <h2>Spring MVC message is: <i>${message}</i></h2>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
