package it.unibo.minigoolf.controller.shot;

import it.unibo.minigoolf.util.Vector2D;

/**
 * Controller that coordinates shot input between the view and the game logic.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Enabling shot input when it is the player's turn.</li>
 *   <li>Consuming the pending shot each tick and forwarding it to the game loop.</li>
 *   <li>Notifying the view when the ball has stopped so input can be re-enabled.</li>
 * </ul>
 *
 * @author fede
 */
public interface ShotController {

    /**
     * Called each tick by the game loop.
     * Consumes a pending shot if available and applies it to the ball.
     *
     * @return true if a shot was fired this tick, false otherwise
     */
    boolean tick();

    /**
     * Called when the ball has stopped moving.
     * Re-enables shot input at the current ball position.
     *
     * @param ballPosition the ball centre in logical coordinates
     */
    void onBallStopped(Vector2D ballPosition);
}
