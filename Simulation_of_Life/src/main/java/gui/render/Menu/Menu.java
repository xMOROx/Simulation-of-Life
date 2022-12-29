package Gui.Render.Menu;

import Gui.Render.CommonParams;
import Gui.Render.IsRenderable;
import Gui.Render.World.Map;
import World.Maps.Earth;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import Settings.Variants.AnimalBehaviorVariant;
import Settings.Variants.GrowthPlantVariant;
import Settings.Variants.MapVariants;
import Settings.Variants.MutationVariant;

import java.util.function.Consumer;


public class Menu extends CommonParams implements IsRenderable {
    //TODO implement recieving data from settings
    //TODO implement saving data to settings

    protected Button launchMapButton;
    protected Consumer<Map> launchMap;
    protected Button ExitButton;
    protected VBox main;
    protected VBox entitiesPlace;
    protected HBox animalsSettings;
    protected VBox plantsSettings;
    protected HBox miscSettings;
    protected VBox buttonsSettings;
    protected VBox mutationSettings;
    protected VBox mapSettingsPlace;
    protected HBox parametersPlace;
    protected VBox entitiesPlaceSettings;
    protected Stage menuStage;
    protected String color = "#ccc";

    protected InputController mapWidthInput;
    protected InputController mapHeightInput;
    protected InputController genomeLengthInput;
    protected InputController parentEnergyReproductionConsumeInput;
    protected InputController parentEnergyNeedToReproductionInput;
    protected InputController animalFirstPopulationInput;
    protected InputController startEnergyInput;
    protected InputController plantGrowthInput;
    protected InputController plantEnergyRestorationInput;
    protected InputController plantFirstPopulationInput;
    protected InputController mutationMinInput;
    protected InputController mutationMaxInput;

    protected ComboBoxController mapVariantSelector;
    protected ComboBoxController animalBehaviorVariantSelector;
    protected ComboBoxController plantGrowthVariantSelector;
    protected ComboBoxController mutationVariantSelector;
    public Menu(Config config, Consumer<Map> onMapLaunch) {
        this.name = config.name;
        this.width = config.width;
        this.height = config.height;
        this.boxWidth = config.boxWidth;
        this.boxHeight = config.boxHeight;
        this.textSize = config.textSize;
        this.padding  = config.padding;
        this.launchMap = onMapLaunch;

        this.menuStage = new Stage();

        this.launchMapButton = new Button("Launch Map");
        this.ExitButton = new Button("Exit");

        this.main = new VBox();
        this.parametersPlace = new HBox();
        this.entitiesPlace = new VBox();
        this.mapSettingsPlace = new VBox();
        this.animalsSettings = new HBox();
        this.plantsSettings = new VBox();
        this.miscSettings = new HBox();
        this.entitiesPlaceSettings = new VBox();
        this.buttonsSettings = new VBox();
        this.mutationSettings = new VBox();
    }



    @Override
    public void render() {
        Label title = new Label("Parameters");
        title.setMinWidth(this.width);
        title.setMinHeight(this.boxHeight);
        title.setAlignment(javafx.geometry.Pos.CENTER);
        title.setStyle("-fx-font-size:"+ (this.textSize) +"px; -fx-background-color:" + this.color + ";");


//       Map
        int mapWidth = (int)(this.width/4);
        this.mapSettingsPlace.setMinWidth(mapWidth);
        this.mapSettingsPlace.setMinHeight(this.height - this.boxHeight);
        this.mapSettingsPlace.setStyle("-fx-background-color:" + this.color + ";");
        this.mapVariantSelector = new ComboBoxController(mapWidth, boxHeight, MapVariants.getValues(), "Map Variants");
        this.mapWidthInput = new InputController(new InputController.Config(){{
            this.name = "Map Width";
            this.width = mapWidth - 20;
            this.height = boxHeight;
            this.min = 1;
            this.max = 500;
        }});


        this.mapHeightInput = new InputController(new InputController.Config(){{
            this.name = "Map Height";
            this.width = mapWidth - 2*this.padding;
            this.height = boxHeight;
            this.min = 1;
            this.max = 500;
        }});

        this.genomeLengthInput = new InputController(new InputController.Config(){{
            this.name = "Genome Length";
            this.width = mapWidth - 2*this.padding;
            this.height = boxHeight;
            this.min = 2;
            this.max = 32;
        }});

        mapWidthInput.render();
        mapHeightInput.render();
        genomeLengthInput.render();
        mapVariantSelector.render();

        this.mapSettingsPlace.getChildren().addAll(mapVariantSelector.getBox(), mapWidthInput.getBox(), mapHeightInput.getBox(), genomeLengthInput.getBox());
        this.mapSettingsPlace.alignmentProperty().setValue(Pos.CENTER);
//      Map

//      Entities
        int entitiesWidth = (int)(this.width * 3/4);
        int entitiesHeight = this.height - this.boxHeight ;
        this.entitiesPlace.setMinWidth(entitiesWidth);
        this.entitiesPlace.setMinHeight(this.height - this.boxHeight);
        this.entitiesPlace.setStyle(" -fx-background-color:" + this.color + "; -fx-font-weight: 600;");


        this.entitiesPlaceSettings.setMinWidth(entitiesWidth);
        this.entitiesPlaceSettings.setMinHeight(entitiesHeight);

        int entitiesSettingsHeight = (int)(entitiesHeight/3)  + 1;
        int animalSettingsWidth = (int)(entitiesWidth/2);
        this.animalsSettings.setMinWidth(entitiesWidth);
        this.animalsSettings.setMinHeight(entitiesSettingsHeight);

        VBox parentSettings = new VBox();
        parentSettings.setMinWidth(animalSettingsWidth);
        parentSettings.setMinHeight(entitiesSettingsHeight);


        this.parentEnergyReproductionConsumeInput = new InputController(new InputController.Config(){{
            this.name = "Energy Reproduction Consume";
            this.width = animalSettingsWidth;
            this.height = entitiesSettingsHeight/2;
        }});

        parentEnergyReproductionConsumeInput.render();

        this.parentEnergyNeedToReproductionInput = new InputController(new InputController.Config(){{
            this.name = "Energy Need To Reproduction";
            this.width = animalSettingsWidth;
            this.height = entitiesSettingsHeight/2;
        }});

        parentEnergyNeedToReproductionInput.render();

        parentSettings.getChildren().addAll( parentEnergyReproductionConsumeInput.getBox(), parentEnergyNeedToReproductionInput.getBox());

        VBox childSettings = new VBox();
        childSettings.setMinWidth(animalSettingsWidth);
        childSettings.setMinHeight(entitiesSettingsHeight);

        this.animalBehaviorVariantSelector = new ComboBoxController(animalSettingsWidth, boxHeight, AnimalBehaviorVariant.getValues(), "Behavior Variants");

        this.startEnergyInput = new InputController(new InputController.Config(){{
            this.name = "Start Energy";
            this.width = animalSettingsWidth/2 - this.padding;
            this.height = boxHeight;
        }});

        this.animalFirstPopulationInput = new InputController(new InputController.Config(){{
            this.name = "Animals First Population";
            this.width = animalSettingsWidth/2 - this.padding;
            this.height = boxHeight;
        }});

        HBox animalInputs = new HBox();
        animalInputs.setMinWidth(animalSettingsWidth);
        animalInputs.setMinHeight(boxHeight);
        animalInputs.getChildren().addAll(startEnergyInput.getBox(), animalFirstPopulationInput.getBox());

        animalBehaviorVariantSelector.render();
        startEnergyInput.render();
        animalFirstPopulationInput.render();

        childSettings.getChildren().addAll( animalBehaviorVariantSelector.getBox(), animalInputs);

        this.animalsSettings.getChildren().addAll(parentSettings, childSettings);

        this.plantsSettings.setMinWidth(entitiesWidth);
        this.plantsSettings.setMinHeight(entitiesSettingsHeight);

        HBox plantsInputsUpper = new HBox();
        plantsInputsUpper.setMinWidth(entitiesWidth);
        plantsInputsUpper.setMinHeight((int)(entitiesSettingsHeight/2));
        HBox plantsInputsLower = new HBox();
        plantsInputsLower.setMinWidth(entitiesWidth);
        plantsInputsLower.setMinHeight((int)(entitiesSettingsHeight/2));

        this.plantEnergyRestorationInput = new InputController(new InputController.Config(){{
            this.name = "Plant Energy Restoration";
            this.width = animalSettingsWidth;
            this.height = entitiesSettingsHeight/2;
        }});

        this.plantFirstPopulationInput = new InputController(new InputController.Config(){{
            this.name = "Plants First Population";
            this.width = animalSettingsWidth;
            this.height = entitiesSettingsHeight/2;
        }});

        this.plantGrowthInput = new InputController(new InputController.Config(){{
            this.name = "Plants Growth Per Day";
            this.width = animalSettingsWidth;
            this.height = entitiesSettingsHeight/2;
        }});

        this.plantGrowthVariantSelector = new ComboBoxController(animalSettingsWidth, boxHeight, GrowthPlantVariant.getValues(), "Growth Variants");

        plantEnergyRestorationInput.render();
        plantFirstPopulationInput.render();
        plantGrowthInput.render();
        plantGrowthVariantSelector.render();

        plantsInputsUpper.getChildren().addAll(plantEnergyRestorationInput.getBox(), plantFirstPopulationInput.getBox());
        plantsInputsLower.getChildren().addAll(plantGrowthInput.getBox(), plantGrowthVariantSelector.getBox());

        this.plantsSettings.getChildren().addAll(plantsInputsUpper, plantsInputsLower);

//        Misc settings
        this.miscSettings.setMinWidth(entitiesWidth);
        this.miscSettings.setMinHeight(entitiesSettingsHeight);

        int widthMisc = (int)(entitiesWidth/2) + 1;

        this.buttonsSettings.setMinWidth(widthMisc);
        this.buttonsSettings.setMinHeight(entitiesSettingsHeight);
        this.buttonsSettings.setAlignment(Pos.BOTTOM_CENTER);
        this.buttonsSettings.setPadding(new Insets(this.padding));

        HBox buttons = new HBox(this.padding);

        buttons.setMinWidth(widthMisc);
        buttons.setMinHeight(entitiesSettingsHeight);
        buttons.setAlignment(Pos.BOTTOM_CENTER);

        this.launchMapButton.setMinWidth(boxWidth);
        this.launchMapButton.setMinHeight(boxHeight);
        this.launchMapButton.setOnAction(event -> launchMap());

        this.ExitButton.setMinWidth(boxWidth);
        this.ExitButton.setMinHeight(boxHeight);
        this.ExitButton.setOnAction(event -> System.exit(0));

        buttons.getChildren().addAll(this.launchMapButton, this.ExitButton);
        this.buttonsSettings.getChildren().addAll(buttons);


        this.mutationSettings.setMinWidth(widthMisc);
        this.mutationSettings.setMinHeight(entitiesSettingsHeight);

        this.mutationVariantSelector = new ComboBoxController(widthMisc, boxHeight, MutationVariant.getValues(), "Mutation Variants");

        HBox mutationMinMax = new HBox();
        mutationMinMax.setMinWidth(widthMisc);
        mutationMinMax.setPrefHeight(entitiesSettingsHeight - boxHeight);

        this.mutationMinInput = new InputController(new InputController.Config(){{
            this.name = "Mutation Min";
            this.width = widthMisc/2 - this.padding;
            this.height = boxHeight;
            this.min = 0;
            this.max = 16;
        }});

        this.mutationMaxInput = new InputController(new InputController.Config(){{
            this.name = "Mutation Max";
            this.width = widthMisc/2 - 10;
            this.height = boxHeight;
            this.min = 1;
            this.max = 24;
        }});

        mutationVariantSelector.render();
        mutationMinInput.render();
        mutationMaxInput.render();

        mutationMinMax.getChildren().addAll(mutationMinInput.getBox(), mutationMaxInput.getBox());
        mutationMinMax.setAlignment(Pos.CENTER);

        this.mutationSettings.getChildren().addAll(mutationVariantSelector.getBox(), mutationMinMax);

        this.miscSettings.getChildren().addAll(this.mutationSettings, this.buttonsSettings );

//        Misc settings

        this.entitiesPlaceSettings.getChildren().addAll(this.animalsSettings, this.plantsSettings, this.miscSettings);
        this.entitiesPlace.getChildren().addAll(this.entitiesPlaceSettings);

//       Entities

        this.parametersPlace.setMinWidth(this.width);
        this.parametersPlace.setMinHeight(this.height - this.boxHeight);
        this.parametersPlace.setStyle("-fx-background-color: #AAA;");
        this.parametersPlace.getChildren().addAll(this.mapSettingsPlace, this.entitiesPlace);

        this.main.setPrefWidth(this.width);
        this.main.setPrefHeight(this.height);
        this.main.getChildren().addAll(title, this.parametersPlace);

        this.scene = new Scene(main, this.width, this.height);

        this.menuStage.setTitle(this.name);
        this.menuStage.setScene(this.scene);
        this.menuStage.setResizable(false);

        this.menuStage.show();




    }

    private void launchMap(){
        this.launchMap.accept(new Map(new Map.ExtendedConfig(){{
            this.name = "Map";
            this.width = mapWidthInput.getValue();
            this.height = mapHeightInput.getValue();
            this.borderWidth = 1.5;
            this.boxHeight = 50;
            this.boxWidth = 50;
            this.energyToReproduce = parentEnergyReproductionConsumeInput.getValue();
            this.energyNeededToReproduce = parentEnergyNeedToReproductionInput.getValue();
            this.genomeLength = genomeLengthInput.getValue();
            this.startEnergy = startEnergyInput.getValue();
            this.animalFirstPopulation = animalFirstPopulationInput.getValue();
            this.plantFirstPopulation = plantFirstPopulationInput.getValue();
            this.plantEnergyRestoration = plantEnergyRestorationInput.getValue();
            this.plantGrowthPerDay = plantGrowthInput.getValue();
            this.minimalNumberOfMutations = mutationMinInput.getValue();
            this.maximalNumberOfMutations = mutationMaxInput.getValue();
            this.mapVariant = MapVariants.fromString(mapVariantSelector.getSelectedValue());
            this.animalBehaviorVariant = AnimalBehaviorVariant.fromString(animalBehaviorVariantSelector.getSelectedValue());
            this.mutationVariant = MutationVariant.fromString(mutationVariantSelector.getSelectedValue());
            this.growthPlantVariant = GrowthPlantVariant.fromString(plantGrowthVariantSelector.getSelectedValue());
        }}, new Earth(this.width, this.height)));
    }

}
