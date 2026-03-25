package it.unibo.minigOOlf.view;

import it.unibo.minigOOlf.controller.MainController;
import it.unibo.minigOOlf.model.logic.GameState;
import it.unibo.minigOOlf.view.panels.GamePanel;

import javax.swing.JFrame;
import java.awt.*;

/**
 * The main application window.
 * Hosts the panel that is currently active.
 *
 * @author dani
 */
public class MainWindow extends JFrame {

    private static final int MIN_WIDTH  = 800;
    private static final int MIN_HEIGHT = 600;

    private final GamePanel gamePanel;

    /**
     * @param controller the main controller
     * @param gameState  the shared game-state instance
     */
    public MainWindow(MainController controller, GameState gameState) {
        this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        this.setTitle("MinigOOlf");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.gamePanel = new GamePanel(controller, gameState);
        this.add(gamePanel);

        this.pack();
        this.setVisible(true);
    }

    /**
     *  @return the active GamePanel
     */
    public GamePanel getGamePanel() {
        return gamePanel;
    }
}