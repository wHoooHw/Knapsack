package Phase1.src.Phase1;


import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

import Phase1.src.Phase1.Solvers.Backtracking;
import Phase1.src.Phase1.Solvers.ISolver;


public class TestMain {
    

    public static void main(String[] args) throws Exception {
        String fileName = "C:\\Users\\abdulla\\Downloads\\Project1.1\\Project1.1\\project-1.1\\src\\PentominoExamples.csv";

        File file = new File(fileName);
    
       
            
            PrintWriter out = new PrintWriter("Tested Cases.csv","UTF-8");
            
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
                ISolver backtracking = new Backtracking(width, height, pieces);
                String toFile = ""; 
                toFile += width + ","+height+","+Arrays.toString(pieces);
                long time = System.currentTimeMillis();
                if(backtracking.search(toFile)){
                    
                    long consumedTime = System.currentTimeMillis()-time;
                    System.out.println("Solved");

                    // UI ui = new UI(width, height, 50);
                    // ui.setState(backtracking.getField());
                    toFile+= consumedTime+"\n";
                    out.append(toFile);
                    
                }
                else{
                    long consumedTime = System.currentTimeMillis()-time;
                    
                    toFile+= consumedTime+" False\n";
                    out.println();
                    out.append(toFile);
                }
              
               System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
            }
            scanner.close();

            out.close();
       
    }
}
