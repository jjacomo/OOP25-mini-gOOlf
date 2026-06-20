package it.unibo.minigoolf.model.surfaces;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Surface implementation that uses a Shape to define its geometry.
 * 
 * @author jack
 */
public final class ShapedSurface implements Surface {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShapedSurface.class.getName());

    private final double friction;
    private final Shape shape;
    private final int zIndex;
    private final String typeId;

    /**
     * Constructs a ShapedSurface with the given shape and type.
     * 
     * @param shape    The geometric shape defining the surface area.
     * @param friction The friction coefficient of the surface.
     * @param zIndex   The z-index of the surface (for rendering purposes).
     * @param typeId   The surface type.
     * @throws IllegalArgumentException if friction is negative
     */
    public ShapedSurface(final Shape shape, final double friction, final int zIndex, final String typeId) {
        if (friction < 0) {
            LOGGER.error("Attempted to create a surface with negative friction: " + friction);
            throw new IllegalArgumentException("Friction must be non-negative, got: " + friction);
        }
        this.shape = shape;
        this.friction = friction;
        this.zIndex = zIndex;
        this.typeId = typeId;
    }

    /**
     * @return the friction coefficient of the surface.
     */
    @Override
    public double getFriction() {
        return friction;
    }

    /**
     * Checks if the given position is contained within the surface.
     * 
     * @param position The position to check.
     * @return true if the position is inside the surface, false otherwise.
     */
    @Override
    public boolean contains(final Vector2D position) {
        return shape.contains(position);
    }

    /**
     * @return the z-index of the surface (for rendering purposes).
     */
    @Override
    public int getZIndex() {
        return zIndex;
    }

    /**
     * @return the type identifier of the surface.
     */
    @Override
    public List<String> getTypeIds() {
        return List.of(typeId);
    }

    /**
     * @return the shape of the surface.
     */
    @Override
    public Shape getShape() {
        return shape;
    }
}
