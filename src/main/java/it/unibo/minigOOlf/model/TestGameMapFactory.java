package it.unibo.minigOOlf.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import it.unibo.minigOOlf.util.Rectangle;

/**
 * Test implementation of the GameMapFactory interface.
 * 
 * <p>This factory creates a simple, flat test game map suitable for development,
 * debugging, and basic testing purposes. The generated map consists of a single
 * rectangular surface with standard friction properties.</p>
 * 
 * <p>Map Specifications:
 * <ul>
 *   <li>Dimensions: 500 units wide × 800 units tall</li>
 *   <li>Position: (0, 0) - top-left corner</li>
 *   <li>Friction Coefficient: 0.75 - moderate friction for realistic ball movement</li>
 *   <li>Z-Index: 0 - base surface layer</li>
 *   <li>Surface Type: RectangularSurface with flat terrain</li>
 * </ul>
 * </p>
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
     * @return a GameMap instance containing a single flat rectangular surface
     *         with dimensions 500×800 units and a friction coefficient of 0.75
     * 
     * @implNote The surface is created with hardcoded dimensions and friction values.
     *           A future implementation should externalize these values to configuration.
     */
    @Override
    public GameMap buildGameMap() {
        List<Surface> surfaces = new ArrayList<>();
        // Flat surface - 500 units wide, 800 units tall, 0.75 friction coefficient, z-index 0
        surfaces.add(new RectangularSurface(new Rectangle(new Vector2D(0, 0), 500, 800), 0.75, 0));
        return new GameMapImpl(surfaces);
    }
}
