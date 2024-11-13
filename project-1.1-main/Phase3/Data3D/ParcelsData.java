package Phase3.Data3D;

/**
 * The ParcelsData class contains static 3D arrays representing different parcels and methods
 * to generate 3D rotations of specific parcels stored in 4D arrays.
 */
public class ParcelsData {

    // 3D array representing Parcel A
   public static int[][][] ParcelA = {
                    {{1,1},{1,1},{1,1},{1,1}},
                    {{1,1},{1,1},{1,1},{1,1}}  
                };

    // 3D array representing Parcel B
    public static int[][][] parcelB =
                {   
                    {{1,1,1,1},{1,1,1,1},{1,1,1,1}},
                    {{1,1,1,1},{1,1,1,1},{1,1,1,1}}
                };

    // 3D array representing Parcel C
    public static int[][][] parcelC =
            
                {   
                    {{1,1,1},{1,1,1},{1,1,1}},
                    {{1,1,1},{1,1,1},{1,1,1}},
                    {{1,1,1},{1,1,1},{1,1,1}}
                };
    
 

    /**
     * Method contains all 3d rotations of parcel B and store in a 4d array
     * @return the address of the 4D array
     */
    public static int[][][][] parcelBRot(){
        // 6 rotations of Parcel B stored in a 4D array
        int[][][][] parcelB =
            {
                {   // int[2][3][4]
                    {{1,1,1,1},{1,1,1,1},{1,1,1,1}},
                    {{1,1,1,1},{1,1,1,1},{1,1,1,1}}
                },
                {   // int[2][4][3]
                    {{1,1,1},{1,1,1},{1,1,1},{1,1,1}},
                    {{1,1,1},{1,1,1},{1,1,1},{1,1,1}}
                },
                {   // int[4][3][2]
                    {{1,1},{1,1},{1,1}},
                    {{1,1},{1,1},{1,1}},
                    {{1,1},{1,1},{1,1}},
                    {{1,1},{1,1},{1,1}}
                },
                {   // int[3][2][4]
                    {{1,1,1,1},{1,1,1,1}},
                    {{1,1,1,1},{1,1,1,1}},
                    {{1,1,1,1},{1,1,1,1}}
                },
                {   // int[4][2][3]
                    {{1,1,1},{1,1,1}},
                    {{1,1,1},{1,1,1}},
                    {{1,1,1},{1,1,1}},
                    {{1,1,1},{1,1,1}}
                },
                {   // int[3][4][2]
                    {{1,1},{1,1},{1,1},{1,1}},
                    {{1,1},{1,1},{1,1},{1,1}},
                    {{1,1},{1,1},{1,1},{1,1}}
                }
            };

        return parcelB;
    }

    /**
     * Method contains all 3d rotations of parcel C and store in a 4d array
     * @return the address of the 4D array
     */
    public static int[][][][] parcelCRot(){
        // 1 rotation
        int[][][][] parcelC =
            {
                {   // int[3][3][3]
                    {{1,1,1},{1,1,1},{1,1,1}},
                    {{1,1,1},{1,1,1},{1,1,1}},
                    {{1,1,1},{1,1,1},{1,1,1}}
                }
            };
        return parcelC;
    }
}