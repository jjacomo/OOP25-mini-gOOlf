package it.unibo.minigoolf.controller.shot;

import it.unibo.minigoolf.util.Vector2D;

/**
 * Controller that coordinates shot input between the view and the game logic.
 *
 * @author fedesparvo1-a11y
 */
public interface ShotController {

    /**
     * Called each tick by the game loop.
     *
     * @return true if a shot was fired this tick, false otherwise
     */
    boolean tick();

    /**
     * Called when the ball has stopped moving.
     *
     * @param ballPosition the ball centre in logical coordinates
     */
    void onBallStopped(Vector2D ballPosition);
}
