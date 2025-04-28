<%@ page import="com.misha.labam.entity.User" %>
<%@ page import="com.misha.labam.entity.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Login</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 400px;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #333;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }
        input[type="text"],
        input[type="password"],
        input[type="email"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            box-sizing: border-box;
        }
        button {
            width: 100%;
            padding: 10px;
            background-color: #5cb85c;
            border: none;
            color: white;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #4cae4c;
        }
        .message {
            margin-top: 15px;
            padding: 10px;
            border-radius: 4px;
            display: none;
        }
        .success {
            background-color: #dff0d8;
            color: #3c763d;
        }
        .error {
            background-color: #f2dede;
            color: #a94442;
        }
    </style>
</head>
<body>
<div class="container">
    <form action="/login" method="get">
        <button type="submit">Go to Login Page</button>
    </form>
    <form action="/registration" method="get">
        <button type="submit">Go to Register Page</button>
    </form>
    <form action="/profile" method="get">
        <button type="submit">Go to Profile Page</button>
    </form>
    <%
        User user = (User) request.getAttribute("user");
        if (user != null && user.getRole().equals(Role.ADMIN)) {
    %>
    <form action="/products" method="get">
        <button type="submit" class="admin-btn">Manage Products (Admin Only)</button>
    </form>
    <form action="/users" method="get">
        <button type="submit" class="admin-btn">Manage Users (Admin Only)</button>
    </form>
    <%
        }
    %>
    <%

        if (user != null && user.getRole().equals(Role.USER)) {
    %>
    <form action="/products" method="get">
        <button type="submit" class="admin-btn">Products</button>
    </form>
    <%
        }
    %>
    <form action="/logout" method="get">
        <button type="submit">Logout </button>
    </form>
    <div>
        <%
            if (user!=null){
                       out.println(user.getEmail());
           }else out.println("Unauthorized user");
        %>
    </div>
</div>

</body>
</html>