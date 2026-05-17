package it.unibo.minigoolf.controller.gamemapcontroller;

import java.util.List;

import it.unibo.minigoolf.controller.ballcontroller.BallController;
import it.unibo.minigoolf.controller.holecontroller.HoleController;
import it.unibo.minigoolf.model.obstacles.Obstacle;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.util.Vector2D;

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

    /**
     * Returns all obstacles on the game map.
     *
     * @return a list of all obstacles
     */
    List<Obstacle> getObstacles();

    /**
     * Returns the surface at the given position.
     *
     * @param position the position to query
     * @return the surface under the given position
     */
    Surface getSurfaceAt(Vector2D position);

    // Hole getHole();

    /**
     * Returns the controller for managing the ball.
     *
     * @return the ball controller
     */
    BallController getBallController();

    /**
     * Returns the hole on the game map.
     *
     * @return the hole
     */
    HoleController getHoleController();
}
