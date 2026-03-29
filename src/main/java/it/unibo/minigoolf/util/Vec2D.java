package it.unibo.minigoolf.util;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import java.awt.Point;

/**
 * Utility class wrapping Apache Commons Math Vector2D.
 * Provides helper methods for 2D vector operations used in shot input and physics.
 *
 * @author fede / dani
 */
public class Vec2D {

    private final Vector2D inner;

    /**
     * Creates a vector from dx and dy components.
     *
     * @param dx horizontal component
     * @param dy vertical component
     */
    public Vec2D(double dx, double dy) {
        this.inner = new Vector2D(dx, dy);
    }

    /**
     * Creates a vector from two AWT Points (start -> end).
     *
     * @param from starting point
     * @param to   ending point
     */
    public Vec2D(Point from, Point to) {
        this.inner = new Vector2D(to.x - from.x, to.y - from.y);
    }

    /**
     * Factory method: creates a vector from an angle (radians) and a magnitude.
     * Separate from the (double, double) constructor to avoid signature collision.
     *
     * @param angle     direction in radians
     * @param magnitude length
     * @return a new Vec2D pointing in the given direction with the given magnitude
     */
    public static Vec2D fromAngleAndMagnitude(double angle, double magnitude) {
        return new Vec2D(Math.cos(angle) * magnitude, Math.sin(angle) * magnitude);
    }

    /** @return the X component */
    public double getX() {
        return inner.getX();
    }

    /** @return the Y component */
    public double getY() {
        return inner.getY();
    }

    /** @return the Euclidean length of this vector */
    public double getModule() {
        return inner.getNorm();
    }

    /** @return the squared length (avoids a sqrt — useful for power comparisons) */
    public double getSquareModule() {
        return inner.getNormSq();
    }

    /** @return the angle of this vector in radians */
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
     * @return a new Point at origin + this vector
     */
    public Point translate(Point origin) {
        return new Point(
            (int) (origin.x + inner.getX()),
            (int) (origin.y + inner.getY())
        );
    }

    /**
     * Returns a scaled copy of this vector clamped to a maximum length.
     *
     * @param maxLength maximum allowed length
     * @return new Vec2D with the same direction but length <= maxLength
     */
    public Vec2D clampedTo(double maxLength) {
        double len = getModule();
        if (len <= maxLength || len == 0) {
            return this;
        }
        double scale = maxLength / len;
        return new Vec2D(inner.getX() * scale, inner.getY() * scale);
    }

    /** @return the underlying Apache Commons Math Vector2D */
    public Vector2D toApache() {
        return inner;
    }

    @Override
    public String toString() {
        return String.format("Vec2D(%.2f, %.2f)", inner.getX(), inner.getY());
    }
}