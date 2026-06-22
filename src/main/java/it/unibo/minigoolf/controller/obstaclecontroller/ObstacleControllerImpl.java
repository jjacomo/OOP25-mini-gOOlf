package it.unibo.minigoolf.controller.obstaclecontroller;

import it.unibo.minigoolf.model.obstacles.Obstacle;
import it.unibo.minigoolf.model.obstacles.PortalObstacle;

import java.util.ArrayList;
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

    /** {@inheritDoc} */
    @Override
    public List<ObstacleData> getObstaclesData() {
        final List<ObstacleData> dataList = new ArrayList<>();

        for (final Obstacle obs : this.obstacles) {
            final boolean isPortal = obs instanceof PortalObstacle;
            final ObstacleData data = new ObstacleData(
                    obs.getShape(), 
                    obs.getBounciness(), 
                    isPortal
            );
            dataList.add(data);
        }

        return List.copyOf(dataList); 
    }
}
