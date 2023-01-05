package gui.render;

import World.Maps.Settings.Parameters;
import World.Maps.Settings.Variants.AnimalBehaviorVariant;
import World.Maps.Settings.Variants.GrowthPlantVariant;
import World.Maps.Settings.Variants.MapVariants;
import World.Maps.Settings.Variants.MutationVariant;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static World.Maps.Settings.Constants.*;

public class NewSimulationController implements Initializable {

    //controllers---------------------------------------------
    private MainSceneController mainSceneController;

    //--------------------------------------------------------
//    private Configuration configuration;
    private Stage stage;

    //choice boxes----------------------------------------------
    @FXML
    private ChoiceBox<String> mapVariantChoiceBox;
    @FXML
    private ChoiceBox<String> mutationVariantChoiceBox;
    @FXML
    private ChoiceBox<String> behaviourVariantChoiceBox;
    @FXML
    private ChoiceBox<String> growingVariantChoiceBox;
    //-----------------------------------------------------------


    //labels-----------------------------------------------------
    @FXML
    private Label mapVariantLabel;
    @FXML
    private Label mapHeightLabel;
    @FXML
    private Label mapWidthLabel;
    @FXML
    private Label plantsEnergyProfitLabel;
    @FXML
    private Label minEnergyToCopulationLabel;
    @FXML
    private Label animalStartEnergyLabel;
    @FXML
    private Label dailyEnergyCostLabel;
    @FXML
    private Label energyUsedToCopulationLabel;
    @FXML
    private Label mutationVariantLabel;
    @FXML
    private Label behaviourVariantLabel;
    @FXML
    private Label genomeLengthLabel;
    @FXML
    private Label animalsStartSpawningLabel;
    @FXML
    private Label plantsStartSpawningLabel;
    @FXML
    private Label plantsEachDaySpawningLabel;
    @FXML
    private Label growingVariantLabel;
    @FXML
    private Label refreshTimeLabel;
    @FXML
    private Label errorMessagesLabel;
    //-----------------------------------------------------------

    //Text fields------------------------------------------------
    @FXML
    private TextField mapHeightTextField;

    @FXML
    private TextField mapWidthTextField;

    @FXML
    private TextField plantsEnergyProfitTextField;
    @FXML
    private TextField minEnergyToCopulationTextField;
    @FXML
    private TextField animalStartEnergyTextField;
    @FXML
    private TextField dailyEnergyCostTextField;
    @FXML
    private TextField energyUsedToCopulationTextField;
    @FXML
    private TextField genomeLengthTextField;
    @FXML
    private TextField animalStartSpawningTextField;
    @FXML
    private TextField plantStartSpawningTextField;
    @FXML
    private TextField plantEachDaySpawningTextField;
    @FXML
    private TextField refreshTimeTextField;
    @FXML
    //-----------------------------------------------------------

    //check boxes------------------------------------------------
    private CheckBox saveCSVCheckBox;
    @FXML
    private CheckBox removeExcessAnimalsCheckBox;

    //-----------------------------------------------------------

    //Variants---------------------------------------------------
    AnimalBehaviorVariant animalBehaviorVariant_;
    GrowthPlantVariant growthPlantVariant_;
    MapVariants mapVariants_;
    MutationVariant mutationVariant_;

    //-----------------------------------------------------------

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapVariantChoiceBox.getItems().addAll(MapVariants.getValues());
        mapVariantChoiceBox.setValue(MapVariants.EARTH.toString());
        mutationVariantChoiceBox.getItems().addAll(MutationVariant.getValues());
        mutationVariantChoiceBox.setValue(MutationVariant.FULL_RANDOM.toString());
        behaviourVariantChoiceBox.getItems().addAll(AnimalBehaviorVariant.getValues());
        behaviourVariantChoiceBox.setValue(AnimalBehaviorVariant.FULL_PREDICTABLE.toString());
        growingVariantChoiceBox.getItems().addAll(GrowthPlantVariant.getValues());
        growingVariantChoiceBox.setValue(GrowthPlantVariant.EQUATOR.toString());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setControllers(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    @FXML
    protected void onCancelButtonClicked() {
        stage.close();
    }

    @FXML
    protected void onCreateNewSimulationButtonClicked() throws IOException {
        if (validateAllUserArguments()) {
            getConfigurationFromUserArguments();
            stage.close();
            this.mainSceneController.createNewSimulation(new Parameters( new Parameters.ApplicationConfig() {{
                this.mapVariant = mapVariants_;
                this.mutationVariant = mutationVariant_;
                this.behaviourVariant = animalBehaviorVariant_;
                this.terrainVariant = growthPlantVariant_;

                this.mapHeight = Integer.parseInt(mapHeightTextField.getText());
                this.mapWidth = Integer.parseInt(mapWidthTextField.getText());

                this.plantsEnergy = Integer.parseInt(plantsEnergyProfitTextField.getText());
                this.energyRequiredForReproduction = Integer.parseInt(minEnergyToCopulationTextField.getText());
                this.energyUsedForReproduction = Integer.parseInt(energyUsedToCopulationTextField.getText());
                this.dailyEnergyConsumption = Integer.parseInt(dailyEnergyCostTextField.getText());
                this.initialAnimalsEnergy = Integer.parseInt(animalStartEnergyTextField.getText());
                this.genomeLength = Integer.parseInt(genomeLengthTextField.getText());
                this.initialAnimalsNumber = Integer.parseInt(animalStartSpawningTextField.getText());
                this.initialPlantsNumber = Integer.parseInt(plantStartSpawningTextField.getText());
                this.numberOfPlantGrowingDaily = Integer.parseInt(plantEachDaySpawningTextField.getText());

                this.saveToCSV = saveCSVCheckBox.isSelected();
                this.removeExcessAnimals = removeExcessAnimalsCheckBox.isSelected();
            }}));
        }
    }

    private boolean validateAllUserArguments() {
        return validateArgument(mapHeightLabel, mapHeightTextField, MIN_MAP_HEIGHT, MAX_MAP_HEIGHT)
                && validateArgument(mapWidthLabel, mapWidthTextField, MIN_MAP_WIDTH, MAX_MAP_WIDTH)
                && validateArgument(plantsEnergyProfitLabel, plantsEnergyProfitTextField, MIN_PLANTS_ENERGY_PROFIT ,MAX_PLANTS_ENERGY_PROFIT)
                && validateArgument(minEnergyToCopulationLabel, minEnergyToCopulationTextField, MIN_MINIMUM_ENERGY_TO_COPULATION, MAX_MINIMUM_ENERGY_TO_COPULATION)
                && validateArgument(animalStartEnergyLabel, animalStartEnergyTextField, MIN_ANIMAL_START_ENERGY, MAX_ANIMAL_START_ENERGY)
                && validateArgument(dailyEnergyCostLabel, dailyEnergyCostTextField, MIN_DAILY_ENERGY_COST, MAX_DAILY_ENERGY_COST)
                && validateArgument(minEnergyToCopulationLabel, minEnergyToCopulationTextField, MIN_ENERGY_USED_TO_COPULATION, MAX_ENERGY_USED_TO_COPULATION)
                && validateArgument(genomeLengthLabel, genomeLengthTextField, MIN_GENOME_LENGTH, MAX_GENOME_LENGTH)
                && validateArgument(animalsStartSpawningLabel, animalStartSpawningTextField, MIN_ANIMALS_SPAWNING_AT_START, MAX_ANIMALS_SPAWNING_AT_START)
                && validateArgument(plantsStartSpawningLabel, plantStartSpawningTextField, MIN_PLANTS_SPAWNING_AT_THE_START, MAX_PLANTS_SPAWNING_AT_THE_START)
                && validateArgument(plantsEachDaySpawningLabel, plantEachDaySpawningTextField, MIN_PLANTS_SPAWNED_AT_EACH_DAY, MAX_PLANTS_SPAWNED_AT_EACH_DAY)
                && validateArgument(refreshTimeLabel, refreshTimeTextField, MIN_REFRESH_TIME, MAX_REFRESH_TIME)
                && validateTwoArguments(energyUsedToCopulationLabel, energyUsedToCopulationTextField, minEnergyToCopulationLabel, minEnergyToCopulationTextField);
    }

    private boolean validateArgument(Label argumentLabel, TextField argumentTextField, int minValue, int maxValue) {
        String stringUserArgument = argumentTextField.getText();
        if (stringUserArgument.length() == 0) {
            argumentLabel.setTextFill(Color.RED);
            errorMessagesLabel.setText("ERROR: The " + argumentLabel.getText() + " value cannot be a empty!");
            return false;
        }
//        if (!Static.isStringInt(stringUserArgument)) {
//            argumentLabel.setTextFill(Color.RED);
//            errorMessagesLabel.setText("ERROR: The " + argumentLabel.getText() + " value must be an integer!");
//            return false;
//        }

        int intUserArgument = Integer.parseInt(stringUserArgument);
        if (intUserArgument < minValue || intUserArgument > maxValue) {
            argumentLabel.setTextFill(Color.RED);
            errorMessagesLabel.setText("ERROR: The " + argumentLabel.getText() + " value exceeded!\n" +
                    "It must be a integer between " + minValue +  " and " + maxValue);
            return false;
        }
        argumentLabel.setTextFill(Color.BLACK);
        return true;
    }

    private boolean validateTwoArguments(Label argumentLabel1, TextField argumentTextField1, Label argumentLabel2, TextField argumentTextField2) {
        int intUserArgument1 = Integer.parseInt(argumentTextField1.getText());
        int intUserArgument2 = Integer.parseInt(argumentTextField2.getText());
        if (intUserArgument1 > intUserArgument2) {
            argumentLabel1.setTextFill(Color.RED);
            argumentLabel1.setTextFill(Color.RED);
            errorMessagesLabel.setText("ERROR: The argument " + argumentLabel1.getText() + "\n cannot be greater than the argument " + argumentLabel2.getText());
            return false;
        }
        return true;
    }

    private void getConfigurationFromUserArguments() {
        this.animalBehaviorVariant_ = String.valueOf(behaviourVariantChoiceBox.getValue()).equals(AnimalBehaviorVariant.FULL_PREDICTABLE.toString()) ? AnimalBehaviorVariant.FULL_PREDICTABLE : AnimalBehaviorVariant.CRAZY_MOVES;
        this.mapVariants_ = String.valueOf(mapVariantChoiceBox.getValue()).equals(MapVariants.EARTH.toString()) ? MapVariants.EARTH : MapVariants.HELL;
        this.mutationVariant_ = String.valueOf(mutationVariantChoiceBox.getValue()).equals(MutationVariant.FULL_RANDOM.toString()) ? MutationVariant.FULL_RANDOM : MutationVariant.SLIGHT_CORRECTION;
        this.growthPlantVariant_ = String.valueOf(growingVariantChoiceBox.getValue()).equals(GrowthPlantVariant.EQUATOR.toString()) ? GrowthPlantVariant.EQUATOR : GrowthPlantVariant.TOXIC_CORPSES;
    }
}
