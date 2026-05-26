package it.unibo.minigoolf.controller.navigationcontroller;

import it.unibo.minigoolf.controller.MainController;
import it.unibo.minigoolf.controller.save.SaveController;
import it.unibo.minigoolf.model.save.SaveManager;
import it.unibo.minigoolf.view.MainWindow;

import java.util.List;

/**
 * Controller for navigation between panels.
 * Also owns the {@link SaveController} since it lives for the full
 * application lifetime, allowing save/load before any match is started.
 */
public final class NavigationController {

    private MainWindow mainWindow;
    private final MainController mainController;
    private final SaveController saveController;

    /**
     * @param mainController the main controller
     */
    public NavigationController(final MainController mainController) {
        this.mainController = mainController;
        this.saveController = new SaveController(new SaveManager());
    }

    /**
     * Returns the save controller so {@link it.unibo.minigoolf.controller.game.MatchManager}
     * can register its snapshot and restore callbacks.
     *
     * @return the save controller
     */
    public SaveController getSaveController() {
        return saveController;
    }

    /**
     * @param mainWindow the main window
     */
    public void setMainWindow(final MainWindow mainWindow) {
        this.mainWindow = mainWindow;
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
     * Saves the current match state to disk.
     * Called by the pause panel when the player chooses to save before quitting.
     */
    public void saveGame() {
        saveController.save();
    }

    /**
     * Loads the saved match and starts it.
     * Called by the menu panel when the player chooses to load.
     */
    public void loadGame() {
        saveController.load();
    }

    /**
     * Handles the transition from the menu to the NewGamePanel.
     */
    public void goToNewGameMenu() {
        this.mainWindow.showScene("NEW_GAME");
    }

    /**
     * Handles the transition to the MenuPanel.
     */
    public void goToMainMenu() {
        this.mainWindow.showScene("MENU");
    }

    /**
     * Handles the transition from the new game menu to the actual game.
     * Passes the names to the logic and starts everything.
     *
     * @param playerNames list of the players names
     */
    public void setupMatchAndStart(final List<String> playerNames) {
        this.mainController.startNewMatch(playerNames);
        this.showGameScene();
    }

    /**
     * Shows the game scene and starts the game loop.
     * Used both when starting a new match and when advancing to the next hole.
     */
    public void showGameScene() {
        this.mainWindow.showScene("GAME");
        this.mainController.start();
    }

    /**
     * Handles the transition from the game to the pause menu.
     */
    public void pauseGame() {
        this.mainController.stop();
        this.mainWindow.getGlassPane().setVisible(true);
    }

    /**
     * Handles the transition from the pause to the game.
     */
    public void resumeGame() {
        this.mainWindow.getGlassPane().setVisible(false);
        this.mainController.start();
    }

    /**
     * Handles the transition from the pause menu to the main menu.
     */
    public void quitToMenu() {
        this.mainWindow.getGlassPane().setVisible(false);
        this.goToMainMenu();
    }
}
