package src;
import java.util.Scanner;
import java.util.Random;

/**
 * Entry point for program when running games automatically.
 */
public class MastermindAuto {
    public static void main(String[] args) {
        //initialize variables
        int length = 4;
        int numColor = 6;
        Random rand = new Random();

        // get game count input
        Scanner scan = new Scanner(System.in);
        boolean invalid = true;
        String input = "";
        System.out.println();
        do {
            System.out.print("Enter the number of games that should be ran: ");
            input = scan.nextLine();
            if (!input.matches("-*\\d+")) {
                System.out.println("\nError. Please enter a valid integer.");
                continue;
            }
            try {
                Integer.parseInt(input);
            } catch(NumberFormatException e) {
                System.out.println("\nError. The integer is out of bounds.");
                continue;
            }
            if (Integer.parseInt(input) <= 0) System.out.println("\nError. Input must be greater than 0.");
            else invalid = false;
        } while (invalid);
        scan.close();
        System.out.println();
        int numGame = Integer.parseInt(input);
        int game = 0;
        int totalGuess = 0;

        while (game < numGame) {
            // initialize game variables
            game++;
            Trie S = new Trie(length, numColor);
            Trie unused = new Trie(length, numColor);
            int[] guess = {0, 0, 1, 1};
            int[] code = new int[length];
            for (int i = 0; i < length; i++) code[i] = rand.nextInt(numColor);
            int white = 0;
            int black = 0;
            int numGuess = 0;
            boolean won = false;

            // continue guessing until the game is completed
            while (!won) {
                // get guess feedback
                numGuess++;
                unused.removePath(unused.getRoot(), guess);
                var tempCode = new int[length];
                for (int i = 0; i < length; i++) tempCode[i] = code[i];
                var tempGuess = new int[length];
                for (int i = 0; i < length; i++) tempGuess[i] = guess[i];
                black = getBlack(tempCode, tempGuess);
                white = getWhite(tempCode, tempGuess);

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
            totalGuess += numGuess;
            System.out.println("Game " + game + ": " + numGuess + (numGuess == 1 ? " guess was " : " guesses were ") +
                    "used to solve a pattern of \"" + toColor(code) + "\".");
        }

        // print stats
        double average = ((double) totalGuess) / numGame;
        System.out.println("\nThe computer played " + numGame + (numGame == 1 ? " game " : " games ") +
                "with an average of " + average + (average == 1.0 ? " guess " : " guesses ") + "per game.");
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
        return out.trim();
    }

    /**
     * Calculate the amount of black response pegs.
     *
     * @param code  the secret pattern
     * @param guess the guess being made
     * @return number of black pegs
     */
    public static int getBlack(int[] code, int[] guess) {
        int black = 0;
        for (int i = 0; i < code.length; i++) {
            if (code[i] == guess[i]) {
                black++;
                code[i] = -1;
                guess[i] = -1;
            }
        }
        return black;
    }

    /**
     * Calculate the amount of white response pegs.
     *
     * @param code  the secret pattern
     * @param guess the guess being made
     * @return number of white pegs
     */
    public static int getWhite(int[] code, int[] guess) {
        int white = 0;
        for (int i = 0; i < code.length; i++) {
            if (code[i] != -1) {
                for (int j = 0; j < guess.length; j++) {
                    if (code[i] == guess[j]) {
                        white++;
                        guess[j] = -1;
                        break;
                    }
                }
            }
        }
        return white;
    }
}
