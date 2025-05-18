<%@ page import="com.misha.labam.entity.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User Registration</title>
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
<form action="/src/main/webapp" method="get">
    <button type="submit">Home Page</button>
</form>
<div class="container">
    <h2>User Registration</h2>
    <div>Email: <%
        User user = (User) request.getAttribute("user");
        out.println(user.getEmail());
    %>
    </div>
    <div>Role: <%
        out.println(user.getRole());
    %></div>
    <div id="message" class="message"></div>
</div>

<script>
    document.getElementById('registrationForm').addEventListener('submit', function(e) {
        e.preventDefault();

        const user = {
            password: document.getElementById('password').value,
            email: document.getElementById('email').value
        };

        fetch('/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(user)
        })
            .then(response => response.json())
            .then(data => {
                const messageDiv = document.getElementById('message');
                messageDiv.style.display = 'block';
                messageDiv.className = 'message success';
                messageDiv.textContent = data.message || 'Registration successful!';
                document.getElementById('registrationForm').reset();
            })
            .catch(error => {
                const messageDiv = document.getElementById('message');
                messageDiv.style.display = 'block';
                messageDiv.className = 'message error';
                messageDiv.textContent = 'Registration failed. Please try again.';
                console.error('Error:', error);
            });
    });
</script>
</body>
</html>