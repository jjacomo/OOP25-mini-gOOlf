package it.unibo.minigoolf;

import it.unibo.minigoolf.controller.MainControllerImpl;

/**
 * Game engine.
 */
public final class App {

    /**
     * Private constructor made to hide the implicit public one.
     */
    private App() {
    }

    /**
     * Main method to start the application.
     * 
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        new MainControllerImpl();
    }
}
