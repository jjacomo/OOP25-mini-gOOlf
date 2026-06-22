package it.unibo.minigoolf.controller.gamemapcontroller;

import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Data Transfer Object containing ball information for rendering.
 *
 * @param shape the shape of the ball
 */
public record BallData(Shape shape) {
}
