package it.unibo.minigoolf.util.shapes;

import it.unibo.minigoolf.util.Vector2D;

/**
 * A record representing a circle shape defined by its center position and radius.
 * Implements the Shape interface to check if a point is contained within the circle.
 *
 * @param position the center position of the circle as a Vector2D
 * @param radius the radius of the circle
 */
public record Circle(Vector2D position, double radius) implements Shape {

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

}
