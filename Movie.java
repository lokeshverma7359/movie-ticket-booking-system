/**
 * Movie Model Class
 * File: Movie.java
 * Location: src/com/movieticket/model/Movie.java
 * 
 * Purpose: Represent movie data
 */

package com.movieticket.model;

import java.sql.Timestamp;

public class Movie {
    
    private int movieId;
    private String movieName;
    private String genre;
    private int duration; // in minutes
    private String language;
    private String description;
    private boolean isActive;
    private Timestamp createdAt;
    
    // Constructor with essential fields
    public Movie(int movieId, String movieName, String genre, String language) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.genre = genre;
        this.language = language;
        this.isActive = true;
    }
    
    // Constructor with all fields
    public Movie(int movieId, String movieName, String genre, int duration, 
                 String language, String description, boolean isActive) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.genre = genre;
        this.duration = duration;
        this.language = language;
        this.description = description;
        this.isActive = isActive;
    }
    
    // Full constructor
    public Movie(int movieId, String movieName, String genre, int duration, 
                 String language, String description, boolean isActive, Timestamp createdAt) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.genre = genre;
        this.duration = duration;
        this.language = language;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
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
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public void setDuration(int duration) {
        this.duration = duration;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public void setLanguage(String language) {
        this.language = language;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    // Override toString for JComboBox display
    @Override
    public String toString() {
        return movieName + " (" + language + ")";
    }
    
    // Method to get full details
    public String getFullDetails() {
        return "Movie{" +
                "movieId=" + movieId +
                ", movieName='" + movieName + '\'' +
                ", genre='" + genre + '\'' +
                ", duration=" + duration + " mins" +
                ", language='" + language + '\'' +
                '}';
    }
}