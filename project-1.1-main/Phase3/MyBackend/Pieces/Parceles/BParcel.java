package Phase3.MyBackend.Pieces.Parceles;

import Phase3.Data3D.ParcelsData;
import Phase3.MyBackend.Pieces.Coordinate3D;
import Phase3.MyBackend.Pieces.Piece3D;

public class BParcel extends Piece3D {

    // Constructor for BParcel
    public BParcel() {
        // Call the super constructor with data specific to Parcel B
        super(ParcelsData.parcelB, 2,'B',new Coordinate3D(),4);
        
    }

    // Method to define all mutations of Parcel B
    @Override
    public int[][][][] allMutations(){
        // 6 rotations
        // Define different rotations of Parcel B
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

    // Method to create a copy of the BParcel
    @Override
    public Piece3D copy() {
       Piece3D piece3d = new BParcel();
       piece3d.setCoordinate(new Coordinate3D(getCoordinate()));
        return piece3d;
    };
}