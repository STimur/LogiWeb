<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Home Page</title>
<link rel="stylesheet" href="/css/bootstrap/flatly.css">
<link rel="stylesheet" href="/css/logiweb.css">
<body>
<jsp:include page="/navbar.jspf"/>
<div class="container">
    <h2>Home Page</h2>
    <%
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
    %>
    <p>Hello, <b><%=username%></b>! I know your password.</p>
    <p>It is: <b><%=password%></b></p>
</div>
<jsp:include page="/footer.jspf"/>
</body>
</html>
