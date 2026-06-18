package it.unibo.minigoolf.controller.maincontroller;

import java.util.List;

/**
 * Manages the game loop for the minigolf application.
 *
 * @author dbakko, fedesparvo1-a11y, jjacomo
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
     * Starts a new match with given players names.
     * 
     * @param playerNames player names
     */
    void startNewMatch(List<String> playerNames);

    /**
     * Skips the current map and advances to the next one.
     */
    void skipMap();
}
