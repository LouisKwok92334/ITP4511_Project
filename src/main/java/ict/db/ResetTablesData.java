/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ict.db;

import java.sql.*;

/**
 *
 * Author: puinamkwok
 */

public class ResetTablesData {
    public static void main(String[] args) {
        String dbUrl = "jdbc:mysql://localhost:3306/ITP4511_Project";
        String user = "root";
        String password = ""; // XAMPP的MySQL默認密碼通常為空

        try (Connection conn = DriverManager.getConnection(dbUrl, user, password)) {
            try (Statement stmt = conn.createStatement()) {
                // 刪除所有表格中的數據
                deleteAllData(stmt);

                // 重新插入預設數據
                insertDefaultData(stmt);

                System.out.println("Database has been reset and default data reinserted successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteAllData(Statement stmt) throws Exception {
        stmt.execute("DELETE FROM Deliveries");
        stmt.execute("DELETE FROM WishList");
        stmt.execute("DELETE FROM DamageReports");
        stmt.execute("DELETE FROM Bookings");
        stmt.execute("DELETE FROM Equipment");
        stmt.execute("DELETE FROM Users");
        resetAutoIncrement(stmt); // 重置AUTO_INCREMENT
    }
    
    private static void resetAutoIncrement(Statement stmt) throws Exception {
        stmt.execute("ALTER TABLE Users AUTO_INCREMENT = 1");
        stmt.execute("ALTER TABLE Equipment AUTO_INCREMENT = 1");
        stmt.execute("ALTER TABLE Bookings AUTO_INCREMENT = 1");
        stmt.execute("ALTER TABLE DamageReports AUTO_INCREMENT = 1");
        stmt.execute("ALTER TABLE WishList AUTO_INCREMENT = 1");
        stmt.execute("ALTER TABLE Deliveries AUTO_INCREMENT = 1");
    }

    private static void insertDefaultData(Statement stmt) throws Exception {
        // 插入Users表的預設資料
        String insertUsersData = "INSERT INTO Users (username, password, role, first_name, last_name, email, phone_number) VALUES " +
                "('user1', 'abc17823', 'user', 'John', 'Doe', 'john.doe@example.com', '1234567890')," +
                "('staff1', 'abc17823', 'staff', 'Jane', 'Smith', 'jane.smith@example.com', '9876543210')," +
                "('tech1', 'abc17823', 'technician', 'Tom', 'Taylor', 'tom.taylor@example.com', '1234512345')," +
                "('admin1', 'abc17823', 'admin', 'Alice', 'Anderson', 'alice.anderson@example.com', '9876598765')," +
                "('courier1', 'abc17823', 'courier', 'Charlie', 'Chaplin', 'charlie.chaplin@example.com', '1357913579')," +
                "('user2', 'password', 'user', 'David', 'Williams', 'david.williams@example.com', '2223334444')," +
                "('staff2', 'password', 'staff', 'Laura', 'Johnson', 'laura.johnson@example.com', '3334445555')," +
                "('tech2', 'password', 'technician', 'Robert', 'Brown', 'robert.brown@example.com', '4445556666')," +
                "('admin2', 'password', 'admin', 'Linda', 'Davis', 'linda.davis@example.com', '5556667777')," +
                "('courier2', 'password', 'courier', 'Brian', 'Miller', 'brian.miller@example.com', '6667778888');";
        stmt.execute(insertUsersData);

        // 插入Equipment表的預設資料
        String insertEquipmentData = "INSERT INTO Equipment (name, description, status, location, staff_only) VALUES " +
                "('Laptop 1', 'Dell XPS 15', 'available', 'Chai Wan', FALSE)," +
                "('Projector 1', 'Epson 5040UB', 'available', 'Tsing Yi', FALSE)," +
                "('Laptop 2', 'Apple MacBook Pro', 'available', 'Sha Tin', TRUE)," +
                "('Camera 1', 'Canon EOS 5D', 'maintenance', 'Tuen Mun', TRUE)," +
                "('Microphone 1', 'Shure SM7B', 'available', 'Lee Wai Lee', FALSE)," +
                "('Laptop 3', 'HP Spectre x360', 'available', 'Chai Wan', FALSE)," +
                "('Printer 1', 'HP LaserJet Pro', 'unavailable', 'Tsing Yi', TRUE)," +
                "('Scanner 1', 'Fujitsu ScanSnap iX1500', 'available', 'Sha Tin', FALSE)," +
                "('Laptop 4', 'Lenovo ThinkPad X1 Carbon', 'available', 'Tuen Mun', FALSE)," +
                "('Tablet 1', 'Apple iPad Pro', 'reserved', 'Lee Wai Lee', FALSE)," +
                "('Projector 2', 'BenQ TK850', 'available', 'Chai Wan', FALSE)," +
                "('Camera 2', 'Nikon D850', 'maintenance', 'Sha Tin', TRUE)," +
                "('Microphone 2', 'Audio-Technica AT2020', 'available', 'Tuen Mun', FALSE)," +
                "('Printer 2', 'Brother HL-L2395DW', 'available', 'Tsing Yi', FALSE)," +
                "('Scanner 2', 'Canon CanoScan LiDE400', 'available', 'Lee Wai Lee', FALSE);";
        stmt.execute(insertEquipmentData);

        // 插入Bookings表的預設資料
        String insertBookingsData = "INSERT INTO Bookings (user_id, equipment_id, start_time, end_time, status, delivery_location) VALUES " +
                "(1, 1, '2022-05-10 09:00:00', '2022-05-12 18:00:00', 'pending', 'Sha Tin')," +
                "(2, 2, '2022-06-01 10:00:00', '2022-06-03 20:00:00', 'approved', 'Lee Wai Lee')," +
                "(3, 3, '2022-07-01 08:00:00', '2022-07-03 17:00:00', 'completed', 'Chai Wan')," +
                "(4, 4, '2022-08-01 09:30:00', '2022-08-02 18:30:00', 'denied', 'Tuen Mun')," +
                "(5, 6, '2022-09-01 10:15:00', '2022-09-04 19:00:00', 'approved', 'Tsing Yi')," +
                "(6, 6, '2022-10-01 11:00:00', '2022-10-05 20:00:00', 'completed', 'Sha Tin')," +
                "(7, 7, '2022-11-01 12:00:00', '2022-11-05 21:00:00', 'pending', 'Lee Wai Lee')," +
                "(8, 10, '2022-12-01 13:00:00', '2022-12-06 22:00:00', 'approved', 'Sha Tin')," +
                "(9, 9, '2023-05-01 14:00:00', '2023-05-07 23:00:00', 'completed', 'Tuen Mun')," +
                "(10, 10, '2023-06-01 15:00:00', '2023-06-08 23:59:00', 'denied', 'Tsing Yi');";
        stmt.execute(insertBookingsData);

        // 插入DamageReports表的預設資料
        String insertDamageReportsData = "INSERT INTO DamageReports (equipment_id, reported_by, description, status) VALUES " +
                "(1, 1, 'Minor scratches on the top cover', 'reported')," +
                "(2, 2, 'The lens needs cleaning', 'reviewed')," +
                "(3, 3, 'Broken screen', 'resolved')," +
                "(4, 4, 'Malfunctioning buttons', 'reported')," +
                "(5, 5, 'Loose cable', 'reviewed')," +
                "(6, 6, 'Battery issues', 'resolved')," +
                "(7, 7, 'Overheating', 'reported')," +
                "(8, 8, 'Cracked case', 'reviewed')," +
                "(9, 9, 'Software problems', 'resolved')," +
                "(10, 10, 'Unresponsive touchscreen', 'reported');";
        stmt.execute(insertDamageReportsData);

        // 插入WishList表的預設資料
        String insertWishListData = "INSERT INTO WishList (user_id, equipment_id, added_date) VALUES " +
                "(1, 2, '2023-04-25 15:00:00')," +
                "(2, 1, '2023-04-26 16:00:00')," +
                "(3, 3, '2023-04-27 17:00:00')," +
                "(4, 4, '2023-04-28 18:00:00')," +
                "(5, 5, '2023-04-29 19:00:00')," +
                "(6, 6, '2023-04-30 20:00:00')," +
                "(7, 7, '2023-05-01 21:00:00')," +
                "(8, 8, '2023-05-02 22:00:00')," +
                "(9, 9, '2023-05-03 23:00:00')," +
                "(10, 10, '2023-05-04 23:59:00');";
        stmt.execute(insertWishListData);

        // 插入Deliveries表的預設資料
        String insertDeliveriesData = "INSERT INTO Deliveries (booking_id, courier_id, pickup_location, status, scheduled_time) VALUES " +
                "(1, 1, 'Main Office', 'scheduled', '2022-05-10 08:00:00')," +
                "(2, 2, 'Main Office', 'delivered', '2022-05-10 08:00:00')," +
                "(3, 3, 'Warehouse', 'in_transit', '2022-06-01 09:00:00')," +
                "(4, 4, 'Main Office', 'delivered', '2022-07-01 08:00:00')," +
                "(5, 5, 'Warehouse', 'scheduled', '2022-08-01 09:00:00')," +
                "(6, 6, 'Main Office', 'in_transit', '2022-09-01 10:00:00')," +
                "(7, 7, 'Warehouse', 'delivered', '2022-10-01 11:00:00')," +
                "(8, 8, 'Main Office', 'scheduled', '2022-11-01 12:00:00')," +
                "(9, 9, 'Warehouse', 'in_transit', '2022-12-01 13:00:00')," +
                "(10, 10, 'Main Office', 'delivered', '2023-12-01 14:00:00');";
        stmt.execute(insertDeliveriesData);

        System.out.println("All default data inserted successfully.");
    }
}
