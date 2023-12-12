
package QuizApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginForm {
    private JFrame loginFrame;
    private JTextField usernameField;
    private JPasswordField passwordField;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/quiz_database";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";

    private void createQuizApplication(int userId, String username) {
        QuizApplication quizApp = new QuizApplication(userId, username);
        quizApp.setVisible(true);
    }

    public LoginForm() {
        loginFrame = new JFrame("Login Form");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(500, 500);
        loginFrame.setLayout(new GridLayout(7, 7));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        usernameField.setFont(new Font("MV Boli", Font.BOLD, 20));

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("MV Boli", Font.BOLD, 20));

        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("MV Boli", Font.BOLD, 20));
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                int user_Id;

                // Database connection and user authentication
                try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String query = "SELECT * FROM users WHERE username = ? AND password = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);

                    ResultSet resultSet = preparedStatement.executeQuery();
                    if (resultSet.next()) {
                        user_Id = resultSet.getInt("user_id"); // Retrieve user_id from the result set
                        // Login successful, open the quiz application
                        loginFrame.dispose(); // Close the login form
                        createQuizApplication(user_Id, username);
                    } else {
                        JOptionPane.showMessageDialog(null, "Invalid username or password");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database connection error", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);
    }

    public void showLoginForm() {
        loginFrame.setVisible(true);
    }
}
