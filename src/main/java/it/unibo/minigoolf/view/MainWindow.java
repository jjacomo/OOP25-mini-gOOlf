package it.unibo.minigoolf.view;

import it.unibo.minigoolf.controller.MainController;
import it.unibo.minigoolf.controller.game.GameController;
import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.view.input.ShotViewPanel;
import it.unibo.minigoolf.view.panels.GamePanel;
import it.unibo.minigoolf.view.panels.MenuPanel;
import it.unibo.minigoolf.view.panels.NewGamePanel;
import it.unibo.minigoolf.view.panels.PausePanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
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

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainContainer = new JPanel(cardLayout);
    private final NavigationController navigationController;

    /**
     * Creates and displays the main application window without a game panel.
     * Call {@link #rebuildGamePanel(GameController)} before showing the game scene.
     *
     * @param controller           the main controller
     * @param navigationController the navigation controller
     */
    public MainWindow(final MainController controller,
            final NavigationController navigationController) {
        this.navigationController = navigationController;
        this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        this.setTitle("MinigOOlf");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        mainContainer.add(new MenuPanel(navigationController), "MENU");
        mainContainer.add(new NewGamePanel(navigationController), "NEW_GAME");
        this.setContentPane(mainContainer);
        cardLayout.show(mainContainer, "MENU");

        this.setGlassPane(new PausePanel(navigationController));
        this.pack();
        this.setVisible(true);
    }

    /**
     * Switches the active panel.
     *
     * @param name the panel key ("MENU", "GAME", "NEW_GAME", …)
     */
    public void showScene(final String name) {
        cardLayout.show(mainContainer, name);
    }

    /**
     * Builds or replaces the game panel with the given match controller.
     * Creates and wires the {@link ShotViewPanel} internally so neither
     * {@link GamePanel} nor the caller needs to expose it.
     *
     * @param gameController the match controller to wire
     */
    public void rebuildGamePanel(final GameController gameController) {
        final ShotViewPanel shotViewPanel = new ShotViewPanel(gameController.getShotState());
        gameController.setShotView(shotViewPanel);
        final GamePanel gamePanel = new GamePanel(navigationController, gameController, shotViewPanel);
        mainContainer.add(gamePanel, "GAME");
    }
}
