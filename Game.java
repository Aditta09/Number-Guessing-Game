import java.util.Scanner;

public abstract class Game {
    protected Scanner scanner;
    protected boolean isPlaying;
    protected int score;

    public Game() {
        this.scanner = new Scanner(System.in);
        this.isPlaying = true;
        this.score = 0;
    }

    public abstract void initializeGame();
    public abstract void playRound();
    public abstract void displayResults();
    public abstract void updateScore(int attempts);

    public void startGame() {
        System.out.println(" Welcome to the Game!");
        initializeGame();

        while (isPlaying) {
            playRound();
            if (isPlaying) askToPlayAgain();
        }

        endGame();
    }

    protected void askToPlayAgain() {
        System.out.print("\nPlay again? (Yes/No): ");
        String choice = scanner.next().trim().toUpperCase();

        if (choice.equals("YES")) {
            initializeGame();
        } else if (choice.equals("NO")) {
            isPlaying = false;
        } else {
            System.out.println("Invalid choice.");
            askToPlayAgain();
        }
    }

    protected void endGame() {
        displayResults();
        System.out.println("\nThank you for playing!");
    }

    public int getScore() {
        return score;
    }
}
