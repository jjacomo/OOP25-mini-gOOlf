package it.unibo.minigoolf.model.surfaces;

import it.unibo.minigoolf.util.Vector2D;

import it.unibo.minigoolf.util.shapes.Rectangle;

/**
 * A rectangular game surface with friction and layering properties.
 * 
 * <p>
 * This implementation delegates boundary containment checks to a
 * {@link Rectangle}
 * and provides friction and z-index information required by the physics system.
 * </p>
 */
public final class RectangularSurface implements Surface {
    private final double friction;
    private final Rectangle bounds;
    private final int zIndex;
    private final String texturePath;

    /**
     * @author jack
     * 
     *         Constructs a new rectangular surface.
     *
     * @param bounds   the rectangle bounds defining the surface area
     * @param friction the friction coefficient applied on this surface
     * @param zIndex   the rendering layer index; higher values are rendered on top
     * @param texturePath    the path to the texture image for rendering
     * 
     */
    // could use a factory pattern for different shapes of surfaces in the future
    public RectangularSurface(final Rectangle bounds, final double friction, final int zIndex, final String texturePath) {
        this.bounds = bounds;
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
        return bounds.contains(position);
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
    public Rectangle getShape() {
        return bounds;
    }
}
