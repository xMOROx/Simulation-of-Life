package Settings.Variants;

import java.util.List;
import java.util.stream.Stream;

public enum MutationVariant {
    FULL_RANDOM,
    SLIGHT_CORRECTION;

    @Override
    public String toString() {
        return this.name().substring(0,1).toUpperCase() + this.name().substring(1, this.name().indexOf("_")).toLowerCase() + " "+ this.name().substring(this.name().indexOf("_") + 1).toLowerCase();
    }
    public MutationVariant fromString(String value) {
        return MutationVariant.valueOf(value.toUpperCase());
    }

    public static List<String> getValues() {
        return Stream.of(MutationVariant.values()).map(MutationVariant::toString).toList();
    }
}
