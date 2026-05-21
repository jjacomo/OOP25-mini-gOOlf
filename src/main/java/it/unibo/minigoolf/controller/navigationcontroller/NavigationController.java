package it.unibo.minigoolf.controller.navigationcontroller;

import java.util.List;

import it.unibo.minigoolf.controller.MainController;
import it.unibo.minigoolf.view.MainWindow;

/**
 * Controller for navigation between panels.
 */
public final class NavigationController {

    private MainWindow mainWindow;
    private final MainController mainController;

    /**
     * @param mainController the main controller
     */
    public NavigationController(final MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * @param mainWindow the main window
     */
    public void setMainWindow(final MainWindow mainWindow) {
        this.mainWindow = mainWindow;
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
     * Handles the transition from the menu, to the new game menu and then to the actual game.
     * Passes the names to the logic and starts everything.
     * * @param playerNames list of the players names
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