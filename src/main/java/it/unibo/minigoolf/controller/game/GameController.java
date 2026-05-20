package it.unibo.minigoolf.controller.game;

import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.controller.shot.ShotView;
import it.unibo.minigoolf.model.logic.ShotState;

/**
 * Controller responsible for the lifecycle of a single match.
 * Manages shot input, physics, turn logic and ball-stop detection.
 *
 * @author fede
 */
public interface GameController {

    /**
     * Called once per frame by the main game loop.
     *
     * @param deltaTime elapsed time since the last frame in seconds
     */
    void updateTick(double deltaTime);

    /**
     * Sets the callback to invoke when the ball enters the hole.
     * Called by the main controller after construction.
     *
     * @param onHoleCompleted the action to run when the hole is completed
     */
    void setOnHoleCompleted(Runnable onHoleCompleted);

    /**
     * Sets the shot view that will be notified when the ball stops.
     * Called by {@link it.unibo.minigoolf.view.MainWindow} after
     * constructing the {@link it.unibo.minigoolf.view.input.ShotViewPanel}.
     *
     * @param shotView the shot view interface
     */
    void setShotView(ShotView shotView);

    /**
     * Returns the display name of the player whose turn it currently is.
     *
     * @return the current player's display name
     */
    String getCurrentPlayerName();

    /**
     * Returns the shot state for this match.
     * Used by {@link it.unibo.minigoolf.view.MainWindow} to build the
     * {@link it.unibo.minigoolf.view.input.ShotViewPanel}.
     *
     * @return the shot state
     */
    ShotState getShotState();

    /**
     * Returns the game map controller for this match.
     * Used by the view to render surfaces, obstacles and the ball.
     *
     * @return the game map controller
     */
    GameMapController getGameMapController();
}
