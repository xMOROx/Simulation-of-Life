package Entities.Abstractions;


import Gui.Render.IsRenderable;
import Misc.Vector2D;
import World.Maps.WorldMap;

public interface IWorldElement extends IsRenderable {
    Vector2D getPosition();
    default void setWorld(WorldMap world) {
    }

    String toString();

}

