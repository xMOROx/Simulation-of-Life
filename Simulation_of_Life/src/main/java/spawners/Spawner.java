package spawners;

import com.google.gson.Gson;
import misc.Vector2D;
import world.World;

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

    protected Vector2D getRandomPosition() {
        return new Vector2D(random.nextInt(world.getWidth()), random.nextInt(world.getHeight()));
    }



}
