package Entities.Abstractions;


import Gui.Render.IsRenderable;
import Misc.Vector2D;
import World.World;

public interface IWorldElement extends IsRenderable {
    Vector2D getPosition();
    default void setWorld(World world) {
    }


    String toString();

}

