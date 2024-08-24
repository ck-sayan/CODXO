/* This is the Task-1 as a Java Programming Intern at CODXO.
   My name is Sayan Chakraborty. GITHUB PROFILE: ck-sayan */

// NOTE: Comments have been provided at each step/function/class/object for clear and easier navigation.

import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random rng = new Random();

        // Setting the range for the game
        final int MIN = 1;
        final int MAX = 100;
        int targetNumber = rng.nextInt(MAX - MIN + 1) + MIN;
        int attempts = 0;
        boolean isCorrect = false;

        // Game introduction
        System.out.println("Welcome! Can you guess the number I'm thinking of?");
        System.out.println("It's between " + MIN + " and " + MAX + ". Give it a try!");

        // Game loop
        while (!isCorrect) {
            System.out.print("Enter your guess: ");

            // Validate input
            if (!input.hasNextInt()) {
                System.out.println("Oops! Please enter a valid integer.");
                input.next(); // Clear invalid input
                continue;
            }

            int userGuess = input.nextInt();
            attempts++;

            // Check if the guess is within bounds
            if (userGuess < MIN || userGuess > MAX) {
                System.out.println("Your guess is out of bounds! Guess between " + MIN + " and " + MAX + ".");
                continue;
            }

            // Compare guess to target number
            if (userGuess < targetNumber) {
                System.out.println("Uh-Oh, it's higher. Try again.");
            } else if (userGuess > targetNumber) {
                System.out.println("Guess it lower buddy. Try again.");
            } else {
                isCorrect = true;
            }
        }

        // Congratulate the player
        System.out.println("VAMOS! You've seemed to guess it right after " + attempts + " tries.");

        // Ask to play again
        System.out.print("Would you like to play again? (y/n): ");
        String replayChoice = input.next();

        if (replayChoice.equalsIgnoreCase("y")) {
            main(args); // Restart the game
        } else {
            System.out.println("Thanks for playing! See you next time.");
        }
        
        input.close();
    }
}
