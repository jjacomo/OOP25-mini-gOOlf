package it.unibo.minigoolf.controller.gamemapcontroller;

import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Data Transfer Object for rendering an obstacle.
 *
 * @param shape the shape of the obstacle
 * @param bounciness the bounciness factor of the obstacle
 * @param isPortal whether the obstacle is a portal
 */
public record ObstacleData(Shape shape, double bounciness, boolean isPortal) {
}
