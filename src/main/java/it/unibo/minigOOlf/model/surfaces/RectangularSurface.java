<<<<<<< HEAD:src/main/java/it/unibo/minigOOlf/model/RectangularSurface.java
package it.unibo.minigoolf.model;
=======
package it.unibo.minigoolf.model.surfaces;

import java.awt.Color;
>>>>>>> 2cbb57ae078d99b49a9daad7701efb4b2428958b:src/main/java/it/unibo/minigOOlf/model/surfaces/RectangularSurface.java

import it.unibo.minigoolf.util.Rectangle;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * A rectangular game surface with friction and layering properties.
 * <p>
 * This implementation delegates boundary containment checks to a {@link Rectangle}
 * and provides friction and z-index information required by the physics system.
 * </p>
 */
public class RectangularSurface implements Surface {
    private final double friction;
    private final Rectangle bounds;
    private final int zIndex;
    private final Color color;

    /**
     * Constructs a new rectangular surface.
     *
     * @param bounds the rectangle bounds defining the surface area
     * @param friction the friction coefficient applied on this surface
     * @param zIndex the rendering layer index; higher values are rendered on top
     * @param color the color of the surface for rendering
     */
    // could use a factory pattern for different shapes of surfaces in the future
    public RectangularSurface(Rectangle bounds, double friction, int zIndex, Color color) { 
        this.bounds = bounds;
        this.friction = friction;
        this.zIndex = zIndex;
        this.color = color;
    }

    @Override
    public double getFriction() {
        return friction;
    }

    @Override
    public boolean contains(Vector2D position) {
        return bounds.contains(position);
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }

    @Override
    public Color getColor() {
        return color;
    }
}
