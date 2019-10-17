<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>
        <title>Create item</title>
    </head>
    <body>
        <form action="createItem" method="post">
            <div>
                <h3>Let's create a new Item!</h3>
                <hr>
                <label for="name"><b>Name</b></label>
                <input type="text" placeholder="Enter name" name="name" id="name" required>
                <label for="price"><b>Price</b></label>
                <input type="text" placeholder="Enter price" name="price" id="price" required>
                <hr>
                <button type="submit">Create</button>
            </div>
        </form>
        <a href="items">Return to items list</a>
    </body>
</html>
