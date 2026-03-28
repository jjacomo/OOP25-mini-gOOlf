package it.unibo.minigoolf.view;

import java.awt.Dimension;
import javax.swing.JFrame;
import it.unibo.minigoolf.controller.MainController;
import it.unibo.minigoolf.view.panels.GamePanel;

/**
 * The window in which the scenes (such as the MenuPanel or GamePanel) 
 * are choosen to be displayed.
 * 
 * @author dani
 * 
 */

public class MainWindow extends JFrame {
    private static final int MIN_WIDTH = 800;
    private static final int MIN_HEIGHT = 600;

    public MainWindow(MainController controller) {
        this.setMinimumSize(new Dimension(MIN_WIDTH,MIN_HEIGHT));
        this.setTitle("MinigOOlf");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GamePanel gamePanel = new GamePanel(controller);
        this.add(gamePanel);
        this.pack(); 
        this.setVisible(true);
    }
}
