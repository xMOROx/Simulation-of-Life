package Entities.Abstractions;


import Misc.Vector2D;
import World.Maps.WorldMap;
import gui.interfaces.isRenderableInsideCell;

public interface IWorldElement extends isRenderableInsideCell {
    Vector2D getPosition();

    default void setWorld(WorldMap world) {
    }

    default WorldMap getWorld() {
        return null;
    }

    String toString();

}

