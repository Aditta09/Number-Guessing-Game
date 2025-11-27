import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GameGUI extends JFrame implements ActionListener {

    private JTextField guessField;
    private JButton guessButton, restartButton;
    private JLabel messageLabel, attemptsLabel, titleLabel, userLabel;
    private JPanel panel;

    private int targetNumber, attempts, maxAttempts;
    private int minRange, maxRange;

    private String playerEmail;

    public GameGUI(String email) {
        this.playerEmail = email;

        setTitle("🎮 Number Guessing Game - " + playerEmail);
        setSize(500, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color bgColor = new Color(30, 30, 46);
        Color btnColor = new Color(100, 149, 237);
        Color textColor = new Color(230, 230, 250);
        Color accentColor = new Color(255, 215, 0);

        panel = new JPanel(new GridBagLayout());
        panel.setBackground(bgColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        titleLabel = new JLabel(" Number Guessing Game", SwingConstants.CENTER);
        titleLabel.setForeground(accentColor);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        userLabel = new JLabel(" Player: " + playerEmail, SwingConstants.CENTER);
        userLabel.setForeground(textColor);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 1;
        panel.add(userLabel, gbc);

        messageLabel = new JLabel("Enter your guess between 1–100:", SwingConstants.CENTER);
        messageLabel.setForeground(textColor);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridy = 2;
        panel.add(messageLabel, gbc);

        guessField = new JTextField(10);
        guessField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(guessField, gbc);

        guessButton = new JButton("Guess");
        guessButton.setBackground(btnColor);
        guessButton.setForeground(Color.WHITE);
        guessButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        guessButton.addActionListener(this);
        gbc.gridx = 1;
        panel.add(guessButton, gbc);

        attemptsLabel = new JLabel("Attempts: 0/10", SwingConstants.CENTER);
        attemptsLabel.setForeground(textColor);
        attemptsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(attemptsLabel, gbc);

        restartButton = new JButton("Restart Game");
        restartButton.setBackground(new Color(46, 139, 87));
        restartButton.setForeground(Color.WHITE);
        restartButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        restartButton.addActionListener(e -> initializeGame());
        gbc.gridy = 5;
        panel.add(restartButton, gbc);

        add(panel);

        initializeGame();
    }

    private void initializeGame() {
        minRange = 1;
        maxRange = 100;
        maxAttempts = 10;
        attempts = 0;

        targetNumber = new Random().nextInt(maxRange - minRange + 1) + minRange;

        messageLabel.setText("Enter your guess between 1–100:");
        messageLabel.setForeground(Color.WHITE);

        attemptsLabel.setText("Attempts: 0/" + maxAttempts);

        guessField.setText("");
        guessField.setEnabled(true);
        guessButton.setEnabled(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            int guess = Integer.parseInt(guessField.getText());
            attempts++;

            if (guess < minRange || guess > maxRange) {
                messageLabel.setText(" Guess must be between " + minRange + "–" + maxRange);
                messageLabel.setForeground(Color.ORANGE);
                return;
            }

            if (guess == targetNumber) {
                messageLabel.setText(" Correct! " + attempts + " tries.");
                messageLabel.setForeground(new Color(0, 255, 127));

                guessField.setEnabled(false);
                guessButton.setEnabled(false);

                FileHandler.updatePlayerScore(playerEmail, attempts);
            } else if (guess < targetNumber) {
                messageLabel.setText("⬆ Too low!");
                messageLabel.setForeground(Color.CYAN);
            } else {
                messageLabel.setText("⬇ Too high!");
                messageLabel.setForeground(Color.PINK);
            }

            attemptsLabel.setText("Attempts: " + attempts + "/" + maxAttempts);

            if (attempts >= maxAttempts && guess != targetNumber) {
                messageLabel.setText(" Out of attempts! Number: " + targetNumber);
                messageLabel.setForeground(Color.RED);

                guessField.setEnabled(false);
                guessButton.setEnabled(false);
            }

        } catch (NumberFormatException ex) {
            messageLabel.setText(" Invalid input. Enter a number.");
            messageLabel.setForeground(Color.ORANGE);
        }
    }
}
