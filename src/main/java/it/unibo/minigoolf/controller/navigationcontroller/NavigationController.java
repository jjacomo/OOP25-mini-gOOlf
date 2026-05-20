package it.unibo.minigoolf.controller.navigationcontroller;

import it.unibo.minigoolf.controller.MainController;
import it.unibo.minigoolf.view.MainWindow;

/**
 * Controller for navigation between panels.
 */
public final class NavigationController {

    private MainWindow mainWindow;
    private final MainController mainController;

    /** Called when a new match must be created from scratch. */
    private Runnable resetMatch = () -> { };

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
     * Sets the callback to invoke when a new match needs to be created.
     * Called by {@link it.unibo.minigoolf.controller.MainControllerImpl}.
     *
     * @param resetMatch the action to run to reset the match
     */
    public void setResetMatch(final Runnable resetMatch) {
        this.resetMatch = resetMatch;
    }

    /**
     * Handles the transition from the menu to the NewGamePanel.
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
     * Resets the match, starts the timer and switches the view.
     */
    public void startGame() {
        resetMatch.run();
        this.mainController.start();
        this.mainWindow.showScene("GAME");
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
     * TODO: Reset the game status!
     */
    public void quitToMenu() {
        this.mainWindow.getGlassPane().setVisible(false);
        this.mainWindow.showScene("MENU");
    }
}
