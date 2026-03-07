package it.unibo.minigOOlf;

import it.unibo.minigOOlf.controller.MainControllerImpl;
import it.unibo.minigOOlf.view.MainWindow; //da togliere quando si sarà messo nel controller!

public class App {
    public static void main(String[] args) {
        new MainControllerImpl().start();
        //while(true){ // per non fare terminare istantaneamente l'applicazione (con l'interfaccia grafica non serve piu')
        new MainWindow(); //messo qui per test sul gameloop, da spostare nel controller
        }
    }

