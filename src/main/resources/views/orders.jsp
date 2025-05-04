<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Products</title>
    <style>
        .product-container {
            display: flex;
            flex-wrap: wrap;
            gap: 20px;
            margin: 20px;
        }
        .product-card {
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 15px;
            width: 250px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .product-actions {
            margin-top: 10px;
            display: flex;
            gap: 10px;
        }
        .admin-only {
            background-color: #f8f9fa;
            padding: 20px;
            margin-top: 20px;
            border-top: 1px solid #ddd;
        }
    </style>
</head>
<body>
<form action="/" method="get">
    <button type="submit">Home Page</button>
</form>
<h1>Orders </h1>

<div class="product-container">
    <c:forEach items="${orders}" var="order">
        <div class="product-card">
            <h3>${order.product.id}</h3>
            <h3>${order.product.name}</h3>
            <h3>${order.product.price}</h3>
            <h3>${order.product.stock}</h3>
            <form action="/orderdelete?id=${order.product.id}" method="get">
                <button type="submit">Delete</button>
            </form>
        </div>
    </c:forEach>
    <form action="/orderdelete" method="get">
        <button type="submit">Delete All</button>
    </form>
</div>
</body>
</html>