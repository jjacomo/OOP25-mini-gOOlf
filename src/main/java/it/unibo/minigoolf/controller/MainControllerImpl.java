package it.unibo.minigoolf.controller;

import it.unibo.minigoolf.controller.game.MatchManager;
import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.model.map.factories.FirstMap;
import it.unibo.minigoolf.model.map.factories.MapSequence;
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
 * @author dani and fede
 */
public final class MainControllerImpl implements MainController, ActionListener {

    private static final int FPS = 60;
    private static final double NANOS_PER_SECOND = 1_000_000_000.0;
    private static final double MAX_DELTA_TIME = 0.1;

    private long lastTime = System.nanoTime();

    private final Timer timer;
    private final MainWindow mainWindow;
    private final MatchManager matchManager;

    /**
     * Creates and wires all components.
     */
    public MainControllerImpl() {
        // TODO: pass real player names from NewGamePanel
        final List<String> playerNames = List.of("Player 1");
        final MapSequence mapSequence = new MapSequence(List.of(new FirstMap()));
        final NavigationController navigationController = new NavigationController(this);

        this.mainWindow = new MainWindow(this, navigationController);
        navigationController.setMainWindow(mainWindow);

        this.matchManager = new MatchManager(
            mapSequence,
            playerNames,
            this::stop,
            navigationController::startGame,
            navigationController::goToMainMenu,
            mainWindow::rebuildGamePanel);

        navigationController.setResetMatch(matchManager::reset);
        this.timer = new Timer(1000 / FPS, this);
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
