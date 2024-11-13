package Phase1.src.Phase1.Solvers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import Phase1.src.Phase1.DataConstructor.PentominoDatabase;

/**
 * An abstract base class that serves as a template for implementing various pentomino puzzle solvers.
 */
public abstract class ISolver {
    final int WIDTH;
    final int HEIGHT;
    final char[] INPUT;
    
    protected int[][] field;
    Map<Character,Integer> map;
	int[] pentIDs;

    /**
     * Constructor for the ISolver class.
     *
     * @param WIDTH  The width of the board.
     * @param HEIGHT The height of the board.
     * @param INPUT  An array of characters representing the pentomino pieces.
     */
    protected ISolver(final int WIDTH,final int HEIGHT ,final char[] INPUT){

        this.WIDTH = WIDTH;
        this.HEIGHT= HEIGHT;
        this.INPUT = INPUT;
		// int blockSize = (WIDTH>12&&HEIGHT>7)?20:50;
        
        field = new int[WIDTH][HEIGHT];
        map =new HashMap<>();
		pentIDs = new int[INPUT.length];

		// Initialize the map for character-to-ID conversion and pentomino IDs.
        for (int i = 0; i < INPUT.length; i++) {
			int pentID = characterToID(INPUT[i]);
			pentIDs[i] = pentID;
            map.put(INPUT[i],pentID);
        }

    }

	/**
	 * Empties the board by setting all cells to -1, indicating no pentomino is placed.
 	*/
    protected void emptyBoard() {
		for (int i = 0; i < WIDTH; i++) {
        	// Loop through each row in the board
			Arrays.fill(field[i],-1);
		}
	}

	/**
 	* Creates a deep copy of the current game board.
 	*
 	* @return A new two-dimensional array representing a copy of the game board.
 	*/
	protected int[][] copyBoard(){
		int[][] s = new int[WIDTH][field[0].length];
		for (int i = 0; i < s.length; i++) {
			// Create a copy of each row in the game board to ensure data independence.
			s[i] = Arrays.copyOf(field[i], HEIGHT);
		}
		return s;
	}

    /**
	 * Get as INPUT the character representation of a pentomino and translate it into its corresponding numerical value (ID)
	 * @param character a character representating a pentomino
	 * @return	the corresponding ID (numerical value)
	 */
    protected int characterToID(char character) {
    	int pentID = -1; 
    	if (character == 'X') {
    		pentID = 0;
    	} else if (character == 'I') {
    		pentID = 1;
    	} else if (character == 'Z') {
    		pentID = 2;
    	} else if (character == 'T') {
    		pentID = 3;
    	} else if (character == 'U') {
    		pentID = 4;
     	} else if (character == 'V') {
     		pentID = 5;
     	} else if (character == 'W') {
     		pentID = 6;
     	} else if (character == 'Y') {
     		pentID = 7;
    	} else if (character == 'L') {
    		pentID = 8;
    	} else if (character == 'P') {
    		pentID = 9;
    	} else if (character == 'N') {
    		pentID = 10;
    	} else if (character == 'F') {
    		pentID = 11;
    	} 
    	return pentID;
    }

    /**
 	* Adds a pentomino piece to the board at the specified position.
 	*
 	* @param piece   The pentomino piece to be added, represented as a 2D array.
 	* @param pieceID The numerical ID of the pentomino piece.
 	* @param x       The x-coordinate on the board where the piece will be added.
 	* @param y       The y-coordinate on the board where the piece will be added.
 	*/
    protected void addPiece(int[][] piece, int pieceID, int x, int y)
    {
			for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
        	{
        	    for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
        	    {
        	        if (piece[i][j] == 1)
        	        {
        	            // Add the ID of the pentomino to the board if the pentomino occupies this square
        	            field[x + i][y + j] = pieceID;
        	        }
        	    }
        	}
	}

	/**
 	* Adds a pentomino piece to the specified field with the given position and piece ID.
 	*
 	* @param field   The target field where the pentomino will be added.
 	* @param piece   The pentomino piece to be added.
 	* @param pieceID The ID of the pentomino.
 	* @param x       The x-coordinate of the position where the pentomino should be added.
 	* @param y       The y-coordinate of the position where the pentomino should be added.
 	* @return A new field with the pentomino added, leaving the original field unchanged.
 	*/
	protected int[][] addPiece(int[][] field,int[][] piece, int pieceID, int x, int y)
    {
			
			for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
        	{
        	    for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
        	    {
        	        if (piece[i][j] == 1)
        	        {
        	            // Add the ID of the pentomino to the board if the pentomino occupies this square
        	            field[x + i][y + j] = pieceID;
        	        }
        	    }
        	}
			return field;
	}

	/**
 	* Removes a pentomino piece from the board by resetting its cells to -1.
 	*
 	* @param piece    The pentomino piece to be removed from the board.
 	* @param pieceID  The numerical ID of the pentomino piece.
 	* @param x        The x-coordinate of the starting position for the piece.
 	* @param y        The y-coordinate of the starting position for the piece.
 	*/
	public void removePiece(int[][] piece, int pieceID, int x, int y)
    {
			for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
        	{
        	    for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
        	    {
        	        if (piece[i][j] == 1)
        	        {
        	            // Add the ID of the pentomino to the board if the pentomino occupies this square
        	            field[x + i][y + j] = -1;
        	        }
        	    }
        	}
    }

	/**	
 	* Checks if the entire game board is completely filled with pentomino pieces.
 	*
 	* @return True if the entire board is filled, otherwise false.
 	*/
    protected boolean isBoardFull() {
		
		for (int i = 0; i < WIDTH; i++) {
			for (int j = 0; j < HEIGHT; j++) {
				if(field[i][j]==-1)
					// If any cell is unoccupied (-1), the board is not fully filled.
					return false;
			}
		}

		// All cells on the board are occupied by pentomino pieces.
		return true;
	}

	/**
 	* Checks if it is possible to place a pentomino piece on the board at the specified position.
 	*
 	* @param piece   The pentomino piece to be placed.
 	* @param pieceID The numerical ID of the pentomino piece.
 	* @param x       The x-coordinate of the board where the piece should be placed.
 	* @param y       The y-coordinate of the board where the piece should be placed.
 	* @return True if the piece can be placed at the specified position without overlapping with other pieces, false otherwise.
 	*/
    protected boolean isPlaceable(int[][] piece, int pieceID, int x, int y) {
		// Create a copy of the board to simulate placing the piece.
		boolean hor = piece.length <= field.length - x;
		boolean ver = piece[0].length <= field[0].length - y;
		if(!hor||!ver)
			return false;
		int [][] copyField = copyBoard();
		for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
        {
            for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
            {
                if (piece[i][j] == 1)
                {
                    // Add the ID of the pentomino to the board if the pentomino occupies this square
                    copyField[x + i][y + j] += (pieceID+1);
					if(copyField[x + i][y + j] != pieceID)
						return false;
                }
            }
        }
		return true;
	}

	/**
 	* Checks if the size of the board is sufficient to accommodate all pentomino pieces.
 	* The maximum number of squares required is calculated based on the number of pentominoes and their size.
 	*
 	* @return true if the board is large enough to fit all the pentomino pieces, otherwise false.
 	*/
	protected boolean isBoardEnough() {
		
		int blocks = INPUT.length *5; // Each pentomino consists of 5 squares

			// Compare the total number of squares needed with the actual board dimensions
			System.out.println("Max INPUT = " +blocks);
			System.out.println("Board dimension = " +(WIDTH*HEIGHT));
		return blocks != WIDTH*HEIGHT;
	}

	/**
 	* An abstract method that should be implemented by subclasses to define the search algorithm.
 	*
	* @return true if a solution is found; otherwise, false.
 	*/
	abstract public boolean search(String solverName);

	/**
 	* Get the possible mutations of a pentomino piece with the given ID.
 	*
	 * @param pentID The numerical ID of the pentomino piece for which mutations are required.
 	* @return An array of 2D matrices representing the different rotations and reflections of the pentomino piece.
 	*/
	public int[][][] getMutations(int pentID) {
		return PentominoDatabase.data[pentID];
	}
	
    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }
	
    public char[] getINPUT() {
        return INPUT;
    }

    public int[][] getField() {
        return field;
    }

    public void setField(int[][] field) {
        this.field = field;
    }

    public Map<Character, Integer> getMap   () {
        return map;
    }

    public void setMap(Map<Character, Integer> map) {
        this.map = map;
    }

	public int[] getPentIDs() {
		return pentIDs;
	}
}