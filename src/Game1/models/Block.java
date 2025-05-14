package Game1.models;


import java.awt.Color;
import java.awt.Point;
import java.io.Serial;
import java.io.Serializable;



public class Block implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public enum BlockType { CAO_CAO, GUAN_YU, GENERAL, SOLDIER }

    private final BlockType type;
    private int x;
    private int y;
    private final int width;
    private final int height;
    private Color color;

    public Block(BlockType type, int x, int y, int width, int height, Color color) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public BlockType getType() { return type; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Color getColor() { return color; }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean contains(Point p) {
        return p.x >= x && p.x < x + width && p.y >= y && p.y < y + height;
    }

    @Override
    public String toString() {
        return type + " (" + x + "," + y + ") " + width + "x" + height;
    }
}
