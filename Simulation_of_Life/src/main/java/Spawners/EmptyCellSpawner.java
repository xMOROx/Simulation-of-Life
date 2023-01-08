package Spawners;

import Entities.EmptyEntity;
import Misc.Vector2D;
import gui.render.World.Cell;

public class EmptyCellSpawner extends Spawner{

    public final Config config;


    public EmptyCellSpawner(Config config) {
        this.config = config;
    }


    @Override
    protected boolean tryToSpawn(int attempts) {
        Vector2D position = findValidPosition(attempts);
        if(position == null) {
            return false;
        }
        EmptyEntity emptyEntity = new EmptyEntity(position);
        emptyEntity.setWorld(this.world);
        this.world.addEntity(emptyEntity);
        return true;
    }

    @Override
    public void spawn() {
        return;
    }

    @Override
    protected void spawnFirstPopulation() {
            for (int y = 0; y <= config.mapHeight; y++) {
                for (int x = 0; x <= config.mapWidth; x++) {
                Vector2D position = new Vector2D(x, y);
                EmptyEntity emptyEntity = new EmptyEntity(position);
                this.world.addEntity(emptyEntity);
            }
        }
    }

    @Override
    public boolean canSpawn(Cell cell) {
        return Cell.isCellEmpty(cell);
    }

    public static class Config {
        protected int mapWidth = 10;
        protected int mapHeight = 10;
    }
}
