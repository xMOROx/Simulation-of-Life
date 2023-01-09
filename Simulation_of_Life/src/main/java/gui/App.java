package gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/primary_scene.fxml"));
        Scene scene = new Scene(loader.load(), 1530, 790);
        primaryStage.setResizable(true);
        primaryStage.setTitle("Simulation of life!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

