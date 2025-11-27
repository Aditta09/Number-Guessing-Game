import java.util.Scanner;

public class ExceptionHandler {

    public static int handleInvalidNumberInput(Scanner scanner, int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input >= min && input <= max) return input;

                System.out.print("⚠ Enter a number between " + min + " and " + max + ": ");
            } catch (Exception e) {
                System.out.print(" Invalid input. Enter a number: ");
                scanner.next();
            }
        }
    }

    public static void handleFileException(Exception e) {
        System.out.println(" File Error: " + e.getMessage());
    }
}
