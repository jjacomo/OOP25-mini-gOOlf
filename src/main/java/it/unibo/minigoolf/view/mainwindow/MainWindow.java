package it.unibo.minigoolf.view.mainwindow;

import it.unibo.minigoolf.controller.game.GameController;
import it.unibo.minigoolf.controller.maincontroller.MainController;
import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.view.input.ShotViewPanel;
import it.unibo.minigoolf.view.panels.GamePanel;
import it.unibo.minigoolf.view.panels.LeaderBoardPanel;
import it.unibo.minigoolf.view.panels.MenuPanel;
import it.unibo.minigoolf.view.panels.MidLeaderBoardPanel;
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
 * and captured in a lambda factory, never stored as a field, avoiding EI2.
 *
 * @author dbakko and fedesparvo1-a11y
 */
public final class MainWindow extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int MIN_WIDTH = 800;
    private static final int MIN_HEIGHT = 600;

    /** Layout manager used to switch between different panels. */
    private final CardLayout cardLayout = new CardLayout();

    /** Main container that holds all the different views. */
    private final JPanel mainContainer = new JPanel(cardLayout);

    /** Panel displaying the global leaderboard. */
    private LeaderBoardPanel leaderboardPanel;

    /**
     * Lambda that builds a {@link GamePanel} from a {@link GameController}.
     * Captures {@link NavigationController} at {@link #initPanels} time
     * so it is never stored as a field, avoids EI2.
     */
    private transient Function<GameController, GamePanel> gamePanelFactory = gc -> null;

    /**
     * Creates and displays the main application window.
     * Call {@link #initPanels} once the navigation controller is available.
     *
     * @param controller the main controller (unused directly, present just for wiring context)
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
        this.leaderboardPanel = new LeaderBoardPanel(navController);
        mainContainer.add(this.leaderboardPanel, "LEADERBOARD");
        cardLayout.show(mainContainer, "MENU");
        this.setGlassPane(new PausePanel(navController));
        // Capture navController in the factory lambda, avoids storing it as a field.
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

    /**
     * Shows the mid-leaderboard panel as an overlay.
     * Safely swaps the current glass pane (pausepanel) and restores it afterwards.
     *
     * @param scores     the map of players and their shots
     * @param onNextHole the action to run when clicking "Next Hole"
     */
    public void showMidLeaderBoard(final java.util.Map<String, Integer> scores, final Runnable onNextHole) {
        // This is needed to save the pause panel
        final java.awt.Component oldGlassPane = this.getGlassPane();

        final MidLeaderBoardPanel summaryPanel = new MidLeaderBoardPanel(scores, () -> {
            this.getGlassPane().setVisible(false);
            // This is needed for the pause panel to appear in the next map.
            this.setGlassPane(oldGlassPane);
            onNextHole.run();
        });

        this.setGlassPane(summaryPanel);
        this.getGlassPane().setVisible(true);
    }

    /**
     * Updates the leaderboard with the latest scores.
     *
     * @param finalScores the map of player names to their total scores
     */
    public void updateLeaderboard(final java.util.Map<String, Integer> finalScores) {
        this.leaderboardPanel.updateScores(finalScores);
    }
}
