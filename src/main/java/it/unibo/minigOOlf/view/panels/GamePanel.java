package it.unibo.minigOOlf.view.panels;

import javax.swing.*;

import it.unibo.minigOOlf.controller.MainController;

import java.awt.*;

/**
 * One of the possibile scenes, this one is where the game is played. 
 */

public class GamePanel extends JPanel {
    private static final int WIDTH = 800; // This is just the starting resolution, should be a fixed size
    private static final int HEIGHT = 600;
    private final MainController controller;

    public GamePanel(MainController controller) {
        this.controller = controller;
        
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(new Color(34, 139, 34)); 
        
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        Graphics2D g2d = (Graphics2D) g;
        // a test ball to see how it appears with the Graphics2D library
        g2d.setColor(Color.WHITE);
        g2d.fillOval(100, 100, 20, 20);
    }


}