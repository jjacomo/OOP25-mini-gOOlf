package it.unibo.minigoolf.model.map.factories;

import it.unibo.minigoolf.model.ball.BallImpl;
import it.unibo.minigoolf.model.hole.HoleImpl;
import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.map.GameMapImpl;
import it.unibo.minigoolf.model.obstacles.Obstacle;
import it.unibo.minigoolf.model.obstacles.PortalObstacle;
import it.unibo.minigoolf.model.obstacles.RoundObstacle;
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
 * My second map.
 *
 * @author fedesparvo1-a11y
 */
public final class MapE implements GameMapFactory {

    // Surfaces
    private static final double GRASS_START_X = 0;
    private static final double GRASS_START_Y = 540;
    private static final double GRASS_START_W = 500;
    private static final double GRASS_START_H = 540;
    private static final int GRASS_START_Z = 0;

    private static final double ICE_X = 500;
    private static final double ICE_Y = 540;
    private static final double ICE_W = 800;
    private static final double ICE_H = 540;
    private static final int ICE_Z = 1;

    private static final Vector2D SAND_CIRCLE_POS = new Vector2D(1000, 800);
    private static final double SAND_CIRCLE_R = 110;
    private static final int SAND_CIRCLE_Z = 2;

    private static final double WINDY_EXIT_X = 1400;
    private static final double WINDY_EXIT_Y = 0;
    private static final double WINDY_EXIT_W = 520;
    private static final double WINDY_EXIT_H = 540;
    private static final int WINDY_EXIT_Z = 0;

    private static final double WIND_STRENGTH = 12.0;

    // Border walls
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

    //Extra walls
    private static final double START_TOP_WALL_X = 0;
    private static final double START_TOP_WALL_Y = 509;
    private static final double START_TOP_WALL_W = 1300;
    private static final double START_TOP_WALL_H = 31;

    private static final double ICE_RIGHT_WALL_X = 1270;
    private static final double ICE_RIGHT_WALL_Y = 540;
    private static final double ICE_RIGHT_WALL_W = 31;
    private static final double ICE_RIGHT_WALL_H = 540;

    private static final double WIND_LEFT_WALL_X = 1370;
    private static final double WIND_LEFT_WALL_Y = 0;
    private static final double WIND_LEFT_WALL_W = 31;
    private static final double WIND_LEFT_WALL_H = 540;

    private static final double WIND_BOTTOM_WALL_X = 1370;
    private static final double WIND_BOTTOM_WALL_Y = 540;
    private static final double WIND_BOTTOM_WALL_W = 550;
    private static final double WIND_BOTTOM_WALL_H = 31;

    // Walls near the hole
    private static final double HOLE_GUARD_WALL_X = 1710;
    private static final double HOLE_GUARD_WALL_Y = 80;
    private static final double HOLE_GUARD_WALL_W = 31;
    private static final double HOLE_GUARD_WALL_H = 250;

    // Round obstacles

    private static final Vector2D OBS1_POS = new Vector2D(650, 700);
    private static final double OBS1_R = 40;

    private static final Vector2D OBS2_POS = new Vector2D(850, 920);
    private static final double OBS2_R = 40;

    private static final Vector2D OBS3_POS = new Vector2D(1080, 620);
    private static final double OBS3_R = 45;

    private static final Vector2D OBS4_POS = new Vector2D(1200, 880);
    private static final double OBS4_R = 35;

    private static final Vector2D OBS5_POS = new Vector2D(1650, 300);
    private static final double OBS5_R = 50;

    // Portals
    private static final Vector2D PORTAL_A_POS = new Vector2D(1180, 980);
    private static final Vector2D PORTAL_B_POS = new Vector2D(1500, 350);

    private static final double PORTAL_R = 55;

    // Ball and holes
    private static final Vector2D BALL_POS = new Vector2D(120, 800);
    private static final double BALL_R = 30;

    private static final Vector2D HOLE_POS = new Vector2D(1800, 150);
    private static final double HOLE_R = 40;

    private final SurfaceFactory surfaceFactory;

    /**
     * Constructs a map using a default SurfaceFactory implementation.
     */
    public MapE() {
            this(new SurfaceFactoryImpl());
    }

    /**
     * Constructs a map using the provided SurfaceFactory.
     * 
     * @param surfaceFactory the factory used to build surfaces
     */
    public MapE(final SurfaceFactory surfaceFactory) {
            this.surfaceFactory = surfaceFactory;
    }

    @Override
    public GameMap buildGameMap() {
        final List<Surface> surfaces = new ArrayList<>();
        final List<Obstacle> obstacles = new ArrayList<>();

        surfaces.add(surfaceFactory.createGrass(
                new Rectangle(
                        new Vector2D(GRASS_START_X, GRASS_START_Y),
                        GRASS_START_W,
                        GRASS_START_H),
                GRASS_START_Z));

        surfaces.add(surfaceFactory.createIce(
                new Rectangle(
                        new Vector2D(ICE_X, ICE_Y),
                        ICE_W,
                        ICE_H),
                ICE_Z));

        surfaces.add(surfaceFactory.createSand(
                new Circle(
                        SAND_CIRCLE_POS,
                        SAND_CIRCLE_R),
                SAND_CIRCLE_Z));

        surfaces.add(surfaceFactory.createWindy(
                surfaceFactory.createGrass(
                        new Rectangle(
                                new Vector2D(WINDY_EXIT_X, WINDY_EXIT_Y),
                                WINDY_EXIT_W,
                                WINDY_EXIT_H),
                        WINDY_EXIT_Z),
                WindDirection.UP,
                WIND_STRENGTH));

        // Border map
        obstacles.add(new WallObstacle(
                new Vector2D(W_TOP_X, W_TOP_Y),
                W_TOP_W,
                W_TOP_H));

        obstacles.add(new WallObstacle(
                new Vector2D(W_BOT_X, W_BOT_Y),
                W_BOT_W,
                W_BOT_H));

        obstacles.add(new WallObstacle(
                new Vector2D(W_LEFT_X, W_LEFT_Y),
                W_LEFT_W,
                W_LEFT_H));

        obstacles.add(new WallObstacle(
                new Vector2D(W_RIGHT_X, W_RIGHT_Y),
                W_RIGHT_W,
                W_RIGHT_H));

        // Border pieces of map
        obstacles.add(new WallObstacle(
                new Vector2D(START_TOP_WALL_X, START_TOP_WALL_Y),
                START_TOP_WALL_W,
                START_TOP_WALL_H));

        obstacles.add(new WallObstacle(
                new Vector2D(ICE_RIGHT_WALL_X, ICE_RIGHT_WALL_Y),
                ICE_RIGHT_WALL_W,
                ICE_RIGHT_WALL_H));

        obstacles.add(new WallObstacle(
                new Vector2D(WIND_LEFT_WALL_X, WIND_LEFT_WALL_Y),
                WIND_LEFT_WALL_W,
                WIND_LEFT_WALL_H));

        obstacles.add(new WallObstacle(
                new Vector2D(WIND_BOTTOM_WALL_X, WIND_BOTTOM_WALL_Y),
                WIND_BOTTOM_WALL_W,
                WIND_BOTTOM_WALL_H));

        obstacles.add(new WallObstacle(
                new Vector2D(HOLE_GUARD_WALL_X, HOLE_GUARD_WALL_Y),
                HOLE_GUARD_WALL_W,
                HOLE_GUARD_WALL_H));

        // Obstacles
        obstacles.add(new RoundObstacle(OBS1_POS, OBS1_R));
        obstacles.add(new RoundObstacle(OBS2_POS, OBS2_R));
        obstacles.add(new RoundObstacle(OBS3_POS, OBS3_R));
        obstacles.add(new RoundObstacle(OBS4_POS, OBS4_R));
        obstacles.add(new RoundObstacle(OBS5_POS, OBS5_R));

        // Portal
        obstacles.addAll(
                PortalObstacle.createPair(
                        PORTAL_A_POS,
                        PORTAL_B_POS,
                        PORTAL_R));

        return new GameMapImpl(
                surfaces,
                new BallImpl(BALL_POS, BALL_R),
                new HoleImpl(HOLE_POS, HOLE_R),
                obstacles);
    }
}
