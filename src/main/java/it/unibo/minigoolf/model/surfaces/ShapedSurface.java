package it.unibo.minigoolf.model.surfaces;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Surface implementation that uses a Shape to define its geometry.
 * 
 * @author jack
 */
public final class ShapedSurface implements Surface {

    private final double friction;
    private final Shape shape;
    private final int zIndex;
    private final String texturePath;

    /**
     * Constructs a ShapedSurface with the given shape and type.
     * 
     * @param shape       The geometric shape defining the surface area.
     * @param friction    The friction coefficient of the surface.
     * @param zIndex      The z-index of the surface (for rendering purposes).
     * @param texturePath The path to the texture of the surface.
     */
    public ShapedSurface(final Shape shape, final double friction, final int zIndex, final String texturePath) {
        this.shape = shape;
        this.friction = friction;
        this.zIndex = zIndex;
        this.texturePath = texturePath;
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
     * @return the texture path of the surface.
     */
    @Override
    public String getTexturePath() {
        return texturePath;
    }

    /**
     * @return the shape of the surface.
     */
    @Override
    public Shape getShape() {
        return shape;
    }
}
