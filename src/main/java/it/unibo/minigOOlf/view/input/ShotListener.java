package it.unibo.minigOOlf.view.input;

import it.unibo.minigOOlf.util.Vec2D;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Mouse listener that translates drag gestures into shot vectors.
 *
 * Flow:
 *  1. mousePressed  → record the starting point (where the drag begins)
 *  2. mouseDragged  → compute the vector from start to current pos,
 *                     negate it (drag away from ball = shoot forward),
 *                     and send it to the ShotVisualizer for live preview.
 *  3. mouseReleased → tell the ShotVisualizer to commit the shot.
 *
 * Can be enabled/disabled via {@link ShotInput#setEnable(boolean)} so that
 * input is ignored while the ball is still moving.
 *
 * @author fede
 */
public class ShotListener extends MouseAdapter implements ShotInput {

    private final ShotVisualizer visualizer;

    /** Where the drag started (null when no drag is in progress). */
    private Point startingPoint = null;

    /** Whether this listener is accepting input. */
    private boolean enable = false;

    /**
     * @param visualizer the panel that draws the indicator and handles shots
     */
    public ShotListener(ShotVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    // ------------------------------------------------------------------ //
    //  MouseAdapter overrides                                              //
    // ------------------------------------------------------------------ //

    @Override
    public synchronized void mousePressed(MouseEvent e) {
        if (this.enable) {
            this.startingPoint = e.getPoint();
        }
    }

    @Override
    public synchronized void mouseDragged(MouseEvent e) {
        if (this.enable && this.startingPoint != null) {
            // Build the raw drag vector (start → current cursor position)
            Vec2D raw = new Vec2D(this.startingPoint, e.getPoint());
            // Negate it: dragging the club backward means shooting the ball forward
            Vec2D shotDirection = raw.getOppositeVector();
            this.visualizer.updateShotIntent(shotDirection);
        }
    }

    @Override
    public synchronized void mouseReleased(MouseEvent e) {
        if (this.enable && this.startingPoint != null) {
            this.visualizer.shoot();
            this.startingPoint = null;
        }
    }

    // ------------------------------------------------------------------ //
    //  ShotInput                                                           //
    // ------------------------------------------------------------------ //

    @Override
    public void setEnable(boolean enable) {
        this.enable = enable;
        if (!enable) {
            // Clear any in-progress drag so stale state does not bleed into the next turn.
            this.startingPoint = null;
        }
    }
}