package Phase3.MyBackend;

import Phase3.MyBackend.Pieces.Coordinate3D;
import Phase3.MyBackend.Pieces.Piece3D;

/**
 * Represents a container to hold 3D pieces.
 */
public class Container {
    // Fields
    int[][][] field;

    private int width;
    private int height;
    private int depth;
    // Constructors

    /**
     * Constructs a container with the specified dimensions.
     * @param depth The depth of the container.
     * @param height The height of the container.
     * @param width The width of the container.
     */
    public Container(int depth ,int height, int width) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.field = new int[depth][height][width];
        
    }

    /**
    * Copy constructor for the Container class.
    * 
    * @param container The Container object to be copied.
    */
    public Container(Container container){
        // Copying the dimensions of the container
        this.width = container.width;
        this.height= container.height;
        this.depth = container.depth;
        // Creating a new field with the same dimensions
        field = new int[depth][height][width];
        // Copying the values from the original container's field to the new field
        for (int i = 0; i < field.length; i++) 
            for (int j = 0; j < field[0].length; j++) 
                for(int k =0; k<field[0][0].length;k++)
                    this.field[i][j][k] = container.field[i][j][k];
    }


    /**
    * Constructor for the Container class that initializes the container with the provided three-dimensional array.
    * 
   * @param container A three-dimensional array representing the initial state of the container.
    */
    public Container(int[][][] container){
        // Setting the dimensions of the container based on the provided array
        this.depth = container.length;
        this.height= container[0].length;
        this.width = container[0][0].length;
        // Creating a new field with the same dimensions
        field = new int[depth][height][width];
        // Copying the values from the provided array to the container's field
        for (int i = 0; i < field.length; i++) 
            for (int j = 0; j < field[0].length; j++) 
                for(int k =0; k<field[0][0].length;k++)
                    this.field[i][j][k] = container[i][j][k];
    }
    
    /**
    * Empties the container by setting all values in the field to zero.
    */
    public void empty() {
        // Iterating through the dimensions of the container and setting all values to zero
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                for(int k = 0; k < field[0][0].length; k++){
                    field[i][j][k] = 0;
                }
            }
        }
    }
    
    /**
    * Removes a 3D piece from the container at the specified coordinates.
    *
    * @param piece3D The 3D piece to be removed.
    * @param c       The coordinates specifying the location in the container to remove the piece.
    */
    public void removePiece3D(Piece3D piece3D, Coordinate3D c) {
    
        // Extracting coordinates
        int x = c.getX();
        int y = c.getY();
        int z = c.getZ();

        // Getting the shape of the piece
        int[][][] shape = piece3D.getShape();

        // Iterating through the dimensions of the shape and updating the container
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    if (shape[i][j][k] == 1) {
                        // Removing the piece by setting the corresponding position in the container to zero
                        field[z + i][y + j][k + x] = 0;
                    }
                }
            }
        }

    }

    /**
    * Adds a 3D piece to the container at the current coordinates of the piece.
    *
    * @param piece3D The 3D piece to be added.
    */
    public void addPiece3D(Piece3D piece3D){
        // Extracting shape and coordinates from the piece
        int[][][] shape = piece3D.getShape();
        int x = piece3D.getCoordinate().x;
        int y = piece3D.getCoordinate().y;
        int z = piece3D.getCoordinate().z;
        // Iterating through the dimensions of the shape and updating the container
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    if (shape[i][j][k] == 1) {
                        // Adding the piece by setting the corresponding position in the container to the piece's ID
                        field[z + i][y + j][k + x] = piece3D.getId();
                    }
                }
            }
        }
    }

    /**
    * Adds a 3D piece to the container at the specified coordinates.
    *
    * @param piece3D The 3D piece to be added.
    * @param c       The coordinates to place the piece.
    * @return True if the piece is successfully added, false otherwise.
    */
    public boolean addPiece3D(Piece3D piece3D, Coordinate3D c) {
        
        // Check if the piece is placeable at the specified coordinates
        if(!isPlaceable(piece3D, c)) return false;
        // Extracting shape and coordinates from the piece
        int[][][] shape = piece3D.getShape();

        int x = c.getX();
        int y = c.getY();
        int z = c.getZ();

        // ID of the piece to be added to the container
        int id = piece3D.getId();
       
        // Iterating through the dimensions of the shape and updating the container
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    if (shape[i][j][k] == 1) {
                        // Adding the piece by setting the corresponding position in the container to the piece's ID
                        field[z + i][y + j][k + x] = id;
                    }
                }
            }
        }
        // Setting the new coordinates of the piece
        piece3D.setCoordinate(c);
        return true;
    }

    /**
    * Adds a 3D piece to the container at the specified coordinates.
    *
    * @param piece3D The 3D piece to be added.
    * @param x       The x-coordinate to place the piece.
    * @param y       The y-coordinate to place the piece.
    * @param z       The z-coordinate to place the piece.
    * @return True if the piece is successfully added, false otherwise.
    */
    public boolean addPiece3D(Piece3D piece3D, int x, int y, int z) {
        
        // Check if the piece is placeable at the specified coordinates
        if(!isPlaceable(piece3D, x,y,z)) return false;
        // Extracting shape from the piece
        int[][][] shape = piece3D.getShape();

        // ID of the piece to be added to the container
        int id = piece3D.getId();
       
        // Iterating through the dimensions of the shape and updating the container
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                for (int k = 0; k < shape[0][0].length; k++) {
                    if (shape[i][j][k] == 1) {
                        // Adding the piece by setting the corresponding position in the container to the piece's ID
                        field[z + i][y + j][k + x] = id;
                    }
                }
            }
        }
        // Setting the new coordinates of the piece
        piece3D.setCoordinate(new Coordinate3D(x,y,z));
        return true;
    }

    /**
    * Creates and returns a copy of the current field configuration.
    *
    * @return A deep copy of the current field configuration.
    */
    public int[][][] copyField() {
        // Creating a new array with the same dimensions as the field
        int[][][] copyField = new int[field.length][field[0].length][field[0][0].length];
        // Copying the values from the field to the new array
        for (int i = 0; i < field.length; i++) 
            for (int j = 0; j < field[0].length; j++) 
                for(int k =0; k<field[0][0].length;k++)
                    copyField[i][j][k] = field[i][j][k];
            
        return copyField;

    }

    /**
    * Returns the width of the container.
    *
    * @return The width of the container.
    */
    public int getWidth() {
        return width;
    }

    /**
    *  Sets the width of the container to the specified value.
    *
    * @param width The new width of the container.
    */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
    * Returns the height of the container.
    *
    * @return The height of the container.
    */
    public int getHeight() {
        return height;
    }

    /**
    * Sets the height of the container to the specified value.
    *
    * @param height The new height of the container.
    */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
    * Returns the depth of the container.
    *
    * @return The depth of the container.
    */
    public int getDepth() {
        return depth;
    }

    /**
    * Sets the depth of the container to the specified value.
    *
    * @param depth The new depth of the container.
    */
    public void setDepth(int depth) {
        this.depth = depth;
    }

    /**
    * Returns the 3D field array representing the container.
    *
    * @return The 3D field array representing the container.
    */
    public int[][][] getField() {
        return field;
    }

    /**
   * Sets the field array of the container to the specified 3D array.
    *
    * @param field The new 3D array to set as the field array.
    */
    public void setField(int[][][] field) {
        for (int i = 0; i < field.length; i++) 
            for (int j = 0; j < field[0].length; j++) 
                for(int k =0; k<field[0][0].length;k++)
                    this.field[i][j][k] = field[i][j][k];
        
    }

    /**
    * Creates and returns a copy of the container.
    *
    * @return A new Container object that is a copy of the current container.
    */
    public Container copy(){
        return new Container(this);
    }

    /**
    * Creates and returns a copy of the field array of the container.
    *
    * @return A new 3D array that is a copy of the field array.
    */
    public int[][][] getCopyField() {
        int[][][] copyField = new int[depth][height][width];
    
        for (int i = 0; i < field.length; i++) 
            for (int j = 0; j < field[0].length; j++) 
                for(int k =0; k<field[0][0].length;k++)
                    copyField[i][j][k] = field[i][j][k];
                
        return copyField;
    }

    /**
    * Checks if a given piece can be placed at the specified coordinates in the container.
    *
    * @param piece The 3D piece to be placed.
    * @param c The coordinates where the piece should be placed.
    * @return True if the piece can be placed at the specified coordinates, false otherwise.
    */
    public boolean isPlaceable(Piece3D piece, Coordinate3D c) {
        int[][][] shape = piece.getShape();
        int x = c.getX() , y = c.getY() , z= c.getZ();

        // Check if the piece will fit within the container dimensions
        if(z+shape.length>depth || y+shape[0].length>height || x+shape[0][0].length>width) {
            return false;
        }

        // Check if the piece can be placed without overlapping with existing occupied cells in the container
        for (int i = 0; i < shape.length; i++) { //Length
            for (int j = 0; j < shape[0].length; j++) { //Height
                for (int k = 0; k < shape[0][0].length; k++) { //Width
                    if (z + i < field.length && y + j < field[0].length && x + k < field[0][0].length) {
                        if (shape[i][j][k] == 1 && field[z + i][y + j][x + k] != 0) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
    * Checks if a given piece can be placed at the specified coordinates in the container.
    *
    * @param piece The 3D piece to be placed.
    * @param x The x-coordinate where the piece should be placed.
    * @param y The y-coordinate where the piece should be placed.
    * @param z The z-coordinate where the piece should be placed.
    * @return True if the piece can be placed at the specified coordinates, false otherwise.
    */
    public boolean isPlaceable(Piece3D piece, int x, int y, int z) {
        int[][][] shape = piece.getShape();
        
        // Check if the piece will fit within the container dimensions
        if(z+shape.length>depth || y+shape[0].length>height || x+shape[0][0].length>width) {
            return false;
        }

        // Check if the piece can be placed without overlapping with existing occupied cells in the container
        for (int i = 0; i < shape.length; i++) { //Length
            for (int j = 0; j < shape[0].length; j++) { //Height
                for (int k = 0; k < shape[0][0].length; k++) { //Width
                    if (z + i < field.length && y + j < field[0].length && x + k < field[0][0].length) {
                        if (shape[i][j][k] == 1 && field[z + i][y + j][x + k] != 0) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    int countA = 0, countB = 0, countC = 0, countL = 0, countP = 0, countT = 0;
   
    /**
    * Calculates the total value of occupied cells in the container based on the counts of different piece types.
    * Assumes that the counts represent the number of occurrences of different piece types in the container.
    *
   * @return The total value of the occupied cells in the container.
    */
    public int totalValue() {

        // Reset the counts to zero before recalculating
        countA = 0; countB = 0; countC = 0; countL = 0; countP = 0; countT = 0;
        // Iterate through each cell in the container
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                for (int j2 = 0; j2 < field[0][0].length; j2++) {

                    // Check if the cell is occupied
                    if(field[i][j][j2] == 0) 
                        continue;

                    else
                        // Update counts based on the type of the occupied piece
                        count(field[i][j][j2]);
                }
            }
        }

        // Normalize the counts based on the size of the corresponding pieces
        countA /= 16; countB /= 24 ;countC /= 27; countL /= 5; countP /= 5; countT /= 5;
        // Calculate the total value based on the weighted sum of counts
        int total = countA*3 + countB*4 + countC*5 + (countL*3)+(countP*4)+ countT*5;

        return total;
    }

    /**
    * Generates a string representation of the used pieces in the container, including the total value and counts of each piece type.
    *
    * @return A string representation of the used pieces in the container.
    */
    public String usedPieces(){
        // Calculate the total value of occupied cells
        int total = totalValue();
        // Create a formatted string with the total value and counts of each piece type
        String s = "              Total value = " + total +"\n\n"+ "              A = " + countA + ", B = "+ 
        countB+ ", C = "+ countC+ "\n\n              L = "+ countL+ ", P = "+ countP+ ", T = "+ countT;
         
        return s;
    }
    
    /**
    * Updates the counts of different piece types based on the provided piece ID.
    *
    * @param pieceId The ID representing the type of the piece.
    */
    private void count(int i){

        switch (i) {
                        case 1:
                            countA++;
                            break;
                        case 2:
                            countB++;
                            break;
                        case 3:
                            countC++;
                            break;
                        case 4:
                            countL++;
                            break;
                        case 5:
                            countP++;
                            break;
                        case 6:
                            countT++;
                            break;
        }
    }

    /**
    * Get the count of piece type A in the container.
    *
    * @return The count of piece type A.
    */
    public int getCountA() {
        return countA;
    }

    /**
    * Get the count of piece type B in the container.
    *
    * @return The count of piece type B.
    */
    public int getCountB() {
        return countB;
    }

    /**
    * Get the count of piece type C in the container.
    *
    * @return The count of piece type C.
    */
    public int getCountC() {
        return countC;
    }

    /**
    * Get the count of piece type L in the container.
    *
    * @return The count of piece type L.
    */
    public int getCountL() {
        return countL;
    }

    /**
    * Get the count of piece type P in the container.
    *
    * @return The count of piece type P.
    */
    public int getCountP() {
        return countP;
    }
    /**
    * Get the count of piece type T in the container.
    *
    * @return The count of piece type T.
    */ 
    public int getCountT() {
        return countT;
    }
}