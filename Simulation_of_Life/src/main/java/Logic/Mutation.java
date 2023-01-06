package Logic;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Mutation
{
    private final DefaultConfiguration config;
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
        int[] newGenes = genes.stream().mapToInt(Integer::intValue).toArray();
        int numberOfMutations = random.nextInt(this.config.numberOfMinimumGenes, this.config.numberOfMaximumGenes + 1);
        System.out.println("Number of mutations: " + numberOfMutations);
        for (int i = 0; i < numberOfMutations; i++) {
            int geneIndex = random.nextInt(genes.size());
            int geneValue = random.nextInt(8);
            newGenes[geneIndex] = geneValue;
        }
        return new Genome(Arrays.stream(newGenes).boxed().toList(), genome.getMutator());
    }

    public Genome controlMutation(Genome genome) {
        List<Integer> genes = genome.getGenes();
        int[] newGenes = genome.getGenes().stream().mapToInt(Integer::intValue).toArray();
        int numberOfMutations = random.nextInt(this.config.numberOfMinimumGenes, this.config.numberOfMaximumGenes + 1);
        for (int i = 0; i < numberOfMutations; i++) {

            int geneIndex = random.nextInt(genes.size());
            int geneValue = List.of(genes.get(geneIndex) + 1, genes.get(geneIndex) - 1).get(random.nextInt(2));
            newGenes[geneIndex] = geneValue;
        }
        return new Genome(Arrays.stream(newGenes).boxed().toList(), genome.getMutator());
    }
}
