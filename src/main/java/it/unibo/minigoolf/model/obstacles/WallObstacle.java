package it.unibo.minigoolf.model.obstacles;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import it.unibo.minigoolf.model.ball.Ball;

/**
 * Represents a rectangular wall obstacle in the minigolf course.
 * This obstacle is defined by its top-left position, width, and height.
 */
public final class WallObstacle extends AbstractObstacle {
    private static final double EPSILON = 1e-10;
    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxY;
    /**
     * Constructs a rectangular wall.
     * 
     * @param position the 2D vector representing the top-left corner of the wall
     * @param width    the width of the wall
     * @param height   the height of the wall
     */

    public WallObstacle(final Vector2D position, final double width, final double height) {
        super(position);
        this.minX = position.getX();
        this.maxX = position.getX() + width;
        this.minY = position.getY();
        this.maxY = position.getY() + height;
    }

    /**
     * Finds the point on the perimeter of the rectangle closest to the center of the ball.
     * 
     * @param ballCenter the 2D vector representing the center of the ball
     * @return the Vector2D representing the closest point on the wall to the ball
     */
    private Vector2D getClosestPoint(final Vector2D ballCenter) {
        final double closestX = Math.max(minX, Math.min(ballCenter.getX(), maxX));
        final double closestY = Math.max(minY, Math.min(ballCenter.getY(), maxY));

        return new Vector2D(closestX, closestY);
    }

    /**
     * Checks if the ball is currently colliding with the wall.
     * 
     * @param ball the ball to be checked for collision
     * @return true if the ball collides with the wall, false otherwise
     */
    @Override
    public boolean isColliding(final Ball ball) {
        final Vector2D closestPoint = getClosestPoint(ball.getPosition());
        final double distance = ball.getPosition().distance(closestPoint);

        return distance <= ball.getRadius();
    }

    /**
     * Resolves the collision between the wall and the ball by calculating the reflection 
     * and correcting the ball's position to prevent overlap.
     * 
     * @param ball the ball on which to apply the collision physics
     */
    @Override
    public void resolveCollision(final Ball ball) {
        final Vector2D ballPos = ball.getPosition();
        final Vector2D closestPoint = getClosestPoint(ballPos);
        final Vector2D collisionVector = ballPos.subtract(closestPoint);
        final double distance = collisionVector.getNorm();
        final Vector2D normal = computeCollisionNormal(ballPos, collisionVector, distance);
        correctPosition(ball, ballPos, normal, distance);
        reflectVelocity(ball, normal);
    }

    /**
     * Computes the collision normal based on whether the ball's center is
     * on the boundary or in a standard collision state.
     * 
     * @param ballPos the current position of the ball's center
     * @param collisionVector vector from closest point to ball center
     * @param distance the distance between ball center and closest point
     * @return the normalized collision normal
     */
    private Vector2D computeCollisionNormal(final Vector2D ballPos, final Vector2D collisionVector,
                                            final double distance) {
        if (distance < EPSILON) {
            return computeNormalForBoundaryCase(ballPos);
        }

        return collisionVector.normalize();
    }

    /**
     * Computes the collision normal for the boundary case where the ball's center
     * lies exactly on the rectangle perimeter or inside it. Handles corners correctly
     * by accumulating normals from all equidistant sides.
     * 
     * @param ballPos the current position of the ball's center
     * @return the normalized collision normal pointing outward from the wall
     */
    private Vector2D computeNormalForBoundaryCase(final Vector2D ballPos) {
        double accX = 0;
        double accY = 0;
        final double dLeft = Math.abs(ballPos.getX() - minX);
        final double dRight = Math.abs(ballPos.getX() - maxX);
        final double dTop = Math.abs(ballPos.getY() - minY);
        final double dBottom = Math.abs(ballPos.getY() - maxY);
        final double minDist = Math.min(Math.min(dLeft, dRight), Math.min(dTop, dBottom));
        // Adaptive tolerance to handle any inaccuracies in dLato distance calculations
        final double tolerance = Math.max(EPSILON, Math.abs(minDist) * EPSILON);
        if (Math.abs(dLeft - minDist) <= tolerance) {
            accX -= 1;
        }
        if (Math.abs(dRight - minDist) <= tolerance) {
            accX += 1;
        }
        if (Math.abs(dTop - minDist) <= tolerance) {
            accY -= 1;
        }
        if (Math.abs(dBottom - minDist) <= tolerance) {
            accY += 1;
        }

        return new Vector2D(accX, accY).normalize();
    }

    /**
     * Corrects the ball's position to resolve penetration with the wall.
     * 
     * @param ball the ball to correct
     * @param ballPos the current position of the ball's center
     * @param normal the collision normal
     * @param distance the distance between ball center and closest point
     */
    private void correctPosition(final Ball ball, final Vector2D ballPos,
                                 final Vector2D normal, final double distance) {
        final double penetrationDepth = ball.getRadius() - distance;
        if (penetrationDepth > 0) {
            ball.setPosition(ballPos.add(normal.scalarMultiply(penetrationDepth)));
        }
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
