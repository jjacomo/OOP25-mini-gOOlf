package it.unibo.minigoolf.controller.gamemapcontroller;

import java.util.List;

import it.unibo.minigoolf.controller.ballcontroller.BallController;
import it.unibo.minigoolf.model.surfaces.Surface;

/**
 * Controller for managing the game map and its interactions.
 * Delegates ball-related operations to {@link BallController}.
 * 
 * @author jack
 */
public interface GameMapController {
    /**
     * Returns all surfaces on the game map.
     * 
     * @return a list of all surfaces
     */
    List<Surface> getSurfaces();

    // List<Obstacle> getObstacles();
    // Hole getHole();
    
    /**
     * Returns the controller for managing the ball.
     * 
     * @return the ball controller
     */
    BallController getBallController();

    // altri metodi per gli ostacoli
}
