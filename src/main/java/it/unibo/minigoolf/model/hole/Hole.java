package it.unibo.minigoolf.model.hole;

import it.unibo.minigoolf.util.Vector2D;

/**
 * Represents the hole in the mini-gOOlf game, defined by its position and radius.
 * The hole is the target for the ball to reach in order to complete a level.
 * 
 * @author jack
 */
public interface Hole {
    /**
     * Returns the current position of the hole in the game coordinate system.
     * 
     * @return a {@link Vector2D} representing the hole's x,y position
     */
    Vector2D getPosition();

    /**
     * Returns the radius of the hole.
     * 
     * @return the hole's radius in game units
     */
    double getRadius();

}
