package gui.interfaces;

import Misc.LoadImages;
import javafx.scene.layout.VBox;

public interface isRenderableInsideCell {
    VBox render(int size, LoadImages images);
}
