package Testing;

import Backend.Game;

public class testing {
    

public static void main(String[] args) {
    
   

        int max = 0;
        for (int i = 0; i <5000 ; i++) {
            
            Game gameAdvanced = new Game("a", 15, 5);
            double[] vector = {-0.005964869288861863,0.011203137719682288,-0.01573291703777835,-0.013968605801162215};
            gameAdvanced.setVector(vector);
            while (!gameAdvanced.runFitnessBot()) {
                
            } 
            if(gameAdvanced.getScore()> max) {max = gameAdvanced.getScore();System.out.println(max);}

            printProgress(i/10);
        }
        System.out.println(max);
        
    
    
    
}

private static void printProgress(int progress) {
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
    System.out.print("\u001B[31m");
    System.out.println("Training bot in progress "+progress+"%");
    for(int i = 0; i < progress; i++){
        System.out.print("█");
        
    }
    System.out.println("\u001B[0m");
}

}
