package Gui.Render.Menu;

import Gui.Render.IsRenderable;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.stage.Stage;
import settings.variants.MapVariants;
import settings.variants.MutationVariant;


public class Menu implements IsRenderable {
    protected String name;
    protected int width;
    protected int height;
    protected int boxWidth;
    protected int boxHeight;
    protected int textSize;
    protected Scene scene;
    protected VBox main;
    protected VBox entitiesPlace;
    protected HBox animalsSettings;
    protected HBox plantsSettings;
    protected HBox miscSettings;
    protected VBox genomeSettings;
    protected VBox mutationSettings;
    protected VBox mapSettingsPlace;
    protected HBox parametersPlace;
    protected VBox entitiesPlaceSettings;
    protected Stage mapStage;
    public Menu(Config config) {
        this.name = config.name;
        this.width = config.width;
        this.height = config.height;
        this.boxWidth = config.boxWidth;
        this.boxHeight = config.boxHeight;
        this.textSize = config.textSize;
        this.mapStage = new Stage();
        this.main = new VBox();
        this.parametersPlace = new HBox();
        this.entitiesPlace = new VBox();
        this.mapSettingsPlace = new VBox();
        this.animalsSettings = new HBox();
        this.plantsSettings = new HBox();
        this.miscSettings = new HBox();
        this.entitiesPlaceSettings = new VBox();
        this.genomeSettings = new VBox();
        this.mutationSettings = new VBox();
    }

    public static class Config {
        public String name = "Menu";
        public int width = 600;
        public int height = 300;
        public int boxWidth = 150;
        public int boxHeight = 50;
        public int textSize = 24;
    }

    @Override
    public void render() {
        Label title = new Label("Parameters");
        title.setMinWidth(this.width);
        title.setMinHeight(this.boxHeight);
        title.setAlignment(javafx.geometry.Pos.CENTER);
        title.setStyle("-fx-font-size:"+ (this.textSize) +"px; -fx-background-color: #CCC;");


//       Map
        int mapWidth = (int)(this.width/4);
        this.mapSettingsPlace.setMinWidth(mapWidth);
        this.mapSettingsPlace.setMinHeight(this.height - this.boxHeight);
        this.mapSettingsPlace.setStyle("-fx-background-color: red;");

        ComboBoxController mapVariant = new ComboBoxController(mapWidth, boxHeight, MapVariants.getValues(), "Map Variants");
        mapVariant.render();

        InputController mapWidthInput = new InputController(new InputController.Config(){{
            this.name = "Map Width";
            this.width = mapWidth;
            this.height = boxHeight;
        }});

        mapWidthInput.render();

        InputController mapHeightInput = new InputController(new InputController.Config(){{
            this.name = "Map Height";
            this.width = mapWidth;
            this.height = boxHeight;
        }});

        mapHeightInput.render();

        this.mapSettingsPlace.getChildren().addAll(mapVariant.getBox(), mapWidthInput.getBox(), mapHeightInput.getBox());
        this.mapSettingsPlace.alignmentProperty().setValue(Pos.CENTER);
//      Map

//      Entities
        int entitiesWidth = (int)(this.width * 3/4);
        int entitiesHeight = this.height - this.boxHeight ;
        this.entitiesPlace.setMinWidth(entitiesWidth);
        this.entitiesPlace.setMinHeight(this.height - this.boxHeight);
        this.entitiesPlace.setStyle("-fx-background-color: #EEE;");

        this.entitiesPlaceSettings.setMinWidth(entitiesWidth);
        this.entitiesPlaceSettings.setMinHeight(entitiesHeight);
        this.entitiesPlaceSettings.setStyle("-fx-background-color: black;");

        int entitiesSettingsHeight = (int)(entitiesHeight/3)  + 1;
        int animalSettingsWidth = (int)(entitiesWidth/2);
        this.animalsSettings.setMinWidth(entitiesWidth);
        this.animalsSettings.setMinHeight(entitiesSettingsHeight);
        this.animalsSettings.setStyle("-fx-background-color: green;");

        VBox parentSettings = new VBox();
        parentSettings.setMinWidth(animalSettingsWidth);
        parentSettings.setMinHeight(entitiesSettingsHeight);
        parentSettings.setStyle("-fx-background-color: yellow;");

        Label parentSettingsTitle = new Label("Parent Settings");
        parentSettingsTitle.setMinWidth(animalSettingsWidth);
        parentSettingsTitle.setMinHeight(boxHeight);
        parentSettingsTitle.setAlignment(Pos.CENTER);
        parentSettingsTitle.setStyle("-fx-font-size:"+ (this.textSize) +"px; -fx-background-color: #CCC;");

        InputController parentEnergyReproductionConsumeInput = new InputController(new InputController.Config(){{
            this.name = "Energy Reproduction Consume";
            this.width = animalSettingsWidth;
            this.height = boxHeight;
        }});

        parentEnergyReproductionConsumeInput.render();

        InputController parentEnergyNeedToReproductionInput = new InputController(new InputController.Config(){{
            this.name = "Energy Need To Reproduction";
            this.width = animalSettingsWidth;
            this.height = boxHeight;
        }});

        parentEnergyNeedToReproductionInput.render();

        parentSettings.getChildren().addAll(parentSettingsTitle, parentEnergyReproductionConsumeInput.getBox(), parentEnergyNeedToReproductionInput.getBox());

        VBox childSettings = new VBox();
        childSettings.setMinWidth(animalSettingsWidth);
        childSettings.setMinHeight(entitiesSettingsHeight);
        childSettings.setStyle("-fx-background-color: purple;");


        Label childSettingsTitle = new Label("Child Settings");
        childSettingsTitle.setMinWidth(animalSettingsWidth);
        childSettingsTitle.setMinHeight(boxHeight);
        childSettingsTitle.setAlignment(Pos.CENTER);
        childSettingsTitle.setStyle("-fx-font-size:"+ (this.textSize) +"px; -fx-background-color: #CCC;");

        childSettings.getChildren().addAll(childSettingsTitle);

        this.animalsSettings.getChildren().addAll(parentSettings, childSettings);




        this.plantsSettings.setMinWidth(entitiesWidth);
        this.plantsSettings.setMinHeight(entitiesSettingsHeight);
        this.plantsSettings.setStyle("-fx-background-color: blue;");
//        Misc settings
        this.miscSettings.setMinWidth(entitiesWidth);
        this.miscSettings.setMinHeight(entitiesSettingsHeight);
        this.miscSettings.setStyle("-fx-background-color: yellow;");

        int widthMisc = (int)(entitiesWidth/2) + 1;

        this.genomeSettings.setMinWidth(widthMisc);
        this.genomeSettings.setMinHeight(entitiesSettingsHeight);
        this.genomeSettings.setStyle("-fx-background-color: pink;");

        InputController genomeLengthInput = new InputController(new InputController.Config(){{
            this.name = "Genome Length";
            this.width = widthMisc;
            this.height = boxHeight;
        }});

        genomeLengthInput.render();

        this.genomeSettings.getChildren().add(genomeLengthInput.getBox());


        this.mutationSettings.setMinWidth(widthMisc);
        this.mutationSettings.setMinHeight(entitiesSettingsHeight);
        this.mutationSettings.setStyle("-fx-background-color: purple;");

        ComboBoxController mutationVariant = new ComboBoxController(widthMisc, boxHeight, MutationVariant.getValues(), "Mutation Variants");
        mutationVariant.render();
        this.mutationSettings.getChildren().add(mutationVariant.getBox());

        this.miscSettings.getChildren().addAll(this.genomeSettings, this.mutationSettings);

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
