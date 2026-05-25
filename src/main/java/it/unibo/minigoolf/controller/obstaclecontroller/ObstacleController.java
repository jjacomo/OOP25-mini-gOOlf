package it.unibo.minigoolf.controller.obstaclecontroller;

import it.unibo.minigoolf.model.obstacles.Obstacle;
import it.unibo.minigoolf.util.shapes.Shape;

import java.util.List;

/**
 * Controller for managing all obstacles in the minigolf map.
 */
public interface ObstacleController {

    /**
     * Returns a list of shapes representing all obstacles for rendering.
     *
     * @return a list of shapes
     */
    List<Shape> getObstacleShapes();

    /**
     * Returns an unmodifiable list of all obstacles currently managed.
     *
     * @return a list of obstacles
     */
    List<Obstacle> getObstacles();
}
