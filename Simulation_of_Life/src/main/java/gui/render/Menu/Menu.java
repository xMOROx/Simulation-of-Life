package Gui.Render.Menu;

import Gui.Render.IsRenderable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import settings.variants.AnimalBehaviorVariant;
import settings.variants.GrowthPlantVariant;
import settings.variants.MapVariants;
import settings.variants.MutationVariant;


public class Menu implements IsRenderable {
    //TODO implement recieving data from settings
    //TODO implement saving data to settings
    protected String name;
    protected int width;
    protected int height;
    protected int boxWidth;
    protected int boxHeight;
    protected int textSize;
    protected int padding;
    protected Scene scene;
    protected Button launchMapButton;
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
    protected Stage mapStage;
    protected String color = "#ccc";
    public Menu(Config config) {
        this.name = config.name;
        this.width = config.width;
        this.height = config.height;
        this.boxWidth = config.boxWidth;
        this.boxHeight = config.boxHeight;
        this.textSize = config.textSize;
        this.padding  = config.padding;

        this.mapStage = new Stage();

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

    public static class Config {
        public String name = "Menu";
        public int width = 600;
        public int height = 300;
        public int boxWidth = 150;
        public int boxHeight = 50;
        public int textSize = 24;

        public int padding = 10;
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
        ComboBoxController mapVariant = new ComboBoxController(mapWidth, boxHeight, MapVariants.getValues(), "Map Variants");

        InputController mapWidthInput = new InputController(new InputController.Config(){{
            this.name = "Map Width";
            this.width = mapWidth - 20;
            this.height = boxHeight;
        }});


        InputController mapHeightInput = new InputController(new InputController.Config(){{
            this.name = "Map Height";
            this.width = mapWidth - 2*this.padding;
            this.height = boxHeight;
        }});

        InputController genomeLengthInput = new InputController(new InputController.Config(){{
            this.name = "Genome Length";
            this.width = mapWidth - 2*this.padding;
            this.height = boxHeight;
            this.min = 8;
            this.max = 32;
        }});

        mapWidthInput.render();
        mapHeightInput.render();
        genomeLengthInput.render();
        mapVariant.render();

        this.mapSettingsPlace.getChildren().addAll(mapVariant.getBox(), mapWidthInput.getBox(), mapHeightInput.getBox(), genomeLengthInput.getBox());
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


        InputController parentEnergyReproductionConsumeInput = new InputController(new InputController.Config(){{
            this.name = "Energy Reproduction Consume";
            this.width = animalSettingsWidth;
            this.height = entitiesSettingsHeight/2;
        }});

        parentEnergyReproductionConsumeInput.render();

        InputController parentEnergyNeedToReproductionInput = new InputController(new InputController.Config(){{
            this.name = "Energy Need To Reproduction";
            this.width = animalSettingsWidth;
            this.height = entitiesSettingsHeight/2;
        }});

        parentEnergyNeedToReproductionInput.render();

        parentSettings.getChildren().addAll( parentEnergyReproductionConsumeInput.getBox(), parentEnergyNeedToReproductionInput.getBox());

        VBox childSettings = new VBox();
        childSettings.setMinWidth(animalSettingsWidth);
        childSettings.setMinHeight(entitiesSettingsHeight);

        ComboBoxController animalBehaviorVariant = new ComboBoxController(animalSettingsWidth, boxHeight, AnimalBehaviorVariant.getValues(), "Behavior Variants");

        InputController startEnergyInput = new InputController(new InputController.Config(){{
            this.name = "Start Energy";
            this.width = animalSettingsWidth/2 - this.padding;
            this.height = boxHeight;
        }});

        InputController firstPopulationNumber = new InputController(new InputController.Config(){{
            this.name = "Animals First Population";
            this.width = animalSettingsWidth/2 - this.padding;
            this.height = boxHeight;
        }});

        HBox animalInputs = new HBox();
        animalInputs.setMinWidth(animalSettingsWidth);
        animalInputs.setMinHeight(boxHeight);
        animalInputs.getChildren().addAll(startEnergyInput.getBox(), firstPopulationNumber.getBox());

        animalBehaviorVariant.render();
        startEnergyInput.render();
        firstPopulationNumber.render();

        childSettings.getChildren().addAll( animalBehaviorVariant.getBox(), animalInputs);

        this.animalsSettings.getChildren().addAll(parentSettings, childSettings);

        this.plantsSettings.setMinWidth(entitiesWidth);
        this.plantsSettings.setMinHeight(entitiesSettingsHeight);

        HBox plantsInputsUpper = new HBox();
        plantsInputsUpper.setMinWidth(entitiesWidth);
        plantsInputsUpper.setMinHeight(entitiesSettingsHeight/2);
        HBox plantsInputsLower = new HBox();
        plantsInputsLower.setMinWidth(entitiesWidth);
        plantsInputsLower.setMinHeight(entitiesSettingsHeight/2);

        InputController plantEnergyInput = new InputController(new InputController.Config(){{
            this.name = "Plant Energy Restoration";
            this.width = animalSettingsWidth;
            this.height = entitiesSettingsHeight/2;
        }});

        InputController plantNumberInput = new InputController(new InputController.Config(){{
            this.name = "Plants First Population";
            this.width = animalSettingsWidth;
            this.height = entitiesSettingsHeight/2;
        }});

        InputController plantGrowthInput = new InputController(new InputController.Config(){{
            this.name = "Plants Growth Per Day";
            this.width = animalSettingsWidth;
            this.height = entitiesSettingsHeight/2;
        }});

        ComboBoxController plantGrowthVariant = new ComboBoxController(animalSettingsWidth, boxHeight, GrowthPlantVariant.getValues(), "Growth Variants");

        plantEnergyInput.render();
        plantNumberInput.render();
        plantGrowthInput.render();
        plantGrowthVariant.render();

        plantsInputsUpper.getChildren().addAll(plantEnergyInput.getBox(), plantNumberInput.getBox());
        plantsInputsLower.getChildren().addAll(plantGrowthInput.getBox(), plantGrowthVariant.getBox());

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

        this.ExitButton.setMinWidth(boxWidth);
        this.ExitButton.setMinHeight(boxHeight);


        buttons.getChildren().addAll(this.launchMapButton, this.ExitButton);
        this.buttonsSettings.getChildren().addAll(buttons);


        this.mutationSettings.setMinWidth(widthMisc);
        this.mutationSettings.setMinHeight(entitiesSettingsHeight);

        ComboBoxController mutationVariant = new ComboBoxController(widthMisc, boxHeight, MutationVariant.getValues(), "Mutation Variants");

        HBox mutationMinMax = new HBox();
        mutationMinMax.setMinWidth(widthMisc);
        mutationMinMax.setPrefHeight(entitiesSettingsHeight - boxHeight);

        InputController mutationMinInput = new InputController(new InputController.Config(){{
            this.name = "Mutation Min";
            this.width = widthMisc/2 - this.padding;
            this.height = boxHeight;
            this.min = 0;
            this.max = 16;
        }});

        InputController mutationMaxInput = new InputController(new InputController.Config(){{
            this.name = "Mutation Max";
            this.width = widthMisc/2 - 10;
            this.height = boxHeight;
            this.min = 1;
            this.max = 24;
        }});

        mutationVariant.render();
        mutationMinInput.render();
        mutationMaxInput.render();

        mutationMinMax.getChildren().addAll(mutationMinInput.getBox(), mutationMaxInput.getBox());
        mutationMinMax.setAlignment(Pos.CENTER);

        this.mutationSettings.getChildren().addAll(mutationVariant.getBox(), mutationMinMax);

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




        this.mapStage.setTitle(this.name);
        this.mapStage.setScene(this.scene);
        this.mapStage.setResizable(false);

        this.mapStage.show();
    }

}
