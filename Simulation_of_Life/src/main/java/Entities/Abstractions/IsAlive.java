package Entities.Abstractions;

import Entities.StateEvent;

public interface IsAlive <State extends IsAlive.State, StateObject extends StatefulObject<State>>{
    default boolean isDead() {
        var stateObject = (StateObject) this;
        var state = stateObject.getState();
        return state.getEnergy() <= 0;
    }

    default boolean isAlive() {return !isDead();}

    default void consumeEnergy(int consumedEnergyValue) {
        var stateObject = (StateObject) this;
        var state = stateObject.getState();
        state.setEnergy(state.getEnergy() - consumedEnergyValue);
        stateObject.notify(consumedEnergy);
        if(isDead()) stateObject.notify(died);
    }

    default void eat(int energy) {
        var stateObject = (StateObject) this;
        var state = stateObject.getState();
        state.setEnergy(state.getEnergy() + energy);
        stateObject.notify(eaten);
    }
    interface State {
        int getEnergy();
        void setEnergy(int newEnergy);
        default int getAge() {
            return 0;
        }
        default void setAge(int newAge) {
        }
        default int getChildren() {
            return 0;
        }
        default void setChildren(int newChildren) {

        }
    }

    StateEvent died = new StateEvent("died");
    StateEvent consumedEnergy = new StateEvent("consumedEnergy");
    StateEvent eaten = new StateEvent("eaten");

}
