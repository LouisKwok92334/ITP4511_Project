import ict.bean.BookingBean;
import ict.bean.UserInfo;
import ict.db.BookingDB;
import ict.db.DeliveryDB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date; 
import java.util.*;

/**
 *
 * @author user
 */
@WebServlet("/booking")
public class BookingServlet extends HttpServlet {
    private BookingDB bookingDB;
    private DeliveryDB deliveryDB;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ServletException("MySQL JDBC driver not found", e);
        }

        String dbUser = this.getServletContext().getInitParameter("dbUser");
        String dbPassword = this.getServletContext().getInitParameter("dbPassword");
        String dbUrl = this.getServletContext().getInitParameter("dbUrl");
        bookingDB = new BookingDB(dbUrl, dbUser, dbPassword);
        deliveryDB = new DeliveryDB(dbUrl, dbUser, dbPassword);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "view":
                showBookings(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_IMPLEMENTED);
                break;
        }
    }
    
    private void showBookings(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        if (userInfo == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            List<BookingBean> bookings = bookingDB.getBookingsByUserId(userInfo.getUserId());
            request.setAttribute("bookings", bookings);
            request.getRequestDispatcher("/viewBookings.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Unable to retrieve bookings", e);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "create":
                createBooking(request, response);
                break;
            case "update":
                updateBooking(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
                break;
        }
    }
    
    private void createBooking(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("user_id"));
        int equipmentId = Integer.parseInt(request.getParameter("equipment_id"));
        String startTimeStr = request.getParameter("start_time");
        String endTimeStr = request.getParameter("end_time");
        String deliveryLocation = request.getParameter("delivery_location");

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date parsedStartDate = dateFormat.parse(startTimeStr);
            Timestamp startTime = new Timestamp(parsedStartDate.getTime());
            Date parsedEndDate = dateFormat.parse(endTimeStr);
            Timestamp endTime = new Timestamp(parsedEndDate.getTime());

            BookingBean booking = new BookingBean();
            booking.setUserId(userId);
            booking.setEquipmentId(equipmentId);
            booking.setStartTime(startTime);
            booking.setEndTime(endTime);
            booking.setDeliveryLocation(deliveryLocation);

            int bookingId = bookingDB.saveBooking(booking);

            // Create a delivery entry for the new booking
            if ("pending".equals(booking.getStatus())) {
                deliveryDB.createDelivery(bookingId);
            }

            response.sendRedirect("equipment?action=listAvailable"); 
        } catch (ParseException e) {
            throw new ServletException("Unable to parse date format", e);
        } catch (SQLException e) {
            throw new ServletException("Unable to save booking", e);
        }
    }
    
    private void updateBooking(HttpServletRequest request, HttpServletResponse response) {
        // 更新預訂的實現
    }
}
