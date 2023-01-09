package Entities;

import Entities.Abstractions.IWorldElement;
import Misc.LoadImages;
import Misc.Vector2D;
import World.Maps.WorldMap;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Random;

public class EmptyEntity implements IWorldElement {
    protected WorldMap world;
    protected Vector2D position;
    protected final int imageIndex;

    public EmptyEntity(Vector2D position) {
        Random random = new Random();
        if (random.nextInt(100) < 90) {
            this.imageIndex = 5;
        } else {
            this.imageIndex = random.nextInt(0, 5);
        }

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
    public VBox render(int size, LoadImages images) {
        return new VBox(new ImageView(images.getEmptyImage(this.imageIndex)));
    }
}

