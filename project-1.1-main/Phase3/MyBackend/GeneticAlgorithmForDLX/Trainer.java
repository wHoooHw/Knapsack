package Phase3.MyBackend.GeneticAlgorithmForDLX;

import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 * The Trainer class manages the training process of a genetic algorithm for a specific type.
 * It evolves a population, saves the best result, and provides progress information.
 */
public class Trainer {
    int popMax; // Maximum population size
    float mutationRate; // Mutation rate for the genetic algorithm
    Population population; // The population for the genetic algorithm
    String type; // Type of individuals in the population

    /**
     * Constructs a Trainer for a specific type.
     *
     * @param type The type of individuals in the population.
     */
    public Trainer(String type){
        this.type = type;
    }

    /**
     * Initiates the genetic algorithm training process and returns the best result.
     *
     * @return The genes of the best individual.
     */
    public List<Integer> start(){

        popMax = 100; // Set the maximum population size
        mutationRate = 0.2f; // Set the mutation rate
        population = new Population(mutationRate, popMax, type); // Create a population for the genetic algorithm
        long time = System.currentTimeMillis(); // Record the starting time
        while (!population.finished()) { // Continue evolution until the termination condition is met
            population.naturalSelection();
            population.generate();
            population.calcFitness();
        
            if(System.currentTimeMillis() - time >50 *60 * 1000) break; // Terminate the loop if a certain time limit is exceeded
            System.out.println(population.getBest().fitness); // Print progress information
        }

        // Output the number of generations and the best result
        System.out.println(population.getGenerations());
        System.out.println(population.getBest());
        try{
            // Save the best result to a text file
            BufferedWriter writer = new BufferedWriter(new FileWriter("BestAnswerTextPent.txt"));
            // Save the best result as a serialized object
            FileOutputStream f = new FileOutputStream(new File("ListIntegerObjectPent"+".txt"));
			ObjectOutputStream o = new ObjectOutputStream(f);
                writer.write(population.getBestArray().toString());
                o.writeObject(population.getBestArray());
            writer.close();
            System.out.println("Data has been written to the file successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
        // Record the total time taken
        long time1 = System.currentTimeMillis() - time;

        // Output information about the training process
        System.out.println(time1 + "," + popMax + "," + mutationRate + "," + population.getGenerations());

        return population.getBestArray(); // Return the best result
    }
    
    
    /**
     * The main method creates multiple threads to run the Trainer for concurrent training.
     *
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        // Create a runnable for training
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Trainer g = new Trainer("Pe");
        
                g.start();
            }
        };

        // Create and start multiple threads for concurrent training
        for (int i = 0; i < 3; i++) {
            Thread t1 = new Thread(runnable);
            t1.start();
        }
    }
}