/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.BookingBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author user
 */
public class BookingDB {
    private String dburl;
    private String dbUser;
    private String dbPassword;

    public BookingDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }
    
    public List<BookingBean> getBookingsByUserId(int userId) throws SQLException {
        List<BookingBean> bookings = new ArrayList<>();
        String sql = "SELECT * FROM Bookings WHERE user_id = ?";
        try (Connection connection = DriverManager.getConnection(dburl, dbUser, dbPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                BookingBean booking = new BookingBean();
                booking.setBookingId(resultSet.getInt("booking_id"));
                booking.setUserId(resultSet.getInt("user_id"));
                booking.setEquipmentId(resultSet.getInt("equipment_id"));
                booking.setQuantity(resultSet.getInt("quantity"));
                booking.setStartTime(resultSet.getTimestamp("start_time"));
                booking.setEndTime(resultSet.getTimestamp("end_time"));
                booking.setStatus(resultSet.getString("status"));
                bookings.add(booking);
            }
        }
        return bookings;
    }
}
