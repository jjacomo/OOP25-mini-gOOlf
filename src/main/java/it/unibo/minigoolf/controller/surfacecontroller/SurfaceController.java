package it.unibo.minigoolf.controller.surfacecontroller;

import java.util.Optional;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Controller interface for managing a surface and providing its view
 * representation.
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
     * Returns the relative path to the texture of the surface.
     *
     * @return the texture path
     */
    String getTexturePath();

    /**
     * Returns the relative path to the wind overlay texture of the surface.
     *
     * @return the wind overlay texture path
     */
    String getWindOverlayTexturePath();

    /**
     * Returns the type identifier of the surface.
     * 
     * @return the type ID as a String
     */
    String getTypeId();

    /**
     * Returns the wind of the surface, if any.
     * 
     * @return an optional wind vector
     */
    Optional<Vector2D> getWind();
}
