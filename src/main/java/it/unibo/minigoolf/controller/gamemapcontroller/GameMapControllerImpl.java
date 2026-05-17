package it.unibo.minigoolf.controller.gamemapcontroller;

import java.util.List;

import it.unibo.minigoolf.controller.ballcontroller.BallController;
import it.unibo.minigoolf.controller.ballcontroller.BallControllerImpl;
import it.unibo.minigoolf.controller.holecontroller.HoleController;
import it.unibo.minigoolf.controller.holecontroller.HoleControllerImpl;
import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.model.obstacles.Obstacle;
import it.unibo.minigoolf.util.Vector2D;

/**
 * Implementation of {@link GameMapController}.
 * Manages the game map and provides access to surfaces and ball controller.
 *
 * @author jack
 */
public final class GameMapControllerImpl implements GameMapController {
    private final GameMap map;
    private final BallController ballController;
    private final HoleController holeController;

    /**
     * Creates a new GameMapController for the given game map.
     * Initializes the ball controller with the map's ball.
     *
     * @param map the game map to control
     */
    public GameMapControllerImpl(final GameMap map) {
        this.map = map;
        this.ballController = new BallControllerImpl(map.getBall());
        this.holeController = new HoleControllerImpl(map.getHole());
    }

    /**
     * Returns a list of all surfaces in the game map.
     *
     * @return a list of surfaces
     */
    @Override
    public List<Surface> getSurfaces() {
        return map.getSurfaces();
    }

    /**
     * Returns the surface at the given position.
     *
     * @param position the position to query
     * @return the surface under the given position
     */
    @Override
    public Surface getSurfaceAt(final Vector2D position) {
        return map.getSurfaceAt(position);
    }

    /**
     * Returns the ball controller for managing ball interactions.
     *
     * @return the ball controller
     */
    @Override
    public BallController getBallController() {
        return ballController;
    }

    /**
     * Returns a list of all surfaces in the game map.
     *
     * @return a list of surfaces
     */
    @Override
    public List<Obstacle> getObstacles() {
        return map.getObstacles();
    }

    /**
     * Returns the hole controller for managing hole interactions.
     *
     * @return the hole controller
     */
    @Override
    public HoleController getHoleController() {
        return holeController;
    }

}
