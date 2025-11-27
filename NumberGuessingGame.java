import java.util.Random;

public class NumberGuessingGame extends Game {
    private int targetNumber;
    private int attempts;
    private int maxAttempts;
    private int minRange;
    private int maxRange;
    private int bestScore;
    private String difficulty;

    public NumberGuessingGame() {
        super();
        this.bestScore = Integer.MAX_VALUE;
    }

    @Override
    public void initializeGame() {
        selectDifficulty();
        generateRandomNumber();
        attempts = 0;

        System.out.println("\n New Round Started!");
        System.out.println("I'm thinking of a number between " + minRange + " and " + maxRange + ".");
        System.out.println("You have " + maxAttempts + " attempts!");
    }

    private void selectDifficulty() {
        System.out.println("\nSelect Difficulty Level:");
        System.out.println("1. Easy (1–50, 10 attempts)");
        System.out.println("2. Medium (1–100, 7 attempts)");
        System.out.println("3. Hard (1–500, 5 attempts)");
        System.out.print("Enter your choice (1–3): ");

        int choice = ExceptionHandler.handleInvalidNumberInput(scanner, 1, 3);

        switch (choice) {
            case 1 -> setDifficulty("EASY", 1, 50, 10);
            case 2 -> setDifficulty("MEDIUM", 1, 100, 7);
            case 3 -> setDifficulty("HARD", 1, 500, 5);
        }

        System.out.println("Difficulty set to: " + difficulty);
    }

    private void setDifficulty(String level, int min, int max, int attempts) {
        this.difficulty = level;
        this.minRange = min;
        this.maxRange = max;
        this.maxAttempts = attempts;
    }

    private void generateRandomNumber() {
        Random random = new Random();
        targetNumber = random.nextInt(maxRange - minRange + 1) + minRange;
    }

    @Override
    public void playRound() {
        boolean guessedCorrectly = false;

        while (attempts < maxAttempts && !guessedCorrectly) {
            System.out.print("\nEnter guess (" + (attempts + 1) + "/" + maxAttempts + "): ");
            int guess = ExceptionHandler.handleInvalidNumberInput(scanner, minRange, maxRange);
            attempts++;

            if (guess == targetNumber) {
                guessedCorrectly = true;
                handleCorrectGuess();
            } else if (guess < targetNumber) {
                System.out.println("Too low!");
            } else {
                System.out.println("Too high!");
            }

            if (!guessedCorrectly && (maxAttempts - attempts) <= 2) {
                System.out.println("⚠ Only " + (maxAttempts - attempts) + " attempts left!");
            }
        }

        if (!guessedCorrectly) handleGameOver();
    }

    private void handleCorrectGuess() {
        System.out.println(" Correct! You guessed it in " + attempts + " tries.");
        updateScore(attempts);
        displayScore();
    }

    private void handleGameOver() {
        System.out.println("\n Game Over! The number was " + targetNumber);
    }

    @Override
    public void updateScore(int attempts) {
        this.score = attempts;
        if (attempts < bestScore) bestScore = attempts;
    }

    @Override
    public void displayResults() {
        System.out.println("\n Game Stats:");
        System.out.println("Difficulty: " + difficulty);
        System.out.println("Best Score: " + (bestScore == Integer.MAX_VALUE ? "N/A" : bestScore));
    }

    private void displayScore() {
        if (attempts == 1) System.out.println(" Legendary! First try!");
        else if (attempts <= 3) System.out.println(" Excellent!");
        else if (attempts <= 5) System.out.println(" Good job!");
        if (attempts == bestScore) System.out.println(" New Best Score!");
    }

    public int getBestScore() {
        return bestScore;
    }
}
