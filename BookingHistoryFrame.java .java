/**
 * Booking History Frame - View and Cancel Bookings Module
 * Displays user's booking history and allows cancellation
 * 
 * Purpose: Show all user bookings, cancel tickets, view ticket details
 * Features: List bookings, filter by status, cancel booking
 */

package com.movieticket.ui;

import com.movieticket.dao.BookingDAO;
import com.movieticket.model.Booking;
import com.movieticket.model.User;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class BookingHistoryFrame extends JFrame {
    
    private User currentUser;
    private MovieSelectionFrame parentFrame;
    private BookingDAO bookingDAO;
    
    // Components
    private JTable tblBookings;
    private DefaultTableModel tableModel;
    private JButton btnViewTicket;
    private JButton btnCancelBooking;
    private JButton btnRefresh;
    private JButton btnClose;
    
    public BookingHistoryFrame(User user, MovieSelectionFrame parent) {
        this.currentUser = user;
        this.parentFrame = parent;
        this.bookingDAO = new BookingDAO();
        
        // Step 1: Setup Frame
        setTitle("My Bookings - " + user.getUsername());
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        setResizable(false);
        
        // Step 2: Initialize Components
        initComponents();
        
        // Step 3: Load Bookings
        loadBookings();
        
        // Step 4: Add Event Listeners
        addEventListeners();
    }
    
    /**
     * Initialize all UI components
     */
    private void initComponents() {
        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Title Panel
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(240, 248, 255));
        JLabel lblTitle = new JLabel("My Booking History");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(25, 25, 112));
        titlePanel.add(lblTitle);
        
        // Table Panel
        String[] columnNames = {"Booking ID", "Movie", "Theater", "Show Time", 
                               "Tickets", "Amount", "Date", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        tblBookings = new JTable(tableModel);
        tblBookings.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblBookings.setRowHeight(30);
        tblBookings.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblBookings.getTableHeader().setBackground(new Color(70, 130, 180));
        tblBookings.getTableHeader().setForeground(Color.WHITE);
        tblBookings.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Set column widths
        tblBookings.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblBookings.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblBookings.getColumnModel().getColumn(2).setPreferredWidth(120);
        tblBookings.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblBookings.getColumnModel().getColumn(4).setPreferredWidth(60);
        tblBookings.getColumnModel().getColumn(5).setPreferredWidth(80);
        tblBookings.getColumnModel().getColumn(6).setPreferredWidth(130);
        tblBookings.getColumnModel().getColumn(7).setPreferredWidth(90);
        
        // Custom renderer for status column
        tblBookings.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, 
                    isSelected, hasFocus, row, column);
                
                String status = value.toString();
                if (status.equals("CONFIRMED")) {
                    setForeground(new Color(0, 128, 0));
                    setFont(new Font("Arial", Font.BOLD, 12));
                } else if (status.equals("CANCELLED")) {
                    setForeground(Color.RED);
                    setFont(new Font("Arial", Font.BOLD, 12));
                }
                
                return c;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tblBookings);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));
        
        btnViewTicket = new JButton("View Ticket");
        btnViewTicket.setPreferredSize(new Dimension(140, 35));
        btnViewTicket.setBackground(new Color(70, 130, 180));
        btnViewTicket.setForeground(Color.WHITE);
        btnViewTicket.setFocusPainted(false);
        btnViewTicket.setEnabled(false);
        
        btnCancelBooking = new JButton("Cancel Booking");
        btnCancelBooking.setPreferredSize(new Dimension(150, 35));
        btnCancelBooking.setBackground(new Color(220, 20, 60));
        btnCancelBooking.setForeground(Color.WHITE);
        btnCancelBooking.setFocusPainted(false);
        btnCancelBooking.setEnabled(false);
        
        btnRefresh = new JButton("Refresh");
        btnRefresh.setPreferredSize(new Dimension(120, 35));
        btnRefresh.setBackground(new Color(100, 149, 237));
        btnRefresh.setForeground(Color.WHITE);
        btnRefresh.setFocusPainted(false);
        
        btnClose = new JButton("Close");
        btnClose.setPreferredSize(new Dimension(100, 35));
        btnClose.setBackground(new Color(128, 128, 128));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFocusPainted(false);
        
        buttonPanel.add(btnViewTicket);
        buttonPanel.add(btnCancelBooking);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnClose);
        
        // Add components to main panel
        mainPanel.add(titlePanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    /**
     * Step 1: Get all bookings from database
     * Step 2: Clear existing table data
     * Step 3: Populate table with booking data
     */
    private void loadBookings() {
        // Step 1: Get bookings
        List<Booking> bookings = bookingDAO.getUserBookings(currentUser.getUserId());
        
        // Step 2: Clear table
        tableModel.setRowCount(0);
        
        // Step 3: Populate table
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
        
        for (Booking booking : bookings) {
            Object[] row = {
                "BKG" + String.format("%06d", booking.getBookingId()),
                booking.getMovieName(),
                booking.getTheaterName(),
                booking.getShowTime(),
                booking.getNumberOfTickets(),
                "₹" + booking.getTotalAmount(),
                dateFormat.format(booking.getBookingDate()),
                booking.getStatus()
            };
            tableModel.addRow(row);
        }
        
        if (bookings.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "You have no bookings yet!",
                "No Bookings",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Add event listeners
     */
    private void addEventListeners() {
        // Table selection listener
        tblBookings.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblBookings.getSelectedRow();
                if (selectedRow >= 0) {
                    String status = tableModel.getValueAt(selectedRow, 7).toString();
                    btnViewTicket.setEnabled(true);
                    btnCancelBooking.setEnabled(status.equals("CONFIRMED"));
                }
            }
        });
        
        // View ticket button
        btnViewTicket.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewSelectedTicket();
            }
        });
        
        // Cancel booking button
        btnCancelBooking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelSelectedBooking();
            }
        });
        
        // Refresh button
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBookings();
                btnViewTicket.setEnabled(false);
                btnCancelBooking.setEnabled(false);
            }
        });
        
        // Close button
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    /**
     * View selected ticket
     */
    private void viewSelectedTicket() {
        int selectedRow = tblBookings.getSelectedRow();
        if (selectedRow >= 0) {
            String bookingIdStr = tableModel.getValueAt(selectedRow, 0).toString();
            int bookingId = Integer.parseInt(bookingIdStr.replace("BKG", ""));
            
            Booking booking = bookingDAO.getBookingById(bookingId);
            if (booking != null) {
                new TicketFrame(booking, currentUser).setVisible(true);
            }
        }
    }
    
    /**
     * Step 1: Confirm cancellation with user
     * Step 2: Call DAO to update booking status
     * Step 3: Refresh table
     */
    private void cancelSelectedBooking() {
        int selectedRow = tblBookings.getSelectedRow();
        if (selectedRow >= 0) {
            String bookingIdStr = tableModel.getValueAt(selectedRow, 0).toString();
            String movieName = tableModel.getValueAt(selectedRow, 1).toString();
            
            // Step 1: Confirm cancellation
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to cancel booking for:\n" + movieName + "?",
                "Confirm Cancellation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm == JOptionPane.YES_OPTION) {
                int bookingId = Integer.parseInt(bookingIdStr.replace("BKG", ""));
                
                // Step 2: Cancel booking
                boolean success = bookingDAO.cancelBooking(bookingId);
                
                if (success) {
                    JOptionPane.showMessageDialog(this,
                        "Booking cancelled successfully!\nRefund will be processed within 5-7 business days.",
                        "Cancellation Success",
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    // Step 3: Refresh table
                    loadBookings();
                    btnViewTicket.setEnabled(false);
                    btnCancelBooking.setEnabled(false);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Failed to cancel booking! Please try again.",
                        "Cancellation Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}