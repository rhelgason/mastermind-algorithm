import java.util.List;
import java.util.ArrayList;

/**
 * Class representing a color placement for the game.
 */
public class Node {
    private int color;
    private int depth;
    private List<Node> children;

    /**
     * Constructor for the Node
     *
     * @param color the color for the Node
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
     * Determines if all children are defunct.
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