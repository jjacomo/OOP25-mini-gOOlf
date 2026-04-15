package it.unibo.minigoolf.view.input;

import it.unibo.minigoolf.util.Vec2D;

/**
 * Minimal interface through which the shot-input view communicates a confirmed
 * shot to the rest of the application.
 * It only knows it can deliver a shot vector, nothing more.
 *
 * @author fede
 */
public interface ShotReceiver {

    /**
     * Called when the player has confirmed a shot.
     *
     * @param shot the direction and power vector of the shot
     */
    void setPendingShot(Vec2D shot);
}
