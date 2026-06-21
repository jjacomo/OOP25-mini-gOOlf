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
import it.unibo.minigoolf.util.shapes.Oval;
import it.unibo.minigoolf.util.shapes.Rectangle;

/**
 * Test implementation of the GameMapFactory interface.
 * 
 * <p>
 * This factory creates a simple, flat test game map suitable for development,
 * debugging, and basic testing purposes.
 * </p>
 * 
 * @author jack
 * 
 * @see GameMapFactory
 * @see GameMap
 */
public class TestMap implements GameMapFactory {

        private static final double MAIN_SURFACE_X = 0;
        private static final double MAIN_SURFACE_Y = 0;
        private static final double MAIN_SURFACE_WIDTH = 1920;
        private static final double MAIN_SURFACE_HEIGHT = 1080;
        private static final int MAIN_SURFACE_Z_INDEX = 0;
        private static final double SECOND_SURFACE_X = 100;
        private static final double SECOND_SURFACE_Y = 50;
        private static final double SECOND_SURFACE_WIDTH = 100;
        private static final double SECOND_SURFACE_HEIGHT = 200;
        private static final int SECOND_SURFACE_Z_INDEX = 1;
        private static final double THIRD_SURFACE_X = 440;
        private static final double THIRD_SURFACE_Y = 100;
        private static final double THIRD_SURFACE_WIDTH = 600;
        private static final double THIRD_SURFACE_HEIGHT = 200;
        private static final int THIRD_SURFACE_Z_INDEX = 2;
        private static final double BALL_RADIUS = 30;
        private static final Vector2D BALL_INITIAL_POSITION = new Vector2D(150, 150);
        private static final double WINDYGRASS1_X = 1200;
        private static final double WINDYGRASS1_Y = 300;
        private static final double WINDYGRASS1_RADIUS1 = 100;
        private static final double WINDYGRASS1_RADIUS2 = 150;
        private static final int WINDYGRASS1_Z_INDEX = 3;
        private static final double WIND_STRENGTH = 10.5;

        private static final double W1_X = 0;
        private static final double W1_Y = 0;
        private static final double W1_WIDTH = 15;
        private static final double W1_HEIGHT = 1080;
        private static final double W2_X = 0;
        private static final double W2_Y = 0;
        private static final double W2_WIDTH = 1920;
        private static final double W2_HEIGHT = 15;
        private static final double W3_X = 1905;
        private static final double W3_Y = 0;
        private static final double W3_WIDTH = 15;
        private static final double W3_HEIGHT = 1080;
        private static final double W4_X = 0;
        private static final double W4_Y = 1065;
        private static final double W4_WIDTH = 1920;
        private static final double W4_HEIGHT = 15;
        private static final double O1_X = 300;
        private static final double O1_Y = 200;
        private static final double O1_WIDTH = 40;
        private static final double O1_HEIGHT = 100;
        private static final double O2_X = 700;
        private static final double O2_Y = 200;
        private static final double O2_RADIUS = 50;
        private static final Vector2D O3_V1 = new Vector2D(900, 400);
        private static final Vector2D O3_V2 = new Vector2D(900, 450);
        private static final Vector2D O3_V3 = new Vector2D(750, 400);

        private static final Vector2D HOLE_POSITION = new Vector2D(1500, 800);
        private static final double HOLE_RADIUS = 40;
        /* 
        private static final double BOOST_SURFACE_X = 600;
        private static final double BOOST_SURFACE_Y = 500;
        private static final double BOOST_SURFACE_WIDTH = 500;
        private static final double BOOST_SURFACE_HEIGHT = 400;
        private static final int BOOST_SURFACE_Z_INDEX = 2;
        private static final double BOOST_INTENSITY = 4.0;
        private static final double WINDYGRASS2_X = 700;
        private static final double WINDYGRASS2_Y = 600;
        private static final double WINDYGRASS2_RADIUS1 = 150;
        private static final double WINDYGRASS2_RADIUS2 = 200;
        private static final int WINDYGRASS2_Z_INDEX = 3;
        */

        private final SurfaceFactory surfaceFactory;

        /**
         * Constructs a TestGameMapFactory using a default SurfaceFactory
         * implementation.
         */
        public TestMap() {
                this(new SurfaceFactoryImpl());
        }

        /**
         * Constructs a TestGameMapFactory using the provided SurfaceFactory.
         * 
         * @param surfaceFactory the factory used to build surfaces
         */
        public TestMap(final SurfaceFactory surfaceFactory) {
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
                                new Rectangle(new Vector2D(MAIN_SURFACE_X, MAIN_SURFACE_Y), MAIN_SURFACE_WIDTH,
                                                MAIN_SURFACE_HEIGHT),
                                MAIN_SURFACE_Z_INDEX));
                surfaces.add(surfaceFactory.createSand(
                                new Rectangle(new Vector2D(SECOND_SURFACE_X, SECOND_SURFACE_Y), SECOND_SURFACE_WIDTH,
                                                SECOND_SURFACE_HEIGHT),
                                SECOND_SURFACE_Z_INDEX));
                surfaces.add(surfaceFactory.createDirt(
                                new Rectangle(new Vector2D(THIRD_SURFACE_X, THIRD_SURFACE_Y), THIRD_SURFACE_WIDTH,
                                                THIRD_SURFACE_HEIGHT),
                                THIRD_SURFACE_Z_INDEX));
                surfaces.add(surfaceFactory.createWindy(
                                surfaceFactory.createDirt(
                                                new Oval(new Vector2D(WINDYGRASS1_X, WINDYGRASS1_Y),
                                                                WINDYGRASS1_RADIUS1, WINDYGRASS1_RADIUS2),
                                                WINDYGRASS1_Z_INDEX),
                                WindDirection.RIGHT, WIND_STRENGTH));
                //surfaces.add(surfaceFactory.createBoost(
                //                surfaceFactory.createDirt(
                //                                new Rectangle(new Vector2D(BOOST_SURFACE_X, BOOST_SURFACE_Y),
                //                                                BOOST_SURFACE_WIDTH,
                //                                                BOOST_SURFACE_HEIGHT),
                //                                BOOST_SURFACE_Z_INDEX),
                //                BOOST_INTENSITY));
                //surfaces.add(surfaceFactory.createWindy(
                //                surfaceFactory.createDirt(
                //                                new Oval(new Vector2D(WINDYGRASS2_X, WINDYGRASS2_Y),
                //                                                WINDYGRASS2_RADIUS1, WINDYGRASS2_RADIUS2),
                //                                WINDYGRASS2_Z_INDEX),
                //                WindDirection.DOWN, WIND_STRENGTH));
                obstacles.add(new WallObstacle(new Vector2D(W1_X, W1_Y), W1_WIDTH, W1_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(W2_X, W2_Y), W2_WIDTH, W2_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(W3_X, W3_Y), W3_WIDTH, W3_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(W4_X, W4_Y), W4_WIDTH, W4_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(O1_X, O1_Y), O1_WIDTH, O1_HEIGHT));
                obstacles.add(new RoundObstacle(new Vector2D(O2_X, O2_Y), O2_RADIUS));
                obstacles.add(new TriangleObstacle(O3_V1, O3_V2, O3_V3));
                return new GameMapImpl(surfaces, new BallImpl(BALL_INITIAL_POSITION, BALL_RADIUS),
                                new HoleImpl(HOLE_POSITION, HOLE_RADIUS), obstacles);
        }
}
