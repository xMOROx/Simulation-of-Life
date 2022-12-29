package Gui;

import Gui.Render.Menu.Menu;
import Gui.Render.World.Map;

import javafx.application.Application;

import javafx.stage.Stage;

import java.util.function.Consumer;


public class App extends Application {

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Consumer<Map> mapFunction = Map::render;
        Menu menu = new Menu(new Menu.Config(){{
            this.name = "Menu";
            this.width = 900;
            this.height = 600;
        }}, mapFunction);
        menu.render();
    }
}

