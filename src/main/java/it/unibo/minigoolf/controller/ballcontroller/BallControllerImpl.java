package it.unibo.minigoolf.controller.ballcontroller;

import java.awt.Color;

import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Circle;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Implementation of {@link BallController}.
 * Manages ball state and coordinates updates to the model.
 *
 * @author jack
 */
public final class BallControllerImpl implements BallController {

    private final Ball ball;

    /**
     * Creates a new BallController for the given ball.
     *
     * <p>
     * The shared reference to {@code ball} is intentional: this controller
     * is responsible for mutating the ball's state (position and velocity)
     * via {@link #updatePosition} and {@link #updateVelocity}. A defensive
     * copy would break synchronisation with the model.
     * </p>
     *
     * @param ball the ball model to control
     */
    @SuppressWarnings("EI_EXPOSE_REP2") // TODO: e' necessario?
    public BallControllerImpl(final Ball ball) {
        this.ball = ball;
    }

    /** {@inheritDoc} */
    @Override
    public Shape getBallShape() {
        return new Circle(ball.getPosition(), ball.getRadius(), Color.WHITE);
    }

    /** {@inheritDoc} */
    @Override
    public Vector2D getPosition() {
        return ball.getPosition();
    }

    /** {@inheritDoc} */
    @Override
    public Vector2D getVelocity() {
        return ball.getVelocity();
    }

    /** {@inheritDoc} */
    @Override
    public double getRadius() {
        return ball.getRadius();
    }

    /** {@inheritDoc} */
    @Override
    public void updatePosition(final Vector2D position) {
        ball.setPosition(position);
    }

    /** {@inheritDoc} */
    @Override
    public void updateVelocity(final Vector2D velocity) {
        ball.setVelocity(velocity);
    }
}
