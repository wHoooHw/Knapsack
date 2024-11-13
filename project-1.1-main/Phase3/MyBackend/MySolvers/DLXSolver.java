package Phase3.MyBackend.MySolvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import Phase3.MyBackend.Container;
import static Phase3.Data3D.MyData.*;

/**
 * The DLXSolver class represents a solver for a specific problem using the Dancing Links (DLX) algorithm.
 * It extends the ISolver class and implements the algorithm to solve the exact cover problem using DLX.
 * 
 * The DLXSolver is designed to work with a matrix where each column represents a constraint, and each row
 * represents a potential solution. The goal is to find a combination of rows that satisfies all constraints
 * exactly once.
 * 
 * @author Group 40
 * @version 1.0
 */
public class DLXSolver{
    
    byte[][] matrix; // The problem matrix
    ArrayList<Header> headers; // List of headers representing constraints
    int[][][] container; // 3D array representing the container
    
    Header firstHeader = new Header(); // First header in the linked list
    Map<Integer,int[][][]> solutions; // Map to store solutions and their values
    MatrixBuilder matrixBuilder; // MatrixBuilder instance
    List<Integer> bestSolution; // List to store the best solution
    String type; // Type of the problem (Parcels or Pentominos)
    long startTime = System.currentTimeMillis(); // Start time for tracking execution time

    /**
     * Constructs a DLXSolver for the specified type (Parcels or Pentominos).
     *
     * @param type The type of the problem (Parcels or Pentominos).
     */
    public DLXSolver(String type) {
        this.type = type;
        headers = new ArrayList<>();
        solutions = new HashMap<>();
        // Initialize the matrix based on the type
        if(type.equals("P"))  matrix = MatrixBuilder.matrixParcels;
        else matrix = MatrixBuilder.matrixPentominos;
        bestSolution = new ArrayList<>();
        createDLXStructure();
    }

    /**
     * Constructs a DLXSolver for the specified type and specific row indexes.
     *
     * @param type    The type of the problem (Parcels or Pentominos).
     * @param indexes List of row indexes to create a modified matrix.
     */
    public DLXSolver(String type,List<Integer> indexes){
        this.type = type;
        headers = new ArrayList<>();
        solutions = new HashMap<>();
        // Modify the matrix based on the specified indexes
        if(type.equals("P")){
            // For Parcels, rearrange rows in the matrix based on the provided indexes
            for (int i = 0; i < MatrixBuilder.parcels.size(); i++) {
                Collections.swap(MatrixBuilder.parcels,i,indexes.get(i));
            }
            // Convert the modified Parcels matrix to a 2D matrix representation
            matrix = MatrixBuilder.to2dMatrix(MatrixBuilder.parcels);
            // Restore the original order of Parcels for future use
            MatrixBuilder.parcels = new ArrayList<>(MatrixBuilder.parcelsCopy);
        }
         
        else{
            // For Pentominos, similar process as Parcels
            for (int i = 0; i < MatrixBuilder.pentominos.size(); i++) {
                Collections.swap(MatrixBuilder.pentominos,i,indexes.get(i));
            }
            matrix = MatrixBuilder.to2dMatrix(MatrixBuilder.pentominos);
            MatrixBuilder.pentominos = new ArrayList<>(MatrixBuilder.pentominosCopy);
        } 

        // Initialize an empty list to store the best solution
        bestSolution = new ArrayList<>();
        // Create the DLX data structure based on the modified matrix
        createDLXStructure();
    }


    /**
    * Creates the Dancing Links (DLX) data structure to represent the problem matrix.
    * The DLX structure is designed to facilitate efficient exact cover problem solving.
    * The matrix is assumed to be in a sparse representation where each column represents a constraint,
    * and each row represents a potential solution.
    */
    private void createDLXStructure() {
        // Initialize the firstHeader as the leftmost header in the DLX structure
        Header prevHeader = firstHeader;
        // Create headers for each column in the matrix
        for (int i = 0; i < matrix[0].length; i++) {
            // Create a new header with a unique identifier
            Header nextheader = new Header(i);
            // Add the new header to the list of headers
            headers.add(i, nextheader);
            // Link the new header horizontally to the previous header
            prevHeader.linkHorizontally(nextheader);
            // Update the previous header to the newly created header
            prevHeader = nextheader;
        }
        // Iterate over each row in the matrix
        for (int i = 0; i < matrix.length; i++) {
            // Initialize a reference to the previous node in the current row
            Node prevNode = null;
            // Iterate over each column in the matrix
            for (int j = 0; j < matrix[0].length; j++) {
                // Check if the matrix element at the current position is non-zero (constraint is present)
                if (matrix[i][j] != 0) {
                    // Retrieve the header corresponding to the current column
                    Header header = headers.get(j);
                    // Create a new node for the current element in the matrix
                    Node node = new Node(i, header);
                    // Increase the size of the header (number of nodes in the column)
                    header.size++;
                    // If this is the first node in the current row, update prevNode
                    if (prevNode == null) {
                        prevNode = node;
                    }
                    // Link the new node vertically to the node above it in the same column
                    header.up.linkVertically(node);
                    // Link the new node horizontally to the previous node in the current row
                    prevNode.linkHorizontally(node);
                }
            }
        }
    }

    /**
    * Recursively solves the exact cover problem using the Dancing Links (DLX) algorithm.
    * The method explores all possible combinations of rows to find a solution that satisfies all constraints.
    * It uses a backtracking approach by selecting the column with the fewest remaining options to explore first.
    * If a solution is found, it updates the 'bestSolution' list with the indices of selected rows.
    * The method stops exploring if a time limit is reached to prevent excessive computation.
    */
    private void solveBest() {
        // Base case: If there are no more columns to explore, a solution is found
        if (firstHeader.right == firstHeader) {
                // Record the current combination of rows as the best solution
                getFinalField(bestSolution);
            return;
        }

        // Select the header with the minimum remaining options (fewest nodes in the column)
        Header h = getMinHeader();
        // Temporarily remove the selected header from the DLX structure
        h.unlink();
        // Explore each row in the selected column
        for (Node i = h.down; i != h; i = i.down) {
            // Include the current row in the solution
            bestSolution.add(i.rowID);
            // Remove nodes in the same row from other columns to maintain exact cover
            for (Node j = i.right; j != i; j = j.right) {
                j.header.unlink();
            }
            solveBest(); // Recursively explore further combinations
            // Check if the time limit is reached
            if (System.currentTimeMillis() - startTime >= 1*1000L) {
                return;
            } else {
                // System.out.print("\rloading...");
            }
            // Backtrack: Undo the changes made for the current row
            bestSolution.remove(bestSolution.size() - 1);
            for (Node k = i.left; k != i; k = k.left) {
                k.header.link();
            }
        }
        h.link(); // Restore the selected header to the DLX structure for other recursive calls
    }

    /**
    * Recursively solves the exact cover problem for parcel packing using the Dancing Links (DLX) algorithm.
    * The method explores all possible combinations of parcels to find a solution that satisfies all constraints.
    * It uses a backtracking approach by selecting the column with the fewest remaining options to explore first.
    * If a solution is found, it updates the 'bestSolution' list with the indices of selected parcels.
    * The method stops exploring if a time limit is reached to prevent excessive computation.
    */
    public void solveBestParcel() {
        
        getFinalField(bestSolution);
        // Select the header with the minimum remaining options (fewest nodes in the column)
        Header h = getMinHeader();
        h.unlink(); // Temporarily remove the selected header from the DLX structure
        for (Node i = h.down; i != h; i = i.down) { // Explore each row in the selected column
            bestSolution.add(i.rowID); // Include the current row (parcel) in the solution
            for (Node j = i.right; j != i; j = j.right) { // Remove nodes in the same row from other columns to maintain exact cover
                j.header.unlink();
            }
            solveBestParcel(); // Recursively explore further combinations
            // Check if the time limit is reached
            if (System.currentTimeMillis() - startTime >= 1*700L) {
                // If the time limit is reached, record the current solution and return
                getFinalField(bestSolution);
                return;
            } 
            
            // Backtrack: Undo the changes made for the current parcel
            bestSolution.remove(bestSolution.size() - 1);
            for (Node k = i.left; k != i; k = k.left) {
                k.header.link();
            }
        }
        h.link(); // Restore the selected header to the DLX structure for other recursive calls
    }
    

    int[][][] finalContainer;

    /**
    * Constructs the final container representing the solution based on the selected rows (parcels).
    * Converts the matrix rows corresponding to the indices in the 'solution' list into the final container.
    * The final container is a three-dimensional array representing the placement of parcels in space.
    * The method calculates the total value of the solution and stores it along with the container in the 'solutions' map.
    * 
    * @param solution A list of indices representing the selected rows (parcels) in the DLX matrix.
    */
    private void getFinalField(List<Integer> solution) {
        // Create a new three-dimensional array to represent the final container
        finalContainer = new int[depth][height][width];

        // Iterate through the selected rows in the DLX matrix
        for (Integer integer : solution) {
            // Retrieve the corresponding row in the matrix
            byte[] row = matrix[integer];
            // Convert the matrix row to the final container representation
            rowToField(row);
        }
        // Calculate the total value of the solution based on the final container
        int val =totalValue(finalContainer);
        // Store the total value and the final container in the 'solutions' map
        solutions.put(val, finalContainer);
    }

    /**
     * Converts a row from the DLX matrix to the final container representation.
    * Iterates through the elements of the row and populates the corresponding positions in the three-dimensional
    * final container only if the value in the matrix row is not zero.
    * 
    * @param row A byte array representing a row from the DLX matrix.
    */
    private void rowToField(byte[] row) {

        int index = 0; // Initialize an index to traverse the elements in the matrix row
        
        // Iterate through the dimensions of the final container
        for (int i = 0; i < finalContainer.length; i++) {
            for (int j = 0; j < finalContainer[0].length; j++) {
                for (int k = 0; k < finalContainer[0][0].length; k++) {
                    // Check if the value in the matrix row is not zero
                    if (row[index] != 0){
                        finalContainer[i][j][k] = row[index]; // Populate the corresponding position in the final container
                    }
                    index++; // Move to the next element in the matrix row
                }
            }
        }
    }

    /**
    * Finds and returns the header with the minimum size among the headers in the DLX structure.
    * Iterates through the headers, comparing their sizes, and keeps track of the header with the smallest size.
    * 
    *  @return The header with the minimum size.
    */
    private Header getMinHeader() {
        // Initialize variables to track the minimum size and the corresponding header
        int minValue = Integer.MAX_VALUE;
        Header minHeader = null;
        // Iterate through the headers in the DLX structure
        for (Header i = (Header) firstHeader.right; i != firstHeader; i = (Header) i.right) {
            // Compare the size of the current header with the minimum size
            if (i.size < minValue) {
                // Update the minimum size and the corresponding header
                minHeader = i;
                minValue = i.size;
            }
            // If the minimum size becomes zero, no need to continue searching
            if (minValue == 0) {
                return minHeader;
            }
        }
        return minHeader; // Return the header with the minimum size
    }

   
    /**
    * Initiates the solving process for the DLX structure based on the specified type.
    * Calls either the 'solveBestParcel' method or 'solveBest' method depending on the value of 'type'.
    * 
    * @return Always returns false.
    */
    public boolean solve() {
        // Check the type of the DLX structure
        if(type.equals("P")) 
            // If it's a Parcel type, invoke the method specialized for solving Parcel problems
            solveBestParcel();
        else
            // Otherwise, invoke the general solving method
            solveBest();

        return false; // The method always returns false
    }


    /**
    * Retrieves the final solution field represented by the DLX structure.
    *
   * @return A 3D array representing the final solution.
    */
    public int[][][] getSolution() {

       return finalContainer;
    }

    // Count variables for different types of pieces
    int countA = 0, countB = 0, countC = 0, countL = 0, countP = 0, countT = 0;

    /**
    * Calculates the total value of the solution based on the counts of different types of pieces.
    * Adjusts counts based on piece sizes and multipliers.
    *
    * @param finalContainer The final solution field.
    * @return The total value of the solution.
    */
    public int totalValue(int[][][] finalContainer) {

        // Reset piece counts
        countA = 0; countB = 0; countC = 0; countL = 0; countP = 0; countT = 0;
        // Iterate through the solution field
        for (int i = 0; i < finalContainer.length; i++) {
            for (int j = 0; j < finalContainer[0].length; j++) {
                for (int j2 = 0; j2 < finalContainer[0][0].length; j2++) {
                    // Check if the cell is occupied by a piece

                    if(finalContainer[i][j][j2] == 0) 
                        continue; // Skip empty cells

                    else
                        count(finalContainer[i][j][j2]); // Update counts based on the type of piece
                }
            }
        }
        // Normalize counts based on piece sizes
        countA /= 16; countB /= 24 ;countC /= 27; countL /= 5; countP /= 5; countT /= 5;
        // Calculate the total value based on adjusted counts and piece multipliers
        int total = countA*3 + countB*4 + countC*5 + (countL*3)+(countP*4)+ countT*5;
        return total;
    }

    
    /**
    * Helper method to update counts based on the type of piece.
    *
    * @param i The type of piece.
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
    * Retrieves the best solution from the stored solutions based on the highest total value.
    * Creates a Container object with the best solution and prints the pieces used.
    *
    * @return The best solution represented by a 3D array.
    */
    public int[][][] bestSolution(){
        int i = 0;
        // Iterate through stored solutions to find the one with the highest total value
        for (Integer value : solutions.keySet()) {
            
            if(value> i) i = value;
        }
        bestValue = i;
        
        // Create a Container object with the best solution
        container1 = new Container(solutions.get(i));
        return solutions.get(i);
    }
    // Container object to store the best solution
    Container container1;

    /**
    * Retrieves the textual representation of the pieces used in the best solution.
    *
    * @return A string containing the pieces used in the best solution.
    */
    public String getText(){
        return container1.usedPieces();
    }

    /*
    * Retrieves the best total value from the stored solutions.
    * Clears the solutions map and puts the best solution back with its total value.
    *
    * @return The best total value.
    */
    public int getBestValue() {
        int i = 0;
        // Iterate through stored solutions to find the highest total value
        for (Integer value : solutions.keySet()) {
            
            if(value> i) i = value;
        }
        // Get the best solution from the stored solutions
        int[][][] bestSolution = solutions.get(i);
        bestValue = i;
        // Clear the solutions map and put the best solution back with its total value
        solutions.clear();
        solutions.put(bestValue, bestSolution);
        
        return bestValue;
    }
    // Variable to store the best total value
    int bestValue;
}