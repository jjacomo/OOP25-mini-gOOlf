package it.unibo.minigoolf.controller.gamemapcontroller;

import java.util.List;

import it.unibo.minigoolf.controller.ballcontroller.BallController;
import it.unibo.minigoolf.controller.holecontroller.HoleController;
import it.unibo.minigoolf.controller.obstaclecontroller.ObstacleController;
import it.unibo.minigoolf.controller.surfacecontroller.SurfaceController;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.util.Vector2D;

/**
 * Controller for managing the game map and its interactions.
 * Delegates ball-related operations to {@link BallController}.
 * Delegates hole-related operations to {@link HoleController}.
 * Delegates obstacle-related operations to {@link ObstacleController}.
 * Delegates surface-related operations to {@link SurfaceController}.
 * 
 * @author jack
 */
public interface GameMapController {
    /**
     * Returns all surfaceControllers for surfaces on the game map.
     *
     * @return a list of all surfaceControllers
     */
    List<SurfaceController> getSurfaceControllers();

    /**
     * Returns the obstacle controller for managing obstacles on the game map.
     *
     * @return the obstacle controller
     */
    ObstacleController getObstacleController();

    /**
     * Returns the surface at the given position.
     *
     * @param position the position to query
     * @return the surface under the given position
     */
    Surface getSurfaceAt(Vector2D position);

    /**
     * Returns the controller for managing the ball.
     *
     * @return the ball controller
     */
    BallController getBallController();

    /**
     * Returns the controller for managing the hole.
     *
     * @return the hole controller
     */
    HoleController getHoleController();
}
