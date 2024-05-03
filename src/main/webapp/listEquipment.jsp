<%-- 
    Document   : equipment
    Created on : 2024年5月2日, 下午4:06:46
    Author     : puinamkwok
--%>

<%@ page import="java.util.List" %>
<%@ page import="ict.bean.EquipmentBean" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.HashSet" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="userInfo" class="ict.bean.UserInfo" scope="session"/>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Equipment</title>
        <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="header.jsp"/>

        <div class="container">
            <br>
            <h1>Available Equipment for Rent</h1>

            <!-- Search Bar -->
            <input type="text" id="searchBar" onkeyup="searchEquipment()" placeholder="Search for equipment..." class="form-control mb-4">

            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Available Quantity</th>
                        <th>Location</th>
                        <th>Staff Only</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                        Calendar cal = Calendar.getInstance();
                        String currentTime = sdf.format(cal.getTime());
                        cal.add(Calendar.MONTH, 1);
                        String oneMonthLater = sdf.format(cal.getTime());

                        HashSet<String> wishlist = (HashSet<String>) session.getAttribute("wishlist");
                        if (wishlist == null) {
                            wishlist = new HashSet<String>();
                            session.setAttribute("wishlist", wishlist);
                        }

                        List<EquipmentBean> equipmentList = (List<EquipmentBean>) request.getAttribute("equipmentList");
                        String userRole = userInfo.getRole();
                        if (equipmentList != null) {
                            for (EquipmentBean equipment : equipmentList) {
                                boolean staffOnly = equipment.isStaffOnly();
                                if (("staff".equals(userRole) || "technician".equals(userRole) || "admin".equals(userRole)) || !staffOnly) {
                    %>
                    <tr>
                        <td><%= equipment.getName() %></td>
                        <td><%= equipment.getDescription() %></td>
                        <td><%= equipment.getAvailableQuantity() %></td>
                        <td><%= equipment.getLocation() %></td>
                        <td>
                            <% if (staffOnly) { %>
                                Yes
                            <% } else { %>
                                No
                            <% } %>
                        </td>
                        <td>
                            <form action="" method="POST" style="display: inline;">
                                <!-- Reserve Button -->
                                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#reserveModal<%= equipment.getEquipmentId() %>">Reserve</button>
                            </form>
                            <button data-equipment-id="<%= equipment.getEquipmentId() %>" onclick="toggleWishList(<%= userInfo.getUserId() %>, <%= equipment.getEquipmentId() %>, this)"
                                class="btn"> <%= wishlist.contains(String.valueOf(equipment.getEquipmentId())) ? "❤️" : "♡" %>
                            </button>
                        </td>
                    </tr>
                    <% 
                                }
                            }
                        } 
                    %>
                </tbody>
            </table>
        </div>

        <% 
            if (equipmentList != null) {
                for (EquipmentBean equipment : equipmentList) {
        %>
        <!-- Reserve Modal -->
        <div class="modal fade" id="reserveModal<%= equipment.getEquipmentId() %>" tabindex="-1" role="dialog" aria-labelledby="reserveModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="reserveModalLabel">Reserve Equipment</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <input type="hidden" name="user_id" value="<%= userInfo.getUserId() %>">
                            <input type="hidden" name="equipment_id" value="<%= equipment.getEquipmentId() %>">
                            <div class="form-group">
                                <label>Quantity</label>
                                <input type="number" class="form-control" name="quantity" max="<%= equipment.getAvailableQuantity() %>" min="1" value="1">
                            </div>
                            <div class="form-group">
                                <label>Start Time</label>
                                <input type="datetime-local" class="form-control" name="start_time" value="<%= currentTime %>">
                            </div>
                            <div class="form-group">
                                <label>End Time</label>
                                <input type="datetime-local" class="form-control" name="end_time" value="<%= oneMonthLater %>">
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save Reservation</button>
                    </div>
                </div>
            </div>
        </div>
        <% 
                }
            } 
        %>

        <!-- Bootstrap JS and jQuery -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.3/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="js/listEquipment.js"></script>
    </body>
</html>