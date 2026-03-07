package it.unibo.minigOOlf.view;

import javax.swing.JFrame;


public class MainWindow extends JFrame {
    
    public MainWindow() {
        this.setTitle("MinigOOlf");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(new GamePanel());
        this.pack(); 
        this.setVisible(true);
    }
}
