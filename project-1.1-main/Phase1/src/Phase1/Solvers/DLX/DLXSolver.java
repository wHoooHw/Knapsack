package Phase1.src.Phase1.Solvers.DLX;

import java.util.ArrayList;
import java.util.List;

import Phase1.src.Phase1.Solvers.AlgorithmX;
import Phase1.src.Phase1.Solvers.ISolver;

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


public class DLXSolver extends ISolver {
    
    int[][] matrix;
    ArrayList<Header> headers;
    AlgorithmX algoX;
    Header firstHeader = new Header();
    List<Integer> rows = new ArrayList<Integer>();

    /**
     * Constructs a DLXSolver object with the specified parameters.
     *
     * @param WIDTH  The width of the problem matrix.
     * @param HEIGHT The height of the problem matrix.
     * @param INPUT  The input data for the problem.
     */
    public DLXSolver(int WIDTH, int HEIGHT, char[] INPUT) {
        super(WIDTH, HEIGHT, INPUT);
        algoX = new AlgorithmX(WIDTH, HEIGHT, INPUT);
        matrix = algoX.allPentominoMatrix();
        headers = new ArrayList<>();
    }

    /**
     * Searches for a solution to the problem using the DLX algorithm.
     *
     * @param solverName The name of the solver.
     * @return true if a solution is found, false otherwise.
     */
    @Override
    public boolean search(String solverName) {
        createDLXStructure();
        long time = System.currentTimeMillis();
        boolean bool = findExactCover();
        System.out.println(System.currentTimeMillis() - time);
        System.out.println(bool);
        if (bool) {
            getFinalField();
            return true;
        }
        return false;
    }

    /**
     * Creates the DLX data structure to represent the problem matrix.
     */
    public void createDLXStructure() {
        Header prevHeader = firstHeader;
        for (int i = 0; i < matrix[0].length; i++) {
            Header nextHeader = new Header(i);
            headers.add(i, nextHeader);
            prevHeader.linkHorizontally(nextHeader);
            prevHeader = nextHeader;
        }
        for (int i = 0; i < matrix.length; i++) {
            Node prevNode = null;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1) {
                    Header header = headers.get(j);
                    Node node = new Node(i, header);
                    header.size++;
                    if (prevNode == null) {
                        prevNode = node;
                    }
                    header.up.linkVertically(node);
                    prevNode.linkHorizontally(node);
                }
            }
        }
    }

    /**
     * Finds an exact cover for the problem using the DLX algorithm.
     *
     * @return true if an exact cover is found, false otherwise.
     */
    public boolean findExactCover() {
        if (firstHeader.right == firstHeader) {
            return true;
        }
        Header h = getMinHeader(); 
        h.unlink();
        for (Node i = h.down; i != h; i = i.down) {
            rows.add(i.rowID);
            for (Node j = i.right; j != i; j = j.right) {
                j.header.unlink();
            }
            if (findExactCover()) {
                return true;
            }
            rows.remove(rows.size() - 1);
            for (Node k = i.left; k != i; k = k.left) {
                k.header.link();
            }
        }
        h.link();
        return false;
    }

    /**
     * Retrieves the final field representing the solution.
     */
    private void getFinalField() {
        for (Integer integer : rows) {
            int[] row = matrix[integer];
            rowToField(row);
        }
    }

    /**
     * Converts a row from the matrix to the final field representation.
     *
     * @param row The row to convert.
     */
    private void rowToField(int[] row) {
        int index = getINPUT().length;
        int id = 0;
        for (int i = 0; i < getINPUT().length; i++) {
            if (row[i] == 1)
                id = i;
        }
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                if (row[index] == 1)
                    field[i][j] = id;
                index++;
            }
        }
    }

    /**
     * Gets the header with the minimum size.
     *
     * @return The header with the minimum size.
     */
    private Header getMinHeader() {
        int minValue = Integer.MAX_VALUE;
        Header minHeader = null;
        for (Header i = (Header) firstHeader.right; i != firstHeader; i = (Header) i.right) {
            if (i.size < minValue) {
                minHeader = i;
                minValue = i.size;
            }
            if (minValue == 0) {
                return minHeader;
            }
        }
        return minHeader;
    }
}
