package it.unibo.minigoolf.model.ball;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Circle;

/**
 * Implementation of the Ball interface representing a golf ball with position,
 * velocity, and radius.
 */
public class BallImpl implements Ball {
    private Vector2D velocity;
    private Circle body;

    /**
     * Constructs a new BallImpl with the specified position, velocity, and radius.
     *
     * @param position the initial position of the ball
     * @param radius   the radius of the ball
     */
    public BallImpl(final Vector2D position, final double radius) {
        this.body = new Circle(position, radius);
        this.velocity = Vector2D.ZERO;

    }

    /**
     * Returns the current position of the ball.
     *
     * @return the position as a Vector2D
     */
    @Override
    public Vector2D getPosition() {
        return this.body.position();
    }

    /**
     * Returns the current velocity of the ball.
     *
     * @return the velocity as a Vector2D
     */
    @Override
    public Vector2D getVelocity() {
        return this.velocity;
    }

    /**
     * Returns the radius of the ball.
     *
     * @return the radius as a double
     */
    @Override
    public double getRadius() {
        return this.body.radius();
    }

    /**
     * Sets the position of the ball.
     *
     * @param position the new position as a Vector2D
     */
    @Override
    public void setPosition(final Vector2D position) {
        this.body = new Circle(position, this.body.radius());
    }

    /**
     * Sets the velocity of the ball.
     *
     * @param velocity the new velocity as a Vector2D
     */
    @Override
    public void setVelocity(final Vector2D velocity) {
        this.velocity = velocity;
    }

}
