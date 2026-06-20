package it.unibo.minigoolf.controller.gamemapcontroller;

import java.util.List;

import it.unibo.minigoolf.controller.ballcontroller.BallController;
import it.unibo.minigoolf.controller.ballcontroller.BallControllerImpl;
import it.unibo.minigoolf.controller.holecontroller.HoleController;
import it.unibo.minigoolf.controller.holecontroller.HoleControllerImpl;
import it.unibo.minigoolf.controller.surfacecontroller.SurfaceController;
import it.unibo.minigoolf.controller.surfacecontroller.SurfaceControllerImpl;
import it.unibo.minigoolf.controller.obstaclecontroller.ObstacleController;
import it.unibo.minigoolf.controller.obstaclecontroller.ObstacleControllerImpl;
import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.util.Vector2D;

/**
 * Implementation of {@link GameMapController}.
 * Manages the game map and provides access to surfaces, obstacles, hole and
 * ball controllers.
 *
 * @author jack
 */
public final class GameMapControllerImpl implements GameMapController {
    private final GameMap map;
    private final BallController ballController;
    private final HoleController holeController;
    private final List<SurfaceController> surfaceControllers;
    private final ObstacleController obstacleController;

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
        this.obstacleController = new ObstacleControllerImpl(map.getObstacles());
        this.surfaceControllers = map.getSurfaces().stream()
                .map(SurfaceControllerImpl::new)
                .map(sc -> (SurfaceController) sc)
                .toList();
    }

    @Override
    public List<SurfaceController> getSurfaceControllers() {
        return List.copyOf(surfaceControllers);
    }

    @Override
    public Surface getSurfaceAt(final Vector2D position) {
        return map.getSurfaceAt(position);
    }

    @Override
    public BallController getBallController() {
        return ballController;
    }

    @Override
    public ObstacleController getObstacleController() {
        return obstacleController;
    }

    @Override
    public HoleController getHoleController() {
        return holeController;
    }

}
