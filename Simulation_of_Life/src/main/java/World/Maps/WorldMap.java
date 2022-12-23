package World.Maps;

import Entities.Abstractions.*;
import Entities.Animal;
import Entities.Grass;
import Gui.Render.World.Cell;
import Misc.Vector2D;
import Settings.Config;
import Settings.SimpleConfig;
import Spawners.Spawner;

import java.util.*;

public abstract class WorldMap {
    protected int width;
    protected int height;

    protected final List<ICanDecide> canDecidesEntities = new LinkedList<>();
    protected final Map<Vector2D, Cell> objects = new HashMap<>();
    protected final List<IWorldElement> deadEntities = new LinkedList<>();
    protected final List<IWorldElement> newChildrenToAdd = new LinkedList<>();
    protected final List<Spawner> spawners =  new LinkedList<>();
    //TODO InteractionResolver
    protected final Statistics avgStats = new Statistics();
    protected final Statistics statistics = new Statistics();


    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
    }



    public abstract  WorldMap fromConfig(Config config);
    public abstract WorldMap fromConfig(SimpleConfig config);


    public Cell cellOrNullAt(int cellX, int cellY) {return objects.getOrDefault(this.mapCoords(cellX, cellY), null);}
    public Cell cellOrNullAt(Vector2D coords) {return objects.getOrDefault(this.mapCoords(coords), null);}


    protected Cell getCellAt(Vector2D coords) {
        Cell cell = this.cellOrNullAt(coords);
        if(cell != null) return cell;
        cell = new Cell();
//        TODO implement spawnProbability at cell
        this.objects.put(coords, cell);
        return cell;
    }

    protected Void moveEntity(StatefulObject<ICanMove.State> entity) {
        Vector2D oldPosition = this.mapCoords(entity.getState().getPreviousPosition());
        Vector2D newPosition = this.mapCoords(entity.getPosition());

        entity.getState().setPosition(newPosition);
        this.getCellAt(oldPosition).addObject(entity);

        Cell cell = objects.get(oldPosition);
        if(cell == null) return null;
        cell.removeObject(entity);
        if(cell.isEmpty()) objects.remove(oldPosition);
        return null;
    }
    protected Void objectDied(IWorldElement entity) {
        this.deadEntities.add(entity);
        return null;
    }

    protected void addEntity(IWorldElement entity) {
        this.newChildrenToAdd.add(entity);
    }
    protected void addFirstPopulation(IWorldElement entity) {
        this.getCellAt(entity.getPosition()).addObject(entity);
        entity.setWorld(this);

        if(entity instanceof ICanDecide) {
            this.canDecidesEntities.add((ICanDecide) entity);
        }

        if(entity instanceof IsAlive) {
            StatefulObject<Object> statefulObject = (StatefulObject<Object>) entity;
            statefulObject.observe(IsAlive.died, this::objectDied);
        }

        if(entity instanceof ICanMove) {
            StatefulObject<ICanMove.State> statefulObject = (StatefulObject<ICanMove.State>) entity;
            statefulObject.observe(ICanMove.moved, this::moveEntity);
        }

        if(entity instanceof Animal) this.statistics.animalCount++;
        if(entity instanceof Grass) this.statistics.grassCount++;

    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Vector2D mapCoords(int x, int y) {
        return new Vector2D(Math.floorMod(x, width), Math.floorMod(y, height));
    }
    public Vector2D mapCoords(Vector2D vector) {
        return mapCoords(vector.getX(), vector.getY());
    }

    public Statistics getAvgStats() {
        return avgStats;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public static class Statistics {
        public int animalCount = 0;
        public int grassCount = 0;
        public int emptyFieldsCount = 0;
        public double avgEnergy = 0;
        public double avgLifespan = 0;
        public List<Integer> theMostPopularGenes = new LinkedList<>();
    }
}
