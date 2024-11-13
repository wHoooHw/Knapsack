package Utilities;

public class Helper {
    
    public static int[][] rotate(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] rotatedMatrix = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotatedMatrix[j][rows - 1 - i] = matrix[i][j];
            }
        }

        return rotatedMatrix;
    }

    public static int[][] submatrix(int[][] matrix) {
        int nonZeroRowCount = 0;
        int nonZeroColCount = 0;

        for (int i = 0; i < matrix.length; i++) {
            if (isNonZeroRow(matrix[i])) {
                nonZeroRowCount++;
            }
        }

        for (int i = 0; i < matrix[0].length; i++) {
            if (isNonZeroColumn(matrix, i)) {
                nonZeroColCount++;
            }
        }

        int[][] newMatrix = new int[nonZeroRowCount][nonZeroColCount];
        int newIndex = 0;

        for (int i = 0; i < matrix.length; i++) {
            if (isNonZeroRow(matrix[i])) {
                int newColIndex = 0;
                for (int j = 0; j < matrix[0].length; j++) {
                    if (isNonZeroColumn(matrix, j)) {
                        newMatrix[newIndex][newColIndex++] = matrix[i][j];
                    }
                }
                newIndex++;
            }
        }

        return newMatrix;
    }

    private static boolean isNonZeroRow(int[] row) {
        for (int value : row) {
            if (value != 0) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNonZeroColumn(int[][] matrix, int colIndex) {
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i][colIndex] != 0) {
                return true;
            }
        }
        return false;
    }


}
