package entities;

import entities.abstractions.IsAlive;
import entities.abstractions.StatefulObject;
import misc.Vector2D;
import world.World;

public class Grass extends StatefulObject<Grass.State> implements IsAlive<Grass.State, Grass> {

    protected Vector2D position;

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
    public void setWorld(World world) {
        super.setWorld(world);
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
