package QuizApp;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Quiz {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Quiz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);

        // Heading label with flashing text
        JLabel headingLabel = new JLabel("Quiz!");
        headingLabel.setFont(new Font("MV Boli", Font.BOLD, 30));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        Timer timer = new Timer(1000, new ActionListener() {
            private boolean isRed = false;

            public void actionPerformed(ActionEvent e) {
                if (isRed) {
                    headingLabel.setForeground(Color.blue);
                } else {
                    headingLabel.setForeground(Color.RED);
                }
                isRed = !isRed;
            }
        });
        timer.start();

        // Central image
        ImageIcon imageIcon = new ImageIcon(
                "D:\\Users\\X510U\\Documents\\c++ files\\c++\\project_files\\QuizApp\\quiz_img.png"); // Replace
                                                                                                      // 'logo.png' with
                                                                                                      // your image file
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setSize(200, 200);

        // Login and Register buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("MV Boli", Font.BOLD, 20));
        loginButton.setFocusable(false);
        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("MV Boli", Font.BOLD, 20));
        registerButton.setFocusable(false);
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // Add components to the frame
        frame.setLayout(new BorderLayout(10, 10));
        frame.add(headingLabel, BorderLayout.NORTH);
        frame.add(imageLabel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners for the buttons
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LoginForm loginForm = new LoginForm();
                loginForm.showLoginForm();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Add register button action here
                // JOptionPane.showMessageDialog(null, "Register clicked");

                RegistrationForm.showRegistrationForm();
            }
        });

        frame.setVisible(true);
    }
}
