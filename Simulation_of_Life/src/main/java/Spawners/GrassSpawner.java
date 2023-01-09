package Spawners;

import Entities.Grass;
import Misc.Vector2D;
import com.google.gson.JsonElement;
import gui.render.World.Cell;

import java.util.Random;

public class GrassSpawner extends Spawner {
    public final Config config;

    public GrassSpawner(Config config) {
        this.config = config;
    }

    @Override
    public boolean canSpawn(Cell cell) {
        Random random = new Random();
        double spawnProbability = random.nextInt(101) / 100.0;
        return Cell.isCellEmpty(cell) && (spawnProbability <= cell.getSpawnProbability()) && (cell.getObjects().stream().noneMatch(object -> object instanceof Grass));
    }

    @Override
    protected boolean tryToSpawn(int attempts) {
        Vector2D position = findValidPosition(attempts);
        if (position == null) {
            return false;
        }
        Grass grass = new Grass(position, this.config.nutritionValue);
        this.world.addEntity(grass);
        return true;
    }

    @Override
    public void spawn() {
        if (this.world.getObjects().values().stream().allMatch(object -> object.getObjects().stream().anyMatch(object1 -> object1 instanceof Grass))) {
            return;
        }
        for (int i = 0; i < this.config.spawnRate; i++) {
            if (!tryToSpawn(100)) {
                break;
            }
        }
    }

    @Override
    protected void spawnFirstPopulation() {
        for (int i = 0; i < this.config.firstPopulationSpawnRate; i++) {
            if (!tryToSpawn(100)) {
                break;
            }
        }
    }

    @Override
    protected Vector2D findValidPosition(int attempts) {
        for (int i = 0; i < attempts; i++) {
            Vector2D newPosition = getRandomPosition();
            if (canSpawn(this.world.cellOrNullAt(newPosition)) && this.world.getNewChildrenToAdd().stream().noneMatch(entity -> entity.getPosition().equals(newPosition))) {
                return newPosition;
            }
        }
        return null;
    }


    public static Spawner fromConfig(JsonElement configJson) {
        Config config = GSON.fromJson(configJson, Config.class);
        return new GrassSpawner(config);
    }

    public static class Config {
        protected int spawnRate = 10;
        protected int firstPopulationSpawnRate = 10;
        protected int nutritionValue = 10;
    }
}
