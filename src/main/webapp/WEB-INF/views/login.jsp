<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    Hello login page!
    <p style="color: crimson">${authFailedMessage}</p>
    <form action="login" method="post">
        <div>
            <h3>Login page</h3>
            <hr>
            <label for="login"><b>Login</b></label>
            <input type="text" placeholder="Enter login" name="login" id="login" value="${prevLogin}" required>

            <label for="psw"><b>Password</b></label>
            <input type="password" placeholder="Enter Password" name="psw" id="psw" value="${prevPsw}" required>

            <button type="submit">Login</button>
        </div>
        <p>Don't have an account? <a href="registration">Sign up</a></p>
    </form>
    <a href="index">To the main page</a>
</body>
</html>
