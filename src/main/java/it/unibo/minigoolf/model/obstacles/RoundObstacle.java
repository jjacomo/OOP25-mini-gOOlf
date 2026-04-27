package it.unibo.minigoolf.model.obstacles;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import it.unibo.minigoolf.model.ball.Ball;

/**
 * Represents a circular obstacle in the minigolf course.
 * This obstacle is defined by its center position and radius.
 */
public final class RoundObstacle extends AbstractObstacle implements Obstacle{ 
    private static final double MIN_RADIUS = 5.0;
    private static final double MAX_RADIUS = 150.0;
    private final double radius;

    /**
     * Constructs a circular obstacle.
     *
     * @param center the center of the circle (must not be null)
     * @param radius the radius of the circular obstacle
     * @throws IllegalArgumentException if radius is not between [MIN_RADIUS, MAX_RADIUS]
     */
    public RoundObstacle(final Vector2D position, final double radius) {
        super(position);
        if (radius < MIN_RADIUS || radius > MAX_RADIUS) {
            throw new IllegalArgumentException("The radius must be between " 
                                             + MIN_RADIUS + " and " + MAX_RADIUS);
        }
        this.radius = radius;
    }
    
    /**
     * Checks if the ball is colliding with the obstacle's boundaries.
     *
     * @param ball the Ball object to be checked for collisions
     * @return true if the ball's boundaries touch the obstacle, false otherwise
     */
    @Override
    public boolean isColliding(final Ball ball) {
        final double distance = ball.getPosition().distance(this.getPosition());
        return distance <= (ball.getRadius() + this.radius);
    }

    /**
     * Resolves the physical collision between the ball and the obstacle calculating
     * the bounce based on the obstacle's shape and applies the new direction to the ball.
     * 
     * @param ball the Ball object that has collided with the obstacle
     */
    @Override
    public void resolveCollision(final Ball ball) {
        final Vector2D ballPosition = ball.getPosition();
        final Vector2D obstaclePos = this.getPosition();
        final Vector2D collisionVector = ballPosition.subtract(obstaclePos);
        final double distance = collisionVector.getNorm();
        final Vector2D normal = computeCollisionNormal(collisionVector, distance, ball.getVelocity());
        final double penetrationDepth = (ball.getRadius() + this.radius) - distance;
		
        if (penetrationDepth > 0) {
            correctPosition(ball, ballPosition, normal, penetrationDepth);
        }
        reflectVelocity(ball, normal);
    }

    /**
     * Computes the collision normal pointing from the obstacle to the ball.
     * Handles the edge case where the centers perfectly overlap.
     *
     * @param collisionVector vector from obstacle center to ball center
     * @param distance the distance between the two centers
     * @param velocity the velocity of the ball
     * @return the normalized collision normal
     */
    private Vector2D computeCollisionNormal(final Vector2D collisionVector, final double distance, final Vector2D velocity) {
        if (distance >= EPSILON) {
            return collisionVector.normalize();
        }

        return  velocity.scalarMultiply(-1).normalize();
    }
}
