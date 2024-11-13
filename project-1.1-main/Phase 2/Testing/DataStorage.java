package Testing;

import java.util.HashMap;

public class DataStorage {

    private static final HashMap<Integer, Integer> scoreStorage = new HashMap<>();
    private static int runCounter = 0; 

    public static void addScore(int score) {
        scoreStorage.put(runCounter++, score); 
        System.out.println(scoreStorage);
        System.out.println("okaokaoakoa");
    }

    public static int getScoreData(int run) {
        return scoreStorage.getOrDefault(run, -1); 
    }


public Object getBotData(int run){
    return scoreStorage.get(run);
}

}
 