package it.unibo.minigoolf.controller.game;

import it.unibo.minigoolf.model.map.factories.MapSequence;

import java.util.List;
import java.util.function.Consumer;

/**
 * Manages the lifecycle of matches within a map sequence.
 * Uses functional callbacks instead of storing collaborators directly,
 * avoiding EI2 warnings and keeping coupling minimal.
 *
 * @author fede 
 */
public final class MatchManager {

    private final MapSequence mapSequence;
    private final List<String> playerNames;

    /** {@code mainController::stop} */
    private final Runnable stopGame;

    /** {@code navigationController::startGame} */
    private final Runnable startGame;

    /** {@code navigationController::goToMainMenu} */
    private final Runnable goToMenu;

    /**
     * Rebuilds and shows the game panel for a new match.
     * Wraps {@code mainWindow::rebuildGamePanel}.
     */
    private final Consumer<GameController> rebuildPanel;

    private GameController activeMatch;

    /**
     * @param mapSequence  the ordered sequence of maps
     * @param playerNames  the player names for all matches
     * @param stopGame     callback to stop the game loop
     * @param startGame    callback to start the game loop and show the game scene
     * @param goToMenu     callback to return to the main menu
     * @param rebuildPanel callback to rebuild the game panel with a new match
     */
    public MatchManager(
            final MapSequence mapSequence,
            final List<String> playerNames,
            final Runnable stopGame,
            final Runnable startGame,
            final Runnable goToMenu,
            final Consumer<GameController> rebuildPanel) {
        this.mapSequence = mapSequence;
        this.playerNames = List.copyOf(playerNames);
        this.stopGame = stopGame;
        this.startGame = startGame;
        this.goToMenu = goToMenu;
        this.rebuildPanel = rebuildPanel;
        this.activeMatch = buildMatch();
    }

    /**
     * Returns the currently active match controller.
     *
     * @return the active {@link GameController}
     */
    public GameController getActiveMatch() {
        return activeMatch;
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
