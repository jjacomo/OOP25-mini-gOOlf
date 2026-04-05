package it.unibo.minigoolf.view.input;

import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.util.Vec2D;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.io.Serial;

/**
 * The view of the shot.
 *
 * @author fede
 */
public final class ShotViewPanel extends JPanel implements ShotVisualizer {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Minimum squared power for a shot to be accepted. */
    private static final double MIN_SQUARE_POWER = 100.0;

    /** Squared power below which the line is drawn green. */
    private static final double LOW_THRESHOLD = 1_000.0;

    /**
     * Squared power above LOW_THRESHOLD and below which the line is yellow.
     * Above this it turns red.
     */
    private static final double MED_THRESHOLD = 5_000.0;

    /**
     * Maximum display length of the dashed line in pixels.
     * Beyond this the line is clamped so it doesn't leave the ball area.
     */
    private static final double MAX_LINE_PIXELS = 150.0;

    /** Dashed stroke for the power indicator line. */
    private static final float LINE_WIDTH = 2.5f;
    private static final float[] DASH_PATTERN = {10f, 6f};
    private static final Stroke DASHED_STROKE = new BasicStroke(
        LINE_WIDTH,
        BasicStroke.CAP_ROUND,
        BasicStroke.JOIN_ROUND,
        10f,
        DASH_PATTERN,
        0f
    );

    /** The ShotListener that translates mouse events into Vec2D values. */
    private final transient ShotListener shotListener;

    /**
     * The game state that receives confirmed shots.
     * Stored by reference intentionally: this panel is a view component that
     * shares state with the controller; defensive copying is not appropriate here.
     */
    @SuppressWarnings("EI_EXPOSE_REP2")
    private final transient GameState gameState;

    private transient Vec2D currentDirection;
    private transient Point ballScreenPos;

    /**
     * Creates a new ShotViewPanel linked to the given game state.
     *
     * @param gameState the shared game-state instance
     */
    @SuppressWarnings("EI_EXPOSE_REP2")
    public ShotViewPanel(final GameState gameState) {
        this.gameState = gameState;
        this.shotListener = new ShotListener(this);

        // Register the listener for both click and drag events.
        this.addMouseListener(shotListener);
        this.addMouseMotionListener(shotListener);

        // Make this panel transparent so the field is visible underneath.
        this.setOpaque(false);
    }

    /**
     * Activates shot input for the current turn.
     * Call this after the ball has stopped and it's the player's turn to shoot.
     *
     * @param ballPosition current screen coordinates of the ball
     */
    public void enableShot(final Point ballPosition) {
        synchronized (this) {
            this.ballScreenPos = new Point(ballPosition);
            this.currentDirection = null;
        }
        this.shotListener.setEnable(true);
    }

    /**
     * Disables shot input (example: while the ball is moving).
     * Clears any in-progress drag indicator.
     */
    public void disableShot() {
        this.shotListener.setEnable(false);
        synchronized (this) {
            this.currentDirection = null;
        }
    }

    /**
     * Receives the live drag vector and keeps it so paintComponent can draw it.
     * The game loop calls repaint every tick, no need to recall here.
     *
     * @param direction current shot direction/power vector (already negated by ShotListener)
     */
    @Override
    public synchronized void updateShotIntent(final Vec2D direction) {
        this.currentDirection = direction;
    }

    /**
     * Called on mouse release.
     * Validates the shot and forwards it to GameState if ok.
     * Disables further input until the controller calls enableShot() again.
     */
    @Override
    public synchronized void shoot() {
        if (isValidShot()) {
            // Forward the shot to GameState.
            gameState.setPendingShot(this.currentDirection);
            // Block input so the user can't queue another shot.
            shotListener.setEnable(false);
        }
        this.currentDirection = null;
    }

    /**
     * Draws the dashed coloured line from the ball position in the shot direction.
     * Does nothing if there is no drag.
     *
     * @param g the Graphics context
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);

        final Vec2D dir;
        final Point origin;

        synchronized (this) {
            if (currentDirection == null || ballScreenPos == null || !isValidShot()) {
                return;
            }
            dir = currentDirection;
            origin = new Point(ballScreenPos);
        }

        // Clamp display length so the line doesn't go off the screen.
        final Vec2D displayDir = dir.clampedTo(MAX_LINE_PIXELS);
        final Point tip = displayDir.translate(origin);

        // Line colour based on power.
        final double squaredPower = dir.getSquareModule();
        final Color lineColor;
        if (squaredPower < LOW_THRESHOLD) {
            lineColor = Color.GREEN;
        } else if (squaredPower < MED_THRESHOLD) {
            lineColor = Color.YELLOW;
        } else {
            lineColor = Color.RED;
        }

        final Graphics2D g2d = (Graphics2D) g.create();
        try {
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(DASHED_STROKE);
            g2d.setColor(lineColor);
            g2d.draw(new Line2D.Float(origin.x, origin.y, tip.x, tip.y));

            // Draw a small solid circle at the origin to mark the ball centre.
            g2d.setStroke(new BasicStroke(1f));
            g2d.fillOval(origin.x - 4, origin.y - 4, 8, 8);
        } finally {
            g2d.dispose();
        }
    }

    /**
     * Returns true if the current drag vector is above the minimum power threshold.
     *
     * @return true if the shot is valid
     */
    private boolean isValidShot() {
        return currentDirection != null
            && currentDirection.getSquareModule() >= MIN_SQUARE_POWER;
    }
}
