/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import java.sql.*;
import java.util.HashSet;

/**
 *
 * @author puinamkwok
 */
public class WishListDB {
    private String dburl;
    private String dbUser;
    private String dbPassword;

    public WishListDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }
    
    public boolean addToWishList(int userId, int equipmentId) {
        String sql = "INSERT INTO wishlist (user_id, equipment_id) VALUES (?, ?)";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, equipmentId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public HashSet<Integer> getUserWishList(int userId) {
        HashSet<Integer> equipmentIds = new HashSet<>();
        String sql = "SELECT equipment_id FROM wishlist WHERE user_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                equipmentIds.add(rs.getInt("equipment_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipmentIds;
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }
}
