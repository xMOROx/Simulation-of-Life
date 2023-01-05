package Settings;

import Settings.Variants.AnimalBehaviorVariant;
import Settings.Variants.GrowthPlantVariant;
import Settings.Variants.MapVariants;
import Settings.Variants.MutationVariant;

public class Parameters {
    private final int mapHeight;
    private final int mapWidth;
    private final int dailyEnergyConsumption;
    private final int initialPlantsNumber;
    private final int initialAnimalsNumber;
    private final int initialAnimalsEnergy;
    private final int energyRequiredForReproduction;
    private final int energyUsedForReproduction;
    private final int genomeLength;
    private final int numberOfPlantGrowingDaily;
    private final int plantsEnergy;
    private final int minimumMutations;
    private final int maximumMutations;
    private final MapVariants mapVariant;
    private final AnimalBehaviorVariant behaviourVariant;
    private final MutationVariant mutationVariant;
    private final GrowthPlantVariant terrainVariant;
    private final boolean saveToCSV;

    public Parameters(ApplicationConfig config) {
        this.mapHeight = config.mapHeight;
        this.mapWidth = config.mapWidth;

        this.initialPlantsNumber = config.initialPlantsNumber;
        this.initialAnimalsNumber = config.initialAnimalsNumber;
        this.initialAnimalsEnergy = config.initialAnimalsEnergy;

        this.dailyEnergyConsumption = config.dailyEnergyConsumption;
        this.plantsEnergy = config.plantsEnergy;
        this.energyRequiredForReproduction = config.energyRequiredForReproduction;
        this.energyUsedForReproduction = config.energyUsedForReproduction;

        this.genomeLength = config.genomeLength;
        this.numberOfPlantGrowingDaily = config.numberOfPlantGrowingDaily;

        this.mapVariant = config.mapVariant;
        this.behaviourVariant = config.behaviourVariant;
        this.mutationVariant = config.mutationVariant;
        this.terrainVariant = config.terrainVariant;

        this.minimumMutations = config.minimumMutations;
        this.maximumMutations = config.maximumMutations;

        this.saveToCSV = config.saveToCSV;
    }

    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }

    public int getDailyEnergyConsumption() {
        return dailyEnergyConsumption;
    }

    public int getInitialPlantsNumber() {
        return initialPlantsNumber;
    }

    public int getInitialAnimalsNumber() {
        return initialAnimalsNumber;
    }

    public int getInitialAnimalsEnergy() {
        return initialAnimalsEnergy;
    }

    public int getEnergyRequiredForReproduction() {
        return energyRequiredForReproduction;
    }

    public int getEnergyUsedForReproduction() {
        return energyUsedForReproduction;
    }

    public int getGenomeLength() {
        return genomeLength;
    }

    public int getNumberOfPlantGrowingDaily() {
        return numberOfPlantGrowingDaily;
    }

    public MapVariants getMapVariant() {
        return mapVariant;
    }

    public AnimalBehaviorVariant getBehaviourVariant() {
        return behaviourVariant;
    }

    public MutationVariant getMutationVariant() {
        return mutationVariant;
    }

    public GrowthPlantVariant getTerrainVariant() {
        return terrainVariant;
    }
    public int getMinimumMutations() {
        return minimumMutations;
    }
    public int getMaximumMutations() {
        return maximumMutations;
    }

    public int getPlantsEnergy() {
        return plantsEnergy;
    }

    public boolean isSaveToCSV() {
        return saveToCSV;
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
        public MapVariants mapVariant = MapVariants.EARTH;
        public AnimalBehaviorVariant behaviourVariant = AnimalBehaviorVariant.FULL_PREDICTABLE;
        public MutationVariant mutationVariant = MutationVariant.FULL_RANDOM;
        public GrowthPlantVariant terrainVariant = GrowthPlantVariant.EQUATOR;
        public boolean saveToCSV = false;
        public int minimumMutations = 0;
        public int maximumMutations = 0;
    }
}
