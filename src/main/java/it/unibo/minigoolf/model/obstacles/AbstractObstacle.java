package it.unibo.minigoolf.model.obstacles;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import it.unibo.minigoolf.model.ball.Ball;

/**
 * Represents a generic obstacle in the game world.
 * Obstacles can have different shapes and interact with the ball,
 * modifying its trajectory upon collision.
 */
public abstract class AbstractObstacle {

    /** Tolerance threshold for floating‑point comparisons in collision detection. */
    protected static final double EPSILON = 1e-10;
    private final Vector2D position;

    /**
     * Constructs an obstacle at the given position.
     *
     * @param position the 2D vector representing the coordinates of the obstacle
     */
    public AbstractObstacle(final Vector2D position) {
        this.position = position;
    }

    /**
     * Gets the position of the obstacle.
     *
     * @return the Vector2D representing the obstacle's coordinates
     */
    public Vector2D getPosition() {
        return this.position;
    }

	/**
     * Checks if the ball is colliding with the obstacle's boundaries.
     *
     * @param ball the ball object to be checked for collisions
     * @return true if the ball's boundaries touch the obstacle, false otherwise
     */
    public abstract boolean isColliding(Ball ball);

	/**
     * Resolves the physical collision between the ball and the obstacle calculating
     * the bounce based on the obstacle's shape and applies the new direction to the ball.
     * 
     * @param ball the Ball object that has collided with the obstacle
     */
    public abstract void resolveCollision(Ball ball);

    /**
     * Corrects the ball's position to resolve penetration with the obstacle.
     * Moves the ball outward along the collision normal by the penetration depth.
     *
     * @param ball the ball to reposition
     * @param normal the collision normal (unit vector pointing outward from the obstacle)
     * @param penetrationDepth the amount of overlap (positive value)
     */
    protected void correctPosition(final Ball ball, final Vector2D ballPosition, final Vector2D normal, final double penetrationDepth) {
        final Vector2D newPosition = ballPosition.add(normal.scalarMultiply(penetrationDepth));
        ball.setPosition(newPosition);
    }

    /**
     * Reflects the ball's velocity according to the collision normal.
     * Uses the elastic reflection formula: v' = v - 2 (v·n) n.
     * If the ball is already moving away from the obstacle (dot ≥ 0), no change is made.
     *
     * @param ball   the ball whose velocity to modify
     * @param normal the collision normal (unit vector pointing outward from the obstacle)
     */
    protected void reflectVelocity(final Ball ball, final Vector2D normal) {
        final Vector2D velocity = ball.getVelocity();
        final double dotProduct = velocity.dotProduct(normal);
        if (dotProduct >= 0) {
            return;
        }
        final Vector2D reflection = normal.scalarMultiply(2 * dotProduct);
        ball.setVelocity(velocity.subtract(reflection));
    }
}
