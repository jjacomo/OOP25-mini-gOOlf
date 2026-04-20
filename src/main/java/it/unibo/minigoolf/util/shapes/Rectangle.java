package it.unibo.minigoolf.util.shapes;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * @author jack
 *
 *         Represents a rectangular area in 2D space.
 *
 * @param position the top-left corner of the rectangle
 * @param width    the width of the rectangle
 * @param height   the height of the rectangle
 *
 */
public record Rectangle(Vector2D position, double width, double height) implements Shape {

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
}
