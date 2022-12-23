package Settings.Variants;

import java.util.List;
import java.util.stream.Stream;

public enum MapVariants {
    EARTH,
    HELL;
    @Override
    public String toString() {
        return this.name().substring(0,1).toUpperCase() + this.name().substring(1).toLowerCase();
    }
    public MapVariants fromString(String value) {
        return MapVariants.valueOf(value.toUpperCase());
    }

    public static List<String> getValues() {
        return Stream.of(MapVariants.values()).map(MapVariants::toString).toList();
    }
}
