import java.util.Scanner;

/**
 * Entry point for program.
 */
public class Mastermind_Algorithm {
    public static void main(String[] args) {
        //initialize variables
        Trie S = new Trie();
        int[] guess = {0, 0, 1, 1};
        int white = 0;
        int black = 0;
        boolean cont = true;
        int numGuess = 0;

        // continue guessing until the game is completed
        while (cont) {
            // guess and get response
            System.out.println("Guess " + ++numGuess + ":" + toColor(guess));
            do {
                black = getPegs("black");
                white = getPegs("white");
                if (white + black > 4) System.out.println("\nError. The total number of pegs must be between 0 and 4 inclusive.");
            } while (white + black > 4);

            // check for win
            if (black == 4) {
                cont = false;
                continue;
            }

            // remove impossible combinations
            S.removeGuesses(white, black, guess);
            cont = false;
        }

        // game has been won
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
