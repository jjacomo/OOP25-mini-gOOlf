package it.unibo.minigoolf.model.surfaces.factory;

import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.model.surfaces.wind.WindDirection;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Factory interface for creating game surfaces.
 * This encapsulates the details of surface creation, including default friction
 * coefficients.
 */
public interface SurfaceFactory {

    /**
     * Creates a grass surface.
     * 
     * @param shape  the geometric shape defining the surface boundaries
     * @param zIndex the z-index of the surface for rendering and layering
     * @return a new grass surface
     */
    Surface createGrass(Shape shape, int zIndex);

    /**
     * Creates a sand surface.
     * 
     * @param shape  the geometric shape defining the surface boundaries
     * @param zIndex the z-index of the surface for rendering and layering
     * @return a new sand surface
     */
    Surface createSand(Shape shape, int zIndex);

    /**
     * Creates a dirt surface.
     * 
     * @param shape  the geometric shape defining the surface boundaries
     * @param zIndex the z-index of the surface for rendering and layering
     * @return a new dirt surface
     */
    Surface createDirt(Shape shape, int zIndex);

    /**
     * Creates a new ice surface.
     * 
     * @param shape  the geometric shape defining the surface boundaries
     * @param zIndex the z-index of the surface for rendering and layering
     * @return a new ice surface
     */
    Surface createIce(Shape shape, int zIndex);

    /**
     * Creates a windy surface wrapping a base surface.
     * 
     * @param baseSurface the base surface to decorate with wind
     * @param direction   the direction of the wind
     * @param intensity   the intensity/strength of the wind
     * @return a new decorated windy surface
     */
    Surface createWindy(Surface baseSurface, WindDirection direction, double intensity);

    /**
     * Creates a boost surface wrapping a base surface.
     * 
     * @param baseSurface the base surface to decorate with boost
     * @param intensity   the intensity/strength of the boost (will result in negative friction)
     * @return a new decorated boost surface
     */
    Surface createBoost(Surface baseSurface, double intensity);
}
