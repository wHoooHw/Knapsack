package Phase3.MyBackend.Pieces.Pentominos;

import Phase3.Data3D.PentominosData;
import Phase3.MyBackend.Pieces.Coordinate3D;
import Phase3.MyBackend.Pieces.Piece3D;

/**
 * Represents the T Pentomino piece in a three-dimensional space.
 */
public class TPent extends Piece3D {

    /**
     * Constructs a TPent object using the predefined shape and properties.
     */
    public TPent() {
        super(PentominosData.shapeT, 6,'T',new Coordinate3D(),5);
        
    }

    /**
     * Generates all possible rotations of the T Pentomino.
     * @return An array containing various rotations of the original shape.
     */
    public int[][][][] allMutations() {
        int[][][][] allPossibilities = { shape, rotateY(shape),rotateX(shape),
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

    /**
     * Creates a copy of the TPent object with the same properties.
     * @return A new TPent object with copied properties.
     */
    @Override
    public Piece3D copy() {
       Piece3D piece3d = new TPent();
       piece3d.setCoordinate(new Coordinate3D(getCoordinate()));
        return piece3d;
    };
}