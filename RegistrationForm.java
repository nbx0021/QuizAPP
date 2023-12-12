package QuizApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationForm {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/quiz_database";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";

    public static void showRegistrationForm() {
        JFrame registerFrame = new JFrame("Register");
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        registerFrame.setSize(500, 500);

        // Components for the registration window
        JPanel registerPanel = new JPanel(null); // Use null layout for absolute positioning

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 50, 80, 30); // Set bounds for label
        JTextField nameField = new JTextField();
        nameField.setBounds(150, 50, 200, 30); // Set bounds for text field
        nameField.setFont(new Font("MV Boli", Font.BOLD, 20));

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 100, 80, 30); // Set bounds for label
        JTextField emailField = new JTextField();
        emailField.setBounds(150, 100, 200, 30); // Set bounds for text field
        emailField.setFont(new Font("MV Boli", Font.BOLD, 20));

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 150, 80, 30); // Set bounds for label
        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 150, 200, 30); // Set bounds for text field
        usernameField.setFont(new Font("MV Boli", Font.BOLD, 20));

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 200, 80, 30); // Set bounds for label
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 200, 200, 30); // Set bounds for password field
        passwordField.setFont(new Font("MV Boli", Font.BOLD, 20));

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(150, 250, 170, 30); // Set bounds for button
        submitButton.setFont(new Font("MV Boli", Font.BOLD, 20));
        submitButton.setFocusable(false);

        // Add components to the registration panel
        registerPanel.add(nameLabel);
        registerPanel.add(nameField);
        registerPanel.add(emailLabel);
        registerPanel.add(emailField);
        registerPanel.add(usernameLabel);
        registerPanel.add(usernameField);
        registerPanel.add(passwordLabel);
        registerPanel.add(passwordField);
        registerPanel.add(submitButton);

        // Action listener for the registration window's submit button
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String email = emailField.getText();
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                // Database connection and user registration
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String insertQuery = "INSERT INTO users (username, password, email, full_name) VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);
                    preparedStatement.setString(3, email);
                    preparedStatement.setString(4, name);

                    int rowsInserted = preparedStatement.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Registration successful", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                        registerFrame.dispose(); // Close the registration window after registration is complete
                    } else {
                        JOptionPane.showMessageDialog(null, "Registration failed", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database connection error", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }); // Add the registration panel to the registration frame
        registerFrame.add(registerPanel);
        registerFrame.setVisible(true);
    }
}
