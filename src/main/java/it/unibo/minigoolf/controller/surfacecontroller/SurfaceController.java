package it.unibo.minigoolf.controller.surfacecontroller;

import java.util.List;
import java.util.Optional;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Controller interface for managing a surface and providing its view
 * representation.
 * 
 * @author jack
 */
public interface SurfaceController {

    /**
     * Returns the shape of the surface.
     *
     * @return the shape
     */
    Shape getShape();

    /**
     * Returns the z-index of the surface.
     *
     * @return the z-index
     */
    int getZIndex();

    /**
     * Returns the list of type IDs for this surface, ordered from base to outermost decorator.
     *
     * @return the list of type IDs
     */
    List<String> getTypeIds();

    /**
     * Returns the wind of the surface, if any.
     * 
     * @return an optional wind vector
     */
    Optional<Vector2D> getWind();
}
