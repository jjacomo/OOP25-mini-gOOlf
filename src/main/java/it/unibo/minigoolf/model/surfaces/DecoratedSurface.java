package it.unibo.minigoolf.model.surfaces;

import it.unibo.minigoolf.util.Vector2D;

public abstract class DecoratedSurface implements Surface {

    protected final Surface baseSurface;

    public DecoratedSurface(final Surface baseSurface) {
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
}
