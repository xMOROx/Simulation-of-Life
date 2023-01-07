package Logic.Interactions;

import Entities.Animal;
import gui.render.World.Cell;

import java.util.SortedMap;
import java.util.TreeMap;

public abstract class BasicInteractions {
    public abstract void resolve(Cell cell);
    protected int countAnimalsWithTheHighestSameEnergy(Cell cell) {
        SortedMap<Integer, Integer> energyToCount = new TreeMap<>();
        for (var object : cell.getObjects()) {
            if (object instanceof Animal animal) {
                if (animal.isAlive()) {
                    int energy = animal.getEnergy();
                    energyToCount.put(energy, energyToCount.getOrDefault(energy, 0) + 1);
                }
            }
        }
        if(energyToCount.isEmpty()) {
            return 0;
        }
        return energyToCount.get(energyToCount.lastKey());
    }

    protected int countAnimalsWithTheHighestSameAge(Cell cell){
        SortedMap<Integer, Integer> ageToCount = new TreeMap<>();
        for (var object : cell.getObjects()) {
            if (object instanceof Animal animal) {
                if (animal.isAlive()) {
                    int age = animal.getAge();
                    ageToCount.put(age, ageToCount.getOrDefault(age, 0) + 1);
                }
            }
        }
        if(ageToCount.isEmpty()) {
            return 0;
        }
        return ageToCount.get(ageToCount.lastKey());
    }

    protected int countAnimalsWithTheHighestSameChildrenCount(Cell cell) {
        SortedMap<Integer, Integer> childrenCountToCount = new TreeMap<>();
        for (var object : cell.getObjects()) {
            if (object instanceof Animal animal) {
                if (animal.isAlive()) {
                    int childrenCount = animal.getChildren();
                    childrenCountToCount.put(childrenCount, childrenCountToCount.getOrDefault(childrenCount, 0) + 1);
                }
            }
        }
        if (childrenCountToCount.isEmpty()) {
            return 0;
        }
        return childrenCountToCount.get(childrenCountToCount.lastKey());
    }
}
