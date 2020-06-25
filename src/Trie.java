package src;

/**
 * Class representing a Trie data structure.
 */
public class Trie {
    private Node root;
    private int size;
    int length;
    int numColors;

    /**
     * Default constructor for Trie class.
     */
    public Trie(int length, int numColors) {
        this.length = length;
        this.numColors = numColors;
        root = new Node(-1, -1);
        size = (int) Math.pow(numColors, length);
        initHelper(root,0);
    }

    /**
     * Recursive helper function for creating the Trie.
     *
     * @param curr  the current Node in the Trie
     * @param depth the current depth in the Trie
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
     * Get the current number of patterns in the Trie.
     *
     * @return the size of the Trie
     */
    public int getSize() {
        return size;
    }

    /**
     * Get the root of the Trie.
     *
     * @return the root of the Trie
     */
    public Node getRoot() { return root; }

    public void getFirst(Node curr, int[] path) {
        // determine if pattern is possible or not
        if (curr.getDepth() == length - 1) {
            size--;
            return;
        }

        // check all child paths
        for (Node child : curr.getChildren()) {
            if (child == null) continue;
            path[child.getDepth()] = child.getColor();
            getFirst(child, path);
            break;
        }
    }

    /**
     * Recursive function for removing a single pattern from the Trie.
     *
     * @param curr the current Node in the Trie
     * @param path the pattern to be removed
     * @return Node to be set as child
     */
    public Node removePath(Node curr, int[] path) {
        // determine if pattern is possible or not
        if (curr.getDepth() == length - 1) {
            size--;
            return null;
        }

        // check all child paths
        int i = -1;
        for (Node child : curr.getChildren()) {
            i++;
            if (child == null) continue;
            if (child.getColor() == path[child.getDepth()]) {
                curr.setChild(i, removePath(child, path));
                if (curr.getDepth() < length - 2) curr.setChild(i, child.checkChildren());
            }
        }

        return curr;
    }

    /**
     * Recursive function for removing impossible guesses.
     *
     * @param curr  the current Node in the Trie
     * @param black the number of black response pegs
     * @param white the number of white response pegs
     * @param code  current pattern traversal of the Trie
     * @param guess the last guess made
     * @return Node to be set as child
     */
    public Node removeGuesses(Node curr, int black, int white, int[] code, int[] guess) {
        // determine if pattern is possible or not
        if (curr.getDepth() == length - 1) {
            if (valid(black, white, code, guess)) return curr;
            size--;
            return null;
        }

        // check all child paths
        int i = -1;
        for (Node child : curr.getChildren()) {
            i++;
            if (child == null) continue;
            code[child.getDepth()] = child.getColor();
            curr.setChild(i, removeGuesses(child, black, white, code, guess));
            if (curr.getDepth() < length - 2) curr.setChild(i, child.checkChildren());
        }

        return curr;
    }

    /**
     * Determines if a pattern is possible given the response pegs.
     *
     * @param origBlack the number of black response pegs
     * @param origWhite the number of white response pegs
     * @param origCode  possible color pattern
     * @param origGuess the last guess made
     * @return whether or not the pattern is possible
     */
    public boolean valid(int origBlack, int origWhite, int[] origCode, int[] origGuess) {
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
     * Choose the next guess by which will eliminate the most possibilities.
     *
     * @param curr      the current Node in the Trie
     * @param S         Trie representing all possible patterns
     * @param path      current pattern traversal of the Trie
     * @param guess     the guess that will be selected next
     * @param bestScore the most possible eliminated patterns
     * @return the most possible eliminated patterns
     */
    public int minimax(Node curr, Trie S, int[] path, int[] guess, int bestScore) {
        // determine if pattern is possible or not
        if (curr.getDepth() == length - 1) {
            int score = Integer.MAX_VALUE;
            for (int i = 0; i < length; i++) {
                for (int j = length - i; j >= 0; j--) {
                    // determine how many would be eliminated
                    int elim = S.numElim(S.getRoot(), i, j, new int[length], path);
                    if (elim < score) score = elim;
                }
            }

            // replace best score if possible
            if (score > bestScore) {
                bestScore = score;
                for (int i = 0; i < path.length; i++) guess[i] = path[i];
            }
            return bestScore;
        }

        // check all child paths
        for (Node child : curr.getChildren()) {
            if (child == null) continue;
            path[child.getDepth()] = child.getColor();
            bestScore = minimax(child, S, path, guess, bestScore);
        }
        return bestScore;
    }

    /**
     * Calculate how many possibilities can be eliminated from a specific guess and response.
     *
     * @param curr  the current Node in the Trie
     * @param black the number of black response pegs
     * @param white the number of white response pegs
     * @param code  current pattern traversal of the Trie
     * @param guess the potential guess being examined
     * @return the number of possibilities eliminated
     */
    public int numElim(Node curr, int black, int white, int[] code, int[] guess) {
        // determine if pattern is possible or not
        if (curr.getDepth() == length - 1) {
            if (valid(black, white, code, guess)) return 0;
            return 1;
        }

        // check all child paths
        int count = 0;
        for (Node child : curr.getChildren()) {
            if (child == null) continue;
            code[child.getDepth()] = child.getColor();
            count += numElim(child, black, white, code, guess);
        }
        return count;
    }

    /**
     * Converts the contents of the Trie to readable format.
     *
     * @return String representation of all paths of the Trie
     */
    @Override
    public String toString() {
        String str = "";
        str = toStringHelper(root, str);
        return str.trim();
    }

    /**
     * Recursive helper function for converting Trie to readable format.
     *
     * @param curr the current Node in the Trie
     * @param str readable format of current traversal
     * @return String representation of all paths of the Trie
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
