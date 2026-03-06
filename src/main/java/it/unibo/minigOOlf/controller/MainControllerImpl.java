package it.unibo.minigOOlf.controller;

import java.awt.event.*;
import javax.swing.Timer;

// import java.util.concurrent.LinkedBlockingQueue;
// import javax.swing.text.View;

public class MainControllerImpl implements MainController, ActionListener{
    private final int FPS = 60;
    private Timer timer;

    public MainControllerImpl() {
        this.timer = new Timer(1000/FPS, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // main loop
        System.out.println("timer is running: " + System.nanoTime());
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
