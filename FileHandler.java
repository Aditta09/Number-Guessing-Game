import java.io.*;
import java.util.*;

public class FileHandler {
    private static final String FILE_NAME = "players.txt";

    static {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            ExceptionHandler.handleFileException(e);
        }
    }

    public static void savePlayer(String email, String phone, int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(email + "," + phone + "," + score);
            writer.newLine();
        } catch (IOException e) {
            ExceptionHandler.handleFileException(e);
        }
    }

    public static List<String[]> loadPlayers() {
        List<String[]> players = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                players.add(line.split(","));
            }
        } catch (IOException e) {
            ExceptionHandler.handleFileException(e);
        }

        return players;
    }

    public static boolean userExists(String email) {
        for (String[] p : loadPlayers()) {
            if (p[0].equalsIgnoreCase(email)) return true;
        }
        return false;
    }

    public static void updatePlayerScore(String email, int newScore) {
        List<String[]> players = loadPlayers();
        boolean updated = false;

        for (String[] p : players) {
            if (p[0].equalsIgnoreCase(email)) {
                int oldScore = Integer.parseInt(p[2]);
                if (newScore < oldScore) {
                    p[2] = String.valueOf(newScore);
                    updated = true;
                }
            }
        }

        if (updated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                for (String[] p : players) {
                    writer.write(String.join(",", p));
                    writer.newLine();
                }
            } catch (IOException e) {
                ExceptionHandler.handleFileException(e);
            }
        }
    }
}
