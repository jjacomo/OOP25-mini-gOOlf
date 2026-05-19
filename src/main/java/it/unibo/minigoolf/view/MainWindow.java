package it.unibo.minigoolf.view;

import it.unibo.minigoolf.controller.MainController;
import it.unibo.minigoolf.controller.game.GameController;
import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.controller.shot.ShotView;
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
    private GamePanel gamePanel;

    /**
     * Creates and displays the main application window.
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

        this.gamePanel = new GamePanel(navigationController, gameController);
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

    /**
     * Rebuilds the game panel with a new match controller.
     * Called when starting a new match mid-session.
     *
     * @param navigationController the navigation controller
     * @param gameController       the new match controller
     */
    public void rebuildGamePanel(final NavigationController navigationController,
            final GameController gameController) {
        this.gamePanel = new GamePanel(navigationController, gameController);
        mainContainer.add(gamePanel, "GAME");
    }

    /**
     * Returns the shot view interface so
     * {@link it.unibo.minigoolf.controller.game.GameController#setShotView(ShotView)}
     * can be called after construction.
     *
     * @return the shot view interface
     */
    public ShotView getShotView() {
        return gamePanel.getShotView();
    }

    /**
     * Switches the active panel.
     *
     * @param name the panel key
     */
    public void showPanel(final String name) {
        cardLayout.show(mainContainer, name);
    }
}
