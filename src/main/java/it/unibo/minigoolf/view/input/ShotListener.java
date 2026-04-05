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

    /** Where the drag started (null when no drag is in progress). */
    private Point startingPoint;

    /** Whether this listener is accepting input. */
    private boolean enable;

    /**
     * @param visualizer the panel that draws the indicator and handles shots
     */
    public ShotListener(final ShotVisualizer visualizer) {
        this.visualizer = visualizer;
    }

    /** {@inheritDoc} */
    @Override
    public void mousePressed(final MouseEvent e) {
        if (this.enable) {
            this.startingPoint = e.getPoint();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void mouseDragged(final MouseEvent e) {
        if (this.enable && this.startingPoint != null) {
            // Build the raw drag vector (start → current cursor position)
            final Vec2D raw = new Vec2D(this.startingPoint, e.getPoint());
            // Negate it: dragging the club backward means shooting the ball forward
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
