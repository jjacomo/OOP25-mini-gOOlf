package it.unibo.minigoolf.model.surfaces;

import java.util.List;
import java.util.Optional;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * An abstract decorator for surfaces that delegates all methods to a base surface.
 * 
 * @author jack
 */
public abstract class AbstractSurfaceDecorator implements Surface {

    private final Surface baseSurface;

    /**
     * Constructs a SurfaceDecorator with the given base surface.
     * 
     * @param baseSurface the surface to decorate
     * @throws IllegalArgumentException if baseSurface is null
     */
    public AbstractSurfaceDecorator(final Surface baseSurface) {
        if (baseSurface == null) {
            throw new IllegalArgumentException("Base surface cannot be null");
        }
        this.baseSurface = baseSurface;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>This implementation delegates to the decorated base surface.
     */
    @Override
    public double getFriction() {
        return baseSurface.getFriction();
    }

    /**
     * {@inheritDoc}
     * 
     * <p>This implementation delegates to the decorated base surface.
     */
    @Override
    public boolean contains(final Vector2D position) {
        return baseSurface.contains(position);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>This implementation delegates to the decorated base surface.
     */
    @Override
    public List<String> getTypeIds() {
        return baseSurface.getTypeIds();
    }

    /**
     * {@inheritDoc}
     * 
     * <p>This implementation delegates to the decorated base surface.
     */
    @Override
    public int getZIndex() {
        return baseSurface.getZIndex();
    }

    /**
     * {@inheritDoc}
     * 
     * <p>This implementation delegates to the decorated base surface.
     */
    @Override
    public Shape getShape() {
        return baseSurface.getShape();
    }

    /**
     * {@inheritDoc}
     * 
     * <p>This implementation delegates to the decorated base surface.
     */
    @Override
    public Optional<Vector2D> getWind() {
        return baseSurface.getWind();
    }
}
