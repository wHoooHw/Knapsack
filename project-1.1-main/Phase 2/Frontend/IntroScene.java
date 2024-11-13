package Frontend;

import Backend.Game;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;

import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class IntroScene extends MyScene {

    public static Stage stage;
    int realWidth;
    int realHeight;
    ImageView logoImage;
    ImageView logoView;
    ImageView startImage;
    ImageView levelView;
    int level = 90;
    public static String bot = "p";

    public IntroScene(int width, int height, Stage stage) {
        super(width, height);
        IntroScene.stage = stage;
        this.realHeight = height * 50;
        this.realWidth = width * 50;

        drawScene();

    }

    private void logo() {
        Image logoImage = new Image("/Assetes/logo-no-background.png");
        ImageView logoView = new ImageView(logoImage);
        logoView.setScaleX(0.5);
        logoView.setScaleY(0.5);
        logoView.setTranslateX(0);
        logoView.setTranslateY(0);
        root.getChildren().add(logoView);
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

    @Override
    public void drawScene() {
        background();
        logo();
        startButton();
        statisticButton();
        bots();
        levelButton();

    }

    private void startButton() {
        Button startButton = new Button("START GAME");
        Image startImage = new Image("/Assetes/startbutton.png");
        ImageView imageView = new ImageView(startImage);
        startButton.setGraphic(imageView);
        startButton.setStyle("-fx-background-color: transparent");
        startButton.setPrefSize(50, 50);
        startButton.setScaleX(0.6);
        startButton.setScaleY(0.6);

        startButton.setOnAction(event -> {
            if (bot.equals("p")) {
                Button enter = new Button("Enter");
                TextField field = new TextField();
                Text text = new Text("Enter your name:");
                text.setTranslateX(20);
                text.setTranslateY(30);
                field.setTranslateY(40);
                enter.setTranslateX(100 / 2);
                enter.setTranslateY(180 / 2);
                Group group = new Group();
                group.getChildren().addAll(enter, field, text);
                Scene sceneInput = new Scene(group, 150, 150);

                Stage stageInput = new Stage();
                stageInput.resizableProperty().set(false);
                stageInput.setScene(sceneInput);

                stage.hide();
                stageInput.show();
                enter.setOnAction(e -> {
                    String name = "#" + field.getText();
                    // Create the new scene (e.g., a new game level or a settings menu)
                    GameScene gameScene = new GameScene(width, height, stage, name);
                    Game game;

                    game = new Game(15, 5, gameScene, level,stage, name);
                    gameScene.setGame(game);

                    stage.setScene(gameScene.getScene());
                    stageInput.hide();
                    stage.show();
                    game.start();

                });
            }

            else {
                GameScene gameScene = new GameScene(width, height, stage, bot);
                Game game;

                game = new Game(bot, 15, 5, gameScene, level, stage);
                gameScene.setGame(game);

                stage.setScene(gameScene.getScene());
                game.start();
            }
        });
        startButton.setTranslateX(-4);
        startButton.setTranslateY(440);
        root.getChildren().add(startButton);
    }

    private void statisticButton() {
        Button statisticButton = new Button("Scores");
        Image statsButton = new Image("/Assetes/statsbutton.png");
        ImageView statGraph = new ImageView(statsButton);
        statisticButton.setGraphic(statGraph);
        statisticButton.setStyle("-fx-background-color: transparent");
        statisticButton.setPrefSize(150, 100);
        statisticButton.setScaleX(0.9);
        statisticButton.setScaleY(0.9);
        statisticButton.setOnAction(event -> {

            StatisticsScene statisticScene = new StatisticsScene(5, 8, stage);
            stage.setScene(statisticScene.getScene());
        });
        statisticButton.setTranslateX(0);
        statisticButton.setTranslateY(380);
        root.getChildren().add(statisticButton);

    }

    public void bots() {
        ContextMenu menu = new ContextMenu();
        MenuItem level1 = new MenuItem("Player");
        MenuItem level2 = new MenuItem("Bot 1");
        MenuItem level3 = new MenuItem("Bot 2");
        MenuItem level4 = new MenuItem("Bot 3");
        menu.getItems().addAll(level1, level2, level3, level4);
        level1.setOnAction(e -> bot = "p");
        level2.setOnAction(e -> bot = "d");
        level3.setOnAction(e -> bot = "b");
        level4.setOnAction(e -> bot = "a");

        Button levelButtonBot = new Button("Bot Level");
        Image botLevel = new Image("/Assetes/botbutton.png");
        ImageView botButton = new ImageView(botLevel);
        levelButtonBot.setGraphic(botButton);
        levelButtonBot.setStyle("-fx-background-color: transparent");
        levelButtonBot.setPrefSize(150, 100);
        levelButtonBot.setOnMouseClicked(e -> menu.show(levelButtonBot, e.getScreenX(), e.getScreenY()));

        levelButtonBot.setTranslateX(-40);
        levelButtonBot.setTranslateY(100);
        levelButtonBot.setScaleX(0.9);
        levelButtonBot.setScaleY(0.9);
        root.getChildren().add(levelButtonBot);
    }

    private void levelButton() {
        // Context Menu as a Level menue to choose from
        ContextMenu levelList = new ContextMenu();
        MenuItem level1 = new MenuItem("Level1");
        MenuItem level3 = new MenuItem("Level3");
        MenuItem level2 = new MenuItem("Level2");
        levelList.getItems().addAll(level1, level2, level3);
        
        // Give the menue items an action
        level1.setOnAction(e -> level = 100);
        level2.setOnAction(e -> level = 400);
        level3.setOnAction(e -> level = 900);

        Button levelButtonDificulty = new Button("Level");
        Image levelImage = new Image("/Assetes/levelsbutton.png");
        ImageView levelButton = new ImageView(levelImage);
        levelButtonDificulty.setGraphic(levelButton);
        levelButtonDificulty.setStyle("-fx-background-color: transparent");
        levelButtonDificulty
                .setOnMouseClicked(e -> levelList.show(levelButtonDificulty, e.getScreenX(), e.getScreenY()));
        levelButtonDificulty.setTranslateX(20);
        levelButtonDificulty.setTranslateY(270);
        levelButtonDificulty.setScaleX(0.9);
        levelButtonDificulty.setScaleY(0.9);
        root.getChildren().add(levelButtonDificulty);
    }

  

}
