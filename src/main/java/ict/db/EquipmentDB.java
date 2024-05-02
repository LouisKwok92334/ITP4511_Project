/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import ict.bean.EquipmentBean;
import java.sql.*;
import java.util.ArrayList;
/**
 *
 * @author puinamkwok
 */
public class EquipmentDB {
    private String dburl;
    private String dbUser;
    private String dbPassword;

    public EquipmentDB(String dburl, String dbUser, String dbPassword) {
        this.dburl = dburl;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }
    
    public ArrayList<EquipmentBean> getAllEquipment() {
        ArrayList<EquipmentBean> equipments = new ArrayList<>();
        String sql = "SELECT * FROM Equipment";
        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EquipmentBean equipment = new EquipmentBean();
                equipment.setEquipmentId(rs.getInt("equipment_id"));
                equipment.setName(rs.getString("name"));
                equipment.setDescription(rs.getString("description"));
                equipment.setTotalQuantity(rs.getInt("total_quantity"));
                equipment.setAvailableQuantity(rs.getInt("available_quantity"));
                equipment.setStatus(rs.getString("status"));
                equipment.setLocation(rs.getString("location"));
                equipments.add(equipment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipments;
    }
    
    public ArrayList<EquipmentBean> getAllAvailableEquipment() {
        ArrayList<EquipmentBean> equipments = new ArrayList<>();
        String sql = "SELECT * FROM Equipment WHERE status = 'available'";
        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbPassword);
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                EquipmentBean equipment = new EquipmentBean();
                equipment.setEquipmentId(rs.getInt("equipment_id"));
                equipment.setName(rs.getString("name"));
                equipment.setDescription(rs.getString("description"));
                equipment.setTotalQuantity(rs.getInt("total_quantity"));
                equipment.setAvailableQuantity(rs.getInt("available_quantity"));
                equipment.setStatus(rs.getString("status"));
                equipment.setLocation(rs.getString("location"));
                equipments.add(equipment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipments;
    }
    
    public EquipmentBean getEquipmentById(int id) {
        EquipmentBean equipment = null;
        String sql = "SELECT * FROM Equipment WHERE equipment_id = ?";
        try (Connection conn = DriverManager.getConnection(dburl, dbUser, dbPassword);
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                equipment = new EquipmentBean();
                equipment.setEquipmentId(rs.getInt("equipment_id"));
                equipment.setName(rs.getString("name"));
                equipment.setDescription(rs.getString("description"));
                equipment.setTotalQuantity(rs.getInt("total_quantity"));
                equipment.setAvailableQuantity(rs.getInt("available_quantity"));
                equipment.setStatus(rs.getString("status"));
                equipment.setLocation(rs.getString("location"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipment;
    }
}
