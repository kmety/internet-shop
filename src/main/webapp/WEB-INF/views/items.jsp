<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Items</title>
</head>
<body>

<table border="1">
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Price</th>
        <th>Update</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="item" items="${items}">
        <tr>
            <td>${item.id}</td>
            <td>${item.name}</td>
            <td>${item.price}</td>
            <td><a href="#">Update</a></td>
            <td><a href="deleteItem?item_id=${item.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>
<br>
<a href="createItem">Create new item</a>
<br>
<a href="../shop">Return to the shop</a>
<br>
</body>
</html>
