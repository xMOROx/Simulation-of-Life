package Entities;

import Entities.Abstractions.*;
import Logic.AnimalBrain;
import Logic.Genome;
import Logic.Reproduce;
import Misc.LoadImages;
import Misc.MapDirection;
import Misc.Vector2D;
import Settings.Variants.AnimalBehaviorVariant;
import Settings.Variants.MutationVariant;
import World.Maps.WorldMap;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.Random;


public class Animal extends StatefulObject<Animal.State> implements
        ICanMove<Animal.State, Animal>,
        IsAlive<Animal.State, Animal>,
        ICanReproduce, ICanDecide
{

    private WorldMap world;
    private final Genome genome;
    private final DefaultConfiguration config;
    private int energyToReproduce;
    private int energyConsumedWhenReproducing;
    private final AnimalBrain brain;
    private final MutationVariant mutationVariant;
    private final AnimalBehaviorVariant animalBehaviorVariant;
    public int genomeLength;
    private int currentGeneIndex = 0;
    private boolean stoppedSimulation = true;
    private boolean isSelected = false;
    private final int imageIndex = new Random().nextInt(0,12);
    private final String name;
    private Image animalImage;
    public Animal(Genome genome, Vector2D startPosition, DefaultConfiguration configuration) {
            super(new State()
                  {{
                      this.energy = configuration.initialEnergy;
                      this.position = startPosition;
                      this.direction = MapDirection.getRandomDirection();
                  }}
            );
            this.genome = genome;
            this.genomeLength = configuration.genomeLength;
            this.config = configuration;
            this.energyToReproduce = configuration.energyToReproduce;
            this.brain = new AnimalBrain(genome);
            this.mutationVariant = configuration.mutationVariant;
            this.animalBehaviorVariant = configuration.animalBehaviorVariant;
            this.energyConsumedWhenReproducing = configuration.energyConsumedWhenReproducing;
            getState().setDirection(MapDirection.getRandomDirection());
            getState().setEnergy(configuration.initialEnergy);
            this.name = "Animal" + this.hashCode();
    }

    @Override
    public void eat(int energy) {
        if(isDead()) return;
        var state = getState();
        state.setEnergy(Math.min(state.getEnergy() + energy, config.maximumEnergy));
        state.setEatenGrass(state.getEatenGrass() + 1);
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

        double percentageOfGenesOfDominantParent = reproducer.calculatePercentageOfGenesOfDominantParent();
        int numberOfGenesOfDominantParent = (int) (percentageOfGenesOfDominantParent * this.genomeLength);

        this.consumeEnergy(this.energyConsumedWhenReproducing);
        secondParent.consumeEnergy(this.energyConsumedWhenReproducing);

        Genome firstGenome = reproducer.getGenesFromSideOfDominantParent(sideOfGenomeOfDominantParent, numberOfGenesOfDominantParent);
        Genome secondGenome = reproducer.getGenesFromSideOfSubordinationParent(switch (sideOfGenomeOfDominantParent) {
            case LEFT -> Reproduce.SideOfGenome.RIGHT;
            case RIGHT -> Reproduce.SideOfGenome.LEFT;
        }, this.genomeLength - numberOfGenesOfDominantParent);

        Genome childGenome =  firstGenome.crossGenomes(secondGenome);


        if(mutationVariant == MutationVariant.FULL_RANDOM) {
            childGenome =  childGenome.getMutator().normalMutation(childGenome);
        } else {
            childGenome =  childGenome.getMutator().controlMutation(childGenome);
        }

        Vector2D childPosition = this.getState().getPosition();
        Animal child = new Animal(childGenome, childPosition, this.config);
        child.getState().setEnergy(this.energyConsumedWhenReproducing * 2);

        this.getState().setChildren(this.getState().getChildren() + 1);
        secondParent.getState().setChildren(secondParent.getState().getChildren() + 1);
        child.notice(false);
        this.notify(reproduced);
        secondParent.notify(reproduced);
        this.world.addEntity(child);

        return child;
    }

    @Override
    public Vector2D getPosition() {
        return this.getState().getPosition();
    }

    @Override
    public void makeDecision() {
       if(isDead()) return;

       this.getState().setAge(this.getState().getAge() + 1);

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
        return this.getState().getAge();
    }

    public int getChildren() {
        return this.getState().getChildren();
    }

    public int getEnergy() {
        return this.getState().getEnergy();
    }

    public String getName() {
        return name;
    }
    public Image getAnimalImage() {
        return animalImage;
    }

    @Override
    public WorldMap getWorld() {
        return world;
    }

    @Override
    public void setWorld(WorldMap world) {
        this.world = world;
    }

    @Override
    public int getEnergyToReproduce() {
        return this.energyToReproduce;
    }

    @Override
    public int getEnergyConsumedWhenReproducing() {
        return this.energyConsumedWhenReproducing;
    }

    @Override
    public void setEnergyToReproduce(int energyToReproduce) {
        this.energyToReproduce = energyToReproduce;
    }

    @Override
    public void setEnergyConsumedWhenReproducing(int energyConsumedWhenReproducing) {
        this.energyConsumedWhenReproducing = energyConsumedWhenReproducing;
    }

    public int getCurrentGeneIndex() {
        return this.currentGeneIndex;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public void notice(boolean stoppedSimulation) {
        this.stoppedSimulation = stoppedSimulation;
    }

    @Override
    public VBox render(int size, LoadImages images) {
        Label positionLabel = new Label(""+this.getPosition());
        ProgressBar energyBar = new ProgressBar();

        double value = (double) this.getEnergy() / (double) this.config.maximumEnergy;
        energyBar.setProgress(value);
        energyBar.setPrefWidth(size);
        energyBar.setMinHeight(15.0);
        energyBar.setPrefHeight(15.0);
        energyBar.setMaxHeight(15.0);

        if(value < 0.3) {
            energyBar.setStyle("-fx-accent: red;");
        } else if(value < 0.6) {
            energyBar.setStyle("-fx-accent: orange;");
        } else {
            energyBar.setStyle("-fx-accent: green;");
        }

        positionLabel.setPrefSize(size, 5);

        positionLabel.alignmentProperty().setValue(Pos.CENTER);

        this.animalImage = images.getAnimalImage(imageIndex);

        VBox animalImageBox = new VBox(new ImageView(this.animalImage), energyBar, positionLabel);

        if(this.stoppedSimulation){
            animalImageBox.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                animalImageBox.requestFocus();
                this.world.setSelectedAnimal(this);
            });
        } else {
            animalImageBox.removeEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                animalImageBox.requestFocus();
            });
        }

        animalImageBox.borderProperty()
                .bind(Bindings.when(animalImageBox.focusedProperty().or(Bindings.createBooleanBinding(() -> this.isSelected)))
                        .then(new Border(new BorderStroke(Color.rgb(238, 75, 43), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)))
                        .otherwise(new Border(new BorderStroke(Color.TRANSPARENT, BorderStrokeStyle.NONE, CornerRadii.EMPTY, BorderWidths.DEFAULT))));



        return animalImageBox;
    }

    public static class DefaultConfiguration  {
        public  int initialEnergy = 100;
        public int genomeLength = 32;
        public  int maximumEnergy = 300;
        public  int dailyEnergyLoss = 1;
        public  int energyToReproduce = 80;
        public  int energyConsumedWhenReproducing = 50;
        public MutationVariant mutationVariant = MutationVariant.FULL_RANDOM;
        public AnimalBehaviorVariant animalBehaviorVariant = AnimalBehaviorVariant.FULL_PREDICTABLE;

    }


    public static class State implements ICanMove.State, IsAlive.State {
        public int energy;
        public int age = 0;
        public int childCount = 0;
        public int eatenGrass = 0;
        public int dayOfDeath = -1;

        public Vector2D position, previousPosition;
        public MapDirection direction = MapDirection.NORTH;
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
            this.previousPosition = this.position;
            this.position = position;
        }

        @Override
        public void setDayOfDeath(int dayOfDeath) {
            this.dayOfDeath = dayOfDeath;
        }

        public int getDayOfDeath() {
            return this.dayOfDeath;
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
        public int getAge() {
            return this.age;
        }

        @Override
        public void setAge(int newAge) {
            this.age = newAge;
        }

        @Override
        public int getChildren() {
            return this.childCount;
        }

        @Override
        public void setChildren(int newChildren) {
            this.childCount = newChildren;
        }

        @Override
        public void setEnergy(int newEnergy) {
            this.energy = newEnergy;
        }

        public int getEatenGrass() {
            return eatenGrass;
        }

        public void setEatenGrass(int eatenGrass) {
            this.eatenGrass = eatenGrass;
        }
    }
}
