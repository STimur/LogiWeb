<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Drivers Page</title>
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/flatly.css" />">
<link rel="stylesheet" href="<c:url value="/resources/css/logiweb.css" />">
<body>
<c:import url="../../navbar.jsp">
    <c:param name="activeTab" value="Drivers"/>
</c:import>
<div class="container">
    <c:choose>
        <c:when test="${empty drivers}">
            <p>You are <b>not logged in</b> and not allowed to see list of drivers.</p>
        </c:when>
        <c:otherwise>
            <h2>Drivers</h2>
            <div class="text-right">
                <form method="post" action="${pageContext.request.contextPath}/drivers/add">
                    <button type="submit" class="btn btn-primary btn-success" name="action" value="add">Add Driver
                    </button>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </div>
            <table class="table middleAligned table-bordered table-striped table-hover">
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Hours of Work this month</th>
                    <th>State</th>
                    <th>Current city</th>
                    <th>Actions</th>
                </tr>
                <c:forEach items="${drivers}" var="driver">
                    <tr>
                        <td>
                                ${driver.getId()}
                        </td>
                        <td>
                                ${driver.getName()}
                        </td>
                        <td>
                                ${driver.getSurname()}
                        </td>
                        <td>
                                ${driver.getHoursWorkedThisMonth()}
                        </td>
                        <td>
                                ${driver.getState()}
                        </td>
                        <td>
                                ${driver.getCurrentCity().getName()}
                        </td>
                        <td class="buttonsCell">
                            <div class="row">
                                <div class="col-md-6 editButton">
                                    <form class="form-inline" method="post"
                                          action="${pageContext.request.contextPath}/drivers/edit">
                                        <button type="submit" class="btn btn-primary btn-primary" name="action"
                                                value="edit">Edit
                                        </button>
                                        <input type="hidden" name="id" value="${driver.getId()}"/>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    </form>
                                </div>
                                <div class="col-md-6 removeButton">
                                    <form class="form-inline" method="post"
                                          action="${pageContext.request.contextPath}/drivers/delete">
                                        <button type="submit" class="btn btn-primary btn-danger" name="action"
                                                value="delete">Remove
                                        </button>
                                        <input type="hidden" name="id" value="${driver.getId()}"/>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    </form>
                                </div>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</div>
<jsp:include page="../../footer.jsp"/>
</body>
</html>
