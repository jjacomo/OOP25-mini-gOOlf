package it.unibo.minigoolf.model.map;

import java.util.List;

import it.unibo.minigoolf.util.Vector2D;

import it.unibo.minigoolf.model.ball.Ball;
import it.unibo.minigoolf.model.hole.Hole;
import it.unibo.minigoolf.model.obstacles.Obstacle;
import it.unibo.minigoolf.model.surfaces.Surface;

/**
 * Represents a game map in the mini-gOOlf game, providing access to surfaces,
 * obstacles, ball and hole.
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
     * Returns the hole in the game map.
     * 
     * @return the hole
     */
    Hole getHole();

    /**
     * Returns a list of all surfaces in the game map.
     * 
     * @return a list of all surfaces
     */
    List<Surface> getSurfaces();

    /**
     * Returns a list of all obstacles in the game map.
     * 
     * @return a list of all obstacles
     */
    List<Obstacle> getObstacles();

}
