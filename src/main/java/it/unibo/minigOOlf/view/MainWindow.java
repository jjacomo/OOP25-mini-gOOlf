package it.unibo.minigOOlf.view;

import javax.swing.JFrame;

import it.unibo.minigOOlf.view.panels.GamePanel;


/**
 * The window in which the scenes (such as the MenuPanel or GamePanel) 
 * are choosen to be displayed.
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
