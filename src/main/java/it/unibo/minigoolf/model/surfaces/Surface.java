package it.unibo.minigoolf.model.surfaces;

import java.util.List;
import java.util.Optional;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Represents a surface in the game map that can affect physics and rendering.
 * 
 * @author jack
 */
public interface Surface {

    /**
     * Returns the friction coefficient of this surface.
     *
     * @return the friction value as a double
     */
    double getFriction();

    /**
     * Checks if the given position is contained within this surface's boundaries.
     *
     * @param position the 2D position to check
     * @return true if the position is within the surface, false otherwise
     */
    boolean contains(Vector2D position);

    /**
     * Returns the z-index of this surface, used for layering in rendering and
     * applying physics.
     * Higher z-index values indicate surfaces that should be rendered on top.
     *
     * @return the z-index as an integer
     */
    int getZIndex();

    /**
     * Returns the list of all type identifiers for this surface, including any decorators.
     *
     * @return a list of type IDs
     */
    List<String> getTypeIds();

    /**
     * Returns the shape representing the boundaries of this surface.
     *
     * @return the shape of the surface
     */
    Shape getShape();

    /**
     * Returns the wind velocity applied by this surface, if any.
     * By default, a surface does not apply any wind.
     * 
     * @return an optional wind vector
     */
    default Optional<Vector2D> getWind() {
        return Optional.empty();
    }
}
