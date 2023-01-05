package World;

import World.Maps.Settings.Config;
import World.Maps.Settings.Parameters;
import World.Maps.Settings.SimpleConfig;
import World.Maps.Settings.Variants.MapVariants;
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

        Spawner grassSpawner = new GrassSpawner(new GrassSpawner.Config() {{
          this.nutritionValue = parameters.getPlantsEnergy();
          this.spawnRate = parameters.getNumberOfPlantGrowingDaily();
        }});

        map.registerSpawners(grassSpawner);

        Spawner animalSpawner = new AnimalSpawner(new AnimalSpawner.Config() {{
          this.startEnergy = parameters.getInitialAnimalsEnergy();
          this.initialPopulation = parameters.getInitialAnimalsNumber();
          this.dailyEnergyLoss = parameters.getDailyEnergyConsumption();
          this.energyToReproduce = parameters.getEnergyUsedForReproduction();
          this.energyConsumedWhenReproducing = parameters.getEnergyRequiredForReproduction();
          this.mutationVariant = parameters.getMutationVariant();
          this.animalBehaviorVariant = parameters.getBehaviourVariant();
          this.genomeLength = parameters.getGenomeLength();
        }});

        map.registerSpawners(animalSpawner);


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
            observer.updateGui();
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
        this.refreshGui();
        this.world.removeDeadEntities();
        this.refreshGui();
        this.world.entitiesMove();
        this.refreshGui();
        this.world.resolveInteractions();
        this.refreshGui();
        this.world.addNewEntities();
        this.world.UpdateStatistics();
    }

}
