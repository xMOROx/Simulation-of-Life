package Entities.Abstractions;

import Entities.StateEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class StatefulObject<State> implements IWorldElement {
    private State state;

    private Map<StateEvent, List<Function<StatefulObject<State>, Void>>> subscribers;

    protected void setState(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public StatefulObject(State initialState) {
        this.state = initialState;
        subscribers = new HashMap<>();
    }


    public final State observe(StateEvent event, Function<StatefulObject<State>, Void> listener) {
        var list = subscribers.getOrDefault(event, null);
        if (list == null) {
            list = new ArrayList<>();
            subscribers.put(event, list);
        }

        list.add(listener);
        return this.state;
    }

    public void stopObserving(StateEvent event, Function<StatefulObject<State>, Void> listener) {
        var list = subscribers.getOrDefault(event, null);
        if (list == null) {
            return;
        }

        list.remove(listener);
    }


    public void notify(StateEvent event) {
        var list = subscribers.getOrDefault(event, null);
        if (list != null) {
            for (var subscriber : list) {
                subscriber.apply(this);
            }
        }

    }

}
