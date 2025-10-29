/**
 * Booking Data Access Object
 * File: BookingDAO.java
 * Location: src/com/movieticket/dao/BookingDAO.java
 * 
 * Purpose: Handle all database operations related to bookings
 * Methods: Create booking, Get user bookings, Cancel booking, Get booking by ID
 */

package com.movieticket.dao;

import com.movieticket.model.Booking;
import com.movieticket.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    
    /**
     * Create a new booking
     * @param booking Booking object
     * @return booking ID if successful, 0 otherwise
     */
    public int createBooking(Booking booking) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO bookings (user_id, movie_id, movie_name, theater_name, " +
                        "show_time, number_of_tickets, total_amount, status) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, booking.getUserId());
            pstmt.setInt(2, booking.getMovieId());
            pstmt.setString(3, booking.getMovieName());
            pstmt.setString(4, booking.getTheaterName());
            pstmt.setString(5, booking.getShowTime());
            pstmt.setInt(6, booking.getNumberOfTickets());
            pstmt.setDouble(7, booking.getTotalAmount());
            pstmt.setString(8, booking.getStatus());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Return generated booking_id
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }
        
        return 0;
    }
    
    /**
     * Get all bookings for a specific user
     * @param userId User ID
     * @return List of Booking objects
     */
    public List<Booking> getUserBookings(int userId) {
        List<Booking> bookings = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM bookings WHERE user_id = ? ORDER BY booking_date DESC";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Booking booking = new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("user_id"),
                    rs.getInt("movie_id"),
                    rs.getString("movie_name"),
                    rs.getString("theater_name"),
                    rs.getString("show_time"),
                    rs.getTimestamp("booking_date"),
                    rs.getInt("number_of_tickets"),
                    rs.getDouble("total_amount"),
                    rs.getString("status")
                );
                bookings.add(booking);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }
        
        return bookings;
    }
    
    /**
     * Get booking by ID
     * @param bookingId Booking ID
     * @return Booking object or null
     */
    public Booking getBookingById(int bookingId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM bookings WHERE booking_id = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookingId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new Booking(
                    rs.getInt("booking_id"),
                    rs.getInt("user_id"),
                    rs.getInt("movie_id"),
                    rs.getString("movie_name"),
                    rs.getString("theater_name"),
                    rs.getString("show_time"),
                    rs.getTimestamp("booking_date"),
                    rs.getInt("number_of_tickets"),
                    rs.getDouble("total_amount"),
                    rs.getString("status")
                );
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }
        
        return null;
    }
    
    /**
     * Cancel a booking
     * @param bookingId Booking ID to cancel
     * @return true if cancellation successful, false otherwise
     */
    public boolean cancelBooking(int bookingId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "UPDATE bookings SET status = 'CANCELLED' WHERE booking_id = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookingId);
            
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(conn, pstmt, null);
        }
    }
    
    /**
     * Get total bookings count for a user
     * @param userId User ID
     * @return Total number of bookings
     */
    public int getTotalBookingsCount(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT COUNT(*) FROM bookings WHERE user_id = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }
        
        return 0;
    }
    
    /**
     * Get total amount spent by user
     * @param userId User ID
     * @return Total amount spent
     */
    public double getTotalAmountSpent(int userId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT SUM(total_amount) FROM bookings " +
                        "WHERE user_id = ? AND status = 'CONFIRMED'";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble(1);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }
        
        return 0.0;
    }
    
    /**
     * Check if booking exists and is confirmed
     * @param bookingId Booking ID
     * @return true if exists and confirmed
     */
    public boolean isBookingConfirmed(int bookingId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseConnection.getConnection();
            String sql = "SELECT status FROM bookings WHERE booking_id = ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bookingId);
            
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return "CONFIRMED".equals(rs.getString("status"));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, pstmt, rs);
        }
        
        return false;
    }
    
    /**
     * Close database resources
     */
    private void closeResources(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}