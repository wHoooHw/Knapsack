package Phase3.MyBackend.Pieces.Pentominos;

import Phase3.Data3D.PentominosData;
import Phase3.MyBackend.Pieces.Coordinate3D;
import Phase3.MyBackend.Pieces.Piece3D;

public class LPent extends Piece3D {

    

    // Constructor for LPent
    public LPent() {
        super(PentominosData.shapeL, 4,'L',new Coordinate3D(),3);
    }

    // Method to define all mutations of LPent
    @Override
    public int[][][][] allMutations() {
        int[][][][] allPossibilities = {
            // Original shape
                // Rotations about the axes
                shape, rotateY(shape),rotateX(shape),
                rotateZ(shape), rotateY(rotateX(shape)),rotateX(rotateX(shape)), rotateZ(rotateX(shape)), rotateX(rotateY(shape)),
                rotateY(rotateY(shape)),
                rotateZ(rotateZ(shape)), rotateX(rotateX(rotateX(shape))), rotateY(rotateZ(shape)),
                rotateY(rotateX(rotateX(shape))),
                rotateZ(rotateX(rotateX(shape))), rotateX(rotateY(rotateX(shape))),
                rotateY(rotateY(rotateX(shape))), rotateZ(rotateZ(rotateX(shape))),
                rotateX(rotateX(rotateY(shape))),
                rotateY(rotateY(rotateY(shape))), rotateZ(rotateZ(rotateZ(shape))),
                rotateY(rotateX(rotateX(rotateX(shape)))),
                rotateX(rotateY(rotateX(rotateX(shape)))),
                rotateX(rotateX(rotateY(rotateX(shape)))),
                rotateY(rotateY(rotateY(rotateX(shape))))
        };
     return allPossibilities;
    };
    
    // Method to create a copy of the LPent
    @Override
    public Piece3D copy() {
       Piece3D piece3d = new LPent();
       piece3d.setCoordinate(new Coordinate3D(getCoordinate()));
        return piece3d;
    };
}
