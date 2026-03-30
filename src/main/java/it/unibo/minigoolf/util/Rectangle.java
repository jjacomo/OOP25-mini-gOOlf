package it.unibo.minigoolf.util;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * Represents a rectangular area in 2D space.
 * 
 * @param position the top-left corner of the rectangle
 * @param width the width of the rectangle
 * @param height the height of the rectangle
 * 
 * @author jack
 */
public record Rectangle(Vector2D position, double width, double height) {

    /**
     * Checks if the given position is contained within this rectangle.
     * 
     * @param position the position to check
     * 
     * @return true if the position is inside the rectangle, false otherwise
     */
    public boolean contains(final Vector2D position2d) {
        return position2d.getX() >= this.position.getX()
               && position2d.getX() <= this.position.getX() + width
               && position2d.getY() >= this.position.getY()
               && position2d.getY() <= this.position.getY() + height;
    }
}
