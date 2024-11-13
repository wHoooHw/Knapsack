package Bots.GABot;

import java.util.ArrayList;


public class Generation {
    float mutationRate;
  Individual[] population;
  ArrayList<Individual> matingPool;
  int generations;
  boolean finished;
  int perfectScore;

  Generation(float m, int num) {
    mutationRate = m;
    population = new Individual[num];
    for (int i = 0; i < population.length; i++) {
      population[i] = new Individual();
    }
    calcFitness();
    matingPool = new ArrayList<Individual>();
    finished = false;
    generations = 0;

    perfectScore = 1;
  }

  void calcFitness() {
    for (int i = 0; i < population.length; i++) {
      
          population[i].fitness();
        
      
    }
  }

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

  public static float map(float value, float rangeStart, float rangeEnd, float toRangeStart, float toRangeEnd) {

    float valueConv = (toRangeStart) + ((value - rangeStart) * (toRangeStart - toRangeEnd)) / (rangeStart - rangeEnd);

    return valueConv;
  }

  void generate() {
    // Refill the population with children from the mating pool
    for (int i = 0; i < population.length; i++) {
      // Random random = new Random();
      int a = (int) (Math.random() * matingPool.size());
      int b = (int) (Math.random() * matingPool.size());
      Individual partnerA = matingPool.get(a);
      Individual partnerB = matingPool.get(b);
      Individual child = partnerA.crossover(partnerB);
      child.mutate(mutationRate);
      population[i] = child;
    }
    generations++;
  }

  String getBest() {
    float worldrecord = 0.0f;
    int index = 0;
    for (int i = 0; i < population.length; i++) {
      if (population[i].fitness > worldrecord) {
        index = i;
        worldrecord = population[i].fitness;
      }
    }

    if (worldrecord >= perfectScore)
      finished = true;
    return population[index].getPhrase();
  }

   double[] getBestDouble() {
    float worldrecord = 0.0f;
    int index = 0;
    for (int i = 0; i < population.length; i++) {
      if (population[i].fitness > worldrecord) {
        index = i;
        worldrecord = population[i].fitness;
      }
    }

    if (worldrecord >= perfectScore)
      finished = true;
   ;
    
    return population[index].genes;
  }

  boolean finished() {
    return finished;
  }

  int getGenerations() {
    return generations;
  }

  float getAverageFitness() {
    float total = 0;
    for (int i = 0; i < population.length; i++) {
      total += population[i].fitness;
    }
    return total / (population.length);
  }

  String allPhrases() {
    String everything = "";

    int displayLimit = Math.min(population.length, 50);

    for (int i = 0; i < displayLimit; i++) {
      everything += population[i].getPhrase() + "\n";
    }
    return everything;
  }
}
