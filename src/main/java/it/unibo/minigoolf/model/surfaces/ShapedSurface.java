package it.unibo.minigoolf.model.surfaces;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Surface implementation that uses a Shape to define its geometry.
 */
public final class ShapedSurface implements Surface {

    private final double friction;
    private final Shape shape;
    private final int zIndex;
    private final String texturePath;

    /**
     * Constructs a ShapedSurface with the given shape and type.
     * 
     * @param shape The geometric shape defining the surface area.
     * @param type  The type of the surface (e.g., GRASS, SAND).
     */
    public ShapedSurface(final Shape shape, final double friction, final int zIndex, final String texturePath) {
        this.shape = shape;
        this.friction = friction;
        this.zIndex = zIndex;
        this.texturePath = texturePath;
    }

    @Override
    public double getFriction() {
        return friction;
    }

    @Override
    public boolean contains(final Vector2D position) {
        return shape.contains(position);
    }

    @Override
    public int getZIndex() {
        return zIndex;
    }

    @Override
    public String getTexturePath() {
        return texturePath;
    }

    @Override
    public Shape getShape() {
        return shape;
    }
}
