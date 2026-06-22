package it.unibo.minigoolf.controller.obstaclecontroller;

import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Data Transfer Object containing visual information about an obstacle.
 * 
 * @param shape the shape of the obstacle
 * @param bounciness the bounciness of the obstacle
 * @param isPortal true if the obstacle is a portal, false otherwise
 */
public record ObstacleData(Shape shape, double bounciness, boolean isPortal) {
}
