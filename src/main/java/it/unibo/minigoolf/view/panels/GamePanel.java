package it.unibo.minigoolf.view.panels;

import it.unibo.minigoolf.controller.MainController;
import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.model.logic.ShotState;
import it.unibo.minigoolf.view.input.ShotViewPanel;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.Serial;

/**
 * The game scene panel.
 * Hosts the map layer and the shot-indicator overlay.
 * No longer owns shot polling or ball-stopped logic —
 * those are handled by {@link it.unibo.minigoolf.controller.shot.ShotControllerImpl}.
 *
 * @author dani
 */
public final class GamePanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int START_WIDTH = 960;
    private static final int START_HEIGHT = 540;
    private static final double ASPECT_W = 16.0;
    private static final double ASPECT_H = 9.0;

    private final ShotViewPanel shotViewPanel;
    private final MapPanel mapPanel;

    /**
     * @param controller        the main controller
     * @param navController     the navigation controller
     * @param gameState         the shared game state (used only for the turn label)
     * @param gameMapController the controller managing the shared game map
     * @param shotState         the shot state model shared with ShotControllerImpl
     */
    public GamePanel(final MainController controller,
            final NavigationController navController,
            final GameState gameState,
            final GameMapController gameMapController,
            final ShotState shotState) {
        this.setPreferredSize(new Dimension(START_WIDTH, START_HEIGHT));
        this.setLayout(new BorderLayout());

        // Turn label bar.
        final JPanel uiPanel = new JPanel();
        uiPanel.setBackground(Color.DARK_GRAY);
        final JLabel turnoLabel = new JLabel(gameState.getCurrentPlayer().toString());
        turnoLabel.setForeground(Color.WHITE);
        uiPanel.add(turnoLabel);
        this.add(uiPanel, BorderLayout.NORTH);

        this.mapPanel = new MapPanel(gameMapController);
        this.shotViewPanel = new ShotViewPanel(shotState);

        final JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(Color.WHITE);

        final JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(START_WIDTH, START_HEIGHT));
        mapPanel.setBounds(0, 0, START_WIDTH, START_HEIGHT);
        layeredPane.add(mapPanel, JLayeredPane.DEFAULT_LAYER);
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

        // ESC → pause.
        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke("ESCAPE"), "pauseAction");
        this.getActionMap().put("pauseAction", new AbstractAction() {
            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(final ActionEvent e) {
                navController.pauseGame();
            }
        });
    }

    /**
     * Returns the shot view panel so the controller can wire it to
     * {@link it.unibo.minigoolf.controller.shot.ShotControllerImpl}.
     *
     * @return the shot view panel
     */
    public ShotViewPanel getShotViewPanel() {
        return shotViewPanel;
    }
}
