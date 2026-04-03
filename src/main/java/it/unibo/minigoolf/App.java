package it.unibo.minigoolf;

import it.unibo.minigoolf.controller.MainControllerImpl;

/**
 * Entry point from where the game starts.
 */
public class App {
    public static void main(String[] args) {
        new MainControllerImpl().start();
    }
}