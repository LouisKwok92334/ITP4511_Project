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
</head>
<body>
    <jsp:include page="header.jsp"/>
    <form action="UserServlet" method="POST">
        Username: <input type="text" name="username" required><br>
        Password: <input type="password" name="password" required><br>
        Role: <select name="role">
            <option value="user">User</option>
            <option value="staff">Staff</option>
            <option value="technician">Technician</option>
            <option value="admin">Admin</option>
            <option value="courier">Courier</option>
        </select><br>
        First Name: <input type="text" name="firstName"><br>
        Last Name: <input type="text" name="lastName"><br>
        Email: <input type="email" name="email"><br>
        Phone Number: <input type="text" name="phoneNumber"><br>
        <input type="submit" value="Update User">
    </form>
</body>
</html>

