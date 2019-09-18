<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Internet-shop</title>
</head>
<body>
        <h3 style="color: crimson">Welcome to the internet-shop!</h3>
        <form action="registration" method="post">
            <p>Please enter your first name and last name.</p>
            <hr>
            <label for="name"><b>First name</b></label>
            <input type="text" placeholder="first name" name="name" id="name" required>
            <label for="surname"><b>Last name</b></label>
            <input type="text" placeholder="last name" name="surname" id="surname" required>
            <hr>
            <button type="submit">Enter</button>
        </form>
</body>
</html>
