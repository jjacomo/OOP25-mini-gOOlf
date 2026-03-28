package it.unibo.minigoolf.controller;

import java.awt.event.*;
import javax.swing.Timer;
import it.unibo.minigoolf.view.MainWindow;

// import java.util.concurrent.LinkedBlockingQueue;
// import javax.swing.text.View;

public class MainControllerImpl implements MainController, ActionListener {
    private final int FPS = 60;
    private Timer timer;
    private long lastTime;
    private MainWindow mainWindow;

    public MainControllerImpl() {
        this.mainWindow = new MainWindow(this);
        this.timer = new Timer(1000 / FPS, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // main loop
        long currentTime = System.nanoTime();
        long elapsed = currentTime - lastTime;
        System.out.println("timer is running, elapsed: " + elapsed);
        lastTime = currentTime;

        if (this.mainWindow != null) {
            this.mainWindow.repaint();
        }
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
