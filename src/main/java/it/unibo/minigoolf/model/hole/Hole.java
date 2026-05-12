package it.unibo.minigoolf.model.hole;

import it.unibo.minigoolf.util.Vector2D;

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
