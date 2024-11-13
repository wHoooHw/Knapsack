package Phase3.MyBackend.Pieces;


/**
 * Abstract base class representing a three-dimensional piece. This class provides a common structure for
 * different types of 3D pieces such as parcels and pentominos.
 */
public abstract class Piece3D {

// Fields to store the shape, identifier, name, coordinates, value, and all possible mutations of the piece
protected int[][][] shape;
private int id;
private char name;
private Coordinate3D coordinate;
private int value;
int[][][][] allPossibilities;

/**
* Parameterized constructor for Piece3D.
* @param shape The shape of the piece.
* @param id The identifier of the piece.
* @param name The name (char) of the piece.
* @param coordinate The three-dimensional coordinates of the piece.
* @param value The value of the piece.
*/
public Piece3D(int[][][] shape, int id, char name, Coordinate3D coordinate,int value) {
    // Initialize fields with provided values
    this.shape = shape;
    this.id = id;
    this.name = name;
    this.coordinate = coordinate;
    this.value = value;
    // Calculate all possible mutations
    allPossibilities = allMutations();
}

/**
* Copy constructor for Piece3D that creates a new instance with the same properties as the provided piece.
* @param piece The piece to copy.
*/
public Piece3D(Piece3D piece) {
    // Create a deep copy of the shape array
    this.shape = new int[piece.shape.length][piece.shape[0].length][piece.shape[0][0].length];
    for (int i = 0; i < piece.shape.length; i++) {
        for (int j = 0; j < piece.shape[0].length; j++) {
            for (int k = 0; k < shape[0][0].length; k++) {
                this.shape[i][j][k] = piece.shape[i][j][k];
            }
            
        }
    }
    // Copy other properties
    this.id = piece.id;
    this.name = piece.name;
    this.coordinate = new Coordinate3D(piece.coordinate.x, piece.coordinate.y,piece.coordinate.z);
    // Calculate all possible mutations
    allPossibilities = allMutations();
}

/**
* Abstract method to create a copy of the current piece.
* @return A new instance of the piece with the same properties.
*/
public abstract Piece3D copy();

/**
* Abstract method to calculate all possible mutations of the piece.
* @return A 4D array representing all possible mutations.
*/
public abstract int[][][][] allMutations();

/**
* Getter method for retrieving all possible mutations of the piece.
* @return A 4D array representing all possible mutations.
*/
public int[][][][] getAllPossibilities() {
    return allPossibilities;
}
/**
* Getter method for retrieving the value of the piece.
* @return The value of the piece.
*/
public int getValue() {
    return value;
}
/**
* Setter method for updating the value of the piece.
* @param value The new value for the piece.
*/
public void setValue(int value) {
    this.value = value;
}
/**
* Getter method for retrieving the shape of the piece.
* @return The shape of the piece.
*/
public int[][][] getShape() {
    return shape;
}

/**
* Setter method for updating the shape of the piece.
* @param shape The new shape for the piece.
*/
public void setShape(int[][][] shape) {
    // Create a deep copy of the shape array
    this.shape = new int[shape.length][shape[0].length][shape[0][0].length];
    for (int i = 0; i < shape.length; i++) {
        for (int j = 0; j < shape[0].length; j++) {
            for (int k = 0; k < shape[0][0].length; k++) {
                this.shape[i][j][k] = shape[i][j][k];
            }
        }
    }
}
/**
* Method to rotate the given parcel array around the X-axis.
* @param parcelArray The parcel array to be rotated.
* @return The rotated parcel array.
*/
public int[][][] rotateX(int[][][] parcelArray) {
    // Rotate the parcel array around the X-axis
    int[][][] rotateArray = new int[parcelArray.length][parcelArray[0][0].length][parcelArray[0].length];
    for (int i = 0; i < rotateArray.length; i++) {
        for (int j = 0; j < rotateArray[0].length; j++) {
            for (int k = 0; k < rotateArray[0][0].length; k++) {
                rotateArray[i][j][k] = parcelArray[i][rotateArray[0][0].length - 1 - k][j];
            }
        }
    }
    return rotateArray;
}

/**
* Method to rotate the given parcel array around the Y-axis.
* @param parcelArray The parcel array to be rotated.
* @return The rotated parcel array.
*/
public int[][][] rotateY(int[][][] parcelArray) {
    // Rotate the parcel array around the Y-axis
    int[][][] rotateArray = new int[parcelArray[0].length][parcelArray.length][parcelArray[0][0].length];
    for (int i = 0; i < rotateArray.length; i++) {
        for (int j = 0; j < rotateArray[0].length; j++) {
            for (int k = 0; k < rotateArray[0][0].length; k++) {
                rotateArray[i][j][k] = parcelArray[j][rotateArray.length - 1 - i][k];
            }
        }
    }
    return rotateArray;
}

/**
 * Method to rotate the given parcel array around the Z-axis.
 * @param parcelArray The parcel array to be rotated.
 * @return The rotated parcel array.
 */
public int[][][] rotateZ(int[][][] parcelArray) {
    // Rotate the parcel array around the Z-axis
    int[][][] rotateArray = new int[parcelArray[0][0].length][parcelArray[0].length][parcelArray.length];
    for (int i = 0; i < rotateArray.length; i++) {
        for (int j = 0; j < rotateArray[0].length; j++) {
            for (int k = 0; k < rotateArray[0][0].length; k++) {
                rotateArray[i][j][k] = parcelArray[k][j][rotateArray.length - 1 - i];
            }
        }
    }
    return rotateArray;
}

/**
 * Getter method for retrieving the identifier of the piece.
 * @return The identifier of the piece.
 */
public int getId() {
    return id;
}

/**
 * Setter method for updating the identifier of the piece.
 * @param id The new identifier for the piece.
 */
public void setId(int id) {
    this.id = id;
}

/**
 * Getter method for retrieving the name of the piece.
 * @return The name of the piece.
 */
public char getName() {
    return name;
}

/**
 * Setter method for updating the name of the piece.
 * @param name The new name for the piece.
 */
public void setName(char name) {
    this.name = name;
}

/**
 * Getter method for retrieving the coordinates of the piece.
 * @return The coordinates of the piece.
 */
public Coordinate3D getCoordinate() {
    return coordinate;
}

/**
 * Setter method for updating the coordinates of the piece.
 * @param coordinate The new coordinates for the piece.
 */
public void setCoordinate(Coordinate3D coordinate) {
    this.coordinate = new Coordinate3D(coordinate);
}

/**
 * Override of the toString method to provide a string representation of the piece (using its name).
 * @return A string representation of the piece.
 */
@Override
public String toString() {
    return name+"";
}

    /**
    * Override of the hashCode method.
    * @return The hash code of the object.
    */
    @Override
    public int hashCode() {
        
        // Delegate to the default hashCode implementation
        return super.hashCode();
    }     
}