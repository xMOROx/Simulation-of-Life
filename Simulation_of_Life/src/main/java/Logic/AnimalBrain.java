package Logic;


import java.util.Random;

public class AnimalBrain {
    private Genome genome;
    private static Random random = new Random();

    public AnimalBrain(Genome genome) {
        this.genome = genome;
    }

    public int randomGeneActivation() {
        return random.nextInt(genome.getSize());
    }

}
