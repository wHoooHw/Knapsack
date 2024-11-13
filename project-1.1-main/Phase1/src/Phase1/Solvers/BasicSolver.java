package Phase1.src.Phase1.Solvers;

import java.util.Random;

import Phase1.src.Phase1.DataConstructor.PentominoDatabase;

/**
 * This class extends the ISolver class and provides a basic implementation of a search algorithm
 * to solve a pentomino puzzle.
 */
public class BasicSolver extends ISolver{

     /**
     * Constructor for the BasicSolver class.
     *
     * @param WIDTH  The width of the board.
     * @param HEIGHT The height of the board.
     * @param INPUT  An array of characters representing the pentomino pieces.
     */
    public BasicSolver(int WIDTH,int HEIGHT ,char[] INPUT){
		super(WIDTH, HEIGHT, INPUT);
    }

	/**
     * The search method initiates the basic search algorithm to find a solution for the pentomino puzzle.
     *
     * @return true if a solution is found; otherwise, false.
     */
	@Override
    public boolean search(String solverName){
		// Initialize an empty board
        emptyBoard();

		// Check if the board can be covered by the pentomino pieces
		if(isBoardEnough()){
			System.out.println("Board is too small");
			return false;
		}

        // Start the basic search and return the result
            return basicSearch(field);
        }

	/**
	 * Basic implementation of a search algorithm. It is not a bruto force algorithms (it does not check all the posssible combinations)
	 * but randomly takes possible combinations and positions to find a possible solution.
	 * The solution is not necessarily the most efficient one
	 * This algorithm can be very time-consuming
	 * @param field a matrix representing the board to be fulfilled with pentominoes
	 */
    private boolean basicSearch(int[][] field){
    	Random random = new Random();
    	boolean solutionFound = false;
		if(isBoardEnough()){
			System.out.println("Board is too small");
			return false;
		}
    	
    	while (!solutionFound) {
    		solutionFound = true;
    		
    		//Empty board again to find a solution
			emptyBoard();
    		
    		//Put all pentominoes with random rotation/flipping on a random position on the board
    		for (int i = 0; i < INPUT.length; i++) {
    			
    			//Choose a pentomino and randomly rotate/flip it
    			int pentID = characterToID(INPUT[i]);
    			int mutation = random.nextInt(PentominoDatabase.data[pentID].length);
    			int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];
    		
    			//Randomly generate a position to put the pentomino on the board
    			int x;
    			int y;
    			if (WIDTH < pieceToPlace.length) {
    				//this particular rotation of the piece is too long for the field
    				x=-1;
    			} else if (WIDTH == pieceToPlace.length) {
    				//this particular rotation of the piece fits perfectly into the width of the field
    				x = 0;
    			} else {
    				//there are multiple possibilities where to place the piece without leaving the field
    				x = random.nextInt(WIDTH-pieceToPlace.length+1);
    			}

    			if (HEIGHT < pieceToPlace[0].length) {
    				//this particular rotation of the piece is too high for the field
    				y=-1;
    			} else if (HEIGHT == pieceToPlace[0].length) {
    				//this particular rotation of the piece fits perfectly into the height of the field
    				y = 0;
    			} else {
    				//there are multiple possibilities where to place the piece without leaving the field
    				y = random.nextInt(HEIGHT-pieceToPlace[0].length+1);
    			}
    		
    			//If there is a possibility to place the piece on the field, do it
    			if (x >= 0 && y >= 0 && !isPlaceable(pieceToPlace, pentID, x, y)) {
	    			addPiece(pieceToPlace, pentID, x, y);
	    		} 
    		}
    		//Check whether complete field is filled
    		solutionFound = isBoardFull();
    	
    		if (solutionFound) {
    			System.out.println("Solution found");
				return true;
    		}
    	}	
		return false;
    }
}