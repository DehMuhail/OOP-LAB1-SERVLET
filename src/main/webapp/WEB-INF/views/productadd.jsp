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
<h1>Product Create</h1>

<form action="/product" method="post">
    <label for="name">Name:
        <input id="name" name="name" type="text">
    </label>
    <label for="price">Price:
        <input id="price" name="price" type="number">
    </label>
    <label for="stock">Stock:
        <input id="stock"  name="stock" type="number">
    </label>
    <button type="submit">Add</button>
</form>
</body>
</html>