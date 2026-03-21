package it.unibo.minigOOlf.view;

import javax.swing.JFrame;

import it.unibo.minigOOlf.controller.MainController;
import it.unibo.minigOOlf.view.panels.GamePanel;


/**
 * The window in which the scenes (such as the MenuPanel or GamePanel) 
 * are choosen to be displayed.
 */

public class MainWindow extends JFrame {
    
    public MainWindow(MainController controller) {
        this.setTitle("MinigOOlf");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel gamePanel = new GamePanel(controller);
        this.add(gamePanel);
        //this.setContentPane(new GamePanel());
        this.pack(); 
        this.setVisible(true);
    }
}
