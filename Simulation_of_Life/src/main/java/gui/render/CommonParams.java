package Gui.Render;

import javafx.scene.Scene;

public  class CommonParams {
    protected String name;
    protected int width;
    protected int height;
    protected int boxWidth;
    protected int boxHeight;
    protected double borderWidth;
    protected int textSize;
    protected int padding;
    protected int offsetX;
    protected int offsetY;
    protected Scene scene;

    public static class Config {
        public String name = "Placeholder";
        public int width = 600;
        public int height = 300;
        public int boxWidth = 150;
        public int boxHeight = 50;
        public int textSize = 24;
        public int padding = 10;
        public double borderWidth = 2;
        public int offsetX = 0;
        public int offsetY = 0;
    }
}
