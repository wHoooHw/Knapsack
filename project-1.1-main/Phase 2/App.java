
import Frontend.IntroScene;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;




public class App extends Application {

    
    @Override
    public void start(Stage primaryStage) {

      
        
        IntroScene scene = new IntroScene(15,15, primaryStage);
        primaryStage.setTitle("Tetris Group 40");
        primaryStage.setScene(scene.getScene());
        Image image = new Image("/Assetes/icon.ico");
        primaryStage.getIcons().add(image);
        primaryStage.show();
    }




 public static void main(String[] args) {

        launch(args);
    }    
}



