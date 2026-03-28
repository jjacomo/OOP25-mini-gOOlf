package it.unibo.minigoolf.view.panels;

import javax.swing.*;
import it.unibo.minigoolf.controller.MainController;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * One of the possible scenes, this one is where the game is played. 
 * 
 * @author dani
 * 
 */

public class GamePanel extends JPanel {
    private static final int START_WIDTH = 1920; // This is just the starting resolution, should be a fixed size
    private static final int START_HEIGHT = 1080;
    private final MainController controller;

    public GamePanel(MainController controller) {
        this.controller = controller;
        this.setPreferredSize(new Dimension(START_WIDTH, START_HEIGHT)); 
        this.setLayout(new BorderLayout());

        /**
         * Here is the "UI" part (Player name, shots, ecc..)
         * TODO: To work later with @fedesparvo1-a11y to determine how to personalize this, just an example for now
         *  */ 


        JPanel uiPanel = new JPanel();
        uiPanel.setBackground(Color.DARK_GRAY);
        JLabel turnoLabel = new JLabel("Player Pippo | Shots: 1");
        turnoLabel.setForeground(Color.WHITE);
        uiPanel.add(turnoLabel);
        this.add(uiPanel, BorderLayout.NORTH);

        //* Here's the real area where the game runs: ball, map and obstacles need to be drawn here @jjacomo @MattiaDambrosio5 */

        JPanel fieldArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); 
                Graphics2D g2d = (Graphics2D) g;
                // A test ball to see how it appears with the Graphics2D library
                g2d.setColor(Color.WHITE);
                g2d.fillOval(100, 100, 20, 20);
                // A simple obstacle
                g2d.setColor(Color.GRAY);
                g2d.fillRect(200, 150, 100, 200);
            }
        };

        fieldArea.setBackground(new Color(0,153,0)); 

        // A "wrapperpanel" that contains the field area, so it can be forced to the 16:9 ratio

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(Color.WHITE); 
        centerWrapper.add(fieldArea);
        this.add(centerWrapper, BorderLayout.CENTER);

        // FIXME: First attempt at forcing the 16:9 ratio, maybe needs to be redone
        centerWrapper.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int w = centerWrapper.getWidth();
                int h = centerWrapper.getHeight();
                
                int expectedHeight = (int) (w * 9.0 / 16.0);
                int expectedWidth = (int) (h * 16.0 / 9.0);

                if (expectedHeight > h) {
                    fieldArea.setPreferredSize(new Dimension(expectedWidth, h));
                } else {
                    fieldArea.setPreferredSize(new Dimension(w, expectedHeight));
                }
                centerWrapper.revalidate();

            }    
        });
    }

}