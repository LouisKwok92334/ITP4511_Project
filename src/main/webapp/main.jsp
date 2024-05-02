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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="header.jsp"/>
    <br>
    <jsp:useBean id="userInfo" class="ict.bean.UserInfo" scope="session"/>
    <div class="header">
        <h1>Hello, <%= userInfo.getUsername() %></h1>
        <h2>Your role is: <%= userInfo.getRole() %></h2>
    </div>
    <p>Welcome to the ICT</p>
    <hr/>

</body>
</html>
