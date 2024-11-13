package Phase3.MyBackend.Pieces.Parceles;

import Phase3.Data3D.ParcelsData;
import Phase3.MyBackend.Pieces.Coordinate3D;
import Phase3.MyBackend.Pieces.Piece3D;

public class AParcel extends Piece3D {

    // Constructor for AParcel
    public AParcel(){
        // Call the super constructor with data specific to Parcel A
        super(ParcelsData.ParcelA, 1,'A',new Coordinate3D(),3);
        
    }

    // Method to define all mutations of Parcel A
    @Override
    public int[][][][] allMutations(){
        // Define different rotations of Parcel A
        int[][][][] parcelA =
            {   {   // int[2][4][2]  
                    {{1,1},{1,1},{1,1},{1,1}},
                    {{1,1},{1,1},{1,1},{1,1}}  
                },
                {   // int[4][2][2]
                    {{1,1},{1,1}},
                    {{1,1},{1,1}},
                    {{1,1},{1,1}},
                    {{1,1},{1,1}}
                },
                {   // int[2][2][4]
                    {{1,1,1,1},{1,1,1,1}},
                    {{1,1,1,1},{1,1,1,1}}    
                }
                
            };

        return parcelA;
    }

    // Method to create a copy of the AParcel
    @Override
    public Piece3D copy() {
       Piece3D piece3d = new AParcel();
       piece3d.setCoordinate(new Coordinate3D(getCoordinate()));
        return piece3d;
    };
}