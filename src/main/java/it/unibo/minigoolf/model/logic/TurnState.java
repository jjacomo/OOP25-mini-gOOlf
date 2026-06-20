package it.unibo.minigoolf.model.logic;

import it.unibo.minigoolf.util.Vector2D;

import java.util.Optional;

/**
 * Minimal interface exposing only the turn-management operations needed
 * by the shot and game controllers.
 * Created this interface instead of the full {@link GameState} to avoid
 * EI2 warnings and to keep the controllers decoupled from the
 * full game-state object.
 *
 * @author fede
 */
public interface TurnState {

    /**
     * Returns true if the ball is currently moving.
     *
     * @return true if ball is moving
     */
    boolean isBallMoving();

    /**
     * Called by the shot controller when a valid shot has been confirmed.
     *
     * @param shot the direction/power vector of the intended shot
     */
    void setPendingShot(Vector2D shot);

    /**
     * Called by the game loop each tick.
     *
     * @return an Optional containing the shot vector, or empty if no shot this tick
     */
    Optional<Vector2D> update();

    /**
     * Called by the physics layer once the ball has stopped moving.
     */
    void onBallStopped();
}
