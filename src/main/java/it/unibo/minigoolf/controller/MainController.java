package it.unibo.minigoolf.controller;

import java.util.List;

/**
 * Manages the game loop for the minigolf application.
 *
 * @author dani
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
     * Starts a new match with given players names
     * @param playerNames
     */
    void startNewMatch(List<String> playerNames);
}
