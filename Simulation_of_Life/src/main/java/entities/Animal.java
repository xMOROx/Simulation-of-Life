package entities;

import entities.abstractions.ICanMove;
import entities.abstractions.ICanReproduce;
import entities.abstractions.IsAlive;
import entities.abstractions.StatefulObject;
import logic.Genome;
import misc.MapDirection;
import misc.Vector2D;



public class Animal extends StatefulObject<Animal.State> implements ICanMove<Animal.State, Animal>, IsAlive<Animal.State, Animal>, ICanReproduce
{
    Animal(State initialState) { super(initialState); }

    private int age = 0;
    private DefaultConfiguration config;

    private Genome genome;

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
        return !isDead() && getState().energy >= config.energyToReproduce;
    }

    @Override
    public Animal reproduce(ICanReproduce other) {
        //TODO: implement
        return null;
    }


    public Genome getGenome() {
        return this.genome;
    }

    public static class DefaultConfiguration  {
        public  int initialEnergy = 100;
        public  int dailyEnergyLoss = 1;
        public  int energyToReproduce = 50;
        public  int maximumEnergy = 100;
        public  int energyFromGrass = 10;
    }


    public static class State implements ICanMove.State, IsAlive.State {
        int energy;
        Vector2D position;
        MapDirection direction = MapDirection.NORTH;
        @Override
        public Vector2D getPosition() {
            return this.position;
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

    @Override
    public Vector2D getPosition() {
        return this.getState().getPosition();
    }


}
