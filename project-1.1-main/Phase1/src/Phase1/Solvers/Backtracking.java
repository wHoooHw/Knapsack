package Phase1.src.Phase1.Solvers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Phase1.src.Phase1.DataConstructor.PentominoBuilder;
import Phase1.src.Phase1.DataConstructor.PentominoDatabase;

/**
 * @author Department of Data Science and Knowledge Engineering (DKE)
 * @version 2022.0
 */


/**
 * This class extends the ISolver class and provides methods for solving the puzzle
 * using a backtracking algorithm (support the search of a solution).
 */
public class Backtracking extends ISolver {
	/**
     * Constructor for the Backtracking class.
     *
     * @param WIDTH  The width of the board.
     * @param HEIGHT The height of the board.
     * @param INPUT  An array of characters representing the pentomino pieces.
     */
	public Backtracking(int WIDTH, int HEIGHT, char[] INPUT) {
		super(WIDTH, HEIGHT, INPUT);
	}

	private Map<String, Boolean> memo = new HashMap<>(); //Helper function which starts a basic search algorithm

	/**
	 * This method initiates the pentomino puzzle solving process.
	 *
	 * @param solverName A string specifying the type of backtracking solver to use.
	 *                   Possible values are "bad," "good," or any other value for
	 *                   the best solver.
	 * @return True if a solution is found, false otherwise.
	 */
	public boolean search(String solverName) {
		// Initialize an empty board

		// Check if the board size is sufficient for pentomino placement
		emptyBoard(field);
		if (isBoardEnough()) {
			System.out.println("Piece can't cover the board");

			return false;
		}
		// Start the basic search
		// basicSearch(field);
		// Start the pentomino placement using the selected backtracking solver
		List<Character> inp = new ArrayList<>();
		for (int i = 0; i < INPUT.length; i++) {
			inp.add(INPUT[i]);
		}
   		 // Depending on the solverName parameter, choose between "bad," "good," or the best backtracking solver
		if (solverName.equalsIgnoreCase("bad")) {
			if (!badBacktracking(inp)) {
				System.out.println("not solvable");
				return false;
			}
		} else if (solverName.equalsIgnoreCase("good")) {
			if (!goodBacktracking(inp, memo)) {
				System.out.println("not solvable");
				return false;
			}
		} else {
			if (!bestBacktracking(inp, memo)) {
				System.out.println("not solvable");
				return false;
			}
		}
		// Solution is found, return true
		return true;
	}

	/**
 	* Checks if a flood fill can be performed on the provided field. A flood fill
 	* is a recursive algorithm to determine whether an area is fully enclosed or
 	* not by checking if the area contains a multiple of 5 filled squares.
 	*
 	* @param field A matrix representing the board to be examined.
 	* @return True if the provided field can be fully flood-filled with regions
	* containing a multiple of 5 filled squares, otherwise false.
 	*/
	private boolean isFloodFill(int[][] field) {
		int[][] copie = copyBoard();
		// Iterate over each square in the field to check for flood-fill conditions
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
				if (field[i][j] == -1)
				// Start a flood-fill and check if the area contains a multiple of 5 squares

					if (floodFill(copie, i, j) % 5 != 0) {
						return false; // The condition is not met; the area cannot be flood-filled.
					}
			}
		}
		return true; // The entire field can be flood-filled with areas of 5 filled squares.
	}

	/**
 	* Converts the 2D integer array representing the game field into a string representation.
 	* Each element in the array is concatenated to form the resulting string.
 	*
 	* @param field The 2D array representing the game field with pentomino placements.
 	* @return A string representation of the game field with pentomino IDs.
 	*/
	private String fieldToString(int[][] field) {
		String s = "";
		// Iterate through the 2D array to build the string representation
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
				s += field[i][j];
			}
		}
		return s; // Return the concatenated string representing the game field
	}

	/**
 	* This method performs a backtracking search to solve a puzzle using the best approach.
 	* It recursively attempts to place pentominoes on the board in a way that satisfies the constraints.
 	*
 	* @param input A list of characters representing the pentominoes to be placed.
 	* @param memo A map used to memoize the state of the board to avoid redundant calculations.
 	* @return True if a valid solution is found, false otherwise.
 	*/
	private boolean bestBacktracking(List<Character> input, Map<String, Boolean> memo) {
		// Convert the current board state to a unique string representation
		String myField = fieldToString(field);
		// Generate additional string representations for memoization
		String reversField = new StringBuilder(myField).reverse().toString();
		int[][] flipdBoardHor = PentominoBuilder.verticalFlip(field);
		String flipedStateHor = fieldToString(flipdBoardHor);
		int[][] flipdBoardVer = PentominoBuilder.verticalFlip(field);
		String flipedStateVer = fieldToString(flipdBoardVer);
		// Check if the current state is memoized and return the result if available

		if (memo.containsKey(myField))
			return memo.get(myField);
		if (memo.containsKey(flipedStateHor))
			return memo.get(flipedStateHor);
		if (memo.containsKey(flipedStateVer))
			return memo.get(flipedStateVer);
		if (memo.containsKey(reversField))
			return memo.get(reversField);
		// If the board is already full and there are no more pentominoes to place, return true
		if (isBoardFull() && input.isEmpty()) {
			// Memoize the successful result
			memo.put(myField, true);
			memo.put(reversField, true);
			memo.put(flipedStateHor, true);
			memo.put(flipedStateVer, true);
			return true; // All pentominoes placed successfully
		}
		// If flood-fill constraint is not satisfied, return false
		if (!isFloodFill(field)) {
			 // Memoize the unsuccessful result
			memo.put(myField, false);
			memo.put(reversField, false);
			memo.put(flipedStateHor, false);
			memo.put(flipedStateVer, false);
			return false;
		}
		// Try to place pentominoes on the board
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < HEIGHT; y++) {

				int pentID = characterToID(input.get(0));

				int mutations = PentominoDatabase.data[pentID].length;

				for (int mutation = 0; mutation < mutations; mutation++) {
					int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];
					boolean hor = pieceToPlace.length <= field.length - x;
					boolean ver = pieceToPlace[0].length <= field[0].length - y;

					if (hor && ver && isPlaceable(pieceToPlace, pentID, x, y)) {
						// Place the pentomino on the board
						addPiece(field, pieceToPlace, pentID, x, y);
						Character temp = input.remove(0);
						// Generate string representations of the new state
						String state = fieldToString(field);

						String reversState = new StringBuilder(state).reverse().toString();
						int[][] flip = PentominoBuilder.verticalFlip(field);
						String fliped = fieldToString(flip);
						// Recursively check if a solution is found
						if (bestBacktracking(input, memo)) {
							// Memoize the successful result
							memo.put(state, true);
							memo.put(reversState, true);
							memo.put(fliped, true);

							return true;
						}
						// Restore the board state and the input list for backtracking
						input.add(0, temp);
						removePiece(field, pieceToPlace, pentID, x, y);

					}

				}
			}
		}
		// Memoize the unsuccessful result
		memo.put(myField, false);
		memo.put(reversField, false);
		memo.put(flipedStateHor, false);
		memo.put(flipedStateVer, false);
		return false; // No solution found
	}

	/**
	 * Attempt to solve the pentomino puzzle using a basic backtracking algorithm.
 	*
 	* @param input A list of remaining pentominoes to be placed on the board.
 	* @return True if a solution is found, false if not.
 	*/
	private boolean badBacktracking(List<Character> input) {
    	// Check if the board is completely filled, indicating a solution.
		if (isBoardFull()) {

			return true; // All pentominoes placed successfully
		}

    	// Iterate through each position on the board to try placing the next pentomino.
		for (int x = 0; x < WIDTH; x++) {

			for (int y = 0; y < HEIGHT; y++) {

				int pentID = characterToID(input.get(0));

            	// Consider all possible mutations of the current pentomino.
				int mutations = PentominoDatabase.data[pentID].length;

				for (int mutation = 0; mutation < mutations; mutation++) {
					int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];
					boolean hor = pieceToPlace.length <= field.length - x;
					boolean ver = pieceToPlace[0].length <= field[0].length - y;

					if (hor && ver && isPlaceable(pieceToPlace, pentID, x, y)) {
						// Attempt to place the pentomino at the current position.
						addPiece(field, pieceToPlace, pentID, x, y);
						Character temp = input.remove(0);
                    	// Recursively try to solve the puzzle with the next pentomino.
						if (badBacktracking(input)) {

							return true; // Solution found.
						}
                    	// If the puzzle is not solvable with the current placement, backtrack.
						input.add(0, temp);
						removePiece(field, pieceToPlace, pentID, x, y);
					}
				}
			}
		}
		return false; // No solution found
	}

	/**
 	* Performs backtracking with memoization to find a valid solution.
 	*
 	* @param input List of characters representing pentominoes to place on the board.
 	* @param memo A map to store memoization data for board states.
 	* @return True if a solution is found, false if not.
 	*/
	private boolean goodBacktracking(List<Character> input, Map<String, Boolean> memo) {
		// Convert the current board state to a string representation for memoization.
		String myField = fieldToString(field);
		String reversField = new StringBuilder(myField).reverse().toString();
		int[][] flipdBoardHor = PentominoBuilder.verticalFlip(field);
		String flipedStateHor = fieldToString(flipdBoardHor);
		int[][] flipdBoardVer = PentominoBuilder.verticalFlip(field);
		String flipedStateVer = fieldToString(flipdBoardVer);

    	// Check if a memoized solution already exists for the current board state or its variations.
		if (memo.containsKey(myField))
			return memo.get(myField);
		if (memo.containsKey(flipedStateHor))
			return memo.get(flipedStateHor);
		if (memo.containsKey(flipedStateVer))
			return memo.get(flipedStateVer);
		if (memo.containsKey(reversField))
			return memo.get(reversField);

		// Base case: If the board is full and there are no pentominoes left to place, a solution is found.
		if (isBoardFull() && input.isEmpty()) {
			memo.put(myField, true);
			memo.put(reversField, true);
			memo.put(flipedStateHor, true);
			memo.put(flipedStateVer, true);
			return true; // All pentominoes placed successfully
		}
		// Iterate through board positions to place pentominoes.
		for (int x = 0; x < WIDTH; x++) {

			for (int y = 0; y < HEIGHT; y++) {

				int pentID = characterToID(input.get(0));

				int mutations = PentominoDatabase.data[pentID].length;

            	// Iterate through pentomino rotations and placements.
				for (int mutation = 0; mutation < mutations; mutation++) {
					int[][] pieceToPlace = PentominoDatabase.data[pentID][mutation];
					boolean hor = pieceToPlace.length <= field.length - x;
					boolean ver = pieceToPlace[0].length <= field[0].length - y;

					// Check if the piece can be placed at the current position.
					if (hor && ver && isPlaceable(pieceToPlace, pentID, x, y)) {

						// Place the pentomino and continue with the next pentomino.
						addPiece(field, pieceToPlace, pentID, x, y);
						Character temp = input.remove(0);
						String state = fieldToString(field);

						// Generate variations of the board state for memoization.
						String reversState = new StringBuilder(state).reverse().toString();
						int[][] flip = PentominoBuilder.verticalFlip(field);
						String fliped = fieldToString(flip);

						// Recursively call the goodBacktracking method.
						if (goodBacktracking(input, memo)) {
							// If a solution is found, update the memoization data.
							memo.put(state, true);
							memo.put(reversState, true);
							memo.put(fliped, true);

							return true;
						}

                    	// If no solution is found, backtrack and remove the pentomino.
						input.add(0, temp);
						removePiece(field, pieceToPlace, pentID, x, y);

					}

				}
			}
		}
		// Update memoization data for the current board state as no solution was found.
		memo.put(myField, false);
		memo.put(reversField, false);
		memo.put(flipedStateHor, false);
		memo.put(flipedStateVer, false);
		return false; // No solution found
	}

	/**
	* Recursively performs flood-fill algorithm to count connected empty cells in the board.
 	*
 	* @param field The game board represented as a matrix.
 	* @param x The x-coordinate of the current cell.
 	* @param y The y-coordinate of the current cell.
 	* @return The number of connected empty cells.
 	*/
	private int floodFill(int[][] field, int x, int y) {
		int counter = 0;

		// Check if the current cell is out of bounds or not empty
		if (x < 0 || y < 0 || x > field.length || y > field[0].length || field[x][y] != -1)
			return 0;

		 // Mark the current cell as visited
		field[x][y] = 20;
		counter++;

    	// Recursively perform flood-fill on adjacent cells
		if (x + 1 < field.length && field[x + 1][y] == -1) {
			// copyField[x][y] = 0;
			counter += floodFill(field, x + 1, y);
		}
		if (x - 1 >= 0 && field[x - 1][y] == -1) {
			// copyField[x][y] = 0;
			counter += floodFill(field, x - 1, y);
		}
		if (y + 1 < field[0].length && field[x][y + 1] == -1) {
			// copyField[x][y] = 0;
			counter += floodFill(field, x, y + 1);
		}

		if (y - 1 >= 0 && field[x][y - 1] == -1) {
			// copyField[x][y] = 0;
			counter += floodFill(field, x, y - 1);
		}

		return counter;
	}

	/**
 	* Empties the entire game board by filling it with -1 values,
 	* indicating empty spaces.
 	*
 	* @param field The 2D array representing the game board to be emptied.
 	*/
	private void emptyBoard(int[][] field) {
		for (int i = 0; i < field.length; i++) {
			// Fill each row of the game board with -1 to indicate empty spaces
			Arrays.fill(field[i], -1);
		}
	}

	/**
 	* Removes a pentomino piece from the board at a specified position (x, y).
 	* This method undoes the placement of a pentomino on the board by setting
 	* the corresponding cells back to empty (-1).
 	*
 	* @param field   The matrix representing the board.
 	* @param piece   The matrix representing the pentomino to be removed.
 	* @param pieceID The ID of the pentomino to be removed.
 	* @param x       The x-coordinate of the pentomino's position on the board.
 	* @param y       The y-coordinate of the pentomino's position on the board.
 	*/
	public void removePiece(int[][] field, int[][] piece, int pieceID, int x, int y) {

		for (int i = 0; i < piece.length; i++) // loop over x position of pentomino
		{
			for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
			{
				if (piece[i][j] == 1) {
					// Set the cell back to empty (-1) to remove the pentomino piece.
					// Add the ID of the pentomino to the board if the pentomino occupies this square
					field[x + i][y + j] = -1;

				}
			}
		}
	}
}