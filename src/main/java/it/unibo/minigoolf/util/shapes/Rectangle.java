package it.unibo.minigoolf.util.shapes;

import it.unibo.minigoolf.util.Vector2D;

/**
 * Represents a rectangular area in 2D space.
 * 
 * @author jack
 *
 * @param position the top-left corner of the rectangle
 * @param width    the width of the rectangle
 * @param height   the height of the rectangle
 */
public record Rectangle(Vector2D position, double width, double height) implements Shape {

    /**
     * Compact constructor to validate width and height.
     */
    public Rectangle {
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be greater than 0, got: " + width);
        }
        if (height <= 0) {
            throw new IllegalArgumentException("Height must be greater than 0, got: " + height);
        }
    }

    @Override
    public boolean contains(final Vector2D position2d) {
        return position2d.getX() >= this.position.getX()
                && position2d.getX() <= this.position.getX() + width
                && position2d.getY() >= this.position.getY()
                && position2d.getY() <= this.position.getY() + height;
    }
}
