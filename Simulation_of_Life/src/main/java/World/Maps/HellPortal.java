package World.Maps;

import Entities.Abstractions.ICanMove;
import Entities.Abstractions.IWorldElement;
import Entities.Abstractions.IsAlive;
import Entities.Abstractions.StatefulObject;
import Entities.Animal;
import Misc.Vector2D;
import gui.render.World.Cell;

public class HellPortal extends WorldMap {
    public HellPortal(int width, int height) {
        super(width, height);
    }

    @Override
    public Vector2D mapCoords(int x, int y) {
        if (x < 0  || x > width || y < 0 || y > height) {
            return Vector2D.randomVector(new Vector2D(0, 0), new Vector2D(width, height));
        }
        return new Vector2D(x, y);
    }

    @Override
    protected Void moveEntity(StatefulObject<ICanMove.State> entity) {
        Vector2D oldPosition = this.mapCoords(entity.getState().getPreviousPosition());
        Vector2D newPosition = this.mapCoords(entity.getPosition());

        entity.getState().setPosition(newPosition);
        this.getCellAt(newPosition).addObject(entity);

        Cell cell = objects.get(oldPosition);

        if(cell == null) return null;
        if(!oldPosition.add(entity.getState().getDirection().toUnitVector()).equals(newPosition)) {
            Animal animal = (Animal) cell.getObjectEqualTo(entity);
            animal.consumeEnergy(animal.getEnergyConsumedWhenReproducing());
        }

        cell.removeObject(entity);
        return null;
    }
}

