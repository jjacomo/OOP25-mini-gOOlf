package it.unibo.minigoolf.model.obstacles;

import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.util.Vector2D;

/**
 * Represents a generic obstacle in the game world.
 * Obstacles can have different shapes and interact with the ball,
 * modifying its trajectory upon collision.
 * 
 * @author Mattia
 */
public abstract class AbstractObstacle implements Obstacle {

    /**
     * Bounciness value used to create the sticky obstacles.
     */
    public static final double STICKY_BOUNCINESS = 0.5;

    /**
     * Bounciness value used to create the bouncy obstacles.
     */
    public static final double BOUNCY_BOUNCINESS = 1.5;

    /**
     * Tolerance threshold for floating-point comparisons in collision detection.
     */
    protected static final double EPSILON = 1e-10;

    /**
     * Default bounciness value used to create the normal obstacles.
     */
    protected static final double DEFAULT_BOUNCINESS = 1.0;

    /**
     * Threshold for resting contact to prevent infinite bouncing against 
     *  continuous forces.
     */
    private static final double RESTING_THRESHOLD = 30.0;

    private final Vector2D position;
    private final double bounciness;

    /**
     * Constructs an obstacle at the given position.
     *
     * @param position the 2D vector representing the coordinates of the obstacle
     */
    public AbstractObstacle(final Vector2D position) {
        this(position, DEFAULT_BOUNCINESS);
    }

    /**
     * Constructs a sticky or bouncy obstacle at the given position.
     *
     * @param position the 2D vector representing the coordinates of the obstacle
     * @param bounciness the bounciness of the bouncy obstacle
     */
    public AbstractObstacle(final Vector2D position, final double bounciness) {
        this.position = position;
        this.bounciness = bounciness;
    }

    /** {@inheritDoc} */
    @Override
    public abstract double getPenetrationDepth(Ball ball);

    /**
     * Corrects the ball's position to resolve penetration with the obstacle.
     * Moves the ball outward along the collision normal by the penetration depth.
     *
     * @param ball             the ball to reposition
     * @param ballPosition     the position of the ball
     * @param normal           the collision normal (unit vector pointing outward from
     *                         the obstacle)
     * @param penetrationDepth the amount of overlap (positive value)
     */
    protected void correctPosition(final Ball ball, final Vector2D ballPosition,
                                   final Vector2D normal, 
                                   final double penetrationDepth) {
        final Vector2D newPosition = ballPosition.add(normal.scalarMultiply(
                                     penetrationDepth));
        ball.setPosition(newPosition);
    }

    /**
     * Reflects the ball's velocity according to the collision normal.
     * 
     * <p>Uses the elastic reflection formula: v' = v - 2 (v·n) n. If the impact is 
     * very soft, it applies resting contact to let the ball slide instead of jittering.</p>
     *
     * @param ball   the ball whose velocity to modify
     * @param normal the collision normal (unit vector pointing outward from the 
     *               obstacle)
     */
    protected void reflectVelocity(final Ball ball, final Vector2D normal) {
        final Vector2D velocity = ball.getVelocity();
        final double dot = velocity.dotProduct(normal);
        final double safeBounciness = Math.max(0.0, this.bounciness);

        if (dot >= 0) {
            return;
        }
        if (Math.abs(dot) < RESTING_THRESHOLD) {
            final Vector2D projection = normal.scalarMultiply(dot);
            ball.setVelocity(velocity.subtract(projection));
        } else {
            final Vector2D perfectBounce = velocity.subtract(normal.scalarMultiply(
                                           2 * dot));
            ball.setVelocity(perfectBounce.scalarMultiply(safeBounciness));
        }
    }

    /** {@inheritDoc} */
    @Override
    public double getBounciness() {
        return this.bounciness;
    }

    /** {@inheritDoc} */
    @Override
    public Vector2D getPosition() {
        return this.position;
    }
}
