package  Backend;
public class Piece{
private int[][] shape;
private int id;
private char name;
private Coordinate coordinate;

public Piece(int[][] shape, int id, char name, Coordinate coordinate) {
    this.shape = shape;
    this.id = id;
    this.name = name;
    this.coordinate = coordinate;
}

public Piece(Piece piece) {
    this.shape = new int[piece.shape.length][piece.shape[0].length];
    for (int i = 0; i < piece.shape.length; i++) {
        for (int j = 0; j < piece.shape[0].length; j++) {
            this.shape[i][j] = piece.shape[i][j];
        }
    }
    this.id = piece.id;
    this.name = piece.name;
    this.coordinate = new Coordinate(piece.coordinate.x, piece.coordinate.y);
}

public Piece copy(){
    
    return new Piece(this);
}

public int[][] getShape() {
    return shape;
}

public void setShape(int[][] shape) {
    this.shape = new int[shape.length][shape[0].length];
    for (int i = 0; i < shape.length; i++) {
        for (int j = 0; j < shape[0].length; j++) {
            this.shape[i][j] = shape[i][j];
        }
    }
    
}

public int getId() {
    return id;
}

public void setId(int id) {
    this.id = id;
}

public char getName() {
    return name;
}

public void setName(char name) {
    this.name = name;
}

public Coordinate getCoordinate() {
    return coordinate;
}

public void setCoordinate(Coordinate coordinate) {
    this.coordinate = coordinate;
}

@Override
public String toString() {
    return name+"";
}

    @Override
    public int hashCode() {
        
        return super.hashCode();
    }


    

                
}