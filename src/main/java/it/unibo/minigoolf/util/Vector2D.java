package it.unibo.minigoolf.util;

import java.awt.Point;

/**
 * A simple immutable 2D vector class.
 * 
 * @author Mattia
 */
public class Vector2D {

    /**
     * The representation of a zero vector with coordinates (0.0, 0.0).
     */
    public static final Vector2D ZERO = new Vector2D(0.0, 0.0);

    private final double x;
    private final double y;

    /**
     * Constructs a new vector with the given coordinates.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public Vector2D(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a vector from two AWT Points.
     *
     * @param from starting point
     * @param to   ending point
     */
    public Vector2D(final Point from, final Point to) {
        this.x = to.x - from.x;
        this.y = to.y - from.y;
    }

    /**
     * Returns the x-coordinate of this vector.
     *
     * @return the x-coordinate
     */
    public double getX() {
        return this.x;
    }

    /**
     * Returns the y-coordinate of this vector.
     *
     * @return the y-coordinate
     */
    public double getY() {
        return this.y;
    }

    /**
     * Returns a new vector whose components are this vector's components
     * multiplied by the given scalar.
     *
     * @param a the scalar multiplier
     * @return a new scaled vector
     */
    public Vector2D scalarMultiply(final double a) {
        return new Vector2D(a * this.x, a * this.y);
    }

    /**
     * Returns a new vector whose components are multiplied by -1.
     *
     * @return the opposite vector (-x, -y).
     */
    public Vector2D getOppositeVector() {
        return scalarMultiply(-1);
    }

    /**
     * Returns the Euclidean distance between this vector and another vector.
     *
     * @param other the other vector
     * @return the Euclidean distance
     */
    public double distance(final Vector2D other) {
        final double dx = this.x - other.x;
        final double dy = this.y - other.y;

        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Returns the squared Euclidean distance between this vector and another vector.
     *
     * @param other the other vector
     * @return the square of the Euclidean distance: dx * dx + dy * dy
     */
    public double distanceSquared(final Vector2D other) {
        final double dx = this.x - other.x;
        final double dy = this.y - other.y;

        return dx * dx + dy * dy;
    }

    /**
     * Returns a new vector that is this vector minus the given vector.
     *
     * @param other the vector to subtract
     * @return a new vector representing this - other
     */
    public Vector2D subtract(final Vector2D other) {
        return new Vector2D(this.x - other.x, this.y - other.y);
    }

    /**
     * Returns the Euclidean norm (length) of this vector.
     *
     * @return the length of this vector
     */
    public double getNorm() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * Returns the squared Euclidean norm (length) of this vector.
     *
     * @return the square of the Euclidean norm: x * x + y * y
     */
    public double getNormSquared() {
        return this.x * this.x + this.y * this.y;
    }

    /**
     * Returns a new vector that is the sum of this vector and the given vector.
     *
     * @param other the vector to add
     * @return a new vector representing this + other
     */
    public Vector2D add(final Vector2D other) {
        return new Vector2D(this.x + other.x, this.y + other.y);
    }

    /**
     * Returns the dot product of this vector and another vector.
     *
     * @param other the other vector
     * @return the dot product
     */
    public double dotProduct(final Vector2D other) {
        return this.x * other.x + this.y * other.y;
    }

    /**
     * Returns a normalized unit vector in the same direction as this vector.
     *
     * @return a unit vector in the same direction
     * @throws ArithmeticException if the norm is zero (cannot normalize a zero vector)
     */
    public Vector2D normalize() {
        final double norm = getNorm();
        if (norm == 0.0) {
            throw new ArithmeticException("Cannot normalize a zero-length vector");
        }

        return scalarMultiply(1.0 / norm);
    }

    /**
     * Returns a scaled copy of this vector clamped to a maximum length.
     *
     * @param maxLength maximum allowed length
     * @return a new Vector2D with the same direction but clamped to a maximum length.
     */
    public Vector2D clampedTo(final double maxLength) {
        final double len = getNorm();
        if (len <= maxLength || len == 0) {
            return this;
        }
        final double scale = maxLength / len;

        return new Vector2D(this.x * scale, this.y * scale);
    }

    /**
     * Translates an AWT Point by this vector.
     *
     * @param origin the starting point
     * @return a new Point at (origin.x + this.x, origin.y + this.y)
     */
    public Point translate(final Point origin) {
        return new Point((int) (origin.x + this.x), (int) (origin.y + this.y));
    }
}
