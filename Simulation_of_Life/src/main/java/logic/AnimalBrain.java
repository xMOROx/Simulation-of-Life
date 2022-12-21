package logic;


import java.util.Random;

public class AnimalBrain {
    private Genome genome;
    private static Random random = new Random();

    public AnimalBrain(Genome genome) {
        this.genome = genome;
    }

    public int calculateDecision() {
        return genome.getGene(random.nextInt(genome.getSize()));
    }
}
