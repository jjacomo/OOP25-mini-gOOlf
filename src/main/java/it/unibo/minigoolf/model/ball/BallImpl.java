package it.unibo.minigoolf.model.ball;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Circle;

/**
 * Implementation of the Ball interface representing a golf ball with position,
 * velocity, and radius.
 */
public final class BallImpl implements Ball {
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

    @Override
    public Vector2D getPosition() {
        return this.body.position();
    }

    @Override
    public Vector2D getVelocity() {
        return this.velocity;
    }

    @Override
    public double getRadius() {
        return this.body.radius();
    }

    @Override
    public void setPosition(final Vector2D position) {
        this.body = new Circle(position, this.body.radius());
    }

    @Override
    public void setVelocity(final Vector2D velocity) {
        this.velocity = velocity;
    }

}
