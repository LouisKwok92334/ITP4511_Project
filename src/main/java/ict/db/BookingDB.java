package ict.db;

import ict.bean.BookingBean;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * BookingDB handles database operations related to bookings.
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

    /**
     * Retrieves all bookings made by a specific user.
     * 
     * @param userId the ID of the user
     * @return a list of BookingBean objects
     * @throws SQLException if a database access error occurs
     */
    public List<BookingBean> getBookingsByUserId(int userId) throws SQLException {
        List<BookingBean> bookings = new ArrayList<>();
        String sql = "SELECT b.booking_id, b.user_id, b.equipment_id, e.name AS equipment_name, b.start_time, b.end_time, b.delivery_location, b.status "
                   + "FROM Bookings b "
                   + "JOIN Equipment e ON b.equipment_id = e.equipment_id "
                   + "WHERE b.user_id = ?";
        try (Connection connection = DriverManager.getConnection(dburl, dbUser, dbPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                BookingBean booking = new BookingBean();
                booking.setBookingId(resultSet.getInt("booking_id"));
                booking.setUserId(resultSet.getInt("user_id"));
                booking.setEquipmentId(resultSet.getInt("equipment_id"));
                booking.setEquipmentName(resultSet.getString("equipment_name"));
                booking.setStartTime(resultSet.getTimestamp("start_time"));
                booking.setEndTime(resultSet.getTimestamp("end_time"));
                booking.setDeliveryLocation(resultSet.getString("delivery_location"));
                booking.setStatus(resultSet.getString("status"));
                bookings.add(booking);
            }
        }
        return bookings;
    }

    /**
     * Saves a new booking to the database.
     * 
     * @param booking the BookingBean object to be saved
     * @throws SQLException if a database access error occurs
     */
    public void saveBooking(BookingBean booking) throws SQLException {
        String sql = "INSERT INTO Bookings (user_id, equipment_id, start_time, end_time, delivery_location) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(dburl, dbUser, dbPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, booking.getUserId());
            statement.setInt(2, booking.getEquipmentId());
            statement.setTimestamp(3, booking.getStartTime());
            statement.setTimestamp(4, booking.getEndTime());
            statement.setString(5, booking.getDeliveryLocation());
            statement.executeUpdate();
        }
    }

    /**
     * Retrieves all bookings from the database.
     * 
     * @return a list of BookingBean objects
     * @throws SQLException if a database access error occurs
     */
    public List<BookingBean> getAllBookings() throws SQLException {
        List<BookingBean> bookings = new ArrayList<>();
        String sql = "SELECT b.booking_id, b.user_id, e.name AS equipment_name, b.start_time, b.end_time, b.delivery_location, b.status "
                   + "FROM Bookings b "
                   + "JOIN Equipment e ON b.equipment_id = e.equipment_id";
        try (Connection connection = DriverManager.getConnection(dburl, dbUser, dbPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                BookingBean booking = new BookingBean();
                booking.setBookingId(resultSet.getInt("booking_id"));
                booking.setUserId(resultSet.getInt("user_id"));
                booking.setEquipmentName(resultSet.getString("equipment_name"));
                booking.setStartTime(resultSet.getTimestamp("start_time"));
                booking.setEndTime(resultSet.getTimestamp("end_time"));
                booking.setDeliveryLocation(resultSet.getString("delivery_location"));
                booking.setStatus(resultSet.getString("status"));
                bookings.add(booking);
            }
        }
        return bookings;
    }

    /**
     * Updates the status of a booking in the database.
     * 
     * @param bookingId the ID of the booking to be updated
     * @param status the new status of the booking
     * @throws SQLException if a database access error occurs
     */
    public void updateBookingStatus(int bookingId, String status) throws SQLException {
        String sql = "UPDATE Bookings SET status = ? WHERE booking_id = ?";
        try (Connection connection = DriverManager.getConnection(dburl, dbUser, dbPassword);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
            statement.setInt(2, bookingId);
            statement.executeUpdate();
        }
    }
}
