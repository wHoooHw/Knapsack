package Phase3.MyBackend.Pieces.Parceles;

import Phase3.Data3D.ParcelsData;
import Phase3.MyBackend.Pieces.Coordinate3D;
import Phase3.MyBackend.Pieces.Piece3D;

public class CParcel extends Piece3D {

    int[][][][] allPossibilities; 

    // Constructor for CParcel
    public CParcel() {
        
        super(ParcelsData.parcelC,3,'C',new Coordinate3D(),5);
        allPossibilities = allMutations();
    }

    // Method to define all mutations of Parcel C
    @Override
    public int[][][][] allMutations(){
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
// Getter method to retrieve all possibilities of Parcel C
public int[][][][] getAllPossibilities() {
    return allPossibilities;
}
    // Method to create a copy of the CParcel
    @Override
    public Piece3D copy() {
       Piece3D piece3d = new CParcel();
       piece3d.setCoordinate(new Coordinate3D(getCoordinate()));
        return piece3d;
    };
}