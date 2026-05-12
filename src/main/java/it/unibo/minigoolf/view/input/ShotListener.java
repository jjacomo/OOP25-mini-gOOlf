package it.unibo.minigoolf.view.input;

import it.unibo.minigoolf.util.Vector2D;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Mouse listener that translates drag gestures into shot vectors.
 *
 * @author fede
 */
public final class ShotListener extends MouseAdapter implements ShotInput {

    /**
     * Maximum distance (in logical pixels) from the ball centre
     * within which a click is accepted as the start of a drag.
     */
    private static final double CLICK_RADIUS = 40.0;

    /**
     * The panel that draws the shot indicator, handles shots, and converts
     * physical mouse coordinates to logical ones.
     * A single reference is kept since {@link ShotViewPanel} implements
     * {@link ShotVisualizer} directly.
     */
    private final transient ShotViewPanel panel;

    /** Where the drag started in logical coordinates (null when no drag is in progress). */
    private transient Point startingPoint;

    /** Whether this listener is accepting input. */
    private boolean enable;

    /**
     * Creates a new ShotListener linked to the given panel.
     *
     * @param panel the panel that draws the indicator and handles shots
     */
    public ShotListener(final ShotViewPanel panel) {
        this.panel = panel;
    }

    /** {@inheritDoc} */
    @Override
    public void mousePressed(final MouseEvent e) {
        if (this.enable) {
            final Point logical = panel.toLogical(e.getPoint());
            if (panel.isNearBall(logical, CLICK_RADIUS)) {
                this.startingPoint = logical;
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void mouseDragged(final MouseEvent e) {
        if (this.enable && this.startingPoint != null) {
            final Point logicalCurrent = panel.toLogical(e.getPoint());
            final Vector2D raw = new Vector2D(this.startingPoint, logicalCurrent);
            final Vector2D shotDirection = raw.getOppositeVector();
            panel.updateShotIntent(shotDirection);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void mouseReleased(final MouseEvent e) {
        if (this.enable && this.startingPoint != null) {
            panel.shoot();
            this.startingPoint = null;
        }
    }

    /** {@inheritDoc} */
    @Override
    public void setEnable(final boolean enable) {
        this.enable = enable;
        if (!enable) {
            this.startingPoint = null;
        }
    }
}
