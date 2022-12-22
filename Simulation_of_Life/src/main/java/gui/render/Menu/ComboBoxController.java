package Gui.Render.Menu;

import Gui.Render.IsRenderable;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class ComboBoxController implements IsRenderable {
    protected ComboBox<String> comboBox;
    protected List<String> items;
    protected VBox box;
    protected Label label;
    protected Integer padding = 5;

    protected String selectedValue = null;

    public ComboBoxController(int width, int height, List<String> items, String name) {
        this.comboBox = new ComboBox<>();
        this.comboBox.setPrefWidth(width);
        this.comboBox.setPrefHeight(height);
        this.box = new VBox((int)(this.padding / 2));
        this.box.setStyle("-fx-background-color: #ccc;");
        this.box.setPrefWidth(width);
        this.box.setPrefHeight(2*height);
        this.box.setPadding(new Insets(padding));
        this.items = items;
        this.label  = new Label(name);
        this.label.setPrefWidth(width);
        this.label.setPrefHeight(height);
    }


    @Override
    public void render() {
        this.comboBox.setItems(FXCollections.observableArrayList(this.items));
        this.comboBox.getSelectionModel().selectFirst();
        this.comboBox.setPromptText(this.items.get(0));
        this.comboBox.setEditable(false);
        this.comboBox.setOnAction(this::setValue);
        this.box.getChildren().addAll(this.label, this.comboBox);
        this.box.setAlignment(javafx.geometry.Pos.CENTER);
        this.label.setAlignment(javafx.geometry.Pos.CENTER);
    }

    public void setValue (ActionEvent actionEvent) {
        this.selectedValue  = this.comboBox.getValue();
    }

    public VBox getBox() {
        return this.box;
    }
}
