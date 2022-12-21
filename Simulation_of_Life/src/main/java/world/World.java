package world;

import entities.abstractions.IWorldElement;

public class World {
    private int width;
    private int height;


    public World(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addObject(IWorldElement object) {

    }


}
