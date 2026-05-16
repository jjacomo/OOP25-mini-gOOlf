package it.unibo.minigoolf.util.shapes;

import it.unibo.minigoolf.util.Vector2D;
import java.awt.Color;

/**
 * A record representing a circle shape defined by its center position and radius.
 * Implements the Shape interface to check if a point is contained within the circle.
 *
 * @param position the center position of the circle as a Vector2D
 * @param radius the radius of the circle
 * @param color    the color of the circle
 */
public record Circle(Vector2D position, double radius, Color color) implements Shape {

    /**
     * Checks if a given position is contained within this circle.
     * A point is considered inside the circle if its distance from the center is less than the radius.
     *
     * @param pos the position to check as a Vector2D
     * @return true if the position is inside the circle, false otherwise
     */
    @Override
    public boolean contains(final Vector2D pos) {
        return position.distance(pos) < radius;
    }

    /**
     * Returns the color of the circle.
     * 
     * @return the color of the circle
     */
    @Override
    public Color getColor() {
        return this.color;
    }
}
