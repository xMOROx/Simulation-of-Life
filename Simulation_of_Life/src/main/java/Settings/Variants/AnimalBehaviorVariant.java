package Settings.Variants;

import java.util.List;
import java.util.stream.Stream;

public enum AnimalBehaviorVariant  {
    FULL_PREDICTABLE,
    CRAZY_MOVES;

    public final double normalMoveProbability = 0.8;

    public double getNormalMoveProbability() {
        return this.normalMoveProbability;
    }

    @Override
    public String toString() {
        return this.name().substring(0,1).toUpperCase() + this.name().substring(1, this.name().indexOf("_")).toLowerCase() + " "+ this.name().substring(this.name().indexOf("_") + 1).toLowerCase();
    }
    public static AnimalBehaviorVariant fromString(String value) {
        value = value.substring(0,value.indexOf(" ")).toUpperCase() + "_" + value.substring(value.indexOf(" ") + 1).toUpperCase();
        return AnimalBehaviorVariant.valueOf(value);
    }

    public static List<String> getValues() {
        return Stream.of(AnimalBehaviorVariant.values()).map(AnimalBehaviorVariant::toString).toList();
    }
}
