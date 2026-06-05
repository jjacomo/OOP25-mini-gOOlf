package it.unibo.minigoolf.controller;

import it.unibo.minigoolf.controller.game.MatchManager;
import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.model.map.factories.FirstMap;
import it.unibo.minigoolf.model.map.factories.SecondMap;
import it.unibo.minigoolf.model.map.factories.ThirdMap;
import it.unibo.minigoolf.model.map.factories.MapSequence;
import it.unibo.minigoolf.model.map.factories.TestMap;
import it.unibo.minigoolf.view.MainWindow;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Main controller.
 * Manages the application lifecycle: timer and navigation.
 * All match logic is delegated to {@link MatchManager}.
 *
 * @author dani, giacomo and fede
 */
public final class MainControllerImpl implements MainController, ActionListener {

    private static final int FPS = 60;
    private static final double NANOS_PER_SECOND = 1_000_000_000.0;
    private static final double MAX_DELTA_TIME = 0.1;

    private long lastTime = System.nanoTime();

    private final Timer timer;
    private final MainWindow mainWindow;
    private final NavigationController navigationController;
    private MatchManager matchManager;

    /**
     * Creates and wires all components.
     */
    public MainControllerImpl() {
        this.mainWindow = new MainWindow(this);
        this.navigationController = new NavigationController(this, mainWindow);
        mainWindow.initPanels(navigationController);
        this.timer = new Timer(1000 / FPS, this);
    }

    /**
     * Initializes the match logic once the real player names are chosen.
     *
     * @param playerNames the names inserted in the NewGamePanel
     */
    @Override
    public void startNewMatch(final List<String> playerNames) {
        final MapSequence mapSequence = new MapSequence(List.of(new ThirdMap(),new TestMap(), new FirstMap(), new SecondMap()));
        this.matchManager = new MatchManager(
            mapSequence,
            playerNames,
            this::stop,
            navigationController::showGameScene,
            navigationController::goToMainMenu,
            mainWindow::rebuildGamePanel,
            navigationController,
            scores -> mainWindow.showMidLeaderBoard(scores, () -> this.matchManager.advanceToNextHole())
        );
    }

    @Override
    public void skipMap() {
        if (this.matchManager != null) {
            // Usa lo stesso identico metodo che usiamo a fine buca!
            this.matchManager.advanceToNextHole(); 
        }
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(final ActionEvent e) {
        final long now = System.nanoTime();
        double deltaTime = (now - lastTime) / NANOS_PER_SECOND;
        if (deltaTime > MAX_DELTA_TIME) {
            deltaTime = 1.0 / FPS;
        }
        lastTime = now;
        if (matchManager != null) {
            matchManager.tickActiveMatch(deltaTime);
        }
        mainWindow.repaint();
    }

    /** {@inheritDoc} */
    @Override
    public void start() {
        timer.start();
    }

    /** {@inheritDoc} */
    @Override
    public void stop() {
        timer.stop();
    }
}
