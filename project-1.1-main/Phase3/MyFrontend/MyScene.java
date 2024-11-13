/* Notes:
 * 1) I decided using Grid Pane rather than vbox so deleted them for more flexible and better grouping
 * 2) Fixed the shape going random places when algorithm changed
 * 3) Deleted initButtonEng method and combined everything inside "initElements"
 * 4) Added
 */
package Phase3.MyFrontend;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import Phase1.src.Phase1.Main;
import Phase3.Data3D.MyData;
import Phase3.MyBackend.MySolvers.DLXSolver;
import Phase3.MyBackend.MySolvers.Greedy;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;

public class MyScene {

    private double mouseX, mouseY;
    Scene scene;
    BorderPane borderPane;
    BorderPane mainLayout;
    String algo;
    private boolean dlxSelected = true;
    private String type = "Pe";
    Group group;
    int size = 20;

    Group pentParcels;
    Group algoGroup;

    Label totalValueLabel;

    public MyScene() {

        intGroup();
        pentParcels = new Group();
        algoGroup = new Group();

        borderPane = initBorderPane();

        initElements();
        initLabel();

        initMainLayout();
        scene = new Scene(mainLayout, 1400, 700, Color.BLUEVIOLET);
        mousy();

    }

    private void intGroup() {
        group = new Group();

        group.setTranslateX(20);
        group.setTranslateY(10);
        group.setTranslateZ(90);

        Scale scale = new Scale(0.8, 0.8, 0.8);
        group.getTransforms().add(scale);

    }

    private void initMainLayout() {
        mainLayout = new BorderPane();

        mainLayout.setRight(borderPane);
        mainLayout.setCenter(group);
        BackgroundFill backgroundFill = new BackgroundFill(Color.valueOf("#B4D4FF"), null, null);
        Background background = new Background(backgroundFill);
        mainLayout.setBackground(background);
    }

    private void initLabel() {
        Label Title = new Label("KNAPSACK PROBLEM");

        // Load the custom font
        Font.loadFont(Main.class.getResourceAsStream(
                "/Phase3/MyFrontend/Assets/Fonts/Pathway_Gothic_One/PathwayGothicOne-Regular.ttf"),
                14);

        Title.setStyle(
                "-fx-font-family: 'Pathway Gothic One'; -fx-font-weight: bold ;-fx-font-size: 38; -fx-text-fill: white; -fx-letter-spacing: 0.8em;");

        BorderPane.setAlignment(Title, Pos.CENTER); // Center the text
        borderPane.setPadding(new Insets(14, 0, 0, 0));
        borderPane.setTop(Title);

    }

    private void initElements() {
        ComboBox<String> botList = initAlgoList();
        ComboBox<String> shapeList = initShapList();

        // Apply custom styles to ComboBoxes
        botList.setStyle("-fx-background-color: #ffffff; -fx-text-fill: white;");
        shapeList.setStyle("-fx-background-color: #ffffff; -fx-text-fill: white;");

        // Create a button and style it
        Button searchButton = new Button("Search");
        searchButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(40, 14, 5, 14)); // Add padding around the GridPane
        gridPane.setHgap(10); // Set horizontal gap between columns
        gridPane.setVgap(10); // Set vertical gap between rows
        gridPane.addRow(0, botList, shapeList);
        gridPane.addRow(1, algoGroup, pentParcels);
        gridPane.addRow(2, searchButton); // Add the search button to the grid in the second column, second row

        GridPane.setColumnSpan(searchButton, 2); // Span the button across two columns
        GridPane.setHalignment(searchButton, HPos.CENTER); // Center align the button

        totalValueLabel = new Label("Total Value: ");
        totalValueLabel.setStyle("-fx-text-fill: white;");
        totalValueLabel.setVisible(false); // Initially hide the label

        totalValueLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24)); // Font settings

        borderPane.setLeft(gridPane);

        shapeList.setOnAction(e -> {
            String selectedShape = shapeList.getValue();

            if ("Pentominos".equals(selectedShape)) {
                type = "Pe";
                drawPentominos();
            } else if ("Parcels".equals(selectedShape)) {
                type = "P";
                drawParcels();
            }
        });

        botList.setOnAction(e -> {
            String selectedShape = botList.getValue();
            if (selectedShape.equals("Greedy")) {
                dlxSelected = false;
                drawGreed();
            } else if (selectedShape.equals("Dancing Links")) {
                dlxSelected = true;
                drawDancing();
            }
        });

        searchButton.setOnAction(e -> {
            if (dlxSelected) {
                DLXSolver solver;
                if (type.equals("P"))
                    solver = new DLXSolver(type, MyData.indecesParc);
                else
                    solver = new DLXSolver("Pe", MyData.indecesPent);
                solver.solve();
                int[][][] solution = solver.bestSolution();
                initCube(solution);
                solver.getText();
                updateTotalValue(solver.getText());
            } else {

                Greedy greedy = new Greedy(type);
                greedy.solve();
                initCube(greedy.getContainer().getField());
                updateTotalValue(greedy.getContainer().usedPieces()+"");
            }

            // Update the position of the label below the cubes
            // totalValueLabel.setTranslateX(88);
            totalValueLabel.setTranslateY(-88);
            totalValueLabel.setVisible(true); // Show the label

            borderPane.setBottom(totalValueLabel);

        });
    }

    private void updateTotalValue(String value) {
        totalValueLabel.setText(value);
    }

    private ComboBox<String> initShapList() {
        ObservableList<String> options2 = FXCollections.observableArrayList("Pentominos", "Parcels");
        ComboBox<String> shapeList = new ComboBox<>(options2);
        shapeList.setValue("Which Shapes?");
        shapeList.setPrefWidth(200);
        return shapeList;
    }

    private ComboBox<String> initAlgoList() {
        ObservableList<String> options1 = FXCollections.observableArrayList("Dancing Links", "Greedy");
        ComboBox<String> botList = new ComboBox<>(options1);
        botList.setValue("Which Algorithm?");
        botList.setPrefWidth(200);
        return botList;
    }

    private BorderPane initBorderPane() {
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: #176B87;");
        borderPane.setPrefWidth(250);
        return borderPane;
    }

    private void mousy() {
        group.setOnMousePressed(event -> {
            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });

        group.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - mouseX;
            double deltaY = event.getSceneY() - mouseY;

            Point3D center = calculateCenter(group);

            Rotate rotateX = new Rotate(-deltaY, center.getX(), center.getY(), center.getZ(), Rotate.X_AXIS);
            Rotate rotateY = new Rotate(deltaX, center.getX(), center.getY(), center.getZ(), Rotate.Y_AXIS);

            group.getTransforms().addAll(rotateX, rotateY);

            mouseX = event.getSceneX();
            mouseY = event.getSceneY();
        });
    }

    private Point3D calculateCenter(Group group) {
        double totalX = 0, totalY = 0, totalZ = 0;
        int count = 0;

        for (Node node : group.getChildren()) {
            if (node instanceof Box) {
                Box box = (Box) node;
                totalX += box.getTranslateX();
                totalY += box.getTranslateY();
                totalZ += box.getTranslateZ();
                count++;
            }
        }

        return new Point3D(totalX / count, totalY / count, totalZ / count);
    }

    public void initCube(int[][][] container) {

        group.getChildren().clear();

        for (int i = 0; i < container.length; i++) {
            for (int j = 0; j < container[0].length; j++) {
                for (int k = 0; k < container[0][0].length; k++) {
                    if (container[i][j][k] == 0)
                        continue;
                    Box box = new Box(size, size, size);
                    box.setTranslateX(i * size - container.length * size / 2.0);
                    box.setTranslateY(j * size - container[0].length * size / 2.0);
                    box.setTranslateZ(k * size - container[0][0].length * size / 2.0);
                    PhongMaterial material = new PhongMaterial();
                    material.setDiffuseColor(getColor(container[i][j][k]));
                    material.setSpecularPower(10);
                    material.setSpecularColor(getColor(container[i][j][k]));
                    box.setMaterial(material);

                    group.getChildren().add(box);
                }
            }
        }

    }

    private Color getColor(int i) {
        if (i == 1) {
            return Color.ORANGE;
        } else if (i == 2) {
            return Color.YELLOW;
        } else if (i == 3) {
            return Color.GREEN;
        } else if (i == 4) {
            return Color.SEAGREEN;
        } else if (i == 5) {
            return Color.BLUE;
        } else if (i == 6) {
            return Color.RED;
        } else
            return Color.GRAY;
    }

    String[] pents = new String[] { "L", "P", "T" };

    void drawPentominos() {

        try {

            pentParcels.getChildren().clear();
            for (int i = 0; i < pents.length; i++) {

                Image image = new Image(new FileInputStream("Phase3/MyFrontend/Assets/" + pents[i] + ".png"));
                ImageView view = new ImageView(image);

                view.setFitHeight(120);
                view.setFitWidth(120);
                view.setPreserveRatio(true);

                if (i == 0) {
                    view.setX(50);
                    view.setY(0);
                } else if (i == 1) {
                    view.setX(100);
                    view.setY(0);
                } else if (i == 2) {
                    view.setX(50);
                    view.setY(55);
                }
                view.setScaleX(1.1);
                view.setScaleY(1.1);
                pentParcels.getChildren().add(view);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    String[] parcels = new String[] { "A", "B", "C" };

    void drawParcels() {
        try {

            pentParcels.getChildren().clear();
            for (int i = 0; i < parcels.length; i++) {

                Image image = new Image(new FileInputStream("Phase3/MyFrontend/Assets/" + parcels[i] + ".png"));
                ImageView view = new ImageView(image);

                view.setFitHeight(100);
                view.setFitWidth(100);
                view.setPreserveRatio(true);

                if (i == 0) {
                    view.setX(20);
                    view.setY(0);
                } else if (i == 1) {
                    view.setX(60);
                    view.setY(8);
                } else if (i == 2) {
                    view.setX(20);
                    view.setY(76);
                }
                view.setScaleX(1.1);
                view.setScaleY(1.1);

                pentParcels.getChildren().add(view);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    void drawGreed() {

        try {

            algoGroup.getChildren().clear();

            Image image = new Image(new FileInputStream("Phase3/MyFrontend/Assets/greedy.gif"));
            ImageView view = new ImageView(image);

            view.setX(110);
            view.setY(0);
            view.setFitHeight(70);
            view.setFitWidth(70);
            view.setScaleX(3); // Scale the image
            view.setScaleY(3); // Scale the image

            // Create a circle to use as a mask
            Circle circleMask = new Circle(30); // Adjust the radius as needed
            circleMask.setCenterX(view.getX() + view.getFitWidth() / 2);
            circleMask.setCenterY(view.getY() + view.getFitHeight() / 2);

            // Set the image as a node with a clip using the circle
            view.setClip(circleMask);

            algoGroup.getChildren().add(view);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    void drawDancing() {
        try {

            algoGroup.getChildren().clear();

            Image image = new Image(new FileInputStream("Phase3/MyFrontend/Assets/dancing.gif"));
            ImageView view = new ImageView(image);

            view.setX(110);
            view.setY(0);
            view.setFitHeight(70);
            view.setFitWidth(70);
            view.setScaleX(3); // Scale the image
            view.setScaleY(3); // Scale the image

            // Create a circle to use as a mask
            Circle circleMask = new Circle(30); // Adjust the radius as needed
            circleMask.setCenterX(view.getX() + view.getFitWidth() / 2);
            circleMask.setCenterY(view.getY() + view.getFitHeight() / 2);

            // Set the image as a node with a clip using the circle
            view.setClip(circleMask);

            algoGroup.getChildren().add(view);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Scene getScene() {
        return scene;
    }
}
