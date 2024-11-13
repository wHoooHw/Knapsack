package Frontend;

import  Backend.Game;
import Utilities.Data;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GameScene extends MyScene {
    
private AudioPlayer audioPlayer;
private boolean isInitialized = false;
private Group nextPieceGroup;

    Stage stage;
    public static boolean paused = false;
    int size = 50;
    int[][] state;
    char nextPiece;
    int sizeHeight = 46;
    int sizeWidth = 52;
    Group gameBoard;
    String bot = "p";
    Game game;
    int level = 90;

    String name;
  
    public GameScene(int width, int height, Stage stage,String name) {
        
        super(width, height);
        this.stage = stage;
        this.name = name;
        pause = new Button("Pause");
        quitButton = new Button("QUIT");
        repeatButton = new Button("REPEAT");
        goBackButton = new Button("BACK"); 
        background();
      

        if (!isInitialized) {
            initializePieces();
            isInitialized = true;
        }
     


        
        state = new int[5][15];
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                state[i][j] = -1;
            }
        }

        gameBoard = new Group();

        root.getChildren().add(gameBoard);
        
     
        nextPieceGroup();
    }

    private void initializePieces(){
        pauseButton();
        goBackButton();
        restartButton();
        quitButton();
        musicOn();
        animationDance();
        animationSad();
    }


    @Override
    public void drawScene() {
        
        gameBoard.getChildren().clear();
        Rectangle infoBox = new Rectangle(350, 100, 300,400);
                infoBox.setFill(Color.BLACK);
                infoBox.setStroke(Color.WHITE);
                gameBoard.getChildren().add(infoBox);
    
        for (int i = 0; i <= state.length; i++)
        {
            Line l =new Line(i * size, 0, i * size, state[0].length * size);
            l.setStroke(Color.WHITE);
            gameBoard.getChildren().add(l);
        }
        for (int i = 0; i <= state[0].length; i++)
        {
            Line l =new Line(0, i * size, state.length * size, i * size);
            l.setStroke(Color.WHITE);
            gameBoard.getChildren().add(l);
        }
       for (int i = 0; i < state.length; i++)
        {
            for (int j = 0; j < state[0].length; j++)
            {
                if(state[i][j] == -1) {
                    continue;
                };

                Rectangle r = new Rectangle(i * size + 1, j * size + 1, size - 1, size - 1);
                
                r.setFill(getColorOfID(state[i][j]));
                gameBoard.getChildren().add(r);

                
    
      
        
            }
        }
        if(game!= null){

            Text scoreText = new Text("Score = " + game.getScore());
            scoreText.setFont(new Font(35)); 
            scoreText.setTranslateX(400); 
            scoreText.setTranslateY(150);
            scoreText.setFill(Color.BEIGE);
            gameBoard.getChildren().add(scoreText);  
            
            Text gameLevel = new Text("Level = " + game.getScore());
            scoreText.setFont(new Font(35)); 
            scoreText.setTranslateX(400); 
            scoreText.setTranslateY(140);
            gameBoard.getChildren().add(gameLevel);  


            nextPieceGroup.getChildren().clear();
            char nextPiece = game.getNextPieceName(); 
            updateNextPieceDisplay(nextPiece);
        }
    }


    private void nextPieceGroup() {
       
        nextPieceGroup = new Group();
        nextPieceGroup.setTranslateX(420); 
        nextPieceGroup.setTranslateY(250); 
        root.getChildren().add(nextPieceGroup);
    }

    private Color getColorOfID(int i)
    {
        if(i==0) {return Color.BLUE;}
        else if(i==1) {return Color.ORANGE;}
        else if(i==2) {return Color.CYAN;}
        else if(i==3) {return Color.GREEN;}
        else if(i==4) {return Color.MAGENTA;}
        else if(i==5) {return Color.PINK;}
        else if(i==6) {return Color.RED;}
        else if(i==7) {return Color.YELLOW;}
        else if(i==8) {return Color.DARKKHAKI;}
        else if(i==9) {return Color.DARKMAGENTA;}
        else if(i==10) {return Color.SIENNA;}
        else if(i==11) {return Color.MISTYROSE;}
        else if(i==20) {return Color.PINK;}
        else {return Color.GRAY;}
    }
   
    public void setState(int[][] state) {
        if(state == null)
            return;
        for (int i = 0; i < this.state.length; i++)
        {
            for (int j = 0; j < this.state[i].length; j++)
            {
                this.state[i][j] = state[i][j];
            }
        }
        drawScene();
    }

    @Override
    public Scene getScene() {
        return super.getScene();
    }
    
    public void setGame(Game game) {
        this.game = game;
    }


    private void background() {
        Image backgroundImage;
        ImageView backgroundView;
       
        backgroundImage = new Image("/Assetes/tetriswallpaper.jpg");
        backgroundView = new ImageView(backgroundImage);
        backgroundView.setTranslateX(0);
        backgroundView.setTranslateY(0);
        backgroundView.setScaleX(1);
        backgroundView.setScaleY(1);
        root.getChildren().add(backgroundView);
    }   
Button quitButton;
        public void quitButton(){
        
        quitButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        quitButton.setBackground(new Background(new BackgroundFill(Color.GOLDENROD, new CornerRadii(5), Insets.EMPTY)));
        quitButton.setTextFill(Color.WHITE);
        quitButton.setScaleX(1.5);
        quitButton.setScaleY(1.5);
        quitButton.setTranslateX(550);
        quitButton.setTranslateY(600);
        disableFocus();
        root.getChildren().add(quitButton);
        quitButton.setOnAction(e -> {
            musicOff();
        exit(stage);
        });
        }


      
        public void exit (Stage stage){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exiting");
        alert.setHeaderText("You are about to exit Pentris");
        alert.setContentText("Do you really want to exit the game?");

        if(alert.showAndWait().get() == ButtonType.OK){
            System.out.println("You successfully left the Game");
            stage.close();
        }
    }


Button pause;
    public void pauseButton(){
        
        pause.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        pause.setBackground(new Background(new BackgroundFill(Color.RED, new CornerRadii(5), Insets.EMPTY)));
        pause.setTextFill(Color.WHITE);
        pause.setScaleX(1.5);
        pause.setScaleY(1.5);
        pause.setTranslateX(650);
        pause.setTranslateY(600);
        disableFocus();

        root.getChildren().add(pause);
        pause.setOnAction(e->{
            if(game!=null) {
                if(!paused) {game.stop();paused = true;musicOff();} 
                else {game.start();paused = false;musicOn();}
            }
        });
    }

    

    public void musicOn(){
        String musicFile = "Phase 2/Assetes/LittleDoYouKnowCryBaby.wav";
        audioPlayer = new AudioPlayer(musicFile);
        audioPlayer.play();
    }
    public void musicOff(){
        
        audioPlayer.stop();
    }

    Button goBackButton;   
    public void goBackButton(){
        
        disableFocus();
        goBackButton.setOnAction(e -> {
            game.stop();
            musicOff();
            game = null;
        IntroScene introScene = new IntroScene(15, 15, stage);  
        
        stage.setScene(introScene.getScene());
        });
        goBackButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        goBackButton.setBackground(new Background(new BackgroundFill(Color.ROSYBROWN, new CornerRadii(5), Insets.EMPTY)));
        goBackButton.setTextFill(Color.WHITE);
        goBackButton.setScaleX(1.5);
        goBackButton.setScaleY(1.5);
        goBackButton.setTranslateX(440);
        goBackButton.setTranslateY(600);
        root.getChildren().add(goBackButton);
    }
Button repeatButton;
    public void restartButton(){
        
        repeatButton.setOnAction(e -> {
            musicOff();
            this.game.stop();
            this.game = null;
            GameScene gameScene = new GameScene(width, height, stage,name);
           
            if(bot.equals("p")){
                game = new Game(15,5,gameScene, level,stage,name);
                gameScene.setGame(game);
            }
            else{
                game = new Game(bot,15,5,gameScene, level,stage);
                gameScene.setGame(game);
            }
    
            stage.setScene(gameScene.getScene());
            game.start();
        });

        repeatButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        repeatButton.setBackground(new Background(new BackgroundFill(Color.GOLD, new CornerRadii(5), Insets.EMPTY)));
        repeatButton.setTextFill(Color.WHITE);
        repeatButton.setScaleX(1.5);
        repeatButton.setScaleY(1.5);
        repeatButton.setTranslateX(300);
        repeatButton.setTranslateY(600);
        disableFocus();
        root.getChildren().add(repeatButton);
    }


    private void updateNextPieceDisplay(char nextPiece) {

        int nextPieceID = Data.getID(nextPiece);
        int[][] shape = Data.getShape(nextPiece);
        double cellSize = 40; 

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    Rectangle rect = new Rectangle(i * cellSize, j * cellSize, cellSize, cellSize);
                    rect.setFill(getColorOfID(nextPieceID));
                    nextPieceGroup.getChildren().add(rect);
                }
            }
        }

    }

    private void disableFocus() {
        pause.setFocusTraversable(false);
        goBackButton.setFocusTraversable(false);
        quitButton.setFocusTraversable(false);
        repeatButton.setFocusTraversable(false);
    }

    private int currentImageIndexDance = 0;
    private int currentXDance = 500;
    private void animationDance(){
        pathesDance = new String[65];
        for (int i = 1; i <= pathesDance.length; i++) {
            if(i<22) 
                pathesDance[i-1] = pathDance+"e" + i+".png";
            else
                pathesDance[i-1] = pathDance + i+".png";
        }
        ImageView imageView = new ImageView();
        root.getChildren().add(imageView);
        AnimationTimer timeline = new AnimationTimer() {
            private long lastUpdateTime = 0;
            private long frameInterval = 10000000000L / 90 ;
            @Override
            public void handle(long currentTime) {
            //    if(currentX < 0) currentX =800;
            if (currentTime - lastUpdateTime >= frameInterval) {
                    if(currentImageIndexDance>=pathesDance.length) currentImageIndexDance = 0;
                    // Load the next image
                    URL url;
                    try {
                        url = new File(pathesDance[currentImageIndexDance]).toURI().toURL();
                         Image nextImage = new Image(url.toExternalForm());
                    imageView.setImage(nextImage); 
                    currentImageIndexDance = (currentImageIndexDance + 1);
                    imageView.setTranslateX(currentXDance);
                    lastUpdateTime = currentTime;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                   
                    
                    // Move to the next image index (looping back to the first image if needed)
                   
            }}
        };
        
        timeline.start();
    }


    private int currentImageIndexSad = 0;
    private int currentXSad = 530;
    private int currentYSad = 520;
    String[] pathesSad;
    private void animationSad(){
        pathesSad = new String[31];
        for (int i = 1; i <= pathesSad.length; i++) {
            pathesSad[i-1] = pathSad + i+".png";
        }
        ImageView imageView = new ImageView();
        imageView.setTranslateX(currentXSad);
        imageView.setTranslateY(currentYSad);
        root.getChildren().add(imageView);
        AnimationTimer timeline = new AnimationTimer() {
            private long lastUpdateTime = 0;
            private long frameInterval = 10000000000L / 90 ;
            @Override
            public void handle(long currentTime) {
            //    if(currentX < 0) currentX =800;
            if (currentTime - lastUpdateTime >= frameInterval) {
                    if(currentImageIndexSad>=pathesSad.length) currentImageIndexSad = 0;
                    // Load the next image
                    URL url;
                    try {
                        url = new File(pathesSad[currentImageIndexSad]).toURI().toURL();
                         Image nextImage = new Image(url.toExternalForm());
                    imageView.setImage(nextImage); 
                    currentImageIndexSad = (currentImageIndexSad + 1);
                    
                    lastUpdateTime = currentTime;
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                   
                    
                    // Move to the next image index (looping back to the first image if needed)
                   
            }}
        };
        
        timeline.start();
    }

   
    String pathDance = "Phase 2/Assetes/Sprite/sprit";
    String pathSad = "Phase 2/Assetes/SpriteSad/sprite";
    

    private String[] pathesDance;
}
