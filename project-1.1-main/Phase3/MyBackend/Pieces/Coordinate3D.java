package Phase3.MyBackend.Pieces;

/**
 * Represents a three-dimensional coordinate in space with x, y, and z components.
 */
public class Coordinate3D {
    // Fields to store x, y, and z coordinates
    public int x;
    public int y;
    public int z;

    /**
     * Default constructor for Coordinate3D with zero-initialized coordinates.
     */
    public Coordinate3D() {
    }

    /**
     * Copy constructor for Coordinate3D that creates a new instance with the same coordinates as the provided coordinate.
     * @param c The coordinate to copy.
     */
    public Coordinate3D(Coordinate3D c){
        x = c.x;
        y = c.y;
        z = c.z;
    }
    /**
     * Parameterized constructor for Coordinate3D with specified x, y, and z coordinates.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param z The z-coordinate.
     */
    public Coordinate3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Getter method for the x-coordinate.
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Setter method for the x-coordinate.
     * @param x The new value for the x-coordinate.
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter method for the y-coordinate.
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Setter method for the y-coordinate.
     * @param y The new value for the y-coordinate.
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Getter method for the z-coordinate.
     * @return The z-coordinate.
     */
    public int getZ() {
        return z;
    }

    /**
     * Setter method for the z-coordinate.
     * @param z The new value for the z-coordinate.
     */
    public void setZ(int z) {
        this.z = z;
    }
}
