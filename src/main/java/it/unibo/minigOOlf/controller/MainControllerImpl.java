package it.unibo.minigOOlf.controller;

import java.awt.event.*;
import javax.swing.Timer;

// import java.util.concurrent.LinkedBlockingQueue;
// import javax.swing.text.View;

public class MainControllerImpl implements MainController, ActionListener{
    private final int FPS = 60;
    private Timer timer;
    private long lastTime = 0;

    private long lastTime = 0;

    public MainControllerImpl() {
        
        this.timer = new Timer(1000/FPS, this);
    }

    @Override
    public void actionPerformed(ActionEvent e) { // main loop
        long currentTime = System.nanoTime();
        long elapsed = currentTime - lastTime;
        System.out.println("timer is running, elapsed: " + elapsed);
        lastTime = currentTime;
<<<<<<< HEAD
        
=======
>>>>>>> f24a6b34e56aed5fd2d87182e3b9ab800aec4887
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
