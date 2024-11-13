package Phase1.src.Phase1.DataAndExperiment;

import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

import Phase1.src.Phase1.Solvers.Backtracking;
import Phase1.src.Phase1.Solvers.ISolver;

public class TestMain {
    

    public static void main(String[] args) throws Exception {
        String fileName = "src/DataAndExperiment/PentominoExamples.csv";

        File file = new File(fileName);
    
       
            
            PrintWriter out = new PrintWriter("Tested bad Cases.csv","UTF-8");
            PrintWriter out2 = new PrintWriter("Tested good Cases.csv","UTF-8");
            
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String[] line = scanner.nextLine().split(",");
                int width = Integer.parseInt(line[0]);
                int height = Integer.parseInt(line[1]);
                
                char[] pieces = new char[line.length-2];
                for (int i = 2,j=0; i < line.length; i++,j++) {
                    pieces[j] = line[i].charAt(0);
                }
                System.out.println(pieces);
                System.out.println("width " +width +"height "+height);
                if(width == 0 || height == 0){
                    out.println("worng input");
                }
                ISolver badBacktracking = new Backtracking(width, height, pieces);
                String toFile = ""; 
                toFile += width + ","+height+","+Arrays.toString(pieces);
                long time = System.currentTimeMillis();
                if(badBacktracking.search("bad")){
                    
                    long consumedTime = System.currentTimeMillis()-time;
                    System.out.println("Solved");

                    // UI ui = new UI(width, height, 50);
                    // ui.setState(badBacktracking.getField());
                    toFile+= consumedTime+"\n";
                    out.append(toFile);
                    
                }
                else{
                    long consumedTime = System.currentTimeMillis()-time;
                    
                    toFile+= consumedTime+" False\n";
                    out.println();
                    out.append(toFile);
                }


                ISolver goodBacktracking = new Backtracking(width, height, pieces);
                String toFile1 = ""; 
                toFile1 += width + ","+height+","+Arrays.toString(pieces);
                long time1 = System.currentTimeMillis();
                if(goodBacktracking.search("good")){
                    
                    long consumedTime = System.currentTimeMillis()-time1;
                    System.out.println("Solved");

                    toFile1+= consumedTime+"\n";
                    out2.append(toFile);
                    
                }
                else{
                    long consumedTime = System.currentTimeMillis()-time1;
                    
                    toFile1+= consumedTime+" False\n";
                    out2.println();
                    out2.append(toFile);
                }
              
                System.out.println(toFile1);
               System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
               
            }

            out.close();
            out2.close();
            scanner.close();
       
    }
}
