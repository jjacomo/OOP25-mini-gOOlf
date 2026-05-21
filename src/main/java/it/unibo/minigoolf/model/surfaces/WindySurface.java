package it.unibo.minigoolf.model.surfaces;

import it.unibo.minigoolf.util.Vector2D;

/**
 * A decorator that adds a wind effect to a base surface.
 * The wind from this decorator is added to any wind already present in the base surface.
 */
public class WindySurface extends SurfaceDecorator {

    private final Vector2D wind;

    /**
     * Constructs a WindySurface decorating the given base surface with the specified wind vector.
     * 
     * @param baseSurface the surface to decorate
     * @param wind the wind vector to apply
     */
    public WindySurface(final Surface baseSurface, final Vector2D wind) {
        super(baseSurface);
        this.wind = wind;
    }

    @Override
    public Vector2D getWind() {
        return super.getWind().add(this.wind);
    }
}
