package it.unibo.minigoolf.model.map.factories;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import it.unibo.minigoolf.util.Vector2D;

import it.unibo.minigoolf.model.ball.BallImpl;
import it.unibo.minigoolf.model.hole.HoleImpl;
import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.map.GameMapImpl;
import it.unibo.minigoolf.model.obstacles.Obstacle;
import it.unibo.minigoolf.model.obstacles.RoundObstacle;
import it.unibo.minigoolf.model.obstacles.TriangleObstacle;
import it.unibo.minigoolf.model.obstacles.WallObstacle;
import it.unibo.minigoolf.model.surfaces.RectangularSurface;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.model.surfaces.SurfaceType;
import it.unibo.minigoolf.util.shapes.Rectangle;

/**
 * Test implementation of the GameMapFactory interface.
 * 
 * <p>
 * This factory creates a simple, flat test game map suitable for development,
 * debugging, and basic testing purposes.
 * </p>
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
    private static final double MAIN_SURFACE_WIDTH = 1920;
    private static final double MAIN_SURFACE_HEIGHT = 1080;
    private static final int MAIN_SURFACE_Z_INDEX = 0;
    private static final double SECOND_SURFACE_X = 100;
    private static final double SECOND_SURFACE_Y = 50;
    private static final double SECOND_SURFACE_WIDTH = 100;
    private static final double SECOND_SURFACE_HEIGHT = 200;
    private static final int SECOND_SURFACE_Z_INDEX = 1;
    private static final double THIRD_SURFACE_X = 440;
    private static final double THIRD_SURFACE_Y = 100;
    private static final double THIRD_SURFACE_WIDTH = 600;
    private static final double THIRD_SURFACE_HEIGHT = 200;
    private static final int THIRD_SURFACE_Z_INDEX = 2;
    private static final double BALL_RADIUS = 30;
    private static final Vector2D BALL_INITIAL_POSITION = new Vector2D(150, 150);

    private static final double W1_X = 0;
    private static final double W1_Y = 0;
    private static final double W1_WIDTH = 15;
    private static final double W1_HEIGHT = 1080;
    private static final double W2_X = 0;
    private static final double W2_Y = 0;
    private static final double W2_WIDTH = 1920;
    private static final double W2_HEIGHT = 15;
    private static final double W3_X = 1905;
    private static final double W3_Y = 0;
    private static final double W3_WIDTH = 15;
    private static final double W3_HEIGHT = 1080;
    private static final double W4_X = 0;
    private static final double W4_Y = 1065;
    private static final double W4_WIDTH = 1920;
    private static final double W4_HEIGHT = 15;
    private static final double O1_X = 300;
    private static final double O1_Y = 200;
    private static final double O1_WIDTH = 40;
    private static final double O1_HEIGHT = 100;
    private static final double O2_X = 700;
    private static final double O2_Y = 200;
    private static final double O2_RADIUS = 50;
    private static final Vector2D O3_V1 = new Vector2D(900, 400);
    private static final Vector2D O3_V2 = new Vector2D(900, 450);
    private static final Vector2D O3_V3 = new Vector2D(750, 400);

    private static final Vector2D HOLE_POSITION = new Vector2D(1500, 800);
    private static final double HOLE_RADIUS = 40;

    /**
     * Builds a simple test game map.
     * 
     * <p>
     * The flat surface allows for straightforward physics simulation and visual
     * testing of the ball mechanics and user interactions.
     * </p>
     * 
     * @return a GameMap instance containing two rectangular surfaces with different
     *         properties: a large green surface (500×800) and a smaller blue
     *         surface (100×200)
     * 
     * @implNote The surface is created with hardcoded dimensions and friction
     *           values.
     *           A future implementation should externalize these values to
     *           configuration.
     */
    @Override
    public GameMap buildGameMap() {
        final List<Surface> surfaces = new ArrayList<>();
        final List<Obstacle> obstacles = new ArrayList<>();
        surfaces.add(new RectangularSurface(
                new Rectangle(new Vector2D(MAIN_SURFACE_X, MAIN_SURFACE_Y), MAIN_SURFACE_WIDTH,
                        MAIN_SURFACE_HEIGHT, Color.WHITE),
                SurfaceType.GRASS.getFriction(), MAIN_SURFACE_Z_INDEX, SurfaceType.GRASS.getTexturePath()));
        surfaces.add(new RectangularSurface(
                new Rectangle(new Vector2D(SECOND_SURFACE_X, SECOND_SURFACE_Y), SECOND_SURFACE_WIDTH,
                        SECOND_SURFACE_HEIGHT, Color.WHITE),
                SurfaceType.SAND.getFriction(), SECOND_SURFACE_Z_INDEX, SurfaceType.SAND.getTexturePath()));
        surfaces.add(new RectangularSurface(
                new Rectangle(new Vector2D(THIRD_SURFACE_X, THIRD_SURFACE_Y), THIRD_SURFACE_WIDTH,
                        THIRD_SURFACE_HEIGHT, Color.WHITE),
                SurfaceType.DIRT.getFriction(), THIRD_SURFACE_Z_INDEX, SurfaceType.DIRT.getTexturePath()));
        obstacles.add(new WallObstacle(new Vector2D(W1_X, W1_Y), W1_WIDTH, W1_HEIGHT, Color.BLACK));
        obstacles.add(new WallObstacle(new Vector2D(W2_X, W2_Y), W2_WIDTH, W2_HEIGHT, Color.BLACK));
        obstacles.add(new WallObstacle(new Vector2D(W3_X, W3_Y), W3_WIDTH, W3_HEIGHT, Color.BLACK));
        obstacles.add(new WallObstacle(new Vector2D(W4_X, W4_Y), W4_WIDTH, W4_HEIGHT, Color.BLACK));
        obstacles.add(new WallObstacle(new Vector2D(O1_X, O1_Y), O1_WIDTH, O1_HEIGHT, Color.BLACK));
        obstacles.add(new RoundObstacle(new Vector2D(O2_X, O2_Y), O2_RADIUS, Color.ORANGE));
        obstacles.add(new TriangleObstacle(O3_V1, O3_V2, O3_V3, Color.ORANGE));
        return new GameMapImpl(surfaces, new BallImpl(BALL_INITIAL_POSITION, BALL_RADIUS),
                new HoleImpl(HOLE_POSITION, HOLE_RADIUS), obstacles);
    }
}
