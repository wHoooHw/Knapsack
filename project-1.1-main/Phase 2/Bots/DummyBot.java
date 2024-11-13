package Bots;

import java.util.Random;

import  Backend.Coordinate;
import  Backend.Game;
public class DummyBot extends Bot {
    
    Random rand;
    public DummyBot(Game game) {
        super(game);
        rand = new Random();
    }
    boolean newPiece = true;
    private void randomMove() {
        
        int randomRotate = rand.nextInt(3);

        int randomX = rand.nextInt(game.getHeight());
        if(newPiece)
            game.getLogic().removePiece(game.getPiece());
        game.getPiece().setCoordinate(new Coordinate(randomX, 0));
        
        if(game.getLogic().isPlaceable(game.getPiece(), new Coordinate(randomX, 0))){
            for (int i = 0; i < randomRotate; i++) {
                game.rotate();
            }
            game.getLogic().addPiece(game.getPiece(),game.getPiece().getCoordinate());
            game.moveDown();
            return;
        }

        else{
            newPiece =false;
            randomMove();
            newPiece = true;
        }

        
        

        
        
        
        
        
    }

    @Override
    public void play() {
        randomMove();
    }

    
}

