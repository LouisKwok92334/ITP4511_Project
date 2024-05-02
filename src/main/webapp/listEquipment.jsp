<%-- 
    Document   : equipment
    Created on : 2024年5月2日, 下午4:06:46
    Author     : puinamkwok
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Equipment</title>
    </head>
    <body>
       <h1>Available Equipment for Rent</h1>
        <table border="1">
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Total Quantity</th>
                <th>Available Quantity</th>
                <th>Location</th>
            </tr>
            <% 
                List<EquipmentBean> equipmentList = (List<EquipmentBean>) request.getAttribute("equipmentList");
                if (equipmentList != null) {
                    for (EquipmentBean equipment : equipmentList) {
            %>
            <tr>
                <td><%= equipment.getName() %></td>
                <td><%= equipment.getDescription() %></td>
                <td><%= equipment.getTotalQuantity() %></td>
                <td><%= equipment.getAvailableQuantity() %></td>
                <td><%= equipment.getLocation() %></td>
            </tr>
            <% 
                    }
                } 
            %>
        </table>
    </body>
</html>
