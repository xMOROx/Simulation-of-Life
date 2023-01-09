package Entities.Abstractions;

public interface ICanDecide {
    void makeDecision();

    void notice(boolean stoppedSimulation);
}
