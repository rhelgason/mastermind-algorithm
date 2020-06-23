import java.lang.reflect.Array;
import java.util.List;
import java.util.ArrayList;

/**
 * Class representing a node representing a color for the game.
 */
public class Node {
    private int color;
    private int depth;
    private List<Node> children;

    /**
     * Constructor for the node.
     *
     * @param color the color for the node
     */
    public Node(int color, int depth) {
        this.color = color;
        this.depth = depth;
        children = new ArrayList<Node>();
    }

    public void setChild(int index, Node child) {
        children.set(index, child);
    }

    /**
     * Sets the children to null if the list is empty.
     */
    public Node checkChildren() {
        for (Node child : children) {
            if (child != null) {
                return this;
            }
        }
        return null;
    }

    /**
     * Gets the color.
     *
     * @return the color
     */
    public int getColor() {
        return color;
    }

    /**
     * Gets the depth.
     *
     * @return the depth
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Adds a child to the Node.
     *
     * @param child the child to add
     */
    public void addChild(Node child) {
        children.add(child);
    }

    /**
     * Gets the children.
     *
     * @return the children
     */
    public List<Node> getChildren() {
        return children;
    }
}