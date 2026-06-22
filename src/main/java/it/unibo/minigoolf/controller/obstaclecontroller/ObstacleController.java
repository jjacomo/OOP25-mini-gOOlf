package it.unibo.minigoolf.controller.obstaclecontroller;

import it.unibo.minigoolf.model.obstacles.Obstacle;
import java.util.List;

/**
 * Controller for managing all obstacles in the minigolf map.
 * 
 * @author Mattia
 */
public interface ObstacleController {
    /**
     * Returns an unmodifiable list of all obstacles currently managed.
     *
     * @return a list of obstacles
     */
    List<Obstacle> getObstacles();

    /**
     * Gets the visual data of all obstacles for rendering.
     * 
     * @return an immutable list of ObstacleData 
     */
    List<ObstacleData> getObstaclesData();
}
