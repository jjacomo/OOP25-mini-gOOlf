package it.unibo.minigoolf.model.obstacles;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import it.unibo.minigoolf.model.ball.Ball;

/**
 * Represents a circular obstacle in the minigolf course.
 * This obstacle is defined by its center position and radius.
 */
public final class RoundObstacle extends AbstractObstacle {
    private static final double EPSILON = 1e-10;
    
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


    public boolean isColliding(final Ball ball) {
        return true;
    }

    public void resolveCollision(final Ball ball) {
    }
}
