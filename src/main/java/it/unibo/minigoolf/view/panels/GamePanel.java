package it.unibo.minigoolf.view.panels;

import it.unibo.minigoolf.controller.MainController;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.view.input.ShotViewPanel;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.Serial;

/**
 * One of the possible scenes, this one is where the game is played.
 *
 * @author dani
 */
@SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
public class GamePanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Starting resolution width (placeholder, should be a fixed size). */
    private static final int START_WIDTH = 960;
    private static final int START_HEIGHT = 540;

    // Placeholder ball position — will come from the physics model later.
    private static final Point BALL_START = new Point(110, 110);

    // Magic numbers for the placeholder field drawing.
    private static final int BALL_X = 100;
    private static final int BALL_Y = 100;
    private static final int BALL_SIZE = 20;
    private static final int OBSTACLE_X = 200;
    private static final int OBSTACLE_Y = 150;
    private static final int OBSTACLE_W = 100;
    private static final int OBSTACLE_H = 200;
    private static final int FIELD_GREEN = 153;

    // Aspect ratio constants.
    private static final double ASPECT_W = 16.0;
    private static final double ASPECT_H = 9.0;

    // The transparent overlay for shot input and power indicator.
    private final ShotViewPanel shotViewPanel;

    /**
     * @param controller the main controller (reserved for future use)
     * @param gameState  the shared game-state instance
     */
    public GamePanel(final MainController controller, final GameState gameState) {
        this.setPreferredSize(new Dimension(START_WIDTH, START_HEIGHT));
        this.setLayout(new BorderLayout());

        // TODO: To work later with @fedesparvo1-a11y to determine how to personalize this
        final JPanel uiPanel = new JPanel();
        uiPanel.setBackground(Color.DARK_GRAY);
        final JLabel turnoLabel = new JLabel(gameState.getCurrentPlayer().toString());
        turnoLabel.setForeground(Color.WHITE);
        uiPanel.add(turnoLabel);
        this.add(uiPanel, BorderLayout.NORTH);

        //* Here's the real area where the game runs: ball, map and obstacles need to be drawn here @jjacomo @MattiaDambrosio5 */

        final JPanel fieldArea = new JPanel() {
            @Override
            protected void paintComponent(final Graphics g) {
                super.paintComponent(g);
                final Graphics2D g2d = (Graphics2D) g;
                // Scale from logical (1920x1080) to actual panel size,
                // so all coordinates match those used by ShotViewPanel.
                g2d.scale((double) getWidth() / START_WIDTH, (double) getHeight() / START_HEIGHT);
                // A test ball to see how it appears with the Graphics2D library
                g2d.setColor(Color.WHITE);
                g2d.fillOval(BALL_X, BALL_Y, BALL_SIZE, BALL_SIZE);
                // A simple obstacle
                g2d.setColor(Color.GRAY);
                g2d.fillRect(OBSTACLE_X, OBSTACLE_Y, OBSTACLE_W, OBSTACLE_H);
            }
        };

        fieldArea.setBackground(new Color(0, FIELD_GREEN, 0));

        // A "wrapperpanel" that contains the field area, so it can be forced to the 16:9 ratio

        final JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(Color.WHITE);

        // Use a JLayeredPane to overlay ShotViewPanel on top of the field area.
        final JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(START_WIDTH, START_HEIGHT));
        fieldArea.setBounds(0, 0, START_WIDTH, START_HEIGHT);
        layeredPane.add(fieldArea, JLayeredPane.DEFAULT_LAYER);

        shotViewPanel = new ShotViewPanel(gameState);
        shotViewPanel.setBounds(0, 0, START_WIDTH, START_HEIGHT);
        layeredPane.add(shotViewPanel, JLayeredPane.PALETTE_LAYER);

        centerWrapper.add(layeredPane);
        this.add(centerWrapper, BorderLayout.CENTER);

        // FIXME: First attempt at forcing the 16:9 ratio, maybe needs to be redone
        centerWrapper.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(final ComponentEvent e) {
                final int w = centerWrapper.getWidth();
                final int h = centerWrapper.getHeight();

                final int expectedHeight = (int) (w * ASPECT_H / ASPECT_W);
                final int expectedWidth = (int) (h * ASPECT_W / ASPECT_H);

                final Dimension fieldSize;
                if (expectedHeight > h) {
                    fieldSize = new Dimension(expectedWidth, h);
                } else {
                    fieldSize = new Dimension(w, expectedHeight);
                }

                fieldArea.setPreferredSize(fieldSize);
                fieldArea.setBounds(0, 0, fieldSize.width, fieldSize.height);
                layeredPane.setPreferredSize(fieldSize);
                layeredPane.setBounds(0, 0, fieldSize.width, fieldSize.height);
                shotViewPanel.setBounds(0, 0, fieldSize.width, fieldSize.height);
                centerWrapper.revalidate();
            }
        });

        // Enable shot input for the first turn.
        shotViewPanel.enableShot(BALL_START);
    }

    /**
     * Called by the controller once the ball has stopped moving.
     * Re-enables shot input for the current player.
     */
    public void onBallStopped() {
        shotViewPanel.enableShot(BALL_START); // TODO: get real ball position from model
    }
}
