package logic;

import java.util.List;
import java.util.Random;

public class Mutation
{
    private DefaultConfiguration config;
    private final Random random = new Random();
    public Mutation(DefaultConfiguration config) {
        this.config = config;
    }

    public static class DefaultConfiguration {
        public int numberOfMinimumGenes = 1;
        public int numberOfMaximumGenes = 2;
    }

    public Genome normalMutation(Genome genome) {
        List<Integer> genes = genome.getGenes();
        int numberOfMutations = random.nextInt(this.config.numberOfMinimumGenes, this.config.numberOfMaximumGenes + 1);
        for (int i = 0; i < numberOfMutations; i++) {
            int geneIndex = random.nextInt(genes.size());
            int geneValue = random.nextInt(8);
            genes.set(geneIndex, geneValue);
        }
        return new Genome(genes, genome.getMutator());
    }

    public Genome controlMutation(Genome genome) {
        List<Integer> genes = genome.getGenes();
        int numberOfMutations = random.nextInt(this.config.numberOfMinimumGenes, this.config.numberOfMaximumGenes + 1);
        for (int i = 0; i < numberOfMutations; i++) {
            int geneIndex = random.nextInt(genes.size());
            int geneValue = List.of(genes.get(geneIndex) + 1, genes.get(geneIndex) - 1).get(random.nextInt(2));
            genes.set(geneIndex, geneValue);
        }
        return new Genome(genes, genome.getMutator());
    }
}
