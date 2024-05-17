package ict.servlet;

import com.google.gson.Gson;
import ict.bean.LocationStats;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/LocationStatisticServlet")
public class LocationStatisticServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/ITP4511_Project";
    private static final String USER = "root";
    private static final String PASSWORD = "";  // Assuming the default password for XAMPP

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String sql = "SELECT delivery_location, COUNT(*) as booking_count FROM Bookings GROUP BY delivery_location;";
        List<LocationStats> stats = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String location = rs.getString("delivery_location");
                int count = rs.getInt("booking_count");
                stats.add(new LocationStats(location, count));
            }
            String json = new Gson().toJson(stats);
            System.out.println("JSON Output: " + json); // Add this line to log the output
            response.getWriter().write(json);

        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"error\":\"Database error occurred: " + e.getMessage() + "\"}");
        }
    }
}
