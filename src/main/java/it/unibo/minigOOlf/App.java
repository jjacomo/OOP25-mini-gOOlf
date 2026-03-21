package it.unibo.minigOOlf;

import it.unibo.minigOOlf.controller.MainControllerImpl;
//import it.unibo.minigOOlf.view.MainWindow; //da togliere quando si sarà messo nel controller!

public class App {
    public static void main(String[] args) {
        new MainControllerImpl().start();
        //new MainWindow(); //messo qui per test sul gameloop, da spostare nel controller
    }
}

