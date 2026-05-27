package it.unibo.minigoolf.model.surfaces.wind;

import it.unibo.minigoolf.util.Vector2D;

/**
 * Represents the possible directions for the wind.
 */
public enum WindDirection {
    /** Upward direction (negative Y in standard 2D graphics coordinates). */
    UP(0, -1),
    /** Downward direction (positive Y). */
    DOWN(0, 1),
    /** Leftward direction (negative X). */
    LEFT(-1, 0),
    /** Rightward direction (positive X). */
    RIGHT(1, 0);

    private final Vector2D direction;

    WindDirection(final double x, final double y) {
        this.direction = new Vector2D(x, y);
    }

    /**
     * Gets a Vector2D representing the wind with the given intensity.
     * 
     * @param intensity the strength of the wind
     * @return the wind vector
     */
    public Vector2D getVector(final double intensity) {
        return this.direction.scalarMultiply(intensity);
    }
}
