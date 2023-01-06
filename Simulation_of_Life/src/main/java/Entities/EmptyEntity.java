package Entities;

import Entities.Abstractions.IWorldElement;
import Misc.Vector2D;
import World.Maps.WorldMap;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class EmptyEntity implements IWorldElement {
    protected WorldMap world;
    protected Vector2D position;

    public EmptyEntity(Vector2D position) {
        this.position = position;
    }
    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public void setWorld(WorldMap world) {
        this.world = world;
    }

    @Override
    public VBox render() {
        return new VBox();
    }
}

