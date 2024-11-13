package GeneticAlgorithm;



public class GA {

    int popMax;
    float mutationRate;
    Population population;
    String bot;
    double[] vector;
    public GA(String bot){
        this.bot = bot;
    }

    public GA(String bot,double[] vector){
        this.bot = bot;
        this.vector = vector;
    }

    public Character[] start(){

        popMax = 1000;
        mutationRate = 0.02f;
        if(bot.equalsIgnoreCase("AdvancedBot")||bot.equalsIgnoreCase("a"))
            population = new Population( mutationRate, popMax, bot,vector);
        else
            population = new Population( mutationRate, popMax, bot);
        long time = System.currentTimeMillis();
        while (!population.finished()) {
            population.naturalSelection();
            population.generate();
            population.calcFitness();
            System.out.println(population.getBest());
            printProgress(population.generations);
        }

        System.out.println(population.getGenerations());
        System.out.println(population.getBest());
        long time1 = System.currentTimeMillis() - time;

        System.out.println(time1 + "," + popMax + "," + mutationRate + "," + population.getGenerations());

        return population.getBestArray();
    }

    
    private void printProgress(int progress) {
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
        System.out.print("\u001B[34m");
        System.out.println("Finding best sequence in progress "+progress+"%");
        for(int i = 0; i < progress; i++){
            // Something that allows user input/interaction capable to stop the progressbar
            System.out.print("█");
            
        }
        System.out.println("\u001B[0m");
    }
}
