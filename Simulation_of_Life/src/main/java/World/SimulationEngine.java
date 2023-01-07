package World;

import Settings.Config;
import Settings.Constants;
import Settings.Parameters;
import Settings.SimpleConfig;
import Settings.Variants.MapVariants;
import Spawners.*;
import World.Maps.Earth;
import World.Maps.HellPortal;
import World.Maps.WorldMap;
import gui.interfaces.IGuiObserver;

import java.util.LinkedList;
import java.util.List;

public class SimulationEngine extends Thread {
    private final WorldMap world;
    private final List<IGuiObserver> guiObservers = new LinkedList<>();
    public SimulationEngine(WorldMap world) {
        this.world = world;

    }

    public static WorldMap fromConfig (Config config) {
        WorldMap map = new Earth(config.world.width, config.world.height);
        for (var spawnerConfig : config.spawners) {
            map.registerSpawners(Builder.buildFromConfig(spawnerConfig));
        }
        return map;
    }

    public static WorldMap fromConfig(Parameters parameters) {
        WorldMap map = parameters.getMapVariant() == MapVariants.EARTH ? new Earth(parameters.getMapWidth(), parameters.getMapHeight()) : new HellPortal(parameters.getMapWidth(), parameters.getMapHeight());
        Spawner emptyCellSpawner = new EmptyCellSpawner(new EmptyCellSpawner.Config() {{
            this.mapHeight = parameters.getMapHeight();
            this.mapWidth = parameters.getMapWidth();
        }});

        map.registerSpawners(emptyCellSpawner);

        Spawner grassSpawner = new GrassSpawner(new GrassSpawner.Config() {{
          this.nutritionValue = parameters.getPlantsEnergy();
          this.spawnRate = parameters.getNumberOfPlantGrowingDaily();
          this.firstPopulationSpawnRate = parameters.getInitialPlantsNumber();
        }});

        map.registerSpawners(grassSpawner);

        Spawner animalSpawner = new AnimalSpawner(new AnimalSpawner.Config() {{
          this.startEnergy = parameters.getInitialAnimalsEnergy();
          this.initialPopulation = parameters.getInitialAnimalsNumber();
          this.dailyEnergyLoss = parameters.getDailyEnergyConsumption();
          this.energyToReproduce = parameters.getEnergyRequiredForReproduction();
          this.energyConsumedWhenReproducing =  parameters.getEnergyUsedForReproduction();
          this.mutationVariant = parameters.getMutationVariant();
          this.minimumMutations = parameters.getMinimumMutations();
          this.maximumMutations = parameters.getMaximumMutations();
          this.animalBehaviorVariant = parameters.getBehaviourVariant();
          this.genomeLength = parameters.getGenomeLength();
          this.maximumEnergy = parameters.getInitialAnimalsEnergy() * 2;
        }});

        map.registerSpawners(animalSpawner);
        map.setGrowthPlantVariant(parameters.getTerrainVariant());

        map.setCategoryForCells();

        return map;
    }

    public static WorldMap fromConfig (SimpleConfig config) {
        WorldMap map = new Earth(config.width, config.height);
        Spawner grassSpawner = new GrassSpawner(new GrassSpawner.Config(){{
            this.nutritionValue = config.plantEnergy;
            this.spawnRate = 1;
        }});
        map.registerSpawners(grassSpawner);

        int jungleWidth = (int) (config.width * config.jungleRatio);
        int jungleHeight = (int) (config.height * config.jungleRatio);

        Spawner jungleSpawner = new JungleSpawner(new JungleSpawner.Config(){{
            this.nutritionValue = config.plantEnergy;
            this.spawnRate = 1;
            this.xMin = (config.width - jungleWidth) / 2;
            this.yMin = (config.height - jungleHeight) / 2;
            this.xMax = this.xMin + jungleWidth;
            this.yMax = this.yMin + jungleHeight;
        }});

        map.registerSpawners(jungleSpawner);

        Spawner animalSpawner = new Spawners.AnimalSpawner(new Spawners.AnimalSpawner.Config(){{
            this.startEnergy = config.startEnergy;
            this.dailyEnergyLoss = config.moveEnergy;;
        }});

        map.registerSpawners(animalSpawner);

        return map;
    }

    public void registerGuiObserver(IGuiObserver observer) {
        guiObservers.add(observer);
    }

    private void refreshGui() {
        for (var observer : guiObservers) {
            observer.updateGuiMap();
        }
    }

    private void refreshGuiCharts() {
        for (var observer : guiObservers) {
            observer.updateGuiCharts();
        }
    }

    public WorldMap getWorld() {
        return world;
    }

    public void run() {
        while (this.world.getStatistics().animalCount > 0) {
            this.step();
        }
    }

    public void step() {
        this.refreshGuiCharts();
        this.refreshGui();
        this.world.removeDeadEntities();
        this.refreshGui();
        this.world.entitiesMove();
        this.refreshGui();
        this.world.resolveInteractions();
        this.refreshGui();
        this.world.addNewEntities();
        this.refreshGui();
        this.world.UpdateStatistics();
    }

}
