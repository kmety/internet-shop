<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users list</title>
</head>
<body>
<h3>Users list:</h3>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Login</th>
        <th>Password</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.surname}</td>
            <td>${user.login}</td>
            <td>${user.password}</td>
            <td><a href="deleteUser?user_id=${user.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<hr>
<a href="index">To the main page</a><br>
<a href="registration">Register new user</a>
</body>
</html>
