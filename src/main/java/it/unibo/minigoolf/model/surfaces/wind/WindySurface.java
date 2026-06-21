package it.unibo.minigoolf.model.surfaces.wind;

import java.util.Optional;

import it.unibo.minigoolf.model.surfaces.AbstractSurfaceDecorator;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.util.Vector2D;

/**
 * A decorator that adds a wind effect to a base surface by returning a not
 * empty wind optional.
 */
public final class WindySurface extends AbstractSurfaceDecorator {

    private final Vector2D wind;

    /**
     * Constructs a WindySurface decorating the given base surface with the
     * specified wind direction and intensity.
     * 
     * @param baseSurface the surface to decorate
     * @param direction   the direction of the wind (UP, DOWN, LEFT, RIGHT)
     * @param intensity   the strength/speed of the wind
     * @throws IllegalArgumentException if intensity is not positive
     */
    public WindySurface(final Surface baseSurface, final WindDirection direction, final double intensity) {
        super(baseSurface);
        if (intensity <= 0) {
            throw new IllegalArgumentException("Wind intensity must be greater than 0, got: " + intensity);
        }
        this.wind = direction.getVector(intensity);
    }

    @Override
    public Optional<Vector2D> getWind() {
        return Optional.of(this.wind);
    }
}
