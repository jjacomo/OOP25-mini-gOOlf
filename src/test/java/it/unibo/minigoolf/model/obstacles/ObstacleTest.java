package it.unibo.minigoolf.model.obstacles;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import org.junit.jupiter.api.Test;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.model.ball.BallImpl;

class ObstacleTest {
    private static final double EPSILON = 0.0001;
    private static final double BALL_RADIUS = 10.0;
    private static final double WALL_SIZE = 50.0;
    private static final double ROUND_RADIUS = 25.0;
    private static final double STICKY_BOUNCINESS = 0.2;
    private static final double BOUNCY_BOUNCINESS = 1.8;
    private static final Vector2D PORTAL_POS_A = new Vector2D(100.0, 100.0);
    private static final Vector2D PORTAL_POS_B = new Vector2D(500.0, 500.0);
    private static final double PORTAL_RADIUS = 30;
    private static final double EXPECTED_PENETRATION_DEPHT = 5.0;
    private static final int SLEEP_TIME_MS = 1000;

    @Test
    void testWallObstacleCollision() {
        final Obstacle wall = new WallObstacle(new Vector2D(0.0, 0.0), WALL_SIZE, WALL_SIZE);
        final Ball collidingBall = new BallImpl(new Vector2D(55.0, 25.0), BALL_RADIUS);
        final Ball safeBall = new BallImpl(new Vector2D(100.0, 100.0), BALL_RADIUS);

        assertAll("WallObstacle collisions and penetration",
                () -> assertTrue(wall.isColliding(collidingBall)),
                () -> assertFalse(wall.isColliding(safeBall)),
                () -> assertEquals(EXPECTED_PENETRATION_DEPHT, wall.getPenetrationDepth(collidingBall), EPSILON)
        );
    }

    @Test
    void testRoundObstacleCollision() {
        final Obstacle round = new RoundObstacle(new Vector2D(0.0, 0.0), ROUND_RADIUS);
        final Ball collidingBall = new BallImpl(new Vector2D(30.0, 0.0), BALL_RADIUS);
        final Ball safeBall = new BallImpl(new Vector2D(100.0, 100.0), BALL_RADIUS);

        assertAll("RoundObstacle collisions and penetration",
                () -> assertTrue(round.isColliding(collidingBall)),
                () -> assertFalse(round.isColliding(safeBall)),
                () -> assertEquals(EXPECTED_PENETRATION_DEPHT, round.getPenetrationDepth(collidingBall), EPSILON)
        );
    }

    @Test
    void testTriangleObstacleCollision() {
        final Vector2D p1 = new Vector2D(0.0, 0.0);
        final Vector2D p2 = new Vector2D(40.0, 0.0);
        final Vector2D p3 = new Vector2D(0.0, 40.0);
        final Obstacle triangle = new TriangleObstacle(p1, p2, p3);
        final Ball collidingBall = new BallImpl(new Vector2D(5.0, 5.0), BALL_RADIUS);
        final Ball safeBall = new BallImpl(new Vector2D(200.0, 200.0), BALL_RADIUS);

        assertAll("TriangleObstacle collisions and penetration",
                () -> assertTrue(triangle.isColliding(collidingBall)),
                () -> assertFalse(triangle.isColliding(safeBall)),
                () -> assertTrue(triangle.getPenetrationDepth(collidingBall) > 0.0)
        );
    }

    @Test
    void testAdvancedObstaclesBounciness() {
        final Obstacle normalWall = new WallObstacle(new Vector2D(0.0, 0.0), WALL_SIZE, WALL_SIZE);
        final Obstacle stickyWall = new WallObstacle(new Vector2D(0.0, 0.0), WALL_SIZE, WALL_SIZE, STICKY_BOUNCINESS);
        final Obstacle bouncyCircle = new RoundObstacle(new Vector2D(0.0, 0.0), ROUND_RADIUS, BOUNCY_BOUNCINESS);

        assertAll("Advanced behavior and costructor chaining",
                () -> assertEquals(1.0, normalWall.getBounciness(), EPSILON),
                () -> assertEquals(STICKY_BOUNCINESS, stickyWall.getBounciness(), EPSILON),
                () -> assertEquals(BOUNCY_BOUNCINESS, bouncyCircle.getBounciness(), EPSILON)
        );
    }

    @Test
    void testNegativeBouncinessSafety() {
        final Obstacle brokenWall = new WallObstacle(new Vector2D(0.0, 0.0), WALL_SIZE, WALL_SIZE, -0.5);
        assertEquals(-0.5, brokenWall.getBounciness(), EPSILON);
    }

    @Test
    void testPortalObstacleFactoryAndCooldown() throws InterruptedException {
        // Updated with PORTAL_RADIUS as the third argument
        final List<PortalObstacle> pair = PortalObstacle.createPair(PORTAL_POS_A, PORTAL_POS_B, PORTAL_RADIUS);
        final PortalObstacle portalA = pair.get(0);
        final PortalObstacle portalB = pair.get(1);
        final Ball ball = new BallImpl(PORTAL_POS_A, BALL_RADIUS);

        assertTrue(portalA.isColliding(ball));
        portalA.resolveCollision(ball);

        assertAll("Portal teleportation and cooldown lifecycle",
                () -> assertEquals(2, pair.size()),
                () -> assertEquals(PORTAL_POS_B, ball.getPosition()),
                () -> assertFalse(portalB.isColliding(ball))
        );

        Thread.sleep(SLEEP_TIME_MS);
        assertTrue(portalB.isColliding(ball));
    }
}
