package Misc;

import java.util.Objects;
import java.util.Random;

public class Vector2D {
    private final int x;
    private final int y;

    public Vector2D (int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector2D vector2D)) return false;
        return x == vector2D.x && y == vector2D.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    public Vector2D opposite() {
        return new Vector2D(-this.x, -this.y);
    }

    public Vector2D precedes(Vector2D other) {
        return new Vector2D(Math.min(this.x, other.x), Math.min(this.y, other.y));
    }

    public Vector2D follows(Vector2D other) {
        return new Vector2D(Math.max(this.x, other.x), Math.max(this.y, other.y));
    }

    public Vector2D upperRight(Vector2D other) {
        return new Vector2D(Math.max(this.x, other.x), Math.min(this.y, other.y));
    }

    public Vector2D lowerLeft(Vector2D other) {
        return new Vector2D(Math.min(this.x, other.x), Math.max(this.y, other.y));
    }

    public static Vector2D randomVector(Vector2D min, Vector2D max) {
        Random random = new Random();
        int x = random.nextInt(min.x, max.x + 1);
        int y = random.nextInt(min.y, max.y + 1);
        return new Vector2D(x, y);
    }
    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
