package it.unibo.minigOOlf.controller;

// import java.util.concurrent.LinkedBlockingQueue;
// import javax.swing.text.View;

public class MainControllerImpl implements MainController{
    private final Loop loop = new Loop();
    
    public MainControllerImpl(){
    }

    public void loop(){
        this.loop.start();
    }

    private class Loop extends Thread {
        private static final int SLEEPING_PERIOD_IN_MILLISECONDS = 10;

        public void run(){
            while (true){
                long time = System.nanoTime();
                // view.show(model.getProjectilesPosition(), model.getEnemiesPosition(), model.getShipPosition());
                // if (model.hasWon()){
                //     view.won();
                // }
                // if (model.hasLost()){
                //     view.lost();
                // }
                try {
                    Thread.sleep(SLEEPING_PERIOD_IN_MILLISECONDS);
                    // switch (queue.poll()) {
                    //     case LEFT -> model.move(Model.Movement.LEFT);
                    //     case RIGHT -> model.move(Model.Movement.RIGHT);
                    //     case FIRE -> model.fire();
                    //     default -> {}
                    // }
                    System.out.println(System.nanoTime()-time);
                } catch (Exception e){}
                // model.update(System.nanoTime() - time);
            }
        }
    }

}
