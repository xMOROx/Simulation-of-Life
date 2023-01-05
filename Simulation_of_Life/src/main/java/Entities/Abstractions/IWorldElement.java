package Entities.Abstractions;


import gui.interfaces.isRenderableInsideCell;
import Misc.Vector2D;
import World.Maps.WorldMap;

public interface IWorldElement extends isRenderableInsideCell {
    Vector2D getPosition();
    default void setWorld(WorldMap world) {
    }

    String toString();

}

