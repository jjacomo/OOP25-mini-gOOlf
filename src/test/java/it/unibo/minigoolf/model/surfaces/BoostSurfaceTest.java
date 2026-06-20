package it.unibo.minigoolf.model.surfaces;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.minigoolf.model.ball.BallImpl;
import it.unibo.minigoolf.model.physics.PhysicsEngine;
import it.unibo.minigoolf.model.physics.velocity.BasicFrictionStrategy;
import it.unibo.minigoolf.model.surfaces.boost.BoostSurface;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Rectangle;

class BoostSurfaceTest {

    private static final double SHAPE_WIDTH = 100.0;
    private static final double SHAPE_HEIGHT = 100.0;
    private static final int BASE_Z_INDEX = 1;
    private static final double BASE_FRICTION = 2.0;
    private static final double TEST_POINT_X = 50.0;
    private static final double TEST_POINT_Y = 50.0;
    private static final double BOOST_INTENSITY_VALID = 3.0;
    private static final double BOOST_INTENSITY_INVALID_NEG = -1.0;
    private static final double BOOST_INTENSITY_INVALID_ZERO = 0.0;
    private static final double BALL_RADIUS = 5.0;
    private static final double DELTA_TIME_SHORT = 0.1;
    private static final double DELTA_TIME_LONG = 1.0;
    private static final double TEST_INITIAL_SPEED = 10.0;
    private static final double TEST_NEAR_LIMIT_SPEED = 1499.0;
    private static final double EXPECTED_CAPPED_SPEED = 1500.0;
    private static final double COMPARISON_DELTA = 0.001;
    private static final double SHAPE_LARGE_SIZE = 1000.0;
    private static final double SHAPE_LARGE_OFFSET = -500.0;
    private static final double BOOST_INTENSITY = 2.0;
    private static final String GRASS_TYPE_ID = "grass";

    @Test
    void testBoostSurfaceConstructorAndGetters() {
        final var shape = new Rectangle(new Vector2D(0, 0), SHAPE_WIDTH, SHAPE_HEIGHT);
        final var baseSurface = new ShapedSurface(shape, BASE_FRICTION, BASE_Z_INDEX, GRASS_TYPE_ID);

        // Invalid intensity
        assertThrows(IllegalArgumentException.class, () -> new BoostSurface(baseSurface, BOOST_INTENSITY_INVALID_NEG));
        assertThrows(IllegalArgumentException.class, () -> new BoostSurface(baseSurface, BOOST_INTENSITY_INVALID_ZERO));

        // Valid boost surface
        final var boostSurface = new BoostSurface(baseSurface, BOOST_INTENSITY_VALID);

        assertAll("boost surface state",
                () -> assertEquals(-BOOST_INTENSITY_VALID, boostSurface.getFriction()),
                () -> assertEquals(List.of(GRASS_TYPE_ID, "boost"), boostSurface.getTypeIds()),
                () -> assertEquals(BASE_Z_INDEX, boostSurface.getZIndex()),
                () -> assertEquals(shape, boostSurface.getShape()),
                () -> assertTrue(boostSurface.contains(new Vector2D(TEST_POINT_X, TEST_POINT_Y)))
        );
    }

    @Test
    void testBasicFrictionStrategyWithBoost() {
        final var shape = new Rectangle(new Vector2D(SHAPE_LARGE_OFFSET, SHAPE_LARGE_OFFSET), SHAPE_LARGE_SIZE, SHAPE_LARGE_SIZE);
        final var baseSurface = new ShapedSurface(shape, BASE_FRICTION, BASE_Z_INDEX, GRASS_TYPE_ID);
        final var boostSurface = new BoostSurface(baseSurface, BOOST_INTENSITY);

        final var ball = new BallImpl(new Vector2D(0.0, 0.0), BALL_RADIUS);
        PhysicsEngine.setVelocityStrategy(new BasicFrictionStrategy());

        // A stopped ball should not be boosted (as direction is undefined)
        ball.setVelocity(Vector2D.ZERO);
        PhysicsEngine.update(ball, boostSurface, List.of(), DELTA_TIME_SHORT);
        assertEquals(Vector2D.ZERO, ball.getVelocity(), "Stopped ball should not accelerate");

        // A moving ball should be accelerated
        final var initialVelocity = new Vector2D(TEST_INITIAL_SPEED, 0.0);
        ball.setVelocity(initialVelocity);
        PhysicsEngine.update(ball, boostSurface, List.of(), DELTA_TIME_SHORT);
        assertTrue(ball.getVelocity().getX() > initialVelocity.getX(), 
                "Moving ball should accelerate on boost surface: " + ball.getVelocity());

        // Verify speed cap (MAX_BOOST_SPEED is 1500.0)
        ball.setVelocity(new Vector2D(TEST_NEAR_LIMIT_SPEED, 0.0));
        PhysicsEngine.update(ball, boostSurface, List.of(), DELTA_TIME_LONG);
        assertEquals(EXPECTED_CAPPED_SPEED, ball.getVelocity().getX(), COMPARISON_DELTA, "Speed should be capped at 1500.0");
    }
}

