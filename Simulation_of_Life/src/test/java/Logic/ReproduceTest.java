package Logic;

import Entities.Animal;
import Misc.Vector2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReproduceTest {

    @Test
    void findDominationParent() {
        Mutation mutator = new Mutation(new Mutation.DefaultConfiguration());
        Vector2D position = new Vector2D(0, 0);
        Animal animal1 = new Animal(new Genome(16, mutator), position, new Animal.DefaultConfiguration());
        animal1.getState().setEnergy(200);
        Animal animal2 = new Animal(new Genome(16, mutator), position, new Animal.DefaultConfiguration());
        Reproduce reproduce = new Reproduce(animal1, animal2);
        assertEquals(animal1, reproduce.findDominationParent());
    }

    @Test
    void findSubordinationParent() {
        Mutation mutator = new Mutation(new Mutation.DefaultConfiguration());
        Vector2D position = new Vector2D(0, 0);
        Animal animal1 = new Animal(new Genome(16, mutator), position, new Animal.DefaultConfiguration());
        animal1.getState().setEnergy(200);
        Animal animal2 = new Animal(new Genome(16, mutator), position, new Animal.DefaultConfiguration());
        Reproduce reproduce = new Reproduce(animal1, animal2);
        assertEquals(animal2, reproduce.findSubordinationParent());
    }


    @Test
    void calculatePercentageOfGenesOfDominantParent() {
        Mutation mutator = new Mutation(new Mutation.DefaultConfiguration());
        Vector2D position = new Vector2D(0, 0);
        Animal animal1 = new Animal(new Genome(16, mutator), position, new Animal.DefaultConfiguration());
        animal1.getState().setEnergy(300);
        Animal animal2 = new Animal(new Genome(16, mutator), position, new Animal.DefaultConfiguration());
        Reproduce reproduce = new Reproduce(animal1, animal2);
        assertEquals(0.75, reproduce.calculatePercentageOfGenesOfDominantParent());
    }

    @Test
    void getGenesFromSideOfDominantParent() {
    }

    @Test
    void getGenesFromSideOfSubordinationParent() {
    }
}