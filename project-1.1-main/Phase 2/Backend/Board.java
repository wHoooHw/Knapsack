package Backend;



public class Board {
    
    private int width;
    private int height;
    private int[][] field; //Change the size of it
    private Piece currentPiece;
    private Piece nextPiece;

    // Methods
    public Board(int width, int height) {
        this.width = width;
        this.height = height;
        this.field = new int[height][width];
        emptythis();
    }

    private void emptythis() {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                field[i][j] = -1;
            }
        }
    }

    

    
    public void removePiece(Piece piece) {
        int[][] shape = piece.getShape();

        int x = piece.getCoordinate().x;
        int y = piece.getCoordinate().y;


        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if(shape[i][j]==1)
                    field[x + i][y + j] =-1;
            }
        }
    }

    protected void addPiece(Piece piece,Piece nextPiece){
        setCurrentPiece(piece);
        setNextPiece(nextPiece);
        int[][] shape = piece.getShape();

        int x = piece.getCoordinate().x;
        int y = piece.getCoordinate().y;

        int id = piece.getId();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if(shape[i][j]==1)
                    field[x + i][y + j] =id;
            }
        }
    }

    protected void addPiece(Piece piece, Coordinate c) {
        
        setCurrentPiece(piece);
        setNextPiece(nextPiece);
        int[][] shape = piece.getShape();

        int x = piece.getCoordinate().x;
        int y = piece.getCoordinate().y;

        int id = piece.getId();

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if(shape[i][j]==1)
                    field[x + i][y + j] =id;
            }
        }
    }

    protected int[][] copyField() {
        int[][] copyField = new int[field.length][field[0].length];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[0].length; j++) {
                copyField[i][j] = field[i][j];
            }
        }
        return copyField;

    }


    

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int[][] getField() {
        return field;
    }

    public void setField(int[][] field) {
        this.field = field;
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;
    }

    public Piece getNextPiece() {
        return nextPiece;
    }

    public void setNextPiece(Piece nextPiece) {
        this.nextPiece = nextPiece;
    }

    

    public Board copy(){
        Board copythis = new Board(this.width, this.height);
        int[][] copyField = getCopyField();
    
        copythis.setField(copyField);
        if(currentPiece != null)
            copythis.setCurrentPiece(currentPiece.copy());
        return copythis;
    }

    public int[][] getCopyField() {
        int[][] copyField = new int[height][width];
    
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                copyField[i][j] = this.field[i][j];
            }
        }
        return copyField;
    }
}