package Bots.GABot;

import java.util.Random;

import Backend.Game;

public class Individual{

  double[] genes;
  float fitness;
  Random random;
  Individual() {
    random = new Random();
    genes = new double[4];
    for (int i = 0; i < genes.length; i++) {
        if(i!=1) 
            genes[i] = random.nextDouble()*-1;
        else
            genes[i] = random.nextDouble();

          
    }
    
  }

  String getPhrase() {
    String phrase = "{";
    for (int i = 0; i < genes.length; i++) {
      phrase += genes[i]+", ";
    }
    phrase += "}";

    return phrase;
  }

  void fitness() {
    Game game = new Game("a",15, 5);
    game.setVector(genes);
    
    while (!game.runFitnessBot() && game.getScore()<=1000) {
        
    }
      
    
    fitness =game.getScore();
    fitness = map(fitness, 0, 1000, 0, 1);
  
    
  }

  public Individual crossover(Individual parent) {
    
    Random random = new Random();
    Individual child = new Individual();

    int Xpoint = (int) (random.nextInt(genes.length));

    for (int i = 0; i < genes.length; i++) {
      if (i > Xpoint) {
        child.genes[i] = genes[i];
      } else {
        child.genes[i] = parent.genes[i];
      }
    }
    return child;
    
}



  void mutate(float mutationRate) {
    Random rand = new Random(); // Create a single Random instance outside the loop

        for (int i = 0; i < genes.length; i++) {
            double random = rand.nextDouble();
            if (random < mutationRate) {
                if (i != 1) {
                    // Assuming genes is a float array, use a float value
                    genes[i] =  (random  * -1.0);
                } else {
                    genes[i] = (random);
                }
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
 
  public void setGenes(double[] genes) {
    for (int i = 0; i < genes.length; i++) {
      this.genes[i] = genes[i];
    }
      
  }

  public static float map(float value, float rangeStart, float rangeEnd, float toRangeStart, float toRangeEnd) {

    float valueConv = (toRangeStart) + ((value - rangeStart) * (toRangeStart - toRangeEnd)) / (rangeStart - rangeEnd);

    return valueConv;
  }
}
