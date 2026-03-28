package it.unibo.minigOOlf.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import it.unibo.minigOOlf.model.surfaces.RectangularSurface;
import it.unibo.minigOOlf.model.surfaces.Surface;
import it.unibo.minigOOlf.model.surfaces.SurfaceType;
import it.unibo.minigOOlf.util.Rectangle;

/**
 * Test implementation of the GameMapFactory interface.
 * 
 * <p>This factory creates a simple, flat test game map suitable for development,
 * debugging, and basic testing purposes. The generated map consists of a single
 * rectangular surface with standard friction properties.</p>
 * 
 * <p>Map Specifications:</p>
 * <ul>
 *   <li><strong>Main Surface:</strong> 500×800 units, position (0,0), friction 0.75, z-index 0, green color</li>
 *   <li><strong>Secondary Surface:</strong> 100×200 units, position (100,50), friction 0.65, z-index 0, blue color</li>
 * </ul>
 * 
 * @see GameMapFactory
 * @see GameMap
 * @see RectangularSurface
 * @see Surface
 * 
 * @author jack
 */
public class TestGameMapFactory implements GameMapFactory {

    /**
     * Builds a simple test game map with a single flat rectangular surface.
     * 
     * <p>The flat surface allows for straightforward physics simulation and visual
     * testing of the ball mechanics and user interactions.</p>
     * 
     * @return a GameMap instance containing two rectangular surfaces with different
     *         properties: a large green surface (500×800) and a smaller blue surface (100×200)
     * 
     * @implNote The surface is created with hardcoded dimensions and friction values.
     *           A future implementation should externalize these values to configuration.
     */
    @Override
    public GameMap buildGameMap() {
        List<Surface> surfaces = new ArrayList<>();
        surfaces.add(new RectangularSurface(new Rectangle(new Vector2D(0, 0), 500, 800),
                SurfaceType.GRASS.getFriction(), 0, SurfaceType.GRASS.getColor()));
        surfaces.add(new RectangularSurface(new Rectangle(new Vector2D(100, 50), 100, 200),
                SurfaceType.SAND.getFriction(), 1, SurfaceType.SAND.getColor()));
        return new GameMapImpl(surfaces);
    }
}
