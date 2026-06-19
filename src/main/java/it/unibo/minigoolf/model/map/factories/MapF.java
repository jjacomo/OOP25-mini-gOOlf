package it.unibo.minigoolf.model.map.factories;

import java.util.ArrayList;
import java.util.List;

import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.model.surfaces.factory.SurfaceFactory;
import it.unibo.minigoolf.model.surfaces.factory.SurfaceFactoryImpl;
import it.unibo.minigoolf.model.obstacles.Obstacle;
import it.unibo.minigoolf.model.obstacles.RoundObstacle;
import it.unibo.minigoolf.model.obstacles.TriangleObstacle;
import it.unibo.minigoolf.model.obstacles.WallObstacle;
import it.unibo.minigoolf.model.ball.BallImpl;
import it.unibo.minigoolf.model.hole.HoleImpl;
import it.unibo.minigoolf.model.map.GameMapImpl;
import it.unibo.minigoolf.model.surfaces.wind.WindDirection;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Oval;
import it.unibo.minigoolf.util.shapes.Rectangle;
import it.unibo.minigoolf.util.shapes.Triangle;

public class MapF implements GameMapFactory {

    private static final double GRASS_X = 0;
    private static final double GRASS_Y = 0;
    private static final double GRASS_WIDTH = 1920;
    private static final double GRASS_HEIGHT = 1080;
    private static final int GRASS_Z_INDEX = 0;
    private static final Vector2D DIRT_V1 = new Vector2D(800, 830);
    private static final Vector2D DIRT_V2 = new Vector2D(300, 300);
    private static final Vector2D DIRT_V3 = new Vector2D(300, 830);
    private static final int DIRT_Z_INDEX = 2;
    private static final double WINDY_GRASS_X = 0;
    private static final double WINDY_GRASS_Y = 300;
    private static final double WINDY_GRASS_WIDTH = 300;
    private static final double WINDY_GRASS_HEIGHT = 780;
    private static final int WINDY_GRASS_Z_INDEX = 1;
    private static final double WIND_STRENGTH = 12.5;
    private static final double DIRT_X = 300;
    private static final double DIRT_Y = 830;
    private static final double DIRT_WIDTH = 600;
    private static final double DIRT_HEIGHT = 250;

    private static final double DIRT_OVAL_X = 900;
    private static final double DIRT_OVAL_Y = 1000;
    private static final double DIRT_OVAL_WIDTH = 200;
    private static final double DIRT_OVAL_HEIGHT = 200;

    private static final double BOOST_SAND_X = 900;
    private static final double BOOST_SAND_Y = 400;
    private static final double BOOST_SAND_WIDTH = 300;
    private static final double BOOST_SAND_HEIGHT = 200;
    private static final int BOOST_SAND_Z_INDEX = 3;
    private static final double BOOST_INTENSITY = 2;

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

    private static final double O1_X = 800;
    private static final double O1_Y = 800;
    private static final double O1_WIDTH = 1120;
    private static final double O1_HEIGHT = 31;
    private static final Vector2D O2_V1 = new Vector2D(800, 800);
    private static final Vector2D O2_V2 = new Vector2D(800, 830);
    private static final Vector2D O2_V3 = new Vector2D(300, 300);
    private static final Vector2D O3_V1 = new Vector2D(800, 830);
    private static final Vector2D O3_V2 = new Vector2D(300, 300);
    private static final Vector2D O3_V3 = new Vector2D(300, 330);
    private static final Vector2D O4_V1 = new Vector2D(550, 0);
    private static final Vector2D O4_V2 = new Vector2D(1050, 0);
    private static final Vector2D O4_V3 = new Vector2D(800, 300);

    private static final Vector2D BOUNCY1_OBS_POS = new Vector2D(1500, 300);
    private static final double BOUNCY1_OBS_RADIUS = 40;
    private static final double BOUNCINESS1 = 1.5;
    private static final Vector2D BOUNCY2_OBS_POS = new Vector2D(0, 0);
    private static final double BOUNCY2_OBS_RADIUS = 150;
    private static final double BOUNCINESS2 = 1.5;

    private static final Vector2D BALL_POSITION = new Vector2D(1800, 700);
    private static final double BALL_RADIUS = 30;
    private static final Vector2D HOLE_POSITION = new Vector2D(1800, 940);
    private static final double HOLE_RADIUS = 33;


    private final SurfaceFactory surfaceFactory;

    /**
     * Constructs the map using a default SurfaceFactory implementation.
     */
    public MapF() {
        this(new SurfaceFactoryImpl());
    }

    /**
     * Constructs the map using the provided SurfaceFactory.
     *
     * @param surfaceFactory the factory used to build surfaces
     */
    public MapF(final SurfaceFactory surfaceFactory) {
        this.surfaceFactory = surfaceFactory;
    }
    @Override
    public GameMap buildGameMap() {
        final List<Surface> surfaces = new ArrayList<>();
        final List<Obstacle> obstacles = new ArrayList<>();

        surfaces.add(surfaceFactory
                .createGrass(new Rectangle(new Vector2D(GRASS_X, GRASS_Y), GRASS_WIDTH, GRASS_HEIGHT), GRASS_Z_INDEX));
        surfaces.add(surfaceFactory
                .createDirt(new Triangle(DIRT_V1, DIRT_V2, DIRT_V3), DIRT_Z_INDEX));
        surfaces.add(surfaceFactory
                .createWindy(
                        surfaceFactory.createGrass(new Rectangle(new Vector2D(WINDY_GRASS_X, WINDY_GRASS_Y),
                                WINDY_GRASS_WIDTH, WINDY_GRASS_HEIGHT), WINDY_GRASS_Z_INDEX),
                        WindDirection.UP, WIND_STRENGTH));
        surfaces.add(surfaceFactory
            .createDirt(new Rectangle(new Vector2D(DIRT_X, DIRT_Y), DIRT_WIDTH, DIRT_HEIGHT), DIRT_Z_INDEX)
        );
        surfaces.add(surfaceFactory
            .createDirt(new Oval(new Vector2D(DIRT_OVAL_X, DIRT_OVAL_Y), DIRT_OVAL_WIDTH, DIRT_OVAL_HEIGHT), DIRT_Z_INDEX)
        );
        surfaces.add(surfaceFactory.createBoost(surfaceFactory.createSand(
                new Oval(new Vector2D(BOOST_SAND_X, BOOST_SAND_Y), BOOST_SAND_WIDTH, BOOST_SAND_HEIGHT),
                BOOST_SAND_Z_INDEX+2), BOOST_INTENSITY)
        );
        // surfaces.add(surfaceFactory.createSand(
        //         new Oval(new Vector2D(BOOST_SAND_X, BOOST_SAND_Y), BOOST_SAND_WIDTH, BOOST_SAND_HEIGHT),
        //         BOOST_SAND_Z_INDEX+1)
        // );

        obstacles.add(new RoundObstacle(BOUNCY2_OBS_POS, BOUNCY2_OBS_RADIUS, BOUNCINESS2));
        obstacles.add(new WallObstacle(new Vector2D(W1_X, W1_Y), W1_WIDTH, W1_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(W2_X, W2_Y), W2_WIDTH, W2_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(W3_X, W3_Y), W3_WIDTH, W3_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(W4_X, W4_Y), W4_WIDTH, W4_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(O1_X, O1_Y), O1_WIDTH, O1_HEIGHT));
        obstacles.add(new TriangleObstacle(O2_V1, O2_V2, O2_V3));
        obstacles.add(new TriangleObstacle(O3_V1, O3_V2, O3_V3));
        obstacles.add(new RoundObstacle(BOUNCY1_OBS_POS, BOUNCY1_OBS_RADIUS, BOUNCINESS1));
        obstacles.add(new TriangleObstacle(O4_V1, O4_V2, O4_V3));
        return new GameMapImpl(surfaces, new BallImpl(BALL_POSITION, BALL_RADIUS),
                new HoleImpl(HOLE_POSITION, HOLE_RADIUS), obstacles);
    }
}
