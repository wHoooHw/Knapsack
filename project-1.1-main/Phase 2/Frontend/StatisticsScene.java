package Frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class StatisticsScene extends MyScene {
    ListView<String> list;
    Stage stage;
    public StatisticsScene(int width, int height, Stage stage) {
        super(width, height);
        this.stage = stage;
        drawScene();
        stage.setResizable(false);
        
    }





    @Override
    public void drawScene() {
        File file = new File("Players.txt");
        list=new ListView<>();
        list.setTranslateY(23);
        root.getChildren().add(list);
        Map<String,Integer> map = new HashMap<>();
        try {
            Scanner scanner = new Scanner(file);
            
            while (scanner.hasNextLine()) {
                String[] line =scanner.nextLine().split(": ");
                int value = Integer.parseInt(line[1]);
                if(!map.containsKey(line[0])){
                    map.put(line[0],value);
                }
            }

            Map<String,Integer> sorted = sortByValues(map);
           
            for (String s : sorted.keySet()) {
                list.getItems().add(s +": "+ sorted.get(s));
                
            }

            scanner.close();
            
            
        } catch (FileNotFoundException e) {
            System.out.println("errrrror");
        }
        
        Rectangle r = new Rectangle(300,25);
        r.setFill(Color.BLACK);
        Button menuBar = new Button("Back");
      
        
        menuBar.setTranslateX(100);
        menuBar.setBackground(Background.fill(Color.BLUE));
        menuBar.setOnMouseClicked(e -> {stage.setScene(new IntroScene(15, 15, stage).getScene());});
        
        root.getChildren().add(r);
        root.getChildren().add(menuBar);
        
    }

    private Map<String, Integer> sortByValues(Map<String, Integer> map) {

        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(map.entrySet());

        Comparator<Map.Entry<String, Integer>> valueComparator = Comparator.comparing(Map.Entry::getValue);


        entryList.sort(valueComparator.reversed());
        
        Map<String, Integer> sortedMap = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry : entryList) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}

