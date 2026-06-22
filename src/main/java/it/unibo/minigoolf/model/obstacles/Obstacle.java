package it.unibo.minigoolf.model.obstacles;

import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.util.shapes.Shape;
import it.unibo.minigoolf.model.ball.Ball;

/**
 * Represents the generic obstacles.
 * 
 * @author Mattia
 */
public interface Obstacle {
    /**
     * Gets the current position of the obstacle.
     *
     * @return the Vector2D representing the obstacle's coordinates
     */
    Vector2D getPosition();

    /**
     * Checks if the ball is colliding with the obstacle's boundaries.
     *
     * @param ball the Ball object to check for collisions
     * @return     true if the ball's boundaries touch the obstacle, false otherwise
     */
    boolean isColliding(Ball ball);

    /**
     * Resolves the physical collision between the ball and the obstacle.
     * 
     * <p>This method calculates the bounce (new velocity vector) based on the obstacle's
     * specific shape and applies the new direction to the ball.</p>
     *
     * @param ball the Ball object that has collided with the obstacle
     */
    void resolveCollision(Ball ball);

    /**
     * Calculates the penetration depth of the ball into the obstacle.
     *
     * @param ball the ball to check
     * @return     the penetration depth (a value > 0 if colliding, 0 otherwise)
     */
    double getPenetrationDepth(Ball ball);

    /**
     * Returns the bounciness of the obstacle.
     * 
     * @return the bounciness
     */
    double getBounciness();

    /**
     * Returns the shape of the obstacle.
     * 
     * @return the shape of the obstacle
     */
    Shape getShape();
}
