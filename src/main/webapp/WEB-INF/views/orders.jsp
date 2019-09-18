<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Show orders</title>
</head>
<body>
<h3>All orders of ${user.name}</h3>

<table border="1">
    <tr>
        <th>Date</th>
        <th>Total price</th>
        <th>Delete</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>${order.date}</td>
            <td>${order.totalPrice}</td>
            <td><a href="deleteOrder?order_id=${order.id}">Delete</a></td>
        </tr>
    </c:forEach>
</table>

<br>

<a href="bucket">Show user's bucket</a>
<br>
<a href="shop">Return to the shop</a>

</body>
</html>
