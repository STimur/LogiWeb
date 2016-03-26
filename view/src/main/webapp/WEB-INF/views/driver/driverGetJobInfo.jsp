<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Driver Get Job Info</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/flatly.css" />">
    <link rel="stylesheet" href="<c:url value="/resources/css/logiweb.css" />">
</head>
<body>
<c:import url="../navbar.jsp">
    <c:param name="activeTab" value="GetJobInfo"/>
</c:import>
<div class="container">
    <h2>Enter driver personal number</h2>
    <form id="getJobInfoForm" method="post" action="${pageContext.request.contextPath}/drivers/job-info">
        <fieldset class="form-group">
            <input type="text" class="form-control" id="id" name="id"
                   placeholder="Enter driver personal number">
            <c:if test="${not empty driverIdNotNumberException}">
                <div class="validationError">
                    <span class="text-danger">Personal number should be more or equal to 1.</span>
                </div>
            </c:if>
        </fieldset>
        <button type="submit" class="btn btn-success" name="action" value="getJobInfo">Get Job Info</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>
<jsp:include page="../footer.jsp"/>
</body>
</html>
