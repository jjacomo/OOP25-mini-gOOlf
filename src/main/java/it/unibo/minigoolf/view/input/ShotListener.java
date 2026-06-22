package it.unibo.minigoolf.view.input;

import it.unibo.minigoolf.util.Vector2D;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Mouse listener that translates drag gestures into shot vectors.
 * Depends on the narrow {@link ShotVisualizer} and {@link ShotCoordinateConverter}
 * interfaces instead of the full {@link ShotViewPanel}, avoiding EI2 warnings.
 *
 * @author fedesparvo1-a11y
 */
public final class ShotListener extends MouseAdapter {

    /**
     * Maximum distance (in logical pixels) from the ball centre
     * within which a click is accepted as the start of a drag.
     */
    private static final double CLICK_RADIUS = 40.0;

    private final ShotVisualizer visualizer;
    private final ShotCoordinateConverter converter;

    // Where the drag started in logical coordinates (null when no drag is in progress).
    private transient Point startingPoint;

    // True if this listener is accepting input.
    private boolean enable;

    /**
     * Creates a new ShotListener.
     *
     * @param visualizer the panel that draws the indicator and handles shots
     * @param converter  the panel that converts coordinates and checks ball proximity
     */
    public ShotListener(final ShotVisualizer visualizer, final ShotCoordinateConverter converter) {
        this.visualizer = visualizer;
        this.converter = converter;
    }

    /** {@inheritDoc} */
    @Override
    public void mousePressed(final MouseEvent e) {
        if (this.enable) {
            final Point logical = converter.toLogical(e.getPoint());
            if (converter.isNearBall(logical, CLICK_RADIUS)) {
                this.startingPoint = logical;
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void mouseDragged(final MouseEvent e) {
        if (this.enable && this.startingPoint != null) {
            final Point logicalCurrent = converter.toLogical(e.getPoint());
            final Vector2D raw = new Vector2D(this.startingPoint, logicalCurrent);
            final Vector2D shotDirection = raw.getOppositeVector();
            visualizer.updateShotIntent(shotDirection);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void mouseReleased(final MouseEvent e) {
        if (this.enable && this.startingPoint != null) {
            visualizer.shoot();
            this.startingPoint = null;
        }
    }

    /**
     * Enables or disables this listener.
     * When disabled, clears any drag in progress.
     *
     * @param enable true to accept input, false to ignore it
     */
    public void setEnable(final boolean enable) {
        this.enable = enable;
        if (!enable) {
            this.startingPoint = null;
        }
    }
}
