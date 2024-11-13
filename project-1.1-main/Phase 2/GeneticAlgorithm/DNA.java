package GeneticAlgorithm;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Backend.Game;
import Utilities.Data;
public class DNA {

  Character[] genes;
  float fitness;
  String bot;
  double[] vector;
  DNA(String bot) {
    this.bot = bot;
    genes = new Character[12];
    for (int i = 0; i < genes.length; i++) {
      genes[i] = Data.PENTLETTERS[i];
    }
    List<Character> list = Arrays.asList(genes);
    Collections.shuffle(list);
    list.toArray(genes);
    }

  DNA(String bot, double[] vector) {
    this.bot = bot;
    genes = new Character[12];
    for (int i = 0; i < genes.length; i++) {
      genes[i] = Data.PENTLETTERS[i];
    }
    List<Character> list = Arrays.asList(genes);
    Collections.shuffle(list);
    list.toArray(genes);
    this.vector = vector;
    }

  String getPhrase() {
    return Arrays.toString(genes);
  }

  void fitness() {
    Game game;
    if(bot.equalsIgnoreCase("a")||bot.equalsIgnoreCase("AdvancedBot"))
      game = new Game(bot,15, 5,genes,null,vector);
    
    else
      game = new Game(bot,15, 5,genes,null,null);
    for (int i = 0; i < genes.length*2; i++) {
      game.runFitnessBot();
    }

   
    fitness =game.getScore();
    fitness = map(fitness, 0, 13, 0, 1);
   
  }

  public DNA orderCrossover(DNA parent2) {
    Random random = new Random();
    int length = genes.length;
    Character[] offspringGenes = new Character[length];

    // Step 2: Randomly choose two crossover points
    int point1 = random.nextInt(length);
    int point2 = random.nextInt(length);
    int start = Math.min(point1, point2);
    int end = Math.max(point1, point2);

    // Step 3: Copy genetic material between crossover points
    for (int i = start; i <= end; i++) {
        offspringGenes[i] = genes[i];
    }

    // Step 4: Fill in remaining positions from the other parent
    int index = 0;
    for (int i = 0; i < length; i++) {
        if (index == start) {
            index = end + 1;
        }

        if (!contains(offspringGenes, parent2.genes[i])) {
            offspringGenes[index] = parent2.genes[i];
            index++;
        }

    }
    DNA child;
    if(vector!=null)
      child = new DNA(bot,vector);
    else
      child = new DNA(bot);
    child.setGenes(offspringGenes);
    return child;
}

private boolean contains(Character[] array, char value) {
    for (int j = 0; j < array.length; j++) {
      
        if (array[j]!=null && array[j] == value) {
            return true;
        }
    }
    return false;
}


  void mutate(float mutationRate) {
    for (int i = 0; i < genes.length; i++) {
      Random rand = new Random();
      double random = rand.nextDouble();
      if (random < mutationRate) {
        
        int index = getIndex(i);
        char temp = genes[index]; 
        genes[index] = genes[i];
        genes[i] = temp;
      }
    }
  }

  int getIndex(int i){
    Random random = new Random();
    int index = random.nextInt(genes.length);
    while (index==i) {
      index = random.nextInt(genes.length);
    }
    return index;
  }
 
  public void setGenes(Character[] genes) {
    for (int i = 0; i < genes.length; i++) {
      this.genes[i] = genes[i];
    }
      
  }

  public static float map(float value, float rangeStart, float rangeEnd, float toRangeStart, float toRangeEnd) {

    float valueConv = (toRangeStart) + ((value - rangeStart) * (toRangeStart - toRangeEnd)) / (rangeStart - rangeEnd);

    return valueConv;
  }
}
