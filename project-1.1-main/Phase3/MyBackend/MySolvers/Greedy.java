package Phase3.MyBackend.MySolvers;

import Phase3.MyBackend.Container;
import Phase3.MyBackend.Pieces.Coordinate3D;
import Phase3.MyBackend.Pieces.Piece3D;
import Phase3.MyBackend.Pieces.Parceles.AParcel;
import Phase3.MyBackend.Pieces.Parceles.BParcel;
import Phase3.MyBackend.Pieces.Parceles.CParcel;
import Phase3.MyBackend.Pieces.Pentominos.LPent;
import Phase3.MyBackend.Pieces.Pentominos.PPent;
import Phase3.MyBackend.Pieces.Pentominos.TPent;

/**
 * The `Greedy` class implements the `ISolver` interface and represents a solver
 * using a greedy algorithm for solving 3D packing problems.
 * It performs recursive backtracking to find a solution by placing pieces in the container.
 * The sorting of parcels is done based on their values and availability.
 * 
 * The class includes methods for solving, recursive backtracking, getting a piece,
 * and creating a sorted array of parcels.
 * 
 * @author Group 40
 * @version 1.0
 */
public class Greedy implements ISolver {

    // Container to store the solution
    Container container = new Container(ISolver.c);
    // Total value of the solution
    public double totalValue = 0.0;
    // Amounts of parcels (Testing)
    private int[] parcelAmounts; // Testing
    // Parcel types sorted by value
    private char[] sorted = { 'A', 'B', 'C', 'L', 'P', 'T' };
    // Values corresponding to each parcel type
    private double[] values = new double[sorted.length];

    // Flag to indicate whether parcels are used
    boolean parcelsOn;

    /**
     * Constructs a `Greedy` solver based on the specified type.
     * 
     * @param type The type of problem (e.g., "P" for parcels).
     */
    public Greedy(String type) {
        // Defining pieces and parcels

        if(type.equalsIgnoreCase("P")){ 
            parcelsOn = true;
            parcelAmounts = new int[]{0, 85, 34, 0,0,0};
        }
        else{
            parcelAmounts = new int[]{ 0,0,0,  2, 237, 133};
        }
        
        // Defining the pieces and parcels on the values array
        values[0] = 3;
        values[1] = 4;
        values[2] = 5;
        values[3] = 3;
        values[4] = 4;
        values[5] = 5;
    }


    /**
     * Sets the amounts of parcels for testing.
     * 
     * @param parcelAmounts The amounts of each parcel type.
     */
    public void setParcelAmounts(int[] parcelAmounts) {
        for (int i = 0; i < parcelAmounts.length; i++) {
            this.parcelAmounts[i] = parcelAmounts[i];
        }
    }

    /**
     * Solves the packing problem using recursive backtracking.
     * 
     * @return `true` if the problem is solved.
     */
    @Override
    public boolean solve(){
        recursiveBacktracking();
        return true;
    }

    /**
    * Recursively explores possible solutions using backtracking.
    * Iterates through the container's space and attempts to place each piece,
    * considering available parcels and their values.
    */
    public void recursiveBacktracking() {

        // Create a sorted array of parcels based on values and availability
        char[] sorted = createSortedArray();
        // Iterate through the container's space
        for (int z= 0; z < container.getField().length; z++) {
            for (int y = 0; y < container.getField()[0].length; y++) {
                for (int x = 0; x < container.getField()[0][0].length; x++) {
                    // Skip already filled positions
                    if(container.getField()[z][y][x] != 0) continue;
                    // Iterate through the sorted array of parcel types
                    for (int l = 0; l < sorted.length; l++) {
                        // Skip if no more parcels of this type are available
                        if (parcelAmounts[l] == 0) {
                            continue;
                        }

                        // Get the piece corresponding to the current parcel type
                        Piece3D piece3d = getPiece(sorted[l]);
                        // Generate all possible mutations of the piece
                        int[][][][] allMutations = piece3d.allMutations();
                        for (int[][][] mutation : allMutations) {
                            piece3d.setShape(mutation);
                            // Attempt to add the piece to the container
                            if(container.addPiece3D(piece3d,new Coordinate3D(x,y,z))){
                                // Reduce the available amount of the current parcel type
                                parcelAmounts[l] -= 1;
                                
                                recursiveBacktracking(); // Recursively continue the backtracking process
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

        /**
        * Returns a new instance of a 3D piece based on the specified parcel type.
        *
        * @param ParcelType The type of parcel ('A', 'B', 'C', 'L', 'P', 'T').
        * @return A new instance of the corresponding 3D piece.
        *         Returns null if the parcel type is not recognized.
        */
        private Piece3D getPiece(char ParcelType) {
        // This method should return a new instance of the piece based on the type
        // Determine the type of 3D piece based on the specified parcel type
        switch (ParcelType) {
            case 'A':
                return new AParcel();
            case 'B':
                return new BParcel();
            case 'C':
                return new CParcel();
            case 'L':
                return new LPent();
            case 'P':
                return new PPent();
            case 'T':
                return new TPent();
            default:
                return null; // Return null for unrecognized parcel types
        }
    }

        /**
        * Creates a sorted array of parcel types based on their values.
        * The array is sorted in descending order, with the most valuable parcels first.
        *
        * @return A char array representing the sorted parcel types.
        */
        private char[] createSortedArray() {
            double[] valPerVol = values.clone(); // Clone the values array to avoid modifying the original
            char tempo;
            double temp;
            int temp2;
            for (int i = valPerVol.length - 1; i > 0; i--) { // Use a modified bubble sort to sort the array in descending order
                for (int j = 0; j < i; j++) {
                    if (valPerVol[j] < valPerVol[j + 1]) {
                        temp = valPerVol[j]; // Swap values, parcel types, and parcel amounts
                        tempo = sorted[j];
                        temp2 = parcelAmounts[j];
                        valPerVol[j] = valPerVol[j + 1];
                        sorted[j] = sorted[j + 1];
                        parcelAmounts[j] = parcelAmounts[j + 1];
                        valPerVol[j + 1] = temp;
                        sorted[j + 1] = tempo;
                        parcelAmounts[j + 1] = temp2;
                    }
                }
            }
            return sorted; // Return the sorted array
        }

    /**
    * Gets the container used by the Greedy solver.
    *
    * @return The container containing the placed pieces.
    */
    public Container getContainer() {
        return container;
    }
    
    }