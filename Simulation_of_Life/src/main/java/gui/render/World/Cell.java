package Gui.Render.World;

import Gui.Render.IsRenderable;
import Entities.Abstractions.IWorldElement;

import java.util.LinkedList;
import java.util.List;

public class Cell implements IsRenderable {
    private final List<IWorldElement> objects = new LinkedList<>();
    private int deadAnimals = 0;
    public final int size;
    public final double spawnProbability;
    public Cell(Config config) {
        this.size = config.size;
        this.spawnProbability = config.grassSpawnProbability;
    }

    public Cell() {
        this(new Config());
    }

    public static boolean isCellEmpty(Cell cell) {
        return cell == null || cell.isEmpty();
    }
    public boolean isEmpty() {
        return objects.isEmpty();
    }

    public List<IWorldElement> getObjects() {
        return this.objects;
    }

    public int getDeadAnimals() {
        return this.deadAnimals;
    }
    public void addObject(IWorldElement object) {
        objects.add(object);
    }

    public void removeObject(IWorldElement object) {
        objects.remove(object);
    }

    public static boolean containsObject(Cell cell, Class c) {
        if(cell == null) return false;
        for(IWorldElement object : cell.objects) {
            if(object.getClass() == c) return true;
        }
        return false;
    }

    public static class Config {
        public double grassSpawnProbability = 0.5;
        public int size = 10;

    }

    //TODO implement cell display
    @Override
    public void render() {
        //TODO implement cell display
    }


}
