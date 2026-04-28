package it.unibo.minigoolf.model.obstacles;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.model.ball.Ball;

/**
 * Represents a triangular obstacle in the minigolf course.
 * This obstacle is defined by its three vertex and centroid.
 */
public final class TriangleObstacle extends AbstractObstacle implements Obstacle {
    private static final double MIN_SIDE_LENGTH = 10.0;
    private static final double MAX_SIDE_LENGTH = 800.0;
    private static final double MIN_AREA = 25.0;

    private final Vector2D vertex1;
    private final Vector2D vertex2;
    private final Vector2D vertex3;
	private final Vector2D normal12;
    private final Vector2D normal23;
    private final Vector2D normal31;

	/**
	* Constructs a triangular obstacle.
	*
	* @param vertex1 the first vertex of the triangle
	* @param vertex2 the second vertex of the triangle
	* @param vertex3 the third vertex of the triangle
	* @throws IllegalArgumentException if the triangle area is less than MIN_AREA
	*/
    public TriangleObstacle(final Vector2D vertex1, final Vector2D vertex2, final Vector2D vertex3) {
        super(new Vector2D((vertex1.getX() + vertex2.getX() + vertex3.getX()) / 3.0,
                            (vertex1.getY() + vertex2.getY() + vertex3.getY()) / 3.0));

        checkDistanceBounds(vertex1, vertex2, "vertex1", "vertex2");
        checkDistanceBounds(vertex2, vertex3, "vertex2", "vertex3");
        checkDistanceBounds(vertex3, vertex1, "vertex3", "vertex1");
        final double area = computeTriangleArea(vertex1, vertex2, vertex3);
        if (area < MIN_AREA) {
            throw new IllegalArgumentException("Triangle too flat. Area: " + area
			+ ". It must be over " + MIN_AREA + ".");
		}

        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;
        this.normal12 = computeOutwardNormal(this.vertex1, this.vertex2);
        this.normal23 = computeOutwardNormal(this.vertex2, this.vertex3);
        this.normal31 = computeOutwardNormal(this.vertex3, this.vertex1);
    }

    /**
     * Checks if the ball is colliding with the obstacle's boundaries.
     *
     * @param ball the ball object to be checked for collisions
     * @return true if the ball's boundaries touch the obstacle, false otherwise
     */
    @Override
    public boolean isColliding(final Ball ball) {
        final Vector2D ballPosition = ball.getPosition();
        final double radiusSquared = ball.getRadius() * ball.getRadius();
        double[] bestDistanceSquared = { Double.POSITIVE_INFINITY };
        Vector2D[] bestPoint = { null };
        Vector2D[] bestNormal = { null };

        evaluateEdge(ballPosition, this.vertex1, this.vertex2, this.normal12, bestDistanceSquared, bestPoint, bestNormal);
        evaluateEdge(ballPosition, this.vertex2, this.vertex3, this.normal23, bestDistanceSquared, bestPoint, bestNormal);
        evaluateEdge(ballPosition, this.vertex3, this.vertex1, this.normal31, bestDistanceSquared, bestPoint, bestNormal);

        return bestDistanceSquared[0] <= radiusSquared;
    }

	/**
     * Resolves the physical collision between the ball and the obstacle calculating
     * the bounce based on the obstacle's shape and applies the new direction to the ball.
     * 
     * @param ball the ball object that has collided with the obstacle
     */
    @Override
    public void resolveCollision(final Ball ball) {
        final Vector2D ballPosition = ball.getPosition();

        double[] bestDistanceSquared = { Double.POSITIVE_INFINITY };
        Vector2D[] bestPoint = { null };
        Vector2D[] bestNormal = { null };

        evaluateEdge(ballPosition, this.vertex1, this.vertex2, this.normal12, bestDistanceSquared, bestPoint, bestNormal);
        evaluateEdge(ballPosition, this.vertex2, this.vertex3, this.normal23, bestDistanceSquared, bestPoint, bestNormal);
        evaluateEdge(ballPosition, this.vertex3, this.vertex1, this.normal31, bestDistanceSquared, bestPoint, bestNormal);

        final double distance = Math.sqrt(bestDistanceSquared[0]);
        final double penetrationDepth = ball.getRadius() - distance;
        final Vector2D normal = bestNormal[0];

        if (penetrationDepth > 0) {
            correctPosition(ball, ballPosition, normal, penetrationDepth);
        }
        reflectVelocity(ball, normal);
    }

	/**
	* Checks that the distance between two points lies within the allowed limits.
	*
	* @param endpointA first endpoint of the segment ab
	* @param endpointB second endpoint of the segment ab
	* @param nameA name of the first endpoint
	* @param nameB name of the second endpoint
	* @throws IllegalArgumentException if the side length is outside [MIN_SIDE_LENGTH, 
	*			MAX_SIDE_LENGTH]
	*/
    private void checkDistanceBounds(final Vector2D endpointA, final Vector2D endpointB,
                                     final String nameA, final String nameB) {
        final double distance = endpointA.distance(endpointB);
        if (distance < MIN_SIDE_LENGTH || distance > MAX_SIDE_LENGTH) {
            throw new IllegalArgumentException("Distance " + nameA + " - " + nameB
			+ " = " + String.format("%.2f", distance) + ". It must be between ["
			+ MIN_SIDE_LENGTH + " and " + MAX_SIDE_LENGTH + "].");
        }
    }
    
	/**
     * Computes the area of a triangle using the 2D cross product.
     *
     * @param vertexA first vertex
     * @param vertexB second vertex
     * @param vertexC third vertex
     * @return the absolute area of the triangle
     */
    private double computeTriangleArea(final Vector2D vertexA, final Vector2D vertexB, final Vector2D vertexC) {
        final Vector2D segmentAB = vertexB.subtract(vertexA);
        final Vector2D segmentAC = vertexC.subtract(vertexA);

        return Math.abs(segmentAB.getX() * segmentAC.getY() - segmentAB.getY() * segmentAC.getX()) / 2.0;
    }

	/**
     * Computes the outward normal of a given edge.
     *
     * @param endpointA start point of the edge
     * @param endpointB end point of the edge
     * @return the outward unit normal
     */
    private Vector2D computeOutwardNormal(final Vector2D endpointA, final Vector2D endpointB) {
        final Vector2D edge = endpointB.subtract(endpointA);
        final Vector2D perpendicular1 = new Vector2D(edge.getY(), -edge.getX());
        final Vector2D perpendicular2 = new Vector2D(-edge.getY(), edge.getX());
        final Vector2D middle = endpointA.add(endpointB).scalarMultiply(0.5);
        final Vector2D toCentroid = getPosition().subtract(middle);

        return (perpendicular1.dotProduct(toCentroid) < 0) ? perpendicular1.normalize() : perpendicular2.normalize();
    }

    /**
     * Evaluates whether the side examined is the closest to the ball. 
     */
    private void evaluateEdge(final Vector2D ballPosition, final Vector2D endpointA, 
								final Vector2D endpointB, final Vector2D faceNormal,
								double[] bestDistanceSquared, Vector2D[] bestPoint,
								Vector2D[] bestNormal) {
        final Vector2D segmentAB = endpointB.subtract(endpointA);
        final Vector2D segmentAP = ballPosition.subtract(endpointA);
        final double lengthSquared = segmentAB.getNormSquared();
        final double scalarProjection = segmentAP.dotProduct(segmentAB) / lengthSquared;
        Vector2D closestPoint;
        Vector2D normal;

        if (scalarProjection <= 0) {
            closestPoint = endpointA;
            final Vector2D radial = ballPosition.subtract(endpointA);
            normal = (radial.getNormSquared() < EPSILON) ? faceNormal : radial.normalize();
        } else if (scalarProjection >= 1) {
            closestPoint = endpointB;
            final Vector2D radial = ballPosition.subtract(endpointB);
            normal = (radial.getNormSquared() < EPSILON) ? faceNormal : radial.normalize();
        } else {
            closestPoint = endpointA.add(segmentAB.scalarMultiply(scalarProjection));
            normal = faceNormal;
        }
        final double distSq = ballPosition.distanceSquared(closestPoint);
        if (distSq < bestDistanceSquared[0]) {
            bestDistanceSquared[0] = distSq;
            bestPoint[0] = closestPoint;
            bestNormal[0] = normal;
        }
    }
}
