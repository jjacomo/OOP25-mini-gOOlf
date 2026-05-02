package it.unibo.minigoolf.controller.navigationcontroller;

import it.unibo.minigoolf.controller.MainController;
import it.unibo.minigoolf.view.MainWindow;

/**
 * Controller for navigation between panels.
 */
public final class NavigationController {
    private MainWindow mainWindow;
    private final MainController mainController;

    public NavigationController(final MainController mainController) {
        this.mainController = mainController;
    }

    public void setMainWindow(final MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * Handles the transition from the menu to the NewGamePanel: the n* and names of players are selected here.
     */
    public void goToNewGameMenu() {
        this.mainWindow.showScene("NEW_GAME");
    }

    /**
     * TODO: Handles the transition from the PauseMenu to the MenuPanel.
     */
    public void goToMainMenu() {
        this.mainWindow.showScene("MENU");
    }

    /**
     * Handles the transition from the menu to the actual game.
     * Starts the timer and switches the view.
     */
    public void startGame() {
        this.mainController.start(); 
        this.mainWindow.showScene("GAME");
    }
    
    /**
     * Handles the transition from the game to the pause menu.
     * Stops the timer and switches the view.
     */
    public void pauseGame() {
        this.mainController.stop(); 
        this.mainWindow.getGlassPane().setVisible(true);
    }
    /**
     * Handles the transition from the pause to the game.
     * Starts the timer and switches the view.
     */
    public void resumeGame() {
    this.mainWindow.getGlassPane().setVisible(false);
    this.mainController.start();
    }
    /**
     * Handles the transition from the pause menu to the main menu.
     * Starts the timer and switches the view.
     * TODO: Reset the game status!
     */
    public void quitToMenu() {
    this.mainWindow.getGlassPane().setVisible(false);
    this.mainWindow.showScene("MENU");
    }
}
