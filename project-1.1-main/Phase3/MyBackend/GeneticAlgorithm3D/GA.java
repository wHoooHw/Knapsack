package Phase3.MyBackend.GeneticAlgorithm3D;

import java.util.*;

/**
 * The GA class represents the Genetic Algorithm for solving the 3D knapsack problem.
 */
public class GA {

    int popMax; // Maximum population size
    float mutationRate; // Mutation rate for genetic operations
    Population population; // Population of DNA sequences
    String type; // Type of DNA, "P" for parcel genes or other types
    int width, height, depth; // Dimensions of the 3D knapsack

    /**
     * Constructor for the GA class.
     * @param type   The type of DNA, "P" for parcel genes or other types.
     */
    public GA(String type){
        this.type = type;
    }

    /**
     * Start the genetic algorithm to find the best sequence for the 3D knapsack problem.
     *
     * @return An array representing the best sequence found by the genetic algorithm.
     */
    public int[] start(){

        popMax = 100;
        mutationRate = 0.02f;
        population = new Population(mutationRate, popMax, type);
        long time = System.currentTimeMillis();
        while (!population.finished()) {
            population.naturalSelection();
            population.generate();
            population.calcFitness();
            System.out.println(Arrays.toString(population.getBestArray()));
            // printProgress(population.generations,time);
            if(System.currentTimeMillis() - time > 5*60 * 1000) break;
            System.out.println(population.getBest());
        }

        System.out.println(population.getGenerations());
        System.out.println(population.getBest());

        long time1 = System.currentTimeMillis() - time;

        System.out.println(time1 + "," + popMax + "," + mutationRate + "," + population.getGenerations());

        return population.getBestArray();
    }


    /**
     * The main method to run the Genetic Algorithm for the 3D knapsack problem.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        GA g = new GA("P");
        g.start();
    }
}