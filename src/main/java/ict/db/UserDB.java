/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.UserInfo;
import ict.bean.UserProfile;
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
    
  public UserInfo getUserById(int userId) {
        UserInfo userInfo = null;
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        
        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                userInfo = new UserInfo();
                userInfo.setUserId(rs.getInt("user_id"));
                userInfo.setUsername(rs.getString("username"));
                userInfo.setPassword(rs.getString("password"));  // Consider security implications
                userInfo.setRole(rs.getString("role"));
                userInfo.setFirstName(rs.getString("first_name"));
                userInfo.setLastName(rs.getString("last_name"));
                userInfo.setEmail(rs.getString("email"));
                userInfo.setPhoneNumber(rs.getString("phone_number"));
                userInfo.setCreatedAt(rs.getTimestamp("created_at"));
                userInfo.setUpdatedAt(rs.getTimestamp("updated_at"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userInfo;
    }
  
    public UserInfo getUserByUsernameAndPassword(String username, String password) {
        // 實現JDBC連接、執行SQL查詢並返回UserBean
        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbPassword); PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Users WHERE username = ? AND password = ?")) {
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
        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbPassword); PreparedStatement stmt = conn.prepareStatement("INSERT INTO Users (ID, USERNAME, PASSWORD) VALUES (?, ?, ?)")) {
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

    public boolean updateUser(UserProfile UserProfile) {
        StringBuilder sql = new StringBuilder("UPDATE Users SET updated_At = CURRENT_TIMESTAMP");

        if (UserProfile.getPassword() != null) {
            sql.append(", password = ?");
        }
        if (UserProfile.getFirstName() != null) {
            sql.append(", first_Name = ?");
        }
        if (UserProfile.getLastName() != null) {
            sql.append(", last_Name = ?");
        }
        if (UserProfile.getEmail() != null) {
            sql.append(", email = ?");
        }
        if (UserProfile.getPhoneNumber() != null) {
            sql.append(", phone_number = ?");
        }
        if (UserProfile.getUsername() != null) {
            sql.append(", username = ?");
        }
        sql.append(" WHERE user_id = ?");

        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbPassword); PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            int index = 1;
            if (UserProfile.getPassword() != null) {
                pstmt.setString(index++, UserProfile.getPassword());
            }

            if (UserProfile.getFirstName() != null) {
                pstmt.setString(index++, UserProfile.getFirstName());
            }
            if (UserProfile.getLastName() != null) {
                pstmt.setString(index++, UserProfile.getLastName());
            }
            if (UserProfile.getEmail() != null) {
                pstmt.setString(index++, UserProfile.getEmail());
            }
            if (UserProfile.getPhoneNumber() != null) {
                pstmt.setString(index++, UserProfile.getPhoneNumber());
            }
            if (UserProfile.getUsername() != null) {
                pstmt.setString(index++, UserProfile.getUsername());
            }
            pstmt.setInt(index, UserProfile.getUserId());

            int updated = pstmt.executeUpdate();
            return updated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}