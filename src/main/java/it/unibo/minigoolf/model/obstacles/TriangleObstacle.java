package it.unibo.minigoolf.model.obstacles;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import it.unibo.minigoolf.model.ball.Ball;

/**
 * Represents a triangular obstacle in the minigolf course.
 * This obstacle is defined by its three arbitrary vertices.
 */
public final class TriangleObstacle extends AbstractObstacle implements Obstacle{ 
    private final Vector2D vertex1;
    private final Vector2D vertex2;
    private final Vector2D vertex3;

    /**
     * Constructs a triangular obstacle using three vertices.
	 * As position we use the centroid of the triangle
     *
     * @param vertex1 the first vertex of the triangle
     * @param vertex2 the second vertex of the triangle
     * @param vertex3 the third vertex of the triangle
     */
    public TriangleObstacle(final Vector2D vertex1, final Vector2D vertex2, final Vector2D vertex3) {
        super(new Vector2D((vertex1.getX() + vertex2.getX() + vertex3.getX()) / 3, 
                           (vertex1.getY() + vertex2.getY() + vertex3.getY()) / 3));
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;
    }

    /**
     * Calculates the closest point on a line segment (from A to B) to a given point P.
     *
     * @param point_p the ball's center
     * @param endpoint_a the start of the line segment
     * @param endpoint_b the end of the line segment
     * @return the closest point on the segment ab to the point p
     */
    private Vector2D getClosestPointOnSegment(final Vector2D point_p, final Vector2D endpoint_a, final Vector2D endpoint_b) {
        final Vector2D ab = endpoint_b.subtract(endpoint_a);
        final Vector2D ap = point_p.subtract(endpoint_a);
        final double lengthSquared = ab.getNormSq();
        if (lengthSquared == 0) {
            return endpoint_a; // The segment is just a single point
        }
        // The relative distance from endpoint_a of the projection of the center of the ball on the segment ab
        double relative_distance = ap.dotProduct(ab) / lengthSquared;
        // relative_distance must be between 0 and 1 to ensure the point stays on the segment
        relative_distance = Math.max(0, Math.min(1, relative_distance));
        
        return endpoint_a.add(ab.scalarMultiply(relative_distance));
    }

    /**
     * Finds the point on the perimeter of the triangle closest to the ball's center.
     *
     * @param ballCenter the center of the ball
     * @return the closest Vector2D point on the triangle's edges
     */
    private Vector2D getClosestPoint(final Vector2D ballCenter) {
        // Find the closest points on all three edges
        final Vector2D closest1 = getClosestPointOnSegment(ballCenter, vertex1, vertex2);
        final Vector2D closest2 = getClosestPointOnSegment(ballCenter, vertex2, vertex3);
        final Vector2D closest3 = getClosestPointOnSegment(ballCenter, vertex3, vertex1);
        
        // Find which of these three points is the absolute closest to the ball
        double dist1 = ballCenter.distanceSq(closest1);
        double dist2 = ballCenter.distanceSq(closest2);
        double dist3 = ballCenter.distanceSq(closest3);
        double minDist = Math.min(dist1, Math.min(dist2, dist3));

        if (minDist == dist1){
            return closest1;
        }
        if (minDist == dist2){
            return closest2;
        } 
        return closest3;
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
        final Vector2D normal = computeCollisionNormal(collisionVector, distance);
		final double penetrationDepth = ball.getRadius() - distance;
		
		if (penetrationDepth > 0) {
			correctPosition(ball, ballPos, normal, penetrationDepth);
        }
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
    private Vector2D computeCollisionNormal(final Vector2D collisionVector, final double distance) {
        if (distance < EPSILON) {
            // Edge case: ball center is exactly on the triangle edge
            return new Vector2D(0, 1); 
        }
        return collisionVector.normalize();
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
