package it.unibo.minigoolf.view.input;

import it.unibo.minigoolf.controller.shot.ShotView;
import it.unibo.minigoolf.model.logic.ShotState;
import it.unibo.minigoolf.util.Vector2D;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.io.Serial;
import java.util.Optional;

/**
 * Pure view panel for the shot indicator.
 * Draws the dashed line and arrowhead while the user is dragging.
 * Implements {@link ShotView} so the controller can enable input
 * without depending on this concrete class.
 *
 * @author fedesparvo1-a11y
 */
public final class ShotViewPanel extends JPanel implements ShotVisualizer, ShotView, ShotCoordinateConverter {

    /** Required by {@link java.io.Serializable} inherited from {@link javax.swing.JPanel}. */
    @Serial
    private static final long serialVersionUID = 1L;

    private static final int LOGICAL_WIDTH = 1920;
    private static final int LOGICAL_HEIGHT = 1080;

    private static final double LOW_THRESHOLD = 1_000.0;
    private static final double MED_THRESHOLD = 5_000.0;

    private static final float LINE_WIDTH = 5.0f;
    private static final float[] DASH_PATTERN = {10f, 6f};
    private static final Stroke DASHED_STROKE = new BasicStroke(
        LINE_WIDTH,
        BasicStroke.CAP_ROUND,
        BasicStroke.JOIN_ROUND,
        10f,
        DASH_PATTERN,
        0f
    );

    private static final double ARROW_SIZE = 18.0;

    private final transient ShotState shotState;
    private final transient ShotListener shotListener;

    /**
     * Creates a new ShotViewPanel linked to the given shot state.
     *
     * @param shotState the model that holds shot intent and confirmation state
     */
    public ShotViewPanel(final ShotState shotState) {
        this.shotState = shotState;
        this.shotListener = new ShotListener(this, this);
        this.addMouseListener(shotListener);
        this.addMouseMotionListener(shotListener);
        this.setOpaque(false);
    }

    /**
     * Returns true if the given logical point is within {@code radius} pixels
     * of the ball centre.
     *
     * @param logical the point to test, in logical coordinates
     * @param radius  maximum allowed distance in logical pixels
     * @return true if the point is close enough to the ball
     */
    @Override
    public boolean isNearBall(final Point logical, final double radius) {
        final Optional<Vector2D> ballPos = shotState.getBallPosition();
        if (ballPos.isEmpty()) {
            return false;
        }
        final double dx = logical.x - ballPos.get().getX();
        final double dy = logical.y - ballPos.get().getY();
        return dx * dx + dy * dy <= radius * radius;
    }

    /**
     * Converts a physical mouse point to logical coordinates.
     *
     * @param physical the raw point from a MouseEvent
     * @return the point in logical space
     */
    @Override
    public Point toLogical(final Point physical) {
        final double factorX = (double) LOGICAL_WIDTH / getWidth();
        final double factorY = (double) LOGICAL_HEIGHT / getHeight();
        return new Point((int) (physical.x * factorX), (int) (physical.y * factorY));
    }

    /**
     * {@inheritDoc}
     * Activates shot input at the given ball position.
     */
    @Override
    public void enableShot(final Point ballPosition) {
        shotState.reset(new Vector2D(ballPosition.x, ballPosition.y));
        this.shotListener.setEnable(true);
    }

    /**
     * Disables shot input.
     */
    public void disableShot() {
        this.shotListener.setEnable(false);
    }

    /** {@inheritDoc} */
    @Override
    public void updateShotIntent(final Vector2D direction) {
        shotState.updateIntent(direction);
        repaint();
    }

    /** {@inheritDoc} */
    @Override
    public void shoot() {
        shotState.confirmShot();
    }

    /** {@inheritDoc} */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        final Optional<Vector2D> intentOpt = shotState.getIntent();
        final Optional<Vector2D> ballPosOpt = shotState.getBallPosition();

        if (intentOpt.isEmpty() || ballPosOpt.isEmpty() || !shotState.isValid()) {
            return;
        }

        final Vector2D dir = intentOpt.get();
        final Vector2D ballPos = ballPosOpt.get();
        final Point origin = new Point((int) ballPos.getX(), (int) ballPos.getY());
        final Vector2D displayDir = dir.clampedTo(ShotState.MAX_POWER);
        final Point tip = displayDir.translate(origin);
        final Color lineColor = colorForPower(dir.getNormSquared());

        final Graphics2D g2d = (Graphics2D) g.create();
        try {
            g2d.scale((double) getWidth() / LOGICAL_WIDTH, (double) getHeight() / LOGICAL_HEIGHT);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(lineColor);
            g2d.setStroke(DASHED_STROKE);
            g2d.draw(new Line2D.Float(origin.x, origin.y, tip.x, tip.y));
            drawArrowhead(g2d, displayDir, tip);
            g2d.setStroke(new BasicStroke(1f));
            g2d.fillOval(origin.x - 4, origin.y - 4, 8, 8);
        } finally {
            g2d.dispose();
        }
    }

    /**
     * Returns the indicator colour based on the squared power of the shot.
     *
     * @param squaredPower the squared norm of the current drag vector
     * @return green for low power, yellow for medium, red for high
     */
    private static Color colorForPower(final double squaredPower) {
        if (squaredPower < LOW_THRESHOLD) {
            return Color.GREEN;
        }
        if (squaredPower < MED_THRESHOLD) {
            return Color.YELLOW;
        }
        return Color.RED;
    }

    /**
     * Draws an arrowhead at the top of the indicator line.
     *
     * @param g2d        the graphics context (already scaled to logical space)
     * @param displayDir the clamped display vector
     * @param tip        the tip point in logical coordinates
     */
    private static void drawArrowhead(final Graphics2D g2d,
            final Vector2D displayDir, final Point tip) {
        final double angle = Math.atan2(displayDir.getY(), displayDir.getX());
        final double wingAngle = Math.toRadians(140);

        final double x1 = tip.x + ARROW_SIZE * Math.cos(angle + wingAngle);
        final double y1 = tip.y + ARROW_SIZE * Math.sin(angle + wingAngle);
        final double x2 = tip.x + ARROW_SIZE * Math.cos(angle - wingAngle);
        final double y2 = tip.y + ARROW_SIZE * Math.sin(angle - wingAngle);

        final Path2D arrow = new Path2D.Double();
        arrow.moveTo(x1, y1);
        arrow.lineTo(tip.x, tip.y);
        arrow.lineTo(x2, y2);

        g2d.setStroke(new BasicStroke(LINE_WIDTH, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.draw(arrow);
    }
}
