/**
 * Ticket Frame - Display Booking Ticket
 * File: TicketFrame.java
 * Location: src/com/movieticket/ui/TicketFrame.java
 * 
 * Purpose: Display ticket details after successful booking
 * Features: Show booking details, Generate ticket ID, Print option
 */

package com.movieticket.ui;

import com.movieticket.model.Booking;
import com.movieticket.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;

public class TicketFrame extends JFrame {
    
    private Booking booking;
    private User user;
    private JButton btnClose;
    private JButton btnPrint;
    
    public TicketFrame(Booking booking, User user) {
        this.booking = booking;
        this.user = user;
        
        // Step 1: Setup Frame
        setTitle("E-Ticket - Booking Confirmed");
        setSize(600, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Step 2: Initialize Components
        initComponents();
    }
    
    /**
     * Initialize all UI components
     */
    private void initComponents() {
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        mainPanel.setBackground(Color.WHITE);
        
        // Header Panel (Success Message)
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(34, 139, 34));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblSuccess = new JLabel("✓ BOOKING CONFIRMED");
        lblSuccess.setFont(new Font("Arial", Font.BOLD, 28));
        lblSuccess.setForeground(Color.WHITE);
        lblSuccess.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel lblMessage = new JLabel("Your ticket has been booked successfully!");
        lblMessage.setFont(new Font("Arial", Font.PLAIN, 14));
        lblMessage.setForeground(Color.WHITE);
        lblMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerPanel.add(lblSuccess);
        headerPanel.add(Box.createVerticalStrut(10));
        headerPanel.add(lblMessage);
        
        // Ticket Details Panel
        JPanel ticketPanel = new JPanel();
        ticketPanel.setLayout(new BoxLayout(ticketPanel, BoxLayout.Y_AXIS));
        ticketPanel.setBackground(Color.WHITE);
        ticketPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 130, 180), 3),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Booking ID
        JLabel lblBookingId = new JLabel("BOOKING ID: BKG" + 
            String.format("%06d", booking.getBookingId()));
        lblBookingId.setFont(new Font("Arial", Font.BOLD, 20));
        lblBookingId.setForeground(new Color(220, 20, 60));
        lblBookingId.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        ticketPanel.add(lblBookingId);
        ticketPanel.add(Box.createVerticalStrut(20));
        
        // Separator
        JSeparator separator1 = new JSeparator();
        separator1.setMaximumSize(new Dimension(500, 1));
        ticketPanel.add(separator1);
        ticketPanel.add(Box.createVerticalStrut(20));
        
        // Movie Details
        ticketPanel.add(createDetailRow("Movie:", booking.getMovieName()));
        ticketPanel.add(Box.createVerticalStrut(10));
        ticketPanel.add(createDetailRow("Theater:", booking.getTheaterName()));
        ticketPanel.add(Box.createVerticalStrut(10));
        ticketPanel.add(createDetailRow("Show Time:", booking.getShowTime()));
        ticketPanel.add(Box.createVerticalStrut(10));
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
        ticketPanel.add(createDetailRow("Booking Date:", 
            dateFormat.format(booking.getBookingDate())));
        ticketPanel.add(Box.createVerticalStrut(20));
        
        // Separator
        JSeparator separator2 = new JSeparator();
        separator2.setMaximumSize(new Dimension(500, 1));
        ticketPanel.add(separator2);
        ticketPanel.add(Box.createVerticalStrut(20));
        
        // Customer Details
        ticketPanel.add(createDetailRow("Customer Name:", user.getUsername()));
        ticketPanel.add(Box.createVerticalStrut(10));
        ticketPanel.add(createDetailRow("Email:", user.getEmail()));
        ticketPanel.add(Box.createVerticalStrut(10));
        ticketPanel.add(createDetailRow("Phone:", user.getPhone()));
        ticketPanel.add(Box.createVerticalStrut(20));
        
        // Separator
        JSeparator separator3 = new JSeparator();
        separator3.setMaximumSize(new Dimension(500, 1));
        ticketPanel.add(separator3);
        ticketPanel.add(Box.createVerticalStrut(20));
        
        // Payment Details
        ticketPanel.add(createDetailRow("Number of Tickets:", 
            String.valueOf(booking.getNumberOfTickets())));
        ticketPanel.add(Box.createVerticalStrut(10));
        
        JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        amountPanel.setBackground(Color.WHITE);
        JLabel lblAmount = new JLabel("Total Amount Paid: ₹" + booking.getTotalAmount());
        lblAmount.setFont(new Font("Arial", Font.BOLD, 18));
        lblAmount.setForeground(new Color(0, 128, 0));
        amountPanel.add(lblAmount);
        
        ticketPanel.add(amountPanel);
        ticketPanel.add(Box.createVerticalStrut(20));
        
        // Status
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        statusPanel.setBackground(Color.WHITE);
        JLabel lblStatus = new JLabel("Status: " + booking.getStatus());
        lblStatus.setFont(new Font("Arial", Font.BOLD, 16));
        lblStatus.setForeground(new Color(34, 139, 34));
        statusPanel.add(lblStatus);
        
        ticketPanel.add(statusPanel);
        
        // Instructions Panel
        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setLayout(new BoxLayout(instructionsPanel, BoxLayout.Y_AXIS));
        instructionsPanel.setBackground(new Color(255, 255, 224));
        instructionsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 215, 0), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblInstructions = new JLabel("Important Instructions:");
        lblInstructions.setFont(new Font("Arial", Font.BOLD, 14));
        lblInstructions.setForeground(new Color(184, 134, 11));
        
        JLabel lblInstruct1 = new JLabel("• Please carry a valid ID proof to the theater");
        lblInstruct1.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblInstruct2 = new JLabel("• Reach the theater 15 minutes before show time");
        lblInstruct2.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblInstruct3 = new JLabel("• Show this ticket at the entrance");
        lblInstruct3.setFont(new Font("Arial", Font.PLAIN, 12));
        
        JLabel lblInstruct4 = new JLabel("• Cancellation allowed up to 2 hours before show");
        lblInstruct4.setFont(new Font("Arial", Font.PLAIN, 12));
        
        instructionsPanel.add(lblInstructions);
        instructionsPanel.add(Box.createVerticalStrut(5));
        instructionsPanel.add(lblInstruct1);
        instructionsPanel.add(lblInstruct2);
        instructionsPanel.add(lblInstruct3);
        instructionsPanel.add(lblInstruct4);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        btnPrint = new JButton("Print Ticket");
        btnPrint.setPreferredSize(new Dimension(140, 40));
        btnPrint.setBackground(new Color(70, 130, 180));
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setFocusPainted(false);
        
        btnClose = new JButton("Close");
        btnClose.setPreferredSize(new Dimension(100, 40));
        btnClose.setBackground(new Color(128, 128, 128));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFocusPainted(false);
        
        buttonPanel.add(btnPrint);
        buttonPanel.add(btnClose);
        
        // Add event listeners
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printTicket();
            }
        });
        
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Add all components to main panel
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(ticketPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(instructionsPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(buttonPanel);
        
        add(mainPanel);
    }
    
    /**
     * Helper method to create detail row
     */
    private JPanel createDetailRow(String label, String value) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setBackground(Color.WHITE);
        panel.setMaximumSize(new Dimension(500, 30));
        
        JLabel lblLabel = new JLabel(label);
        lblLabel.setFont(new Font("Arial", Font.BOLD, 14));
        lblLabel.setForeground(new Color(70, 130, 180));
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Arial", Font.PLAIN, 14));
        
        panel.add(lblLabel, BorderLayout.WEST);
        panel.add(lblValue, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Print ticket (simulated)
     */
    private void printTicket() {
        JOptionPane.showMessageDialog(this,
            "Printing ticket...\n\nBooking ID: BKG" + 
            String.format("%06d", booking.getBookingId()) + 
            "\nMovie: " + booking.getMovieName() +
            "\n\nTicket will be sent to your email: " + user.getEmail(),
            "Print Ticket",
            JOptionPane.INFORMATION_MESSAGE);
    }
}