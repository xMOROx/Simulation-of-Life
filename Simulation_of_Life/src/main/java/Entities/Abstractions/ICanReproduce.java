package Entities.Abstractions;

import Entities.StateEvent;

public interface ICanReproduce extends IWorldElement {

    boolean canReproduce();
    boolean canReproduceWith(ICanReproduce other);
    ICanReproduce reproduce(ICanReproduce other);

    StateEvent reproduced = new StateEvent("reproduced");
}
