package it.unibo.minigOOlf.model;

import it.unibo.minigOOlf.model.surfaces.RectangularSurface;
import it.unibo.minigOOlf.model.surfaces.Surface;

/**
 * Factory interface for creating game map instances.
 * 
 * <p>This interface encapsulates the creation of diverse game maps with different
 * configurations, surfaces, and obstacles. Implementations of this interface provide
 * concrete strategies for building game maps tailored to specific game scenarios,
 * difficulty levels, or test requirements.</p>
 * 
 * <p>Implementations should follow the Factory Pattern to enable flexible map creation
 * without tightly coupling map construction logic to client code.</p>
 * 
 * @see GameMap
 * @see RectangularSurface
 * @see Surface
 * 
 * @author jack
 */
public interface GameMapFactory {
    
    /**
     * Builds and returns a new game map instance.
     * 
     * <p>The returned GameMap contains a collection of surfaces with various properties
     * such as friction coefficients and z-indices. The exact configuration depends on
     * the specific implementation.</p>
     * 
     * @return a newly constructed GameMap instance with configured surfaces
     */
    GameMap buildGameMap();
}
