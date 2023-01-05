package Logic;

import Entities.Animal;

import java.util.Random;

public class Reproduce {

    private final  Random random = new Random();
    private final Animal firstParent;
    private final Animal secondParent;


    public Reproduce(Animal first, Animal second) {
        this.firstParent = first;
        this.secondParent = second;
    }

    protected  Animal findDominationParent() {
        return firstParent.getEnergy() > secondParent.getEnergy() ? firstParent : secondParent;
    }

    protected  Animal findSubordinationParent() {
        return this.findDominationParent() == firstParent ? secondParent : firstParent;
    }

    public  SideOfGenome getSideOfGenome() {
        return random.nextBoolean() ? SideOfGenome.LEFT : SideOfGenome.RIGHT;
    }
    // Calculate energy of first parent before reproduction
    public double calculatePercentageOfGenesOfDominantParent() {
        int sumOfEnergy = this.firstParent.getEnergy() + secondParent.getEnergy();
        return Math.round((double)(findDominationParent().getEnergy()) / sumOfEnergy * 100.0) / 100.0;
    }

    private Genome getGenesFromSideOfParent(SideOfGenome sideOfGenome, int nucleusSize, Animal parent) {
        return sideOfGenome == SideOfGenome.LEFT ? parent.getGenome().cutNucleusFromLeft(nucleusSize) : parent.getGenome().cutNucleusFromRight(nucleusSize);
    }
    public Genome getGenesFromSideOfDominantParent(SideOfGenome sideOfGenome, int nucleusSize) {
        return this.getGenesFromSideOfParent(sideOfGenome, nucleusSize, this.findDominationParent());
    }

    public Genome getGenesFromSideOfSubordinationParent(SideOfGenome sideOfGenome, int nucleusSize) {
        return this.getGenesFromSideOfParent(sideOfGenome, nucleusSize, this.findSubordinationParent());
    }
    public enum SideOfGenome {
        LEFT,
        RIGHT
    }
}
