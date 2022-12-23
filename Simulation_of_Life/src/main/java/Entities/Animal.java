package Entities;

import Entities.Abstractions.*;
import Logic.AnimalBrain;
import Logic.Genome;
import Logic.Reproduce;
import Misc.MapDirection;
import Misc.Vector2D;
import Settings.Variants.AnimalBehaviorVariant;
import Settings.Variants.MutationVariant;
import World.World;

import java.util.Random;


public class Animal extends StatefulObject<Animal.State> implements
        ICanMove<Animal.State, Animal>,
        IsAlive<Animal.State, Animal>,
        ICanReproduce, ICanDecide
{
    private int age = 0;
    private int childCount = 0;
    private World world;
    private final Genome genome;
    private final DefaultConfiguration config;
    private final int energyToReproduce;
    private final int energyConsumedWhenReproducing;
    private final AnimalBrain brain;
    private final MutationVariant mutationVariant;
    private final AnimalBehaviorVariant animalBehaviorVariant;
    private static final int genomeLength = 32;
    private int currentGeneIndex = 0;


    public Animal(Genome genome, Vector2D startPosition, DefaultConfiguration configuration) {
            super(new State()
                  {{
                      this.energy = configuration.initialEnergy;
                      this.position = startPosition;
                      this.direction = MapDirection.getRandomDirection();
                  }}
            );
            this.genome = genome;
            this.config = configuration;
            this.energyToReproduce = configuration.energyToReproduce;
            this.brain = new AnimalBrain(genome);
            this.mutationVariant = configuration.mutationVariant;
            this.animalBehaviorVariant = configuration.animalBehaviorVariant;
            this.energyConsumedWhenReproducing = configuration.energyConsumedWhenReproducing;
            getState().setDirection(MapDirection.getRandomDirection());
        }

    @Override
    public void eat(int energy) {
        if(isDead()) return;
        var state = getState();
        state.setEnergy(Math.min(state.getEnergy() + energy, config.maximumEnergy));
        notify(eaten);
    }


    @Override
    public boolean canReproduceWith(ICanReproduce other) {
        if(!(other instanceof Animal otherAnimal)) return false;
        return canReproduce() && otherAnimal.canReproduce() && this != otherAnimal;
    }

    @Override
    public boolean canReproduce() {
        return !isDead() && getState().energy >= this.energyToReproduce;
    }

    @Override
    public Animal reproduce(ICanReproduce other) {
        Animal secondParent = (Animal) other;
        Reproduce reproducer = new Reproduce(this, secondParent);
        Reproduce.SideOfGenome sideOfGenomeOfDominantParent = reproducer.getSideOfGenome();

        int percentageOfGenesOfDominantParent = (int)(reproducer.calculatePercentageOfGenesOfDominantParent());

        this.consumeEnergy(this.energyConsumedWhenReproducing);
        secondParent.consumeEnergy(this.energyConsumedWhenReproducing);

        Genome firstGenome = reproducer.getGenesFromSideOfDominantParent(sideOfGenomeOfDominantParent, percentageOfGenesOfDominantParent);
        Genome secondGenome = reproducer.getGenesFromSideOfSubordinationParent(switch (sideOfGenomeOfDominantParent) {
            case LEFT -> Reproduce.SideOfGenome.RIGHT;
            case RIGHT -> Reproduce.SideOfGenome.LEFT;
        }, 1 - percentageOfGenesOfDominantParent);

        Genome childGenome =  firstGenome.crossGenomes(secondGenome);

        if(mutationVariant == MutationVariant.FULL_RANDOM) {
            childGenome =  childGenome.getMutator().normalMutation(childGenome);
        } else {
            childGenome =  childGenome.getMutator().controlMutation(childGenome);
        }

        Vector2D childPosition = this.getPosition();
        Animal child = new Animal(childGenome, childPosition, this.config);
        child.getState().setEnergy(this.energyConsumedWhenReproducing * 2);
        this.childCount++;
        secondParent.childCount++;

        this.notify(reproduced);
        secondParent.notify(reproduced);
        this.world.addObject(child);

        return child;

    }

    @Override
    public Vector2D getPosition() {
        return this.getState().getPosition();
    }

    @Override
    public void makeDecision() {
    //TODO maybe wrong implementation
       if(isDead()) return;
       this.age++;
       this.rotate(this.genome.getGene(this.currentGeneIndex % genomeLength));

       if(this.animalBehaviorVariant == AnimalBehaviorVariant.FULL_PREDICTABLE) {
           this.currentGeneIndex++;
       } else {
           Random random = new Random();
           if(random.nextDouble( 1.0) > 1 - this.animalBehaviorVariant.getNormalMoveProbability()) {
               this.currentGeneIndex = this.brain.randomGeneActivation();
           } else {
                this.currentGeneIndex++;
           }
       }

       this.move();
       this.consumeEnergy(config.dailyEnergyLoss);
    }

    public Genome getGenome() {
        return this.genome;
    }

    public int getAge() {
        return this.age;
    }

    public int getChildCount() {
        return this.childCount;
    }

    public int getEnergy() {
        return this.getState().getEnergy();
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void setWorld(World world) {
        this.world = world;
    }

    @Override
    public void render() {
    //TODO implement
    }

    public static class DefaultConfiguration  {
        public  int initialEnergy = 100;
        public  int maximumEnergy = 300;
        public  int dailyEnergyLoss = 1;
        public  int energyToReproduce = 80;
        public  int energyConsumedWhenReproducing = 50;
        public MutationVariant mutationVariant = MutationVariant.FULL_RANDOM;
        public AnimalBehaviorVariant animalBehaviorVariant = AnimalBehaviorVariant.FULL_PREDICTABLE;

    }


    public static class State implements ICanMove.State, IsAlive.State {
        int energy;

        Vector2D position, previousPosition;
        MapDirection direction = MapDirection.NORTH;
        @Override
        public Vector2D getPosition() {
            return this.position;
        }

        @Override
        public Vector2D getPreviousPosition() {
            return previousPosition;
        }

        @Override
        public MapDirection getDirection() {
            return this.direction;
        }

        @Override
        public void setPosition(Vector2D position) {
            this.position = position;
        }

        @Override
        public void setDirection(MapDirection direction) {
            this.direction = direction;
        }

        @Override
        public int getEnergy() {
            return this.energy;
        }

        @Override
        public void setEnergy(int newEnergy) {
            this.energy = newEnergy;
        }
    }




}
