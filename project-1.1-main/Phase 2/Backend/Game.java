package Backend;

import Bots.AdvancedBot;
import Bots.BasicBot;
import Bots.Bot;
import Bots.DummyBot;
import Frontend.AudioPlayer;
import Frontend.GameOverScene;
import Frontend.GameScene;
import Frontend.MyScene;
import Listener.KeyboardListener;
import Utilities.Data;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.*;
import java.io.*;
public class Game extends AnimationTimer {
    
    Board board;
    Logic logic;
    int width,height;
    Piece piece;
    Piece nextPiece;

    KeyboardListener listener;
    GameScene gui;
    public boolean keyboardOn;
    Bot bot;
    char[] genes;
    String playerName;
    int level = 90;
    String name;
    Stage stage;
    public Game(int width,int height,GameScene gui,int level,Stage stage, String playername){
        this.width = width;
        this.height = height;
        this.stage = stage;
        board = new Board(width, height);
        logic = new Logic(board,this);
        
        this.gui = gui;
        listener = new KeyboardListener(this,gui.getScene());
        startPlayer = true;
        startBot = false;
        this.playerName = playername;
        frameInterval = 100000000000L/level;
        
    }
    

    public Game(String botName,int width,int height,GameScene gui,int level,Stage stage){
        this.width = width;
        this.height = height;
        this.stage = stage;
        board = new Board(width, height);
        logic = new Logic(board,this);
        
        this.gui = gui;
        startPlayer =false;
        startBot = true;
        keyboardOn = true;
        if(botName.equalsIgnoreCase("Dummy")||botName.equalsIgnoreCase("D"))
            this.bot = new DummyBot(this);
        else if(botName.equalsIgnoreCase("BasicBot")||botName.equalsIgnoreCase("B")){
            Scanner scanner;
                try {
                    scanner = new Scanner(new File("BasicBot1.csv"));
                    List<String> list = new ArrayList<>();

                    while (scanner.hasNext()) {
                        list.add(scanner.nextLine());
                    }
                    String line = list.get(4);

                String[] split = line.split(",");
                
                this.genes = new char[12];
                for (int i = 0; i < split.length; i++) {
                    
                        this.genes[i] = split[i].charAt(0);
                }
                
                
                this.bot = new BasicBot(this,gui);
                scanner.close();
            
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                
            
                
            
            
        }
        else if(botName.equalsIgnoreCase("AdvancedBot")||botName.equalsIgnoreCase("a"))
            {
                Scanner scanner;
                try {
                    scanner = new Scanner(new File("AdvancedBot1.csv"));
                    List<String> list = new ArrayList<>();
                    while (scanner.hasNext()) {
                        list.add(scanner.nextLine());
                    }
                    String line = list.get(1);
                    
                String[] split = line.split(",");
                double[] vector= new double[4];
                this.genes = new char[12];
                for (int i = 0; i < split.length; i++) {
                    if(i<4){
                        vector[i] = Double.parseDouble(split[i]);}
                    else
                        this.genes[i-4] = split[i].charAt(0);
                }
                
                
                this.bot = new AdvancedBot(this,gui);
                ((AdvancedBot) bot).setVector(vector);
                scanner.close();
            
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                
            
                
            }
            frameInterval = 100000000000L/level;
            playerName = botName;
    }
    public Game(String botName,int width,int height){
        this.width = width;
        this.height = height;
        board = new Board(width, height);
        logic = new Logic(board,this);
        
        startPlayer =false;
        startBot = true;
        keyboardOn = true;
        if(botName.equalsIgnoreCase("Dummy")||botName.equalsIgnoreCase("D"))
            this.bot = new DummyBot(this);
        else if(botName.equalsIgnoreCase("BasicBot")||botName.equalsIgnoreCase("B"))
            this.bot = new BasicBot(this,gui);
        else if(botName.equalsIgnoreCase("AdvancedBot")||botName.equalsIgnoreCase("a")){
            this.bot = new AdvancedBot(this,gui);
        }
        playerName = botName;

    }

    public Game(String botName,int width,int height,Character[] genes, Stage stage,double[] vector){
        this.width = width;
        this.height = height;
        board = new Board(width, height);
        logic = new Logic(board,this);
        
        startPlayer =false;
        startBot = true;
        keyboardOn = true;
        if(botName.equalsIgnoreCase("Dummy")||botName.equalsIgnoreCase("D"))
            this.bot = new DummyBot(this);
        else if(botName.equalsIgnoreCase("BasicBot")||botName.equalsIgnoreCase("B"))
            this.bot = new BasicBot(this,gui);
        else if(botName.equalsIgnoreCase("AdvancedBot")||botName.equalsIgnoreCase("a")){
            this.bot = new AdvancedBot(this,gui);
            ((AdvancedBot)bot).setVector(vector);
        }
        this.genes = new char[genes.length];
        for (int i = 0; i < genes.length; i++) {
            this.genes[i] = genes[i];
        }
    }




    


    private long lastUpdateTime = 0;
    private long frameInterval = 100000000000L / 90 ;

    boolean isNewPiece = true;
    boolean startPlayer;
    public boolean startBot;
    @Override
    public void handle(long currentTime) {
        if(startPlayer){
            runGame(currentTime);
            
        }
        else if(startBot){
            runBot(currentTime);
            
        }
    }



    public void setStart(boolean start) {
        this.startPlayer = start;
    }


    private void runGame(long currentTime) {
        if (currentTime - lastUpdateTime >= frameInterval) {
            
            piece = board.getCurrentPiece();
            
            if(isNewPiece){
                initPieces();
                 if(logic.isPlaceable(piece,isNewPiece)){
                    keyboardOn = false;
                    board.addPiece(piece,nextPiece);
                    
                    keyboardOn = true;
                    isNewPiece = false;              
                    lastUpdateTime = currentTime;
                    logic.clearRow();
                    keyboardOn = true;
                    updateGUI();
                    return;
                }
                else {
                    this.stop();
                    gameOver();
                }
                
            }
            keyboardOn = true;
            Coordinate coordinate = piece.getCoordinate();
            int y =coordinate.y;
            int x =coordinate.x;
            
            if(logic.isPlaceable(piece,new Coordinate(x,y+1)))
            {
                keyboardOn = false;
                board.removePiece(piece);
                y++;        
                piece.setCoordinate(new Coordinate(coordinate.x,y));
                board.addPiece(piece,piece.getCoordinate());
                keyboardOn = true;
                            
            }
            else{
                keyboardOn = false;
                isNewPiece = true;
                logic.clearRow();
            }
                
            lastUpdateTime = currentTime;
            updateGUI();
        
        }
    }



    private void gameOver() {
        
        try {
            AudioPlayer player = new AudioPlayer("Phase 2/Assetes/gameover.wav");
            player.play();
            BufferedWriter writer = new BufferedWriter(new FileWriter("Players.txt",true));
            String s = playerName+" : "+ getScore()+"\n";
            writer.write(s);
            writer.close();
            MyScene gameoverScene = new GameOverScene(15, 16,stage,playerName);
            gui.musicOff();
            
            stage.setScene(gameoverScene.getScene());
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void runBot(long currentTime){
        if (currentTime - lastUpdateTime >= frameInterval) {
            piece = board.getCurrentPiece();
            
            if(isNewPiece){
                initPieces();
                if(logic.isPlaceable(piece,isNewPiece)){
                   
                    addPieceToBoard();
                    
                    updateGUI();
                    return;
                }
                else {
                    this.stop();
                    gameOver();
                    return;
                }
                
            }
            movePieceDown();
                
            lastUpdateTime = currentTime;
            updateGUI();
        
        }
    }


    public boolean  runFitnessBot(){
        
            
            piece = board.getCurrentPiece();
            
            if(isNewPiece){
                initPieces();
                if(logic.isPlaceable(piece,isNewPiece)){
                   
                    addPieceToBoard();
                    
                    bot.play();
                    return false;
                }
                else {
                    this.stop();
                    return true;
                }
                
            }
            movePieceDown();
                
            return false;
        
        
    }

    private void movePieceDown() {
        Coordinate coordinate = piece.getCoordinate();
        int y =coordinate.y;
        int x =coordinate.x;
        
        if(logic.isPlaceable(piece,new Coordinate(x,y+1)))
        {
            
            movePieceNextPos(coordinate, y);
                        
        }
        else{
            keyboardOn = false;
            isNewPiece = true;
            logic.clearRow();
        }
    }

    private void movePieceNextPos(Coordinate coordinate, int y) {
        board.removePiece(piece);
        y++;        
        piece.setCoordinate(new Coordinate(coordinate.x,y));
        board.addPiece(piece,piece.getCoordinate());
        bot.play();
    }

    private void addPieceToBoard() {
        board.addPiece(piece,nextPiece);
        
        
        isNewPiece = false;          
        logic.clearRow();
    }

    private void initPieces() {
        if(piece==null)
            piece = getRandomPiece();
        else
            piece = nextPiece;

        nextPiece = getRandomPiece();
    }


    public void updateGUI() {
        gui.setState(board.getField());
    }
    
    public void updateGUI(int[][] field) {
        gui.setState(field);
    }


    int counter = 0;
    private Piece getRandomPiece() {
        
        if(genes != null) {
            if(counter==12) counter = 0;
            char name  = genes[counter];
            int[][] shape = Data.getShape(name);
            int id = Data.getID(name);
            int fix = id==1 ? 2:1;
            Coordinate coordinate = new Coordinate((height/2)-fix,0);
            counter++;
            return new Piece(shape, id, name, coordinate);
        }
        
        else{
            int r = new Random().nextInt(12);
            int[][] shape = Data.getShape(r);
            int fix = r==1 ? 2:1;
            Coordinate coordinate = new Coordinate((height/2)-fix,0);
            return new Piece(shape, r, Data.PENTLETTERS[r],coordinate);
        }
    }



    public char getNextPieceName() {
        return nextPiece.getName();
    }


    public Piece getPiece() {
        return board.getCurrentPiece();
    }
    
    public Piece getNextPiece() {
        return board.getCurrentPiece();
    }



    public void moveLeft() {
        if(keyboardOn){
        logic.moveLeft();
        updateGUI();
}
    }



    public void moveRight() {
        if(keyboardOn){
        logic.moveRight();
    updateGUI();}
    }



    public void moveDown() {
        if(keyboardOn|| startBot)  {
        logic.moveDown();
//    updateGUI();
}
    }

    public void moveDownOne(){
        if(keyboardOn|| startBot)  {
        logic.moveDownOne();
    updateGUI();}
    }

    public void rotate() {
        if(keyboardOn || startBot){
        logic.rotate();
    // updateGUI();
}
    }


    
    public Scene getScene() {
        return gui.getScene();
    }



    public Board getBoard() {
        return board;
    }



    public void setBoard(Board board) {
        this.board = board;
    }



    public Logic getLogic() {
        return logic;
    }



   


    public int getWidth() {
        return width;
    }



    
    public int getHeight() {
        return height;
    }







    




    public boolean isKeyboardOn() {
        return keyboardOn;
    }



    public void setKeyboardOn(boolean keyboardOn) {
        this.keyboardOn = keyboardOn;
    }




    public long getFrameInterval() {
        return frameInterval;
    }



    public void setFrameInterval(long frameInterval) {
        this.frameInterval = frameInterval;
    }



    public boolean isNewPiece() {
        return isNewPiece;
    }


    
    public int getScore(){
       return logic.getScore();
    }

    public void restart(char[] genes){
        board = new Board(width, height);
        logic = new Logic(board,this);
        counter = 0;
        isNewPiece = true;
        piece = null;

        nextPiece = null;
        keyboardOn = true;
        board.setCurrentPiece(null);
        if(listener!=null)
            listener = new KeyboardListener(this,gui.getScene());
        if(this.genes!= null){
            setGenes(genes);
        }
        
        
    }

    public void setGenes(char[] genes) {
        this.genes = new char[genes.length];
            for (int i = 0; i < genes.length; i++) {
                this.genes[i] = genes[i];
            }
    }

    public void setVector(double[] genes2) {
       ((AdvancedBot) bot).setVector(genes2);
    }
}
