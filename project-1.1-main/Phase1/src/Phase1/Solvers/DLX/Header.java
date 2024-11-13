package Phase1.src.Phase1.Solvers.DLX;



/**
 * The Header class represents a special type of node used in the Dancing Links (DLX) algorithm to
 * represent column headers in the exact cover problem. Each header corresponds to a column in the
 * matrix, and it maintains additional information about the column, such as its size and identifier.
 * 
 * @author Group 40
 * @version 1.0
 */
class Header extends Node {
    int size;
    int colID;

    /**
     * Constructs a Header object with default values.
     */
    Header() {
        super();
        this.header = this;
        this.size = 0;
        this.colID = -1;
    }

    /**
     * Constructs a Header object with a specified column identifier.
     *
     * @param id The identifier of the column.
     */
    public Header(int id) {
        super();
        this.header = this;
        this.size = 0;
        this.colID = id;
    }

    /**
     * Unlinks the header from the data structure, effectively removing the column.
     */
    public void unlink() {
        unlinkFromRow();
        for (Node i = this.down; i != this; i = i.down) {
            for (Node j = i.right; j != i; j = j.right) {
                j.unlinkFromColumn();
            }
        }
    }

    /**
     * Links the header back into the data structure, effectively restoring the column.
     */
    public void link() {
        for (Node i = this.up; i != this; i = i.up) {
            for (Node j = i.left; j != i; j = j.left) {
                j.relinkToColumn();
            }
        }
        relinkToRow();
    }
}
