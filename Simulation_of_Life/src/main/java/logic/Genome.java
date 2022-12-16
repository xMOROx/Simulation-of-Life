package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Genome {
    private List<Integer> genes;
    private final int size;
    private static final Random random = new Random();

    public Genome() {
        this(8);
    }

    public Genome(int size) {
        this(randomGenome(size));
    }

    public Genome(List<Integer> genes) {
        this.genes = genes;
        this.size = genes.size();
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
        return new Genome(genes.subList(0, nucleusSize));
    }

    public Genome cutNucleusFromRight(int nucleusSize) {
        return new Genome(genes.subList(size - nucleusSize, size));
    }

    public List<Integer> getGenes() {
        return this.genes;
    }

    public void setGenome(List<Integer> genes) {
        this.genes = genes;
    }

    public Genome crossGenomes(Genome other) {
        return new Genome(
                Stream.of(this.genes, other.getGenes())
                        .flatMap(List::stream)
                        .collect(Collectors.toList())
        );
    }

}



