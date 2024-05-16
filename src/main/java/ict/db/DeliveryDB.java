/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.DeliveryBean;
import java.sql.*;

public class DeliveryDB {

    private String dburl;
    private String dbUser;
    private String dbPassword;

    public DeliveryDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public DeliveryBean getDeliveryByBookingId(int bookingId) throws SQLException {
        DeliveryBean delivery = null;
        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbPassword); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Deliveries WHERE booking_id = ?")) {
            stmt.setInt(1, bookingId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    delivery = new DeliveryBean();
                    delivery.setDeliveryId(rs.getInt("delivery_id"));
                    delivery.setCourierId(rs.getInt("courier_id"));
                    delivery.setPickupLocation(rs.getString("pickup_location"));
                    delivery.setStatus(rs.getString("status"));
                }
            }
        }
        return delivery;
    }

    public void createDelivery(int bookingId) throws SQLException {
        String fetchBookingSQL = "SELECT user_id, delivery_location FROM Bookings WHERE booking_id = ?";
        String insertDeliverySQL = "INSERT INTO Deliveries (booking_id, courier_id, pickup_location, status, scheduled_time) "
                + "VALUES (?, ?, ?, 'scheduled', CURRENT_TIMESTAMP)";

        try (Connection connection = DriverManager.getConnection(dburl, dbUser, dbPassword); PreparedStatement fetchBookingStmt = connection.prepareStatement(fetchBookingSQL); PreparedStatement insertDeliveryStmt = connection.prepareStatement(insertDeliverySQL)) {

            // Fetch booking details
            fetchBookingStmt.setInt(1, bookingId);
            ResultSet resultSet = fetchBookingStmt.executeQuery();

            if (resultSet.next()) {
                int courierId = resultSet.getInt("user_id"); // Assuming the user is also the courier for now
                String pickupLocation = resultSet.getString("delivery_location");

                // Insert delivery record
                insertDeliveryStmt.setInt(1, bookingId);
                insertDeliveryStmt.setInt(2, courierId);
                insertDeliveryStmt.setString(3, pickupLocation);
                insertDeliveryStmt.executeUpdate();
            }
        }
    }

    public void updateDeliveryStatus(int deliveryId, String status) throws SQLException {
        String sql = "UPDATE Deliveries SET status = ? WHERE delivery_id = ?";
        try (Connection connection = DriverManager.getConnection(dburl, dbUser, dbPassword); PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, deliveryId);
            statement.executeUpdate();
        }
    }
}
