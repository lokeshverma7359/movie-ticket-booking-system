/**
 * Online Movie Ticket Booking System
 * Main Entry Point
 * 
 * This is the main class that starts the application
 * Author: Your Name
 * Date: October 2025
 */

package com.movieticket.main;

import com.movieticket.ui.LoginFrame;
import javax.swing.SwingUtilities;

public class MovieTicketBookingSystem {
    
    public static void main(String[] args) {
        // Run the application on Event Dispatch Thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Step 1: Start with Login Frame
                new LoginFrame().setVisible(true);
            }
        });
    }
}