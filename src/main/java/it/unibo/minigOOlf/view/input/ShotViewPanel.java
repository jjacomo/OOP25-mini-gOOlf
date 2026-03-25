package it.unibo.minigOOlf.view.input;

import it.unibo.minigOOlf.model.logic.GameState;
import it.unibo.minigOOlf.util.Vec2D;
 
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
 
/**
 * Transparent overlay panel drawn on top of the field area.
 *
 * Responsibilities:
 *  - Registers a {@link ShotListener} for mouse drag input.
 *  - Paints a dashed coloured line showing shot direction and power:
 *      green  → low power  (squaredLength < LOW_THRESHOLD)
 *      yellow → medium     (LOW_THRESHOLD <= squaredLength < MED_THRESHOLD)
 *      red    → high power (squaredLength >= MED_THRESHOLD)
 *  - On mouse release calls {@link GameState#setPendingShot(Vec2D)} so the
 *    game loop can process the shot on the next tick.
 *  - Listens to GameState to disable input while the ball is moving.
 *
 * The panel is transparent (non-opaque) so the field below remains visible.
 *
 * @author fede
 */
public class ShotViewPanel extends JPanel implements ShotVisualizer {
 
    // ------------------------------------------------------------------ //
    //  Tuning constants                                                    //
    // ------------------------------------------------------------------ //
 
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
 
    // Dashed stroke for the power indicator line.
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
 
    // ------------------------------------------------------------------ //
    //  State                                                               //
    // ------------------------------------------------------------------ //
 
    /** The ShotListener that translates mouse events into Vec2D values. */
    private final ShotListener shotListener;
 
    /** The game state; receives confirmed shots. */
    private final GameState gameState;
 
    /**
     * Current shot direction/power (updated during drag, null when idle).
     * Guarded by this monitor for thread safety with the paint thread.
     */
    private Vec2D currentDirection = null;
 
    /**
     * Screen position of the ball (where the dashed line originates).
     * Set by {@link #enableShot(Point)}.
     */
    private Point ballScreenPos = null;
 
    // ------------------------------------------------------------------ //
    //  Construction                                                        //
    // ------------------------------------------------------------------ //
 
    /**
     * @param gameState the shared game-state instance
     */
    public ShotViewPanel(GameState gameState) {
        this.gameState = gameState;
        this.shotListener = new ShotListener(this);
 
        // Register the listener for both click and drag events.
        this.addMouseListener(shotListener);
        this.addMouseMotionListener(shotListener);
 
        // Make this panel transparent so the field is visible underneath.
        this.setOpaque(false);
    }
 
    // ------------------------------------------------------------------ //
    //  Public API used by GamePanel / controller                           //
    // ------------------------------------------------------------------ //
 
    /**
     * Activates shot input for the current turn.
     * Call this after the ball has stopped and it's the player's turn to shoot.
     *
     * @param ballPosition current screen coordinates of the ball
     */
    public void enableShot(Point ballPosition) {
        synchronized (this) {
            this.ballScreenPos = ballPosition;
            this.currentDirection = null;
        }
        this.shotListener.setEnable(true);
    }
 
    /**
     * Disables shot input (e.g. while the ball is moving).
     * Clears any in-progress drag indicator.
     */
    public void disableShot() {
        this.shotListener.setEnable(false);
        synchronized (this) {
            this.currentDirection = null;
        }
    }
 
    // ------------------------------------------------------------------ //
    //  ShotVisualizer — called from ShotListener on the EDT               //
    // ------------------------------------------------------------------ //
 
    /**
     * Receives the live drag vector and stores it so paintComponent can draw it.
     * NOT calling repaint here: the game loop calls repaint every tick.
     *
     * @param direction current shot direction/power vector (already negated by ShotListener)
     */
    @Override
    public synchronized void updateShotIntent(Vec2D direction) {
        this.currentDirection = direction;
    }
 
    /**
     * Called on mouse release.
     * Validates the shot and forwards it to GameState if valid.
     * Disables further input until the controller calls enableShot() again.
     */
    @Override
    public synchronized void shoot() {
        if (isValidShot()) {
            // Forward the shot to GameState — the game loop will pick it up next tick.
            gameState.setPendingShot(this.currentDirection);
            // Block input immediately so the user can't queue another shot.
            shotListener.setEnable(false);
        }
        // Clear the indicator regardless (invalid shots are silently discarded).
        this.currentDirection = null;
    }
 
    // ------------------------------------------------------------------ //
    //  Painting                                                            //
    // ------------------------------------------------------------------ //
 
    /**
     * Draws the dashed coloured line from the ball position in the shot direction.
     * Does nothing if no drag is in progress.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // honours transparency (no-op for non-opaque)
 
        final Vec2D dir;
        final Point origin;
 
        // Snapshot under lock so we don't race with ShotListener callbacks.
        synchronized (this) {
            if (currentDirection == null || ballScreenPos == null || !isValidShot()) {
                return; // nothing to draw
            }
            dir = currentDirection;
            origin = ballScreenPos;
        }
 
        // Clamp display length so the line doesn't fly off screen.
        Vec2D displayDir = dir.clampedTo(MAX_LINE_PIXELS);
        Point tip = displayDir.translate(origin);
 
        // Choose line colour based on raw (unclamped) power.
        double squaredPower = dir.getSquareModule();
        Color lineColor;
        if (squaredPower < LOW_THRESHOLD) {
            lineColor = Color.GREEN;
        } else if (squaredPower < MED_THRESHOLD) {
            lineColor = Color.YELLOW;
        } else {
            lineColor = Color.RED;
        }
 
        Graphics2D g2d = (Graphics2D) g.create();
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
 
    // ------------------------------------------------------------------ //
    //  Helpers                                                             //
    // ------------------------------------------------------------------ //
 
    /** @return true if the current drag vector is above the minimum power threshold */
    private boolean isValidShot() {
        return currentDirection != null
            && currentDirection.getSquareModule() >= MIN_SQUARE_POWER;
    }
}
