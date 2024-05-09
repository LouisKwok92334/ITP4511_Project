<%-- 
    Document   : inventory
    Created on : 2024年5月4日, 上午12:19:06
    Author     : boscochuen
--%>
<%@ page import="java.util.List" %>
<%@ page import="ict.bean.EquipmentBean" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <jsp:include page="header.jsp"/>
        <link href="css/inventory.css" rel="stylesheet">
        <script src="js/inventory.js"></script>
    </head>
    <body>
        <div class="header">
            <h1>Inventory Data</h1>
            <div id="inventory"></div>
        </div>

        <!-- The Modal -->
        <div id="editModal" style="display:none; position: fixed; z-index: 1; left: 0; top: 0; width: 100%; height: 100%; overflow: auto; background-color: rgb(0,0,0); background-color: rgba(0,0,0,0.4);">
            <div style="background-color: #fefefe; margin: 5% auto; padding: 20px; border: 1px solid #888; width: 50%;">
                <span onclick="closeModal()" style="color: #aaa; float: right; font-size: 28px; font-weight: bold;">&times;</span>
                <p>Edit Equipment</p>
                <form id="editEquipmentForm">
                    <input type="hidden" id="editId">
                    Name: <input type="text" id="editName"  disabled><br>
                    Description: <input type="text" id="editDescription"  disabled><br>
                    Status:<br>
                    <select id="editStatus">
                        <option value="available">Available</option>
                        <option value="unavailable">Unavailable</option>
                        <option value="maintenance">Maintenance</option>
                        <option value="reserved">Reserved</option>
                    </select><br>
                    Location: <input type="text" id="editLocation"  disabled><br>
                    Staff Only: <input type="checkbox" id="editStaffOnly" disabled ><br>

                    <button type="button" onclick="submitEdit()">Save Changes</button>
                </form>
            </div>
        </div>
    </body>
</html>
