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
import it.unibo.minigoolf.model.obstacles.RoundObstacle;
import it.unibo.minigoolf.model.obstacles.TriangleObstacle;
import it.unibo.minigoolf.model.obstacles.WallObstacle;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.model.surfaces.factory.SurfaceFactory;
import it.unibo.minigoolf.model.surfaces.factory.SurfaceFactoryImpl;
import it.unibo.minigoolf.model.surfaces.wind.WindDirection;
import it.unibo.minigoolf.util.shapes.Rectangle;

/**
 * Tutorial map.
 * 
 * @author dbakko
 * @see GameMapFactory
 * @see GameMap
 */
public final class MapT implements GameMapFactory {

    private static final double GRASS_X = 0;
    private static final double GRASS_Y = 0;
    private static final double LOGICAL_WIDTH = 1920;
    private static final double LOGICAL_HEIGHT = 1080;
    private static final int GRASS_Z_INDEX = 0;

    private static final double SURF_Y = 150;
    private static final double SURF_SIZE = 200;
    private static final int SURF_Z = 1;

    private static final double DIRT_X = 60;
    private static final double SAND_X = 460;
    private static final double ICE_X = 860;
    private static final double WINDY_X = 1260;
    private static final double BOOST_X = 1660;

    private static final double WIND_STR = 10.0;
    private static final double BOOST_INT = 1.5;

    private static final double WALL_X = 60;
    private static final double WALL_Y = 550;
    private static final double WALL_W = 200;
    private static final double WALL_H = 100;
    private static final Vector2D TRI_V1 = new Vector2D(460, 650);
    private static final Vector2D TRI_V2 = new Vector2D(660, 650);
    private static final Vector2D TRI_V3 = new Vector2D(560, 500);
    private static final double ROUND_NORM_X = 960;
    private static final double ROUND_NORM_Y = 600;
    private static final double ROUND_R = 80;
    private static final double ROUND_BOUNCY_X = 1360;
    private static final double ROUND_BOUNCY_Y = 600;
    private static final double BOUNCINESS = AbstractObstacle.BOUNCY_BOUNCINESS;
    private static final Vector2D PORTAL_A = new Vector2D(1760, 520);
    private static final Vector2D PORTAL_B = new Vector2D(1760, 680);
    private static final double PORTAL_R = 50;
    private static final Vector2D BALL_POS = new Vector2D(560, 900);
    private static final double BALL_R = 30;
    private static final Vector2D HOLE_POS = new Vector2D(1360, 900);
    private static final double HOLE_R = 40;

    private static final double BORDER_THICKNESS = 31;

    private final SurfaceFactory surfaceFactory;

    /**
     * Constructs the map using a default SurfaceFactory implementation.
     */
    public MapT() {
        this(new SurfaceFactoryImpl());
    }

    /**
     * Constructs the map using the provided SurfaceFactory.
     * 
     * @param surfaceFactory the factory used to build surfaces
     */
    public MapT(final SurfaceFactory surfaceFactory) {
        this.surfaceFactory = surfaceFactory;
    }

    @Override
    public GameMap buildGameMap() {
        final List<Surface> surfaces = new ArrayList<>();
        final List<Obstacle> obstacles = new ArrayList<>();

        surfaces.add(surfaceFactory.createGrass(
                new Rectangle(new Vector2D(GRASS_X, GRASS_Y), LOGICAL_WIDTH, LOGICAL_HEIGHT), 
                GRASS_Z_INDEX));

        surfaces.add(surfaceFactory.createDirt(
                new Rectangle(new Vector2D(DIRT_X, SURF_Y), SURF_SIZE, SURF_SIZE), SURF_Z));

        surfaces.add(surfaceFactory.createSand(
                new Rectangle(new Vector2D(SAND_X, SURF_Y), SURF_SIZE, SURF_SIZE), SURF_Z));

        surfaces.add(surfaceFactory.createIce(
                new Rectangle(new Vector2D(ICE_X, SURF_Y), SURF_SIZE, SURF_SIZE), SURF_Z));

        surfaces.add(surfaceFactory.createWindy(
                surfaceFactory.createGrass(
                    new Rectangle(new Vector2D(WINDY_X, SURF_Y), SURF_SIZE, SURF_SIZE), SURF_Z), 
                WindDirection.UP, WIND_STR));

        surfaces.add(surfaceFactory.createBoost(
                surfaceFactory.createSand(
                    new Rectangle(new Vector2D(BOOST_X, SURF_Y), SURF_SIZE, SURF_SIZE), SURF_Z), 
                BOOST_INT));

        obstacles.add(new WallObstacle(new Vector2D(WALL_X, WALL_Y), WALL_W, WALL_H));
        obstacles.add(new TriangleObstacle(TRI_V1, TRI_V2, TRI_V3));
        obstacles.add(new RoundObstacle(new Vector2D(ROUND_NORM_X, ROUND_NORM_Y), ROUND_R));
        obstacles.add(new RoundObstacle(new Vector2D(ROUND_BOUNCY_X, ROUND_BOUNCY_Y), ROUND_R, BOUNCINESS));
        obstacles.addAll(PortalObstacle.createPair(PORTAL_A, PORTAL_B, PORTAL_R));

        obstacles.add(new WallObstacle(new Vector2D(0, 0), BORDER_THICKNESS, LOGICAL_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(0, 0), LOGICAL_WIDTH, BORDER_THICKNESS));
        obstacles.add(new WallObstacle(new Vector2D(LOGICAL_WIDTH - BORDER_THICKNESS, 0), BORDER_THICKNESS, LOGICAL_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(0, LOGICAL_HEIGHT - BORDER_THICKNESS), LOGICAL_WIDTH, BORDER_THICKNESS));

        return new GameMapImpl(
                surfaces, 
                new BallImpl(BALL_POS, BALL_R),
                new HoleImpl(HOLE_POS, HOLE_R), 
                obstacles);
    }
}
