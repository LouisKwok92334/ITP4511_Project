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
        <script src="js/validatePassword.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script>
            $(document).ready(function () {
                $('form').on('submit', function (event) {
                    event.preventDefault();

                    var formData = $(this).serialize(); 
                    $.ajax({
                        type: 'POST',
                        url: 'UserServlet',
                        data: formData,
                        success: function (response) {
                            alert('User updated successfully!');
                        },
                        error: function () {
                            alert('Error updating user.');
                        }
                    });
                });
            });
        </script>
    </head>
    <body>
        <jsp:useBean id="userInfo" class="ict.bean.UserInfo" scope="session"/>
        <jsp:include page="header.jsp"/>
        <div class="container">
            <h2>Update User Information</h2>
            <form action="UserServlet" method="POST" class="needs-validation" novalidate>
                <input type="hidden" name="userId" value="${userInfo.userId}">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <input type="text" name="username" class="form-control" id="username" value="${userInfo.username}" >
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <input type="password" name="password" class="form-control" id="password" required>
                    <small id="passwordMessage" class="text-danger"></small>
                </div>
                <div class="form-group">
                    <label for="confirm_password">Confirm Password:</label>
                    <input type="password" name="confirm_password" class="form-control" id="confirm_password" required>
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
                    <input type="email" name="email" class="form-control" id="email"value="${userInfo.email}" >
                </div>
                <div class="form-group">
                    <label for="phoneNumber">Phone Number:</label>
                    <input type="text" name="phoneNumber" class="form-control" id="phoneNumber" value="${userInfo.phoneNumber}">
                </div>
                <button type="submit" class="btn btn-primary" id="submitBtn">Update User</button>
            </form>
        </div>
    </body>
</html>
