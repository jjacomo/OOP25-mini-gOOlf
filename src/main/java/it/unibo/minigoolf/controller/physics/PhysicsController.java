package it.unibo.minigoolf.controller.physics;

import it.unibo.minigoolf.model.physics.velocity.BallVelocityStrategy;

/**
 * Controller for managing the physics of the mini-gOOlf game, including updating
 * the ball's velocity and position based on the surfaces it interacts with.
 * Delegates the actual physics calculations to {@link it.unibo.minigoolf.model.physics.PhysicsEngine}.
 * 
 * @author jack
 */
public interface PhysicsController {
    /**
     * Updates the physics state of the game, including the ball's position and
     * velocity, based on the elapsed time since the last update.
     *
     * @param deltaTime the time in seconds since the last update
     */
    void update(double deltaTime);

    /**
     * Sets the strategy used to update the ball's velocity.
     *
     * @param strategy the BallVelocityStrategy to use for velocity updates
     */
    void setVelocityStrategy(BallVelocityStrategy strategy);
}
