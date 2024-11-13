package Bots;

import java.util.HashMap;
import java.util.Map;

import Frontend.GameScene;
import  Backend.Board;
import  Backend.Coordinate;
import  Backend.Game;
import  Backend.Logic;
import  Backend.Piece;


public class AdvancedBot extends Bot{

   
    double a;
    double b ;
    double c ;
    double d ;


    Map<Double,Piece> moves;
    public AdvancedBot(Game game,GameScene gui) {
        super(game);
        moves = new HashMap<>();

    }
    

    @Override
    public void play() {
        moves.clear();
        Board board = game.getBoard().copy();
        Piece currentPiece = board.getCurrentPiece();
        int rotations = getRotations(currentPiece);
        Logic logic = new Logic(board);
        logic.moveMostLeft();
        for (int i = 0; i <= rotations; i++) {
            for (int j = 0; j < game.getWidth(); j++) {
                Board copyField = logic.getBoard().copy();
                
                logic.moveDown();
                
                Piece piece= logic.getCurrentPiece().copy();
                
                
                logic.clearRow();
                moves.put(chooseMove(board, logic.getClearedLines()), piece);
                logic.setClearedLines(0);
                
                logic.setBoard(copyField);
                logic.moveRight();
                logic.setScore(0);

            }
            
            logic.moveMostLeft();
            logic.rotate();
        }

        

        double hieghst= -99999;
        game.getLogic().removePiece(game.getPiece());
        for (double possibility : moves.keySet()) {
            
            if(hieghst< possibility) hieghst = possibility;
        }
        try {
             game.getLogic().addPiece(moves.get(hieghst),moves.get(hieghst).getCoordinate());
        } catch (Exception e) {
            System.out.println("error");
        }
       
        
       

    }


    private double chooseMove(Board board,int clearedLines){
        
        return (a*aggregateHeight(board))+(b*clearedLines)+(c*holes(board))+(d*bumpiness(board));
    }
    int aggregateHeight(Board board){
        int height = 0;
        int[][] field = board.getField();
        outerloop:
        for (int i = 0; i < field[0].length; i++) {
            for (int j = 0; j < field.length; j++) {
                if(field[j][i]!=-1){
                    height+= field[0].length - i;
                    continue outerloop;
                }
            }
        }
        return height;
    }

    int holesFloodFill(int[][] field,Coordinate c){
        int holes = 0;
        int x = c.getX();
        int y = c.getY();
        if(x>=field.length||y>=field[0].length||x<0||y<0||field[x][y]!=-1)
            return 0;
        field[x][y] = 20;
        holes++;

        if(x+1<field.length && field[x+1][y]==-1){
            holes+= holesFloodFill(field,new Coordinate(x+1, y));
        }
        if(x-1>=0 && field[x-1][y]==-1){
            holes+= holesFloodFill(field,new Coordinate(x-1, y));
        }
        if(y+1<field[0].length && field[x][y+1]==-1){
            holes+= holesFloodFill(field,new Coordinate(x, y+1));
        }
        if(y-1>=0 && field[x][y-1]==-1){
            holes+= holesFloodFill(field,new Coordinate(x, y-1));
        }

        return holes;
    }

    int holes(Board board){
        int[][] field = board.getCopyField();
        int holes =0;
        for (int i = 0; i < field[0].length; i++) {
            for (int j = 0; j < field.length; j++) {
                holes += holesFloodFill(field, new Coordinate(j, i));
            }
        }
        
        return holes-1;
    }

    int bumpiness(Board board){
        int[][] field = board.getField();
        int bumpiness = 0;
        
        for (int i = 0; i < field[0].length; i++) {
            innerloop:
            for (int j = 0; j < field.length; j++) {
                if(field[j][i]!=-1){
                  bumpiness+= Math.abs(bumpiness- (field[0].length - i));
                  break innerloop;
                }

            }
        }

        return bumpiness;
    }

    public void setVector(double[] vector){
        a= vector[0];
        b= vector[1];
        c= vector[2];
        d= vector[3];
    }
    
}
