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
}
