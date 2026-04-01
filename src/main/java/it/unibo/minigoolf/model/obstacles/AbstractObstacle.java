package it.unibo.minigoolf.model.obstacles;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import it.unibo.minigoolf.model.Ball;
/**
 * Represents a generic obstacle in the game world.
 * Obstacles can have different shapes and interact with the ball,
 * modifying its trajectory upon collision.
 */
public abstract class AbstractObstacle {

    /**
     * The position of the obstacle in the 2D space.
     */
    protected Vector2D position;

    /**
     * Constructs a new Obstacle at the specified position.
     *
     * @param position the 2D vector representing the coordinates of the obstacle
     */
    public AbstractObstacle(Vector2D position) {
        this.position = position;
    }

    /**
     * Gets the current position of the obstacle.
     *
     * @return the Vector2D representing the obstacle's coordinates
     */
    public Vector2D getPosition() {
        return this.position;
    }

    /**
     * Checks if the ball is colliding with the obstacle's boundaries.
     *
     * @param ball the Ball object to check for collisions
     * @return true if the ball's boundaries touch the obstacle, false otherwise
     */
    public abstract boolean isColliding(Ball ball);

    /**
     * Resolves the physical collision between the ball and the obstacle.
     * This method calculates the bounce (new velocity vector) based on the obstacle's
     * specific shape and applies the new direction to the ball.
     *
     * @param ball the Ball object that has collided with the obstacle
     */
    public abstract void resolveCollision(Ball ball);
}
