package it.unibo.minigoolf.model.map.factories;

import it.unibo.minigoolf.model.ball.BallImpl;
import it.unibo.minigoolf.model.hole.HoleImpl;
import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.map.GameMapImpl;
import it.unibo.minigoolf.model.obstacles.Obstacle;
import it.unibo.minigoolf.model.obstacles.RoundObstacle;
import it.unibo.minigoolf.model.obstacles.TriangleObstacle;
import it.unibo.minigoolf.model.obstacles.WallObstacle;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.model.surfaces.factory.SurfaceFactory;
import it.unibo.minigoolf.model.surfaces.factory.SurfaceFactoryImpl;
import it.unibo.minigoolf.model.surfaces.wind.WindDirection;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Circle;
import it.unibo.minigoolf.util.shapes.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * My first map.
 *
 * @author fedesparvo1-a11y
 */

public final class MapB implements GameMapFactory { 

    // Grass start area
    private static final double GRASS_START_X = 0;
    private static final double GRASS_START_Y = 0;
    private static final double GRASS_START_W = 400;
    private static final double GRASS_START_H = 1080;
    private static final int GRASS_START_Z = 0;

    // Ice corridor 
    private static final double ICE_X = 400;
    private static final double ICE_Y = 390;
    private static final double ICE_W = 1120;
    private static final double ICE_H = 300;
    private static final int ICE_Z = 1;

    // Sand trap circle inside the corridor
    private static final Vector2D SAND_CIRCLE_POS = new Vector2D(960, 540);
    private static final double SAND_CIRCLE_R = 90;
    private static final int SAND_CIRCLE_Z = 2;

    // Small solid obstacle concentric with the sand trap
    private static final Vector2D CENTER_OBS_POS = new Vector2D(960, 540);
    private static final double CENTER_OBS_R = 30;

    // Windy grass exit area
    private static final double WINDY_EXIT_X = 1520;
    private static final double WINDY_EXIT_Y = 0;
    private static final double WINDY_EXIT_W = 400;
    private static final double WINDY_EXIT_H = 1080;
    private static final int WINDY_EXIT_Z = 0;
    private static final double WIND_STRENGTH = 10.0;

    // Boundary walls 
    private static final double W_TOP_X = 0;
    private static final double W_TOP_Y = 0;
    private static final double W_TOP_W = 1920;
    private static final double W_TOP_H = 31;

    private static final double W_BOT_X = 0;
    private static final double W_BOT_Y = 1049;
    private static final double W_BOT_W = 1920;
    private static final double W_BOT_H = 31;

    private static final double W_LEFT_X = 0;
    private static final double W_LEFT_Y = 0;
    private static final double W_LEFT_W = 31;
    private static final double W_LEFT_H = 1080;

    private static final double W_RIGHT_X = 1889;
    private static final double W_RIGHT_Y = 0;
    private static final double W_RIGHT_W = 31;
    private static final double W_RIGHT_H = 1080;

    // Corridor upper wall
    private static final double COR_TOP_X = 400;
    private static final double COR_TOP_Y = 359;
    private static final double COR_TOP_W = 1120;
    private static final double COR_TOP_H = 31;

    // Corridor lower wall
    private static final double COR_BOT_X = 400;
    private static final double COR_BOT_Y = 685;
    private static final double COR_BOT_W = 1120;
    private static final double COR_BOT_H = 40;

    // Round obstacles inside the ice corridor
    private static final Vector2D OBS1_POS = new Vector2D(600, 450);
    private static final double OBS1_R = 30;

    private static final Vector2D OBS2_POS = new Vector2D(600, 630);
    private static final double OBS2_R = 30;

    private static final Vector2D OBS3_POS = new Vector2D(1100, 460);
    private static final double OBS3_R = 35;

    private static final Vector2D OBS4_POS = new Vector2D(1300, 620);
    private static final double OBS4_R = 35;

    // Left gap — upper block (between left grass area and corridor top wall)
    private static final double GAP_L_TOP_X = 400;
    private static final double GAP_L_TOP_Y = 31;
    private static final double GAP_L_TOP_W = 31;
    private static final double GAP_L_TOP_H = 328; // from top wall to corridor top wall

    // Left gap — lower block (between corridor bottom wall and bottom boundary)
    private static final double GAP_L_BOT_X = 400;
    private static final double GAP_L_BOT_Y = 721;
    private static final double GAP_L_BOT_W = 31;
    private static final double GAP_L_BOT_H = 328; // from corridor bot wall to bottom boundary

    // Right gap — upper block (between corridor top wall and top boundary)
    private static final double GAP_R_TOP_X = 1489;
    private static final double GAP_R_TOP_Y = 31;
    private static final double GAP_R_TOP_W = 31;
    private static final double GAP_R_TOP_H = 328;

    // Right gap — lower block (between corridor bottom wall and bottom boundary)
    private static final double GAP_R_BOT_X = 1489;
    private static final double GAP_R_BOT_Y = 721;
    private static final double GAP_R_BOT_W = 31;
    private static final double GAP_R_BOT_H = 328;

    // Triangle obstacle near the exit, points downward
    private static final Vector2D TRI_V1 = new Vector2D(1450, 359);
    private static final Vector2D TRI_V2 = new Vector2D(1520, 359);
    private static final Vector2D TRI_V3 = new Vector2D(1485, 480);

    // Ball and hole
    private static final Vector2D BALL_POS = new Vector2D(80, 540);
    private static final double BALL_R = 30;
    private static final Vector2D HOLE_POS = new Vector2D(1840, 540);
    private static final double HOLE_R = 40;

    private final SurfaceFactory surfaceFactory;

    /**
     * Constructs the map using a default SurfaceFactory implementation.
     */
    public MapB() {
        this(new SurfaceFactoryImpl());
    }

    /**
     * Constructs the map using the provided SurfaceFactory.
     *
     * @param surfaceFactory the factory used to build surfaces
     */
    public MapB(final SurfaceFactory surfaceFactory) {
        this.surfaceFactory = surfaceFactory;
    }

    /** {@inheritDoc} */
    @Override
    public GameMap buildGameMap() {
        final List<Surface> surfaces = new ArrayList<>();
        final List<Obstacle> obstacles = new ArrayList<>();

        // Grass start
        surfaces.add(surfaceFactory.createGrass(
                new Rectangle(new Vector2D(GRASS_START_X, GRASS_START_Y), GRASS_START_W, GRASS_START_H),
                GRASS_START_Z));

        // Ice corridor
        surfaces.add(surfaceFactory.createIce(
                new Rectangle(new Vector2D(ICE_X, ICE_Y), ICE_W, ICE_H),
                ICE_Z));

        // Sand trap circle
        surfaces.add(surfaceFactory.createSand(
                new Circle(SAND_CIRCLE_POS, SAND_CIRCLE_R),
                SAND_CIRCLE_Z));

        // Windy grass exit
        surfaces.add(surfaceFactory.createWindy(
                surfaceFactory.createGrass(
                        new Rectangle(new Vector2D(WINDY_EXIT_X, WINDY_EXIT_Y), WINDY_EXIT_W, WINDY_EXIT_H),
                        WINDY_EXIT_Z),
                WindDirection.UP, WIND_STRENGTH));

        // Boundary walls
        obstacles.add(new WallObstacle(new Vector2D(W_TOP_X, W_TOP_Y), W_TOP_W, W_TOP_H));
        obstacles.add(new WallObstacle(new Vector2D(W_BOT_X, W_BOT_Y), W_BOT_W, W_BOT_H));
        obstacles.add(new WallObstacle(new Vector2D(W_LEFT_X, W_LEFT_Y), W_LEFT_W, W_LEFT_H));
        obstacles.add(new WallObstacle(new Vector2D(W_RIGHT_X, W_RIGHT_Y), W_RIGHT_W, W_RIGHT_H));

        // Corridor walls
        obstacles.add(new WallObstacle(new Vector2D(COR_TOP_X, COR_TOP_Y), COR_TOP_W, COR_TOP_H));
        obstacles.add(new WallObstacle(new Vector2D(COR_BOT_X, COR_BOT_Y), COR_BOT_W, COR_BOT_H));

        // Closing walls
        obstacles.add(new WallObstacle(new Vector2D(GAP_L_TOP_X, GAP_L_TOP_Y), GAP_L_TOP_W, GAP_L_TOP_H));
        obstacles.add(new WallObstacle(new Vector2D(GAP_L_BOT_X, GAP_L_BOT_Y), GAP_L_BOT_W, GAP_L_BOT_H));
        obstacles.add(new WallObstacle(new Vector2D(GAP_R_TOP_X, GAP_R_TOP_Y), GAP_R_TOP_W, GAP_R_TOP_H));
        obstacles.add(new WallObstacle(new Vector2D(GAP_R_BOT_X, GAP_R_BOT_Y), GAP_R_BOT_W, GAP_R_BOT_H));

        // Round obstacles
        obstacles.add(new RoundObstacle(OBS1_POS, OBS1_R));
        obstacles.add(new RoundObstacle(OBS2_POS, OBS2_R));
        obstacles.add(new RoundObstacle(OBS3_POS, OBS3_R));
        obstacles.add(new RoundObstacle(OBS4_POS, OBS4_R));
        obstacles.add(new RoundObstacle(CENTER_OBS_POS, CENTER_OBS_R));

        // Triangle near exit
        obstacles.add(new TriangleObstacle(TRI_V1, TRI_V2, TRI_V3));

        return new GameMapImpl(
                surfaces,
                new BallImpl(BALL_POS, BALL_R),
                new HoleImpl(HOLE_POS, HOLE_R),
                obstacles);
    }
}
