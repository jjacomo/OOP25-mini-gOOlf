package it.unibo.minigoolf.model.surfaces.boost;

import java.util.ArrayList;
import java.util.List;

import it.unibo.minigoolf.model.surfaces.AbstractSurfaceDecorator;
import it.unibo.minigoolf.model.surfaces.Surface;

/**
 * A decorator that applies a speed boost to the ball by returning a negative friction coefficient.
 * 
 * @author jack
 */
public final class BoostSurface extends AbstractSurfaceDecorator {

    private final double boostIntensity;

    /**
     * Constructs a BoostSurface.
     * 
     * @param baseSurface    the base surface to decorate
     * @param boostIntensity the intensity of the boost
     * @throws IllegalArgumentException if boostIntensity is not positive
     */
    public BoostSurface(final Surface baseSurface, final double boostIntensity) {
        super(baseSurface);
        if (boostIntensity <= 0) {
            throw new IllegalArgumentException("Boost intensity must be greater than 0, got: " + boostIntensity);
        }
        this.boostIntensity = boostIntensity;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Returns a negative value representing the boost intensity.
     */
    @Override
    public double getFriction() {
        return -this.boostIntensity;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Returns the "boost" type identifier.
     */
    @Override
    public List<String> getTypeIds() {
        final List<String> ids = new ArrayList<>(super.getTypeIds());
        ids.add("boost");
        return ids;
    }
}
