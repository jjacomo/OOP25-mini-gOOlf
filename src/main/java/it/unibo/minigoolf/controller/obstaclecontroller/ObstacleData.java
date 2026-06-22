package it.unibo.minigoolf.controller.obstaclecontroller;

import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Data Transfer Object containing visual information about an obstacle.
 */
public record ObstacleData(Shape shape, double bounciness, boolean isPortal) {
}
