package it.unibo.minigoolf.controller.gamemapcontroller;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Data Transfer Object containing hole information for rendering.
 *
 * @param shape the shape of the hole
 * @param position the position of the hole
 * @param radius the radius of the hole
 */
public record HoleData(Shape shape, Vector2D position, double radius) {
}
