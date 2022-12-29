package Gui.Render.Menu;

import Gui.Render.IsRenderable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;

public class InputController implements IsRenderable {
    protected String name;
    protected int width;
    protected int height;
    protected int boxWidth;
    protected int boxHeight;
    protected int textSize;
    protected int padding;

    protected int min;
    protected int max;
    protected VBox box;

    public InputController(Config config) {
        this.name = config.name;
        this.width = config.width - 2*config.padding;
        this.height = config.height - 2*config.padding;
        this.boxWidth = config.boxWidth - 2*config.padding;
        this.boxHeight = config.boxHeight - 2*config.padding;
        this.textSize = config.textSize;
        this.padding  = config.padding;
        this.min = config.min;
        this.max = config.max;
        this.box = new VBox();
    }

    @Override
    public void render() {
        Label title = new Label(this.name);
        title.setMinWidth(this.width);
        title.setMinHeight(this.height/2);
        title.setAlignment(javafx.geometry.Pos.CENTER);

        TextField numberField = new NumberTextField();
        numberField.setPromptText(this.min + " - " + this.max);
        numberField.setTextFormatter(new TextFormatter<>(change -> {
            if (change.isDeleted()) {
                return change;
            }

            String txt = change.getControlNewText();

            if (txt.matches("0\\d+")) {
                return null;
            }

            try {
                int n = Integer.parseInt(txt);
                return this.min <= n && n <= this.max ? change : null;
            } catch (NumberFormatException e) {
                return null;
            }
        }));

        numberField.setMinWidth(this.width);
        numberField.setMinHeight(this.height/2);
        numberField.setAlignment(javafx.geometry.Pos.CENTER);
        this.box.setPadding(new Insets(this.padding));
        this.box.setSpacing((int)(this.padding/2));
        this.box.setMinWidth(this.boxWidth);
        this.box.setMinHeight(this.boxHeight);
        this.box.getChildren().addAll(title, numberField);

    }

    public VBox getBox() {
        return this.box;
    }

    public int getValue() {
        return Integer.parseInt(((TextField)this.box.getChildren().get(1)).getText());
    }

    public static class Config {
        public String name = "InputController";
        public int width = 150;
        public int height = 30;
        public int boxWidth = 150;
        public int boxHeight = 30;
        public int textSize = 20;

        public int min = 1;
        public int max = 64;
        public int padding = 10;
    }
}
