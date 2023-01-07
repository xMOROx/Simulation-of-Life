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
        this.sideLengthOfSquarePx = Math.max(60, Math.max(squareWidth, squareHeight));
        this.gridPane.getColumnConstraints().add(new ColumnConstraints(this.sideLengthOfSquarePx));
        this.gridPane.getRowConstraints().add(new RowConstraints(this.sideLengthOfSquarePx));
    }

    public void visualizeMap() {
        this.gridPane.getChildren().clear();
        for (int x = 0; x < this.map.getWidth(); x++) {
            for (int y = 0; y < this.map.getHeight(); y++) {
                Vector2D position = new Vector2D(x, y);
                Cell cell = this.map.cellOrNullAt(position);
                GridPane cellGrid = cell.render(this.sideLengthOfSquarePx, 5);
                this.gridPane.add(cellGrid, x, y);
            }
        }
    }
}
