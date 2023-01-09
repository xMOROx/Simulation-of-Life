package Logic;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Mutation {
    private final DefaultConfiguration config;

    public Mutation(DefaultConfiguration config) {
        this.config = config;
    }

    public static class DefaultConfiguration {
        public int numberOfMinimumGenes = 1;
        public int numberOfMaximumGenes = 2;
    }

    public DefaultConfiguration getConfig() {
        return this.config;
    }

    public Genome normalMutation(Genome genome) {
        Random random = new Random();
        List<Integer> genes = genome.getGenes();
        int[] newGenes = genes.stream().mapToInt(Integer::intValue).toArray();
        int numberOfMutations = random.nextInt(this.config.numberOfMinimumGenes, this.config.numberOfMaximumGenes + 1);
        for (int i = 0; i < numberOfMutations; i++) {
            int geneIndex = random.nextInt(genes.size());
            int geneValue = random.nextInt(7);
            newGenes[geneIndex] = geneValue;
        }
        return new Genome(Arrays.stream(newGenes).boxed().toList(), genome.getMutator());
    }

    public Genome controlMutation(Genome genome) {
        Random random = new Random();
        List<Integer> genes = genome.getGenes();
        int[] newGenes = genome.getGenes().stream().mapToInt(Integer::intValue).toArray();
        int numberOfMutations = random.nextInt(this.config.numberOfMinimumGenes, this.config.numberOfMaximumGenes + 1);
        for (int i = 0; i < numberOfMutations; i++) {
            int geneIndex = random.nextInt(genes.size());
            int geneValue = List.of(genes.get(geneIndex) + 1, genes.get(geneIndex) - 1).get(random.nextInt(2));
            if (geneValue < 0) {
                geneValue = 7;
            } else if (geneValue > 7) {
                geneValue = 0;
            }
            newGenes[geneIndex] = geneValue;
        }
        return new Genome(Arrays.stream(newGenes).boxed().toList(), genome.getMutator());
    }
}
