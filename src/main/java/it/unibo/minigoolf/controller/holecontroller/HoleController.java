package it.unibo.minigoolf.controller.holecontroller;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Shape;

public interface HoleController {

    /**
     * Returns the shape of the hole for rendering.
     *
     * @return the shape of the hole
     */
    Shape getHoleShape();

    /**
     * Returns the current position of the hole in logical coordinates.
     *
     * @return the hole position
     */
    Vector2D getPosition();

    /**
     * Returns the radius of the hole in logical coordinates.
     *
     * @return the hole radius
     */
    double getRadius();

}
