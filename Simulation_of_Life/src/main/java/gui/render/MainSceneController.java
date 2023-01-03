package gui.render;


import Settings.Parameters;
import World.Maps.WorldMap;
import World.SimulationEngine;
import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainSceneController implements Initializable {

    //controllers---------------------------------------
    @FXML
    private NewSimulationController newSimulationController;
    @FXML
    private final List<SimulationSceneController> simulationSceneControllerList = new ArrayList<>();
    //---------------------------------------------------

    private int simulationNumber = 1;
    @FXML
    private TabPane tabPane;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onNewSimulationButtonClicked() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/new-simulation.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 867, 481);
        Stage stage = new Stage();
        newSimulationController = fxmlLoader.getController();
        newSimulationController.setStage(stage);
        newSimulationController.setControllers(this);
        stage.setResizable(false);
        stage.setTitle("New simulation");
        stage.setScene(scene);
        stage.show();
    }

    public void createNewSimulation(Parameters parameters) throws IOException {
        System.out.println(getClass().getResource("/simulation-scene.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/simulation-scene.fxml"));
        Parent root = fxmlLoader.load();
        SimulationSceneController simulationSceneController = fxmlLoader.getController();
        simulationSceneController.setMainSceneController(this);
        WorldMap worldMap = SimulationEngine.fromConfig(parameters);
        simulationSceneController.setWorld(new SimulationEngine(worldMap));
        simulationSceneControllerList.add(simulationSceneController);
        Tab tab = new Tab("Simulation " + simulationNumber);
        simulationNumber += 1;
        tab.setContent(root);
        tabPane.getTabs().add(tab);
    }

    public void removeTab(SimulationSceneController simulationSceneController) {
        if (simulationSceneControllerList.size() == 0) return;
        int i = simulationSceneControllerList.indexOf(simulationSceneController);
        tabPane.getTabs().remove(i + 1, i + 2);
        simulationSceneControllerList.remove(i);
    }


}
