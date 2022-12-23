package World.Maps;

import Entities.Abstractions.ICanDecide;
import Entities.Abstractions.IWorldElement;
import Settings.Config;
import Settings.SimpleConfig;

import java.util.LinkedList;
import java.util.List;

public abstract class WorldMap {
    protected int width;
    protected int height;

    protected final List<ICanDecide> canDecidesEntities = new LinkedList<>();
    protected final List<IWorldElement> deadEntities = new LinkedList<>();
    protected final List<IWorldElement> newChildrenToAdd = new LinkedList<>();

    //TODO InteractionResolver
    protected final Statistics avgStats = new Statistics();


    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    protected Void objectDied(IWorldElement entity) {
        this.deadEntities.add(entity);
        return null;
    }

    public abstract  WorldMap fromConfig(Config config);
    public abstract WorldMap fromConfig(SimpleConfig config);


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Statistics getAvgStats() {
        return avgStats;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    private Statistics statistics = new Statistics();
    public static class Statistics {
        public int animalCount = 0;
        public int grassCount = 0;
        public int emptyFieldsCount = 0;
        public double avgEnergy = 0;
        public double avgLifespan = 0;
        public List<Integer> theMostPopularGenes = new LinkedList<>();
    }
}
