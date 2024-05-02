<%-- 
    Document   : header
    Created on : 2024年5月2日, 下午6:14:34
    Author     : boscochuen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:useBean id="userInfo" class="ict.bean.UserInfo" scope="session"/>
<!DOCTYPE html>
<head>
    <meta charset="UTF-8">
    <title>Booking System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/header.css" rel="stylesheet"> 
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">
            <img src="img/download.png" alt="Logo"> 
            Booking System
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="#">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="equipment?action=listAvailable">View Available Equipment</a>
                </li>
                <% if ("admin".equals(userInfo.getRole())) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="adminController?action=manageUsers">Manage Users</a>
                    </li>
                <% } %>
                <% if ("technician".equals(userInfo.getRole())) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="techController?action=viewReports">View Reports</a>
                    </li>
                <% } %>
                <% if ("staff".equals(userInfo.getRole())) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="staffController?action=viewTasks">View Tasks</a>
                    </li>
                <% } %>
                <% if ("courier".equals(userInfo.getRole())) { %>
                    <li class="nav-item">
                        <a class="nav-link" href="courierController?action=trackDeliveries">Track Deliveries</a>
                    </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>

<!-- 引入 Popper 和 Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js"></script>
</body>
</html>
