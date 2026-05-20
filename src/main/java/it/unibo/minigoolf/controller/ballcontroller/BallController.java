package it.unibo.minigoolf.controller.ballcontroller;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Controller for managing the ball and its interactions.
 * Handles ball state updates like position and velocity.
 *
 * @author jack
 */
public interface BallController {

    /**
     * Returns the shape of the ball for rendering.
     *
     * @return the shape of the ball
     */
    Shape getBallShape();

    /**
     * Returns the current position of the ball in logical coordinates.
     *
     * @return the ball position (top-left corner of bounding box)
     */
    Vector2D getPosition();

    /**
     * Returns the current velocity of the ball in logical coordinates.
     *
     * @return the ball velocity
     */
    Vector2D getVelocity();

    /**
     * Returns the radius of the ball in logical coordinates.
     *
     * @return the ball radius
     */
    double getRadius();

    /**
     * Updates the position of the ball.
     *
     * @param position the new position of the ball
     */
    void updatePosition(Vector2D position);

    /**
     * Updates the velocity of the ball.
     *
     * @param velocity the new velocity of the ball
     */
    void updateVelocity(Vector2D velocity);

    /**
     * Checks if the ball is currently moving.
     *
     * @return true if the ball is moving, false otherwise
     */
    boolean isBallMoving();
}
