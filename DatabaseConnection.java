/**
 * Database Connection Utility
 * File: DatabaseConnection.java
 * Location: src/com/movieticket/util/DatabaseConnection.java
 * 
 * Purpose: Provide database connection to all DAO classes
 */

package com.movieticket.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    
    // Database credentials - CHANGE THESE ACCORDING TO YOUR MYSQL SETUP
    private static final String URL = "jdbc:mysql://localhost:3306/movie_ticket_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root"; // Change this to your MySQL password
    
    /**
     * Get database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Return connection
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found! Add mysql-connector-java.jar to classpath", e);
        }
    }
    
    /**
     * Close database connection
     * @param connection Connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Test database connection
     * @return true if connection successful
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            boolean isValid = conn.isValid(2);
            closeConnection(conn);
            return isValid;
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
            return false;
        }
    }
}