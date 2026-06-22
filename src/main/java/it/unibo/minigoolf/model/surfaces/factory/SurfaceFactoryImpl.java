package it.unibo.minigoolf.model.surfaces.factory;

import it.unibo.minigoolf.model.surfaces.ShapedSurface;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.model.surfaces.boost.BoostSurface;
import it.unibo.minigoolf.model.surfaces.wind.WindDirection;
import it.unibo.minigoolf.model.surfaces.wind.WindySurface;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Concrete implementation of the {@link SurfaceFactory} interface.
 * Defines the default physical parameters for surfaces.
 */
public final class SurfaceFactoryImpl implements SurfaceFactory {

    private static final double GRASS_FRICTION = 3.50;
    private static final double SAND_FRICTION = 12.50;
    private static final double DIRT_FRICTION = 7.75;
    private static final double ICE_FRICTION = 0.25;

    /**
     * Default constructor.
     */
    public SurfaceFactoryImpl() {
        // No initialization needed
    }

    @Override
    public Surface createGrass(final Shape shape, final int zIndex) {
        return new ShapedSurface(shape, GRASS_FRICTION, zIndex, "grass");
    }

    @Override
    public Surface createSand(final Shape shape, final int zIndex) {
        return new ShapedSurface(shape, SAND_FRICTION, zIndex, "sand");
    }

    @Override
    public Surface createDirt(final Shape shape, final int zIndex) {
        return new ShapedSurface(shape, DIRT_FRICTION, zIndex, "dirt");
    }

    @Override
    public Surface createIce(final Shape shape, final int zIndex) {
        return new ShapedSurface(shape, ICE_FRICTION, zIndex, "ice");
    }

    @Override
    public Surface createWindy(final Surface baseSurface, final WindDirection direction, final double intensity) {
        return new WindySurface(baseSurface, direction, intensity);
    }

    @Override
    public Surface createBoost(final Surface baseSurface, final double intensity) {
        return new BoostSurface(baseSurface, intensity);
    }
}
