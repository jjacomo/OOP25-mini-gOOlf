package it.unibo.minigOOlf.view.input;

import it.unibo.minigOOlf.model.logic.GameState;
import it.unibo.minigOOlf.util.Vec2D;
 
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
 
/**
 * The view of the shot
 * 
 * @author fede
 */
public class ShotViewPanel extends JPanel implements ShotVisualizer {
 
    // minimum squared power for a shot to be accepted
    private static final double MIN_SQUARE_POWER = 100.0;
 
    // squared power below which the line is drawn green
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
 
    //dashed stroke for the power indicator line.
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
 

    //the ShotListener that translates mouse events into Vec2D values
    private final ShotListener shotListener;
 
    //the game state that receives confirmed shots
    private final GameState gameState;
    private Vec2D currentDirection = null;
    private Point ballScreenPos = null;
 
    /**
     * @param gameState the shared game-state instance
     */
    public ShotViewPanel(GameState gameState) {
        this.gameState = gameState;
        this.shotListener = new ShotListener(this);
 
        //register the listener for both click and drag events
        this.addMouseListener(shotListener);
        this.addMouseMotionListener(shotListener);
 
        //make this panel transparent so the field is visible underneath
        this.setOpaque(false);
    }

    /**
     * Activates shot input for the current turn
     * Call this after the ball has stopped and it's the player's turn to shoot
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
     * Disables shot input (example: while the ball is moving)
     * Clears any in-progress drag indicator
     */
    public void disableShot() {
        this.shotListener.setEnable(false);
        synchronized (this) {
            this.currentDirection = null;
        }
    }
 
    /**
     * receives the live drag vector and keeps it so paintComponent can draw it
     * the game loop calls repaint every tick, no need to recall here
     *
     * @param direction current shot direction/power vector (already negated by ShotListener)
     */
    @Override
    public synchronized void updateShotIntent(Vec2D direction) {
        this.currentDirection = direction;
    }
 
    /**
     * called on mouse release
     * validates the shot and forwards it to GameState if ok
     * disables further input until the controller calls enableShot() again
     */
    @Override
    public synchronized void shoot() {
        if (isValidShot()) {
            //forward the shot to GameState
            gameState.setPendingShot(this.currentDirection);
            //block input so the user can't queue another shot
            shotListener.setEnable(false);
        }
        this.currentDirection = null;
    }
 
    /**
     * Draws the dashed coloured line from the ball position in the shot direction
     * Does nothing if there is no drag 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
 
        final Vec2D dir;
        final Point origin;
 
        if (currentDirection == null || ballScreenPos == null || !isValidShot()) {
            return; // nothing to draw
        }
        dir = currentDirection;
        origin = ballScreenPos;
 
        //clamp display length so the line doesn't go off the screen
        Vec2D displayDir = dir.clampedTo(MAX_LINE_PIXELS);
        Point tip = displayDir.translate(origin);
 
        //line colour based on power
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
 
            //draw a small solid circle at the origin to mark the ball centre.
            g2d.setStroke(new BasicStroke(1f));
            g2d.fillOval(origin.x - 4, origin.y - 4, 8, 8);
        } finally {
            g2d.dispose();
        }
    }
 
    /** 
     * @return true if the current drag vector is above the minimum power threshold 
     */
    private boolean isValidShot() {
        return currentDirection != null
            && currentDirection.getSquareModule() >= MIN_SQUARE_POWER;
    }
}
