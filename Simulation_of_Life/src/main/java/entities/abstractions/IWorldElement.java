package entities.abstractions;


import Gui.Render.IsRenderable;
import misc.Vector2D;
import world.World;

public interface IWorldElement extends IsRenderable {
    Vector2D getPosition();
    default void setWorld(World world) {
    }


    String toString();

}

