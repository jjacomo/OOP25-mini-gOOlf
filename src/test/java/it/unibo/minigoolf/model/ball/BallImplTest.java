package it.unibo.minigoolf.model.ball;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.minigoolf.model.hole.HoleImpl;
import it.unibo.minigoolf.model.map.GameMapImpl;
import it.unibo.minigoolf.model.physics.PhysicsEngine;
import it.unibo.minigoolf.model.physics.velocity.BasicFrictionStrategy;
import it.unibo.minigoolf.model.surfaces.ShapedSurface;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Rectangle;

class BallImplTest {

    private static final double RADIUS = 1.0;

    @Test
    void testConstructorInitializesPositionVelocityAndRadius() {
        final var position = new Vector2D(1.5, 2.5);
        final var ball = new BallImpl(position, RADIUS);

        assertAll("initial ball state",
                () -> assertEquals(position.getX(), ball.getPosition().getX()),
                () -> assertEquals(position.getY(), ball.getPosition().getY()),
                () -> assertEquals(RADIUS, ball.getRadius()),
                () -> assertEquals(0.0, ball.getVelocity().getX()),
                () -> assertEquals(0.0, ball.getVelocity().getY()));
    }

    @Test
    void testSetVelocityUpdatesVelocity() {
        final var ball = new BallImpl(new Vector2D(0.0, 0.0), RADIUS);
        final var velocity = new Vector2D(3.0, -4.0);

        ball.setVelocity(velocity);

        assertAll("updated velocity",
                () -> assertEquals(velocity.getX(), ball.getVelocity().getX()),
                () -> assertEquals(velocity.getY(), ball.getVelocity().getY()));
    }

    @Test
    void testSetPositionUpdatesPositionWithoutChangingRadius() {
        final var ball = new BallImpl(new Vector2D(10.0, 10.0), RADIUS);
        final var newPosition = new Vector2D(-5.0, 7.0);

        ball.setPosition(newPosition);

        assertAll("updated position and radius",
                () -> assertEquals(newPosition.getX(), ball.getPosition().getX()),
                () -> assertEquals(newPosition.getY(), ball.getPosition().getY()),
                () -> assertEquals(RADIUS, ball.getRadius()));
    }

    @Test
    void testUpdateVelocity() {
        final var ball = new BallImpl(new Vector2D(0.0, 0.0), RADIUS);
        ball.setVelocity(new Vector2D(100.0, 100.0));
        final var grassSurface = new ShapedSurface(new Rectangle(new Vector2D(0, 0), 1000, 1000), 10, 1, "grass");
        PhysicsEngine.setVelocityStrategy(new BasicFrictionStrategy());
        PhysicsEngine.update(ball, grassSurface, List.of(), 10.0);

        assertAll("updated velocity",
                () -> assertEquals(0.0, ball.getVelocity().getX()),
                () -> assertEquals(0.0, ball.getVelocity().getY()));
    }

    @Test
    void testOutOfBounds() {
        final var ball = new BallImpl(new Vector2D(-10.0, -10.0), RADIUS);
        final var grassSurface = new ShapedSurface(new Rectangle(new Vector2D(0, 0), 1000, 1000), 10, 1,
                "grass");
        final var map = new GameMapImpl(List.of(grassSurface), ball, new HoleImpl(new Vector2D(0, 0), RADIUS),
                List.of());

        assertThrows(IllegalStateException.class,
                () -> map.getSurfaceAt(ball.getPosition()));
    }
}
