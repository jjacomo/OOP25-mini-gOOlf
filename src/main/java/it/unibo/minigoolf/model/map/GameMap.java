package it.unibo.minigoolf.model.map;

import java.util.List;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.model.surfaces.Surface;

/**
 * Represents a game map in the mini-golf game, providing access to surfaces at specific positions.
 * 
 * @author jack
 */
public interface GameMap {
    /**
     * Returns the surface with highest z index at the given position.
     * 
     * @param position the position to check
     * 
     * @return the surface with highest z index at the given position.
     */
    Surface getSurfaceAt(Vector2D position);

    /**
     * Returns the ball in the game map.
     * 
     * @return the ball
     */
    Ball getBall();

    /**
     * Returns a list of all surfaces in the game map.
     * 
     * @return a list of all surfaces
     */
    List<Surface> getSurfaces();

}
