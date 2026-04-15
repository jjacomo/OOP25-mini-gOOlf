package it.unibo.minigoolf.view.input;

import it.unibo.minigoolf.util.Vec2D;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Mouse listener that translates drag gestures into shot vectors.
 *
 * @author fede
 */
public final class ShotListener extends MouseAdapter implements ShotInput {

    private final ShotVisualizer visualizer;
 
    /**
     * Reference to the panel used to convert physical logical coordinates.
     * Needed because MouseEvent coordinates are always in physical pixels.
     */
    private final ShotViewPanel panel;
 
    /** Where the drag started in logical coordinates (null when no drag is in progress). */
    private Point startingPoint;
 
    /** Whether this listener is accepting input. */
    private boolean enable;

    /**
     * Maximum distance (in logical pixels) from the ball centre
     * within which a click is accepted as the start of a drag.
     */
    private static final double CLICK_RADIUS = 40.0;

    /**
     * @param visualizer the panel that draws the indicator and handles shots
     */
    public ShotListener(final ShotViewPanel visualizer) {
        this.visualizer = visualizer;
        this.panel = visualizer;
    }

    /** {@inheritDoc} */
    @Override
    public void mousePressed(final MouseEvent e) {
        if (this.enable) {
            final Point logical = panel.toLogical(e.getPoint());
            // Only start a drag if the click is close enough to the ball.
            if (panel.isNearBall(logical, CLICK_RADIUS)) {
                this.startingPoint = logical;
            }
        }
    }
 
    /** {@inheritDoc} */
    @Override
    public void mouseDragged(final MouseEvent e) {
        if (this.enable && this.startingPoint != null) {
            // Convert current physical position to logical space.
            final Point logicalCurrent = panel.toLogical(e.getPoint());
            // Build the raw drag vector (logical start → logical current).
            final Vec2D raw = new Vec2D(this.startingPoint, logicalCurrent);
            // Negate it: dragging the club backward means shooting the ball forward.
            final Vec2D shotDirection = raw.getOppositeVector();
            this.visualizer.updateShotIntent(shotDirection);
        }
    }
 
    /** {@inheritDoc} */
    @Override
    public void mouseReleased(final MouseEvent e) {
        if (this.enable && this.startingPoint != null) {
            this.visualizer.shoot();
            this.startingPoint = null;
        }
    }
 
    /** {@inheritDoc} */
    @Override
    public void setEnable(final boolean enable) {
        this.enable = enable;
        if (!enable) {
            // Clear any in-progress drag so stale state does not bleed into the next turn.
            this.startingPoint = null;
        }
    }
}