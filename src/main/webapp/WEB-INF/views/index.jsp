<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <title>Internet-shop</title>
    </head>
    <body>
        <h3 style="color: green">Welcome to the internet-shop!</h3>
        <c:choose>
            <c:when test="${sessionScope.loggedInUser == null}">
                <p>You entered as guest!</p>
                <br/>
            </c:when>
            <c:otherwise>
                You entered as ${sessionScope.loggedInUser.name}
                <form action="logout">
                    <button type="submit">Logout</button>
                </form>
                <br/>
            </c:otherwise>
        </c:choose>
        <p>
            You can <a href="login">Login</a> | <a href="registration">Register</a> on this site
        </p>
        <p>
            Or you can <a href="shop">Enter the shop</a>
        </p>
    </body>
</html>
