package Phase3.MyBackend.GeneticAlgorithm3D;

import java.util.*;
import Phase3.MyBackend.MySolvers.Greedy;
import Phase3.MyBackend.MySolvers.ISolver;


/**
 * The DNA class represents a genetic sequence for the 3D knapsack problem.
 * It includes genes that determine the amounts of different parcels to be used.
 * The DNA can be of type "P" (for parcel genes) or other types.
 */
public class DNA {
  int[] genes;
  Random rand;
  float fitness;
  String type;
  static int count = 0;

  /**
     * Constructor for the DNA class.
     *
     * @param type The type of DNA, either "P" for parcel genes or other for pentominoes.
     */
  DNA(String type) {
    rand  = new Random();
    this.type = type;
    genes = new int[6];
    // Initialize genes based on the DNA type
    if(type.equals("P")){
      for (int i = 0; i < 3; i++) {
        genes[i] = rand.nextInt(100);
      }
    }
    else{
      for (int i = 3; i < genes.length; i++) {
        genes[i] = rand.nextInt(300);
      }
    }
  }

  /**
     * Calculates the fitness of the DNA by solving the 3D knapsack problem.
     */
  void calcFitness() {
    ISolver.c.empty();
    Greedy greedyBot = new Greedy(type);
    greedyBot.setParcelAmounts(genes);
    greedyBot.recursiveBacktracking();
    fitness = greedyBot.getContainer().totalValue();

    // Map fitness value based on the DNA type
    if(type.equals("P"))
      fitness = map(fitness, 0, 240, 0, 1); 
    
    else
      fitness = map(fitness, 0, 1320, 0, 1);
    
  }

  /**
     * Performs crossover with another DNA to produce a child DNA.
     *
     * @param partner The DNA to perform crossover with.
     * @return The child DNA resulting from the crossover.
     */
  DNA crossover(DNA partner) {

    DNA child = new DNA(type);

    int Xpoint;
    // Perform crossover based on the DNA type
    if(type.equals("P")){
      Xpoint = rand.nextInt(3);
      for (int i = 0; i < 3; i++) {
        if (i > Xpoint) {
          child.genes[i] = genes[i];
        } else {
          child.genes[i] = partner.genes[i];
        }
      }
    }

    else{
      Xpoint = rand.nextInt(7);
      while (Xpoint<3) {
        Xpoint = rand.nextInt(7);
      }
      for (int i = 3; i < genes.length; i++) {
        if (i > Xpoint) {
          child.genes[i] = genes[i];
        } else {
          child.genes[i] = partner.genes[i];
        }
      }
    }
    return child;
  }

  /**
  * Mutates the DNA based on a given mutation rate.
  *
  * @param mutationRate The rate at which mutations occur.
  */
  void mutate(float mutationRate) {

    // Mutate genes based on the DNA type
    if(type.equals("P")){
      for (int i = 0; i < 3; i++) {
        double random = Math.random();
        if (random < mutationRate)
          genes[i] = rand.nextInt(100);
      }
    }
    else{
      for (int i = 3; i < genes.length; i++) {
        double random = Math.random();
        if (random < mutationRate)
          genes[i] = rand.nextInt(350);
      }
    }
    
  }
  /**
  * Converts the DNA to a string for display purposes.
  *
  * @return A string representation of the DNA.
  */
  @Override
  public String toString() {
      return Arrays.toString(genes);
  }

  /**
  * Maps a value from one range to another.
  *
  * @param value         The value to be mapped.
  * @param rangeStart    The start of the original range.
  * @param rangeEnd      The end of the original range.
  * @param toRangeStart  The start of the target range.
  * @param toRangeEnd    The end of the target range.
  * @return The mapped value.
  */
  public static float map(float value, float rangeStart, float rangeEnd, float toRangeStart, float toRangeEnd) {

    float valueConv = (toRangeStart) + ((value - rangeStart) * (toRangeStart - toRangeEnd)) / (rangeStart - rangeEnd);

    return valueConv;
  }  
}
