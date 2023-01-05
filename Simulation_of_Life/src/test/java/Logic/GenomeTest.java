package Logic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenomeTest {

    @Test
    void cutNucleusFromLeft() {
        Genome genome = new Genome(16);
        Genome nucleus = genome.cutNucleusFromLeft(4);
        assertEquals(4, nucleus.getGenes().size());
    }

    @Test
    void cutNucleusFromRight() {
        Genome genome = new Genome(16);
        Genome nucleus = genome.cutNucleusFromRight(4);
        assertEquals(4, nucleus.getGenes().size());
    }

    @Test
    void getGenes() {
        Genome genome = new Genome(16);
        assertEquals(16, genome.getGenes().size());
    }

    @Test
    void setMutator() {
        Genome genome = new Genome(16);
        Mutation mutator = new Mutation(new Mutation.DefaultConfiguration());
        genome.setMutator(mutator);
        assertEquals(mutator, genome.getMutator());
    }

    @Test
    void getMutator() {
        Genome genome = new Genome(16);
        Mutation mutator = new Mutation(new Mutation.DefaultConfiguration());
        genome.setMutator(mutator);
        assertEquals(mutator, genome.getMutator());
    }

    @Test
    void setGenome() {
        Genome genome = new Genome(16);
        List<Integer> genes = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            genes.add(i);
        }
        genome.setGenome(genes);
        assertEquals(genes, genome.getGenes());
    }

    @Test
    void getSize() {
        Genome genome = new Genome();
        assertEquals(8, genome.getSize());
    }

    @Test
    void getGene() {
        int size = 16;
        Genome genome = new Genome(size);
        List<Integer> genes = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            genes.add(i);
        }
        genome.setGenome(genes);
        for (int i = 0; i < size; i++) {
            assertEquals(i, genome.getGene(i));
        }
    }

    @Test
    void crossGenomes() {
        Genome genome = new Genome(16);
        Genome other = new Genome(16);
        Genome child = genome.cutNucleusFromLeft(8).crossGenomes(other.cutNucleusFromRight(8));
        assertEquals(16, child.getGenes().size());
    }
}