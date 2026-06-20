package it.unibo.minigoolf.model.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.minigoolf.model.ball.BallImpl;
import it.unibo.minigoolf.model.hole.HoleImpl;
import it.unibo.minigoolf.model.surfaces.ShapedSurface;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Rectangle;

class GameMapImplTest {

    private static final double FRICTION = 1.0;
    private static final double BALL_RADIUS = 5.0;
    private static final double HOLE_RADIUS = 10.0;

    private static final Vector2D BASE_SURFACE_MIN = new Vector2D(-500, -500);
    private static final double BASE_SURFACE_WIDTH = 1000;
    private static final double BASE_SURFACE_HEIGHT = 1000;
    private static final Vector2D TOP_SURFACE_MIN = new Vector2D(-100, -100);
    private static final double TOP_SURFACE_WIDTH = 200;
    private static final double TOP_SURFACE_HEIGHT = 200;
    private static final Vector2D HOLE_POSITION = new Vector2D(200.0, 200.0);
    private static final Vector2D BALL_POSITION = new Vector2D(0.0, 0.0);

    private static final Vector2D POINT_INSIDE_BASE_SURFACE = new Vector2D(0.0, 0.0);
    private static final Vector2D OTHER_POINT_INSIDE_BASE_SURFACE = new Vector2D(400.0, 400.0);
    private static final Vector2D POINT_OUTSIDE_BASE_SURFACE = new Vector2D(600.0, 600.0);

    private ShapedSurface baseSurface;
    private ShapedSurface topSurface;

    private BallImpl ball;
    private HoleImpl hole;

    @BeforeEach
    void setUp() {
        baseSurface = new ShapedSurface(
                new Rectangle(BASE_SURFACE_MIN, BASE_SURFACE_WIDTH, BASE_SURFACE_HEIGHT),
                FRICTION, 0, "grass");
        topSurface = new ShapedSurface(
                new Rectangle(TOP_SURFACE_MIN, TOP_SURFACE_WIDTH, TOP_SURFACE_HEIGHT),
                FRICTION * 2, 1, "sand");
        ball = new BallImpl(BALL_POSITION, BALL_RADIUS);
        hole = new HoleImpl(HOLE_POSITION, HOLE_RADIUS);
    }

    @Test
    void testConstructorRejectsNullSurfaces() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameMapImpl(null, ball, hole, List.of()));
    }

    @Test
    void testConstructorRejectsNullBall() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameMapImpl(List.of(baseSurface), null, hole, List.of()));
    }

    @Test
    void testConstructorRejectsNullHole() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameMapImpl(List.of(baseSurface), ball, null, List.of()));
    }

    @Test
    void testConstructorRejectsNullObstacles() {
        assertThrows(IllegalArgumentException.class,
                () -> new GameMapImpl(List.of(baseSurface), ball, hole, null));
    }

    @Test
    void testGetSurfaceAtReturnsOnlyAvailableSurface() {
        final var map = new GameMapImpl(List.of(baseSurface), ball, hole, List.of());
        // Any point inside baseSurface should return it
        assertEquals(baseSurface, map.getSurfaceAt(POINT_INSIDE_BASE_SURFACE));
        assertEquals(baseSurface, map.getSurfaceAt(OTHER_POINT_INSIDE_BASE_SURFACE));
    }

    @Test
    void testGetSurfaceAtReturnsHighestZIndex() {
        final var map = new GameMapImpl(List.of(baseSurface, topSurface), ball, hole, List.of());
        final var surface = map.getSurfaceAt(POINT_INSIDE_BASE_SURFACE);

        assertEquals(topSurface, surface,
                "getSurfaceAt should return the surface with the highest z-index");
    }

    @Test
    void testGetSurfaceAtThrowsWhenOutOfBounds() {
        final var map = new GameMapImpl(List.of(baseSurface), ball, hole, List.of());
        assertThrows(IllegalStateException.class,
                () -> map.getSurfaceAt(POINT_OUTSIDE_BASE_SURFACE));
    }

}
