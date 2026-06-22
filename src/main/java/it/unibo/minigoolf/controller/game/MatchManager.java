package it.unibo.minigoolf.controller.game;

import it.unibo.minigoolf.controller.maincontroller.MainControllerImpl;
import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.model.map.factories.MapSequence;
import it.unibo.minigoolf.model.save.SaveData;
import it.unibo.minigoolf.model.LeaderBoardManager;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Manages the lifecycle of matches within a map sequence.
 * Uses functional callbacks instead of storing collaborators directly,
 * (to avoid EI2 warnings).
 *
 * @author fedesparvo1-a11y
 */
public final class MatchManager {

    private final MapSequence mapSequence;
    private final List<String> playerNames;

    // Stops the game loop. 
    private final Runnable stopGame;

    // Starts the game loop and shows the game scene. 
    private final Runnable startGame;

    // Returns to the main menu. 
    private final Runnable goToMenu;

    // Rebuilds the game panel for a new match.
    private final Consumer<GameController> rebuildPanel;

    // Stores the scores, used in the leaderboard. 
    private final Map<String, Integer> globalScores = new LinkedHashMap<>();

    // Callback to show the summary-leaderboard after a map is completed.
    private final Consumer<Map<String, Integer>> showSummaryPanel;

    private GameController activeMatch;

    /**
     * Builds the match manager and wires save, restore and pause callbacks.
     * 
     * @param mapSequence    the ordered sequence of maps
     * @param playerNames    the player names for all matches
     * @param stopGame       callback to stop the game loop
     * @param startGame      callback to start the game loop and show the game scene
     * @param goToMenu       callback to return to the main menu
     * @param rebuildPanel   callback to rebuild the game panel with a new match
     * @param navController  navigation controller that owns the SaveController
     * @param showSummaryPanel callback to display the scores after a hole is completed
     */
    public MatchManager(
            final MapSequence mapSequence,
            final List<String> playerNames,
            final Runnable stopGame,
            final Runnable startGame,
            final Runnable goToMenu,
            final Consumer<GameController> rebuildPanel,
            final NavigationController navController,
            final Consumer<Map<String, Integer>> showSummaryPanel) {

        this.mapSequence = mapSequence;
        this.playerNames = List.copyOf(playerNames);
        this.stopGame = stopGame;
        this.startGame = startGame;
        this.goToMenu = goToMenu;
        this.activeMatch = buildMatch();
        this.rebuildPanel = rebuildPanel;
        rebuildPanel.accept(activeMatch);
        this.showSummaryPanel = showSummaryPanel;
        for (final String name : this.playerNames) {
            this.globalScores.put(name, 0);
        }
        // Register save/restore data on SaveController via NavigationController.
        navController.registerSnapshotSupplier(
            () -> activeMatch.createSaveData(
                String.valueOf(mapSequence.getCurrentIndex())));
        navController.registerRestoreCallback(this::restoreFromSaveData);
        // Prevent pausing while the ball is moving.
        navController.setPauseChecker(() -> !activeMatch.isBallMoving());
    }

    /**
     * Runs one tick of the active match in {@link MainControllerImpl}.
     *
     * @param deltaTime elapsed time since the last frame in seconds
     */
    public void tickActiveMatch(final double deltaTime) {
        activeMatch.updateTick(deltaTime);
    }

    /**
     * Resets the sequence to the first map and builds a new match.
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
     * Advances the map sequence to the saved index and rebuilds the match
     * with full state (player index, shots, ball position) restored.
     *
     * @param data the snapshot to restore
     */
    private void restoreFromSaveData(final SaveData data) {
        mapSequence.reset();
        final int targetIndex = Integer.parseInt(data.mapId());
        IntStream.range(0, targetIndex)
            .filter(i -> mapSequence.hasNext())
            .forEach(i -> mapSequence.advance());
        activeMatch = GameFactory.buildMatch(
            playerNames, mapSequence, this::onHoleCompleted,
            java.util.Optional.of(data));
        rebuildPanel.accept(activeMatch);
        startGame.run();
    }

    /**
     * Called when the ball enters the hole.
     * Gets the current scores.
     */
    private void onHoleCompleted() {
        stopGame.run();

        final Map<String, Integer> holeScores = activeMatch.getHoleScores();
        for (final Map.Entry<String, Integer> entry : holeScores.entrySet()) {
            globalScores.put(entry.getKey(), globalScores.get(entry.getKey()) + entry.getValue());
        }
        showSummaryPanel.accept(holeScores);
    }

    /**
     * Called when the map is completed, goes to the next "hole"
     * Advances to the next map if available, otherwise returns to the main menu.
     */
    public void advanceToNextHole() {
        if (mapSequence.hasNext()) {
            mapSequence.advance();
            activeMatch = buildMatch();
            rebuildPanel.accept(activeMatch);
            startGame.run();
        } else {
            final LeaderBoardManager leaderManager = new LeaderBoardManager();
            leaderManager.updateAndSaveScores(this.globalScores);
            goToMenu.run();
        }
    }
}
