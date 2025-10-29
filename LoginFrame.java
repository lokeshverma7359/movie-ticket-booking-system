/**
 * Login Frame - User Login Module
 * First screen of the application
 * 
 * Purpose: Allow users to login with username and password
 * Features: Login validation, Navigate to registration, Open movie selection
 */

package com.movieticket.ui;

import com.movieticket.dao.UserDAO;
import com.movieticket.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    
    // Components
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private UserDAO userDAO;
    
    public LoginFrame() {
        // Initialize DAO
        userDAO = new UserDAO();
        
        // Step 1: Setup Frame
        setTitle("Movie Ticket Booking - Login");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
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
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        mainPanel.setBackground(new Color(240, 248, 255));
        
        // Title Label
        JLabel lblTitle = new JLabel("Online Movie Ticket Booking");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(new Color(25, 25, 112));
        
        // Subtitle
        JLabel lblSubtitle = new JLabel("User Login");
        lblSubtitle.setFont(new Font("Arial", Font.PLAIN, 16));
        lblSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSubtitle.setForeground(new Color(70, 130, 180));
        
        // Username Panel
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernamePanel.setBackground(new Color(240, 248, 255));
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setPreferredSize(new Dimension(100, 25));
        txtUsername = new JTextField(20);
        usernamePanel.add(lblUsername);
        usernamePanel.add(txtUsername);
        
        // Password Panel
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.setBackground(new Color(240, 248, 255));
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setPreferredSize(new Dimension(100, 25));
        txtPassword = new JPasswordField(20);
        passwordPanel.add(lblPassword);
        passwordPanel.add(txtPassword);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(new Color(240, 248, 255));
        btnLogin = new JButton("Login");
        btnLogin.setPreferredSize(new Dimension(120, 35));
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        
        btnRegister = new JButton("Register");
        btnRegister.setPreferredSize(new Dimension(120, 35));
        btnRegister.setBackground(new Color(100, 149, 237));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setFocusPainted(false);
        
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnRegister);
        
        // Add components to main panel
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(lblTitle);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(lblSubtitle);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(usernamePanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(passwordPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonPanel);
        
        add(mainPanel);
    }
    
    /**
     * Add event listeners to buttons
     */
    private void addEventListeners() {
        // Login button click event
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();
            }
        });
        
        // Register button click event
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openRegistrationFrame();
            }
        });
        
        // Enter key press on password field
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });
    }
    
    /**
     * Step 1: Get username and password from fields
     * Step 2: Validate input
     * Step 3: Call DAO to verify credentials
     * Step 4: Open movie selection frame if successful
     */
    private void handleLogin() {
        // Step 1: Get input
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        // Step 2: Validate input
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both username and password!",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Step 3: Verify credentials
        User user = userDAO.validateLogin(username, password);
        
        // Step 4: Handle result
        if (user != null) {
            JOptionPane.showMessageDialog(this,
                "Login Successful! Welcome " + user.getUsername(),
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Open movie selection frame
            new MovieSelectionFrame(user).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid username or password!",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
        }
    }
    
    /**
     * Open registration frame
     */
    private void openRegistrationFrame() {
        new RegistrationFrame(this).setVisible(true);
        this.setVisible(false);
    }
}