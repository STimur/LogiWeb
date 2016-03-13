<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Home Page</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/flatly.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/logiweb.css">
<body>
<c:import url="navbar.jsp"/>
<div class="container col-md-2 col-md-offset-5">
    <h2>Login Page</h2>
    <form role="form" method="post" action="j_security_check">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" id="username" name="j_username" value="manager">
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" name="j_password" value="manager">
        </div>
        <button type="submit" class="btn btn-default" value="Login">Login</button>
    </form>
</div>
<c:import url="footer.jsp"/>
</body>
</html>
