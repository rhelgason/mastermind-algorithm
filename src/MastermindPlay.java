package src;
import java.util.Scanner;

/**
 * Entry point for program when playing against the computer.
 */
public class MastermindPlay {
    public static void main(String[] args) {
        //initialize variables
        int length = 4;
        int numColor = 6;
        Trie S = new Trie(length, numColor);
        Trie unused = new Trie(length, numColor);
        int[] guess = {0, 0, 1, 1};
        int white = 0;
        int black = 0;
        int numGuess = 0;
        boolean won = false;

        // continue guessing until the game is completed
        System.out.println("\nWelcome to Mastermind. Create a permutation of " + length + " colors for the computer to guess.");
        System.out.println("You may choose between the following colors: red, blue, green, yellow, white, and black.");
        while (!won) {
            // get user input
            unused.removePath(unused.getRoot(), guess);
            System.out.println("\nGuess " + ++numGuess + ":" + toColor(guess));
            do {
                black = getPegs("black");
                white = getPegs("white");
                if (white + black > 4) System.out.println("\nError. The total number of pegs must be between 0 and 4 inclusive.");
            } while (white + black > 4);

            // check for win
            if (black == 4) {
                won = true;
                continue;
            }

            // select next guess
            S.removeGuesses(S.getRoot(), black, white, new int[length], guess);
            if (S.getSize() == 0) {
                break;
            } else {
                unused.minimax(unused.getRoot(), S, new int[length], guess, -1);
            }
        }

        // game has finished
        if (won && numGuess <= 5) {
            System.out.println("\nThe correct code was guessed in " + numGuess + " attempts.");
        } else if (won) {
            System.out.println("\nThe correct code was guessed in " + numGuess + " attempts. However, this algorithm " +
                    "should always succeed in five or fewer guesses. This is most likely not an error with the program. " +
                    "Please check your responses and retry.");
        } else {
            System.out.println("\nThe computer was unable to guess your code. This is most likely not an error with the " +
                    "program. Please check your responses and retry.");
        }
    }

    /**
     * Convert integers to color format.
     *
     * @param guess array representing string of colors
     * @return readable format for color series
     */
    public static String toColor(int[] guess) {
        String[] colors = { "red", "blue", "green", "yellow", "white", "black" };
        String out = "";
        for (int i = 0; i < guess.length; i++) out += " " + colors[guess[i]];
        return out;
    }

    /**
     * Get user input for the number of white and black pegs.
     *
     * @param color the current color of pegs
     * @return the number of pegs
     */
    public static int getPegs(String color) {
        Scanner scan = new Scanner(System.in);
        boolean invalid = true;
        String input = "";
        do {
            System.out.print("Enter the number of " + color + " pegs: ");
            input = scan.nextLine();
            if (!input.matches("-*\\d+")) System.out.println("\nError. Please enter a valid integer.");
            else if (Integer.parseInt(input) < 0 || Integer.parseInt(input) > 4) System.out.println("\nError. Input must be between 0 and 4 inclusive.");
            else invalid = false;
        } while (invalid);
        return Integer.parseInt(input);
    }
}
