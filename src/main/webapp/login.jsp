<%-- 
    Document   : login
    Created on : 2024年5月1日, 下午10:42:34
    Author     : puinamkwok
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/login.css" rel="stylesheet">
    <style>
        body {
            background: url('img/Programme_ICT.jpg') no-repeat center center fixed;
            background-size: cover;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
            padding-top: 70px; /* Adjust based on navbar height */
        }
        .navbar {
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1000;
        }
        .login-container {
            background: rgba(255, 255, 255, 0.9); /* Slightly transparent background */
            padding: 2rem;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            margin-top: -70px; /* Adjust based on navbar height */
        }
        .form-control {
            margin-bottom: 1rem;
        }
        .login-title {
            text-align: center;
            margin-bottom: 2rem;
        }
        .navbar-brand img {
            width: 200px;
        }
    </style>
</head>
<body>
    <!-- Simple Header with Logo -->
    <nav class="navbar navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="main.jsp">
                <img src="img/download.png" alt="Logo">
            </a>
        </div>
    </nav>

    <div class="login-container">
        <h2 class="login-title">Login to Your Account</h2>
        <form method="post" action="main">
            <input type="hidden" name="action" value="authenticate"/>
            <div class="mb-3">
                <label for="username" class="form-label">Login</label>
                <input type="text" class="form-control" name="username" maxLength="10" id="username" value="user1">
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" name="password" maxLength="10" id="password" value="abc17823">
            </div>
            <div class="text-center">
                <button type="submit" class="btn btn-primary">Login</button>
            </div>
        </form>
    </div>
</body>
</html>
