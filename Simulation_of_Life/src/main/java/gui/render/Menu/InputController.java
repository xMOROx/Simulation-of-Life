package Gui.Render.Menu;

import Gui.Render.IsRenderable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class InputController implements IsRenderable {
    protected String name;
    protected int width;
    protected int height;
    protected int boxWidth;
    protected int boxHeight;
    protected int textSize;
    protected VBox box;

    public InputController(Config config) {
        this.name = config.name;
        this.width = config.width;
        this.height = config.height;
        this.boxWidth = config.boxWidth;
        this.boxHeight = config.boxHeight;
        this.textSize = config.textSize;
        this.box = new VBox();
    }

    @Override
    public void render() {
        Label title = new Label(this.name);
        title.setMinWidth(this.width);
        title.setMinHeight(this.height);
        title.setAlignment(javafx.geometry.Pos.CENTER);

        NumberTextField input = new NumberTextField();
        input.setMinWidth(this.width);
        input.setMinHeight(this.height);
        input.setAlignment(javafx.geometry.Pos.CENTER);
        this.box.getChildren().addAll(title, input);

    }

    public VBox getBox() {
        return this.box;
    }

    public static class Config {
        public String name = "InputController";
        public int width = 150;
        public int height = 30;
        public int boxWidth = 150;
        public int boxHeight = 30;
        public int textSize = 20;
    }
}
