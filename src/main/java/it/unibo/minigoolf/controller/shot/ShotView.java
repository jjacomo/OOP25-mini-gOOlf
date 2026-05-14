package it.unibo.minigoolf.controller.shot;

import java.awt.Point;

/**
 * Minimal interface through which {@link ShotControllerImpl}
 * communicates with the shot-input view.
 * Keeping this boundary narrow means the controller stays decoupled
 * from the full view implementation: it only needs to enable input
 * at a given position.
 *
 * @author fede
 */
public interface ShotView {

    /**
     * Activates shot input for the current turn at the given ball position.
     *
     * @param ballPosition the ball centre in logical (1920x1080) coordinates
     */
    void enableShot(Point ballPosition);
}
