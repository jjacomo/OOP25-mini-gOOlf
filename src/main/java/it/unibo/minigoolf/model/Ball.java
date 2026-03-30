package it.unibo.minigoolf.model;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * Interface representing the ball in the mini-golf game.
 * 
 * <p>A Ball is the main entity controlled by the player and interacts with
 * map surfaces and obstacles. It possesses physical properties such as
 * position, velocity, and radius, which are used to simulate movement
 * and collisions.</p>
 * 
 * <p>The interface provides methods to access and modify the ball's state
 * during gameplay.</p>
 * 
 * @author jack
 */
public interface Ball {
    /**
     * Returns the current position of the ball in the game coordinate system.
     * 
     * @return a {@link Vector2D} representing the ball's x,y position
     */
    Vector2D getPosition();

    /**
     * Returns the current velocity of the ball.
     * 
     * <p>The velocity is expressed as a two-dimensional vector, where the components
     * represent the velocity along the x and y axes.</p>
     * 
     * @return a {@link Vector2D} representing the ball's velocity
     */
    Vector2D getVelocity();

    /**
     * Returns the radius of the ball.
     * 
     * <p>The radius is used to calculate collisions with obstacles and surfaces,
     * as well as for rendering the ball on the game screen.</p>
     * 
     * @return the ball's radius in game units
     */
    double getRadius();

    /**
     * Sets the position of the ball.
     * 
     * <p>This method is typically used to initialize the ball at the beginning
     * of a hole or after resolving a collision.</p>
     * 
     * @param position a {@link Vector2D} representing the new position of the ball
     */
    void setPosition(Vector2D position);

    /**
     * Sets the velocity of the ball.
     * 
     * <p>This method updates the ball's velocity vector, affecting its movement
     * during the next physics simulation cycle.</p>
     * 
     * @param velocity a {@link Vector2D} representing the new velocity of the ball
     */
    void setVelocity(Vector2D velocity);
}
