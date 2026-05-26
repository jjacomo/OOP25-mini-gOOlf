package it.unibo.minigoolf.controller.game;

import it.unibo.minigoolf.controller.save.SaveController;
import it.unibo.minigoolf.model.map.factories.MapSequence;
import it.unibo.minigoolf.model.save.SaveData;

import java.util.List;
import java.util.function.Consumer;

/**
 * Manages the lifecycle of matches within a map sequence.
 * Uses functional callbacks instead of storing collaborators directly,
 * avoiding EI2 warnings and keeping coupling minimal.
 *
 * @author fede and dani
 */
public final class MatchManager {

    private final MapSequence mapSequence;
    private final List<String> playerNames;

    /** Stops the game loop. */
    private final Runnable stopGame;

    /** Starts the game loop and shows the game scene. */
    private final Runnable startGame;

    /** Returns to the main menu. */
    private final Runnable goToMenu;

    /** Rebuilds the game panel for a new match. */
    private final Consumer<GameController> rebuildPanel;

    private GameController activeMatch;

    /**
     * @param mapSequence    the ordered sequence of maps
     * @param playerNames    the player names for all matches
     * @param stopGame       callback to stop the game loop
     * @param startGame      callback to start the game loop and show the game scene
     * @param goToMenu       callback to return to the main menu
     * @param rebuildPanel   callback to rebuild the game panel with a new match
     * @param saveController handles persistence — MatchManager registers its
     *                       snapshot and restore callbacks on it
     */
    public MatchManager(
            final MapSequence mapSequence,
            final List<String> playerNames,
            final Runnable stopGame,
            final Runnable startGame,
            final Runnable goToMenu,
            final Consumer<GameController> rebuildPanel,
            final SaveController saveController) {
        this.mapSequence = mapSequence;
        this.playerNames = List.copyOf(playerNames);
        this.stopGame = stopGame;
        this.startGame = startGame;
        this.goToMenu = goToMenu;
        this.rebuildPanel = rebuildPanel;
        this.activeMatch = buildMatch();
        rebuildPanel.accept(activeMatch);
        // Register save/restore behaviors on SaveController — no field stored.
        saveController.setSnapshotSupplier(
            () -> activeMatch.createSaveData(
                String.valueOf(mapSequence.getCurrentIndex())));
        saveController.setRestoreCallback(this::restoreFromSaveData);    }

    /**
     * Runs one tick of the active match.
     *
     * @param deltaTime elapsed time since the last frame in seconds
     */
    public void tickActiveMatch(final double deltaTime) {
        activeMatch.updateTick(deltaTime);
    }

    /**
     * Resets the sequence to the first map and builds a fresh match.
     * Called when the player starts a new game from the main menu.
     */
    public void reset() {
        mapSequence.reset();
        activeMatch = buildMatch();
        rebuildPanel.accept(activeMatch);
    }

    /**
     * Builds a new match for the current map and wires the hole-completed callback.
     *
     * @return a wired {@link GameController}
     */
    private GameController buildMatch() {
        return GameFactory.buildMatch(playerNames, mapSequence, this::onHoleCompleted);
    }

    /**
     * Restores a match from a {@link SaveData} snapshot.
     * Advances the map sequence to the saved index and rebuilds the match.
     *
     * @param data the snapshot to restore
     */
    private void restoreFromSaveData(final SaveData data) {
        mapSequence.reset();
        while (mapSequence.getCurrentIndex() < Integer.parseInt(data.mapId())
                && mapSequence.hasNext()) {
            mapSequence.advance();
        }
        activeMatch = buildMatch();
        rebuildPanel.accept(activeMatch);
        startGame.run();
    }

    /**
     * Called when the ball enters the hole.
     * Advances to the next map if available, otherwise returns to the main menu.
     */
    private void onHoleCompleted() {
        stopGame.run();
        if (mapSequence.hasNext()) {
            mapSequence.advance();
            activeMatch = buildMatch();
            rebuildPanel.accept(activeMatch);
            startGame.run();
        } else {
            goToMenu.run();
        }
    }
}
