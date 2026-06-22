package it.unibo.minigoolf.controller.shot;

import java.awt.Point;

/**
 * Minimal interface through which {@link ShotControllerImpl}
 * communicates with the shot-input view.
 * Created to respetc the mvc pattern.
 *
 * @author fedesparvo1-a11y
 */
@FunctionalInterface
public interface ShotView {

    /**
     * Activates shot input for the current turn at the given ball position.
     *
     * @param ballPosition the ball centre in logical (1920x1080) coordinates
     */
    void enableShot(Point ballPosition);
}
