package Spawners;

import Entities.Grass;
import gui.render.World.Cell;
import Misc.Vector2D;
import World.Maps.WorldMap;
import com.google.gson.JsonElement;

public class JungleSpawner extends Spawner {
    private final Config config;
    public JungleSpawner(Config config) {
        this.config = config;
    }

    @Override
    protected Vector2D getRandomPosition(){
        return new Vector2D(
                random.nextInt(this.config.xMax - this.config.xMin) + this.config.xMin,
                random.nextInt(this.config.yMax - this.config.yMin) + this.config.yMin);
    }

    @Override
    protected boolean tryToSpawn(int attempts) {
        Vector2D position = findValidPosition(attempts);
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
            if(!tryToSpawn(100)) {
                break;
            }
        }
    }

    @Override
    protected void spawnFirstPopulation() {
        for (int i = 0; i < this.config.firstPopulationSpawnRate; i++) {
            if(!tryToSpawn(100)) {
                break;
            }
        }
    }

    @Override
    public boolean canSpawn(Cell cell) {
        return Cell.isCellEmpty(cell);
    }

    public static Spawner fromConfig(JsonElement configJSON) {
        Config config = GSON.fromJson(configJSON, Config.class);
        return new JungleSpawner(config);
    }


    public static class Config {
        protected int xMin = 20, xMax = 60;
        protected int yMin = 20, yMax = 60;
        protected int spawnRate = 50;
        protected int firstPopulationSpawnRate = 100;
        protected int nutritionValue = 50;
    }
}
