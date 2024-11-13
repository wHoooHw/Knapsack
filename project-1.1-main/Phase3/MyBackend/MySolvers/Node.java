package Phase3.MyBackend.MySolvers;


/**
 * The Node class represents a basic node used in the Dancing Links (DLX) algorithm to construct the
 * doubly linked list data structure. Nodes are used to represent elements within the exact cover problem.
 * Each node can be linked both vertically and horizontally to form the DLX data structure.
 * 
 * @author Group 40
 * @version 1.0
 */
public class Node {
    Node up;
    Node down;
    Node right;
    Node left;
    Header header;
    int rowID;

    /**
     * Constructs a Node with default values.
     */
    Node() {
        up = left = right = down = this;
        rowID = -1;
    }

    /**
     * Constructs a Node with a specified row identifier.
     *
     * @param rowInput The identifier of the row.
     */
    public Node(int rowInput) {
        left = right = up = down = this;
        this.rowID = rowInput;
    }

    /**
     * Constructs a Node with a specified row identifier and a header.
     *
     * @param rowInput The identifier of the row.
     * @param h        The header to which this node belongs.
     */
        public Node(int rowInput, Header h) {
            this(rowInput);
            this.header = h;
        }

    /**
     * Unlinks the node from its row by adjusting the neighboring node references.
     */
    public void unlinkFromRow() {
        this.left.right = right;
        this.right.left = left;
    }

    /**
     * Relinks the node to its row by adjusting the neighboring node references.
     */
    public void relinkToRow() {
        this.left.right = this;
        this.right.left = this;
    }

    /**
     * Unlinks the node from its column by adjusting the neighboring node references and decreasing the
     * size of the column.
     */
    public void unlinkFromColumn() {
        this.up.down = down;
        this.down.up = up;
        header.size--;
    }

    /**
     * Relinks the node to its column by adjusting the neighboring node references and increasing the
     * size of the column.
     */
    public void relinkToColumn() {
        this.up.down = this;
        this.down.up = this;
        this.header.size++;
    }

    /**
     * Links the current node below another node in the vertical direction.
     *
     * @param node The node to link below the current node.
     */
    public void linkVertically(Node node) {
        node.down = this.down;
        node.up = this;
        node.down.up = node;
        this.down = node;
    }

    /**
     * Links the current node to the right of another node in the horizontal direction.
     *
     * @param node The node to link to the right of the current node.
     */
    public void linkHorizontally(Node node) {
        node.right = this.right;
        node.left = this;
        this.right.left = node;
        this.right = node;
    }
}
