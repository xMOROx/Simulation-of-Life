package entities.abstractions;

import gui.render.IsRenderable;
import misc.Vector2D;

public interface IWorldElement extends IsRenderable {
    Vector2D getPosition();
    default void setWorld() {
    }

    String toString();

}

