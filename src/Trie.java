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
        if (depth == length) {
            curr.checkChildren();
            return curr;
        }

        // set all children
        List<Node> list = curr.getChildren();
        list = new ArrayList<Node>();
        for (int i = 0; i < numColors; i++) {
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

    public void toString2() {
        // initialize variables
        toString2Helper(root, 0, "");
    }

    public void toString2Helper(Node curr, int depth, String str) {
        // base case
        if (curr.getChildren() == null) {
            System.out.println(str + curr.getColor());
            return;
        }

        // set all children
        if (depth != 0) str += curr.getColor() + " ";
        for (Node child : curr.getChildren()) {
            toString2Helper(child, depth + 1, str);
        }
    }
}
