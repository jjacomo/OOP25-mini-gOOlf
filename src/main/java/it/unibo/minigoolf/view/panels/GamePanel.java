package it.unibo.minigoolf.view.panels;

import it.unibo.minigoolf.controller.game.GameController;
import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.view.input.ShotViewPanel;
import it.unibo.minigoolf.view.elements.UserInterfaceFactory;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.Serial;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 * The game scene panel.
 * Receives a pre-built {@link ShotViewPanel} from {@link it.unibo.minigoolf.view.mainwindow.MainWindow}
 * so it does not need to expose any internal reference via a getter.
 *
 * @author dbakko
 */
public final class GamePanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int START_WIDTH = 960;
    private static final int START_HEIGHT = 540;
    private static final double ASPECT_W = 16.0;
    private static final double ASPECT_H = 9.0;

    /** The panel responsible for rendering the game map and its entities. */
    private final MapPanel mapPanel;

    /** The label displaying the name of the active player. */
    private final JLabel turnoLabel;

    /** The label displaying the number of shots taken by the active player. */
    private final JLabel shotsLabel;

    // Supplies the current player name, avoids storing GameController directly. 
    private final transient Supplier<String> playerNameSupplier;

    // Supplies the current player shots, avoids storing GameController directly.
    private final transient IntSupplier playerShotsSupplier;

    /**
     * Constructs the main game panel, setting up the UI and the layered game views.
     * 
     * @param navController  the navigation controller
     * @param gameController the active match controller
     * @param shotViewPanel  the pre-built shot view panel, created and wired by MainWindow
     */
    public GamePanel(final NavigationController navController,
            final GameController gameController,
            final ShotViewPanel shotViewPanel) {
        this.setPreferredSize(new Dimension(START_WIDTH, START_HEIGHT));
        this.setLayout(new BorderLayout());

        // Extract only the needed behaviors from gameController, avoids EI2.
        this.playerNameSupplier = gameController::getCurrentPlayerName;
        this.playerShotsSupplier = gameController::getCurrentPlayerShots;
        // Panel that displays player names and his number of shots
        final JPanel uiPanel = new JPanel();
        uiPanel.setBackground(Color.DARK_GRAY);
        this.turnoLabel = UserInterfaceFactory.createLabel("Player: " + playerNameSupplier.get());
        this.shotsLabel = UserInterfaceFactory.createLabel(" | Shots: " + playerShotsSupplier.getAsInt());
        uiPanel.add(turnoLabel);
        uiPanel.add(shotsLabel);
        this.add(uiPanel, BorderLayout.NORTH);

        this.mapPanel = new MapPanel();
        gameController.setMapElementsView(this.mapPanel);
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
     * Called automatically by the repaint loop to keep the HUD updated in real time.
     *
     * @param g the graphics context
     */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        turnoLabel.setText("Player: " + playerNameSupplier.get());
        shotsLabel.setText(" | Shots: " + playerShotsSupplier.getAsInt());
    }
}
