package it.unibo.minigoolf.model.map;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import it.unibo.minigoolf.model.ball.BallImpl;
import it.unibo.minigoolf.model.surfaces.RectangularSurface;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.model.surfaces.SurfaceType;
import it.unibo.minigoolf.util.Rectangle;

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
 * @author jack
 * 
 * @see GameMapFactory
 * @see GameMap
 * @see RectangularSurface
 * @see Surface
 */
public class TestGameMapFactory implements GameMapFactory {
 
    private static final double MAIN_SURFACE_X = 0;
    private static final double MAIN_SURFACE_Y = 0;
    private static final double MAIN_SURFACE_WIDTH = 500;
    private static final double MAIN_SURFACE_HEIGHT = 800;
    private static final int MAIN_SURFACE_Z_INDEX = 0;
    private static final double SECOND_SURFACE_X = 100;
    private static final double SECOND_SURFACE_Y = 50;
    private static final double SECOND_SURFACE_WIDTH = 100;
    private static final double SECOND_SURFACE_HEIGHT = 200;
    private static final int SECOND_SURFACE_Z_INDEX = 1;
    private static final double BALL_RADIUS = 10;
    private static final Vector2D BALL_INITIAL_POSITION = new Vector2D(50, 50);

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
        final List<Surface> surfaces = new ArrayList<>();
        surfaces.add(new RectangularSurface(
                new Rectangle(new Vector2D(MAIN_SURFACE_X, MAIN_SURFACE_Y), MAIN_SURFACE_WIDTH, MAIN_SURFACE_HEIGHT),
                SurfaceType.GRASS.getFriction(), MAIN_SURFACE_Z_INDEX, SurfaceType.GRASS.getColor()));
        surfaces.add(new RectangularSurface(
                new Rectangle(new Vector2D(SECOND_SURFACE_X, SECOND_SURFACE_Y), SECOND_SURFACE_WIDTH,
                        SECOND_SURFACE_HEIGHT),
                SurfaceType.SAND.getFriction(), SECOND_SURFACE_Z_INDEX, SurfaceType.SAND.getColor()));
        return new GameMapImpl(surfaces, new BallImpl(BALL_INITIAL_POSITION, BALL_RADIUS));
    }
}
