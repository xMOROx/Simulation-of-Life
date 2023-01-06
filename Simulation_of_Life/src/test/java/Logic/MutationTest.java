package Logic;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MutationTest {

    @Test
    void normalMutation() {
        Genome genome = new Genome(List.of(0, 1, 2, 3, 4, 5, 6, 7), new Mutation(new Mutation.DefaultConfiguration() {{
            numberOfMinimumGenes = 2;
            numberOfMaximumGenes = 5;
        }}));
        Genome mutatedGenome = genome.getMutator().normalMutation(genome);
        assertNotEquals(genome, mutatedGenome);
    }

    @Test
    void controlMutation() {
        Genome genome = new Genome(List.of(0, 1, 2, 3, 4, 5, 6, 7), new Mutation(new Mutation.DefaultConfiguration() {{
            numberOfMinimumGenes = 2;
            numberOfMaximumGenes = 5;
        }}));
        Genome mutatedGenome = genome.getMutator().controlMutation(genome);

        assertNotEquals(genome, mutatedGenome);
    }
}