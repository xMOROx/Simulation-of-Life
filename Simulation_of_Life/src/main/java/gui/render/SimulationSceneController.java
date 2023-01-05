package gui.render;

import World.SimulationEngine;
import gui.interfaces.IGuiObserver;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.Arrays;

public class SimulationSceneController implements IGuiObserver {

    //FXML---------------------------------------------------
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
    //------------------------------------------------------

    //simulation--------------------------------------------
    private SimulationEngine engine;
    private MapVisualizer mapVisualizer;
    private Thread thread;

    //------------------------------------------------------
    private boolean firstStart = true;


    public void onDeleteSimulationButtonClicked() {
        mainSceneController.removeTab(this);
    }

    public void setMainSceneController(MainSceneController mainSceneController) {
        this.mainSceneController = mainSceneController;
    }

    public void onStartButtonClicked() {
        startSimulation();
        deleteSimulationButton.setDisable(true);
        startButton.setDisable(true);
        stopButton.setDisable(false);
        liveAnimalsChoiceBox.setDisable(true);
        deadAnimalsChoiceBox.setDisable(true);
    }

    public void onStopButtonClicked() throws InterruptedException {
        deleteSimulationButton.setDisable(false);
        startButton.setDisable(false);
        stopButton.setDisable(true);
        liveAnimalsChoiceBox.setDisable(false);
        deadAnimalsChoiceBox.setDisable(false);
        pauseSimulation();
    }

    private void startSimulation() {
        if (firstStart) {
            thread.start();
            firstStart = false;
        } else {
            thread.resume();
        }
    }

    private void pauseSimulation() throws InterruptedException {
        thread.suspend();
    }

    public void setWorld(SimulationEngine engine) {
        this.engine = engine;
        engine.registerGuiObserver(this);
        this.thread = new Thread(engine);
        this.engine.getWorld().firstPopulation();
        this.mapVisualizer = new MapVisualizer(this.engine.getWorld(), mapGridPane);
        mapGridPane.setAlignment(Pos.CENTER);
        updateGui();
    }


    public void updateGui() {
        try {
            Platform.runLater(this::updateMap);
            Thread.sleep(300);
        } catch (InterruptedException ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    public void updateMap() {
        mapVisualizer.visualizeMap();
    }
}
