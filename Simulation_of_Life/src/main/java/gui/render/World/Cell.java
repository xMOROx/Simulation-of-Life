package gui.render.World;


import Entities.Abstractions.IWorldElement;
import Entities.EmptyEntity;
import Entities.Grass;
import Misc.CellCategory;
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
    private CellCategory category;
    public double spawnProbability;
    public Cell(Config config) {
        this.size = config.size;
        this.spawnProbability = config.grassSpawnProbability;
        this.category = config.category;
        this.cellGrid = new GridPane();
    }

    public IWorldElement getObjectEqualTo(IWorldElement object) {
        for (IWorldElement o : objects) {
            if (o.equals(object)) {
                return o;
            }
        }
        return null;
    }
    public void setCategory(CellCategory category) {
        this.category = category;
    }
    public CellCategory getCategory() {
        return this.category;
    }

    public Cell() {
        this(new Config());
    }

    public static boolean isCellEmpty(Cell cell) {
        return cell == null || cell.isEmpty();
    }
    public boolean isEmpty() {
        return this.containOnlyEmptyEntities();
    }

    private boolean containOnlyEmptyEntities() {
        return objects.stream().allMatch(object -> object instanceof EmptyEntity);
    }

    public List<IWorldElement> getObjects() {
        return this.objects;
    }

    public double getSpawnProbability() {
        return this.spawnProbability;
    }

    public void setSpawnProbability(double spawnProbability) {
        this.spawnProbability = spawnProbability;
    }
    public int getDeadAnimals() {
        return this.deadAnimals;
    }
    public void incrementDeadAnimals(int value) {
        this.deadAnimals += value;
    }
    public void addObject(IWorldElement object) {
        if(this.containOnlyEmptyEntities()) {
            this.objects.clear();
        }
        this.objects.add(object);
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

    private void setStyleBasedOnNumberOfDeathAnimals() {

        if(this.deadAnimals <= 5) {
            if(category == CellCategory.FIRST) {
                this.cellGrid.setStyle("-fx-background-color: #00b300");
            } else {
                this.cellGrid.setStyle("-fx-background-color: #007f5f");
            }
        }
        else if (this.deadAnimals <= 10) {
            if(category == CellCategory.FIRST) {
                this.cellGrid.setStyle("-fx-background-color: #00cc00");
            } else {
                this.cellGrid.setStyle("-fx-background-color: #2b9348");
            }
        }
        else if (this.deadAnimals <= 15) {
            if (category == CellCategory.FIRST) {
                this.cellGrid.setStyle("-fx-background-color: #00e600");
            } else {
                this.cellGrid.setStyle("-fx-background-color: #55a630");
            }
        }
        else if (this.deadAnimals <= 20) {
            if(category == CellCategory.FIRST) {
                this.cellGrid.setStyle("-fx-background-color: #b3b300");
            } else {
                this.cellGrid.setStyle("-fx-background-color: #80b918");
            }
        }
        else if (this.deadAnimals <= 25) {
            if(category == CellCategory.FIRST) {
                this.cellGrid.setStyle("-fx-background-color: #b38c00");
            } else {
                this.cellGrid.setStyle("-fx-background-color: #aacc00");
            }
        }
        else if (this.deadAnimals <= 30) {
            if(category == CellCategory.FIRST) {
              this.cellGrid.setStyle("-fx-background-color: #b35e00");
            } else {
                this.cellGrid.setStyle("-fx-background-color: #d6de00");
            }
        }
        else if (this.deadAnimals <= 33) {
            this.cellGrid.setStyle("-fx-background-color: #f7f700");
        }
        else if (this.deadAnimals <= 36) {
            this.cellGrid.setStyle("-fx-background-color: #ffba00");
        }
        else if (this.deadAnimals <= 39) {
            this.cellGrid.setStyle("-fx-background-color: #ff8c00");
        }
        else if (this.deadAnimals <= 41) {
            this.cellGrid.setStyle("-fx-background-color: #ff5e00");
        }
        else if (this.deadAnimals <= 43) {
            this.cellGrid.setStyle("-fx-background-color: #ff3100");
        }
        else if (this.deadAnimals <= 45) {
            this.cellGrid.setStyle("-fx-background-color: #ff0300");
        }
        else {
            this.cellGrid.setStyle("-fx-background-color: #b20000");
        }
    }

    public GridPane render(int size, int padding) {
        this.cellGrid.getChildren().clear();
        this.setStyleBasedOnNumberOfDeathAnimals();
        int cellSize = (int) (size * Math.ceil(this.objects.size() / 2.0));
        this.cellGrid.setPrefSize(size*2, cellSize);
        for (int y = 0, i = 0; y < cellSize; y+=size) {
            for (int x = 0; x < size*2; x+=size, i++) {
                if(i >= this.objects.size()) break;
                this.cellGrid.add(this.objects.get(i).render(), x, y);
            }
        }
        return cellGrid;
    }

    public static class Config {
        public double grassSpawnProbability = 0.7;
        public int size = 20;
        public CellCategory category = CellCategory.SECOND;
    }
}
