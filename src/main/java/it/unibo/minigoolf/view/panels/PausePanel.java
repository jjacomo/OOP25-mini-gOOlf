package it.unibo.minigoolf.view.panels;

import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.view.elements.UserInterfaceFactory;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;

/**
 * The pause menu. It is NOT a panel within the cardlayout! It's an overlay if gamepanel.
 * * @author dani
 */
public class PausePanel extends JPanel {
    private static final long serialVersionUID = 1L;

    public PausePanel(final NavigationController navController) {
        this.setOpaque(false); 
        this.setLayout(new GridBagLayout());

        final JPanel menuBox = new JPanel();
        menuBox.setLayout(new BoxLayout(menuBox, BoxLayout.Y_AXIS));
        menuBox.setOpaque(false);

        final JButton resumeBtn = UserInterfaceFactory.createButton("RESUME");
        resumeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        resumeBtn.addActionListener(e -> navController.resumeGame());
        
        final JButton exitBtn = UserInterfaceFactory.createButton("EXIT TO MENU");
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.addActionListener(e -> navController.quitToMenu());

        menuBox.add(resumeBtn);
        menuBox.add(Box.createVerticalStrut(20));
        menuBox.add(exitBtn);

        this.add(menuBox, new GridBagConstraints());
        // This is needed so if the player clicks with his mouse during pause-state, the ball won't react
        this.addMouseListener(new MouseAdapter() {}); 
    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        // Tinted effect for the background
        g.setColor(new Color(0, 0, 0, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
