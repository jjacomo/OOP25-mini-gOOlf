package it.unibo.minigoolf.util.shapes;

import it.unibo.minigoolf.util.Vector2D;

/**
 * A record representing an oval (ellipse) shape defined by its center position,
 * horizontal radius, and vertical radius.
 * 
 * @author Jack
 *
 * @param position the center position of the oval as a Vector2D
 * @param radiusX  the horizontal radius of the oval
 * @param radiusY  the vertical radius of the oval
 */
public record Oval(Vector2D position, double radiusX, double radiusY) implements Shape {

    /**
     * Compact constructor to validate the radii.
     */
    public Oval {
        if (radiusX <= 0) {
            throw new IllegalArgumentException("Horizontal radius must be greater than 0, got: " + radiusX);
        }
        if (radiusY <= 0) {
            throw new IllegalArgumentException("Vertical radius must be greater than 0, got: " + radiusY);
        }
    }

    /**
     * Checks if a given position is contained within this oval.
     * A point is considered inside the oval if it satisfies the ellipse equation
     *
     * @param pos the position to check as a Vector2D
     * @return true if the position is inside the oval, false otherwise
     */
    @Override
    public boolean contains(final Vector2D pos) {
        final double dx = pos.getX() - position.getX();
        final double dy = pos.getY() - position.getY();
        return dx * dx / (radiusX * radiusX) + dy * dy / (radiusY * radiusY) <= 1.0;
    }
}
