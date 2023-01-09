package World.Maps;

import Entities.Abstractions.ICanMove;
import Entities.Abstractions.StatefulObject;
import Misc.Vector2D;
import gui.render.World.Cell;

public class Earth extends WorldMap {
    public Earth(int width, int height) {
        super(width, height);
    }

    @Override
    public Vector2D mapCoords(int x, int y) {
        Vector2D position = new Vector2D(x, y);
        if (x < 0) {
            position = new Vector2D(this.width, y);
        } else if (x > this.width) {
            position = new Vector2D(0, y);
        }
        return position;
    }

    @Override
    protected Void moveEntity(StatefulObject<ICanMove.State> entity) {
        Vector2D oldPosition = this.mapCoords(entity.getState().getPreviousPosition());
        Vector2D newPosition = this.mapCoords(entity.getPosition());

        if (newPosition.getY() < 0 || newPosition.getY() > this.height) {
            entity.getState().setPosition(oldPosition);
            entity.getState().setDirection(entity.getState().getDirection().opposite());
        } else {
            entity.getState().setPosition(newPosition);
        }

        this.getCellAt(newPosition).addObject(entity);

        Cell cell = objects.get(oldPosition);
        if (cell == null) return null;
        cell.removeObject(entity);
        return null;
    }
}



