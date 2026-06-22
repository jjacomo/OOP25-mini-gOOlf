package it.unibo.minigoolf.controller.game;

import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.controller.shot.ShotView;
import it.unibo.minigoolf.model.logic.ShotState;
import it.unibo.minigoolf.model.save.SaveData;
import java.util.Map;

/**
 * Controller responsible for the lifecycle of a single match.
 * Manages shot input, physics, turn logic and ball-stop detection.
 *
 * @author fedesparvo1-a11y
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
     * Called by {@link it.unibo.minigoolf.view.mainwindow.MainWindow} after
     * constructing the {@link it.unibo.minigoolf.view.input.ShotViewPanel}.
     *
     * @param shotView the shot view interface
     */
    void setShotView(ShotView shotView);

    /**
     * Sets the view that will render the map elements.
     * Called by {@link it.unibo.minigoolf.view.mainwindow.MainWindow} after
     * constructing the {@link it.unibo.minigoolf.view.panels.MapPanel}.
     *
     * @param view the map elements view interface
     */
    void setMapElementsView(it.unibo.minigoolf.controller.gamemapcontroller.MapElementsView view);

    /**
     * Returns the name of the player whose turn it currently is.
     *
     * @return the current player's name
     */
    String getCurrentPlayerName();

    /**
     * Returns the number of shots taken by the current player on this hole.
     *
     * @return the current player's shot count
     */
    int getCurrentPlayerShots();

    /**
     * Returns the shot state for this match.
     * Used by {@link it.unibo.minigoolf.view.mainwindow.MainWindow} to build the
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

    /**
     * Catch a snapshot of the current
     * match state, ready to be used by
     * {@link it.unibo.minigoolf.model.save.SaveManager}.
     *
     * @param mapId the string identifier of the current map
     * @return an immutable snapshot of the match
     */
    SaveData createSaveData(String mapId);

    /**
     * Returns the scores for the current hole.
     * 
     * @return a map associating each player's name with the shots taken in this hole.
     */
    Map<String, Integer> getHoleScores();

    /**
     * Returns true if the ball is currently moving.
     * Used to prevent pausing while a shot is in progress.
     *
     * @return true if the ball is in motion
     */
    boolean isBallMoving();
}
