<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Error Page</title>
<link rel="stylesheet" href="<c:url value="/resources/css/bootstrap/flatly.css" />">
<link rel="stylesheet" href="<c:url value="/resources/css/logiweb.css" />">
<body>
<c:import url="navbar.jsp"/>
<div class="container col-md-4 col-md-offset-4">
    <h2 class="text-info">Sorry, such page does not exist.</h2>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
