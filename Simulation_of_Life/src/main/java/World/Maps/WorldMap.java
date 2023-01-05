package World.Maps;

import Entities.Abstractions.*;
import Entities.Animal;
import Entities.Grass;
import Logic.Interactions.InteractionResolver;
import gui.render.World.Cell;
import Misc.Vector2D;
import Spawners.Spawner;

import java.util.*;

public abstract class WorldMap {
    protected int width;
    protected int height;

    protected final List<ICanDecide> canDecidesEntities = new LinkedList<>();
    protected final Map<Vector2D, Cell> objects = new HashMap<>();
    protected final List<IWorldElement> deadEntities = new LinkedList<>();
    protected final List<IWorldElement> newChildrenToAdd = new LinkedList<>();
    protected final InteractionResolver resolver = new InteractionResolver();
    protected final SortedMap<List<Integer>, Integer> genoTypes = new TreeMap<>((o1, o2) -> {
        for(int i = 0; i < o1.size(); i++) {
            if(o1.get(i) < o2.get(i)) return -1;
            if(o1.get(i) > o2.get(i)) return 1;
        }
        return 0;
    });

    protected final List<Spawner> spawners =  new LinkedList<>();
    protected int animalLifespanSum = 0;
    protected int animalDead = 0;
    protected final Statistics avgStats = new Statistics();
    protected final Statistics statistics = new Statistics();


    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void removeDeadEntities() {
        for (IWorldElement entity : deadEntities) {
            this.removeEntity(entity);
        }
        deadEntities.clear();
    }

    public void entitiesMove() {
        for (ICanDecide entity : canDecidesEntities) {
            entity.makeDecision();
        }
    }

    public void resolveInteractions() {
        for(var entity : objects.values()) {
            resolver.resolve(entity);
        }
    }

    public void firstPopulation() {
        this.addNewEntities();
    }

    public void addNewEntities() {
        this.spawners.forEach(Spawner::spawn);
        this.newChildrenToAdd.forEach(this::addPopulation);
        newChildrenToAdd.clear();
    }

    public void registerSpawners(Spawner spawner) {
        if(spawner.register(this)) {
            spawners.add(spawner);
        }
    }

    public Cell cellOrNullAt(int cellX, int cellY) {return objects.getOrDefault(this.mapCoords(cellX, cellY), null);}
    public Cell cellOrNullAt(Vector2D coords) {return objects.getOrDefault(this.mapCoords(coords), null);}


    protected Cell getCellAt(Vector2D coords) {
        Cell cell = this.cellOrNullAt(coords);
        if(cell != null) return cell;
        cell = new Cell();
        this.objects.put(coords, cell);
        return cell;
    }

    protected Void moveEntity(StatefulObject<ICanMove.State> entity) {
        Vector2D oldPosition = this.mapCoords(entity.getState().getPreviousPosition());
        Vector2D newPosition = this.mapCoords(entity.getPosition());

        entity.getState().setPosition(newPosition);
        this.getCellAt(newPosition).addObject(entity);

        Cell cell = objects.get(oldPosition);
        if(cell == null) return null;
        cell.removeObject(entity);
        if(cell.isEmpty()) objects.remove(oldPosition);
        return null;
    }
    protected Void objectDied(IWorldElement entity) {
        this.deadEntities.add(entity);

        if(entity instanceof Animal animal) {
            if(this.genoTypes.getOrDefault(animal.getGenome().getGenes(), null) == null) return null;
            if(this.genoTypes.get(animal.getGenome().getGenes()) > 0)  {
                this.genoTypes.put(animal.getGenome().getGenes(), this.genoTypes.get(animal.getGenome().getGenes()) - 1);
            } else {
                this.genoTypes.remove(animal.getGenome().getGenes());
            }
        }

        return null;
    }

    public void addEntity(IWorldElement entity) {
        this.newChildrenToAdd.add(entity);
        if(entity instanceof Animal animal) {
            this.genoTypes.put(animal.getGenome().getGenes(), this.genoTypes.getOrDefault(animal.getGenome().getGenes(), 0) + 1);
        }
    }
    public void addPopulation(IWorldElement entity) {
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
    protected void removeEntity(IWorldElement entity) {
        Vector2D position = entity.getPosition();
        Cell cell = this.getCellAt(position);
        cell.removeObject(entity);
        if(cell.isEmpty()) this.objects.remove(position);
        if(entity instanceof Animal animal) {
            this.animalLifespanSum += animal.getAge();
            this.animalDead++;
            this.statistics.animalCount--;
        }

        if(entity instanceof Grass) this.statistics.grassCount--;
    }


    public void UpdateStatistics() {
        statistics.avgLifespan = this.animalDead == 0 ? 0 : (double) this.animalLifespanSum / this.animalDead;
        int energySum = 0;
        int childrenSum = 0;

        for(Cell cell : this.objects.values()) {
            for(IWorldElement entity : cell.getObjects()) {
                if(entity instanceof Animal animal) {
                    energySum += animal.getEnergy();
                    childrenSum += animal.getChildrenCount();
                }
            }

            if(cell.isEmpty()) {
                this.statistics.emptyFieldsCount++;
            }
        }

        this.statistics.theMostPopularGenes = this.genoTypes.lastKey();

        this.statistics.avgEnergy = this.statistics.animalCount == 0 ? 0 : (double) energySum / this.statistics.animalCount;
        this.statistics.avgChildren = this.statistics.animalCount == 0 ? 0 : (double) childrenSum / this.statistics.animalCount;

        this.avgStats.avgEnergy = (this.avgStats.avgEnergy + this.statistics.avgEnergy) / 2;
        this.avgStats.avgLifespan = (this.avgStats.avgLifespan + this.statistics.avgLifespan) / 2;
        this.avgStats.avgChildren = (this.avgStats.avgChildren + this.statistics.avgChildren) / 2;
        this.avgStats.animalCount = (this.avgStats.animalCount + this.statistics.animalCount) / 2;
        this.avgStats.grassCount = (this.avgStats.grassCount + this.statistics.grassCount) / 2;

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
        public double avgChildren = 0;
        public double avgLifespan = 0;
        public List<Integer> theMostPopularGenes = new LinkedList<>();
    }
}

