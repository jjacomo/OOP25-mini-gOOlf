package it.unibo.minigoolf.controller.obstaclecontroller;

import it.unibo.minigoolf.model.obstacles.Obstacle;

import java.util.List;

/**
 * Implementation of {@link ObstacleController}.
 */
public final class ObstacleControllerImpl implements ObstacleController {

    private final List<Obstacle> obstacles;

    /**
     * Creates a new ObstacleController with an initial list of obstacles.
     *
     * @author Mattia
     * 
     * @param obstacles the initial list of obstacles to manage
     * 
     */
    public ObstacleControllerImpl(final List<Obstacle> obstacles) {
        this.obstacles = List.copyOf(obstacles);
    }

    /** {@inheritDoc} */
    @Override
    public List<Obstacle> getObstacles() {
        return this.obstacles;
    }
}
