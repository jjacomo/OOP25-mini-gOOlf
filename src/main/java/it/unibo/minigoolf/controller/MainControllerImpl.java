package it.unibo.minigoolf.controller;

import it.unibo.minigoolf.controller.game.MatchManager;
import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.model.map.factories.FirstMap;
import it.unibo.minigoolf.model.map.factories.MapSequence;
import it.unibo.minigoolf.model.map.factories.TestGameMapFactory;
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
    private MatchManager matchManager;  //TODO: dire a fede che ho tolto il final qui! (dani)

    /**
     * Creates and wires all components.
     */
    public MainControllerImpl() {
        this.navigationController = new NavigationController(this);
        this.mainWindow = new MainWindow(this, navigationController);
        navigationController.setMainWindow(mainWindow);
        this.timer = new Timer(1000 / FPS, this);
    }

    /**
     * TODO: Dire a fede che ho creato questa classe dedicata! (dani)
     * Initializes the match logic once the real player names are chosen.
     * @param playerNames the names inserted in the NewGamePanel
     */
    @Override
    public void startNewMatch(final List<String> playerNames) {
        final MapSequence mapSequence = new MapSequence(List.of(new TestGameMapFactory()));
        this.matchManager = new MatchManager(
            mapSequence,
            playerNames,
            this::stop,
            navigationController::showGameScene,
            navigationController::goToMainMenu,
            mainWindow::rebuildGamePanel
        );
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
        matchManager.tickActiveMatch(deltaTime);
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
