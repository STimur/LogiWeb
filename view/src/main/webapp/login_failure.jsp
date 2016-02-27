<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Login Fail Page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
<body>
<jsp:include page="navbar.jspf"/>
<div class="container col-md-2 col-md-offset-5">
    <h2 class="text-info">You credentials are not valid.</h2>
</div>
<jsp:include page="footer.jspf"/>
</body>
</html>
