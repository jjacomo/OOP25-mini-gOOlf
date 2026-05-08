package it.unibo.minigoolf.util.shapes;

import it.unibo.minigoolf.util.Vector2D;
import java.awt.Color;

/**
 * @author jack
 *
 *         Represents a rectangular area in 2D space.
 *
 * @param position the top-left corner of the rectangle
 * @param width    the width of the rectangle
 * @param height   the height of the rectangle
 * @param color    the color of the rectangle
 */
public record Rectangle(Vector2D position, double width, double height, Color color) implements Shape {

    /**
     * Checks if the given position is contained within this rectangle.
     * 
     * @param position the position to check
     * 
     * @return true if the position is inside the rectangle, false otherwise
     */
    @Override
    public boolean contains(final Vector2D position2d) {
        return position2d.getX() >= this.position.getX()
                && position2d.getX() <= this.position.getX() + width
                && position2d.getY() >= this.position.getY()
                && position2d.getY() <= this.position.getY() + height;
    }

    /**
     * Returns the color of the rectangle
     * 
     * @param color the color
     * @return the color of the rectangle
     */
    public Color getColor(Color color){
        return this.color;
    }
}
