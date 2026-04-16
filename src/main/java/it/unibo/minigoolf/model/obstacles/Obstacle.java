package it.unibo.minigoolf.model.obstacles;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import it.unibo.minigoolf.model.ball.Ball;

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
     * @return true if the ball's boundaries touch the obstacle, false otherwise
     */
    boolean isColliding(Ball ball);

    /**
     * Resolves the physical collision between the ball and the obstacle.
     * This method calculates the bounce (new velocity vector) based on the obstacle's
     * specific shape and applies the new direction to the ball.
     *
     * @param ball the Ball object that has collided with the obstacle
     */
    void resolveCollision(Ball ball);

}
