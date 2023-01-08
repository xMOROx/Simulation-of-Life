package gui.render;

import Entities.Animal;
import Entities.Grass;
import Settings.CsvWriter;
import World.SimulationEngine;
import gui.interfaces.IGuiObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class SimulationSceneController implements IGuiObserver {



    //FXML---------------------------------------------------
    @FXML
    private MainSceneController mainSceneController;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button deleteSimulationButton;
    @FXML
    private Pane simulationPane;
    @FXML
    private ChoiceBox liveAnimalsChoiceBox;
    @FXML
    private ChoiceBox deadAnimalsChoiceBox;
    @FXML
    private GridPane mapGridPane;
    @FXML
    private HBox MainHBox;
    @FXML
    private LineChart<?, ?> populationChart;
    @FXML
    private LineChart<?, ?> plantsChart;
    @FXML
    public Label numberOfAllPlantsLabel;
    @FXML
    public Label numberOfAllAnimalsLabel;
    @FXML
    public Label numberOfFreeFieldLabel;
    @FXML
    public Label numberOfOccupiedFieldLabel1;
    @FXML
    public Label theMostPopularGenomeLabel;
    @FXML
    public Label averageEnergyLevelLabel;
    @FXML
    public Label lifeExpectancyLabel;
    @FXML
    public Label dayLabel;
    @FXML
    public ScrollPane theMostPopularGenomeScrollPane;
    @FXML
    public Label theMostPopularGenomeLabelOutPut;
    @FXML
    private final XYChart.Series populationData = new XYChart.Series();
    @FXML
    private final XYChart.Series grassData = new XYChart.Series();
    //------------------------------------------------------

    //selected animal--------------------------------------
    @FXML
    public Label animalGenomeLabel;
    @FXML
    public Label animalActiveGeneLabel;
    @FXML
    public Label animalEnergyLabel;
    @FXML
    public Label animalNumberOfEatenGrassesLabel;
    @FXML
    public Label animalNumberOfChildrenLabel;
    @FXML
    public Label animalDaysOfLifeLabel;
    @FXML
    public Label animalDayOfDeathLabel;
    //------------------------------------------------------


    //simulation--------------------------------------------
    private SimulationEngine engine;
    private MapVisualizer mapVisualizer;
    private Thread thread;

    //------------------------------------------------------
    private boolean firstStart = true;
    private int refreshRate = 200;
    private boolean isSaveToCSV = false;
    CsvWriter csvWriter = null;
    List<String[]> data = new ArrayList<>();



    public void onDeleteSimulationButtonClicked() throws IOException {
        csvWriter.writeData(data);
        this.mainSceneController.removeTab(this);
    }

    public void setMainSceneController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    public void onStartButtonClicked()  {
        this.startSimulation();
        this.deleteSimulationButton.setDisable(true);
        this.startButton.setDisable(true);
        this.stopButton.setDisable(false);
        this.liveAnimalsChoiceBox.setDisable(true);
        this.deadAnimalsChoiceBox.setDisable(true);
        this.updateGuiCharts();
        this.engine.getWorld().noticeAnimals(false);
    }

    public void onStopButtonClicked() throws InterruptedException {
        this.deleteSimulationButton.setDisable(false);
        this.startButton.setDisable(false);
        this.stopButton.setDisable(true);
        this.liveAnimalsChoiceBox.setDisable(false);
        this.deadAnimalsChoiceBox.setDisable(false);
        this.engine.getWorld().noticeAnimals(true);
        this.updateGuiMap();
        this.pauseSimulation();
    }

    private void setSelectedAnimalStatistics() {
        Animal selectedAnimal = this.engine.getWorld().getSelectedAnimal();
        if (selectedAnimal == null) return;
        this.animalGenomeLabel.setText("animal's genome: " + selectedAnimal.getGenome().getGenes().toString());
        this.animalActiveGeneLabel.setText("active gene: " + (selectedAnimal.getGenome().getGene(selectedAnimal.getCurrentGeneIndex() % selectedAnimal.genomeLength)));
        this.animalEnergyLabel.setText("animal energy: " + (selectedAnimal.getEnergy()));
        this.animalNumberOfEatenGrassesLabel.setText("The number of plants eaten: " +(selectedAnimal.getState().getEatenGrass()));
        this.animalNumberOfChildrenLabel.setText("Number of children: " + (selectedAnimal.getState().getChildren()));
        this.animalDaysOfLifeLabel.setText("Life expectancy: " + (selectedAnimal.getState().getAge()));
        this.animalDayOfDeathLabel.setText("Day of death: " + (selectedAnimal.getState().getDayOfDeath()));
    }

    private void startSimulation()  {
        if (this.firstStart) {
            if(this.isSaveToCSV){
                try {
                    LocalDate date = LocalDate.now();
                    this.csvWriter = new CsvWriter(Objects.requireNonNull(getClass().getResource("")).getPath() + "/statistics_"+ date.getYear() + "_" + date.getMonth()+"_"+ date.getDayOfMonth() + ".csv");

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            this.thread.start();
            this.firstStart = false;
        } else {
            this.thread.resume();
        }
    }

    private void pauseSimulation(){
        this.thread.suspend();
    }

    public void setRefreshRate(int refreshRate) {
        this.refreshRate = refreshRate;
    }

    public void setIsSaveToCSV(boolean isSaveToCSV) {
        this.isSaveToCSV = isSaveToCSV;
    }

    public void setWorld(SimulationEngine engine) {
        this.engine = engine;
        this.engine.registerGuiObserver(this);
        this.thread = new Thread(this.engine);
        this.mapVisualizer = new MapVisualizer(this.engine.getWorld(), this.mapGridPane);
        this.mapGridPane.setAlignment(Pos.CENTER);

        this.populationData.setName("Day");
        this.grassData.setName("Day");

        this.populationChart.getData().add(this.populationData);
        this.plantsChart.getData().add(this.grassData);


        this.updateGuiMap();
        this.engine.getWorld().UpdateStatistics();
    }


    private void updateGeneralStatistics() {
        this.numberOfAllAnimalsLabel.setText("Number of all animals: " + this.engine.getWorld().getStatistics().animalCount);
        this.numberOfAllPlantsLabel.setText("Number of all plants: " + this.engine.getWorld().getStatistics().grassCount);
        this.numberOfFreeFieldLabel.setText("Number of free field: " + this.engine.getWorld().getStatistics().emptyFieldsCount);
        this.numberOfOccupiedFieldLabel1.setText("Number of occupied field: " + this.engine.getWorld().getStatistics().occupiedFieldsCount);
        this.theMostPopularGenomeLabelOutPut.setText(engine.getWorld().getStatistics().theMostPopularGenes.toString());
        this.averageEnergyLevelLabel.setText("Average energy level: " + Math.round(this.engine.getWorld().getAvgStats().avgEnergy * 100.0) / 100.0);
        this.lifeExpectancyLabel.setText("Life expectancy: " + Math.round(this.engine.getWorld().getAvgStats().avgLifespan * 100.0) / 100.0);
        this.dayLabel.setText("Day: " + this.engine.getWorld().getStatistics().day);
        if(this.isSaveToCSV) {
            System.out.println(Objects.requireNonNull(getClass().getResource("")).getPath());
            String[] dataToSave = {this.engine.getWorld().getStatistics().day.toString(), Integer.toString(this.engine.getWorld().getStatistics().animalCount), Integer.toString(this.engine.getWorld().getStatistics().grassCount), Integer.toString(this.engine.getWorld().getStatistics().emptyFieldsCount), Integer.toString(this.engine.getWorld().getStatistics().occupiedFieldsCount),Double.toString(this.engine.getWorld().getAvgStats().avgEnergy), Double.toString(this.engine.getWorld().getAvgStats().avgLifespan) ,this.engine.getWorld().getStatistics().theMostPopularGenes.toString()};
            this.data.add(dataToSave);
        }
    }

    public void updateGuiMap()  {
        try {
            Platform.runLater(this::setSelectedAnimalStatistics);
            Platform.runLater(this::updateMap);
            Platform.runLater(this::updateGeneralStatistics);
            Thread.sleep(this.refreshRate);
        } catch (InterruptedException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    public void updateGuiCharts() {
        Platform.runLater(this::updateCharts);
    }
    public void updateMap() {
        this.mapVisualizer.visualizeMap();
    }


    private void updateCharts() {
        Integer day = this.engine.getWorld().getStatistics().day;
        populationData.getData().add(new XYChart.Data<>(Integer.toString(day), this.engine.getWorld().getStatistics().animalCount));
        grassData.getData().add(new XYChart.Data<>(Integer.toString(day), this.engine.getWorld().getStatistics().grassCount));
    }
}
