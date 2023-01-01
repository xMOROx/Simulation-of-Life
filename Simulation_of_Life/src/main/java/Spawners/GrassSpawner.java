package Spawners;

import Entities.Grass;
import Gui.Render.World.Cell;
import Misc.Vector2D;
import Settings.Variants.GrowthPlantVariant;
import World.Maps.WorldMap;
import com.google.gson.JsonElement;

public class GrassSpawner extends Spawner {
    public final Config config;

    public GrassSpawner(Config config) {
        this.config = config;
    }

    @Override
    public boolean canSpawn(Cell cell) {
        return Cell.isCellEmpty(cell);
    }
    @Override
    public boolean register(WorldMap world) {
        super.register(world);
        return true;
    }

    private boolean tryToSpawn() {
        Vector2D position = findValidPosition(100);
        if(position == null) {
            return false;
        }
        Grass grass = new Grass(position, this.config.nutritionValue);
        this.world.addEntity(grass);
        return true;
    }

    @Override
    public void spawn() {
        for (int i = 0; i < this.config.spawnRate; i++) {
            if(!tryToSpawn()) {
                break;
            }
        }
    }

    public static Spawner fromConfig(JsonElement configJson) {
        Config config = GSON.fromJson(configJson, Config.class);
        return new GrassSpawner(config);
    }

    public static class Config {
        protected int spawnRate = 10;
        protected int nutritionValue = 10;
        protected GrowthPlantVariant growthVariant = GrowthPlantVariant.EQUATOR;
    }
}
