package Frontend;

import javafx.scene.Group;
import javafx.scene.Scene;

public abstract class MyScene {
    Group root;
    Scene scene;
    int width,height;
    public MyScene(int width,int height) {
        this.width = width;
        this.height= height;
        root = new Group();
        root.getChildren();

        scene = new Scene(root,width*50,height*50);
    }


    abstract public void drawScene();

  
    public Scene getScene() {
        
        return scene;
    }

    
    
}
