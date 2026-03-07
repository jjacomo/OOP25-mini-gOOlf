package it.unibo.minigOOlf.view;

import javax.swing.JFrame;


/**
 * La finestra principale dove cambieranno le varie scene.
 */

public class MainWindow extends JFrame {
    
    public MainWindow() {
        this.setTitle("MinigOOlf");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(new GamePanel());
        this.pack(); 
        this.setVisible(true);
    }
}
