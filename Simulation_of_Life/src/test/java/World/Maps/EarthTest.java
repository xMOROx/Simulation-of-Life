package World.Maps;

import Misc.Vector2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class EarthTest {

    @Test
    void mapCoords() {
        int width = 10;
        int height = 10;
        Earth earth = new Earth(width, height);
        int x = 0;
        int y = 0;
        Assertions.assertEquals(new Vector2D(x, y), earth.mapCoords(x, y));
        x = -1;
        Assertions.assertEquals(new Vector2D(width, y), earth.mapCoords(x, y));
        x = width + 1;
        Assertions.assertEquals(new Vector2D(0, y), earth.mapCoords(x, y));
        x = 0;
        y = -1;
        Assertions.assertEquals(new Vector2D(x, y), earth.mapCoords(x, y));
        y = height + 1;
        Assertions.assertEquals(new Vector2D(x, y), earth.mapCoords(x, y));

    }
}

