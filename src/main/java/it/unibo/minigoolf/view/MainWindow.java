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
 * Creates the {@link ShotViewPanel} and wires it to both
 * the {@link GamePanel} and the {@link GameController},
 * so neither exposes internal references.
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

    /**
     * Creates and displays the main application window.
     * The {@link ShotViewPanel} is created here and passed to both
     * the {@link GamePanel} and {@link GameController#setShotView},
     * eliminating the need for any {@code getShotView()} method on {@link GamePanel}.
     *
     * @param controller           the main controller
     * @param navigationController the navigation controller
     * @param gameController       the active match controller
     */
    public MainWindow(final MainController controller,
            final NavigationController navigationController,
            final GameController gameController) {
        this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        this.setTitle("MinigOOlf");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ShotViewPanel is created here so it can be passed to both
        // GamePanel (for rendering) and GameController (for input wiring)
        // without either needing to expose it via a getter.
        final ShotViewPanel shotViewPanel = new ShotViewPanel(gameController.getShotState());
        gameController.setShotView(shotViewPanel);

        final GamePanel gamePanel = new GamePanel(navigationController, gameController, shotViewPanel);
        mainContainer.add(new MenuPanel(navigationController), "MENU");
        mainContainer.add(new NewGamePanel(navigationController), "NEW_GAME");
        mainContainer.add(gamePanel, "GAME");
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
}
