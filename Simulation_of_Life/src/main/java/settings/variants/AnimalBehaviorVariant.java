package settings.variants;

public enum AnimalBehaviorVariant {
    FULL_PREDICTABLE,
    SOME_CRAZY_MOVES;

    public final double normalMoveProbability = 0.8;

    public double getNormalMoveProbability() {
        return this.normalMoveProbability;
    }
}
