package it.unibo.minigoolf.controller;

/**
 * Manages the game loop for the minigolf application.
 *
 * @author fede and dani
 */

public interface MainController {

    /**
     * Starts the game loop and initializes the app.
     */
    void start();

    /**
     * Stops the game loop.
     */
    void stop();

    /**
     * Handles the transition from the menu to the actual game.
     * Starts the timer and switches the view.
     */
    void startGame();

    /**
     * Handles the transition from the menu to the newGamePanel: the n* and names of players are selected here.
     */
    void goToNewGameMenu();
}
