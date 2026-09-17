package com.fashionstore.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/fashion_store?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";       // Change if your MySQL username is different
    private static final String PASSWORD = "root6263"; // Put your actual MySQL password here

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Explicitly load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found in project build path!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection failed! Check your URL, username, or password.");
            e.printStackTrace();
        }
        return conn;
    }
}