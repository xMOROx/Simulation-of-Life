package Entities.Abstractions;

import Entities.StateEvent;

public interface ICanReproduce extends IWorldElement {

    boolean canReproduce();

    boolean canReproduceWith(ICanReproduce other);

    ICanReproduce reproduce(ICanReproduce other);

    int getEnergyToReproduce();

    int getEnergyConsumedWhenReproducing();

    void setEnergyToReproduce(int energyToReproduce);

    void setEnergyConsumedWhenReproducing(int energyConsumedWhenReproducing);

    StateEvent reproduced = new StateEvent("reproduced");
}
