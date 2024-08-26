package tp1;

import java.awt.*;

public class Square {
    private int x;
    private int y;
    private Color color;

    public Square(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public boolean isInside(int x, int y) {
        return x >= this.x && x <= this.x + 50 &&
                y >= this.y && y <= this.y + 50;

    }

    public boolean isInsideTarget(int x, int y, int targetX, int targetY) {
        return x >= targetX && x <= targetX + 50 &&
                y >= targetY && y <= targetY + 50;
    }
}