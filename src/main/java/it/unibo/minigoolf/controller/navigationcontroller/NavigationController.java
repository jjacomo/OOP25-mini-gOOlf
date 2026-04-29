package it.unibo.minigoolf.controller.navigationcontroller;

import it.unibo.minigoolf.controller.MainController;
import it.unibo.minigoolf.view.MainWindow;

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
}