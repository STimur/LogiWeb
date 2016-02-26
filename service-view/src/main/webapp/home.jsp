<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Home Page</title>
<link rel="stylesheet" href="css/bootstrap/flatly.css">
<link rel="stylesheet" href="css/logiweb.css">
<body>
<jsp:include page="navbar.jspf"/>
<div class="container">
    <h2>Home Page</h2>
    <%
        String username = (String) request.getUserPrincipal().getName();
        String userrole = "";
        if (request.isUserInRole("manager"))
            userrole = "manager";
        else
            userrole = "driver";
    %>
    <p>Hello, <span class="text-info"><%=userrole%></span> <b><%=username%></b>!
</div>
<jsp:include page="footer.jspf"/>
</body>
</html>
