package gui.interfaces;

import Misc.LoadImages;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public interface isRenderableOnMap {
    GridPane render(int size, LoadImages loadImages);
}

