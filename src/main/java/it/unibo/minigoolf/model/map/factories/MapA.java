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
import it.unibo.minigoolf.model.obstacles.TriangleObstacle;
import it.unibo.minigoolf.model.obstacles.WallObstacle;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.model.surfaces.factory.SurfaceFactory;
import it.unibo.minigoolf.model.surfaces.factory.SurfaceFactoryImpl;
import it.unibo.minigoolf.model.surfaces.wind.WindDirection;
import it.unibo.minigoolf.util.shapes.Circle;
import it.unibo.minigoolf.util.shapes.Oval;
import it.unibo.minigoolf.util.shapes.Rectangle;

/**
 * Map A.
 * 
 * @author jack
 * 
 */
public final class MapA implements GameMapFactory {

    private static final double GRASS1_X = 0;
    private static final double GRASS1_Y = 0;
    private static final double GRASS1_WIDTH = 550;
    private static final double GRASS1_HEIGHT = 540;
    private static final int GRASS1_Z_INDEX = 0;
    private static final double WINDYGRASS1_X = 0;
    private static final double WINDYGRASS1_Y = 540;
    private static final double WINDYGRASS1_WIDTH = 550;
    private static final double WINDYGRASS1_HEIGHT = 540;
    private static final int WINDYGRASS1_Z_INDEX = 0;
    private static final double WIND_STRENGTH = 12.5;
    private static final double DIRT_X = 550;
    private static final double DIRT_Y = 0;
    private static final double DIRT_WIDTH = 690;
    private static final double DIRT_HEIGHT = 540;
    private static final int DIRT_Z_INDEX = 1;
    private static final double DIRT2_X = 275;
    private static final double DIRT2_Y = 330;
    private static final double DIRT2_RADIUS = 180;
    private static final int DIRT2_Z_INDEX = 3;
    private static final double SAND_X = 550;
    private static final double SAND_Y = 540;
    private static final double SAND_WIDTH = 690;
    private static final double SAND_HEIGHT = 540;
    private static final int SAND_Z_INDEX = 2;
    private static final Vector2D SAND2_POSITION = new Vector2D(1780, 520);
    private static final double SAND2_RADIUS_X = 50;
    private static final double SAND2_RADIUS_Y = 110;
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
    private static final double ICE_X = 1240;
    private static final double ICE_Y = 270;
    private static final double ICE_WIDTH = 410;
    private static final double ICE_HEIGHT = 540;
    private static final int ICE_Z_INDEX = 4;

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
    private static final double O1_X = 525;
    private static final double O1_Y = 15;
    private static final double O1_WIDTH = 50;
    private static final double O1_HEIGHT = 700;
    private static final double O2_X = 800;
    private static final double O2_Y = 565;
    private static final double O2_WIDTH = 50;
    private static final double O2_HEIGHT = 500;
    private static final double O3_X = 1000;
    private static final double O3_Y = 15;
    private static final double O3_WIDTH = 50;
    private static final double O3_HEIGHT = 300;
    private static final double O4_X = 1215;
    private static final double O4_Y = 245;
    private static final double O4_WIDTH = 50;
    private static final double O4_HEIGHT = 590;
    private static final double O5_X = 1625;
    private static final double O5_Y = 245;
    private static final double O5_WIDTH = 50;
    private static final double O5_HEIGHT = 590;
    private static final double O6_X = 1215;
    private static final double O6_Y = 245;
    private static final double O6_WIDTH = 460;
    private static final double O6_HEIGHT = 50;
    private static final double O7_X = 1265;
    private static final double O7_Y = 785;
    private static final double O7_WIDTH = 410;
    private static final double O7_HEIGHT = 50;
    private static final double O8_X = 275;
    private static final double O8_Y = 330;
    private static final double O8_RADIUS = 100;
    private static final Vector2D O9_V1 = new Vector2D(850, 15);
    private static final Vector2D O9_V2 = new Vector2D(1000, 15);
    private static final Vector2D O9_V3 = new Vector2D(1000, 165);
    private static final Vector2D O10_V1 = new Vector2D(850, 1065);
    private static final Vector2D O10_V2 = new Vector2D(1100, 1065);
    private static final Vector2D O10_V3 = new Vector2D(850, 915);
    private static final double O11_X = 550;
    private static final double O11_Y = 715;
    private static final double O11_RADIUS = 25;
    private static final double O12_X = 275;
    private static final double O12_Y = 745;
    private static final double O12_RADIUS = 80;
    private static final double O13_X = 825;
    private static final double O13_Y = 557;
    private static final double O13_RADIUS = 25;
    private static final double O14_X = 1025;
    private static final double O14_Y = 310;
    private static final double O14_RADIUS = 25;

    private static final double BALL_RADIUS = 30;
    private static final Vector2D BALL_INITIAL_POSITION = new Vector2D(80, 80);
    private static final double HOLE_RADIUS = 33;
    private static final Vector2D HOLE_POSITION = new Vector2D(1780, 520);

    private final SurfaceFactory surfaceFactory;

    /**
     * Constructs MapA using a default SurfaceFactory implementation.
     */
    public MapA() {
        this(new SurfaceFactoryImpl());
    }

    /**
     * Constructs MapA using the provided SurfaceFactory.
     * 
     * @param surfaceFactory the factory used to build surfaces
     */
    public MapA(final SurfaceFactory surfaceFactory) {
        this.surfaceFactory = surfaceFactory;
    }

    @Override
    public GameMap buildGameMap() {
        final List<Surface> surfaces = new ArrayList<>();
        final List<Obstacle> obstacles = new ArrayList<>();
        surfaces.add(surfaceFactory.createGrass(
                new Rectangle(new Vector2D(GRASS1_X, GRASS1_Y), GRASS1_WIDTH, GRASS1_HEIGHT),
                GRASS1_Z_INDEX));
        surfaces.add(surfaceFactory.createWindy(
                surfaceFactory.createGrass(
                        new Rectangle(new Vector2D(WINDYGRASS1_X, WINDYGRASS1_Y),
                                WINDYGRASS1_WIDTH,
                                WINDYGRASS1_HEIGHT),
                        WINDYGRASS1_Z_INDEX),
                WindDirection.UP, WIND_STRENGTH));
        surfaces.add(surfaceFactory.createGrass(
                new Rectangle(new Vector2D(GRASS2_X, GRASS2_Y), GRASS2_WIDTH, GRASS2_HEIGHT),
                GRASS2_Z_INDEX));
        surfaces.add(surfaceFactory.createGrass(
                new Rectangle(new Vector2D(GRASS3_X, GRASS3_Y), GRASS3_WIDTH, GRASS3_HEIGHT),
                GRASS3_Z_INDEX));
        surfaces.add(surfaceFactory.createGrass(
                new Rectangle(new Vector2D(GRASS4_X, GRASS4_Y), GRASS4_WIDTH, GRASS4_HEIGHT),
                GRASS4_Z_INDEX));
        surfaces.add(surfaceFactory.createSand(
                new Rectangle(new Vector2D(SAND_X, SAND_Y), SAND_WIDTH,
                        SAND_HEIGHT),
                SAND_Z_INDEX));
        surfaces.add(surfaceFactory.createIce(
                new Rectangle(new Vector2D(ICE_X, ICE_Y), ICE_WIDTH,
                        ICE_HEIGHT),
                ICE_Z_INDEX));
        surfaces.add(surfaceFactory.createDirt(
                new Rectangle(new Vector2D(DIRT_X, DIRT_Y), DIRT_WIDTH,
                        DIRT_HEIGHT),
                DIRT_Z_INDEX));
        surfaces.add(surfaceFactory.createDirt(
                new Circle(new Vector2D(DIRT2_X, DIRT2_Y), DIRT2_RADIUS),
                DIRT2_Z_INDEX));
        surfaces.add(surfaceFactory.createSand(
                new Oval(SAND2_POSITION, SAND2_RADIUS_X, SAND2_RADIUS_Y),
                DIRT2_Z_INDEX));
        obstacles.add(new WallObstacle(new Vector2D(W1_X, W1_Y), W1_WIDTH, W1_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(W2_X, W2_Y), W2_WIDTH, W2_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(W3_X, W3_Y), W3_WIDTH, W3_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(W4_X, W4_Y), W4_WIDTH, W4_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(O1_X, O1_Y), O1_WIDTH, O1_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(O2_X, O2_Y), O2_WIDTH, O2_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(O3_X, O3_Y), O3_WIDTH, O3_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(O4_X, O4_Y), O4_WIDTH, O4_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(O5_X, O5_Y), O5_WIDTH, O5_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(O6_X, O6_Y), O6_WIDTH, O6_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(O7_X, O7_Y), O7_WIDTH, O7_HEIGHT));
        obstacles.add(new RoundObstacle(new Vector2D(O8_X, O8_Y), O8_RADIUS));
        obstacles.add(new TriangleObstacle(O9_V1, O9_V2, O9_V3));
        obstacles.add(new TriangleObstacle(O10_V1, O10_V2, O10_V3));
        obstacles.add(new RoundObstacle(new Vector2D(O11_X, O11_Y), O11_RADIUS));
        obstacles.add(new RoundObstacle(new Vector2D(O12_X, O12_Y), O12_RADIUS));
        obstacles.add(new RoundObstacle(new Vector2D(O13_X, O13_Y), O13_RADIUS));
        obstacles.add(new RoundObstacle(new Vector2D(O14_X, O14_Y), O14_RADIUS));
        return new GameMapImpl(surfaces, new BallImpl(BALL_INITIAL_POSITION, BALL_RADIUS),
               new HoleImpl(HOLE_POSITION, HOLE_RADIUS), obstacles);
    }
}
