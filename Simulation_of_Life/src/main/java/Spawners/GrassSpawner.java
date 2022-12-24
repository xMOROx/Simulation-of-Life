package Spawners;

import Entities.Grass;
import Gui.Render.World.Cell;
import Misc.Vector2D;
import World.Maps.WorldMap;

public class GrassSpawner extends Spawner {
    public final Config config;

    public GrassSpawner(Config config) {
        this.config = config;
    }

    @Override
    public boolean canSpawn(Cell cell) {
        return cell.isEmpty();
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

    public static class Config {
        protected int spawnRate = 10;
        protected int nutritionValue = 10;
    }
}
