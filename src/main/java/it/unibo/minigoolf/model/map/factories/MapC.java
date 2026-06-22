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
import it.unibo.minigoolf.model.obstacles.WallObstacle;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.model.surfaces.factory.SurfaceFactory;
import it.unibo.minigoolf.model.surfaces.factory.SurfaceFactoryImpl;
import it.unibo.minigoolf.util.shapes.Rectangle;

/**
 * Third map.
 * 
 * @author dani
 * 
 * @see GameMapFactory
 * @see GameMap
 */
public class MapC implements GameMapFactory {

        // GRASS (FILLS THE WHOLE MAP)
        private static final double GRASS1_X = 0;
        private static final double GRASS1_Y = 0;
        private static final double GRASS1_WIDTH = 1920;
        private static final double GRASS1_HEIGHT = 1080;
        private static final int GRASS1_Z_INDEX = 0;

        // PORTAL
        private static final Vector2D PORTAL_A_POS = new Vector2D(550, 375);
        private static final Vector2D PORTAL_B_POS = new Vector2D(1800, 375);
        private static final double PORTAL_R = 55;

        // SAND 
        private static final double SAND_X = 0;
        private static final double SAND_Y = 250;
        private static final double SAND_WIDTH = 400;
        private static final double SAND_HEIGHT = 900;
        private static final int SAND_Z_INDEX = 2;

        // ICE BOTTOM
        private static final double ICE_X = 400;
        private static final double ICE_Y = 850;
        private static final double ICE_WIDTH = 1270;
        private static final double ICE_HEIGHT = 200;
        private static final int ICE_Z_INDEX = 4;

        // ICE TOP
        private static final double ICE2_X = 0;
        private static final double ICE2_Y = 0;
        private static final double ICE2_WIDTH = 1920;
        private static final double ICE2_HEIGHT = 250;
        private static final int ICE2_Z_INDEX = 4;

        // WALLS
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

        private static final double O1_X = 380;
        private static final double O1_Y = 300;
        private static final double O1_WIDTH = 50;
        private static final double O1_HEIGHT = 200;

        private static final double O2_X = 380;
        private static final double O2_Y = 700;
        private static final double O2_WIDTH = 50;
        private static final double O2_HEIGHT = 200;

        // BOTTOM
        private static final double O3_X = 400;
        private static final double O3_Y = 850;
        private static final double O3_WIDTH = 1275;
        private static final double O3_HEIGHT = 50;

        // TOP
        private static final double O31_X = 380;
        private static final double O31_Y = 250;
        private static final double O31_WIDTH = 1550;
        private static final double O31_HEIGHT = 50;

        private static final double O4_X = 1400;
        private static final double O4_Y = 700;
        private static final double O4_WIDTH = 50;
        private static final double O4_HEIGHT = 200;

        private static final double O5_X = 1625;
        private static final double O5_Y = 300;
        private static final double O5_WIDTH = 50;
        private static final double O5_HEIGHT = 550;

        private static final double O6_X = 400;
        private static final double O6_Y = 450;
        private static final double O6_WIDTH = 1000;
        private static final double O6_HEIGHT = 50;

        private static final double O7_X = 400;
        private static final double O7_Y = 700;
        private static final double O7_WIDTH = 1000;
        private static final double O7_HEIGHT = 50;

        private static final double O8_X = 200;
        private static final double O8_Y = 600;
        private static final double O8_RADIUS = 90;
        private static final double O12_X = 200;
        private static final double O12_Y = 400;
        private static final double O12_RADIUS = 50;
        private static final double O14_X = 200;
        private static final double O14_Y = 800;
        private static final double O14_RADIUS = 50;

        private static final double BALL_RADIUS = 30;
        private static final Vector2D BALL_INITIAL_POSITION = new Vector2D(1800, 80);
        private static final double HOLE_RADIUS = 30;
        private static final Vector2D HOLE_POSITION = new Vector2D(1780, 980);

        private final SurfaceFactory surfaceFactory;

        /**
         * Constructs a FirstMap using a default SurfaceFactory implementation.
         */
        public MapC() {
                this(new SurfaceFactoryImpl());
        }

        /**
         * Constructs a FirstMap using the provided SurfaceFactory.
         * 
         * @param surfaceFactory the factory used to build surfaces
         */
        public MapC(final SurfaceFactory surfaceFactory) {
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
         */
        @Override
        public GameMap buildGameMap() {
                final List<Surface> surfaces = new ArrayList<>();
                final List<Obstacle> obstacles = new ArrayList<>();
                surfaces.add(surfaceFactory.createGrass(
                                new Rectangle(new Vector2D(GRASS1_X, GRASS1_Y), GRASS1_WIDTH, GRASS1_HEIGHT),
                                GRASS1_Z_INDEX));

                surfaces.add(surfaceFactory.createSand(
                                new Rectangle(new Vector2D(SAND_X, SAND_Y), SAND_WIDTH,
                                                SAND_HEIGHT),
                                SAND_Z_INDEX));
                surfaces.add(surfaceFactory.createIce(
                                new Rectangle(new Vector2D(ICE_X, ICE_Y), ICE_WIDTH,
                                                ICE_HEIGHT),
                                ICE_Z_INDEX));
                surfaces.add(surfaceFactory.createIce(
                                new Rectangle(new Vector2D(ICE2_X, ICE2_Y), ICE2_WIDTH,
                                                ICE2_HEIGHT),
                                ICE2_Z_INDEX));
                obstacles.add(new WallObstacle(new Vector2D(W1_X, W1_Y), W1_WIDTH, W1_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(W2_X, W2_Y), W2_WIDTH, W2_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(W3_X, W3_Y), W3_WIDTH, W3_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(W4_X, W4_Y), W4_WIDTH, W4_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(O1_X, O1_Y), O1_WIDTH, O1_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(O2_X, O2_Y), O2_WIDTH, O2_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(O3_X, O3_Y), O3_WIDTH, O3_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(O31_X, O31_Y), O31_WIDTH, O31_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(O4_X, O4_Y), O4_WIDTH, O4_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(O5_X, O5_Y), O5_WIDTH, O5_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(O6_X, O6_Y), O6_WIDTH, O6_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(O7_X, O7_Y), O7_WIDTH, O7_HEIGHT));
                obstacles.add(new RoundObstacle(new Vector2D(O8_X, O8_Y), O8_RADIUS));
                obstacles.add(new RoundObstacle(new Vector2D(O12_X, O12_Y), O12_RADIUS, AbstractObstacle.BOUNCY_BOUNCINESS));
                obstacles.add(new RoundObstacle(new Vector2D(O14_X, O14_Y), O14_RADIUS, AbstractObstacle.BOUNCY_BOUNCINESS));

                obstacles.addAll(
                PortalObstacle.createPair(
                        PORTAL_A_POS,
                        PORTAL_B_POS,
                        PORTAL_R));

                return new GameMapImpl(surfaces, new BallImpl(BALL_INITIAL_POSITION, BALL_RADIUS),
                                new HoleImpl(HOLE_POSITION, HOLE_RADIUS), obstacles);
        }
}

