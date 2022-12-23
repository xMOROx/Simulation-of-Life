package Settings.Variants;

import java.util.List;
import java.util.stream.Stream;

public enum GrowthPlantVariant {
    EQUATOR,
    TOXIC_CORPSES;
    @Override
    public String toString() {
        if(!this.name().contains("_")) {
            return this.name().substring(0, 1).toUpperCase() + this.name().substring(1).toLowerCase();
        }
        return this.name().substring(0,1).toUpperCase() + this.name().substring(1, this.name().indexOf("_")).toLowerCase() + " "+ this.name().substring(this.name().indexOf("_") + 1).toLowerCase();

    }
    public GrowthPlantVariant fromString(String value) {
        return GrowthPlantVariant.valueOf(value.toUpperCase());
    }

    public static List<String> getValues() {
        return Stream.of(GrowthPlantVariant.values()).map(GrowthPlantVariant::toString).toList();
    }
}
