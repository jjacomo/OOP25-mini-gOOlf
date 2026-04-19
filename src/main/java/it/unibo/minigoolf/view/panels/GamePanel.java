package it.unibo.minigoolf.view.panels;

import it.unibo.minigoolf.controller.MainController;
import it.unibo.minigoolf.controller.gamemapcontroller.GameMapControllerImpl;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.model.map.TestGameMapFactory;
import it.unibo.minigoolf.view.input.ShotViewPanel;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
// import java.awt.Graphics;
// import java.awt.Graphics2D;
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
public final class GamePanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    /** Starting resolution width (placeholder, should be a fixed size). */
    private static final int START_WIDTH = 960;
    private static final int START_HEIGHT = 540;

    /**
     * Logical resolution shared with ShotViewPanel.
     * ALL game-object coordinates must live in this space so the two
     * layers (field drawing + shot overlay) stay perfectly aligned.
     */
    // private static final int LOGICAL_WIDTH = 1920;
    // private static final int LOGICAL_HEIGHT = 1080;

    // Placeholder ball position in logical (1920×1080) coordinates — will come from the physics model later.
    private static final Point BALL_START = new Point(220, 220);

    // Aspect ratio constants.
    private static final double ASPECT_W = 16.0;
    private static final double ASPECT_H = 9.0;

    // The transparent overlay for shot input and power indicator.
    private final ShotViewPanel shotViewPanel;

    // The panel where the map, ball and obstacles are drawn. 
    private final MapPanel mapPanel = new MapPanel(new GameMapControllerImpl(new TestGameMapFactory().buildGameMap())); //va bene crearla qui?

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

        // A "wrapperpanel" that contains the field area, so it can be forced to the 16:9 ratio

        final JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(Color.WHITE);

        // Use a JLayeredPane to overlay ShotViewPanel on top of the field area.
        final JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(START_WIDTH, START_HEIGHT));
        // fieldArea.setBounds(0, 0, START_WIDTH, START_HEIGHT);
        layeredPane.add(mapPanel, JLayeredPane.DEFAULT_LAYER);

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

                mapPanel.setPreferredSize(fieldSize);
                mapPanel.setBounds(0, 0, fieldSize.width, fieldSize.height);
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