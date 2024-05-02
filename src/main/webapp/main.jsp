<%-- 
    Document   : main
    Created on : 2024年5月2日, 上午10:17:41
    Author     : puinamkwok
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp"/>

<!DOCTYPE html>
<html>
<<<<<<< Updated upstream
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main page</title>
    </head>
    <body>
        <jsp:useBean id="userInfo" class="ict.bean.UserInfo" scope="session"/>    
        <b>Hello, <%= userInfo.getUsername() %></b><br>
        <b>Your role is: <%= userInfo.getRole() %></b>
        <p>Welcome to the ICT</p>
        <form method="post" action="main">
            <input type="hidden" name="action" value="logout"/>
            <input type="submit" value="Logout" name="logoutButton"/>
        </form>
        <hr/>
        
        <!-- 公共链接 -->
        <a href="brandController?action=list">getAllBrands</a><br/>

        <!-- 仅管理员可见 -->
        <% if ("admin".equals(userInfo.getRole())) { %>
            <a href="adminController?action=manageUsers">Manage Users</a><br/>
        <% } %>
=======
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Main page</title>
    <!-- 引入 Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- 引入外部 CSS 文件 -->
    <link href="css/main.css" rel="stylesheet">
</head>
<body>
    <br>
<jsp:useBean id="userInfo" class="ict.bean.UserInfo" scope="session"/>
<div class="header">
    <h1>Hello, <%= userInfo.getUsername() %></h1>
    <h2>Your role is: <%= userInfo.getRole() %></h2>
</div>
<p>Welcome to the ICT</p>
<form method="post" action="main">
    <input type="hidden" name="action" value="logout"/>
    <input type="submit" class="btn btn-primary logout-button" value="Logout" name="logoutButton"/>
</form>
<hr/>
>>>>>>> Stashed changes


</body>
</html>