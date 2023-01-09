package Spawners;

import Misc.Vector2D;
import World.Maps.WorldMap;
import com.google.gson.Gson;
import gui.render.World.Cell;

import java.util.Random;

public abstract class Spawner {
    protected static final Gson GSON = new Gson();
    protected final Random random = new Random();
    protected WorldMap world = null;

    public boolean register(WorldMap world) {
        this.world = world;
        this.spawnFirstPopulation();
        return true;
    }

    public abstract void spawn();

    protected abstract void spawnFirstPopulation();

    protected abstract boolean tryToSpawn(int attempts);

    public abstract boolean canSpawn(Cell cell);

    protected Vector2D getRandomPosition() {
        return Vector2D.randomVector(new Vector2D(0, 0), new Vector2D(this.world.getWidth(), this.world.getHeight()));
    }

    protected Vector2D findValidPosition(int attempts) {
        for (int i = 0; i < attempts; i++) {
            Vector2D newPosition = getRandomPosition();
            if (canSpawn(world.cellOrNullAt(newPosition))) {
                return newPosition;
            }
        }
        return null;
    }

}
