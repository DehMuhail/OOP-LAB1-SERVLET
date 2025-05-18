<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error - Page Not Found</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .error-container {
            max-width: 600px;
            margin: 50px auto;
            padding: 30px;
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }
        h1 {
            color: #e74c3c;
            margin-bottom: 20px;
        }
        p {
            color: #333;
            line-height: 1.6;
        }
        .home-link {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background-color: #3498db;
            color: white;
            text-decoration: none;
            border-radius: 3px;
        }
        .home-link:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>Page Not Found</h1>
        <p>The page you are looking for does not exist or is not available.</p>
        <p>Please check the URL or navigate back to the home page.</p>
        <a href="/" class="home-link">Go to Home Page</a>
    </div>
</body>
</html>