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

    public void createDelivery(int bookingId) throws SQLException {
        String fetchBookingSQL = "SELECT user_id, delivery_location FROM Bookings WHERE booking_id = ?";
        String insertDeliverySQL = "INSERT INTO Deliveries (booking_id, courier_id, pickup_location, status, scheduled_time) "
                + "VALUES (?, ?, ?, 'scheduled', CURRENT_TIMESTAMP)";

        try (Connection connection = DriverManager.getConnection(dburl, dbUser, dbPassword); PreparedStatement fetchBookingStmt = connection.prepareStatement(fetchBookingSQL); PreparedStatement insertDeliveryStmt = connection.prepareStatement(insertDeliverySQL)) {

            fetchBookingStmt.setInt(1, bookingId);
            ResultSet resultSet = fetchBookingStmt.executeQuery();

            if (resultSet.next()) {
                int courierId = resultSet.getInt("user_id"); // Assuming the user is also the courier for now
                String pickupLocation = resultSet.getString("delivery_location");

                insertDeliveryStmt.setInt(1, bookingId);
                insertDeliveryStmt.setInt(2, courierId);
                insertDeliveryStmt.setString(3, pickupLocation);
                insertDeliveryStmt.executeUpdate();
            }
        }
    }

    public DeliveryBean getDeliveryByBookingId(int bookingId) throws SQLException {
        String sql = "SELECT * FROM Deliveries WHERE booking_id = ?";
        DeliveryBean delivery = null;
        try (Connection connection = DriverManager.getConnection(dburl, dbUser, dbPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, bookingId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                delivery = new DeliveryBean();
                delivery.setDeliveryId(resultSet.getInt("delivery_id"));
                delivery.setBookingId(resultSet.getInt("booking_id"));
                delivery.setCourierId(resultSet.getInt("courier_id"));
                delivery.setPickupLocation(resultSet.getString("pickup_location"));
                delivery.setStatus(resultSet.getString("status"));
                delivery.setScheduledTime(resultSet.getTimestamp("scheduled_time"));
                delivery.setDeliveredTime(resultSet.getTimestamp("delivered_time"));
                delivery.setCreatedAt(resultSet.getTimestamp("created_at"));
                delivery.setUpdatedAt(resultSet.getTimestamp("updated_at"));
            }
        }
        return delivery;
    }

    public void createDelivery(int bookingId) throws SQLException {
        String fetchBookingSQL = "SELECT user_id, delivery_location FROM Bookings WHERE booking_id = ?";
        String insertDeliverySQL = "INSERT INTO Deliveries (booking_id, courier_id, pickup_location, status, scheduled_time) " +
                                   "VALUES (?, ?, ?, 'scheduled', CURRENT_TIMESTAMP)";

        try (Connection connection = DriverManager.getConnection(dburl, dbUser, dbPassword);
             PreparedStatement fetchBookingStmt = connection.prepareStatement(fetchBookingSQL);
             PreparedStatement insertDeliveryStmt = connection.prepareStatement(insertDeliverySQL)) {

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
        try (Connection connection = DriverManager.getConnection(dburl, dbUser, dbPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, deliveryId);
            statement.executeUpdate();
        }
    }
}
