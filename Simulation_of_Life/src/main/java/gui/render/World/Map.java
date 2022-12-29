package Gui.Render.World;

import Gui.Render.CommonParams;
import Gui.Render.IsRenderable;
import Settings.Variants.AnimalBehaviorVariant;
import Settings.Variants.GrowthPlantVariant;
import Settings.Variants.MapVariants;
import Settings.Variants.MutationVariant;
import World.Maps.WorldMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Map extends CommonParams implements IsRenderable {

    protected Stage mapStage;
    protected GridPane gridPane;
    protected MapVariants mapVariant;
    protected AnimalBehaviorVariant animalBehaviorVariant;
    protected GrowthPlantVariant growthPlantVariant;
    protected MutationVariant mutationVariant;
    protected WorldMap worldMap;
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
    public Map(ExtendedConfig config, WorldMap worldMap) {
        this.name = config.name;
        this.width = config.width;
        this.height = config.height;
        this.boxWidth = config.boxWidth;
        this.boxHeight = config.boxHeight;
        this.textSize = config.textSize;
        this.padding = config.padding;
        this.borderWidth = config.borderWidth;
        this.offsetX = config.offsetX;
        this.offsetY = config.offsetY;
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
        this.gridPane = new GridPane();
        this.worldMap = worldMap;
    }

    @Override
    public void render() {



        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(this.gridPane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.getOnDragDetected(

        );
        this.scene  = new Scene(scrollPane, this.width * 2, this.height * 2);
        this.mapStage.setTitle(this.name);
        this.mapStage.setScene(this.scene);
        this.renderMap();
        this.mapStage.resizableProperty().setValue(Boolean.FALSE);
        this.mapStage.show();
    }

    private void renderMap() {
        String inlineStyle = "-fx-border-color: darkgray; " +
                "-fx-min-width: " + this.boxWidth + ";" +
                "-fx-min-height:" + this.boxHeight + ";" +
                "-fx-padding: " + this.padding  + ";";


        this.gridPane.getChildren().clear();
        this.gridPane.setAlignment(Pos.CENTER);
        Label label = new Label("y/x");
        label.setAlignment(Pos.CENTER);
        label.setStyle(inlineStyle + "-fx-border-width:" + this.borderWidth +";" + "-fx-background-color: yellow"  + ";");

        this.gridPane.add(label, 0,0 );

        this.gridPane.setPadding(new Insets(this.padding));

        int xFit = (int) this.width / this.boxWidth;
        int yFit = (int) this.height / this.boxHeight;


        int firstCellX = this.offsetX/this.boxWidth % this.width;
        int firstCellY = this.offsetX/this.boxWidth % this.width;

        int xCount = this.width;
        int yCount = this.height;
        int value = 0;
        for (int x = 1; x <= xCount + 1; x++) {
            label = new Label(Integer.toString(value));
            label.setStyle(inlineStyle + "-fx-border-width:" + this.borderWidth + " " + this.borderWidth + " " + this.borderWidth +  this.borderWidth +" ;");
            label.setAlignment(Pos.CENTER);
            this.gridPane.add(label, x, 0);
            value += 1;
        }


        value = 0;
        for (int y = 1; y <= yCount + 1; y++) {
            label = new Label(Integer.toString(value));
            label.setStyle(inlineStyle + "-fx-border-width: 0 " + this.borderWidth + " " + this.borderWidth + " " + this.borderWidth + ";");
            label.setAlignment(Pos.CENTER);
            this.gridPane.add(label, 0, y);
            value += 1;
        }



        for (int y = 0; y <= yCount ; y++) {
            int cellY = Math.floorMod(y + firstCellY, this.height);
            for (int x = 0; x <= xCount ; x++) {
                int cellX = Math.floorMod(x + firstCellX, this.width);
                Cell cell = this.worldMap.cellOrNullAt(cellX, cellY);
                if(cell != null) {
                    this.gridPane.add(cell.render(this.boxWidth, this.padding), x, y);
                }
            }
        }
        var test = new HBox();
        test.setMinWidth(100);
        test.setMinHeight(100);
        test.setAlignment(Pos.CENTER);
        test.getChildren().add(new Label("test"));
        test.setAlignment(Pos.CENTER);
        gridPane.add(test, 1, 1);
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
