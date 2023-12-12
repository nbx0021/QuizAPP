package QuizApp;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class QuizApplication extends JFrame {
    private JLabel questionLabel, resultLabel, usernameLabel;
    private JButton option1Button, option2Button, option3Button, option4Button, leaderboardButton;
    private List<Question> questions;
    private int currentQuestionIndex = -1;
    private String username;
    private int score = 0;
    private int user_id;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/quiz_database";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";

    public QuizApplication(int user_id, String username) {
        this.user_id = user_id;
        this.username=username;

        setTitle("Quiz Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 500);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        usernameLabel = new JLabel("User: " + username);
        usernameLabel.setFont(new Font("MV Boli", Font.BOLD, 24));
        topPanel.add(usernameLabel);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(5, 1));
        questionLabel = new JLabel();
        questionLabel.setBackground(Color.green);
        questionLabel.setFont(new Font("MV Boli", Font.BOLD, 24));
        resultLabel = new JLabel();
        resultLabel.setFont(new Font("MV Boli", Font.BOLD, 24));
        option1Button = new JButton();
        option2Button = new JButton();
        option3Button = new JButton();
        option4Button = new JButton();
        centerPanel.add(questionLabel);
        centerPanel.add(option1Button);
        centerPanel.add(option2Button);
        centerPanel.add(option3Button);
        centerPanel.add(option4Button);
        centerPanel.add(resultLabel);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.setFont(new Font("MV Boli", Font.BOLD, 18));
        bottomPanel.add(leaderboardButton);
        add(bottomPanel, BorderLayout.SOUTH);

        initializeQuestions();
        displayNextQuestion();

        option1Button.addActionListener(e -> handleOptionButtonClick(1));
        option2Button.addActionListener(e -> handleOptionButtonClick(2));
        option3Button.addActionListener(e -> handleOptionButtonClick(3));
        option4Button.addActionListener(e -> handleOptionButtonClick(4));
        leaderboardButton.addActionListener(e -> openLeaderboard());

        setVisible(true);
    }

    class Question {
        private int id;
        private String questionText;
        private String option1;
        private String option2;
        private String option3;
        private String option4;
        private int correctOption;

        public Question(int id, String questionText, String option1, String option2, String option3, String option4,
                int correctOption) {
            this.id = id;
            this.questionText = questionText;
            this.option1 = option1;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
            this.correctOption = correctOption;
        }

        public String getQuestion() {
            return questionText;
        }

        public String getOption1() {
            return option1;
        }

        public String getOption2() {
            return option2;
        }

        public String getOption3() {
            return option3;
        }

        public String getOption4() {
            return option4;
        }

        public int getCorrectOption() {
            return correctOption;
        }
    }

    // Method for initializing questions
    private void initializeQuestions() {
        questions = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT * FROM questions";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                    ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("question_id");
                    String questionText = resultSet.getString("question_text");
                    String option1 = resultSet.getString("option1");
                    String option2 = resultSet.getString("option2");
                    String option3 = resultSet.getString("option3");
                    String option4 = resultSet.getString("option4");
                    int correctOption = resultSet.getInt("correct_option");

                    Question question = new Question(id, questionText, option1, option2, option3, option4,
                            correctOption);
                    questions.add(question);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method for displaying the next question
    private void displayNextQuestion() {
        if (currentQuestionIndex < questions.size() - 1) {
            currentQuestionIndex++;
            Question currentQuestion = questions.get(currentQuestionIndex);

            questionLabel.setText(currentQuestion.getQuestion());
            option1Button.setText("1. " + currentQuestion.getOption1());
            option2Button.setText("2. " + currentQuestion.getOption2());
            option3Button.setText("3. " + currentQuestion.getOption3());
            option4Button.setText("4. " + currentQuestion.getOption4());
            resultLabel.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Quiz Completed!");
            openLeaderboard();
            // Additional actions after completing the quiz
        }
    }

    // Method for handling button click (answering questions)
    private void handleOptionButtonClick(int selectedOption) {
        Question currentQuestion = questions.get(currentQuestionIndex);

        if (selectedOption == currentQuestion.getCorrectOption()) {
            resultLabel.setText("Correct!");
            resultLabel.setForeground(Color.GREEN);
            score += 10; // Increment score by 10 only for correct answers
        } else {
            resultLabel.setText("Wrong Answer!");
            resultLabel.setForeground(Color.RED);
        }

        if (selectedOption == currentQuestion.getCorrectOption()) {
            try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                String updateQuery = "INSERT INTO user_scores (user_id, score) VALUES (?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                    preparedStatement.setInt(1, user_id);
                    preparedStatement.setInt(2, score);
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        option1Button.setEnabled(false);
        option2Button.setEnabled(false);
        option3Button.setEnabled(false);
        option4Button.setEnabled(false);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                displayNextQuestion();
                option1Button.setEnabled(true);
                option2Button.setEnabled(true);
                option3Button.setEnabled(true);
                option4Button.setEnabled(true);
            }
        }, 2000);
    }

    // Method for displaying the leaderboard
    private void openLeaderboard() {
        JFrame leaderboardFrame = new JFrame("Leaderboard");
        leaderboardFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        leaderboardFrame.setSize(400, 300);
        leaderboardFrame.setLayout(new BorderLayout());

        JTextArea leaderboardTextArea = new JTextArea();
        leaderboardTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(leaderboardTextArea);
        leaderboardFrame.add(scrollPane, BorderLayout.CENTER);

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT u.username, SUM(us.score) AS total_score " +
                    "FROM user_scores us " +
                    "JOIN users u ON us.user_id = u.user_id " +
                    "GROUP BY us.user_id " +
                    "ORDER BY total_score DESC";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                    ResultSet resultSet = preparedStatement.executeQuery()) {

                StringBuilder leaderboard = new StringBuilder();
                leaderboard.append("Username\t\tScore\n");
                while (resultSet.next()) {
                    String username = resultSet.getString("username");
                    int totalScore = resultSet.getInt("total_score");
                    leaderboard.append(username).append("\t\t\t").append(totalScore).append("\n");
                }

                leaderboardTextArea.setText(leaderboard.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        leaderboardFrame.setVisible(true);
    }
}
