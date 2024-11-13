package Phase3.MyBackend.MySolvers;


import java.util.ArrayList;
import java.util.List;

import Phase3.MyBackend.Container;
import Phase3.MyBackend.Pieces.Piece3D;
import Phase3.MyBackend.Pieces.Parceles.AParcel;
import Phase3.MyBackend.Pieces.Parceles.BParcel;
import Phase3.MyBackend.Pieces.Parceles.CParcel;
import Phase3.MyBackend.Pieces.Pentominos.LPent;
import Phase3.MyBackend.Pieces.Pentominos.PPent;
import Phase3.MyBackend.Pieces.Pentominos.TPent;
import static Phase3.Data3D.MyData.*;


/**
 * The MatrixBuilder class is responsible for creating the matrix representation of the 3D packing problem.
 * It generates rows of the matrix based on the possible configurations of pieces in the container.
 */
public class MatrixBuilder {
    
    List<Piece3D> input; 
    Container container;
    
    /**
     * Constructs a MatrixBuilder object with the specified container and type.
     * 
     * @param container The container for the 3D packing problem.
     * @param type      The type of pieces (either "P" for parcels or "Pento" for pentominos).
     */
    public MatrixBuilder(Container container,String type){
       
        this.input = new ArrayList<>();
        if(!type.equalsIgnoreCase("P")){
            input.add(new TPent());
            input.add(new PPent()); 
            // input.add(new LPent());        
        }
        else{

            input.add(new AParcel());
            input.add(new BParcel());
            input.add(new CParcel());         
            
        }
        
        this.container= container;
    }

    /**
     * Generates the matrix rows based on the possible configurations of pieces in the container.
     * 
     * @param type The type of pieces (either "P" for parcels or "Pento" for pentominos).
     * @return A list of byte arrays representing rows in the matrix.
     */
    public static List<byte[]> matrix(String type) {
        List<byte[]> rows = new ArrayList<>();
        Container originalContainer = new Container(depth,height,width);
    
        // Iterate over each piece
        List<Piece3D> input = new ArrayList<>();
        if(!type.equalsIgnoreCase("P")){
            input.add(new TPent());
            input.add(new PPent());
        }
        else{
            input.add(new CParcel());
            input.add(new BParcel()); 
            input.add(new AParcel());
            
            
        }
        for (Piece3D piece : input) {
            // Iterate over each mutation of the piece
            for (int[][][] mutation : piece.getAllPossibilities()) {
                piece.setShape(mutation);
                // Iterate over each position in the container
                for (int z = 0; z < depth; z++) {
                    for (int y = 0; y < height; y++) {
                        for (int x = 0; x < width; x++) {
    
                            // Reset the container to its original state
                            // Attempt to add the piece to the container at the current position
                            if (originalContainer.addPiece3D(piece, x, y, z)) {
                                byte[] row = new byte[(width * height * depth)];
                                int index = 0;
                                
                                // Encode the state of the container in the row
                                for (int i = 0; i < depth; i++) {
                                    for (int j = 0; j < height; j++) {
                                        for (int k = 0; k < width; k++) {
                                            if(originalContainer.getField()[i][j][k] != 0)
                                                row[index] = (byte) piece.getId();
                                            
                                            index++;
                                        }
                                    }
                                }
                                rows.add(row);
                                originalContainer.empty();
                            }
                        }
                    }
                }
            }
        }  
        return rows;
    }
    
    /**
    * Converts a list of byte arrays to a 2D byte array matrix.
    * 
    * @param parcels The list of byte arrays representing rows in the matrix.
    * @return A 2D byte array matrix.
    */
    public static byte[][] to2dMatrix(List<byte[]> parcels){
        byte[][] matrix = new byte[parcels.size()][];
            for (int i = 0; i < matrix.length; i++) {
                matrix[i] = parcels.get(i); // Use clone to avoid modifying the original array
            }
            return matrix;
    }
    
    // Initialize lists of byte arrays representing parcels and pentominos
   public static List<byte[]> parcels = matrix("P");
   public static List<byte[]> pentominos = matrix("Pento");
   
   // Create copies of the original lists to avoid modification issues
   public static List<byte[]> parcelsCopy = new ArrayList<>(parcels);
   public static List<byte[]> pentominosCopy =new ArrayList<>(pentominos);

    // Convert the lists to 2D matrices
   public static byte[][] matrixParcels = to2dMatrix(parcels);
   public static byte[][] matrixPentominos = to2dMatrix(pentominos);
}