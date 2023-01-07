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

    public Image getRandomAnimalImage() {
        return this.animalsImages.get((int) (Math.random() * this.animalsImages.size()));
    }
    public Image getRandomGrassImage() {
        return this.grassesImages.get((int) (Math.random() * this.grassesImages.size()));
    }
    public Image getRandomEmptyImage() {
        Random random = new Random();
        if (random.nextInt(100) < 90) {
            return this.emptiesImages.get(this.emptiesImages.size() - 1);
        }
        return this.emptiesImages.get((int) (Math.random() * this.emptiesImages.size() - 1));
    }
}
