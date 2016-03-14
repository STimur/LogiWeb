<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: tims
  Date: 2/17/2016
  Time: 10:24 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cargos State Page</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
</head>
<body>
<c:import url="../../navbar.jsp">
    <c:param name="activeTab" value="Cargos"/>
</c:import>
<div class="container">
    <c:choose>
        <c:when test="${empty cargos}">
            <p>You are <b>not logged in</b> and not allowed to get cargos state.</p>
        </c:when>
        <c:otherwise>
            <h2>Cargos State</h2>
            <table id="cargoStatesTable" class="table table-bordered table-striped table-hover">
                <tr>
                    <th class="col-md-5">Id</th>
                    <th class="col-md-7">Ready</th>
                </tr>
                <c:forEach items="${cargos}" var="cargo">
                    <tr>
                        <td>${cargo.getId()}</td>
                        <td>${cargo.getState().toString()}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="../../footer.jsp"/>
</body>
</html>
