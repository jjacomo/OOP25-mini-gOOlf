package it.unibo.minigoolf.controller.game;

import it.unibo.minigoolf.controller.shot.ShotView;
import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;

/**
 * Controller responsible for the lifecycle of a single match.
 * Manages shot input, physics, turn logic and ball-stop detection.
 * Called each frame by {@link it.unibo.minigoolf.controller.MainControllerImpl}.
 *
 * <p>Intentionally exposes only what the view and the main controller
 * strictly need — internal objects like {@code GameState}, {@code ShotState},
 * {@code GameMapController} and {@code PhysicsController} are encapsulated.</p>
 *
 * @author fede
 */
public interface GameController {

    /**
     * Called once per frame by the main game loop.
     * Processes shot input, updates physics and detects when the ball stops.
     *
     * @param deltaTime elapsed time since the last frame in seconds
     */
    void updateTick(double deltaTime);

    /**
     * Sets the shot view that will be notified when the ball stops.
     * Must be called once after the view has been constructed,
     * before the first {@link #updateTick(double)} call.
     *
     * @param shotView the shot view interface
     */
    void setShotView(ShotView shotView);

    /**
     * Returns the shot state for this match.
     * Exposed only for constructing the shot indicator panel in the view.
     *
     * @return the shot state
     */
    it.unibo.minigoolf.model.logic.ShotState getShotState();

    /**
     * Returns the game map controller for this match.
     * Exposed only for constructing the map panel in the view.
     *
     * @return the game map controller
     */
    GameMapController getGameMapController();

    /**
     * Returns the display name of the player whose turn it currently is.
     * Allows the view to show turn information without knowing {@code GameState}.
     *
     * @return the current player's display name
     */
    String getCurrentPlayerName();

    /**
     * Returns the shot view interface wired to this controller.
     * Used by {@link it.unibo.minigoolf.view.MainWindow} to expose
     * the view to the main controller without leaking implementation details.
     *
     * @return the shot view
     */
    ShotView getShotView();
}
