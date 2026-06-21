package it.unibo.minigoolf.model.map.factories;

import java.util.ArrayList;
import java.util.List;

import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.model.surfaces.factory.SurfaceFactory;
import it.unibo.minigoolf.model.surfaces.factory.SurfaceFactoryImpl;
import it.unibo.minigoolf.model.obstacles.AbstractObstacle;
import it.unibo.minigoolf.model.obstacles.Obstacle;
import it.unibo.minigoolf.model.obstacles.PortalObstacle;
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

/**
 * Map F.
 * 
 * @author jack
 * 
 */
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

    // ICE
    private static final double ICE_X = 1080;
    private static final double ICE_Y = 10;
    private static final double ICE_WIDTH = 840;
    private static final double ICE_HEIGHT = 500;
    private static final double ICE_OVAL_X = 1080;
    private static final double ICE_OVAL_Y = 0;
    private static final double ICE_OVAL_WIDTH = 100;
    private static final double ICE_OVAL_HEIGHT = 530;
    private static final int ICE_Z_INDEX = 3;

    private static final double DIRT_OVAL_X = 900;
    private static final double DIRT_OVAL_Y = 1000;
    private static final double DIRT_OVAL_WIDTH = 200;
    private static final double DIRT_OVAL_HEIGHT = 200;

    private static final double BOOST_SAND_X = 1310;
    private static final double BOOST_SAND_Y = 660;
    private static final double BOOST_SAND_WIDTH = 200;
    private static final double BOOST_SAND_HEIGHT = 80;
    private static final int BOOST_SAND_Z_INDEX = 3;
    private static final double BOOST_INTENSITY = 1;

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

    // oblique walls
    private static final Vector2D O2_V1 = new Vector2D(800, 800);
    private static final Vector2D O2_V2 = new Vector2D(800, 830);
    private static final Vector2D O2_V3 = new Vector2D(300, 300);
    private static final Vector2D O3_V1 = new Vector2D(800, 830);
    private static final Vector2D O3_V2 = new Vector2D(300, 300);
    private static final Vector2D O3_V3 = new Vector2D(300, 330);
    private static final Vector2D O5_V1 = new Vector2D(800, 800);
    private static final Vector2D O5_V2 = new Vector2D(800, 830);
    private static final Vector2D O5_V3 = new Vector2D(1100, 500);
    private static final Vector2D O6_V1 = new Vector2D(800, 830);
    private static final Vector2D O6_V2 = new Vector2D(1100, 530);
    private static final Vector2D O6_V3 = new Vector2D(1100, 500);

    private static final Vector2D O4_V1 = new Vector2D(520, 0);
    private static final Vector2D O4_V2 = new Vector2D(1080, 0);
    private static final Vector2D O4_V3 = new Vector2D(800, 300);

    private static final double O7_X = 1100;
    private static final double O7_Y = 500;
    private static final double O7_WIDTH = 1120;
    private static final double O7_HEIGHT = 30;
    private static final Vector2D O8_V1 = new Vector2D(1300, 610);
    private static final Vector2D O8_V2 = new Vector2D(1270, 530);
    private static final Vector2D O8_V3 = new Vector2D(1330, 530);
    private static final Vector2D O9_V1 = new Vector2D(1300, 720);
    private static final Vector2D O9_V2 = new Vector2D(1270, 800);
    private static final Vector2D O9_V3 = new Vector2D(1330, 800);
    private static final double O10_X = 1600;
    private static final double O10_Y = 270;
    private static final double O10_WIDTH = 320;
    private static final double O10_HEIGHT = 30;
    private static final double O11_X = 1600;
    private static final double O11_Y = 285;
    private static final double O11_RADIUS = 15;

    // special obstacles
    private static final Vector2D BOUNCY1_OBS_POS = new Vector2D(500, 250);
    private static final double BOUNCY1_OBS_RADIUS = 40;
    private static final double BOUNCINESS1 = AbstractObstacle.STICKY_BOUNCINESS;
    private static final Vector2D BOUNCY2_OBS_POS = new Vector2D(0, 0);
    private static final double BOUNCY2_OBS_RADIUS = 150;
    private static final double BOUNCINESS2 = AbstractObstacle.BOUNCY_BOUNCINESS;
    private static final Vector2D BOUNCY3_OBS_POS = new Vector2D(1300, 250);
    private static final double BOUNCY3_OBS_RADIUS = 100;
    private static final double BOUNCINESS3 = AbstractObstacle.STICKY_BOUNCINESS;
    private static final Vector2D BOUNCY4_OBS_POS = new Vector2D(1920, 660);
    private static final double BOUNCY4_OBS_RADIUS = 150;
    private static final double BOUNCINESS4 = AbstractObstacle.STICKY_BOUNCINESS;
    private static final Vector2D PORTAL1_POS = new Vector2D(1000, 730);
    private static final Vector2D PORTAL2_POS = new Vector2D(1800, 110);
    private static final double PORTAL_RADIUS = 30;

    private static final Vector2D BALL_POSITION = new Vector2D(1700, 730);
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

    /**
     * Builds the F game map.
     *
     * @return the constructed game map
     */
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
                BOOST_SAND_Z_INDEX + 2), BOOST_INTENSITY)
        );
        surfaces.add(surfaceFactory.createIce(new Rectangle(new Vector2D(ICE_X, ICE_Y), ICE_WIDTH, ICE_HEIGHT),
                ICE_Z_INDEX));
        surfaces.add(surfaceFactory.createIce(new Oval(new Vector2D(ICE_OVAL_X, ICE_OVAL_Y), ICE_OVAL_WIDTH, ICE_OVAL_HEIGHT),
                ICE_Z_INDEX));

        obstacles.add(new RoundObstacle(BOUNCY4_OBS_POS, BOUNCY4_OBS_RADIUS, BOUNCINESS4));
        obstacles.add(new RoundObstacle(BOUNCY2_OBS_POS, BOUNCY2_OBS_RADIUS, BOUNCINESS2));
        obstacles.add(new WallObstacle(new Vector2D(W1_X, W1_Y), W1_WIDTH, W1_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(W2_X, W2_Y), W2_WIDTH, W2_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(W3_X, W3_Y), W3_WIDTH, W3_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(W4_X, W4_Y), W4_WIDTH, W4_HEIGHT));
        obstacles.add(new WallObstacle(new Vector2D(O1_X, O1_Y), O1_WIDTH, O1_HEIGHT));
        obstacles.add(new TriangleObstacle(O2_V1, O2_V2, O2_V3));
        obstacles.add(new TriangleObstacle(O3_V1, O3_V2, O3_V3));
        obstacles.add(new TriangleObstacle(O5_V1, O5_V2, O5_V3));
        obstacles.add(new TriangleObstacle(O6_V1, O6_V2, O6_V3));
        obstacles.add(new RoundObstacle(BOUNCY1_OBS_POS, BOUNCY1_OBS_RADIUS, BOUNCINESS1));
        obstacles.add(new TriangleObstacle(O4_V1, O4_V2, O4_V3));
        obstacles.add(new WallObstacle(new Vector2D(O7_X, O7_Y), O7_WIDTH, O7_HEIGHT));
        obstacles.add(new RoundObstacle(BOUNCY3_OBS_POS, BOUNCY3_OBS_RADIUS, BOUNCINESS3));
        obstacles.add(new TriangleObstacle(O8_V1, O8_V2, O8_V3));
        obstacles.add(new TriangleObstacle(O9_V1, O9_V2, O9_V3));
        obstacles.add(new WallObstacle(new Vector2D(O10_X, O10_Y), O10_WIDTH, O10_HEIGHT));
        obstacles.add(new RoundObstacle(new Vector2D(O11_X, O11_Y), O11_RADIUS));
        obstacles.addAll(PortalObstacle.createPair(PORTAL1_POS, PORTAL2_POS, PORTAL_RADIUS));
        return new GameMapImpl(surfaces, new BallImpl(BALL_POSITION, BALL_RADIUS),
                new HoleImpl(HOLE_POSITION, HOLE_RADIUS), obstacles);
    }
}
