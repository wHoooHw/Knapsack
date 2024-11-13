package Phase1.src.Phase1;


/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Phase1.src.Phase1.DataConstructor.PentominoDatabase;
import Phase1.src.Phase1.GUI.UI;


/**
 * This class includes the methods to support the search of a solution.
 */
public class Search
{
    public static final int WIDTH = 12;
    public static final int HEIGHT = 5;
    
    // public static final char[] input = {'T','W','Z','L','I','Y','X','U','V','P','N','F'};
	public static final char[] input = {'P','X','F','V','W','Y','T','Z','U','N','L','I'};
    static int[][] field = new int[WIDTH][HEIGHT];
    //Static UI class to display the board
    public static UI ui = new UI(WIDTH, HEIGHT, 50);
	
	private static Map<String,Boolean> memo = new HashMap<>();
	/**
	 * Helper function which starts a basic search algorithm
	 */
    public static void search()
    {
        // Initialize an empty board
        

        emptyBoard(field);
		if(isBoardEnough()){
			System.out.println("Board is too small");
			return;
		}
        // Start the basic search
        // basicSearch(field);
		List<Character> inp = new ArrayList<>();
		for (int i = 0; i <input.length; i++) {
			inp.add(input[i]);
		}
		long time = System.currentTimeMillis();
		if(backtracking(field,inp,memo))
			System.out.println("solution found");
		System.out.println(System.currentTimeMillis()-time);
		
    }
	
	/**
	 * Get as input the character representation of a pentomino and translate it into its corresponding numerical value (ID)
	 * @param character a character representating a pentomino
	 * @return	the corresponding ID (numerical value)
	 */
    private static int characterToID(char character) {
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
	 * Basic implementation of a search algorithm. It is not a bruto force algorithms (it does not check all the posssible combinations)
	 * but randomly takes possible combinations and positions to find a possible solution.
	 * The solution is not necessarily the most efficient one
	 * This algorithm can be very time-consuming
	 * @param field a matrix representing the board to be fulfilled with pentominoes
	 */
    protected static void basicSearch(int[][] field){
    	Random random = new Random();
    	boolean solutionFound = false;
		if(isBoardEnough()){
			System.out.println("Board is too small");
			return;
		}
    	
    	while (!solutionFound) {
    		solutionFound = true;
    		
    		//Empty board again to find a solution
			emptyBoard(field);
    		
    		//Put all pentominoes with random rotation/flipping on a random position on the board
    		for (int i = 0; i < input.length; i++) {
    			
    			//Choose a pentomino and randomly rotate/flip it
    			int pentID = characterToID(input[i]);
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
    			}
				 else if (HEIGHT == pieceToPlace[0].length) {
    				//this particular rotation of the piece fits perfectly into the height of the field
    				y = 0;
    			} else {
    				//there are multiple possibilities where to place the piece without leaving the field
    				y = random.nextInt(HEIGHT-pieceToPlace[0].length+1);
    			}
    		
    			//If there is a possibility to place the piece on the field, do it
    			if (x >= 0 && y >= 0 && !isPlaceable(field, pieceToPlace, pentID, x, y)) {
	    			addPiece(field, pieceToPlace, pentID, x, y);
	    		} 
				
    		}
    		//Check whether complete field is filled
    		solutionFound = isBoardFull(field);
    		

    		
    		if (solutionFound) {
    			//display the field
    			ui.setState(field); 
    			System.out.println("Solution found");
    			break;
    		}
    	}	
    }



	private static boolean isFloodFill(int[][] field){
		int[][] copie = copyBoard(field);
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
				if(field[i][j]==-1)
					if(floodFill(copie,i, j)%5!=0){
						return false;
					}
					
			}
		}
		return true;

	}
	
	protected static boolean checkSpace(int[][] field, int input){
		int space = 0;
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				if(field[i][j]==-1)
					space +=1;
			}
		}

		return space == input;
	}
	
	
	private static String fieldToString(int[][] field){
		String s ="";
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
				s += field[i][j];
			}
		}
		return s;
	}



	private static boolean backtracking(int[][] field, List<Character> input, Map <String, Boolean>memo) {
		String myField = fieldToString(field); 
		
		if(memo.containsKey(myField))
			return memo.get(myField);
		if (isBoardFull(field)&&input.isEmpty()) {
			return true;  // All pentominoes placed successfully
		}
		if(!isFloodFill(field))
			return false;
		
		
		for (int x = 0; x < WIDTH; x++) {
			
			for (int y = 0; y < HEIGHT; y++) {
					
					int pentID = characterToID(input.get(0));
					
					int mutations = PentominoDatabase.data[pentID].length;
					
					for (int mutation = 0; mutation < mutations; mutation++) {
						int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];
						boolean hor = pieceToPlace.length <= field.length - x;
						boolean ver = pieceToPlace[0].length <= field[0].length - y;
	
						if (hor && ver && isPlaceable(field, pieceToPlace, pentID, x, y)) {
							addPiece(field, pieceToPlace, pentID, x, y);

							// ui.setState(field);
							Character temp = input.remove(0);
							String state = fieldToString(field);
							String reversState =new StringBuilder(state).reverse().toString();
							
							if (backtracking(field, input, memo)) {
								memo.put(state,true );
								memo.put(reversState,true);
								return true;
							}

							input.add(0,temp);
							removePiece(field,pieceToPlace, pentID,x,y);
						}
						
				}
			}
		}
		String state = fieldToString(field);
		String reversState =new StringBuilder(state).reverse().toString();
		memo.put(state,false);
		memo.put(reversState,false);
		return memo.get(state);  // No solution found
	}

	
	
	
	
	



	private static int floodFill(int[][] field, int x,int y){
		int counter = 0;
		
		
		if(x<0||y<0||x>field.length||y>field[0].length||field[x][y]!=-1)
			return 0;
		field[x][y] = 20;
		counter++;
		
		if(x+1<field.length && field[x+1][y]==-1){
			// copyField[x][y] = 0;
			counter+= floodFill(field, x+1, y);
		} 
		if(x-1>=0 && field[x-1][y]==-1){
			// copyField[x][y] = 0;
			counter+= floodFill(field, x-1, y);
		} 
		if(y+1<field[0].length && field[x][y+1]==-1){
			// copyField[x][y] = 0;
			counter+= floodFill(field, x, y+1);
		} 
		
		if(y-1>=0&& field[x][y-1]==-1){
			// copyField[x][y] = 0;
			counter+= floodFill(field, x, y-1);
		}
		
		return counter;
	}

	private static boolean isBoardFull(int[][] field) {
		
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				if(field[i][j]==-1)
					return false;
			}
		}
		return true;
	}

	private static boolean isBoardEnough() {
		
		int [][][] allPiece = new int[input.length][][];
		for (int i = 0; i < input.length; i++) {
			int pentID = characterToID(input[i]);
			int mutation = PentominoDatabase.data[pentID].length-1;
			allPiece[i] = PentominoDatabase.data[pentID][mutation];
		}
		int count =0;
		for (int i = 0; i < allPiece.length; i++) {
			for (int j = 0; j < allPiece[i].length; j++) {
				for (int j2 = 0; j2 < allPiece[i][j].length; j2++) {
					count += allPiece[i][j][j2] ;
				}
			}
		}
		
			System.out.println("Max input = " +count);
			System.out.println("Board dimension = " +(WIDTH*HEIGHT));
		return count > WIDTH*HEIGHT;
	}

	
	private static void emptyBoard(int[][] field) {
		for (int i = 0; i < field.length; i++) {
			Arrays.fill(field[i],-1);
		}
	}

    
	/**
	 * Adds a pentomino to the position on the field (overriding current board at that position)
	 * @param field a matrix representing the board to be fulfilled with pentominoes
	 * @param piece a matrix representing the pentomino to be placed in the board
	 * @param pieceID ID of the relevant pentomino
	 * @param x x position of the pentomino
	 * @param y y position of the pentomino
	 */
    public static void addPiece(int[][] field, int[][] piece, int pieceID, int x, int y)
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

	public static void removePiece(int[][] field, int[][] piece, int pieceID, int x, int y)
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

	private static boolean isPlaceable(int[][] field, int[][] piece, int pieceID, int x, int y) {
		int [][] copyField = copyBoard(field);
		
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

	private static int[][] copyBoard(int[][] field){
		int[][] s = new int[field.length][field[0].length];
		for (int i = 0; i < s.length; i++) {
			
			s[i] = Arrays.copyOf(field[i], field[i].length);
		}
		
		
		return s;
	}
	/**
	 * Main function. Needs to be executed to start the basic search algorithm
	 */
    public static void main(String[] args)
    {
		
        search();
		ui.setState(field);
	}

}

