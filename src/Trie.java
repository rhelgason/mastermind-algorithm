import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Class representing a trie data structure.
 */
public class Trie {
    private Node root;
    private int length = 4;
    private int numColors = 6;

    /**
     * Default constructor for Trie class.
     */
    public Trie() {
        root = new Node(-1, -1);
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

    public void removeGuesses(int white, int black) {
        removeGuessesHelper(root, white, black);
    }

    public void removeGuessesHelper(Node curr, int white, int black) {
        // base case
        if (curr == null) return;

        if (curr.getDepth() == length - 1) {
            // CHECK IF IT IS VALID
        }

        // check all child paths
        int i = 0;
        for (Node child : curr.getChildren()) {
            removeGuessesHelper(child, white, black);
            if (curr.getDepth() < length - 2 && child != null) curr.setChild(child.checkChildren(), i);
            i++;
        }
    }

    /**
     * Converts the contents of the trie to readable format.
     *
     * @return String representation of all paths of the trie
     */
    @Override
    public String toString() {
        String str = "";
        for (Node child : root.getChildren()) {
            str += toStringHelper(child, "");
        }
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
        // base case
        if (curr == null) {
            return "";
        }

        // end of path
        if (curr.getDepth() == length - 1) {
            return str + curr.getColor() + "\n";
        }

        // set all children
        str += curr.getColor() + " ";
        String out = "";
        for (Node child : curr.getChildren()) {
            out += toStringHelper(child, str);
        }
        return out;
    }
}
