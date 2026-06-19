package it.unibo.minigoolf.model.map.factories;

import java.util.ArrayList;
import java.util.List;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.model.ball.BallImpl;
import it.unibo.minigoolf.model.hole.HoleImpl;
import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.map.GameMapImpl;
import it.unibo.minigoolf.model.obstacles.Obstacle;
import it.unibo.minigoolf.model.obstacles.RoundObstacle;
import it.unibo.minigoolf.model.obstacles.WallObstacle;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.model.surfaces.factory.SurfaceFactory;
import it.unibo.minigoolf.model.surfaces.factory.SurfaceFactoryImpl;
import it.unibo.minigoolf.util.shapes.Rectangle;

/**
 * Fourth map.
 * 
 * @author Mattia
 * 
 * @see GameMapFactory
 * @see GameMap
 * @see RectangularSurface
 * @see Surface
 * 
 */
public class MapD implements GameMapFactory {

    //GRASS
    private static final double GRASS_X = 0;
    private static final double GRASS_Y = 0;
    private static final double GRASS_WIDTH = 1920;
    private static final double GRASS_HEIGHT = 1080;
    private static final int GRASS_Z_INDEX = 0;

    // SAND 
    private static final double SAND_X = 400;
    private static final double SAND_Y = 30;
    private static final double SAND_WIDTH = 1200;
    private static final double SAND_HEIGHT = 300;
    private static final int SAND_Z_INDEX = 2;

    // ICE
    private static final double ICE_X = 400;
    private static final double ICE_Y = 750;
    private static final double ICE_WIDTH = 1200;
    private static final double ICE_HEIGHT = 200;
    private static final int ICE_Z_INDEX = 4;

    // OBSTACLES BOUNCYNESS
    private static final double STICKY_BOUNCINESS = 0.5;
    private static final double BOUNCY_BOUNCINESS = 1.5;

    // EXTERNAL WALLS
    private static final double W1_X = 0;
    private static final double W1_Y = 0;
    private static final double W1_WIDTH = 31;
    private static final double W1_HEIGHT = 1080;

    private static final double W2_X = 0;
    private static final double W2_Y = 0;
    private static final double W2_WIDTH = 1920;
    private static final double W2_HEIGHT = 31;

    private static final double W3_X = 1889;
    private static final double W3_Y = 0;
    private static final double W3_WIDTH = 31;
    private static final double W3_HEIGHT = 1080;

    private static final double W4_X = 0;
    private static final double W4_Y = 1049;
    private static final double W4_WIDTH = 1920;
    private static final double W4_HEIGHT = 31;

    // ROUND OBSTACLES
    private static final double O1_X = 500;
    private static final double O1_Y = 185;
    private static final double O1_RADIUS = 40;
    private static final double O1_BOUNCINESS = STICKY_BOUNCINESS;

    private static final double O2_X = 600;
    private static final double O2_Y = 75;
    private static final double O2_RADIUS = 40;
    private static final double O2_BOUNCINESS = BOUNCY_BOUNCINESS;

    private static final double O3_X = 600;
    private static final double O3_Y = 205;
    private static final double O3_RADIUS = 40;
    private static final double O3_BOUNCINESS = BOUNCY_BOUNCINESS;

    private static final double O4_X = 700;
    private static final double O4_Y = 185;
    private static final double O4_RADIUS = 40;
    private static final double O4_BOUNCINESS = STICKY_BOUNCINESS;

    private static final double BALL_RADIUS = 30;
    private static final Vector2D BALL_INITIAL_POSITION = new Vector2D(1800, 80);
    private static final double HOLE_RADIUS = 30;
    private static final Vector2D HOLE_POSITION = new Vector2D(1780, 980);

    private final SurfaceFactory surfaceFactory;

    /**
     * Constructs a FirstMap using a default SurfaceFactory implementation.
     */
    public MapD() {
            this(new SurfaceFactoryImpl());
    }

    /**
     * Constructs a FirstMap using the provided SurfaceFactory.
     * 
     * @param surfaceFactory the factory used to build surfaces
     */
    public MapD(final SurfaceFactory surfaceFactory) {
            this.surfaceFactory = surfaceFactory;
    }

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
            surfaces.add(surfaceFactory.createGrass(
                            new Rectangle(new Vector2D(GRASS_X, GRASS_Y), GRASS_WIDTH,
                            GRASS_HEIGHT), GRASS_Z_INDEX));

            surfaces.add(surfaceFactory.createSand(
                            new Rectangle(new Vector2D(SAND_X, SAND_Y), SAND_WIDTH,
                                            SAND_HEIGHT), SAND_Z_INDEX));
            surfaces.add(surfaceFactory.createIce(
                            new Rectangle(new Vector2D(ICE_X, ICE_Y), ICE_WIDTH,
                                            ICE_HEIGHT), ICE_Z_INDEX));
            obstacles.add(new WallObstacle(new Vector2D(W1_X, W1_Y), W1_WIDTH, W1_HEIGHT));
            obstacles.add(new WallObstacle(new Vector2D(W2_X, W2_Y), W2_WIDTH, W2_HEIGHT));
            obstacles.add(new WallObstacle(new Vector2D(W3_X, W3_Y), W3_WIDTH, W3_HEIGHT));
            obstacles.add(new WallObstacle(new Vector2D(W4_X, W4_Y), W4_WIDTH, W4_HEIGHT));
            obstacles.add(new RoundObstacle(new Vector2D(O1_X, O1_Y), O1_RADIUS, O1_BOUNCINESS));
            obstacles.add(new RoundObstacle(new Vector2D(O2_X, O2_Y), O2_RADIUS, O2_BOUNCINESS));
            obstacles.add(new RoundObstacle(new Vector2D(O3_X, O3_Y), O3_RADIUS, O3_BOUNCINESS));
            obstacles.add(new RoundObstacle(new Vector2D(O4_X, O4_Y), O4_RADIUS, O4_BOUNCINESS));
            return new GameMapImpl(surfaces, new BallImpl(BALL_INITIAL_POSITION, BALL_RADIUS),
                            new HoleImpl(HOLE_POSITION, HOLE_RADIUS), obstacles);
    }
}

