import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Class representing a trie data structure.
 */
public class Trie {
    private Node root;
    private int size;
    private static final int length = 4;
    private static final int numColors = 6;

    /**
     * Default constructor for Trie class.
     */
    public Trie() {
        root = new Node(-1, -1);
        size = (int) Math.pow(numColors, length);
        initHelper(root,0);
    }

    /**
     * Recursive helper function for creating the trie.
     *
     * @param curr the current Node in the trie
     * @param depth the current depth in the trie
     * @return initialized Node to be added as a child
     */
    public Node initHelper(Node curr, int depth) {
        // base case
        if (depth > length) {
            return null;
        }

        // set all children
        for (int i = 0; i < numColors; i++) {
            Node temp = new Node(i, depth);
            curr.addChild(initHelper(temp, depth + 1));
        }
        return curr;
    }

    /**
     * Get the current number of patterns in the trie.
     *
     * @return the size of the trie
     */
    public int getSize() {
        return size;
    }

    /**
     * Remove any guesses from the trie that are not possible.
     *
     * @param white the number of white response pegs
     * @param black the number of black response pegs
     * @param guess the last guess made
     */
    public void removeGuesses(int white, int black, int[] guess) {
        int[] empty = new int[length];
        removeGuessesHelper(root, white, black, empty, guess);
    }

    /**
     * Recursive helper function for removing impossible guesses.
     *
     * @param curr the current Node in the trie
     * @param white the number of white response pegs
     * @param black the number of black response pegs
     * @param code current pattern traversal of the trie
     * @param guess the last guess made
     * @return
     */
    public Node removeGuessesHelper(Node curr, int white, int black, int[] code, int[] guess) {
        // determine if pattern is possible or not
        if (curr.getDepth() == length - 1) {
            if (valid(white, black, code, guess)) return curr;
            size--;
            return null;
        }

        // check all child paths
        int i = -1;
        for (Node child : curr.getChildren()) {
            i++;
            if (child == null) continue;
            code[child.getDepth()] = child.getColor();
            curr.setChild(i, removeGuessesHelper(child, white, black, code, guess));
            if (curr.getDepth() < length - 2) curr.setChild(i, child.checkChildren());
        }

        return curr;
    }

    /**
     * Determines if a pattern is possible given the response pegs.
     *
     * @param origWhite the number of white response pegs
     * @param origBlack the number of black response pegs
     * @param origCode possible color pattern
     * @param origGuess the last guess made
     * @return whether or not the pattern is possible
     */
    public boolean valid(int origWhite, int origBlack, int[] origCode, int[] origGuess) {
        // make copies of patterns
        int[] code = new int[length];
        for (int i = 0; i < length; i++) code[i] = origCode[i];
        int[] guess = new int[length];
        for (int i = 0; i < length; i++) guess[i] = origGuess[i];

        // determine black peg count
        int black = 0;
        for (int i = 0; i < length; i++) {
            if (code[i] == guess[i]) {
                black++;
                code[i] = -1;
                guess[i] = -1;
            }
        }

        // determine white peg count
        int white = 0;
        for (int i = 0; i < length; i++) {
            if (code[i] != -1) {
                for (int j = 0; j < length; j++) {
                    if (code[i] == guess[j]) {
                        white++;
                        guess[j] = -1;
                        break;
                    }
                }
            }
        }

        // determine validity of code
        if (white == origWhite && black == origBlack) return true;
        return false;
    }

    /**
     * Converts the contents of the trie to readable format.
     *
     * @return String representation of all paths of the trie
     */
    @Override
    public String toString() {
        String str = "";
        str = toStringHelper(root, str);
        return str.trim();
    }

    /**
     * Recursive helper function for converting trie to readable format.
     *
     * @param curr the current Node in the trie
     * @param str readable format of current traversal
     * @return String representation of all paths of the trie
     */
    public String toStringHelper(Node curr, String str) {
        // end of path
        if (curr.getDepth() == length - 1) {
            return str.trim() + "\n";
        }

        // set all children
        String out = "";
        for (Node child : curr.getChildren()) {
            if (child == null) continue;
            out += toStringHelper(child, str + child.getColor() + " ");
        }
        return out;
    }
}
