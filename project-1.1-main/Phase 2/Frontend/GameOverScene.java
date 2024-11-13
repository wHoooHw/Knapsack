package Frontend;

import Backend.Game;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameOverScene extends MyScene{

Stage stage; 
int Height = 46;
int Width = 52;
int level = 90; 
int size = 50;
int windowSize = 16; 
// GameOverScene gameOverScene;
String name;
    public GameOverScene(int width, int height, Stage stage,String name) {

        super(width, height);
        this.stage = stage;
        this.name = name;
        stage.setTitle("GAME OVER");
        background();
        GameOverText();
        
        quitButton = new Button("QUIT");
        repeatButton = new Button("REPEAT");
        goBackButton = new Button("HOME"); 
        quitButton();
        restartButton();
        goBackButton();
    }

    private void GameOverText() {
        Text gameOverText = new Text("GAME OVER");
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 100)); 
        gameOverText.setFill(Color.WHITESMOKE);
        gameOverText.setTranslateX(75); 
        gameOverText.setTranslateY(300);
        root.getChildren().add(gameOverText);
    }
    
    @Override
    public void drawScene() {
      
    }

    @Override
    public Scene getScene() {
        return super.getScene();
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
        quitButton.setScaleX(2);
        quitButton.setScaleY(2);
        quitButton.setTranslateX(590);
        quitButton.setTranslateY(600);
        disableFocus();
        root.getChildren().add(quitButton);
        quitButton.setOnAction(e -> {
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


    Button repeatButton;
    private void restartButton(){
        
        repeatButton.setOnAction(e -> {
            GameScene gameScene = new GameScene(width, height,stage, name);
            Game game;
            if(name.equalsIgnoreCase("a")||name.equalsIgnoreCase("advancedbot")||name.equalsIgnoreCase("b")||name.equalsIgnoreCase("basicbot")||name.equalsIgnoreCase("d")||name.equalsIgnoreCase("dummy")){
                game = new Game(name,15,5,gameScene, level, stage);
                gameScene.setGame(game);
            }
            else{
                game = new Game(15,5,gameScene, level,stage,name);
                gameScene.setGame(game);
            }
    
            stage.setScene(gameScene.getScene());
            game.start();
        });

        repeatButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        repeatButton.setBackground(new Background(new BackgroundFill(Color.GOLD, new CornerRadii(5), Insets.EMPTY)));
        repeatButton.setTextFill(Color.WHITE);
        repeatButton.setScaleX(2);
        repeatButton.setScaleY(2);
        repeatButton.setTranslateX(100);
        repeatButton.setTranslateY(600);
        disableFocus();
        root.getChildren().add(repeatButton);
    }

    Button goBackButton;   
    public void goBackButton(){
        
        disableFocus();
        goBackButton.setOnAction(e -> {
        IntroScene introScene = new IntroScene(windowSize, windowSize, stage);  
        stage.setScene(introScene.getScene());
        });
        goBackButton.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        goBackButton.setBackground(new Background(new BackgroundFill(Color.ROSYBROWN, new CornerRadii(5), Insets.EMPTY)));
        goBackButton.setTextFill(Color.WHITE);
        goBackButton.setScaleX(2);
        goBackButton.setScaleY(2);
        goBackButton.setTranslateX(350);
        goBackButton.setTranslateY(600);
        root.getChildren().add(goBackButton);
    }

    private void disableFocus() {
        quitButton.setFocusTraversable(false);
        repeatButton.setFocusTraversable(false);
    }
}
