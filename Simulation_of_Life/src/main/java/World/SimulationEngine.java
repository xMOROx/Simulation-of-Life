package World;

import Gui.Render.World.Map;
import Settings.Config;
import Settings.SimpleConfig;
import Settings.Variants.MapVariants;
import Spawners.Builder;
import Spawners.GrassSpawner;
import Spawners.JungleSpawner;
import Spawners.Spawner;
import World.Maps.Earth;
import World.Maps.HellPortal;
import World.Maps.WorldMap;

public class SimulationEngine {
    private final WorldMap world;
    public SimulationEngine(WorldMap world) {
        this.world = world;
    }

    public static WorldMap fromInputs(Map.ExtendedConfig config, MapVariants mapVariant) {
        WorldMap map = mapVariant == MapVariants.EARTH ? new Earth(config.width, config.height) : new HellPortal(config.width, config.height);
        Spawner grassSpawner = new GrassSpawner(new GrassSpawner.Config() {{
            this.nutritionValue = config.plantEnergyRestoration;
            this.spawnRate = config.plantGrowthPerDay;
            this.growthVariant = config.growthPlantVariant;
        }});
        map.registerSpawners(grassSpawner);

        int jungleWidth = (int) (config.width * config.jungleRatio);
        int jungleHeight = (int) (config.height * config.jungleRatio);
        Spawner jungleSpawner = new JungleSpawner(new JungleSpawner.Config() {{
            this.nutritionValue = config.plantEnergyRestoration;
            this.spawnRate = 1;
            this.xMin = (config.width - jungleWidth) / 2;
            this.yMin = (config.height - jungleHeight) / 2;
            this.xMax = this.xMin + jungleWidth;
            this.yMax = this.yMin + jungleHeight;
        }});
        map.registerSpawners(jungleSpawner);
        Spawner animalSpawner = new Spawners.AnimalSpawner(new Spawners.AnimalSpawner.Config(){{
            this.startEnergy = config.startEnergy;
            this.eatingEnergy = config.plantEnergyRestoration;
            this.energyToReproduce = config.energyToReproduce;
            this.energyNeededToReproduce = config.energyNeededToReproduce;
            this.genomeLength = config.genomeLength;
            this.mutationVariant = config.mutationVariant;
            this.minimalNumberOfMutations = config.minimalNumberOfMutations;
            this.maximalNumberOfMutations = config.maximalNumberOfMutations;
            this.behaviorVariant = config.animalBehaviorVariant;
        }});
        map.registerSpawners(animalSpawner);
        return map;
    }

    public static WorldMap fromConfig (Config config, MapVariants mapVariant) {
        WorldMap map = mapVariant == MapVariants.EARTH ? new Earth(config.world.width, config.world.height) : new HellPortal(config.world.width, config.world.height);

        for (var spawnerConfig : config.spawners) {
            map.registerSpawners(Builder.buildFromConfig(spawnerConfig));
        }
        return map;
    }

    public static WorldMap fromConfig (SimpleConfig config, MapVariants mapVariant) {
        WorldMap map = mapVariant == MapVariants.EARTH ? new Earth(config.width, config.height) : new HellPortal(config.width, config.height);
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
