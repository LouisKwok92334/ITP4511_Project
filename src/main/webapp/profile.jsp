<%-- 
    Document   : profile
    Created on : 2024年5月2日, 下午10:39:54
    Author     : boscochuen
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Update User</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/profile.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="header.jsp"/>
    <jsp:useBean id="userInfo" class="ict.bean.UserInfo" scope="session"/>
    <div class="container">
        <h2>Update User Information</h2>
        <form action="UserServlet" method="POST" class="needs-validation" novalidate>
            <input type="hidden" name="userId" value="${userInfo.userId}">
            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" name="username" class="form-control" id="username" value="${userInfo.username}" required>
            </div>
            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" name="password" class="form-control" id="password" value="" required>
            </div>
            <div class="form-group">
                <label for="role">Role:</label>
                <select name="role" class="form-control" id="role">
                    <option value="user" ${userInfo.role eq 'user' ? 'selected' : ''}>User</option>
                    <option value="staff" ${userInfo.role eq 'staff' ? 'selected' : ''}>Staff</option>
                    <option value="technician" ${userInfo.role eq 'technician' ? 'selected' : ''}>Technician</option>
                    <option value="admin" ${userInfo.role eq 'admin' ? 'selected' : ''}>Admin</option>
                    <option value="courier" ${userInfo.role eq 'courier' ? 'selected' : ''}>Courier</option>
                </select>
            </div>
            <div class="form-group">
                <label for="firstName">First Name:</label>
                <input type="text" name="firstName" class="form-control" id="firstName" value="${userInfo.firstName}">
            </div>
            <div class="form-group">
                <label for="lastName">Last Name:</label>
                <input type="text" name="lastName" class="form-control" id="lastName" value="${userInfo.lastName}">
            </div>
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" name="email" class="form-control" id="email" value="${userInfo.email}">
            </div>
            <div class="form-group">
                <label for="phoneNumber">Phone Number:</label>
                <input type="text" name="phoneNumber" class="form-control" id="phoneNumber" value="${userInfo.phoneNumber}">
            </div>
            <button type="submit" class="btn btn-primary">Update User</button>
        </form>
    </div>
</body>
</html>
