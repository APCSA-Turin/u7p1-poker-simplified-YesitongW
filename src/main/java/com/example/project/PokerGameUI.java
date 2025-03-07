package com.example.project;

import java.util.Scanner;

public class PokerGameUI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = "y";

        System.out.println("Welcome to Simplified Poker!");

        // Main game loop using a while loop instead of do/while.
        while (input.equals("y")) {
            System.out.println("\nPress Enter to start a new round...");
            scanner.nextLine(); // Wait for user input

            // Start a round of the game.
            Game.play();

            System.out.println("\nPlay another round? (y/or any other key to end): ");
            input = scanner.nextLine().toLowerCase();
        }

        System.out.println("Thanks for playing!");
        scanner.close();
    }
}
