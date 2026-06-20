package it.unibo.minigoolf.model.physics.velocity;

import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.model.surfaces.Surface;

/**
 * Interface representing a strategy for updating the velocity of a ball based on
 * the surface it is currently on and the time elapsed since the last update.
 * This allows for different implementations of velocity updates, such as
 * applying friction or other physics effects depending on the surface
 * properties.
 */
@FunctionalInterface
public interface BallVelocityStrategy {
    /**
     * Updates the velocity of the ball based on the surface it is currently on and
     * the time elapsed since the last update.
     * This method should be called every frame to ensure that the ball's velocity
     * is correctly updated according to the physics of the game.
     * 
     * @param ball      the ball whose velocity is to be updated
     * @param surface   the surface on which the ball is currently located, which
     *                  may affect its velocity.
     * @param deltaTime the time elapsed since the last update, used to calculate
     *                  the change in velocity based on acceleration and other
     *                  factors
     */
    void updateVelocity(Ball ball, Surface surface, double deltaTime);
}
