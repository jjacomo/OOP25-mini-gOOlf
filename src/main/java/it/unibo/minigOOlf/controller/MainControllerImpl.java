package it.unibo.minigOOlf.controller;

import java.awt.event.*;
import java.util.List;
import java.util.Optional;

import javax.swing.Timer;

import it.unibo.minigOOlf.model.logic.GameState;
import it.unibo.minigOOlf.view.MainWindow;
import it.unibo.minigOOlf.util.Vec2D;

// import java.util.concurrent.LinkedBlockingQueue;
// import javax.swing.text.View;

public class MainControllerImpl implements MainController, ActionListener{
    private final int FPS = 60;
    private Timer timer;
    private long lastTime = 0;
    private MainWindow mainWindow;
    private final GameState gameState;


    public MainControllerImpl() {
        this.gameState = new GameState(List.of("Player 1"));
        this.mainWindow = new MainWindow(this, gameState);
        this.timer = new Timer(1000/FPS, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long currentTime = System.nanoTime();
        long elapsed = currentTime - lastTime; // nanoseconds since last tick
        lastTime = currentTime;
 
        Optional<Vec2D> shot = gameState.update();
        if (shot.isPresent()) {
            handleShot(shot.get(), elapsed);
        }
        if (!gameState.isBallMoving()) {
            mainWindow.getGamePanel().onBallStopped();
        }
 
        mainWindow.repaint();
    }


     /**
     * Placeholder for physics processing of a shot.
     * //TODO sistemalo quando la fisica sarà pronta
     *
     * @param shot    the shot vector
     * @param elapsed nanoseconds elapsed since the last tick
     */
    private void handleShot(Vec2D shot, long elapsed) {
        System.out.printf("[SHOT] player=%s  vector=%s  power=%.1f%n",
            gameState.getCurrentPlayer().getName(),
            shot,
            shot.getModule());
 
        gameState.onBallStopped();
    }




    @Override
    public void start() {
        System.out.println("Start");
        timer.start();
    }

    @Override
    public void stop() {
        System.out.println("Stop");
        timer.stop();
    }
}
