package Phase3;

import Phase3.MyFrontend.MyScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main3 extends Application {
  

    // Start method where the application is initialized
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        // Create an instance of MyScene, which represents the user interface
        MyScene myScene = new MyScene();

        // Get the JavaFX Scene from MyScene
        Scene scene = myScene.getScene();
        // Configure the primary stage (window)
        primaryStage.setTitle("Knapsack Problem");
        primaryStage.setScene(scene);

        // Configure a 3D perspective camera for the scene
        scene.setCamera(new javafx.scene.PerspectiveCamera());
        scene.getCamera().setTranslateZ(0);
        
        primaryStage.setResizable(false);
        // Display the primary stage
        primaryStage.show();
    }


    // Entry point of the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}