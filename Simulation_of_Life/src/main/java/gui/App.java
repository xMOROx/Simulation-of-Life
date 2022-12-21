package Gui;

import Gui.Render.Menu.Menu;
import javafx.application.Application;

import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Menu menu = new Menu(new Menu.Config(){{
            this.name = "Menu";
            this.width = 900;
            this.height = 600;
        }});
        menu.render();
    }
}

