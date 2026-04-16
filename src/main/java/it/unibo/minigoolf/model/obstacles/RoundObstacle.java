package it.unibo.minigoolf.model.obstacles;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import it.unibo.minigoolf.model.ball.Ball;

/**
 * Represents a circular obstacle in the minigolf course.
 * This obstacle is defined by its center position and radius.
 */
public final class RoundObstacle extends AbstractObstacle implements Obstacle{ 
    private final double radius;

    /**
     * Constructs a circular obstacle.
     * 
	 * @param position the 2D vector representing the center of the circle
     * @param radius   the radius of the circular obstacle
     */
    public RoundObstacle(final Vector2D position, final double radius) {
        super(position);
        this.radius = radius;
    }
    
    /**
     * Checks if the ball is currently colliding with the circular obstacle.
     *
	 * @param ball the ball to be checked for collision
     * @return true if the ball collides with the obstacle, false otherwise
     */
    @Override
    public boolean isColliding(final Ball ball) {
        final double distance = ball.getPosition().distance(this.getPosition());
        return distance <= (ball.getRadius() + this.radius);
    }

    public void resolveCollision(final Ball ball) {
        final Vector2D ballPos = ball.getPosition();
        final Vector2D obstaclePos = this.getPosition();
        
        // Vector from the center of the obstacle to the center of the ball
        final Vector2D collisionVector = ballPos.subtract(obstaclePos);
        final double distance = collisionVector.getNorm();

        final Vector2D normal = computeCollisionNormal(collisionVector, distance, ball.getVelocity());

        // Penetration is calculated using the sum of the two radii
        final double penetrationDepth = (ball.getRadius() + this.radius) - distance;
        if (penetrationDepth > 0) {
            correctPosition(ball, ballPos, normal, penetrationDepth);
        }
        reflectVelocity(ball, normal);
    }

    /**
     * Computes the collision normal pointing from the obstacle to the ball.
     * Handles the edge case where the centers perfectly overlap.
     *
     * @param collisionVector vector from obstacle center to ball center
     * @param distance the distance between the two centers
     * @return the normalized collision normal
     */
    private Vector2D computeCollisionNormal(final Vector2D collisionVector, final double distance, final Vector2D velocity) {
        if (distance >= EPSILON) {
            return collisionVector.normalize();
        }

        return  velocity.scalarMultiply(-1).normalize();
    }

    /**
     * Corrects the ball's position to resolve penetration with the obstacle.
     *
     * @param ball the ball to correct
     * @param ballPos the current position of the ball's center
     * @param normal the collision normal
     * @param distance the distance between the two centers
     */
    private void correctPosition(final Ball ball, final Vector2D ballPos,
                                 final Vector2D normal, final double penetrationDepth) {
        ball.setPosition(ballPos.add(normal.scalarMultiply(penetrationDepth)));
    }

    /**
     * Reflects the ball's velocity based on the collision normal.
     *
     * @param ball the ball whose velocity to reflect
     * @param normal the collision normal
     */
    private void reflectVelocity(final Ball ball, final Vector2D normal) {
        final Vector2D velocity = ball.getVelocity();
        final double dotProduct = velocity.dotProduct(normal);
        // If the ball is already moving away, no reflection needed
        if (dotProduct > 0) {
            return;
        }
        // Apply the reflection formula: v' = v - 2 * (v · n) * n
        final Vector2D reflection = normal.scalarMultiply(2 * dotProduct);
        ball.setVelocity(velocity.subtract(reflection));
    }
}
