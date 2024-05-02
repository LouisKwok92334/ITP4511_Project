<%-- 
    Document   : main
    Created on : 2024年5月2日, 上午10:17:41
    Author     : puinamkwok
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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

        <!-- 仅技术人员可见 -->
        <% if ("technician".equals(userInfo.getRole())) { %>
            <a href="techController?action=viewReports">View Reports</a><br/>
        <% } %>

        <!-- 仅员工可见 -->
        <% if ("staff".equals(userInfo.getRole())) { %>
            <a href="staffController?action=viewTasks">View Tasks</a><br/>
        <% } %>

        <!-- 仅快递员可见 -->
        <% if ("courier".equals(userInfo.getRole())) { %>
            <a href="courierController?action=trackDeliveries">Track Deliveries</a><br/>
        <% } %>
        
    </body>
</html>
