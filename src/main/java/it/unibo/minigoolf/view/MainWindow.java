package it.unibo.minigoolf.view;

import it.unibo.minigoolf.controller.MainController;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.view.panels.GamePanel;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.io.Serial;

/**
 * The main application window.
 * Hosts the panel that is currently active.
 *
 * @author dani and fede
 */
public final class MainWindow extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int MIN_WIDTH = 800;
    private static final int MIN_HEIGHT = 600;

    private final GamePanel gamePanel;

    /**
     * Creates and displays the main application window.
     *
     * @param controller the main controller
     * @param gameState  the shared state of the game instance
     */
    public MainWindow(final MainController controller, final GameState gameState) {
        this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        this.setTitle("MinigOOlf");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.gamePanel = new GamePanel(controller, gameState);
        this.add(gamePanel);

        this.pack();
        this.setVisible(true);
    }

    /**
     * Returns the active GamePanel.
     *
     * @return the game panel
     */
    @SuppressWarnings("EI_EXPOSE_REP")//TODO: find a way to remove this
    public GamePanel getGamePanel() {
        return gamePanel;
    }
}
