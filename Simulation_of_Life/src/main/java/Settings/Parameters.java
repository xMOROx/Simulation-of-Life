package Settings;

import Settings.Variants.AnimalBehaviorVariant;
import Settings.Variants.GrowthPlantVariant;
import Settings.Variants.MapVariants;
import Settings.Variants.MutationVariant;

public class Parameters {
    private final ApplicationConfig applicationConfig;

    public Parameters(ApplicationConfig config) {
        this.applicationConfig = config;
    }

    public int getMapHeight() {
        return applicationConfig.mapHeight;
    }

    public int getMapWidth() {
        return applicationConfig.mapWidth;
    }

    public int getDailyEnergyConsumption() {
        return applicationConfig.dailyEnergyConsumption;
    }

    public int getInitialPlantsNumber() {
        return applicationConfig.initialPlantsNumber;
    }

    public int getInitialAnimalsNumber() {
        return applicationConfig.initialAnimalsNumber;
    }

    public int getInitialAnimalsEnergy() {
        return applicationConfig.initialAnimalsEnergy;
    }

    public int getEnergyRequiredForReproduction() {
        return applicationConfig.energyRequiredForReproduction;
    }

    public int getEnergyUsedForReproduction() {
        return applicationConfig.energyUsedForReproduction;
    }

    public int getGenomeLength() {
        return applicationConfig.genomeLength;
    }

    public int getNumberOfPlantGrowingDaily() {
        return applicationConfig.numberOfPlantGrowingDaily;
    }

    public MapVariants getMapVariant() {
        return applicationConfig.mapVariant;
    }

    public AnimalBehaviorVariant getBehaviourVariant() {
        return applicationConfig.behaviourVariant;
    }

    public MutationVariant getMutationVariant() {
        return applicationConfig.mutationVariant;
    }

    public GrowthPlantVariant getTerrainVariant() {
        return applicationConfig.terrainVariant;
    }

    public int getMinimumMutations() {
        return applicationConfig.minimumMutations;
    }

    public int getMaximumMutations() {
        return applicationConfig.maximumMutations;
    }

    public int getPlantsEnergy() {
        return applicationConfig.plantsEnergy;
    }

    public boolean isSaveToCSV() {
        return applicationConfig.saveToCSV;
    }

    public int getRefreshRate() {
        return applicationConfig.refreshRate;
    }

    public static class ApplicationConfig {
        public int mapHeight = 100;
        public int mapWidth = 100;
        public int dailyEnergyConsumption = 10;
        public int initialPlantsNumber = 10;
        public int initialAnimalsNumber = 10;
        public int initialAnimalsEnergy = 10;
        public int energyRequiredForReproduction = 10;
        public int energyUsedForReproduction = 10;
        public int genomeLength = 32;
        public int numberOfPlantGrowingDaily = 10;
        public int plantsEnergy = 10;
        public int refreshRate = 100;
        public MapVariants mapVariant = MapVariants.EARTH;
        public AnimalBehaviorVariant behaviourVariant = AnimalBehaviorVariant.FULL_PREDICTABLE;
        public MutationVariant mutationVariant = MutationVariant.FULL_RANDOM;
        public GrowthPlantVariant terrainVariant = GrowthPlantVariant.EQUATOR;
        public boolean saveToCSV = false;
        public int minimumMutations = 0;
        public int maximumMutations = 0;
    }
}
