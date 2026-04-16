package it.unibo.minigoolf.util.shapes;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * @author jack
 * 
 *         Represents a shape in 2D space that can be used for collision
 *         detection and rendering.
 * 
 */
public interface Shape {
    /**
     * Checks if the given position is contained within this shape.
     * 
     * @param position the position to check
     * 
     * @return true if the position is inside the shape, false otherwise
     */
    boolean contains(Vector2D position);
}
