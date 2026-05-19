package it.unibo.minigoolf.controller;

import it.unibo.minigoolf.controller.game.GameController;
import it.unibo.minigoolf.controller.game.GameFactory;
import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.view.MainWindow;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Main controller.
 * Manages the application lifecycle: timer, navigation and the active match.
 * All match logic is fully delegated to {@link GameController}.
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
    private final NavigationController navigationController;
    private GameController activeMatch;

    /**
     * Creates and wires all components.
     */
    public MainControllerImpl() {
        this.navigationController = new NavigationController(this);
        // TODO: pass real player names from NewGamePanel
        this.activeMatch = GameFactory.buildMatch(List.of("Player 1"));
        this.mainWindow = new MainWindow(this, navigationController, activeMatch);
        this.navigationController.setMainWindow(mainWindow);
        // Wire the shot view now that the window (and its panels) exist.
        activeMatch.setShotView(mainWindow.getShotView());
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
        activeMatch.updateTick(deltaTime);
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
