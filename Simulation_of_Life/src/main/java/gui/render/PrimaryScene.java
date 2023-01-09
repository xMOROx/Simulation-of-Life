package gui.render;


import Settings.Parameters;

import World.Maps.WorldMap;
import World.SimulationEngine;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PrimaryScene implements Initializable {

    //controllers---------------------------------------
    @FXML
    private SimulationMenuForNewSimulation simulationMenuForNewSimulation;
    @FXML
    private final List<SimulationScene> simulationSceneList = new ArrayList<>();
    @FXML
    public Label introduceText;

    //---------------------------------------------------

    private int simulationNumber = 1;
    @FXML
    private TabPane tabPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.introduceText.setStyle("-fx-font-size: 56px;");
    }

    @FXML
    public void onNewSimulationButtonClicked() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/create_simulationa_menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 867, 481);
        Stage stage = new Stage();
        simulationMenuForNewSimulation = fxmlLoader.getController();
        simulationMenuForNewSimulation.setStage(stage);
        simulationMenuForNewSimulation.setControllers(this);
        stage.setResizable(false);
        stage.setTitle("New simulation");
        stage.setScene(scene);
        stage.show();
    }

    public void createNewSimulation(Parameters parameters) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simulation_scene.fxml"));
        Parent root = fxmlLoader.load();
        SimulationScene simulationScene = fxmlLoader.getController();
        simulationScene.setMainSceneController(this);

        WorldMap worldMap = SimulationEngine.fromConfig(parameters);

        simulationScene.setWorld(new SimulationEngine(worldMap));
        simulationScene.setRefreshRate(parameters.getRefreshRate());
        simulationScene.setIsSaveToCSV(parameters.isSaveToCSV());
        simulationSceneList.add(simulationScene);
        Tab tab = new Tab("Simulation " + simulationNumber);
        simulationNumber += 1;
        tab.setContent(root);
        tabPane.getTabs().add(tab);
    }

    public void removeTab(SimulationScene simulationScene) {
        if (simulationSceneList.size() == 0) return;
        int i = simulationSceneList.indexOf(simulationScene);
        tabPane.getTabs().remove(i + 1, i + 2);
        simulationSceneList.remove(i);
    }


}
