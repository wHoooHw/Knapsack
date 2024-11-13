package Bots;

import  Backend.Game;
import  Backend.Piece;

public abstract class Bot {

    Game game;
    
    protected Bot(Game game){
        this.game = game;
    }

    public abstract void play();

    

    protected int getRotations(Piece currentPiece) {
            if(currentPiece.getName()=='X'){
                return 0;
            }
            else if(currentPiece.getName()=='I'){
                return 1;
            }
            else{
                return 3;
            }
            
        }

    
}