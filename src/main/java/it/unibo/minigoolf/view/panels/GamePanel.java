package it.unibo.minigoolf.view.panels;

import it.unibo.minigoolf.controller.game.GameController;
import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.view.input.ShotViewPanel;

import javax.swing.AbstractAction;
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
import java.awt.Graphics;
import java.io.Serial;

/**
 * The game scene panel.
 * Receives a pre-built {@link ShotViewPanel} from {@link it.unibo.minigoolf.view.MainWindow}
 * so it does not need to expose any internal reference via a getter.
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

    private final MapPanel mapPanel;
    private final JLabel turnoLabel;
    private final JLabel shotsLabel;
    private final GameController gameController;

    /**
     * @param navController  the navigation controller
     * @param gameController the active match controller
     * @param shotViewPanel  the pre-built shot view panel, created and wired by MainWindow
     */
    public GamePanel(final NavigationController navController,
            final GameController gameController,
            final ShotViewPanel shotViewPanel) {
        this.setPreferredSize(new Dimension(START_WIDTH, START_HEIGHT));
        this.setLayout(new BorderLayout());

        this.gameController = gameController;

        // The panel for the current player and the shots counter
        final JPanel uiPanel = new JPanel();
        uiPanel.setBackground(Color.DARK_GRAY);
        
        this.turnoLabel = new JLabel("Player: " + gameController.getCurrentPlayerName());
        this.turnoLabel.setForeground(Color.WHITE);
        
        this.shotsLabel = new JLabel(" | Shots: " + gameController.getCurrentPlayerShots());
        this.shotsLabel.setForeground(Color.WHITE);
        
        uiPanel.add(turnoLabel);
        uiPanel.add(shotsLabel);
        
        this.add(uiPanel, BorderLayout.NORTH);

        this.mapPanel = new MapPanel(gameController.getGameMapController());
        // shotViewPanel is used directly from the parameter — no field needed.

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
        this.getInputMap(WHEN_IN_FOCUSED_WINDOW)
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
     * Called automatically by the MainController repaint loop to keep the HUD updated in real time
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        
        // Se il controller esiste, aggiorniamo i testi delle etichette
        if (this.gameController != null) {
            this.turnoLabel.setText("Player: " + gameController.getCurrentPlayerName());
            this.shotsLabel.setText(" | Shots: " + gameController.getCurrentPlayerShots());
        }
    }
}
