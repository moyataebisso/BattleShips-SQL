package com.example;
import java.util.Scanner;

import com.example.util.DatabaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class Game {
    public static void main(String[] args) {
        System.out.println("What mode would you like:");
        System.out.println("Beginner, Intermediate, or Expert?");
        Scanner s = new Scanner(System.in);
        String input = s.nextLine();

        // Log game mode to the database
        logGameMode(input);

        if (input.equals("Beginner")) {
            Board b = new Board(3);
            boolean debugMode = initializeDebugMode(b);
            runGame(b, debugMode);
        } else if (input.equals("Intermediate")) {
            Board b = new Board(6);
            boolean debugMode = initializeDebugMode(b);
            runGame(b, debugMode);
        } else if (input.equals("Expert")) {
            Board b = new Board(9);
            b.display();
            runGame(b, false);
        } else {
            System.out.println("Incorrect Mode Selected.");
        }
    }

    private static boolean initializeDebugMode(Board b) {
        Scanner debug = new Scanner(System.in);
        System.out.println("Would you like to play in debug mode, yes or no?");
        String debugInput = debug.nextLine();
        boolean debugMode = false;

        if (debugInput.equals("yes")) {
            System.out.println("Game beginning in debug mode");
            debugMode = true;
            b.setDebug();
        } else if (debugInput.equals("no")) {
            System.out.println("Game beginning normally.");
        } else {
            System.out.println("Invalid input, game starting normally.");
        }

        return debugMode;
    }

    private static void runGame(Board b, boolean debugMode) {
        Scanner ss = new Scanner(System.in);

        while (b.getB_remain() > 0) {
            System.out.println("Turn " + b.getTurns() + ": Where would you like to hit?");
            String input = ss.nextLine();

            if (input.equals("missile")) {
                System.out.println("Where would you like to hit with the missile?");
                String coordinates = ss.nextLine();
                int result = Integer.parseInt(coordinates);
                int col = result % 10;
                int row = result / 10;
                b.missile(row, col);
                b.display();
            } else if (input.equals("drone")) {
                b.drone();
                b.display();
            } else if (input.equals("submarine")) {
                System.out.println("Where would you like to attack with the submarine?");
                String coordinates = ss.nextLine();
                int result = Integer.parseInt(coordinates);
                int col = result % 10;
                int row = result / 10;
                b.submarine(row, col);
                b.display();
            } else {
                int result = Integer.parseInt(input);
                int col = result % 10;
                int row = result / 10;
                b.fire(row, col);

                if (debugMode) {
                    b.display();
                } else {
                    b.print();
                }
            }
        }

        System.out.println("Game Over!");
        logGameEnd(b.getTurns());
    }

    private static void logGameMode(String mode) {
        String sql = "INSERT INTO game_state (state) VALUES (?)";
        try (Connection conn = DatabaseUtils.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Mode: " + mode);
            stmt.executeUpdate();
            System.out.println("Game mode logged to database: " + mode);
        } catch (Exception e) {
            System.out.println("Error logging game mode: " + e.getMessage());
        }
    }

    private static void logGameEnd(int turns) {
        String sql = "INSERT INTO game_state (state) VALUES (?)";
        try (Connection conn = DatabaseUtils.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "Game ended in " + turns + " turns");
            stmt.executeUpdate();
            System.out.println("Game end logged to database: " + turns + " turns");
        } catch (Exception e) {
            System.out.println("Error logging game end: " + e.getMessage());
        }
    }
}
