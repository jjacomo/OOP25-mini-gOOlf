package it.unibo.minigoolf.view;

import it.unibo.minigoolf.controller.MainController;
import it.unibo.minigoolf.controller.gamemapcontroller.GameMapController;
import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.model.logic.ShotState;
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
 * No longer handles shot polling or ball-stopped notification —
 * those are managed by {@link it.unibo.minigoolf.controller.shot.ShotControllerImpl}.
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

    /**
     * Creates and displays the main application window.
     *
     * @param controller           the main controller
     * @param navigationController the navigation controller
     * @param gameState            the shared game state
     * @param gameMapController    the controller managing the shared game map
     * @param shotState            the shared shot state (created by MainControllerImpl)
     */
    public MainWindow(final MainController controller, final NavigationController navigationController,
            final GameState gameState, final GameMapController gameMapController,
            final ShotState shotState) {
        this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        this.setTitle("MinigOOlf");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.gamePanel = new GamePanel(controller, navigationController, gameState,
                gameMapController, shotState);

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
     * Allows switching the active panel via CardLayout.
     *
     * @param name the panel key ("MENU", "GAME", "NEW_GAME", …)
     */
    public void showScene(final String name) {
        cardLayout.show(mainContainer, name);
    }

    /**
     * Returns the {@link ShotViewPanel} so the controller can wire it to
     * {@link it.unibo.minigoolf.controller.shot.ShotControllerImpl}.
     *
     * @return the shot view panel
     */
    public ShotViewPanel getShotViewPanel() {
        return gamePanel.getShotViewPanel();
    }
}
