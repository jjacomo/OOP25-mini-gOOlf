package it.unibo.minigoolf.model.obstacles;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import it.unibo.minigoolf.model.ball.Ball;

public class TriangleObstacle extends AbstractObstacle implements Obstacle{
    private static final double EPSILON = 1e-10;
    
    private final Vector2D vertex1;
    private final Vector2D vertex2;
    private final Vector2D vertex3;

    /**
     * Constructs a triangular obstacle using three vertices.
     *
     * @param vertex1 the first vertex of the triangle
     * @param vertex2 the second vertex of the triangle
     * @param vertex3 the third vertex of the triangle
     */
    public TriangleObstacle(final Vector2D vertex1, final Vector2D vertex2, final Vector2D vertex3) {
        // As a base position for AbstractObstacle, we can use the centroid (center of mass) of the triangle
        super(new Vector2D((vertex1.getX() + vertex2.getX() + vertex3.getX()) / 3, 
                           (vertex1.getY() + vertex2.getY() + vertex3.getY()) / 3));
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;
    }

    public void resolveCollision(final Ball ball) {};

    public boolean isColliding(final Ball ball) {
        return true;
    }
}
