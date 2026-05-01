package it.unibo.minigoolf.view;

import it.unibo.minigoolf.controller.MainController;
import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.util.Vector2D;
import it.unibo.minigoolf.view.panels.GamePanel;
import it.unibo.minigoolf.view.panels.MenuPanel;
import it.unibo.minigoolf.view.panels.NewGamePanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.io.Serial;
import java.util.Optional;

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

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainContainer = new JPanel(cardLayout);

    private final GamePanel gamePanel;
    private final JPanel menuPanel;

    /**
     * Creates and displays the main application window.
     *
     * @param controller           the main controller
     * @param navigationController the navigation controller
     * @param gameState            the shared state of the game instance
     * @param gameMapController    the controller managing the single shared game map
     */
    public MainWindow(final MainController controller, final NavigationController navigationController,
            final GameState gameState, final GameMapController gameMapController) {
        this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        this.setTitle("MinigOOlf");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.gamePanel = new GamePanel(controller, gameState, gameMapController);
        this.menuPanel = new MenuPanel(navigationController);
        final JPanel newGamePanel = new NewGamePanel(navigationController);

        mainContainer.add(menuPanel, "MENU");
        mainContainer.add(newGamePanel, "NEW_GAME");
        mainContainer.add(gamePanel, "GAME");
        this.setContentPane(mainContainer);
        cardLayout.show(mainContainer, "MENU");

        this.pack();
        this.setVisible(true);
    }

    /**
     * Allows to change the panel through the cardLayout.
     *
     * @param name the name assigned to the according panel ("MENU", "GAME", ....)
     */
    public void showScene(final String name) {
        cardLayout.show(mainContainer, name);
    }

    /**
     * Polls the game panel for a pending shot each tick.
     * Called by the controller instead of exposing the panel directly.
     *
     * @return an Optional containing the shot vector, or empty if none is pending
     */
    public Optional<Vector2D> consumePendingShot() {
        return gamePanel.consumePendingShot();
    }

    /**
     * Notifies the game panel that the ball has stopped moving.
     * Re-enables shot input for the current player.
     */
    public void onBallStopped() {
        gamePanel.onBallStopped();
    }
}
