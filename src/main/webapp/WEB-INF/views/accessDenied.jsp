<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Access denied</title>
</head>
<body>
<c:choose>
    <c:when test="${sessionScope.loggedInUser == null}">
        <p>You entered as guest!</p>
        <br/>
    </c:when>
    <c:otherwise>
        You entered as ${sessionScope.loggedInUser.name}
        <form action="../logoff">
            <button type="submit">Log off</button>
        </form>
        <br />
    </c:otherwise>
</c:choose>
    <h3 style="color: crimson">Sorry, this page is denied for you :( </h3>
<p><a href="../index">Main page</a></p>
<p><a href="../shop">Enter the shop</a></p>
</body>
</html>
