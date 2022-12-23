package Spawners;

import Gui.Render.World.Cell;
import com.google.gson.Gson;
import Misc.Vector2D;
import World.World;

import java.util.Random;

public abstract class Spawner {
    private static final Gson GSON = new Gson();
    private Random random = new Random();
    private World world = null;

    public boolean register(World world) {
        this.world = world;
        return true;
    }

    public abstract void spawn();

    public abstract boolean canSpawn(Cell cell);

    protected Vector2D getRandomPosition() {
        return new Vector2D(random.nextInt(world.getWidth()), random.nextInt(world.getHeight()));
    }

    protected Vector2D findValidPosition(int attempts) {
        Vector2D position = getRandomPosition();
        for (int i = 0; i < attempts; i++) {
            Vector2D newPosition = getRandomPosition();
            if(canSpawn(world.cellOrNullAt(newPosition))) {
                return newPosition;
            }
        }
        return null;
    }

}
