package it.unibo.minigOOlf;

import it.unibo.minigOOlf.controller.MainControllerImpl;

public class App {
    public static void main(String[] args) {
        new MainControllerImpl().start();
        while(true){ // per non fare terminare istantaneamente l'applicazione (con l'interfaccia grafica non serve piu')

        }
    }
}
