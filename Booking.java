/**
 * Booking Model Class
 * File: Booking.java
 * Location: src/com/movieticket/model/Booking.java
 * 
 * Purpose: Represent booking/ticket data
 */

package com.movieticket.model;

import java.sql.Timestamp;
import java.util.Date;

public class Booking {
    
    private int bookingId;
    private int userId;
    private int movieId;
    private String movieName;
    private String theaterName;
    private String showTime;
    private Date bookingDate;
    private int numberOfTickets;
    private double totalAmount;
    private String status; // CONFIRMED, CANCELLED
    
    // Constructor for new booking (without bookingId and bookingDate)
    public Booking(int userId, int movieId, String movieName, String theaterName,
                   String showTime, int numberOfTickets, double totalAmount) {
        this.userId = userId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.theaterName = theaterName;
        this.showTime = showTime;
        this.numberOfTickets = numberOfTickets;
        this.totalAmount = totalAmount;
        this.status = "CONFIRMED";
        this.bookingDate = new Date();
    }
    
    // Constructor with bookingId
    public Booking(int bookingId, int userId, int movieId, String movieName, 
                   String theaterName, String showTime, int numberOfTickets, 
                   double totalAmount) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.theaterName = theaterName;
        this.showTime = showTime;
        this.numberOfTickets = numberOfTickets;
        this.totalAmount = totalAmount;
        this.status = "CONFIRMED";
        this.bookingDate = new Date();
    }
    
    // Full constructor (for retrieving from database)
    public Booking(int bookingId, int userId, int movieId, String movieName, 
                   String theaterName, String showTime, Date bookingDate,
                   int numberOfTickets, double totalAmount, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.theaterName = theaterName;
        this.showTime = showTime;
        this.bookingDate = bookingDate;
        this.numberOfTickets = numberOfTickets;
        this.totalAmount = totalAmount;
        this.status = status;
    }
    
    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }
    
    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
    
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public int getMovieId() {
        return movieId;
    }
    
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    
    public String getMovieName() {
        return movieName;
    }
    
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    
    public String getTheaterName() {
        return theaterName;
    }
    
    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }
    
    public String getShowTime() {
        return showTime;
    }
    
    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }
    
    public Date getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    public int getNumberOfTickets() {
        return numberOfTickets;
    }
    
    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", movieName='" + movieName + '\'' +
                ", theaterName='" + theaterName + '\'' +
                ", showTime='" + showTime + '\'' +
                ", numberOfTickets=" + numberOfTickets +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                '}';
    }
}