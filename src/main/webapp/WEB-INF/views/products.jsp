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
<form action="/src/main/webapp" method="get">
    <button type="submit">Home Page</button>
</form>
<h1>Product List</h1>

<div class="product-container">
    <c:forEach items="${products}" var="product">
        <div class="product-card">
            <h3>${product.name}</h3>
            <p>Price: $${product.price}</p>
            <p>Stock: ${product.stock}</p>
            <form action="/order?id=${product.id}" method="post">

                <button type="submit">Add to order</button>
            </form>

            <c:if test="${admin}">
                <div class="product-actions">
                    <form action="/product/delete" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="${product.id}">
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </form>
                </div>
            </c:if>
        </div>
    </c:forEach>
</div>

<c:if test="${admin}">
    <div class="admin-only">
        <h2>Admin Actions</h2>
        <a href="/product/add" class="btn btn-success">Add New Product</a>
    </div>
</c:if>

<c:if test="${not empty param.message}">
    <div class="alert alert-info">
            ${param.message}
    </div>
</c:if>
</body>
</html>