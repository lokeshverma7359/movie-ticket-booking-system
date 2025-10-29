/**
 * Payment Frame - Card Selection and Payment Module
 * Handles payment processing and card details
 * 
 * Purpose: Select payment method, enter card details, process payment
 * Features: Card type selection, card validation, generate booking
 */

package com.movieticket.ui;

import com.movieticket.dao.BookingDAO;
import com.movieticket.model.Booking;
import com.movieticket.model.Movie;
import com.movieticket.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PaymentFrame extends JFrame {
    
    private User currentUser;
    private Movie selectedMovie;
    private String selectedTheater;
    private String selectedShowTime;
    private int numberOfTickets;
    private double totalAmount;
    private MovieSelectionFrame parentFrame;
    private BookingDAO bookingDAO;
    
    // Components
    private JComboBox<String> cmbCardType;
    private JTextField txtCardNumber;
    private JTextField txtCardHolder;
    private JComboBox<String> cmbExpiryMonth;
    private JComboBox<String> cmbExpiryYear;
    private JTextField txtCVV;
    private JButton btnPay;
    private JButton btnCancel;
    
    public PaymentFrame(User user, Movie movie, String theater, String showTime,
                       int tickets, double amount, MovieSelectionFrame parent) {
        this.currentUser = user;
        this.selectedMovie = movie;
        this.selectedTheater = theater;
        this.selectedShowTime = showTime;
        this.numberOfTickets = tickets;
        this.totalAmount = amount;
        this.parentFrame = parent;
        this.bookingDAO = new BookingDAO();
        
        // Step 1: Setup Frame
        setTitle("Payment - Card Details");
        setSize(550, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        // Step 2: Initialize Components
        initComponents();
        
        // Step 3: Add Event Listeners
        addEventListeners();
    }
    
    /**
     * Initialize all UI components
     */
    private void initComponents() {
        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Title
        JLabel lblTitle = new JLabel("Payment Details");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(new Color(25, 25, 112));
        
        // Booking Summary Panel
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Booking Summary"));
        summaryPanel.setBackground(Color.WHITE);
        
        summaryPanel.add(new JLabel("Movie: " + selectedMovie.getMovieName()));
        summaryPanel.add(new JLabel("Theater: " + selectedTheater));
        summaryPanel.add(new JLabel("Show Time: " + selectedShowTime));
        summaryPanel.add(new JLabel("Number of Tickets: " + numberOfTickets));
        JLabel lblAmount = new JLabel("Total Amount: ₹" + totalAmount);
        lblAmount.setFont(new Font("Arial", Font.BOLD, 16));
        lblAmount.setForeground(new Color(0, 128, 0));
        summaryPanel.add(lblAmount);
        
        // Card Type Panel
        JPanel cardTypePanel = createLabelFieldPanel("Card Type:", 
            cmbCardType = new JComboBox<>(new String[]{"Credit Card", "Debit Card"}));
        cmbCardType.setPreferredSize(new Dimension(300, 30));
        
        // Card Number Panel
        JPanel cardNumberPanel = createLabelFieldPanel("Card Number:", 
            txtCardNumber = new JTextField(16));
        txtCardNumber.setPreferredSize(new Dimension(300, 30));
        
        // Card Holder Panel
        JPanel cardHolderPanel = createLabelFieldPanel("Card Holder Name:", 
            txtCardHolder = new JTextField(20));
        txtCardHolder.setPreferredSize(new Dimension(300, 30));
        
        // Expiry Date Panel
        JPanel expiryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        expiryPanel.setBackground(new Color(240, 248, 255));
        JLabel lblExpiry = new JLabel("Expiry Date:");
        lblExpiry.setPreferredSize(new Dimension(130, 25));
        
        String[] months = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
        cmbExpiryMonth = new JComboBox<>(months);
        cmbExpiryMonth.setPreferredSize(new Dimension(60, 30));
        
        String[] years = new String[10];
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        for (int i = 0; i < 10; i++) {
            years[i] = String.valueOf(currentYear + i);
        }
        cmbExpiryYear = new JComboBox<>(years);
        cmbExpiryYear.setPreferredSize(new Dimension(80, 30));
        
        expiryPanel.add(lblExpiry);
        expiryPanel.add(cmbExpiryMonth);
        expiryPanel.add(new JLabel(" / "));
        expiryPanel.add(cmbExpiryYear);
        
        // CVV Panel
        JPanel cvvPanel = createLabelFieldPanel("CVV:", 
            txtCVV = new JTextField(3));
        txtCVV.setPreferredSize(new Dimension(80, 30));
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        btnPay = new JButton("Pay ₹" + totalAmount);
        btnPay.setPreferredSize(new Dimension(180, 40));
        btnPay.setBackground(new Color(34, 139, 34));
        btnPay.setForeground(Color.WHITE);
        btnPay.setFont(new Font("Arial", Font.BOLD, 14));
        btnPay.setFocusPainted(false);
        
        btnCancel = new JButton("Cancel");
        btnCancel.setPreferredSize(new Dimension(120, 40));
        btnCancel.setBackground(new Color(220, 20, 60));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        
        buttonPanel.add(btnPay);
        buttonPanel.add(btnCancel);
        
        // Add all components
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(summaryPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(cardTypePanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(cardNumberPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(cardHolderPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(expiryPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(cvvPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);
        
        add(mainPanel);
    }
    
    /**
     * Helper method to create label-field panel
     */
    private JPanel createLabelFieldPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(new Color(240, 248, 255));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(130, 25));
        panel.add(label);
        panel.add(field);
        return panel;
    }
    
    /**
     * Add event listeners
     */
    private void addEventListeners() {
        btnPay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processPayment();
            }
        });
        
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    /**
     * Step 1: Validate card details
     * Step 2: Create booking object
     * Step 3: Save booking to database
     * Step 4: Generate and display ticket
     */
    private void processPayment() {
        // Step 1: Validate card details
        if (!validateCardDetails()) {
            return;
        }
        
        // Show processing dialog
        JDialog processingDialog = new JDialog(this, "Processing Payment", true);
        JLabel lblProcessing = new JLabel("Processing your payment...", SwingConstants.CENTER);
        lblProcessing.setFont(new Font("Arial", Font.PLAIN, 14));
        processingDialog.add(lblProcessing);
        processingDialog.setSize(300, 100);
        processingDialog.setLocationRelativeTo(this);
        
        // Simulate payment processing in background
        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                Thread.sleep(2000); // Simulate processing time
                
                // Step 2: Create booking object
                Booking booking = new Booking(
                    currentUser.getUserId(),
                    selectedMovie.getMovieId(),
                    selectedMovie.getMovieName(),
                    selectedTheater,
                    selectedShowTime,
                    numberOfTickets,
                    totalAmount
                );
                
                // Step 3: Save booking to database
                int bookingId = bookingDAO.createBooking(booking);
                booking.setBookingId(bookingId);
                
                return bookingId > 0;
            }
            
            @Override
            protected void done() {
                processingDialog.dispose();
                try {
                    boolean success = get();
                    if (success) {
                        // Payment successful
                        JOptionPane.showMessageDialog(PaymentFrame.this,
                            "Payment Successful!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                        
                        // Step 4: Generate ticket
                        Booking booking = new Booking(
                            currentUser.getUserId(),
                            selectedMovie.getMovieId(),
                            selectedMovie.getMovieName(),
                            selectedTheater,
                            selectedShowTime,
                            numberOfTickets,
                            totalAmount
                        );
                        
                        new TicketFrame(booking, currentUser).setVisible(true);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(PaymentFrame.this,
                            "Payment failed! Please try again.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        
        worker.execute();
        processingDialog.setVisible(true);
    }
    
    /**
     * Validate card details
     */
    private boolean validateCardDetails() {
        String cardNumber = txtCardNumber.getText().trim();
        String cardHolder = txtCardHolder.getText().trim();
        String cvv = txtCVV.getText().trim();
        
        if (cardNumber.isEmpty() || cardHolder.isEmpty() || cvv.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please fill all card details!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (cardNumber.length() != 16 || !cardNumber.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid 16-digit card number!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (cvv.length() != 3 || !cvv.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid 3-digit CVV!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
}