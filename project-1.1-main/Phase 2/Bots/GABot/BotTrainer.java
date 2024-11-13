package Bots.GABot;


public class BotTrainer {
    
    String target;
    int popMax;
    float mutationRate;
    Generation population;
    public BotTrainer(){
        popMax = 100;
        mutationRate = 0.02f;
        population = new Generation( mutationRate, popMax);

    }

    public double[] start(){
       

        
        while (!population.finished()&&population.generations<500) {
            population.naturalSelection();
            population.generate();
            population.calcFitness();
            System.out.println(population.getBest());
        }

        System.out.println(population.getGenerations());
        System.out.println(population.getBest());
     
        return population.getBestDouble();
    }


   

}
