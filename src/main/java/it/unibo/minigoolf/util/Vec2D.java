package it.unibo.minigoolf.util;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import java.awt.Point;

/**
 * Temporary class imported to verify vector method behaviour.
 * Will be removed once the test class is ready.
 *
 * @author fede
 */
public final class Vec2D {

    private final Vector2D inner;

    /**
     * Creates a vector from dx and dy components.
     *
     * @param dx horizontal component
     * @param dy vertical component
     */
    public Vec2D(final double dx, final double dy) {
        this.inner = new Vector2D(dx, dy);
    }

    /**
     * Creates a vector from two AWT Points (start to end).
     *
     * @param from starting point
     * @param to   ending point
     */
    public Vec2D(final Point from, final Point to) {
        this.inner = new Vector2D(to.x - from.x, to.y - from.y);
    }

    /**
     * Factory method: creates a vector from an angle (radians) and a magnitude.
     * Separate from the (double, double) constructor to avoid signature collision.
     *
     * @param angle     direction in radians
     * @param magnitude length
     *
     * @return a new Vec2D pointing in the given direction with the given magnitude
     */
    public static Vec2D fromAngleAndMagnitude(final double angle, final double magnitude) {
        return new Vec2D(Math.cos(angle) * magnitude, Math.sin(angle) * magnitude);
    }

    /**
     * Returns the X component.
     *
     * @return the X component
     */
    public double getX() {
        return inner.getX();
    }

    /**
     * Returns the Y component.
     *
     * @return the Y component
     */
    public double getY() {
        return inner.getY();
    }

    /**
     * Returns the Euclidean length of this vector.
     *
     * @return the module
     */
    public double getModule() {
        return inner.getNorm();
    }

    /**
     * Returns the squared length (avoids a sqrt, useful for power comparisons).
     *
     * @return the squared module
     */
    public double getSquareModule() {
        return inner.getNormSq();
    }

    /**
     * Returns the angle of this vector in radians.
     *
     * @return the angle in radians
     */
    public double getAngle() {
        return Math.atan2(inner.getY(), inner.getX());
    }

    /**
     * Returns the opposite (negated) vector.
     *
     * @return new Vec2D pointing in the opposite direction
     */
    public Vec2D getOppositeVector() {
        return new Vec2D(-inner.getX(), -inner.getY());
    }

    /**
     * Translates a Point by this vector.
     *
     * @param origin the starting point
     *
     * @return a new Point at origin + this vector
     */
    public Point translate(final Point origin) {
        return new Point(
            (int) (origin.x + inner.getX()),
            (int) (origin.y + inner.getY())
        );
    }

    /**
     * Returns a scaled copy of this vector clamped to a maximum length.
     *
     * @param maxLength maximum allowed length
     *
     * @return new Vec2D with the same direction but length &lt;= maxLength
     */
    public Vec2D clampedTo(final double maxLength) {
        final double len = getModule();
        if (len <= maxLength || len == 0) {
            return this;
        }
        final double scale = maxLength / len;
        return new Vec2D(inner.getX() * scale, inner.getY() * scale);
    }

    /**
     * Returns the underlying Apache Commons Math Vector2D.
     * Returns a defensive copy to avoid exposing internal representation.
     *
     * @return a copy of the inner Vector2D
     */
    public Vector2D toApache() {
        return new Vector2D(inner.getX(), inner.getY());
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return String.format("Vec2D(%.2f, %.2f)", inner.getX(), inner.getY());
    }
}
