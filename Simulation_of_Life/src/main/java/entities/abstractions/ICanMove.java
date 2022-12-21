package entities.abstractions;

import entities.StateEvent;
import misc.MapDirection;
import misc.Vector2D;


public interface ICanMove<State extends ICanMove.State, StateObject extends StatefulObject<State>>
{

    default void move() {

        var stateObject = (StateObject) this;
        var state = stateObject.getState();
        state.setPosition(state.getPosition().add(state.getDirection().toUnitVector()));
        stateObject.notify(moved);
    }



    default void rotate(int i) {
        var stateObject = (StateObject)this;
        var state = stateObject.getState();
        state.setDirection(state.getDirection().rotateRight(i));

    }

    interface State
    {
        Vector2D getPreviousPosition();
        Vector2D getPosition();
        MapDirection getDirection();
        void setPosition(Vector2D position);
        void setDirection(MapDirection direction);
    }

    StateEvent moved = new StateEvent("moved");
}
