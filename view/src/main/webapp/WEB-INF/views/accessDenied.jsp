<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Access Denied Page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
<body>
<jsp:include page="navbar.jsp"/>
<div class="container col-md-4 col-md-offset-4">
    <h2 class="text-info">You have not enough permissions to get page you want.</h2>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
