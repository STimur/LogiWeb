<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Home Page</title>
<link rel="stylesheet" href="css/bootstrap/flatly.css">
<link rel="stylesheet" href="css/logiweb.css">
<body>
<jsp:include page="navbar.jspf"/>
<div class="container col-md-2 col-md-offset-5">
    <h2>Login Page</h2>
    <form role="form" method="post" action="j_security_check">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text" class="form-control" id="username" name="j_username">
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password" class="form-control" id="password" name="j_password">
        </div>
        <button type="submit" class="btn btn-default" value="Login">Login</button>
    </form>
</div>
<jsp:include page="footer.jspf"/>
</body>
</html>
