package gui.render;

import Entities.Animal;
import Settings.CsvWriter;
import World.SimulationEngine;
import gui.interfaces.IGuiObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SimulationScene implements IGuiObserver {


    @FXML
    private final XYChart.Series populationData = new XYChart.Series();
    @FXML
    private final XYChart.Series grassData = new XYChart.Series();
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
    public Label displayPathLabel;
    @FXML
    public ScrollPane theMostPopularGenomeScrollPane;
    @FXML
    public Button saveCSVButton;
    @FXML
    public Label theMostPopularGenomeLabelOutPut;
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
    @FXML
    public Label selectedAnimalNameLabel;
    @FXML
    public ImageView selectedAnimalPictureView;
    //------------------------------------------------------
    CsvWriter csvWriter = null;
    String path = null;
    List<String[]> data = new ArrayList<>();
    //FXML---------------------------------------------------
    @FXML
    private PrimaryScene primaryScene;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button deleteSimulationButton;
    @FXML
    private Pane simulationPane;
    @FXML
    private GridPane mapGridPane;
    //------------------------------------------------------
    @FXML
    private HBox MainHBox;
    @FXML
    private LineChart<?, ?> populationChart;
    @FXML
    private LineChart<?, ?> plantsChart;
    //simulation--------------------------------------------
    private SimulationEngine engine;
    private MapVisualizer mapVisualizer;
    private Thread thread;
    //------------------------------------------------------
    private boolean firstStart = true;
    private int refreshRate = 200;
    private boolean isSaveToCSV = false;
    private boolean selectedAnimalDeath = false;

    public void onDeleteSimulationButtonClicked() throws IOException {
        if (this.csvWriter != null) {
            this.csvWriter.close();
        }
        this.primaryScene.removeTab(this);
    }

    public void setMainSceneController(PrimaryScene primaryScene) {
        this.primaryScene = primaryScene;
    }

    public void onStartButtonClicked() {
        this.startSimulation();
        this.deleteSimulationButton.setDisable(true);
        this.startButton.setDisable(true);
        this.stopButton.setDisable(false);
        if (this.isSaveToCSV) {
            this.saveCSVButton.setDisable(true);
        }
        this.updateGuiCharts();
        this.engine.getWorld().noticeAnimals(false);
    }

    public void onStopButtonClicked() throws InterruptedException {
        this.deleteSimulationButton.setDisable(false);
        this.startButton.setDisable(false);
        this.stopButton.setDisable(true);
        if (this.isSaveToCSV) {
            this.saveCSVButton.setDisable(false);
        }
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
        this.animalNumberOfEatenGrassesLabel.setText("The number of plants eaten: " + (selectedAnimal.getState().getEatenGrass()));
        this.animalNumberOfChildrenLabel.setText("Number of children: " + (selectedAnimal.getState().getChildren()));
        this.animalDaysOfLifeLabel.setText("Life expectancy: " + (selectedAnimal.getState().getAge()));
        if (selectedAnimal.isDead() && !this.selectedAnimalDeath) {
            this.selectedAnimalDeath = true;
            this.animalDayOfDeathLabel.setText("Day of death: " + (this.engine.getWorld().getStatistics().day));
        } else if (!selectedAnimal.isDead()) {
            this.animalDayOfDeathLabel.setText("Day of death: " + "Alive");
        }
        this.selectedAnimalNameLabel.setText("Animal name: " + (selectedAnimal.getName()));
        this.selectedAnimalPictureView.setImage(selectedAnimal.getAnimalImage());
    }

    private void startSimulation() {
        if (this.firstStart) {
            if (this.isSaveToCSV) {
                try {
                    LocalDate date = LocalDate.now();
                    this.path = Objects.requireNonNull(getClass().getResource("")).getPath() + "/statistics_" + date.getYear() + "_" + date.getMonth() + "_" + date.getDayOfMonth() + "_" + hashCode() + ".csv";
                    this.csvWriter = new CsvWriter(this.path);
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

    private void pauseSimulation() {
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

        this.theMostPopularGenomeScrollPane.setFitToWidth(true);

        this.theMostPopularGenomeLabelOutPut.setText(engine.getWorld().getStatistics().theMostPopularGenes.toString());

        this.theMostPopularGenomeLabelOutPut.setWrapText(true);
        this.theMostPopularGenomeLabelOutPut.setPrefWidth(this.theMostPopularGenomeScrollPane.getWidth());
        this.theMostPopularGenomeLabelOutPut.setPrefHeight(this.theMostPopularGenomeScrollPane.getHeight());
        this.theMostPopularGenomeScrollPane.setContent(this.theMostPopularGenomeLabelOutPut);

        this.averageEnergyLevelLabel.setText("Average energy level: " + Math.round(this.engine.getWorld().getAvgStats().avgEnergy * 100.0) / 100.0);
        this.lifeExpectancyLabel.setText("Life expectancy: " + Math.round(this.engine.getWorld().getAvgStats().avgLifespan * 100.0) / 100.0);
        this.dayLabel.setText("Day: " + this.engine.getWorld().getStatistics().day);
        if (this.isSaveToCSV) {
            String[] dataToSave = {this.engine.getWorld().getStatistics().day.toString(), Integer.toString(this.engine.getWorld().getStatistics().animalCount), Integer.toString(this.engine.getWorld().getStatistics().grassCount), Integer.toString(this.engine.getWorld().getStatistics().emptyFieldsCount), Integer.toString(this.engine.getWorld().getStatistics().occupiedFieldsCount), Double.toString(this.engine.getWorld().getAvgStats().avgEnergy), Double.toString(this.engine.getWorld().getAvgStats().avgLifespan), this.engine.getWorld().getStatistics().theMostPopularGenes.toString()};
            this.data.add(dataToSave);
        }
    }

    public void updateGuiMap() {
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
        this.populationData.getData().add(new XYChart.Data<>(Integer.toString(day), this.engine.getWorld().getStatistics().animalCount));
        this.grassData.getData().add(new XYChart.Data<>(Integer.toString(day), this.engine.getWorld().getStatistics().grassCount));
    }

    public void saveCSV() {
        try {
            this.csvWriter.writeData(this.data);
            this.data.clear();
            this.displayPathLabel.setText(this.path);
            this.displayPathLabel.setWrapText(true);
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }
}
