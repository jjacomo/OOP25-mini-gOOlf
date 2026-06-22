package it.unibo.minigoolf.controller.navigationcontroller;

import it.unibo.minigoolf.controller.maincontroller.MainController;
import it.unibo.minigoolf.controller.save.SaveController;
import it.unibo.minigoolf.model.save.SaveData;
import it.unibo.minigoolf.model.save.SaveManager;
import it.unibo.minigoolf.util.AudioManager;
import it.unibo.minigoolf.view.mainwindow.MainWindow;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Controller for navigation between panels.
 * Also owns the {@link SaveController} since it lives for the full
 * application lifetime, allowing save/load before any match is started.
 * 
 * @author dbakko, fedesparvo1-a11y
 */
public final class NavigationController {

    // Stored as a callback to avoid EI2. 
    private final Runnable showMenuCallback;
    private final Runnable showGameCallback;
    private final Runnable showNewGameCallback;
    private final Runnable pauseWindowCallback;
    private final Runnable resumeWindowCallback;
    private final Runnable showLeaderboardCallback;
    private final Consumer<java.util.Map<String, Integer>> updateLeaderboardCallback;

    private final MainController mainController;
    private final SaveController saveController;

    private final AudioManager audioManager;

    // Returns true if the ball is not moving and the game can be paused. 
    private BooleanSupplier pauseChecker = () -> true;

    /**
     * Constructs the navigation controller and extracts panel transition callbacks from the main window.
     * 
     * @param mainController the main controller
     * @param mainWindow     the main application window
     */
    public NavigationController(final MainController mainController,
            final MainWindow mainWindow) {
        this.mainController = mainController;
        this.saveController = new SaveController(new SaveManager());
        this.audioManager = new AudioManager();
        // Extract only the needed behaviors from mainWindow
        this.showMenuCallback = () -> mainWindow.showScene("MENU");
        this.showGameCallback = () -> mainWindow.showScene("GAME");
        this.showNewGameCallback = () -> mainWindow.showScene("NEW_GAME");
        this.pauseWindowCallback = () -> mainWindow.getGlassPane().setVisible(true);
        this.resumeWindowCallback = () -> mainWindow.getGlassPane().setVisible(false);
        this.showLeaderboardCallback = () -> mainWindow.showScene("LEADERBOARD");
        this.updateLeaderboardCallback = mainWindow::updateLeaderboard;
        this.audioManager.playMenuMusic();
    }

    /**
     * Registers the snapshot supplier on the save controller.
     * Called by {@link it.unibo.minigoolf.controller.game.MatchManager}.
     *
     * @param supplier supplies the current match snapshot
     */
    public void registerSnapshotSupplier(final Supplier<SaveData> supplier) {
        saveController.setSnapshotSupplier(supplier);
    }

    /**
     * Registers the restore callback on the save controller.
     * Called by {@link it.unibo.minigoolf.controller.game.MatchManager}.
     *
     * @param callback receives the loaded snapshot and starts the match
     */
    public void registerRestoreCallback(final Consumer<SaveData> callback) {
        saveController.setRestoreCallback(callback);
    }

    /**
     * Sets the supplier that checks whether the game can be paused.
     * Called by {@link it.unibo.minigoolf.controller.game.MatchManager}
     * when a match is active.
     *
     * @param checker returns true if the ball is not moving
     */
    public void setPauseChecker(final BooleanSupplier checker) {
        this.pauseChecker = checker;
    }

    /**
     * Returns true if a save file is available to load.
     *
     * @return true if a save exists
     */
    public boolean hasSave() {
        return saveController.hasSave();
    }

    /**
     * Saves the current match state.
     */
    public void saveGame() {
        saveController.save();
    }

    /**
     * Loads the saved match and starts it.
     */
    public void loadGame() {
        saveController.load();
    }

    /**
     * Handles the transition from the menu to the NewGamePanel.
     */
    public void goToNewGameMenu() {
        showNewGameCallback.run();
    }

    /**
     * Handles the transition to the MenuPanel.
     */
    public void goToMainMenu() {
        this.audioManager.playMenuMusic();
        showMenuCallback.run();
    }

    /**
     * Handles the transition from the new game menu to the actual game.
     *
     * @param playerNames list of the players names
     */
    public void setupMatchAndStart(final List<String> playerNames) {
        this.mainController.startNewMatch(playerNames);
        this.showGameScene();
    }

    /**
     * Shows the game scene and starts the game loop.
     */
    public void showGameScene() {
        this.audioManager.playGameMusic();
        showGameCallback.run();
        this.mainController.start();
    }

    /**
     * Handles the transition from the game to the pause menu.
     * Does nothing if the ball is currently moving.
     */
    public void pauseGame() {
        if (!pauseChecker.getAsBoolean()) {
            return;
        }
        this.mainController.stop();
        pauseWindowCallback.run();
    }

    /**
     * Handles the transition from the pause to the game.
     */
    public void resumeGame() {
        resumeWindowCallback.run();
        this.mainController.start();
    }

    /**
     * Handles the transition from the pause menu to the main menu.
     */
    public void quitToMenu() {
        resumeWindowCallback.run();
        this.goToMainMenu();
    }

    /**
     * Handles the transition from the pause menu to the leaderboard.
     */
    public void goToLeaderBoard() {
        final it.unibo.minigoolf.model.LeaderBoardManager leaderManager = 
                new it.unibo.minigoolf.model.LeaderBoardManager();
        final java.util.Map<String, Integer> bestScores = leaderManager.loadBestScores();
        this.updateLeaderboardCallback.accept(bestScores);
        this.showLeaderboardCallback.run();
    }

    /**
     * Closes the pause menu and skips to the next map.
     */
    public void skipCurrentMap() {
        this.resumeWindowCallback.run();
        this.mainController.skipMap();
    }
}
