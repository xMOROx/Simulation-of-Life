package Gui.Render.World;

import Gui.Render.IsRenderable;
import Entities.Abstractions.IWorldElement;

import java.util.LinkedList;
import java.util.List;

public class Cell implements IsRenderable {
    public List<IWorldElement> objects = new LinkedList<>();
    public int deadAnimals = 0;
    public final int size;
    public final double spawnProbability;
    public Cell(Config config) {
        this.size = config.size;
        this.spawnProbability = config.grassSpawnProbability;
    }

    public static boolean isCellEmpty(Cell cell) {
        return cell == null || cell.isEmpty();
    }
    public boolean isEmpty() {
        return objects.isEmpty();
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
