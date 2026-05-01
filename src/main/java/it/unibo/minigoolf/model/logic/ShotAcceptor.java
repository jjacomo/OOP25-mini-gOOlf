package it.unibo.minigoolf.model.logic;

import it.unibo.minigoolf.util.Vector2D;

/**
 * Minimal interface for submitting a shot from the view layer.
 * Avoids exposing the full {@link GameState} to view components.
 */
@FunctionalInterface
public interface ShotAcceptor {

    /**
     * Submits a shot vector to the game logic.
     *
     * @param shot the direction and power vector of the shot
     */
    void setPendingShot(Vector2D shot);
}
