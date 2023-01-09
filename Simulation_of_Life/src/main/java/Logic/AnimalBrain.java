package Logic;


import java.util.Random;

public class AnimalBrain {
    private final Genome genome;
    private static final Random random = new Random();

    public AnimalBrain(Genome genome) {
        this.genome = genome;
    }

    public int randomGeneActivation() {
        return random.nextInt(genome.getSize());
    }

}
