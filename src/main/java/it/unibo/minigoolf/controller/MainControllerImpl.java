package it.unibo.minigoolf.controller;

import it.unibo.minigoolf.model.logic.GameState;
import it.unibo.minigoolf.util.Vec2D;
import it.unibo.minigoolf.view.MainWindow;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

/**
 * Main controller.
 *
 * @author dani and fede
 */
public final class MainControllerImpl implements MainController, ActionListener {

    private static final int FPS = 60;

    private final Timer timer;

    private final GameState gameState;
    private final MainWindow mainWindow;

    /**
     * Creates the controller, the game state and the main window.
     */
    public MainControllerImpl() {
        // TODO: let the player write their own nickname
        this.gameState = new GameState(List.of("Player 1"));
        this.mainWindow = new MainWindow(this, gameState);
        this.timer = new Timer(1000 / FPS, this);
    }

    /** {@inheritDoc} */
    @Override
    public void actionPerformed(final ActionEvent e) {
        // Process shot from GameState.
        final Optional<Vec2D> shot = gameState.update();
        if (shot.isPresent()) {
            handleShot(shot.get());
        }

        mainWindow.repaint();
    }

    /**
     * Placeholder for physics processing of a shot.
     * TODO: replace when physics is ready
     *
     * @param ignored the shot vector (unused until physics is implemented)
     */
    private void handleShot(final Vec2D ignored) {
        // TODO: when physics is ready this will be called only when the ball stops.
        // For now we stop it immediately.
        gameState.onBallStopped();
        mainWindow.getGamePanel().onBallStopped();
        mainWindow.repaint();
    }

    /** {@inheritDoc} */
    @Override
    public void startGame() {
        this.start();
        this.mainWindow.showScene("GAME");
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
