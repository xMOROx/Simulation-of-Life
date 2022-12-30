package World;

import Settings.Config;
import Settings.SimpleConfig;
import Spawners.Builder;
import Spawners.GrassSpawner;
import Spawners.JungleSpawner;
import Spawners.Spawner;
import World.Maps.Earth;
import World.Maps.WorldMap;

public class SimulationEngine {
    private final WorldMap world;
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
            this.dailyEnergyLoss = config.moveEnergy;
            this.eatingEnergy = config.plantEnergy;
        }});

        map.registerSpawners(animalSpawner);

        return map;
    }

    public void step() {
        this.world.removeDeadEntities();
        this.world.entitiesMove();
        this.world.resolveInteractions();
        this.world.addNewEntities();
        this.world.UpdateStatistics();
    }

}
