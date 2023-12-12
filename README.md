# Quiz Application with Java Swing and MySQL Integration

This is a simple quiz application implemented in Java using Swing for the GUI and MySQL for database integration.

## Table of Contents

- [Introduction](#introduction)
- [Project Structure](#project-structure)
- [Setup](#setup)
- [Usage](#usage)
- [Database Configuration](#database-configuration)
- [Code Overview](#code-overview)
- [Contributing](#contributing)
- [License](#license)

## Introduction

This project implements a quiz application where users can register, login, attempt quizzes, and view the leaderboard. It demonstrates Java Swing for the GUI and MySQL for database management.

## Project Structure

The project consists of the following main files:

- `Quiz.java`: Main class to start the application. Contains the GUI layout for the main menu.
- `LoginForm.java`: Manages the login form functionality and user authentication.
- `RegistrationForm.java`: Handles user registration and database insertion of new users.
- `QuizApplication.java`: Implements the quiz functionality, questions loading, answering questions, and displaying scores.

## Setup

To run the application locally, follow these steps:
1. Set up a MySQL database named `quiz_database`.
2. Import the provided SQL schema for the `questions` and `users` tables.
3. Update the `DB_URL`, `DB_USER`, and `DB_PASSWORD` variables in the code files with your MySQL credentials.

## Usage

1. Run the `Quiz.java` file to start the application.
2. Users can register, login, attempt quizzes, and view the leaderboard from the GUI.

## Database Configuration

The application connects to a MySQL database using JDBC. The database configuration details are specified in the code files using the `DB_URL`, `DB_USER`, and `DB_PASSWORD` variables.

## Code Overview

### `LoginForm.java`

- Handles user authentication by querying the `users` table.
- Validates user credentials against the database.

### `RegistrationForm.java`

- Manages the registration form and inserts new user data into the `users` table.

### `QuizApplication.java`

- Implements the quiz logic:
  - Loads questions from the `questions` table in the database.
  - Allows users to answer questions and displays the score.
  - Updates the `user_scores` table with user scores.

## Contributing

Feel free to contribute to this project by opening issues or pull requests.

## License

This project is licensed under the [MIT License](LICENSE).
