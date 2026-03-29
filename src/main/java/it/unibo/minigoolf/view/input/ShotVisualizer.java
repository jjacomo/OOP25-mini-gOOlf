package it.unibo.minigoolf.view.input;

import it.unibo.minigoolf.util.Vec2D;
 
/**
 * Interface implemented by the panel that draws the shot-intent indicator.
 *
 * @author fede
 */
public interface ShotVisualizer {
 
    /**
     * Updates the visual indicator shown while the user is dragging.
     *
     * @param direction the shot direction
     */
    void updateShotIntent(Vec2D direction);
 
    /**
     * Called when the user releases the mouse.
     */
    void shoot();
}
