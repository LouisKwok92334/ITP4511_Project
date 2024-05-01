/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import java.io.IOException;
import java.sql.*;

/**
 *
 * @author puinamkwok
 */
public class UserDB {
    private String dburl;
    private String dbUser;
    private String dbPassword;

    public UserDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public boolean isValidUser(String user, String pwd) {
        boolean isValid = false;
        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbPassword);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM userInfo WHERE username=? AND password=?")) {
            stmt.setString(1, user);
            stmt.setString(2, pwd);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    isValid = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    public boolean addUserInfo(String id, String user, String pwd) {
        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbPassword);
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO userInfo (ID, USERNAME, PASSWORD) VALUES (?, ?, ?)")) {
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

    public void createUserInfoTable() {
        Connection cnnct = null;
        Statement stmnt = null;
        try {
            cnnct = getConnection();
            stmnt = cnnct.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS userInfo ("
                    + "id CHAR(5) NOT NULL, "
                    + "username VARCHAR(25) NOT NULL, "
                    + "password VARCHAR(25) NOT NULL, "
                    + "PRIMARY KEY (id)"
                    + ")";
            stmnt.execute(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (stmnt != null) {
                try {
                    stmnt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (cnnct != null) {
                try {
                    cnnct.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dburl, dbUser, dbPassword);
    }
}
