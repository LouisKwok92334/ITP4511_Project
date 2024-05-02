/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.UserInfo;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author puinamkwok
 */
public class UserDB {
    private String dburl;
    private String dbUser;
    private String dbPassword;
    private Connection connection; // Add connection variable

    public UserDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }
    
    public UserInfo getUserByUsernameAndPassword(String username, String password) {
        // 實現JDBC連接、執行SQL查詢並返回UserBean
        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbPassword);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUserId(rs.getInt("user_id"));
                userInfo.setUsername(rs.getString("username"));
                userInfo.setPassword(rs.getString("password"));
                userInfo.setFirstName(rs.getString("first_name"));
                userInfo.setLastName(rs.getString("last_name"));
                userInfo.setEmail(rs.getString("email"));
                userInfo.setPhoneNumber(rs.getString("phone_number"));
                userInfo.setRole(rs.getString("role"));
                userInfo.setCreatedAt(rs.getTimestamp("created_at"));
                userInfo.setUpdatedAt(rs.getTimestamp("updated_at"));
                return userInfo;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean addUserInfo(String id, String user, String pwd) {
        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users (ID, USERNAME, PASSWORD) VALUES (?, ?, ?)")) {
            stmt.setString(1, id);
            stmt.setString(2, user);
            stmt.setString(3, pwd);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
public boolean updateUser(UserInfo user) throws SQLException {
    Connection conn = null;
    PreparedStatement ps = null;
    try {
        conn = DriverManager.getConnection(dburl, dbUser, dbPassword);
        String sql = "UPDATE Users SET password = ?, role = ?, first_name = ?, last_name = ?, email = ?, phone_number = ?, updated_at = ? WHERE username = ?";
        ps = conn.prepareStatement(sql);
        ps.setString(1, user.getPassword());
        ps.setString(2, user.getRole());
        ps.setString(3, user.getFirstName());
        ps.setString(4, user.getLastName());
        ps.setString(5, user.getEmail());
        ps.setString(6, user.getPhoneNumber());
        ps.setTimestamp(7, user.getUpdatedAt());
        ps.setString(8, user.getUsername());

        int result = ps.executeUpdate();
        return result > 0;
    } finally {
        if (ps != null) ps.close();
        if (conn != null) conn.close();
    }
}


    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }
}
