<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Edit item</title>
</head>
<body>
<form action="editItem" method="post">
    <div>
        <h3>Edit item</h3>
        <hr>

        <label for="name"><b>Name</b></label>
        <input type="text" placeholder="Enter name" name="name" id="name" value="${item.name}" required>

        <label for="price"><b>Price</b></label>
        <input type="text" placeholder="Enter price" name="price" id="price" value="${item.price}" required>

        <input type="hidden" name="item_id" id="item_id" value="${item.id}">

        <hr>

        <button type="submit">Submit</button>
    </div>

</form>
<a href="items">Return to items list</a>
</body>
</html>
