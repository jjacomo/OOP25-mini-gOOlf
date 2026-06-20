package it.unibo.minigoolf.model.surfaces;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.minigoolf.model.ball.BallImpl;
import it.unibo.minigoolf.model.physics.PhysicsEngine;
import it.unibo.minigoolf.model.physics.velocity.BasicFrictionStrategy;
import it.unibo.minigoolf.model.surfaces.boost.BoostSurface;
import it.unibo.minigoolf.model.surfaces.wind.WindDirection;
import it.unibo.minigoolf.model.surfaces.wind.WindySurface;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Rectangle;

class WindySurfaceTest {

        private static final double SHAPE_SIZE = 1000.0;
        private static final double SHAPE_OFFSET = -500.0;
        private static final double BASE_FRICTION = 2.0;
        private static final int BASE_Z_INDEX = 1;
        private static final double BALL_RADIUS = 5.0;
        private static final double WIND_INTENSITY = 5.0;
        private static final double DELTA_TIME = 0.5;
        private static final double COMPARISON_DELTA = 1e-9;
        private static final double BIG_X = 10_000.0;

        private ShapedSurface baseSurface;

        @BeforeEach
        void setUp() {
                final var shape = new Rectangle(new Vector2D(SHAPE_OFFSET, SHAPE_OFFSET), SHAPE_SIZE, SHAPE_SIZE);
                baseSurface = new ShapedSurface(shape, BASE_FRICTION, BASE_Z_INDEX, "grass");
                PhysicsEngine.setVelocityStrategy(new BasicFrictionStrategy());
        }

        @Test
        void testConstructorRejectsZeroIntensity() {
                assertThrows(IllegalArgumentException.class,
                                () -> new WindySurface(baseSurface, WindDirection.RIGHT, 0.0));
        }

        @Test
        void testConstructorRejectsNegativeIntensity() {
                assertThrows(IllegalArgumentException.class,
                                () -> new WindySurface(baseSurface, WindDirection.UP, -1.0));
        }

        @Test
        void testConstructorRejectsNullBaseSurface() {
                assertThrows(IllegalArgumentException.class,
                                () -> new WindySurface(null, WindDirection.RIGHT, WIND_INTENSITY));
        }

        @Test
        void testGetWindRightDirection() {
                final var surface = new WindySurface(baseSurface, WindDirection.RIGHT, WIND_INTENSITY);
                final var wind = surface.getWind();

                assertTrue(wind.isPresent(), "getWind() should return a non-empty Optional");
                assertAll("RIGHT wind vector",
                                () -> assertEquals(WIND_INTENSITY, wind.get().getX(), COMPARISON_DELTA),
                                () -> assertEquals(0.0, wind.get().getY(), COMPARISON_DELTA));
        }

        @Test
        void testDelegatesPropertiesFromBase() {
                final var surface = new WindySurface(baseSurface, WindDirection.RIGHT, WIND_INTENSITY);

                assertAll("delegated properties",
                                () -> assertEquals(BASE_FRICTION, surface.getFriction(), COMPARISON_DELTA),
                                () -> assertEquals(BASE_Z_INDEX, surface.getZIndex()),
                                () -> assertEquals(baseSurface.getShape(), surface.getShape()),
                                () -> assertTrue(surface.contains(new Vector2D(0.0, 0.0))),
                                () -> assertFalse(surface.contains(new Vector2D(BIG_X, 0.0))),
                                () -> assertEquals(List.of("grass"), surface.getTypeIds()));
        }

        @Test
        void testWindAcceleratesStationaryBall() {
                // A ball at rest should be pushed by wind even when velocity is zero
                final var surface = new WindySurface(baseSurface, WindDirection.RIGHT, WIND_INTENSITY);
                final var ball = new BallImpl(new Vector2D(0.0, 0.0), BALL_RADIUS);
                ball.setVelocity(Vector2D.ZERO);

                PhysicsEngine.update(ball, surface, List.of(), DELTA_TIME);

                assertTrue(ball.getVelocity().getX() > 0.0,
                                "Stationary ball should gain positive X velocity from RIGHT wind, got: "
                                                + ball.getVelocity());
                assertEquals(0.0, ball.getVelocity().getY(), COMPARISON_DELTA,
                                "Y velocity should remain zero for RIGHT wind");
        }

        @Test
        void testWindySurfaceOnTopOfBoostSurface() {
                final double boostIntensity = 2.0;
                final var boost = new BoostSurface(baseSurface, boostIntensity);
                final var windyBoost = new WindySurface(boost, WindDirection.RIGHT, WIND_INTENSITY);

                // Friction should be the negative boost friction (from BoostSurface)
                assertTrue(windyBoost.getFriction() < 0,
                                "Stacked surface should have negative friction from BoostSurface");
                // Wind should be present from WindySurface
                assertTrue(windyBoost.getWind().isPresent(),
                                "Stacked surface should have wind from WindySurface");
                assertEquals(WIND_INTENSITY, windyBoost.getWind().get().getX(), COMPARISON_DELTA);
                // typeIds should include "boost" from the inner decorator
                assertTrue(windyBoost.getTypeIds().contains("boost"),
                                "typeIds should include 'boost' from inner decorator");
        }
}
