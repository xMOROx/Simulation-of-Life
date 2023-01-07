package Misc;

import javafx.scene.image.Image;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LoadImages {
    public  List<Image> animalsImages;
    public  List<Image> grassesImages;
    public List<Image> emptiesImages;

    public LoadImages(int size) {
        this.emptiesImages = new LinkedList<>(List.of(
                new Image("file:src/main/resources/empties/empty_1.png", size, size, false, false),
                new Image("file:src/main/resources/empties/empty_2.png", size, size, false, false),
                new Image("file:src/main/resources/empties/empty_3.png", size, size, false, false),
                new Image("file:src/main/resources/empties/empty_4.png", size, size, false, false),
                new Image("file:src/main/resources/empties/empty_5.png", size, size, false, false),
                new Image("file:src/main/resources/empties/empty_6.png", size, size, false, false)
        ));
        this.grassesImages = new LinkedList<>(
                List.of(
                        new Image("file:src/main/resources/grasses/grass_1.png", size, size, false, false),
                        new Image("file:src/main/resources/grasses/grass_2.png", size, size, false, false)
                )
        );
        this.animalsImages = new LinkedList<>(
                List.of(
                        new Image("file:src/main/resources/animals/animal_1.png", size, size, false, false),
                        new Image("file:src/main/resources/animals/animal_2.png",   size, size, false, false),
                        new Image("file:src/main/resources/animals/animal_3.png",  size, size, false, false),
                        new Image("file:src/main/resources/animals/animal_4.png", size, size, false, false),
                        new Image("file:src/main/resources/animals/animal_5.png", size, size, false, false),
                        new Image("file:src/main/resources/animals/animal_6.png", size, size, false, false),
                        new Image("file:src/main/resources/animals/animal_7.png", size, size, false, false),
                        new Image("file:src/main/resources/animals/animal_8.png", size, size, false, false),
                        new Image("file:src/main/resources/animals/animal_9.png", size, size, false, false),
                        new Image("file:src/main/resources/animals/animal_10.png",  size, size, false, false),
                        new Image("file:src/main/resources/animals/animal_11.png", size, size, false, false),
                        new Image("file:src/main/resources/animals/animal_12.png", size, size, false, false)
                )
        );
    }

    public Image getAnimalImage(int index) {
        return this.animalsImages.get(index);
    }
    public int getAnimalImageSize() {
        return this.animalsImages.size();
    }

    public Image getGrassImage(int index) {
        return this.grassesImages.get(index);
    }

    public int getGrassImageSize() {
        return this.grassesImages.size();
    }
    public Image getEmptyImage(int index) {
        return this.emptiesImages.get(index);
    }
    public int getEmptyImageSize() {
        return this.emptiesImages.size();
    }
}
