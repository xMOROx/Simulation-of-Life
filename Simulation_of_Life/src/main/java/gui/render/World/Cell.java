package gui.render.World;


import Entities.Abstractions.IWorldElement;
import gui.interfaces.IGuiObserver;
import gui.interfaces.isRenderableOnMap;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.LinkedList;
import java.util.List;

public class Cell implements isRenderableOnMap {
    private final List<IWorldElement> objects = new LinkedList<>();
    private int deadAnimals = 0;
    private final int size;
    private final GridPane cellGrid;

    public final double spawnProbability;
    public Cell(Config config) {
        this.size = config.size;
        this.spawnProbability = config.grassSpawnProbability;
        this.cellGrid = new GridPane();
    }


    public Cell() {
        this(new Config());
    }

    public static boolean isCellEmpty(Cell cell) {
        return cell == null || cell.isEmpty();
    }
    public boolean isEmpty() {
        return  objects.isEmpty();
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

    public int getNumberOfObjects() {
        return this.objects.size();
    }


    public GridPane render(int size, int padding) {
        cellGrid.getChildren().clear();
        cellGrid.getChildren().add(new Label("Komorka"));
        int cellSize = (int) (size * Math.ceil(this.objects.size() / 2.0));
        cellGrid.setPrefSize(cellSize, cellSize);
        for (int y = 0, i = 0; y < cellSize; y+=size) {
            for (int x = 0; x < size*2; x+=size, i++) {
                if(i >= this.objects.size()) break;
                cellGrid.add(this.objects.get(i).render(), x, y);
            }
        }
        return cellGrid;
    }

    public static class Config {
        public double grassSpawnProbability = 0.5;
        public int size = 10;
    }

}
