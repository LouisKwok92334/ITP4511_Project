/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/CheckOutStatisticServlet")
public class CheckOutStatisticServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ITP4511_Project";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<CheckoutStatistic> statistics = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            // 查询每个设备的名称和预订次数
            String sql = "SELECT e.name AS equipment_name, COUNT(b.booking_id) AS checkouts " +
                         "FROM Bookings b " +
                         "JOIN Equipment e ON b.equipment_id = e.equipment_id " +
                         "GROUP BY e.name";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CheckoutStatistic stat = new CheckoutStatistic();
                stat.setEquipmentName(rs.getString("equipment_name"));
                stat.setCheckouts(rs.getInt("checkouts"));
                statistics.add(stat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        StringBuilder json = new StringBuilder();
        json.append("[");

        for (int i = 0; i < statistics.size(); i++) {
            CheckoutStatistic stat = statistics.get(i);
            json.append("{");
            json.append("\"equipmentName\":\"").append(stat.getEquipmentName()).append("\",");
            json.append("\"checkouts\":").append(stat.getCheckouts());
            json.append("}");

            if (i < statistics.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");

        PrintWriter out = response.getWriter();
        out.print(json.toString());
        out.flush();
    }

    private class CheckoutStatistic {
        private String equipmentName;
        private int checkouts;

        public String getEquipmentName() {
            return equipmentName;
        }

        public void setEquipmentName(String equipmentName) {
            this.equipmentName = equipmentName;
        }

        public int getCheckouts() {
            return checkouts;
        }

        public void setCheckouts(int checkouts) {
            this.checkouts = checkouts;
        }
    }
}
