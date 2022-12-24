package Spawners;

import Entities.Animal;
import Gui.Render.World.Cell;
import Logic.Genome;
import Misc.Vector2D;
import World.Maps.WorldMap;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class AnimalSpawner extends Spawner {

    protected final AnimalSpawner.Config config;
    protected final Animal.DefaultConfiguration defaultConfig;
    protected static Gson g = new Gson();

    public AnimalSpawner(Config config) {
        this.config = config;
        this.defaultConfig = new Animal.DefaultConfiguration() {{
            this.dailyEnergyLoss = config.dailyEnergyLoss;
            this.initialEnergy = config.startEnergy;
        }};
    }

    public static Spawner fromConfig(JsonElement configJson) {
        Config config = g.fromJson(configJson, Config.class);
        return new AnimalSpawner(config);
    }

    @Override
    public boolean register(WorldMap world) {
        super.register(world);
        for (int i = 0; i < config.initialPopulation; i++) {
            this.trySpawnOne(120);
        }
        return true;
    }

    protected boolean trySpawnOne(int attempts) {
        Vector2D position = findValidPosition(attempts);
        if (position == null) {
            return false;
        }
        Animal animal = new Animal(new Genome(Animal.genomeLength), position, defaultConfig);
        this.world.addEntity(animal);
        return true;
    }

    @Override
    public void spawn() {

    }

    @Override
    public boolean canSpawn(Cell cell) {
        return cell.isEmpty();
    }


    public static class Config {
        protected int eatingEnergy = 100;
        protected int startEnergy = 100;
        protected int dailyEnergyLoss = 100;
        protected int initialPopulation = 100;
    }
}
