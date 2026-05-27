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
import java.util.function.Function;

/**
 * The main application window.
 * Hosts the panel that is currently active.
 * The {@link NavigationController} is passed once via {@link #initPanels}
 * and captured in a lambda factory — never stored as a field — avoiding EI2.
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
     * Lambda that builds a {@link GamePanel} from a {@link GameController}.
     * Captures {@link NavigationController} at {@link #initPanels} time
     * so it is never stored as a field — avoids EI2.
     */
    private transient Function<GameController, GamePanel> gamePanelFactory = gc -> null;

    /**
     * Creates and displays the main application window.
     * Call {@link #initPanels} once the navigation controller is available.
     *
     * @param controller the main controller (unused directly; present for wiring context)
     */
    public MainWindow(final MainController controller) {
        this.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
        this.setTitle("MinigOOlf");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setContentPane(mainContainer);
        this.pack();
        this.setVisible(true);
    }

    /**
     * Adds the static panels and wires the game panel factory.
     * Must be called once immediately after the navigation controller is created.
     *
     * @param navController the navigation controller
     */
    public void initPanels(final NavigationController navController) {
        mainContainer.add(new MenuPanel(navController), "MENU");
        mainContainer.add(new NewGamePanel(navController), "NEW_GAME");
        cardLayout.show(mainContainer, "MENU");
        this.setGlassPane(new PausePanel(navController));
        // Capture navController in the factory lambda — avoids storing it as a field.
        this.gamePanelFactory = gc -> {
            final ShotViewPanel svp = new ShotViewPanel(gc.getShotState());
            gc.setShotView(svp);
            return new GamePanel(navController, gc, svp);
        };
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
     * Creates and wires the {@link ShotViewPanel} internally.
     *
     * @param gameController the match controller to wire
     */
    public void rebuildGamePanel(final GameController gameController) {
        mainContainer.add(gamePanelFactory.apply(gameController), "GAME");
    }
}
