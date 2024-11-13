package  Backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Utilities.Helper;

public class Logic {
    
    Board board;
    int score;
    
    private Map<String,Piece> map;
    private List<Integer> clearedIndeces;
    int clearedLines = 0;
    Game game;
    public Logic(Board board){
        this.board = board;
        score = 0;
        map = new HashMap<>();
        clearedIndeces = new ArrayList<>();
        
    }

    public Logic(Board board,Game game){
        this.board = board;
        score = 0;
        map = new HashMap<>();
        clearedIndeces = new ArrayList<>();
        this.game = game;
        
    }



    void addScore(int lines){

        if(lines==0) score += 0;
        else if(lines==1) score += 1;
        else if(lines==2) score += 2;
        else if(lines==3) score += 3;
        else if(lines==4) score += 4;
        
        else if(lines==5) score += 5;
        else if(lines==6) score += 6;
        else if(lines==7) score += 7;
        else if(lines==8) score += 8;
        else if(lines==9) score += 9;
        else if(lines==10) score += 10;
        else if(lines==11) score += 11;
        else if(lines>11) score += 12;
        else return;
    }

    public void clearRow() {
        
        boolean gravityCheck = false;
        clearedIndeces.clear();
        outerloop:
        for (int i=0;i<board.getField()[0].length; i++) {
            boolean flag = false;
            
            for (int j = 0; j < board.getField().length; j++) 
                if(board.getField()[j][i]==-1){
                    flag = false;
                    continue outerloop;
            }
            flag = true;
            if(flag){
                clearedIndeces.add(i);
                
                for (int j = 0; j < board.getHeight();j++) {
                    board.getField()[j][i] = -1;
                    clearedLines++;
                }
                gravityCheck = true;
                flag = false;
            }
         
        }
        
        if(gravityCheck){
            addScore(clearedIndeces.size());
            runNaiveGravity();
            cascadeGravity();
            clearRow();
        }
            
        
    }

    private void niveGravity(int index){

        int[][] copyField = new int[board.getField().length][board.getField()[0].length];

        for (int i = 0; i < board.getField().length; i++) {
            for (int j = 0; j < board.getField()[i].length; j++) {
                copyField[i][j] = board.getField()[i][j];
            }
        }
        

        for(int i=index-1;i>=0;i--){
            for(int j=0;j<board.getField().length;j++){
                copyField[j][i+1]=board.getField()[j][i];
            }
        }
        for(int j=0;j<board.getField().length;j++){
            copyField[j][0]=-1;
        } 

        board.setField(copyField);
        if(game!=null&&game.gui!=null)
            game.updateGUI(board.getField());
        
    } 
    private void runNaiveGravity(){
        for (Integer index : clearedIndeces) {
            niveGravity(index);
        }
    }

    private boolean checkXY(int[][] field,int x, int y) {
        if (x < 0 || y < 0 || x > field.length || y > field[0].length)
			return true;
        return false;
    }

    // find new shapes or parts of shapes
     private int[][] getShape(int[][] copyField,int id, Coordinate c){
        int x = c.x,  y= c.y;
        if(checkXY(board.getField(),x,y) || board.getField()[x][y]!=id || copyField[x][y] == 1){
            return copyField;
            
        }
        copyField[x][y]=1;

        if (x + 1 < board.getField().length && board.getField()[x + 1][y] == id ) {
            getShape(copyField, id,new Coordinate(x+1, y));
        }

        if (x - 1 >= 0 && board.getField()[x - 1][y] == id) {
            getShape(copyField, id,new Coordinate(x-1, y));
        }

        if (y + 1 < board.getField()[0].length && board.getField()[x][y + 1] == id) {
            getShape(copyField, id, new Coordinate(x, y+1));
        }

        if (y - 1 >= 0 && board.getField()[x][y - 1] == id) {
            getShape(copyField, id, new Coordinate(x, y-1));
        }

        return copyField;
     }

     boolean cas = false;
     protected void cascadeGravity(){
        cas = false;
        fillMap();
        
        for (Piece piece : map.values()) {
            moveParts(piece, piece.getCoordinate());
            if(game!=null&&game.gui!=null)
                game.updateGUI(board.getField());
        }
        if(cas) cascadeGravity();
     }

     public boolean isPlaceable(Piece p, Coordinate c) {
        boolean horOutOfBound = (c.x<=board.getHeight()-p.getShape().length)&&(c.x>=0);
        boolean verOutOfBound =  c.y<=board.getWidth()-p.getShape()[0].length&&c.y>=0;
        if(!horOutOfBound || ! verOutOfBound)
            return false;

		Board copyBoard = board.copy();
		
        copyBoard.removePiece(p);
        int [][] copyField =copyBoard.getField();
        
        int [][] piece = p.getShape(); 
        int pieceID = p.getId();

		for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
        {
            for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
            {
                if (piece[i][j] == 1)
                {
                    // Add the ID of the pentomino to the board if the pentomino occupies board square
                    // if(copyField[c.x + i][c.y + j] ==-1) return false;
                    copyField[c.x + i][c.y + j] += (pieceID+1);
                    
					if(copyField[c.x + i][c.y + j] != pieceID)
						return false;
                }
            }
        }
		return true;
    }

    public boolean isPlaceableRotation(Piece p, Coordinate c) {
        boolean horOutOfBound = (c.x<=board.getHeight()-p.getShape().length)&&(c.x>=0);
        boolean verOutOfBound =  c.y<=board.getWidth()-p.getShape()[0].length&&c.y>=0;
        if(!horOutOfBound || ! verOutOfBound)
            return false;

		Board copyBoard = board.copy();
		
        copyBoard.removePiece(board.getCurrentPiece());
        int [][] copyField =copyBoard.getField();
        
        int [][] piece = p.getShape(); 
        int pieceID = p.getId();

		for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
        {
            for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
            {
                if (piece[i][j] == 1)
                {
                    // Add the ID of the pentomino to the board if the pentomino occupies board square
                    // if(copyField[c.x + i][c.y + j] ==-1) return false;
                    copyField[c.x + i][c.y + j] += (pieceID+1);
                    
					if(copyField[c.x + i][c.y + j] != pieceID)
						return false;
                }
            }
        }
		return true;
    }

    protected boolean isPlaceable(Piece p,boolean isNewPiece) {
        Coordinate c = p.getCoordinate();
        boolean horOutOfBound = (c.x<=board.getHeight()-p.getShape().length)&&(c.x>=0);
        boolean verOutOfBound =  c.y<=board.getWidth()-p.getShape()[0].length&&c.y>=0;
        if(!horOutOfBound || ! verOutOfBound)
            return false;

		Board copyBoard = board.copy();
		if(!isNewPiece)
            copyBoard.removePiece(p);
        int [][] copyField =copyBoard.getField();
        
        int [][] piece = p.getShape(); 
        int pieceID = p.getId();

		for(int i = 0; i < piece.length; i++) // loop over x position of pentomino
        {
            for (int j = 0; j < piece[i].length; j++) // loop over y position of pentomino
            {
                if (piece[i][j] == 1)
                {
                    // Add the ID of the pentomino to the board if the pentomino occupies board square
                    
                    copyField[c.x + i][c.y + j] += (pieceID+1);
					if(copyField[c.x + i][c.y + j] != pieceID)
						return false;
                }
            }
        }
		return true;
    }

    private void moveParts(Piece piece,Coordinate c) {
        
        int x = piece.getCoordinate().x;
        int y = piece.getCoordinate().y+1;
        if(isPlaceable(piece, new Coordinate(x, y))){
            cas = true;
            board.removePiece(piece);
            piece.setCoordinate(new Coordinate(x, y));
            board.addPiece(piece, piece.getCoordinate());
            moveParts(piece, piece.getCoordinate());
        }
        else
            return;
    }

     protected void fillMap(){
        map.clear();
        for (int i = 0; i < board.getField().length; i++) {
            for (int j = 0; j < board.getField()[0].length; j++) {
                if(board.getField()[i][j]!=-1){
                    int id=board.getField()[i][j];
                    int[][] copyField = new int[board.getField().length][board.getField()[0].length];
                    int[][] shape = getShape(copyField, id, new Coordinate(i,j));
                    String stringShape=fieldToString(shape);
                    if(!map.containsKey(stringShape)){
                        Coordinate c =new Coordinate(-1, -1);
                        
                        outerloop:
                        for (int x = 0; x <shape.length; x++) {
                            for (int y = 0; y < shape[0].length; y++) {
                                
                                if(shape[x][y]!=0){
                                   c.setX(x);
                                   break outerloop;
                                }
                                
                            }
                        }
                        outerloop:
                        for (int x = 0; x <shape[0].length; x++) {
                            for (int y = 0; y < shape.length; y++) {
                               
                                if(shape[y][x]==1){
                                   c.setY(x);
                                   break outerloop;
                                }
                                
                            }
                        }
                        
                        Piece piece=new Piece(Helper.submatrix(shape), id, 'R', c);
                        map.put(stringShape, piece);

                    }
                }
                
                
            }
            
        }
     }





    public String fieldToString(int[][] field) {
		String s = "";
		// Iterate through the 2D array to build the string representation
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
				s += field[i][j];
			}
		}
		return s; // Return the concatenated string representing the game field
	}

    public int[][] stringToField(String s) {
		int[][] field = new int[board.getField().length][board.getField()[0].length];
		// Iterate through the 2D array to build the string representation
        int index =0;
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[0].length; j++) {
                if(s.charAt(index)=='-'){
                    index++;
                    field[i][j]= Integer.parseInt(s.charAt(index)+"")*-1;
                }
                else{
				field[i][j]= Integer.parseInt(s.charAt(index)+"");
                index++;
                }
			}
		}
		return field; // Return the concatenated string representing the game field
	}


    public void moveRight() {
        int x = board.getCurrentPiece().getCoordinate().x+1;
        int y = board.getCurrentPiece().getCoordinate().y;
        if(isPlaceable(board.getCurrentPiece(), new Coordinate(x, y))){
            board.removePiece(board.getCurrentPiece());
            board.getCurrentPiece().setCoordinate(new Coordinate(x, y));
            board.addPiece(board.getCurrentPiece(),board.getCurrentPiece().getCoordinate());
        }
    }

    public void moveLeft() {
        int x = board.getCurrentPiece().getCoordinate().x-1;
        int y = board.getCurrentPiece().getCoordinate().y;
        if(isPlaceable(board.getCurrentPiece(), new Coordinate(x, y))){
            board.removePiece(board.getCurrentPiece());
            board.getCurrentPiece().setCoordinate(new Coordinate(x, y));
            board.addPiece(board.getCurrentPiece(),board.getCurrentPiece().getCoordinate());
        }
    }

    public void moveDown() {
        int x = board.getCurrentPiece().getCoordinate().x;
        int y = board.getCurrentPiece().getCoordinate().y+1;
        if(isPlaceable(board.getCurrentPiece(), new Coordinate(x, y))){
            board.removePiece(board.getCurrentPiece());
            board.getCurrentPiece().setCoordinate(new Coordinate(x, y));
            board.addPiece(board.getCurrentPiece(), board.getCurrentPiece().getCoordinate());
            moveDown();
        }
        else
            return;
    }

    public void moveDownOne() {
        int x = board.getCurrentPiece().getCoordinate().x;
        int y = board.getCurrentPiece().getCoordinate().y+1;
        if(isPlaceable(board.getCurrentPiece(), new Coordinate(x, y))){
            board.removePiece(board.getCurrentPiece());
            board.getCurrentPiece().setCoordinate(new Coordinate(x, y));
            board.addPiece(board.getCurrentPiece(), board.getCurrentPiece().getCoordinate());
            
        }
    }

    public void rotate() {

        Piece currentPiece = board.getCurrentPiece();
    Piece rotatedPiece = currentPiece.copy();
    int[][] rotatedShape = Helper.rotate(rotatedPiece.getShape());
    rotatedPiece.setShape(rotatedShape);
    int x = currentPiece.getCoordinate().x;
    int y = currentPiece.getCoordinate().y;

    // Check if the rotated piece is placeable
    if (isPlaceableRotation(rotatedPiece, new Coordinate(x, y))) {
        // If placeable, remove the current piece and add the rotated one
        board.removePiece(currentPiece);
        board.addPiece(rotatedPiece, rotatedPiece.getCoordinate());
    }
    else if(isPlaceable(rotatedPiece, new Coordinate(x+1, y))){
        board.removePiece(currentPiece);
        board.addPiece(rotatedPiece, rotatedPiece.getCoordinate());
        }
    }

    public int getClearedLines() {
        return clearedLines;
    }



    public void moveMostLeft() {
        int x = board.getCurrentPiece().getCoordinate().x-1;
        int y = board.getCurrentPiece().getCoordinate().y;
        if(isPlaceable(board.getCurrentPiece(), new Coordinate(x, y))){
            board.removePiece(board.getCurrentPiece());
            board.getCurrentPiece().setCoordinate(new Coordinate(x, y));
            board.addPiece(board.getCurrentPiece(),board.getCurrentPiece().getCoordinate());
            moveMostLeft();
        }
        else return;
    }



    public void moveUp() {
        int x = board.getCurrentPiece().getCoordinate().x;
        int y = board.getCurrentPiece().getCoordinate().y-1;
        if(isPlaceable(board.getCurrentPiece(), new Coordinate(x, y))){
            board.removePiece(board.getCurrentPiece());
            board.getCurrentPiece().setCoordinate(new Coordinate(x, y));
            board.addPiece(board.getCurrentPiece(), board.getCurrentPiece().getCoordinate());
            moveUp();
        }
        else
            return;
    }

    public void addPiece(Piece piece,Coordinate c){
        board.addPiece(piece, c);
    }
    

    public void removePiece(Piece piece){
        board.removePiece(piece);
    }

    public void setBoard(Board board) {
        this.board = board;
    }



    public void setClearedLines(int i) {
        this.clearedLines = i;
    }



    public Piece getCurrentPiece() {
        return board.getCurrentPiece();
    }

    public Board getBoard() {
        return board;
    }



    public void setScore(int score) {
        this.score = score;
    }



    public void setMap(Map<String, Piece> map) {
        this.map = map;
    }



    public void setClearedIndeces(List<Integer> clearedIndeces) {
        this.clearedIndeces = clearedIndeces;
    }



    public void setCas(boolean cas) {
        this.cas = cas;
    }

    public int getScore() {
        return score;
    }

}
