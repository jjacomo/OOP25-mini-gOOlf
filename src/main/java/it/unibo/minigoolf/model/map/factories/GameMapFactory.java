package it.unibo.minigoolf.model.map.factories;

import it.unibo.minigoolf.model.map.GameMap;

/**
 * Factory interface for creating game map instances.
 * 
 * <p>
 * This interface encapsulates the creation of diverse game maps with different
 * configurations, surfaces, and obstacles.
 * </p>
 * 
 * @author jack
 * 
 * @see GameMap
 */
@FunctionalInterface
public interface GameMapFactory {

    /**
     * Builds and returns a new game map instance containing a set of surfaces, a
     * set of obstacles, a ball and a hole.
     * 
     * @return a newly constructed GameMap instance
     */
    GameMap buildGameMap();
}
