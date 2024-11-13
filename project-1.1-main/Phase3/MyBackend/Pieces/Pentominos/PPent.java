package Phase3.MyBackend.Pieces.Pentominos;

import Phase3.Data3D.PentominosData;
import Phase3.MyBackend.Pieces.Coordinate3D;
import Phase3.MyBackend.Pieces.Piece3D;

/**
 * Represents the P Pentomino piece in a three-dimensional space.
 */
public class PPent extends Piece3D {

    /**
     * Constructs a PPent object using the predefined shape and properties.
     */
    public PPent() {
        super(PentominosData.shapeP, 5,'P',new Coordinate3D(),4);
    }


    /**
     * Generates all possible rotations of the P Pentomino.
     * @return An array containing various rotations of the original shape.
     */
    @Override
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
    }


    /**
     * Creates a copy of the PPent object with the same properties.
     * @return A new PPent object with copied properties.
     */
    @Override
    public Piece3D copy() {
       Piece3D piece3d = new PPent();
       piece3d.setCoordinate(new Coordinate3D(getCoordinate()));
        return piece3d;
    };
}