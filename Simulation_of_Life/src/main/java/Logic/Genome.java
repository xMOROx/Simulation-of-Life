package Logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Genome {
    private List<Integer> genes;
    private final int size;
    private static final Random random = new Random();
    private Mutation mutator;
    public Genome() {
        this(8);
    }

    public Genome(int size) {
        this(randomGenome(size), new Mutation(new Mutation.DefaultConfiguration()));
    }

    public Genome(int size, Mutation mutator) {
        this(randomGenome(size), mutator);
    }

    public Genome(List<Integer> genes, Mutation mutator) {
        this.genes = genes;
        this.size = genes.size();
        this.mutator = mutator;
    }

    private static List<Integer> randomGenome(int size) {
        List<Integer> genes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            genes.add(randomGene());
        }
        return genes;
    }

    private static int randomGene() {
        return random.nextInt(8);
    }

    public Genome cutNucleusFromLeft(int nucleusSize) {
        return new Genome(this.genes.subList(0, nucleusSize), this.mutator);
    }

    public Genome  cutNucleusFromRight(int nucleusSize) {
        return new Genome(this.genes.subList(this.size - nucleusSize, this.size), this.mutator);
    }

    public List<Integer> getGenes() {
        return this.genes;
    }

    public void setMutator(Mutation mutator) {
        this.mutator = mutator;
    }
    public Mutation getMutator() {
        return this.mutator;
    }

    public void setGenome(List<Integer> genes) {
        this.genes = genes;
    }

    public int getSize() {
        return size;
    }

    public int getGene(int index) {
        return genes.get(index);
    }
    public Genome crossGenomes(Genome other) {
        return new Genome(
                Stream.of(this.genes, other.getGenes())
                        .flatMap(List::stream)
                        .collect(Collectors.toList()), this.mutator);
    }

}



