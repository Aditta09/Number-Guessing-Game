import java.util.Scanner;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        System.out.println(" Welcome to Number Guessing Game!");
        System.out.println("==============================");

        UserAuth auth = new UserAuth();
        Scanner sc = new Scanner(System.in);

        while (!auth.isLoggedIn()) {
            System.out.println("\n1️ Register");
            System.out.println("2️ Login");
            System.out.print("Choose an option: ");
            int choice = ExceptionHandler.handleInvalidNumberInput(sc, 1, 2);
            sc.nextLine();

            if (choice == 1) auth.register();
            else if (choice == 2) auth.login();
        }

        System.out.println("\nLaunching game window for " + auth.getEmail() + "...");

        
        final Object lock = new Object();

        SwingUtilities.invokeLater(() -> {
            GameGUI gui = new GameGUI(auth.getEmail());
            gui.setVisible(true);

            
            gui.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    synchronized (lock) {
                        lock.notify();
                    }
                }
            });
        });

        synchronized (lock) {
            try {
                lock.wait();  
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        auth.logout();
    }
}
