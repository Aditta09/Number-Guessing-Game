import java.util.Scanner;

public class UserAuth {
    private Scanner scanner;
    private String email;
    private String phone;
    private boolean loggedIn;

    public UserAuth() {
        this.scanner = new Scanner(System.in);
        this.loggedIn = false;
    }

    public void register() {
        System.out.println("\n=== Register New Player ===");
        System.out.print("Enter your email: ");
        email = scanner.nextLine().trim();

        if (FileHandler.userExists(email)) {
            System.out.println(" This email already exists. Try login instead.");
            return;
        }

        System.out.print("Enter phone number: ");
        phone = scanner.nextLine().trim();

        FileHandler.savePlayer(email, phone, Integer.MAX_VALUE);
        loggedIn = true;
        System.out.println(" Registration successful!");
    }

    public void login() {
        System.out.println("\n=== Login ===");
        System.out.print("Enter email: ");
        String inputEmail = scanner.nextLine().trim();

        if (FileHandler.userExists(inputEmail)) {
            this.email = inputEmail;
            loggedIn = true;
            System.out.println(" Login successful!");
        } else {
            System.out.println(" Email not found. Please register.");
        }
    }

    public void logout() {
        System.out.println("\n Logged out successfully.");
        loggedIn = false;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public String getEmail() {
        return email;
    }
}
