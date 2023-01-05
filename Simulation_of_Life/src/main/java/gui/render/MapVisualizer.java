package gui.render;

import Misc.Vector2D;
import Settings.Constants;
import World.Maps.WorldMap;
import gui.render.World.Cell;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;


public class MapVisualizer {
    private final WorldMap map;
    private final GridPane gridPane;
    private int sideLengthOfSquarePx;

    public MapVisualizer(WorldMap map, GridPane gridPane) {
        this.map = map;
        this.gridPane = gridPane;
        this.calculateColumnsAndRowsSize();
    }

    private void calculateColumnsAndRowsSize() {
        int squareHeight = (int) Math.ceil(1.0 * Constants.GRID_PANE_HEIGHT_PX / map.getHeight());
        int squareWidth  = (int) Math.ceil(1.0 * Constants.GRID_PANE_WIDTH_PX / map.getWidth());
        this.sideLengthOfSquarePx = Math.max(40, Math.max(squareWidth, squareHeight));
        gridPane.getColumnConstraints().add(new ColumnConstraints(sideLengthOfSquarePx));
        gridPane.getRowConstraints().add(new RowConstraints(sideLengthOfSquarePx));
    }

    public void visualizeMap() {
        gridPane.getChildren().clear();
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Vector2D position = new Vector2D(x, y);
                Cell cell = this.map.cellOrNullAt(position);
                if(cell != null) {
                    GridPane cellGrid = cell.render(this.sideLengthOfSquarePx, 5);
                    gridPane.add(cellGrid, x, y);
                }
                else {
                    HBox hBox = new HBox();
                    Label coordinates = new Label(position.toString());
                    coordinates.setAlignment(javafx.geometry.Pos.CENTER);
                    hBox.getChildren().add(coordinates);
                    hBox.setAlignment(javafx.geometry.Pos.CENTER);
                    hBox.setPrefSize(this.sideLengthOfSquarePx, this.sideLengthOfSquarePx);
                    this.gridPane.add(hBox, x, y);
                }
            }
        }
    }
}
