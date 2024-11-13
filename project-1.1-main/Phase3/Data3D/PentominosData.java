package Phase3.Data3D;

/**
 * The PentominosData class contains static 3D arrays representing different pentomino shapes,
 * along with methods for rotating these shapes around the X, Y, and Z axes.
 */
public class PentominosData {

    // 3D array representing the L-shaped pentomino
    public static int[][][] shapeL = { { { 1 }, { 1 }, { 1 }, { 1 } }, { { 1 }, { 0 }, { 0 }, { 0 } } };
    // 3D array representing the P-shaped pentomino
    public static int[][][] shapeP = { { { 1, 1 }, { 1, 1 }, { 1, 0 } } };
    // 3D array representing the T-shaped pentomino
    public static int[][][] shapeT = { { { 1, 1, 1 }, { 0, 1, 0 }, { 0, 1, 0 } } };
 
    /**
    * Rotates a 3D parcel array around the X-axis.
    *
    * @param parcelArray The original 3D parcel array to be rotated.
    * @return A new 3D array representing the rotated parcel around the X-axis.
    */
    public static int[][][] rotateX(int[][][] parcelArray) {
        // Create a new 3D array with dimensions swapped for rotation
        int[][][] rotateArray = new int[parcelArray.length][parcelArray[0][0].length][parcelArray[0].length];
        // Iterate through the original array to perform the X-axis rotation
        for (int i = 0; i < rotateArray.length; i++) {
            for (int j = 0; j < rotateArray[0].length; j++) {
                for (int k = 0; k < rotateArray[0][0].length; k++) {
                    // Perform the X-axis rotation by swapping dimensions
                    rotateArray[i][j][k] = parcelArray[i][rotateArray[0][0].length - 1 - k][j];
                }
            }
        }
        // Return the rotated 3D array
        return rotateArray;
    }

    /**
    * Rotates a 3D parcel array around the Y-axis.
    *
    * @param parcelArray The original 3D parcel array to be rotated.
    * @return A new 3D array representing the rotated parcel around the Y-axis.
    */
    public static int[][][] rotateY(int[][][] parcelArray) {
        // Create a new 3D array with dimensions swapped for rotation
        int[][][] rotateArray = new int[parcelArray[0].length][parcelArray.length][parcelArray[0][0].length];
        // Iterate through the original array to perform the Y-axis rotation
        for (int i = 0; i < rotateArray.length; i++) {
            for (int j = 0; j < rotateArray[0].length; j++) {
                for (int k = 0; k < rotateArray[0][0].length; k++) {
                    // Perform the Y-axis rotation by swapping dimensions
                    rotateArray[i][j][k] = parcelArray[j][rotateArray.length - 1 - i][k];
                }
            }
        }
        return rotateArray; // Return the rotated 3D array
    }

    /**
    * Rotates a 3D parcel array around the Z-axis.
    *
    * @param parcelArray The original 3D parcel array to be rotated.
    * @return A new 3D array representing the rotated parcel around the Z-axis.
    */
    public static int[][][] rotateZ(int[][][] parcelArray) {
        // Create a new 3D array with dimensions swapped for rotation
        int[][][] rotateArray = new int[parcelArray[0][0].length][parcelArray[0].length][parcelArray.length];
        // Iterate through the original array to perform the Z-axis rotation
        for (int i = 0; i < rotateArray.length; i++) {
            for (int j = 0; j < rotateArray[0].length; j++) {
                for (int k = 0; k < rotateArray[0][0].length; k++) {
                    // Perform the Z-axis rotation by swapping dimensions
                    rotateArray[i][j][k] = parcelArray[k][j][rotateArray.length - 1 - i];
                }
            }
        }
        return rotateArray; // Return the rotated 3D array
    }

    /**
    * Generates all possible rotations of the L-shaped pentomino parcel.
    *
    * @return A 4D array containing all possible rotations of the L-shaped pentomino.
    */
    public static int[][][][] parcelLRot() {
        int[][][][] allPossibilities = {
                shapeL, rotateY(shapeL),rotateX(shapeL),
                rotateZ(shapeL), rotateY(rotateX(shapeL)),rotateX(rotateX(shapeL)), rotateZ(rotateX(shapeL)), rotateX(rotateY(shapeL)),
                rotateY(rotateY(shapeL)),
                rotateZ(rotateZ(shapeL)), rotateX(rotateX(rotateX(shapeL))), rotateY(rotateZ(shapeL)),
                rotateY(rotateX(rotateX(shapeL))),
                rotateZ(rotateX(rotateX(shapeL))), rotateX(rotateY(rotateX(shapeL))),
                rotateY(rotateY(rotateX(shapeL))), rotateZ(rotateZ(rotateX(shapeL))),
                rotateX(rotateX(rotateY(shapeL))),
                rotateY(rotateY(rotateY(shapeL))), rotateZ(rotateZ(rotateZ(shapeL))),
                rotateY(rotateX(rotateX(rotateX(shapeL)))),
                rotateX(rotateY(rotateX(rotateX(shapeL)))),
                rotateX(rotateX(rotateY(rotateX(shapeL)))),
                rotateY(rotateY(rotateY(rotateX(shapeL))))
        };
     return allPossibilities;
    };

    
    /**
    * Generates all possible rotations of the P-shaped pentomino parcel.
    *
    * @return A 4D array containing all possible rotations of the P-shaped pentomino.
    */
    public static int[][][][] parcelPRot() {
        int[][][][] allPossibilities = { shapeP, rotateX(shapeP), rotateY(shapeP),
                rotateZ(shapeP), rotateX(rotateX(shapeP)),
                rotateY(rotateX(shapeP)), rotateZ(rotateX(shapeP)), rotateX(rotateY(shapeP)),
                rotateY(rotateY(shapeP)),
                rotateY(rotateZ(shapeP)), rotateZ(rotateZ(shapeP)),
                rotateX(rotateX(rotateX(shapeP))),
                rotateY(rotateX(rotateX(shapeP))), rotateZ(rotateX(rotateX(shapeP))),
                rotateX(rotateY(rotateX(shapeP))),
                rotateY(rotateY(rotateX(shapeP))), rotateZ(rotateZ(rotateX(shapeP))),
                rotateX(rotateX(rotateY(shapeP))),
                rotateY(rotateY(rotateY(shapeP))), rotateZ(rotateZ(rotateZ(shapeP))),
                rotateY(rotateX(rotateX(rotateX(shapeP)))),
                rotateX(rotateY(rotateX(rotateX(shapeP)))),
                rotateX(rotateX(rotateY(rotateX(shapeP)))),
                rotateY(rotateY(rotateY(rotateX(shapeP))))
        };

        return allPossibilities;
    };

    /**
    * Generates all possible rotations of the T-shaped pentomino parcel.
    *
    * @return A 4D array containing all possible rotations of the T-shaped pentomino.
    */
    public static int[][][][] parcelTRot() {
        int[][][][] allPossibilities = { shapeT, rotateX(shapeT), rotateY(shapeT), rotateZ(shapeT),
                rotateY(rotateX(shapeT)), rotateZ(rotateX(shapeT)), rotateY(rotateZ(shapeT)), rotateX(rotateX(shapeT)),
                rotateX(rotateX(rotateX(shapeT))), rotateY(rotateX(rotateX(shapeT))),
                rotateZ(rotateX(rotateX(shapeT))), rotateY(rotateY(rotateY(rotateX(shapeT))))
        };
        return allPossibilities;
    }; 
}