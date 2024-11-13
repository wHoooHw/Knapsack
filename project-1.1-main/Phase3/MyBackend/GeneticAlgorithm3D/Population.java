package Phase3.MyBackend.GeneticAlgorithm3D;
import java.util.*;


/**
 * The Population class represents a population of DNA sequences for the Genetic Algorithm.
 */
public class Population {

  float mutationRate; // Mutation rate for genetic operation
  DNA[] population; // Array of DNA sequences
  ArrayList<DNA> matingPool; // List of DNA sequences for mating
  int generations; // Number of generations
  boolean finished; // Flag indicating if the algorithm is finished
  int perfectScore; // Perfect score for termination
  String tybe; // Type of DNA, "P" for parcel genes or other types

  /**
   * Constructor for the Population class.
   *
   * @param m    Mutation rate for genetic operations.
   * @param num  Number of DNA sequences in the population.
   * @param type Type of DNA, "P" for parcel genes or other types.
   */
  Population(float m, int num,String tybe) {
    mutationRate = m;
    population = new DNA[num];
    this.tybe = tybe;
    // Initialize the population with random DNA sequences
    for (int i = 0; i < population.length; i++) {
      population[i] = new DNA(tybe);
    }
    calcFitness();
    matingPool = new ArrayList<DNA>();
    finished = false;
    generations = 0;

    perfectScore = 1;
  }

  /**
   * Calculate the fitness for each DNA sequence in the population.
   */
  void calcFitness() {
    
    
    for (int i = 0; i < population.length; i++) {
      population[i].calcFitness();
    }
  }

  /**
   * Perform natural selection to fill the mating pool based on fitness.
   */
  void naturalSelection() {
    // Clear the ArrayList
    matingPool.clear();

    float maxFitness = 0;
    for (int i = 0; i < population.length; i++) {
      if (population[i].fitness > maxFitness) {
        maxFitness = population[i].fitness;
      }
    }

    for (int i = 0; i < population.length; i++) {

      float fitness = map(population[i].fitness, 0, maxFitness, 0, 1);
      int n = (int) fitness*100; // Arbitrary multiplier, we can also use monte carlo method
      if (population[i].fitness == maxFitness) {
        for (int j = 0; j < n; j++) { // and pick two random numbers
          matingPool.add(population[i]);
        }
      }
    }
  }

  /**
  * Maps a value from one range to another.
  *
  * @param value        The value to be mapped.
  * @param rangeStart   The start of the original range.
  * @param rangeEnd     The end of the original range.
  * @param toRangeStart The start of the target range.
  * @param toRangeEnd   The end of the target range.
  * @return The mapped value in the target range.
  */
  public static float map(float value, float rangeStart, float rangeEnd, float toRangeStart, float toRangeEnd) {

    // Calculate the mapped value using linear interpolation
    float valueConv = (toRangeStart) + ((value - rangeStart) * (toRangeStart - toRangeEnd)) / (rangeStart - rangeEnd);

    return valueConv; // Return the mapped value
  }

  /**
 * Generates a new population by refilling it with children from the mating pool.
 * Each child is created through crossover and mutation of two randomly selected parents.
 */
  void generate() {
    // Refill the population with children from the mating pool
    for (int i = 0; i < population.length; i++) {
      // Select two random parents from the mating pool
      int a = (int) (Math.random() * matingPool.size());
      int b = (int) (Math.random() * matingPool.size());
      DNA partnerA = matingPool.get(a);
      DNA partnerB = matingPool.get(b);
      // Create a child through crossover and mutation
      DNA child = partnerA.crossover(partnerB);
      child.mutate(mutationRate);
      // Replace the current individual in the population with the new child
      population[i] = child;
    }
    generations++; // Increment the generations counter
  }

  /**
  * Returns the DNA with the highest fitness in the current population.
  * Updates the 'finished' flag if the fitness reaches the perfect score.
  *
  * @return The DNA with the highest fitness.
  */
  DNA getBest() {
    float worldrecord = 0.0f;
    int index = 0;

    // Iterate through the population to find the DNA with the highest fitness
    for (int i = 0; i < population.length; i++) {
      if (population[i].fitness > worldrecord) {
        index = i;
        worldrecord = population[i].fitness;
      }
    }
    // If the world record surpasses or equals the perfect score, mark the algorithm as finished
    if (worldrecord >= perfectScore)
      finished = true;
    
      // Return the DNA with the highest fitness
    return population[index];
  }

  /**
  * Returns the gene array of the DNA with the highest fitness in the current population.
  * Updates the 'finished' flag if the fitness reaches the perfect score.
  *
  * @return The gene array of the DNA with the highest fitness.
  */
  int[] getBestArray() {
    float worldrecord = 0.0f;
    int index = 0;
    // Iterate through the population to find the DNA with the highest fitness
    for (int i = 0; i < population.length; i++) {
      if (population[i].fitness > worldrecord) {
        index = i;
        worldrecord = population[i].fitness;
      }
    }
    // If the world record surpasses or equals the perfect score, mark the algorithm as finished
    if (worldrecord >= perfectScore)
      finished = true;
    
    // Return the gene array of the DNA with the highest fitness
    return population[index].genes;
  }

  /**
  * Checks if the genetic algorithm has finished its execution.
  *
  * @return True if the algorithm is finished; otherwise, false.
  */
  boolean finished() {
    return finished;
  }

  /**
  * Returns the number of generations the genetic algorithm has gone through.
  *
  * @return The number of generations.
  */
  int getGenerations() {
    return generations;
  }

  /**
  * Calculates and returns the average fitness of the entire population.
  *
  * @return The average fitness value.
  */
  float getAverageFitness() {
    float total = 0;
    // Calculate the sum of fitness values in the population
    for (int i = 0; i < population.length; i++) {
      total += population[i].fitness;
    }
    // Return the average fitness by dividing the total by the population size
    return total / (population.length);
  }


  /**
  * Helper class for calculating the fitness of a DNA instance in a separate thread.
  * This is designed to be used in a multi-threaded context to improve performance
  * by parallelizing fitness calculations for multiple DNA instances.
  */
  public class FitnessCalculator implements Runnable{
    // The DNA instance for which fitness is calculated
    DNA dnas;

    /**
     * Constructs a FitnessCalculator with the given DNA instance.
     *
     * @param dnas The DNA instance for fitness calculation.
     */
    FitnessCalculator(DNA dnas){
      this.dnas = dnas;
    }
    
    /**
     * Runs the fitness calculation for the associated DNA instance.
     * This method is executed when the thread starts.
     */
    @Override
    public void run() {
      dnas.calcFitness();
    }
  }
}