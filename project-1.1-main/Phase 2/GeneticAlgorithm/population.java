package GeneticAlgorithm;

import java.util.ArrayList;


public class Population {
  float mutationRate;
  DNA[] population;
  ArrayList<DNA> matingPool;
  int generations;
  boolean finished;
  int perfectScore;
  String bot;
  double[] vector;
  Population(float m, int num,String bot) {
    mutationRate = m;
    population = new DNA[num];
    this.bot = bot;
    for (int i = 0; i < population.length; i++) {
      population[i] = new DNA(bot);
    }
    calcFitness();
    matingPool = new ArrayList<DNA>();
    finished = false;
    generations = 0;

    perfectScore = 1;
  }

  Population(float m, int num,String bot,double[] vector) {
    mutationRate = m;
    population = new DNA[num];
    this.bot = bot;
    this.vector = vector;
    for (int i = 0; i < population.length; i++) {
      population[i] = new DNA(bot,vector);
    }

    calcFitness();
    matingPool = new ArrayList<DNA>();
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
      int a = (int) (Math.random() * matingPool.size());
      int b = (int) (Math.random() * matingPool.size());
      DNA partnerA = matingPool.get(a);
      DNA partnerB = matingPool.get(b);
      DNA child = partnerA.orderCrossover(partnerB);
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
    System.out.println("fitness "+ population[index].fitness);
    return population[index].getPhrase();
  }

  Character[] getBestArray() {
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
    System.out.println("fitness "+ population[index].fitness);
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
