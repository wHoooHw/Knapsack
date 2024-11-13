package Listener;


import Frontend.GameScene;
import  Backend.Game;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyboardListener implements EventHandler<KeyEvent>  {

    Game board;
    Scene scene;


    


    public KeyboardListener(Game board,Scene scene) {
        this.board = board;
        this.scene = scene;
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this);
    }




    @Override
    public void handle(KeyEvent key) {
        KeyCode keyCode = key.getCode();
        //before the not was gone
        
        if(keyCode== KeyCode.RIGHT && !GameScene.paused){
            board.moveRight();
        }
        else if(keyCode== KeyCode.LEFT && !GameScene.paused){
            board.moveLeft();
        }
        else if(keyCode== KeyCode.SPACE && !GameScene.paused){
            if (board.isKeyboardOn() || board.startBot) {
                board.moveDown();
                board.updateGUI();
            }
            
        }
        else if(keyCode== KeyCode.SHIFT && !GameScene.paused){
            board.rotate();
        }
        else if(keyCode== KeyCode.DOWN && !GameScene.paused){
            board.moveDownOne();
        }
        
        


        
    }


    
}
