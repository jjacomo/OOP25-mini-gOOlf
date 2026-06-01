package it.unibo.minigoolf.controller.obstaclecontroller;

import it.unibo.minigoolf.model.obstacles.Obstacle;
import it.unibo.minigoolf.util.shapes.Shape;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link ObstacleController}.
 */
public final class ObstacleControllerImpl implements ObstacleController {

    private final List<Obstacle> obstacles;

    /**
     * Creates a new ObstacleController with an initial list of obstacles.
     *
     * @param obstacles the initial list of obstacles to manage
     */
    public ObstacleControllerImpl(final List<Obstacle> obstacles) {
        // List.copyOf crea una copia immutabile, SpotBugs ringrazia!
        this.obstacles = List.copyOf(obstacles);
    }

    /** {@inheritDoc} */
    /*@Override
    public List<Shape> getObstacleShapes() {
        return this.obstacles.stream()
                .map(Obstacle::getShape)
                .collect(Collectors.toList());
    }*/

    /** {@inheritDoc} */
    @Override
    public List<Obstacle> getObstacles() {
        return this.obstacles;
    }
}
