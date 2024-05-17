<%-- 
    Document   : main
    Created on : 2024年5月2日, 上午10:17:41
    Author     : puinamkwok
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="ict.bean.BookingBean" %>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Main page</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link href="css/main.css" rel="stylesheet">
</head>
<body>
    <jsp:include page="header.jsp"/>
    
    <jsp:useBean id="userInfo" class="ict.bean.UserInfo" scope="session"/>
    <div class="header">
        <h1>Hello, <%= userInfo.getUsername() %></h1>
        <h2>Your role is: <%= userInfo.getRole() %></h2>
        <h2>Your User id: <%= userInfo.getUserId() %></h2>
    </div>
    <p>Welcome to the ICT</p>
   
    <h1>Your Bookings</h1>
    <c:choose>
         <c:when test="${not empty bookings}">
             <table border="1">
                 <tr>
                     <th>預訂編號</th>
                     <th>預訂日期</th>
                     <th>狀態</th>
                 </tr>
                 <%-- 迭代預訂信息 --%>
                 <c:forEach var="booking" items="${bookings}">
                     <tr>
                         <td><c:out value="${booking.bookingId}" /></td>
                         <td><c:out value="${booking.status}" /></td>
                     </tr>
                 </c:forEach>
             </table>
         </c:when>
         <c:otherwise>
             <p>沒有找到任何預訂信息。</p>
         </c:otherwise>
     </c:choose>       
</body>
</html>
