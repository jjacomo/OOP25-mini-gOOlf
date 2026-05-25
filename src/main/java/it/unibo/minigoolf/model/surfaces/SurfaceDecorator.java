package it.unibo.minigoolf.model.surfaces;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Shape;

public abstract class SurfaceDecorator implements Surface {

    protected final Surface baseSurface;

    /**
     * Constructs a SurfaceDecorator with the given base surface.
     * 
     * @param baseSurface the surface to decorate
     * @throws IllegalArgumentException if baseSurface is null
     */
    public SurfaceDecorator(final Surface baseSurface) {
        if (baseSurface == null) {
            throw new IllegalArgumentException("Base surface cannot be null");
        }
        this.baseSurface = baseSurface;
    }

    @Override
    public double getFriction() {
        return baseSurface.getFriction();
    }

    @Override
    public boolean contains(final Vector2D position) {
        return baseSurface.contains(position);
    }

    @Override
    public SurfaceType getType() {
        return baseSurface.getType();
    }

    @Override
    public int getZIndex() {
        return baseSurface.getZIndex();
    }

    @Override
    public Shape getShape() {
        return baseSurface.getShape();
    }

    @Override
    public Vector2D getWind() {
        return baseSurface.getWind();
    }
}
