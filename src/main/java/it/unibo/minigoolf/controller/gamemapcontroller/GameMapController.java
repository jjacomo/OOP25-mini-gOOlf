package it.unibo.minigoolf.controller.gamemapcontroller;

import java.util.List;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import it.unibo.minigoolf.model.surfaces.Surface;
import it.unibo.minigoolf.util.shapes.Shape;

/**
 * Controller for managing the game map and its interactions.
 * 
 * @author jack
 */
public interface GameMapController {
    List<Surface> getSurfaces();
    // List<Obstacle> getObstacles();
    // Hole getHole();
    Shape getBallShape(); // e' meglio cosi oppure restituire direttamente la palla? Cosi comunque funziona
    void updateBallPosition(Vector2D position);
    void updateBallVelocity(Vector2D velocity);
    // altri metodi per gli ostacoli
}
