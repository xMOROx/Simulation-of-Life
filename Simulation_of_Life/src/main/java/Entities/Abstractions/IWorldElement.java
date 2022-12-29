package Entities.Abstractions;


import Gui.Render.IsRenderable;
import Gui.Render.isRenderableInsideCell;
import Gui.Render.isRenderableOnMap;
import Misc.Vector2D;
import World.Maps.WorldMap;

public interface IWorldElement extends isRenderableInsideCell {
    Vector2D getPosition();
    default void setWorld(WorldMap world) {
    }

    String toString();

}

