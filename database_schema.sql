-- ====================================================================
-- Online Movie Ticket Booking System - Database Schema
-- Database: MySQL
-- Purpose: Create database structure for the booking system
-- ====================================================================

-- Step 1: Create Database
DROP DATABASE IF EXISTS movie_ticket_db;
CREATE DATABASE movie_ticket_db;
USE movie_ticket_db;

-- Step 2: Create Users Table
-- Purpose: Store user registration information
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Step 3: Create Movies Table
-- Purpose: Store movie information
CREATE TABLE movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_name VARCHAR(200) NOT NULL,
    genre VARCHAR(50),
    duration INT, -- in minutes
    language VARCHAR(50),
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_movie_name (movie_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Step 4: Create Shows Table
-- Purpose: Store theater and show timing information
CREATE TABLE shows (
    show_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT NOT NULL,
    theater_name VARCHAR(200) NOT NULL,
    show_time VARCHAR(20) NOT NULL,
    available_seats INT DEFAULT 100,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id) ON DELETE CASCADE,
    INDEX idx_movie_theater (movie_id, theater_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Step 5: Create Bookings Table
-- Purpose: Store ticket booking information
CREATE TABLE bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    movie_name VARCHAR(200) NOT NULL,
    theater_name VARCHAR(200) NOT NULL,
    show_time VARCHAR(20) NOT NULL,
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    number_of_tickets INT NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) DEFAULT 'CONFIRMED', -- CONFIRMED, CANCELLED
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id) ON DELETE CASCADE,
    INDEX idx_user_bookings (user_id),
    INDEX idx_booking_date (booking_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ====================================================================
-- Step 6: Insert Sample Data
-- ====================================================================

-- Insert Sample Users
INSERT INTO users (username, password, email, phone) VALUES
('admin', 'admin123', 'admin@movieticket.com', '9876543210'),
('john_doe', 'john@123', 'john@example.com', '9876543211'),
('jane_smith', 'jane@123', 'jane@example.com', '9876543212');

-- Insert Sample Movies
INSERT INTO movies (movie_name, genre, duration, language, description, is_active) VALUES
('Avengers: Endgame', 'Action/Sci-Fi', 181, 'English', 'The epic conclusion to the Infinity Saga', TRUE),
('The Dark Knight', 'Action/Thriller', 152, 'English', 'Batman fights against the Joker', TRUE),
('Inception', 'Sci-Fi/Thriller', 148, 'English', 'A thief who steals corporate secrets through dream-sharing', TRUE),
('Interstellar', 'Sci-Fi/Adventure', 169, 'English', 'A team explores space through a wormhole', TRUE),
('The Shawshank Redemption', 'Drama', 142, 'English', 'Two imprisoned men bond over years', TRUE),
('Titanic', 'Romance/Drama', 195, 'English', 'A love story aboard the ill-fated ship', TRUE),
('The Lion King', 'Animation/Adventure', 118, 'English', 'A young lion prince flees his kingdom', TRUE),
('3 Idiots', 'Comedy/Drama', 170, 'Hindi', 'Three friends embark on a quest for a lost buddy', TRUE),
('Dangal', 'Biography/Sport', 161, 'Hindi', 'A father trains his daughters in wrestling', TRUE),
('Baahubali: The Beginning', 'Action/Adventure', 159, 'Hindi/Telugu', 'Epic tale of a warrior prince', TRUE);

-- Insert Sample Theaters and Show Timings
-- For Avengers: Endgame
INSERT INTO shows (movie_id, theater_name, show_time, available_seats) VALUES
(1, 'PVR Cinemas - Phoenix Mall', '10:00 AM', 100),
(1, 'PVR Cinemas - Phoenix Mall', '02:00 PM', 100),
(1, 'PVR Cinemas - Phoenix Mall', '06:00 PM', 100),
(1, 'INOX - Himalaya Mall', '11:00 AM', 100),
(1, 'INOX - Himalaya Mall', '03:00 PM', 100),
(1, 'INOX - Himalaya Mall', '07:00 PM', 100);

-- For The Dark Knight
INSERT INTO shows (movie_id, theater_name, show_time, available_seats) VALUES
(2, 'PVR Cinemas - Phoenix Mall', '10:30 AM', 100),
(2, 'PVR Cinemas - Phoenix Mall', '02:30 PM', 100),
(2, 'PVR Cinemas - Phoenix Mall', '06:30 PM', 100),
(2, 'Cinepolis - Ahmedabad One', '11:30 AM', 100),
(2, 'Cinepolis - Ahmedabad One', '03:30 PM', 100);

-- For Inception
INSERT INTO shows (movie_id, theater_name, show_time, available_seats) VALUES
(3, 'INOX - Himalaya Mall', '09:00 AM', 100),
(3, 'INOX - Himalaya Mall', '01:00 PM', 100),
(3, 'INOX - Himalaya Mall', '05:00 PM', 100),
(3, 'Cinepolis - Ahmedabad One', '10:00 AM', 100),
(3, 'Cinepolis - Ahmedabad One', '02:00 PM', 100);

-- For Interstellar
INSERT INTO shows (movie_id, theater_name, show_time, available_seats) VALUES
(4, 'PVR Cinemas - Phoenix Mall', '09:30 AM', 100),
(4, 'PVR Cinemas - Phoenix Mall', '01:30 PM', 100),
(4, 'INOX - Himalaya Mall', '10:30 AM', 100),
(4, 'INOX - Himalaya Mall', '02:30 PM', 100);

-- For The Shawshank Redemption
INSERT INTO shows (movie_id, theater_name, show_time, available_seats) VALUES
(5, 'Cinepolis - Ahmedabad One', '09:00 AM', 100),
(5, 'Cinepolis - Ahmedabad One', '01:00 PM', 100),
(5, 'Cinepolis - Ahmedabad One', '05:00 PM', 100);

-- For Titanic
INSERT INTO shows (movie_id, theater_name, show_time, available_seats) VALUES
(6, 'PVR Cinemas - Phoenix Mall', '12:00 PM', 100),
(6, 'PVR Cinemas - Phoenix Mall', '04:00 PM', 100),
(6, 'INOX - Himalaya Mall', '01:00 PM', 100);

-- For The Lion King
INSERT INTO shows (movie_id, theater_name, show_time, available_seats) VALUES
(7, 'PVR Cinemas - Phoenix Mall', '11:00 AM', 100),
(7, 'PVR Cinemas - Phoenix Mall', '03:00 PM', 100),
(7, 'Cinepolis - Ahmedabad One', '12:00 PM', 100);

-- For 3 Idiots
INSERT INTO shows (movie_id, theater_name, show_time, available_seats) VALUES
(8, 'INOX - Himalaya Mall', '12:00 PM', 100),
(8, 'INOX - Himalaya Mall', '04:00 PM', 100),
(8, 'PVR Cinemas - Phoenix Mall', '01:00 PM', 100);

-- For Dangal
INSERT INTO shows (movie_id, theater_name, show_time, available_seats) VALUES
(9, 'Cinepolis - Ahmedabad One', '11:00 AM', 100),
(9, 'Cinepolis - Ahmedabad One', '03:00 PM', 100),
(9, 'INOX - Himalaya Mall', '12:30 PM', 100);

-- For Baahubali: The Beginning
INSERT INTO shows (movie_id, theater_name, show_time, available_seats) VALUES
(10, 'PVR Cinemas - Phoenix Mall', '10:00 AM', 100),
(10, 'PVR Cinemas - Phoenix Mall', '02:00 PM', 100),
(10, 'INOX - Himalaya Mall', '11:00 AM', 100),
(10, 'Cinepolis - Ahmedabad One', '12:00 PM', 100);

-- Insert Sample Bookings
INSERT INTO bookings (user_id, movie_id, movie_name, theater_name, show_time, number_of_tickets, total_amount, status) VALUES
(2, 1, 'Avengers: Endgame', 'PVR Cinemas - Phoenix Mall', '06:00 PM', 2, 500.00, 'CONFIRMED'),
(3, 3, 'Inception', 'INOX - Himalaya Mall', '05:00 PM', 3, 750.00, 'CONFIRMED'),
(2, 8, '3 Idiots', 'INOX - Himalaya Mall', '04:00 PM', 4, 1000.00, 'CANCELLED');

-- ====================================================================
-- Step 7: Create Views for Quick Queries
-- ====================================================================

-- View: Active Movies with Show Count
CREATE VIEW v_active_movies AS
SELECT 
    m.movie_id,
    m.movie_name,
    m.genre,
    m.language,
    COUNT(s.show_id) as total_shows
FROM movies m
LEFT JOIN shows s ON m.movie_id = s.movie_id
WHERE m.is_active = TRUE
GROUP BY m.movie_id;

-- View: User Booking Summary
CREATE VIEW v_user_booking_summary AS
SELECT 
    u.user_id,
    u.username,
    COUNT(b.booking_id) as total_bookings,
    SUM(CASE WHEN b.status = 'CONFIRMED' THEN b.total_amount ELSE 0 END) as total_spent
FROM users u
LEFT JOIN bookings b ON u.user_id = b.user_id
GROUP BY u.user_id;

-- ====================================================================
-- Step 8: Display Table Information
-- ====================================================================

SELECT 'Database Created Successfully!' as Status;
SELECT COUNT(*) as Total_Users FROM users;
SELECT COUNT(*) as Total_Movies FROM movies;
SELECT COUNT(*) as Total_Shows FROM shows;
SELECT COUNT(*) as Total_Bookings FROM bookings;