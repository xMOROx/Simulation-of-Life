package Entities;

import Entities.Abstractions.IsAlive;
import Entities.Abstractions.StatefulObject;
import Misc.LoadImages;
import Misc.Vector2D;
import World.Maps.WorldMap;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class Grass extends StatefulObject<Grass.State> implements IsAlive<Grass.State, Grass> {

    protected Vector2D position;
    protected WorldMap world;
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
        return new VBox(new ImageView(images.getRandomGrassImage()));
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
