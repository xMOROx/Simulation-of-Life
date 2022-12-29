package Gui.Render;

import javafx.scene.Scene;

public  class CommonParams {
    protected String name;
    protected int width;
    protected int height;
    protected int boxWidth;
    protected int boxHeight;
    protected int textSize;
    protected int padding;
    protected Scene scene;

    public static class Config {
        public String name = "Placeholder";
        public int width = 600;
        public int height = 300;
        public int boxWidth = 150;
        public int boxHeight = 50;
        public int textSize = 24;
        public int padding = 10;
    }
}
