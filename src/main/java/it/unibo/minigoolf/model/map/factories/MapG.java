package it.unibo.minigoolf.model.map.factories;

import java.util.ArrayList;
import java.util.List;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.model.ball.BallImpl;
import it.unibo.minigoolf.model.hole.HoleImpl;
import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.map.GameMapImpl;
import it.unibo.minigoolf.model.obstacles.AbstractObstacle;
import it.unibo.minigoolf.model.obstacles.Obstacle;
import it.unibo.minigoolf.model.obstacles.PortalObstacle;
import it.unibo.minigoolf.model.obstacles.TriangleObstacle;
import it.unibo.minigoolf.model.obstacles.WallObstacle;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.model.surfaces.factory.SurfaceFactory;
import it.unibo.minigoolf.model.surfaces.factory.SurfaceFactoryImpl;
import it.unibo.minigoolf.model.surfaces.wind.WindDirection;
import it.unibo.minigoolf.util.shapes.Rectangle;

/**
 * Seventh map.
 * 
 * @author Mattia
 * 
 * @see GameMapFactory
 * @see GameMap
 */
public class MapG implements GameMapFactory {

    // GRASS
    private static final double GRASS_X = 0;
    private static final double GRASS_Y = 0;
    private static final double GRASS_WIDTH = 1920;
    private static final double GRASS_HEIGHT = 1080;
    private static final int GRASS_Z_INDEX = 0;

    // SAND 
    private static final double SAND_X = 31;
    private static final double SAND_Y = 31;
    private static final double SAND_WIDTH = 1858;
    private static final double SAND_HEIGHT = 250;
    private static final int SAND_Z_INDEX = 2;

    // WINDY GRASS
    private static final double WINDY_GRASS_X = 31;
    private static final double WINDY_GRASS_Y = 300;
    private static final double WINDY_GRASS_WIDTH = 369;
    private static final double WINDY_GRASS_HEIGHT = 430;
    private static final int WINDY_GRASS_Z_INDEX = 1;
    private static final double WIND_STRENGTH = 12;

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

    // WALL OBSTACLES
    private static final double O1_X = 400;
    private static final double O1_Y = 31;
    private static final double O1_WIDTH = 100;
    private static final double O1_HEIGHT = 1018;

    private static final double O2_X = 750;
    private static final double O2_Y = 281;
    private static final double O2_WIDTH = 100;
    private static final double O2_HEIGHT = 868;

    private static final double O3_X = 1100;
    private static final double O3_Y = 31;
    private static final double O3_WIDTH = 100;
    private static final double O3_HEIGHT = 768;

    private static final double O4_X = 1450;
    private static final double O4_Y = 281;
    private static final double O4_WIDTH = 100;
    private static final double O4_HEIGHT = 868;

    // TRIANGLE OBSTACLES
    private static final Vector2D O5_VERTEX_A = new Vector2D(499, 31);
    private static final Vector2D O5_VERTEX_B = new Vector2D(649, 31);
    private static final Vector2D O5_VERTEX_C = new Vector2D(499, 181);
    private static final double O5_BOUNCINESS = AbstractObstacle.BOUNCY_BOUNCINESS;

    private static final Vector2D O6_VERTEX_A = new Vector2D(950, 31);
    private static final Vector2D O6_VERTEX_B = new Vector2D(1100, 31);
    private static final Vector2D O6_VERTEX_C = new Vector2D(1100, 181);
    private static final double O6_BOUNCINESS = AbstractObstacle.BOUNCY_BOUNCINESS;

    private static final Vector2D O7_VERTEX_A = new Vector2D(849, 899);
    private static final Vector2D O7_VERTEX_B = new Vector2D(849, 1048);
    private static final Vector2D O7_VERTEX_C = new Vector2D(999, 1048);
    private static final double O7_BOUNCINESS = AbstractObstacle.BOUNCY_BOUNCINESS;

    private static final Vector2D O8_VERTEX_A = new Vector2D(1449, 899);
    private static final Vector2D O8_VERTEX_B = new Vector2D(1299, 1048);
    private static final Vector2D O8_VERTEX_C = new Vector2D(1449, 1048);
    private static final double O8_BOUNCINESS = AbstractObstacle.BOUNCY_BOUNCINESS;

    private static final Vector2D O9_VERTEX_A = new Vector2D(1199, 31);
    private static final Vector2D O9_VERTEX_B = new Vector2D(1349, 31);
    private static final Vector2D O9_VERTEX_C = new Vector2D(1199, 181);
    private static final double O9_BOUNCINESS = AbstractObstacle.BOUNCY_BOUNCINESS;

    // PORTAL OBSTACLES
    private static final Vector2D O10_PORTAL_A = new Vector2D(215, 100);
    private static final Vector2D O11_PORTAL_B = new Vector2D(625, 950);
    private static final double PORTAL_RADIUS = 30;

    // BALL
    private static final double BALL_RADIUS = 30;
    private static final Vector2D BALL_INITIAL_POSITION = new Vector2D(215, 950);

    // HOLE
    private static final double HOLE_RADIUS = 30;
    private static final Vector2D HOLE_POSITION = new Vector2D(1720, 950);

    private final SurfaceFactory surfaceFactory;

    /**
     * Constructs the map using a default SurfaceFactory implementation.
     */
    public MapG() {
            this(new SurfaceFactoryImpl());
    }

    /**
     * Constructs the map using the provided SurfaceFactory.
     * 
     * @param surfaceFactory the factory used to build surfaces
     */
    public MapG(final SurfaceFactory surfaceFactory) {
            this.surfaceFactory = surfaceFactory;
    }

    /**
     * Builds the seventh game map.
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
        surfaces.add(surfaceFactory.createWindy(
                surfaceFactory.createGrass(
                        new Rectangle(new Vector2D(WINDY_GRASS_X, WINDY_GRASS_Y),
                        WINDY_GRASS_WIDTH, WINDY_GRASS_HEIGHT), WINDY_GRASS_Z_INDEX),
                        WindDirection.DOWN, WIND_STRENGTH));

        obstacles.add(new WallObstacle(new Vector2D(W1_X, W1_Y), W1_WIDTH, W1_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(W2_X, W2_Y), W2_WIDTH, W2_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(W3_X, W3_Y), W3_WIDTH, W3_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(W4_X, W4_Y), W4_WIDTH, W4_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(O1_X, O1_Y), O1_WIDTH, O1_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(O2_X, O2_Y), O2_WIDTH, O2_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(O3_X, O3_Y), O3_WIDTH, O3_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(O4_X, O4_Y), O4_WIDTH, O4_HEIGHT));
        obstacles.add(new TriangleObstacle(O5_VERTEX_A, O5_VERTEX_B, O5_VERTEX_C, 
                                                O5_BOUNCINESS));
        obstacles.add(new TriangleObstacle(O6_VERTEX_A, O6_VERTEX_B, O6_VERTEX_C, 
                                                O6_BOUNCINESS));
        obstacles.add(new TriangleObstacle(O7_VERTEX_A, O7_VERTEX_B, O7_VERTEX_C, 
                                                O7_BOUNCINESS));
        obstacles.add(new TriangleObstacle(O8_VERTEX_A, O8_VERTEX_B, O8_VERTEX_C, 
                                                O8_BOUNCINESS));
        obstacles.add(new TriangleObstacle(O9_VERTEX_A, O9_VERTEX_B, O9_VERTEX_C, 
                                                O9_BOUNCINESS));
        obstacles.addAll(PortalObstacle.createPair(O10_PORTAL_A, O11_PORTAL_B, 
                                                        PORTAL_RADIUS));

        return new GameMapImpl(surfaces, new BallImpl(BALL_INITIAL_POSITION, BALL_RADIUS),
                new HoleImpl(HOLE_POSITION, HOLE_RADIUS), obstacles);
    }
}
