/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.servlet;

/**
 *
 * @author boscochuen
 */
import java.io.IOException;
import java.sql.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AccountServlet")
public class AccountServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ITP4511_Project";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("list".equals(action)) {
            listAccounts(response);
        } else if ("get".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("user_id"));
            getAccount(userId, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("update".equals(action)) {
            updateAccount(request, response);
        }
    }

    private void listAccounts(HttpServletResponse response) throws IOException {
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Users")) {
            while (rs.next()) {
                Account account = new Account(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("phone_number")
                );
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String json = convertListToJson(accounts);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    private void getAccount(int userId, HttpServletResponse response) throws IOException {
        Account account = null;
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE user_id = ?")) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    account = new Account(
                            rs.getInt("user_id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("role"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getString("email"),
                            rs.getString("phone_number")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String json = convertObjectToJson(account);
        response.setContentType("application/json");
        response.getWriter().write(json);
    }

    private void updateAccount(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("user_id"));
        String username = request.getParameter("username");
        String firstName = request.getParameter("first_name");
        String lastName = request.getParameter("last_name");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone_number");
        String role = request.getParameter("role");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement("UPDATE Users SET username = ?, first_name = ?, last_name = ?, email = ?, phone_number = ?, role = ? WHERE user_id = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, firstName);
            stmt.setString(3, lastName);
            stmt.setString(4, email);
            stmt.setString(5, phoneNumber);
            stmt.setString(6, role);
            stmt.setInt(7, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.setContentType("text/plain");
        response.getWriter().write("Success");
    }

    private String convertListToJson(List<Account> accounts) {
        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < accounts.size(); i++) {
            json.append(convertObjectToJson(accounts.get(i)));
            if (i < accounts.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        return json.toString();
    }

    private String convertObjectToJson(Account account) {
        if (account == null) {
            return "{}";
        }
        return "{" +
                "\"user_id\":" + account.user_id + "," +
                "\"username\":\"" + account.username + "\"," +
                "\"password\":\"" + account.password + "\"," +
                "\"role\":\"" + account.role + "\"," +
                "\"first_name\":\"" + account.first_name + "\"," +
                "\"last_name\":\"" + account.last_name + "\"," +
                "\"email\":\"" + account.email + "\"," +
                "\"phone_number\":\"" + account.phone_number + "\"" +
                "}";
    }

    private static class Account {
        private int user_id;
        private String username;
        private String password;
        private String role;
        private String first_name;
        private String last_name;
        private String email;
        private String phone_number;

        public Account(int user_id, String username, String password, String role, String first_name, String last_name, String email, String phone_number) {
            this.user_id = user_id;
            this.username = username;
            this.password = password;
            this.role = role;
            this.first_name = first_name;
            this.last_name = last_name;
            this.email = email;
            this.phone_number = phone_number;
        }
    }
}
