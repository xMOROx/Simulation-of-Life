package Entities;

import Entities.Abstractions.IsAlive;
import Entities.Abstractions.StatefulObject;
import Misc.LoadImages;
import Misc.Vector2D;
import World.Maps.WorldMap;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.Random;

public class Grass extends StatefulObject<Grass.State> implements IsAlive<Grass.State, Grass> {

    protected Vector2D position;
    protected WorldMap world;
    protected final int imageIndex = new Random().nextInt(0,2);
    public Grass(Vector2D position, int grassNutritionValue) {
        super(new State()
            {{
                this.nutritionValue = grassNutritionValue;
            }}
        );
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
        Label energyLabel = new Label("" + this.getState().getEnergy());
        energyLabel.setPrefSize(size, 5);
        energyLabel.setAlignment(Pos.CENTER);
        return new VBox(new ImageView(images.getGrassImage(this.imageIndex)), energyLabel);
    }

    public static class State implements IsAlive.State {
        int nutritionValue;

        @Override
        public int getEnergy() {
            return nutritionValue;
        }

        @Override
        public void setEnergy(int newEnergy) {
            this.nutritionValue = newEnergy;
        }
    }


}
