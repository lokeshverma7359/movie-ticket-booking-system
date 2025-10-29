/**
 * Movie Selection Frame - Select Movie, Theater, Show Time, Tickets
 * Main booking interface after login
 * 
 * Purpose: Display available movies and allow user to make selections
 * Features: Select movie, theater, show time, number of tickets
 */

package com.movieticket.ui;

import com.movieticket.dao.MovieDAO;
import com.movieticket.model.Movie;
import com.movieticket.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class MovieSelectionFrame extends JFrame {
    
    private User currentUser;
    private MovieDAO movieDAO;
    
    // Components
    private JComboBox<Movie> cmbMovies;
    private JComboBox<String> cmbTheaters;
    private JComboBox<String> cmbShowTimes;
    private JSpinner spnTickets;
    private JLabel lblTicketPrice;
    private JLabel lblTotalAmount;
    private JButton btnProceed;
    private JButton btnViewBookings;
    private JButton btnLogout;
    
    private final double TICKET_PRICE = 250.0; // Price per ticket
    
    public MovieSelectionFrame(User user) {
        this.currentUser = user;
        this.movieDAO = new MovieDAO();
        
        // Step 1: Setup Frame
        setTitle("Movie Ticket Booking - Select Movie");
        setSize(600, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Step 2: Initialize Components
        initComponents();
        
        // Step 3: Load Movies
        loadMovies();
        
        // Step 4: Add Event Listeners
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
        
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(240, 248, 255));
        JLabel lblTitle = new JLabel("Book Your Movie Tickets");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(new Color(25, 25, 112));
        JLabel lblUser = new JLabel("Welcome, " + currentUser.getUsername() + "!");
        lblUser.setFont(new Font("Arial", Font.PLAIN, 14));
        headerPanel.add(lblTitle, BorderLayout.WEST);
        headerPanel.add(lblUser, BorderLayout.EAST);
        
        // Movie Selection Panel
        JPanel moviePanel = createLabelFieldPanel("Select Movie:", 
            cmbMovies = new JComboBox<>());
        cmbMovies.setPreferredSize(new Dimension(350, 30));
        
        // Theater Selection Panel
        JPanel theaterPanel = createLabelFieldPanel("Select Theater:", 
            cmbTheaters = new JComboBox<>());
        cmbTheaters.setPreferredSize(new Dimension(350, 30));
        cmbTheaters.setEnabled(false);
        
        // Show Time Selection Panel
        JPanel showTimePanel = createLabelFieldPanel("Select Show Time:", 
            cmbShowTimes = new JComboBox<>());
        cmbShowTimes.setPreferredSize(new Dimension(350, 30));
        cmbShowTimes.setEnabled(false);
        
        // Number of Tickets Panel
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(1, 1, 10, 1);
        spnTickets = new JSpinner(spinnerModel);
        spnTickets.setPreferredSize(new Dimension(100, 30));
        JPanel ticketsPanel = createLabelFieldPanel("Number of Tickets:", spnTickets);
        
        // Price Information Panel
        JPanel pricePanel = new JPanel(new GridLayout(2, 1, 5, 5));
        pricePanel.setBackground(new Color(240, 248, 255));
        lblTicketPrice = new JLabel("Ticket Price: ₹" + TICKET_PRICE);
        lblTicketPrice.setFont(new Font("Arial", Font.PLAIN, 14));
        lblTotalAmount = new JLabel("Total Amount: ₹" + TICKET_PRICE);
        lblTotalAmount.setFont(new Font("Arial", Font.BOLD, 16));
        lblTotalAmount.setForeground(new Color(0, 128, 0));
        pricePanel.add(lblTicketPrice);
        pricePanel.add(lblTotalAmount);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        btnProceed = new JButton("Proceed to Payment");
        btnProceed.setPreferredSize(new Dimension(180, 40));
        btnProceed.setBackground(new Color(34, 139, 34));
        btnProceed.setForeground(Color.WHITE);
        btnProceed.setFont(new Font("Arial", Font.BOLD, 14));
        btnProceed.setFocusPainted(false);
        btnProceed.setEnabled(false);
        
        btnViewBookings = new JButton("My Bookings");
        btnViewBookings.setPreferredSize(new Dimension(140, 40));
        btnViewBookings.setBackground(new Color(70, 130, 180));
        btnViewBookings.setForeground(Color.WHITE);
        btnViewBookings.setFocusPainted(false);
        
        btnLogout = new JButton("Logout");
        btnLogout.setPreferredSize(new Dimension(100, 40));
        btnLogout.setBackground(new Color(220, 20, 60));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        
        buttonPanel.add(btnProceed);
        buttonPanel.add(btnViewBookings);
        buttonPanel.add(btnLogout);
        
        // Add all components to main panel
        mainPanel.add(headerPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(moviePanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(theaterPanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(showTimePanel);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(ticketsPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(pricePanel);
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
        label.setPreferredSize(new Dimension(150, 25));
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(label);
        panel.add(field);
        return panel;
    }
    
    /**
     * Step 1: Get all movies from database
     * Step 2: Populate movie combo box
     */
    private void loadMovies() {
        List<Movie> movies = movieDAO.getAllMovies();
        
        cmbMovies.removeAllItems();
        cmbMovies.addItem(null); // Add empty option
        
        for (Movie movie : movies) {
            cmbMovies.addItem(movie);
        }
    }
    
    /**
     * Add event listeners to components
     */
    private void addEventListeners() {
        // Movie selection change
        cmbMovies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Movie selectedMovie = (Movie) cmbMovies.getSelectedItem();
                if (selectedMovie != null) {
                    loadTheaters(selectedMovie.getMovieId());
                    cmbTheaters.setEnabled(true);
                } else {
                    cmbTheaters.removeAllItems();
                    cmbTheaters.setEnabled(false);
                    cmbShowTimes.removeAllItems();
                    cmbShowTimes.setEnabled(false);
                    btnProceed.setEnabled(false);
                }
            }
        });
        
        // Theater selection change
        cmbTheaters.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Movie selectedMovie = (Movie) cmbMovies.getSelectedItem();
                String selectedTheater = (String) cmbTheaters.getSelectedItem();
                if (selectedMovie != null && selectedTheater != null) {
                    loadShowTimes(selectedMovie.getMovieId(), selectedTheater);
                    cmbShowTimes.setEnabled(true);
                }
            }
        });
        
        // Show time selection change
        cmbShowTimes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cmbShowTimes.getSelectedItem() != null) {
                    btnProceed.setEnabled(true);
                }
            }
        });
        
        // Ticket count change
        spnTickets.addChangeListener(e -> updateTotalAmount());
        
        // Proceed button
        btnProceed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                proceedToPayment();
            }
        });
        
        // View bookings button
        btnViewBookings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BookingHistoryFrame(currentUser, MovieSelectionFrame.this).setVisible(true);
            }
        });
        
        // Logout button
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
    }
    
    /**
     * Load theaters for selected movie
     */
    private void loadTheaters(int movieId) {
        List<String> theaters = movieDAO.getTheatersForMovie(movieId);
        cmbTheaters.removeAllItems();
        for (String theater : theaters) {
            cmbTheaters.addItem(theater);
        }
    }
    
    /**
     * Load show timings for selected movie and theater
     */
    private void loadShowTimes(int movieId, String theaterName) {
        List<String> showTimes = movieDAO.getShowTimings(movieId, theaterName);
        cmbShowTimes.removeAllItems();
        for (String showTime : showTimes) {
            cmbShowTimes.addItem(showTime);
        }
    }
    
    /**
     * Update total amount based on ticket count
     */
    private void updateTotalAmount() {
        int tickets = (int) spnTickets.getValue();
        double total = tickets * TICKET_PRICE;
        lblTotalAmount.setText("Total Amount: ₹" + total);
    }
    
    /**
     * Proceed to payment
     */
    private void proceedToPayment() {
        Movie selectedMovie = (Movie) cmbMovies.getSelectedItem();
        String selectedTheater = (String) cmbTheaters.getSelectedItem();
        String selectedShowTime = (String) cmbShowTimes.getSelectedItem();
        int tickets = (int) spnTickets.getValue();
        double totalAmount = tickets * TICKET_PRICE;
        
        // Open payment frame
        new PaymentFrame(currentUser, selectedMovie, selectedTheater, 
                        selectedShowTime, tickets, totalAmount, this).setVisible(true);
    }
}