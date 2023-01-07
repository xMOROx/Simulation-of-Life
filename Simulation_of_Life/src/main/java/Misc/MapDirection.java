package Misc;

import java.util.Random;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public MapDirection next() {
        return getMapDirection(NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST, NORTH);
    }

    public MapDirection previous() {
        return getMapDirection(NORTHWEST, NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST);
    }
    public MapDirection opposite() {
        return getMapDirection(SOUTH, SOUTHWEST, WEST, NORTHWEST, NORTH, NORTHEAST, EAST, SOUTHEAST);
    }

    private MapDirection getMapDirection(MapDirection northwest, MapDirection north, MapDirection northeast, MapDirection east, MapDirection southeast, MapDirection south, MapDirection southwest, MapDirection west) {
        return switch (this) {
            case NORTH -> northwest;
            case NORTHEAST -> north;
            case EAST -> northeast;
            case SOUTHEAST -> east;
            case SOUTH -> southeast;
            case SOUTHWEST -> south;
            case WEST -> southwest;
            case NORTHWEST -> west;
        };
    }

    public static MapDirection getRandomDirection() throws IllegalArgumentException {
        int value = (new Random().nextInt(8));

        return switch (value) {
            case 0 -> NORTH;
            case 1 -> NORTHEAST;
            case 2 -> EAST;
            case 3 -> SOUTHEAST;
            case 4 -> SOUTH;
            case 5 -> SOUTHWEST;
            case 6 -> WEST;
            case 7 -> NORTHWEST;
            default -> throw new IllegalStateException("Unexpected value: " + value);
        };
    }

    public Vector2D toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2D(0,1);
            case NORTHEAST -> new Vector2D(1,1);
            case EAST -> new Vector2D(1,0);
            case SOUTHEAST -> new Vector2D(1,-1);
            case SOUTH -> new Vector2D(0, -1);
            case SOUTHWEST -> new Vector2D(-1, -1);
            case WEST -> new Vector2D(-1, 0);
            case NORTHWEST -> new Vector2D(-1, 1);
        };
    }


    public MapDirection rotateRight(int i) {
        MapDirection result = this;
        for (int j = 0; j < i; j++) {
            result = result.next();
        }
        return result;
    }

    public MapDirection rotateLeft(int i) {
        MapDirection result = this;
        for (int j = 0; j < i; j++) {
            result = result.previous();
        }
        return result;
    }

}
