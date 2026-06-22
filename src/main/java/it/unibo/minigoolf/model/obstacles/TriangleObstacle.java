package it.unibo.minigoolf.model.obstacles;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Triangle;
import it.unibo.minigoolf.model.ball.Ball;

/**
 * Represents a triangular obstacle in the minigolf course.
 * This obstacle is defined by its three vertices and centroid.
 * 
 * @author Mattia
 */
public final class TriangleObstacle extends AbstractObstacle {
    private static final double MIN_SIDE_LENGTH = 10.0;
    private static final double MAX_SIDE_LENGTH = 800.0;
    private static final double MIN_AREA = 25.0;

    private final Vector2D vertex1;
    private final Vector2D vertex2;
    private final Vector2D vertex3;
    private final Vector2D normal12;
    private final Vector2D normal23;
    private final Vector2D normal31;
    private final Triangle shape;

    /**
     * Constructs a triangular obstacle.
     *
     * @param vertex1 the first vertex of the triangle
     * @param vertex2 the second vertex of the triangle
     * @param vertex3 the third vertex of the triangle
     * @throws IllegalArgumentException if the triangle area is less than MIN_AREA
     */
    public TriangleObstacle(final Vector2D vertex1, final Vector2D vertex2, final Vector2D vertex3) {
        this(vertex1, vertex2, vertex3, DEFAULT_BOUNCINESS);
    }

    /**
     * Constructs a bouncy or sticky triangular obstacle.
     *
     * @param vertex1 the first vertex of the triangle
     * @param vertex2 the second vertex of the triangle
     * @param vertex3 the third vertex of the triangle
     * @param bounciness the bounciness of the bouncy triangle obstacle 
     * @throws IllegalArgumentException if the triangle area is less than MIN_AREA
     */
    public TriangleObstacle(final Vector2D vertex1, final Vector2D vertex2, 
                            final Vector2D vertex3, final double bounciness) {
        super(new Vector2D((vertex1.getX() + vertex2.getX() + vertex3.getX()) / 3.0,
                (vertex1.getY() + vertex2.getY() + vertex3.getY()) / 3.0), bounciness);

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
        this.shape = new Triangle(this.vertex1, this.vertex2, this.vertex3);
    }

    /** {@inheritDoc} */
    @Override
    public boolean isColliding(final Ball ball) {
        final Vector2D pos = ball.getPosition();
        return isInside(pos) || getMinDistanceToPerimeter(pos) <= ball.getRadius();
    }

    /** {@inheritDoc} */
    @Override
    public double getPenetrationDepth(final Ball ball) {
        final Vector2D pos = ball.getPosition();
        final double minDist = getMinDistanceToPerimeter(pos);

        if (isInside(pos)) {
            return ball.getRadius() + minDist;
        } else {
            return minDist < ball.getRadius() ? ball.getRadius() - minDist : 0.0;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void resolveCollision(final Ball ball) {
        final Vector2D pos = ball.getPosition();
        final boolean inside = isInside(pos);
        final Vector2D cp1 = getClosestPointOnSegment(pos, vertex1, vertex2);
        final Vector2D cp2 = getClosestPointOnSegment(pos, vertex2, vertex3);
        final Vector2D cp3 = getClosestPointOnSegment(pos, vertex3, vertex1);
        final double d1 = pos.distanceSquared(cp1);
        final double d2 = pos.distanceSquared(cp2);
        final double d3 = pos.distanceSquared(cp3);
        final double minDistSq = Math.min(d1, Math.min(d2, d3));
        final double minDist = Math.sqrt(minDistSq);
        final double penetrationDepth = inside ? ball.getRadius() + minDist
         : ball.getRadius() - minDist;
        final Vector2D normal;

        if (inside) {
            double sumX = 0;
            double sumY = 0;

            if (Math.abs(d1 - minDistSq) < EPSILON) {
                sumX += normal12.getX();
                sumY += normal12.getY();
            }
            if (Math.abs(d2 - minDistSq) < EPSILON) {
                sumX += normal23.getX();
                sumY += normal23.getY();
            }
            if (Math.abs(d3 - minDistSq) < EPSILON) {
                sumX += normal31.getX();
                sumY += normal31.getY();
            }
            normal = new Vector2D(sumX, sumY).normalize();
        } else {
            final Vector2D closestPoint;
            final Vector2D faceNormal;

            if (minDistSq == d1) {
                closestPoint = cp1;
                faceNormal = normal12;
            } else if (minDistSq == d2) {
                closestPoint = cp2;
                faceNormal = normal23;
            } else {
                closestPoint = cp3;
                faceNormal = normal31;
            }

            if (minDist < EPSILON) {
                normal = faceNormal;
            } else {
                normal = pos.subtract(closestPoint).normalize();
            }
        }

        correctPosition(ball, pos, normal, penetrationDepth);
        reflectVelocity(ball, normal);
    }

    /**
     * Checks that the distance between two points lies within the allowed limits.
     *
     * @param endpointA first endpoint of the segment ab
     * @param endpointB second endpoint of the segment ab
     * @param nameA     name of the first endpoint
     * @param nameB     name of the second endpoint
     * @throws IllegalArgumentException if the side length is outside
     *                                  [MIN_SIDE_LENGTH,
     *                                  MAX_SIDE_LENGTH]
     */
    private void checkDistanceBounds(final Vector2D endpointA, final Vector2D endpointB,
            final String nameA, final String nameB) {
        final double distance = endpointA.distance(endpointB);
        if (distance < MIN_SIDE_LENGTH || distance > MAX_SIDE_LENGTH) {
            throw new IllegalArgumentException("Distance " + nameA + " - " + nameB
                    + " = " + String.format("%.2f", distance) 
                    + ". It must be between [" + MIN_SIDE_LENGTH + " and " 
                    + MAX_SIDE_LENGTH + "].");
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
    private double computeTriangleArea(final Vector2D vertexA, final Vector2D vertexB,
                                       final Vector2D vertexC) {
        final Vector2D segmentAB = vertexB.subtract(vertexA);
        final Vector2D segmentAC = vertexC.subtract(vertexA);

        return Math.abs(segmentAB.getX() * segmentAC.getY() - segmentAB.getY()
         * segmentAC.getX()) / 2.0;
    }

    /**
     * Checks if a point lies strictly inside the triangle using outward normals.
     *
     * @param pos the position to check
     * @return true if the point is inside the triangle
     */
    private boolean isInside(final Vector2D pos) {
        final boolean in1 = pos.subtract(vertex1).dotProduct(normal12) <= 0;
        final boolean in2 = pos.subtract(vertex2).dotProduct(normal23) <= 0;
        final boolean in3 = pos.subtract(vertex3).dotProduct(normal31) <= 0;
        return in1 && in2 && in3;
    }

    /**
     * Calculates the minimum distance from a point to the perimeter of the triangle.
     *
     * @param pos the point
     * @return the minimum distance
     */
    private double getMinDistanceToPerimeter(final Vector2D pos) {
        final double d1 = pos.distance(getClosestPointOnSegment(pos, vertex1, vertex2));
        final double d2 = pos.distance(getClosestPointOnSegment(pos, vertex2, vertex3));
        final double d3 = pos.distance(getClosestPointOnSegment(pos, vertex3, vertex1));
        return Math.min(d1, Math.min(d2, d3));
    }

    /**
     * Finds the closest point on a segment to a given point.
     *
     * @param p the point
     * @param a segment start
     * @param b segment end
     * @return the closest point on the segment
     */
    private Vector2D getClosestPointOnSegment(final Vector2D p, final Vector2D a, 
                                              final Vector2D b) {
        final Vector2D ab = b.subtract(a);
        final Vector2D ap = p.subtract(a);
        final double abSq = ab.getNormSquared();

        if (abSq == 0) {
            return a;
        }

        double t = ap.dotProduct(ab) / abSq;
        if (t < 0) {
            t = 0;
        } else if (t > 1) {
            t = 1;
        }

        return new Vector2D(a.getX() + t * ab.getX(), a.getY() + t * ab.getY());
    }

    /**
     * Computes the outward normal of a given edge using the centroid.
     *
     * @param endpointA start point of the edge
     * @param endpointB end point of the edge
     * @return the outward unit normal
     */
    private Vector2D computeOutwardNormal(final Vector2D endpointA, 
                                          final Vector2D endpointB) {
        final Vector2D edge = endpointB.subtract(endpointA);
        final Vector2D perpendicular1 = new Vector2D(edge.getY(), -edge.getX());
        final Vector2D perpendicular2 = new Vector2D(-edge.getY(), edge.getX());
        final Vector2D middle = endpointA.add(endpointB).scalarMultiply(0.5);
        final Vector2D toCentroid = getPosition().subtract(middle);

        return (perpendicular1.dotProduct(toCentroid) < 0) ? perpendicular1.normalize()
                : perpendicular2.normalize();
    }

    /** {@inheritDoc} */
    @Override
    public Triangle getShape() {
        return this.shape;
    }
}
