package Logic.Interactions;

import Entities.Abstractions.IWorldElement;
import gui.render.World.Cell;

public abstract class Interactions<Object1 extends IWorldElement, Object2 extends IWorldElement> extends BasicInteractions {
    protected final Class<Object1> object1;
    protected final Class<Object2> object2;
    Interactions(Class<Object1> object1, Class<Object2> object2) {
        this.object1 = object1;
        this.object2 = object2;
    }
    public abstract void resolveInteraction(Object1 object1, Object2 object2);

    public void resolve(Cell cell) {
        for (int i = 0, size = cell.getNumberOfObjects(); i < size; i++) {
            IWorldElement object1 = cell.getObjects().get(i);
            if (object1.getClass() == this.object1) {
                for (int j = i + 1; j < size; j++) {
                    IWorldElement object2 = cell.getObjects().get(j);
                    if (object2.getClass() == this.object2 && this.canInteract((Object1) object1, (Object2) object2)) {
                        resolveInteraction((Object1) object1, (Object2) object2);
                    }
                }
            }
        }
    }

    protected boolean canInteract(Object1 object1, Object2 object2) {
        return true;
    }
}
