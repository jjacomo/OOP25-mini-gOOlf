package it.unibo.minigoolf.model.map.factories;

import java.util.ArrayList;
import java.util.List;
import it.unibo.minigoolf.util.Vector2D;
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
import it.unibo.minigoolf.util.shapes.Rectangle;

/**
 * Tutorial Map.
 * 
 * @author dbakko
 * @see GameMapFactory
 * @see GameMap
 */
public class MapT implements GameMapFactory {

        // BACKGROUND GRASS
        private static final double GRASS_X = 0;
        private static final double GRASS_Y = 0;
        private static final double GRASS_WIDTH = 1920;
        private static final double GRASS_HEIGHT = 1080;
        private static final int GRASS_Z_INDEX = 0;

        // SURFACE PATCHES 
        private static final double ICE_X = 550;
        private static final double ICE_Y = 200;
        private static final double ICE_WIDTH = 250;
        private static final double ICE_HEIGHT = 680;
        
        private static final double SAND_X = 1100;
        private static final double SAND_Y = 200;
        private static final double SAND_WIDTH = 250;
        private static final double SAND_HEIGHT = 680;
        private static final int PATCH_Z_INDEX = 1;

        // ARROWS
        private static final int ARROW_Z_INDEX = 1;

        // ARROW 1
        private static final double A1_S_X = 300; private static final double A1_S_Y = 525;
        private static final double A1_S_W = 100; private static final double A1_S_H = 30;
        private static final double A1_H1_X = 400; private static final double A1_H1_Y = 490;
        private static final double A1_H1_W = 40; private static final double A1_H1_H = 100;
        private static final double A1_H2_X = 440; private static final double A1_H2_Y = 510;
        private static final double A1_H2_W = 30; private static final double A1_H2_H = 60;
        private static final double A1_H3_X = 470; private static final double A1_H3_Y = 525;
        private static final double A1_H3_W = 30; private static final double A1_H3_H = 30;

        // ARROW 2
        private static final double A2_S_X = 850; private static final double A2_S_Y = 525;
        private static final double A2_S_W = 100; private static final double A2_S_H = 30;
        private static final double A2_H1_X = 950; private static final double A2_H1_Y = 490;
        private static final double A2_H1_W = 40; private static final double A2_H1_H = 100;
        private static final double A2_H2_X = 990; private static final double A2_H2_Y = 510;
        private static final double A2_H2_W = 30; private static final double A2_H2_H = 60;
        private static final double A2_H3_X = 1020; private static final double A2_H3_Y = 525;
        private static final double A2_H3_W = 30; private static final double A2_H3_H = 30;

        // ARROW 3
        private static final double A3_S_X = 1420; private static final double A3_S_Y = 525;
        private static final double A3_S_W = 150; private static final double A3_S_H = 30;
        private static final double A3_H1_X = 1570; private static final double A3_H1_Y = 490;
        private static final double A3_H1_W = 40; private static final double A3_H1_H = 100;
        private static final double A3_H2_X = 1610; private static final double A3_H2_Y = 510;
        private static final double A3_H2_W = 30; private static final double A3_H2_H = 60;
        private static final double A3_H3_X = 1640; private static final double A3_H3_Y = 525;
        private static final double A3_H3_W = 30; private static final double A3_H3_H = 30;

        // BORDER WALLS
        private static final double W1_X = 0; private static final double W1_Y = 0;
        private static final double W1_WIDTH = 31; private static final double W1_HEIGHT = 1080;
        private static final double W2_X = 0; private static final double W2_Y = 0;
        private static final double W2_WIDTH = 1920; private static final double W2_HEIGHT = 31;
        private static final double W3_X = 1889; private static final double W3_Y = 0;
        private static final double W3_WIDTH = 31; private static final double W3_HEIGHT = 1080;
        private static final double W4_X = 0; private static final double W4_Y = 1049;
        private static final double W4_WIDTH = 1920; private static final double W4_HEIGHT = 31;

        // PERIPHERAL OBSTACLES 
        private static final Vector2D PORTAL_A_POS = new Vector2D(150, 150);
        private static final Vector2D PORTAL_B_POS = new Vector2D(150, 930);
        private static final double PORTAL_R = 60;

        private static final double ROUND_NORM_X = 1750;
        private static final double ROUND_NORM_Y = 150;
        private static final double ROUND_NORM_RADIUS = 60;

        private static final double ROUND_BOUNCY_X = 1750;
        private static final double ROUND_BOUNCY_Y = 930;
        private static final double ROUND_BOUNCY_RADIUS = 60;
        
        // Bounciness constant
        private static final double BOUNCINESS = 1.5;

        // BALL AND HOLE
        private static final Vector2D BALL_INITIAL_POSITION = new Vector2D(150, 540);
        private static final double BALL_RADIUS = 30;
        private static final Vector2D HOLE_POSITION = new Vector2D(1720, 540);
        private static final double HOLE_RADIUS = 30;

        private final SurfaceFactory surfaceFactory;

        /**
         * Constructs a TutorialMap using a default SurfaceFactory implementation.
         */
        public MapT() {
                this(new SurfaceFactoryImpl());
        }

        /**
         * Constructs a TutorialMap using the provided SurfaceFactory.
         * * @param surfaceFactory the factory used to build surfaces
         */
        public MapT(final SurfaceFactory surfaceFactory) {
                this.surfaceFactory = surfaceFactory;
        }

        /**
         * Builds the tutorial game map.
         * * @return a GameMap instance containing the tutorial layout
         */
        @Override
        public GameMap buildGameMap() {
                final List<Surface> surfaces = new ArrayList<>();
                final List<Obstacle> obstacles = new ArrayList<>();

                // BACKGROUND
                surfaces.add(surfaceFactory.createGrass(
                                new Rectangle(new Vector2D(GRASS_X, GRASS_Y), GRASS_WIDTH, GRASS_HEIGHT),
                                GRASS_Z_INDEX));

                // SURFACE PATCHES
                surfaces.add(surfaceFactory.createIce(
                                new Rectangle(new Vector2D(ICE_X, ICE_Y), ICE_WIDTH, ICE_HEIGHT),
                                PATCH_Z_INDEX));
                surfaces.add(surfaceFactory.createSand(
                                new Rectangle(new Vector2D(SAND_X, SAND_Y), SAND_WIDTH, SAND_HEIGHT),
                                PATCH_Z_INDEX));

                // ARROW 1
                surfaces.add(surfaceFactory.createDirt(new Rectangle(new Vector2D(A1_S_X, A1_S_Y), A1_S_W, A1_S_H), ARROW_Z_INDEX));
                surfaces.add(surfaceFactory.createDirt(new Rectangle(new Vector2D(A1_H1_X, A1_H1_Y), A1_H1_W, A1_H1_H), ARROW_Z_INDEX));
                surfaces.add(surfaceFactory.createDirt(new Rectangle(new Vector2D(A1_H2_X, A1_H2_Y), A1_H2_W, A1_H2_H), ARROW_Z_INDEX));
                surfaces.add(surfaceFactory.createDirt(new Rectangle(new Vector2D(A1_H3_X, A1_H3_Y), A1_H3_W, A1_H3_H), ARROW_Z_INDEX));

                // ARROW 2
                surfaces.add(surfaceFactory.createDirt(new Rectangle(new Vector2D(A2_S_X, A2_S_Y), A2_S_W, A2_S_H), ARROW_Z_INDEX));
                surfaces.add(surfaceFactory.createDirt(new Rectangle(new Vector2D(A2_H1_X, A2_H1_Y), A2_H1_W, A2_H1_H), ARROW_Z_INDEX));
                surfaces.add(surfaceFactory.createDirt(new Rectangle(new Vector2D(A2_H2_X, A2_H2_Y), A2_H2_W, A2_H2_H), ARROW_Z_INDEX));
                surfaces.add(surfaceFactory.createDirt(new Rectangle(new Vector2D(A2_H3_X, A2_H3_Y), A2_H3_W, A2_H3_H), ARROW_Z_INDEX));

                // ARROW 3
                surfaces.add(surfaceFactory.createDirt(new Rectangle(new Vector2D(A3_S_X, A3_S_Y), A3_S_W, A3_S_H), ARROW_Z_INDEX));
                surfaces.add(surfaceFactory.createDirt(new Rectangle(new Vector2D(A3_H1_X, A3_H1_Y), A3_H1_W, A3_H1_H), ARROW_Z_INDEX));
                surfaces.add(surfaceFactory.createDirt(new Rectangle(new Vector2D(A3_H2_X, A3_H2_Y), A3_H2_W, A3_H2_H), ARROW_Z_INDEX));
                surfaces.add(surfaceFactory.createDirt(new Rectangle(new Vector2D(A3_H3_X, A3_H3_Y), A3_H3_W, A3_H3_H), ARROW_Z_INDEX));

                // BORDER WALLS
                obstacles.add(new WallObstacle(new Vector2D(W1_X, W1_Y), W1_WIDTH, W1_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(W2_X, W2_Y), W2_WIDTH, W2_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(W3_X, W3_Y), W3_WIDTH, W3_HEIGHT));
                obstacles.add(new WallObstacle(new Vector2D(W4_X, W4_Y), W4_WIDTH, W4_HEIGHT));

                obstacles.add(new RoundObstacle(new Vector2D(ROUND_NORM_X, ROUND_NORM_Y), ROUND_NORM_RADIUS));
                obstacles.add(new RoundObstacle(new Vector2D(ROUND_BOUNCY_X, ROUND_BOUNCY_Y), ROUND_BOUNCY_RADIUS, BOUNCINESS));
                obstacles.addAll(PortalObstacle.createPair(PORTAL_A_POS, PORTAL_B_POS, PORTAL_R));

                return new GameMapImpl(surfaces, new BallImpl(BALL_INITIAL_POSITION, BALL_RADIUS),
                                new HoleImpl(HOLE_POSITION, HOLE_RADIUS), obstacles);
        }
}