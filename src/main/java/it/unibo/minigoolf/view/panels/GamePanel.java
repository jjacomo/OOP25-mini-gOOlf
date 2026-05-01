package it.unibo.minigoolf.view.panels;

import it.unibo.minigoolf.controller.MainController;
import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.view.input.ShotViewPanel;

import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.Serial;
import java.util.Optional;

/**
 * One of the possible scenes, this one is where the game is played.
 *
 * @author dani
 */
public final class GamePanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int START_WIDTH = 960;
    private static final int START_HEIGHT = 540;

    private static final Point BALL_START = new Point(220, 220);

    private static final double ASPECT_W = 16.0;
    private static final double ASPECT_H = 9.0;

    private final ShotViewPanel shotViewPanel;
    private final MapPanel mapPanel;

    /**
     * @param controller        the main controller
     * @param gameState         the shared game-state instance
     * @param gameMapController the controller managing the shared game map
     */
    public GamePanel(final MainController controller, final GameState gameState,
            final GameMapController gameMapController) {
        this.setPreferredSize(new Dimension(START_WIDTH, START_HEIGHT));
        this.setLayout(new BorderLayout());

        final JPanel uiPanel = new JPanel();
        uiPanel.setBackground(Color.DARK_GRAY);
        final JLabel turnoLabel = new JLabel(gameState.getCurrentPlayer().toString());
        turnoLabel.setForeground(Color.WHITE);
        uiPanel.add(turnoLabel);
        this.add(uiPanel, BorderLayout.NORTH);

        this.mapPanel = new MapPanel(gameMapController);

        final JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(Color.WHITE);

        final JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(START_WIDTH, START_HEIGHT));
        mapPanel.setBounds(0, 0, START_WIDTH, START_HEIGHT);
        layeredPane.add(mapPanel, JLayeredPane.DEFAULT_LAYER);

        shotViewPanel = new ShotViewPanel();
        shotViewPanel.setBounds(0, 0, START_WIDTH, START_HEIGHT);
        layeredPane.add(shotViewPanel, JLayeredPane.PALETTE_LAYER);

        centerWrapper.add(layeredPane);
        this.add(centerWrapper, BorderLayout.CENTER);

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

        shotViewPanel.enableShot(BALL_START);
    }

    /**
     * Called by the controller once the ball has stopped moving.
     * Re-enables shot input for the current player.
     */
    public void onBallStopped() {
        shotViewPanel.enableShot(BALL_START);
    }

    /**
     * Polls the shot view panel for a pending shot and returns it if present.
     * Called by the controller each tick instead of exposing the panel directly.
     *
     * @return an Optional containing the shot vector, or empty if none is pending
     */
    public Optional<Vector2D> consumePendingShot() {
        return shotViewPanel.consumePendingShot();
    }
}
