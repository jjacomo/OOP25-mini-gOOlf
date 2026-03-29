package it.unibo.minigOOlf.controller;

import it.unibo.minigOOlf.model.logic.GameState;
import it.unibo.minigOOlf.util.Vec2D;
import it.unibo.minigOOlf.view.MainWindow;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

// import java.util.concurrent.LinkedBlockingQueue;
// import javax.swing.text.View;

public class MainControllerImpl implements MainController, ActionListener {

    private static final int FPS = 60;

    private final Timer timer;
    private long lastTime = 0;

    private final GameState gameState;
    private final MainWindow mainWindow;

    public MainControllerImpl() {
        //TODO: qua poi faremo scrivere al player il proprio nickname
        this.gameState = new GameState(List.of("Player 1"));

        this.mainWindow = new MainWindow(this, gameState);
        this.timer = new Timer(1000 / FPS, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long currentTime = System.nanoTime();
        long elapsed = currentTime - lastTime; // nanoseconds since last tick
        lastTime = currentTime;

        //process shot from GameState 
        Optional<Vec2D> shot = gameState.update();
        if (shot.isPresent()) {
            handleShot(shot.get(), elapsed);
        }

        mainWindow.repaint();
    }

    /**
     * Placeholder for physics processing of a shot.
     * //TODO sistemalo quando la fisica sarà pronta
     *
     * @param shot the shot vector
     * @param elapsed nanoseconds elapsed since the last tick
     */
    private void handleShot(Vec2D shot, long elapsed) {
        System.out.printf("[SHOT] player=%s  vector=%s  power=%.1f%n",
            gameState.getCurrentPlayer().getName(),
            shot,
            shot.getModule());

        // TODO: quando la fisica sarà pronta verrà chiamato quando la pallina sarà ferma
        // per ora la stoppo forzatamente
        gameState.onBallStopped();
        mainWindow.getGamePanel().onBallStopped();
    }

    @Override
    public void start() {
        System.out.println("Start");
        lastTime = System.nanoTime();
        timer.start();
    }

    @Override
    public void stop() {
        System.out.println("Stop");
        timer.stop();
    }
}