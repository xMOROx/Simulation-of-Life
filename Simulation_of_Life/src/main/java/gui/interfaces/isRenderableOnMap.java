package gui.interfaces;

import Misc.LoadImages;
import javafx.scene.layout.GridPane;

public interface isRenderableOnMap {
    GridPane render(int size, LoadImages loadImages);
}

