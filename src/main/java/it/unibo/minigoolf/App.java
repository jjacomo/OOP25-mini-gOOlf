package it.unibo.minigoolf;

import it.unibo.minigoolf.controller.MainControllerImpl;

/**
 * Entry point from where the game starts.
 */
public final class App {

    /**
     * Private constructor to hide the implicit public one. Known as utility class.
     */
    private App() {
    }

    /**
     * Main method to start the application.
     * * @param args command line arguments
     */
    public static void main(final String[] args) {
        new MainControllerImpl().start();
    }
}
