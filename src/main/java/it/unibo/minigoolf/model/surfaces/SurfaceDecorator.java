package it.unibo.minigoolf.model.surfaces;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Shape;

public abstract class SurfaceDecorator implements Surface {

    protected final Surface baseSurface;

    public SurfaceDecorator(final Surface baseSurface) {
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
