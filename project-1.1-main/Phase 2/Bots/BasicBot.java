package Bots;

import Frontend.GameScene;
import  Backend.Board;
import  Backend.Game;
import  Backend.Logic;
import  Backend.Piece;

import java.util.Map;
import java.util.HashMap;


public class BasicBot extends Bot{

    Map<Integer,Piece> highestCol;
    Map<Integer,Piece> clearedLines;
    GameScene gui;
    public BasicBot(Game game,GameScene gui) {
        super(game);
        highestCol = new HashMap<>();
        clearedLines = new HashMap<>();
        this.gui = gui;
    }

    @Override
    public void play() {
        highestCol.clear();
        clearedLines.clear();
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
                
                highestCol.put(findHighestColumn(logic.getBoard().getField()),piece);
                logic.clearRow();
                
                clearedLines.put(logic.getClearedLines(), piece);
                logic.setClearedLines(0);
                
                logic.setBoard(copyField);
                logic.moveRight();
                logic.setScore(0);

            }
            
            logic.moveMostLeft();
            logic.rotate();
        }

        

        int hieghst =0;
        boolean nothingCleared = false;
        for (int integer : clearedLines.keySet()) {
            if(integer>hieghst) hieghst = integer;
        }
        if(hieghst ==0){ 
            nothingCleared = true;
            for (int integer : highestCol.keySet()) {

                if(integer>hieghst) hieghst = integer;
            }
        }
        game.getLogic().removePiece(game.getPiece());
        if(nothingCleared){
            
            game.getLogic().addPiece(highestCol.get(hieghst),highestCol.get(hieghst).getCoordinate());
        }
        else{
            game.getLogic().addPiece(clearedLines.get(hieghst),clearedLines.get(hieghst).getCoordinate());
        }

    }

    

    

    
       
        
        

        public int findHighestColumn(int[][] board){
            int counter =999;
            
            for(int i=0;i<board.length;i++){
                loop:
                for(int j=0;j<board[0].length;j++){
                    if(board[i][j]!=-1){
                        if(counter > j) counter = j;
                        break loop;
                    }
                }
            }
            return counter;
        }

      
}
