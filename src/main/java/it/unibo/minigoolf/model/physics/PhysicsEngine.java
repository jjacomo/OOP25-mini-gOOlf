package it.unibo.minigoolf.model.physics;

import java.util.List;

import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.model.map.GameMap;
import it.unibo.minigoolf.model.obstacles.Obstacle;
import it.unibo.minigoolf.model.physics.velocity.BallVelocityStrategy;
import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.util.Vector2D;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Physics engine for the mini-golf domain.
 *
 * <p>
 * It contains the physics update logic for the ball and the interactions with
 * surfaces and obstacles. This keeps the domain behavior out of the controller
 * layer. {@code PhysicsEngine} intentionally has <em>no</em> dependency on any
 * controller class; coordination with the controller layer is the caller's
 * responsibility.
 */
public final class PhysicsEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhysicsEngine.class);

    /**
     * Max number of iterations to resolve overlapping collisions in a single
     * update.
     */
    private static final int MAX_COLLISION_ITERATIONS = 3;

    private static BallVelocityStrategy velocityStrategy;

    private PhysicsEngine() {
        throw new UnsupportedOperationException("PhysicsEngine is a utility class");
    }

    /**
     * Sets the strategy used to update the ball's velocity based on the surface
     * it is currently on.
     *
     * @param strategy the BallVelocityStrategy to use for velocity updates
     */
    public static void setVelocityStrategy(final BallVelocityStrategy strategy) {
        velocityStrategy = strategy;
    }

    /**
     * Updates the ball state according to the current game map and elapsed time.
     *
     * <p>
     * This method performs the full physics tick for the ball, including
     * friction, movement and collision handling.
     * </p>
     *
     * @param gameMap   the map containing the ball, surfaces and obstacles
     * @param deltaTime the elapsed time in seconds
     */
    public static void update(final GameMap gameMap, final double deltaTime) {
        final Ball ball = gameMap.getBall();
        update(ball, gameMap.getSurfaceAt(ball.getPosition()), gameMap.getObstacles(), deltaTime);
    }

    /**
     * Updates the ball state using pre-resolved model objects.
     *
     * <p>
     * This overload is intended for use by the controller layer: the caller
     * extracts the current surface and obstacle list from its own controllers
     * and passes them in, keeping {@code PhysicsEngine} free of any controller
     * dependency. The {@code ball} parameter may be any implementation of
     * {@link Ball}, including an adapter that delegates writes back to a
     * {@code BallController}.
     * </p>
     *
     * @param ball      the ball whose state will be updated
     * @param surface   the surface currently under the ball
     * @param obstacles all obstacles on the map
     * @param deltaTime the elapsed time in seconds
     */
    public static void update(
            final Ball ball,
            final Surface surface,
            final List<Obstacle> obstacles,
            final double deltaTime) {
        LOGGER.debug("Physics update: pos={}, vel={}, dt={}", ball.getPosition(), ball.getVelocity(), deltaTime);
        updateBallVelocity(ball, surface, deltaTime);
        updateBallPosition(ball, deltaTime);
        resolveCollisions(ball, obstacles);
    }

    /**
     * Applies surface friction to the ball's velocity.
     *
     * @param ball      the ball to update
     * @param surface   the surface currently under the ball
     * @param deltaTime the elapsed time in seconds
     */
    private static void updateBallVelocity(final Ball ball, final Surface surface, final double deltaTime) {
        if (velocityStrategy != null) {
            velocityStrategy.updateVelocity(ball, surface, deltaTime);
        } else {
            LOGGER.warn("No velocity strategy set for PhysicsEngine. Ball velocity will not be updated.");
        }
    }

    /**
     * Moves the ball according to its velocity and the elapsed time.
     *
     * @param ball      the ball to move
     * @param deltaTime the elapsed time in seconds
     */
    private static void updateBallPosition(final Ball ball, final double deltaTime) {
        final Vector2D newPosition = ball.getPosition().add(ball.getVelocity().scalarMultiply(deltaTime));
        ball.setPosition(newPosition);
    }

    /**
     * For every obstacle, checks if it's colliding with the ball. Them have to be
     * checked multiple times to avoid potioning errors in double collision at the
     * same time.
     *
     * @param ball      the ball to be checked
     * @param obstacles list with all the obstacles of the map
     */
    private static void resolveCollisions(final Ball ball, final List<Obstacle> obstacles) {
        for (int iter = 0; iter < MAX_COLLISION_ITERATIONS; iter++) {
            boolean anyCollision = false;
            for (final Obstacle obs : obstacles) {
                if (obs.isColliding(ball)) {
                    obs.resolveCollision(ball);
                    anyCollision = true;
                }
            }
            if (!anyCollision) {
                break;
            }
        }
    }
}
