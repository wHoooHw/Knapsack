package Phase3.MyBackend.GeneticAlgorithmForDLX;
import java.util.*;


/**
 * Represents a population of individuals in a genetic algorithm.
 */
public class Population {

  float mutationRate; // Mutation rate for the population
  Individual[] population; // Array to store individuals in the population
  ArrayList<Individual> matingPool; // ArrayList to store individuals eligible for mating
  int generations; // Number of generations
  boolean finished; // Flag indicating whether the evolution process is finished
  int perfectScore; // Perfect fitness score
  String tybe; // Type of the individual in the population

  /**
   * Constructs a new population with the specified mutation rate, number of individuals, and type.
   *
   * @param m     The mutation rate for the population.
   * @param num   The number of individuals in the population.
   * @param type  The type of the individuals in the population.
   */
  Population(float m, int num,String tybe) {
    mutationRate = m;
    population = new Individual[num];
    this.tybe = tybe;
    // Initialize individuals in the population
    for (int i = 0; i < population.length; i++) {
      population[i] = new Individual(tybe);
    }
    // Calculate the initial fitness for all individuals
    calcFitness();
    // Initialize the mating pool
    matingPool = new ArrayList<Individual>();
    // Set initial values for other properties
    finished = false;
    generations = 0;

    perfectScore = 1;
  }
  /**
   * Calculates the fitness for each individual in the population.
   */
  void calcFitness() {
    for (int i = 0; i < population.length; i++) {
      population[i].calcFitness();
    }
  }

  // Maximum fitness value in the population
  float maxFitness;

  /**
   * Performs natural selection to fill the mating pool based on individual fitness.
   */
  void naturalSelection() {
    // Clear the ArrayList
    matingPool.clear(); // Clear the ArrayList

    maxFitness = 0; // Find the maximum fitness value in the population
    for (int i = 0; i < population.length; i++) { 
      if (population[i].fitness > maxFitness) {
        maxFitness = population[i].fitness;
      }
    }

    // Fill the mating pool based on fitness
    for (int i = 0; i < population.length; i++) {

      float fitness =Individual.map(population[i].fitness, 0, maxFitness, 0, 1);
      int n = (int) fitness*100; // Arbitrary multiplier, we can also use monte carlo method

      // If the individual has the maximum fitness, add it to the mating pool multiple times
      if (population[i].fitness == maxFitness) {
        for (int j = 0; j < n; j++) { // and pick two random numbers
          matingPool.add(population[i]);
        }
      }
    }
  }


  int count = 0; // Counter to skip a few generations after finding a top fitness individual

   /**
   * Generates a new generation of individuals using the individuals in the mating pool.
   */
  void generate() {
    // Refill the population with children from the mating pool
    for (int i = 0; i < population.length; i++) {
      int a = (int) (Math.random() * matingPool.size());
      int b = (int) (Math.random() * matingPool.size());

      // Skip individuals with the maximum fitness for a few generations
      if(population[i].fitness == maxFitness && count <5){count++; continue;}

      // Select parents from the mating pool
      Individual partnerA = matingPool.get(a);
      Individual partnerB = matingPool.get(b);
      // Create a child using order crossover and mutate it
      Individual child = partnerA.orderCrossover(partnerB);
      child.mutate(mutationRate);
      // Replace the current individual with the child
      population[i] = child;
    }
    count = 0; // Reset the counter after generating a few generations
    generations++; // Increment the generation count
  }

  /**
   * Finds and returns the individual with the highest fitness in the population.
   *
   * @return The individual with the highest fitness.
   */
  Individual getBest() {
    float worldrecord = 0.0f;
    int index = 0;

    // Find the individual with the highest fitness
    for (int i = 0; i < population.length; i++) {
      if (population[i].fitness > worldrecord) {
        index = i;
        worldrecord = population[i].fitness;
      }
    }

    // If the highest fitness is equal to or greater than the perfect score, set finished to true
    if (worldrecord >= perfectScore)
      finished = true;
    // Return the individual with the highest fitness
    return population[index];
  }

   /**
   * Finds and returns the genes of the individual with the highest fitness in the population.
   *
   * @return The genes of the individual with the highest fitness.
   */
  List<Integer> getBestArray() {
    float worldrecord = 0.0f;
    int index = 0;
    // Find the individual with the highest fitness
    for (int i = 0; i < population.length; i++) {
      if (population[i].fitness > worldrecord) {
        index = i;
        worldrecord = population[i].fitness;
      }
    }

    // If the highest fitness is equal to or greater than the perfect score, set finished to true
    if (worldrecord >= perfectScore)
      finished = true;
    return population[index].genes; // Return the genes of the individual with the highest fitness
  }

   /**
   * Checks if the evolution process is finished.
   *
   * @return True if the process is finished, false otherwise.
   */
  boolean finished() {
    return finished;
  }

  /**
   * Gets the number of generations that have passed.
   *
   * @return The number of generations.
   */
  int getGenerations() {
    return generations;
  }

  /**
   * Calculates and returns the average fitness of the population.
   *
   * @return The average fitness.
   */
  float getAverageFitness() {
    float total = 0;
    // Calculate the total fitness of all individuals in the population
    for (int i = 0; i < population.length; i++) {
      total += population[i].fitness;
    }
    // Return the average fitness
    return total / (population.length);
  }

}