package Logic.Interactions;

import Entities.Animal;
import Entities.Grass;
import Misc.BestNObjects;
import gui.render.World.Cell;

import java.util.*;

public class InteractionResolver {
    private final List<BasicInteractions> interactions = new LinkedList<>();
    private void registerInteraction(BasicInteractions interaction) {
        interactions.add(interaction);
    }

    public void resolve(Cell cell) {
        for(BasicInteractions interaction : interactions) {
            interaction.resolve(cell);
        }
    }
    {
        registerInteraction(new BasicInteractions() {

            @Override
            public void resolve(Cell cell) {
                BestNObjects<Animal> top =  new BestNObjects<>(1, (Animal a1) -> a1.getState().getEnergy());

                if(this.countAnimalsWithTheHighestSameEnergy(cell) <= 1) {
                    top = new BestNObjects<>(1, (Animal a1) -> a1.getState().getEnergy());
                } else if (this.countAnimalsWithTheHighestSameAge(cell) <= 1) {
                    top = new BestNObjects<>(1, (Animal a1) -> a1.getState().getAge());
                } else if (this.countAnimalsWithTheHighestSameChildrenCount(cell) <= 1) {
                    top = new BestNObjects<>(1, (Animal a1) -> a1.getState().getChildren());
                }


                Grass grass = null;

                for (var object : cell.getObjects()) {
                    if (object instanceof Animal) {
                        top.add((Animal) object);
                    } else if (object instanceof Grass) {
                        grass = (Grass) object;
                    }
                }

                if (grass != null && top.count() == 1) {
                    Animal animal = top.get(0);
                    if(animal.isAlive() && grass.isAlive()) {
                        animal.eat(grass.getState().getEnergy());
                        grass.consumeEnergy(grass.getState().getEnergy());
                    }
                }
            }
        });
        registerInteraction(new BasicInteractions() {

            @Override
            public void resolve(Cell cell) {

                BestNObjects<Animal> top =  new BestNObjects<>(2, (Animal a1) -> a1.getState().getEnergy()); ;

                if(this.countAnimalsWithTheHighestSameEnergy(cell) <= 2) {
                    top = new BestNObjects<>(2, (Animal a1) -> a1.getState().getEnergy());
                } else if (this.countAnimalsWithTheHighestSameAge(cell) <= 2) {
                    top = new BestNObjects<>(2, (Animal a1) -> a1.getState().getAge());
                } else if (this.countAnimalsWithTheHighestSameChildrenCount(cell) <= 2) {
                    top = new BestNObjects<>(2, (Animal a1) -> a1.getState().getChildren());
                }

                for (var object : cell.getObjects()) {
                    if (object instanceof Animal) {
                        top.add((Animal) object);
                    }
                }

                if (top.count() == 2) {
                    Animal animal1 = top.get(0);
                    Animal animal2 = top.get(1);
                    if(animal1.canReproduceWith(animal2)) {
                        animal1.reproduce(animal2);
                    }
                }
            }
        });
    }
}
