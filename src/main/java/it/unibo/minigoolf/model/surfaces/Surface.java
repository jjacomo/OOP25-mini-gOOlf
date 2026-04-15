package it.unibo.minigoolf.model.surfaces;

import java.awt.Color;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import it.unibo.minigoolf.util.Rectangle;

/**
 * Represents a surface in the game world that can affect physics and rendering.
 * Surfaces have friction properties that must be applied to the ball.
 * 
 * @author jack
 */
public interface Surface {

    /**
     * Returns the friction coefficient of this surface.
     * Friction affects the movement of objects interacting with this surface.
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
     * Returns the z-index of this surface, used for layering in rendering.
     * Higher z-index values indicate surfaces that should be rendered on top.
     * Friction is applied based on the surface with the highest z-index at the ball's position.
     *
     * @return the z-index as an integer
     */
    int getZIndex();

    /**
     * Returns the color of this surface for rendering purposes.
     *
     * @return the color of the surface
     */
    Color getColor();

    Rectangle getBounds();
}
