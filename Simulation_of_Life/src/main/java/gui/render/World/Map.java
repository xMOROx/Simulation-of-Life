package Gui.Render.World;

import Gui.Render.CommonParams;
import Gui.Render.IsRenderable;
import Settings.Variants.AnimalBehaviorVariant;
import Settings.Variants.GrowthPlantVariant;
import Settings.Variants.MapVariants;
import Settings.Variants.MutationVariant;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Map extends CommonParams implements IsRenderable {

    protected VBox main;
    protected Stage mapStage;

    protected MapVariants mapVariant;
    protected AnimalBehaviorVariant animalBehaviorVariant;
    protected GrowthPlantVariant growthPlantVariant;
    protected MutationVariant mutationVariant;

    protected int energyToReproduce;
    protected int energyNeededToReproduce;
    protected int genomeLength;
    protected int startEnergy;
    protected int animalFirstPopulation;
    protected int plantFirstPopulation;
    protected int plantEnergyRestoration;
    protected int plantGrowthPerDay;
    protected int minimalNumberOfMutations;
    protected int maximalNumberOfMutations;
    public Map(ExtendedConfig config) {
        this.name = config.name;
        this.width = config.width;
        this.height = config.height;
        this.boxWidth = config.boxWidth;
        this.boxHeight = config.boxHeight;
        this.textSize = config.textSize;
        this.padding = config.padding;

        this.mapVariant = config.mapVariant;
        this.animalBehaviorVariant = config.animalBehaviorVariant;
        this.growthPlantVariant = config.growthPlantVariant;
        this.mutationVariant = config.mutationVariant;

        this.energyToReproduce = config.energyToReproduce;
        this.energyNeededToReproduce = config.energyNeededToReproduce;
        this.genomeLength = config.genomeLength;
        this.startEnergy = config.startEnergy;
        this.animalFirstPopulation = config.animalFirstPopulation;
        this.plantFirstPopulation = config.plantFirstPopulation;
        this.plantEnergyRestoration = config.plantEnergyRestoration;
        this.plantGrowthPerDay = config.plantGrowthPerDay;
        this.minimalNumberOfMutations = config.minimalNumberOfMutations;
        this.maximalNumberOfMutations = config.maximalNumberOfMutations;

        this.mapStage = new Stage();
        this.main = new VBox();
    }

    @Override
    public void render() {
        this.main.setPrefSize(this.width, this.height);
        this.main.setStyle("-fx-background-color: #000000");
        this.scene  = new Scene(this.main, this.width, this.height);
        this.mapStage.setTitle(this.name);
        this.mapStage.setScene(this.scene);
        this.mapStage.resizableProperty().setValue(Boolean.FALSE);
        this.mapStage.show();

    }

    public Stage getMapStage() {
        return this.mapStage;
    }

    public void setMapStage(Stage mapStage) {
        this.mapStage = mapStage;
    }
    public static class ExtendedConfig extends Config {
        public int energyToReproduce = 150;
        public int energyNeededToReproduce = 200;
        public int genomeLength = 32;
        public int startEnergy = 100;
        public int animalFirstPopulation = 10;
        public int plantFirstPopulation = 10;
        public int plantEnergyRestoration = 50;
        public int plantGrowthPerDay = 10;
        public int minimalNumberOfMutations = 1;
        public int maximalNumberOfMutations = 3;
        public MapVariants mapVariant = MapVariants.EARTH;
        public MutationVariant mutationVariant = MutationVariant.FULL_RANDOM;
        public AnimalBehaviorVariant animalBehaviorVariant = AnimalBehaviorVariant.FULL_PREDICTABLE;
        public GrowthPlantVariant growthPlantVariant = GrowthPlantVariant.EQUATOR;
    }
}
