package Phase3.MyBackend.GeneticAlgorithmForDLX;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import Phase3.Data3D.MyData;
import Phase3.MyBackend.MySolvers.DLXSolver;

/**
 * Represents an individual in the genetic algorithm population.
 * Each individual has a list of genes that represents a solution to a problem.
 * The genes are used to generate and evolve solutions.
 */
public class Individual {
  // List of genes representing the individual's solution
  List<Integer> genes;
  
  // Fitness score of the individual
  float fitness;
  String type; // Type of individual (e.g., "P" for parcel)

  static int counter = 0; // Counter to control mutation and shuffling behavior

  /**
  * Constructor for the Individual class.
  *
  * @param type The type of individual (e.g., "P" for parcel).
  */
  Individual(String type) {
    this.type = type;

    genes = new ArrayList<>(MyData.indecesPent);
    
    // Apply different initialization strategies based on the counter value
    if(counter > 50 && counter<70) {
      // Shuffle genes randomly
      Collections.shuffle(genes);
    }
    else if(counter>70){
      // Mutate genes with a 50% probability
      mutate(0.5f);
      counter++;
    }
    
    else counter++;
    
  }

  /**
  * Calculates the fitness of the individual based on its genes.
  * Uses a DLX solver to find the fitness value.
  */
  void calcFitness() {
  
      DLXSolver solver = new DLXSolver(type, genes);
      solver.solve();
      fitness = solver.getBestValue();

    // Print the fitness value for debugging or monitoring
    System.out.println(fitness);

    // Map the fitness value based on the type
    if(type.equals("P"))
        fitness = map(fitness, 0, 244, 0, 1);
    else
    fitness = map(fitness, 0, 1320, 0, 1); 
  }

  /**
  * Performs ordered crossover between the current individual and another parent.
  *
  * @param parent2 The second parent for crossover.
  * @return A new individual representing the offspring after crossover.
  */
  public Individual orderCrossover(Individual parent2) {
    Random random = new Random();
    int length = genes.size();
    List<Integer> offspringGenes = new ArrayList<>(genes);

    // Step 2: Randomly choose two crossover points
    int point1 = random.nextInt(length);
    int point2 = random.nextInt(length);
    int start = Math.min(point1, point2);
    int end = Math.max(point1, point2);

    // Step 3: Copy genetic material between crossover points
    for (int i = start; i <= end; i++) {
        offspringGenes.set(i, genes.get(i));
    }

    // Step 4: Fill in remaining positions from the other parent
    int index = 0;
    for (int i = 0; i < length; i++) {
        if (index == start) {
            index = end + 1;
        }

        if (!offspringGenes.contains(parent2.genes.get(i))) {
            offspringGenes.set(index , parent2.genes.get(i));
            index++;
        }

    }
    Individual child =new Individual(type);
    child.setGenes(offspringGenes);
    return child;
}

  /**
  * Mutates the genes of the individual with a given mutation rate.
  *
  * @param mutationRate The probability of mutation for each gene.
  */
  void mutate(float mutationRate) {
    // Iterate through each gene in the individual's genome
    for (int i = 0; i < genes.size(); i++) {
      // Generate a random number between 0 and 1 to determine if mutation should occur
      Random rand = new Random();
      double random = rand.nextDouble();

      // If the random number is less than the mutation rate, perform mutation
      if (random < mutationRate) {
        
        // Get a random index different from the current index
        int index = getIndex(i);
        // Swap the current gene with the gene at the randomly chosen index
        Collections.swap(genes,i,index);
      }
    }
  }

  /**
  * Gets a random index different from the specified index within the genes.
  *
  * @param currentIndex The index to be avoided when generating a random index.
  * @return A random index that is different from the specified index.
  */
  int getIndex(int i){
    // Create a Random object to generate random indices
    Random random = new Random();
    // Initialize index with a random value
    int index = random.nextInt(genes.size());
    // Ensure the generated index is different from the specified currentIndex
    while (index==i) {
      index = random.nextInt(genes.size());
    }
    return index; // Return the generated index
  }
 
  /**
  * Sets the genes of the individual using the provided list of integers.
  *
  * @param genes The list of integers representing the new set of genes.
  */
  public void setGenes(List<Integer> genes) {

    this.genes.clear(); // Clear the existing genes
    for (Integer bs : genes) { // Copy the values from the provided list to the genes
        this.genes.add(bs);
    }
  }

  /**
  * Maps a value from one range to another using linear interpolation.
  * 
  * @param value        The input value to be mapped.
  * @param rangeStart   The start of the input range.
  * @param rangeEnd     The end of the input range.
  * @param toRangeStart The start of the output range.
  * @param toRangeEnd   The end of the output range.
  * @return The mapped value in the output range.
  */
  public static float map(float value, float rangeStart, float rangeEnd, float toRangeStart, float toRangeEnd) {

    // Perform linear interpolation to map the value to the new range
    float valueConv = (toRangeStart) + ((value - rangeStart) * (toRangeStart - toRangeEnd)) / (rangeStart - rangeEnd);

    return valueConv; // Return the mapped value
  }  
}