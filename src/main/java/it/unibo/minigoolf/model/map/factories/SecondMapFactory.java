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
 * <p>This factory creates a simple, flat test game map suitable for development,
 * debugging, and basic testing purposes. </p>
 * 
 * @author jack
 * 
 * @see GameMapFactory
 * @see GameMap
 * @see RectangularSurface
 * @see Surface
 */
public class SecondMapFactory implements GameMapFactory {
 
    private static final double GRASS1_X = 0;
    private static final double GRASS1_Y = 0;
    private static final double GRASS1_WIDTH = 550;
    private static final double GRASS1_HEIGHT = 1080;
    private static final int GRASS1_Z_INDEX = 0;
    private static final double DIRT_X = 550;
    private static final double DIRT_Y = 0;
    private static final double DIRT_WIDTH = 690;
    private static final double DIRT_HEIGHT = 540;
    private static final int DIRT_Z_INDEX = 1;
    private static final double SAND_X = 550;
    private static final double SAND_Y = 540;
    private static final double SAND_WIDTH = 690;
    private static final double SAND_HEIGHT = 540;
    private static final int SAND_Z_INDEX = 2;
    private static final double GRASS2_X = 1240;
    private static final double GRASS2_Y = 0;
    private static final double GRASS2_WIDTH = 680;
    private static final double GRASS2_HEIGHT = 270;
    private static final int GRASS2_Z_INDEX = 0;
    private static final double GRASS3_X = 1240;
    private static final double GRASS3_Y = 810;
    private static final double GRASS3_WIDTH = 680;
    private static final double GRASS3_HEIGHT = 270;
    private static final int GRASS3_Z_INDEX = 0;
    private static final double GRASS4_X = 1650;
    private static final double GRASS4_Y = 0;
    private static final double GRASS4_WIDTH = 270;
    private static final double GRASS4_HEIGHT = 1080;
    private static final int GRASS4_Z_INDEX = 0;
    private static final double BALL_RADIUS = 30;
    private static final Vector2D BALL_INITIAL_POSITION = new Vector2D(80, 80);

    /**
     * Builds a simple test game map.
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
        final List<Obstacle> obstacles = new ArrayList<>();
        surfaces.add(new RectangularSurface(
                new Rectangle(new Vector2D(GRASS1_X, GRASS1_Y), GRASS1_WIDTH, GRASS1_HEIGHT, Color.WHITE),
                SurfaceType.GRASS.getFriction(), GRASS1_Z_INDEX, SurfaceType.GRASS.getTexturePath()));
        surfaces.add(new RectangularSurface(
                new Rectangle(new Vector2D(GRASS2_X, GRASS2_Y), GRASS2_WIDTH, GRASS2_HEIGHT, Color.WHITE),
                SurfaceType.GRASS.getFriction(), GRASS2_Z_INDEX, SurfaceType.GRASS.getTexturePath()));
        surfaces.add(new RectangularSurface(
                new Rectangle(new Vector2D(GRASS3_X, GRASS3_Y), GRASS3_WIDTH, GRASS3_HEIGHT, Color.WHITE),
                SurfaceType.GRASS.getFriction(), GRASS3_Z_INDEX, SurfaceType.GRASS.getTexturePath()));
        surfaces.add(new RectangularSurface(
                new Rectangle(new Vector2D(GRASS4_X, GRASS4_Y), GRASS4_WIDTH, GRASS4_HEIGHT, Color.WHITE),
                SurfaceType.GRASS.getFriction(), GRASS4_Z_INDEX, SurfaceType.GRASS.getTexturePath()));
        surfaces.add(new RectangularSurface(
                new Rectangle(new Vector2D(SAND_X, SAND_Y), SAND_WIDTH,
                        SAND_HEIGHT, Color.WHITE),
                SurfaceType.SAND.getFriction(), SAND_Z_INDEX, SurfaceType.SAND.getTexturePath()));
        surfaces.add(new RectangularSurface(
                new Rectangle(new Vector2D(DIRT_X, DIRT_Y), DIRT_WIDTH,
                        DIRT_HEIGHT, Color.WHITE),
                SurfaceType.DIRT.getFriction(), DIRT_Z_INDEX, SurfaceType.DIRT.getTexturePath()));
        obstacles.add(new WallObstacle(new Vector2D(0, 0), 15, 1080, Color.BLACK));
        obstacles.add(new WallObstacle(new Vector2D(0, 0), 1920, 15, Color.BLACK));
        obstacles.add(new WallObstacle(new Vector2D(1905, 0), 15, 1080, Color.BLACK));
        obstacles.add(new WallObstacle(new Vector2D(0, 1065), 1920, 15, Color.BLACK));
        obstacles.add(new WallObstacle(new Vector2D(300, 200), 40, 100, Color.ORANGE));
        obstacles.add(new RoundObstacle(new Vector2D(700, 200), 50, Color.ORANGE));
        obstacles.add(new TriangleObstacle(new Vector2D(900, 400), new Vector2D(900, 450), new Vector2D(750, 400), Color.ORANGE));
        return new GameMapImpl(surfaces, new BallImpl(BALL_INITIAL_POSITION, BALL_RADIUS), obstacles, new HoleImpl(new Vector2D(1500, 800), 40));
    }
}
