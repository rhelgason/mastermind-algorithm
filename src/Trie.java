import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Class representing a trie data structure.
 */
public class Trie {
    private Node root;

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
        if (depth == 4) {
            curr.checkChildren();
            return curr;
        }

        // set all children
        List<Node> list = curr.getChildren();
        list = new ArrayList<Node>();
        for (int i = 1; i <= 6; i++) {
            Node temp = new Node(i, depth + 1);
            initHelper(temp, depth + 1);
            curr.addChild(temp);
        }
        return curr;
    }

    @Override
    public String toString() {
        // initialize variables
        String str = "";
        int depth = -1;
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);

        // breadth traversal of trie
        while (!queue.isEmpty()) {
            // recognize end of level
            Node curr = queue.remove();
            if (curr.getDepth() > depth) {
                str += "\n";
                depth = curr.getDepth();
            }

            // add children to queue
            if (curr.getChildren() == null) {
                continue;
            }
            for (Node child : curr.getChildren()) {
                str += child.getColor() + " ";
                queue.add(child);
            }
            str += "     ";
        }

        return str;
    }
}
