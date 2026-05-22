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
import it.unibo.minigoolf.model.surfaces.SurfaceType;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Rectangle;

class BallImplTest {

    @Test
    void testConstructorInitializesPositionVelocityAndRadius() {
        final var position = new Vector2D(1.5, 2.5);
        final var ball = new BallImpl(position, 1.0);

        assertAll("initial ball state",
                () -> assertEquals(1.5, ball.getPosition().getX(), 0.0),
                () -> assertEquals(2.5, ball.getPosition().getY(), 0.0),
                () -> assertEquals(1.0, ball.getRadius(), 0.0),
                () -> assertEquals(0.0, ball.getVelocity().getX(), 0.0),
                () -> assertEquals(0.0, ball.getVelocity().getY(), 0.0)
        );
    }

    @Test
    void testSetVelocityUpdatesVelocity() {
        final var ball = new BallImpl(new Vector2D(0.0, 0.0), 2.0);
        final var velocity = new Vector2D(3.0, -4.0);

        ball.setVelocity(velocity);

        assertAll("updated velocity",
                () -> assertEquals(3.0, ball.getVelocity().getX(), 0.0),
                () -> assertEquals(-4.0, ball.getVelocity().getY(), 0.0)
        );
    }

    @Test
    void testSetPositionUpdatesPositionWithoutChangingRadius() {
        final var ball = new BallImpl(new Vector2D(10.0, 10.0), 1.5);
        final var newPosition = new Vector2D(-5.0, 7.0);

        ball.setPosition(newPosition);

        assertAll("updated position and radius",
                () -> assertEquals(-5.0, ball.getPosition().getX(), 0.0),
                () -> assertEquals(7.0, ball.getPosition().getY(), 0.0),
                () -> assertEquals(1.5, ball.getRadius(), 0.0)
        );
    }

    @Test
    void testSetVelocityThenSetPositionMaintainsRadiusAndStateSeparately() {
        final var ball = new BallImpl(new Vector2D(5.0, 5.0), 2.5);
        ball.setVelocity(new Vector2D(1.0, 1.0));
        ball.setPosition(new Vector2D(0.0, 0.0));

        assertAll("position and velocity after updates",
                () -> assertEquals(0.0, ball.getPosition().getX(), 0.0),
                () -> assertEquals(0.0, ball.getPosition().getY(), 0.0),
                () -> assertEquals(1.0, ball.getVelocity().getX(), 0.0),
                () -> assertEquals(1.0, ball.getVelocity().getY(), 0.0),
                () -> assertEquals(2.5, ball.getRadius(), 0.0)
        );
    }

    @Test
    void testUpdateVelocity() {
        final var ball = new BallImpl(new Vector2D(0.0, 0.0), 1.0);
        ball.setVelocity(new Vector2D(100.0, 100.0));
        final var grassSurface = new ShapedSurface(new Rectangle(new Vector2D(0, 0), 1000, 1000), 10, 1, SurfaceType.GRASS);
        PhysicsEngine.setVelocityStrategy(new BasicFrictionStrategy());
        PhysicsEngine.update(ball, grassSurface, List.of(), 10.0);

        assertAll("updated velocity",
                () -> assertEquals(0.0, ball.getVelocity().getX(), 0.0),
                () -> assertEquals(0.0, ball.getVelocity().getY(), 0.0)
        );
    }

    @Test
    void testOutOfBounds() {
        final var ball = new BallImpl(new Vector2D(-10.0, -10.0), 1.0);
        final var grassSurface = new ShapedSurface(new Rectangle(new Vector2D(0, 0), 1000, 1000), 10, 1,
                SurfaceType.GRASS);
        final var map = new GameMapImpl(List.of(grassSurface), ball, new HoleImpl(new Vector2D(0, 0), 1.0), List.of());

        assertThrows(IllegalStateException.class,
                () -> map.getSurfaceAt(ball.getPosition()));
    }
}
