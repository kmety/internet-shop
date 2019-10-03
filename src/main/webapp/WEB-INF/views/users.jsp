<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Users list</title>
</head>
<body>
You entered as ${sessionScope.loggedInUser.name}
<form action="../logout">
    <button type="submit">Logout</button>
</form>
<h3>Users list:</h3>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Surname</th>
        <th>Login</th>
        <th>Delete</th>
    </tr>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.id}</td>
            <td>${user.name}</td>
            <td>${user.surname}</td>
            <td>${user.login}</td>
            <td><a href="deleteUser?user_id=${user.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<hr>
<a href="../registration">Register new user</a><br>
<a href="../index">To the main page</a>
</body>
</html>
