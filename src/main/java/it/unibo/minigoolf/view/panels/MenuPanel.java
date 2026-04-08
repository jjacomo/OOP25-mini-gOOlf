package it.unibo.minigoolf.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JPanel;


import it.unibo.minigoolf.controller.MainController;

/**
 * One of the possibile scenes, this is the main menu where the user can start a new game,
 * check the leaderboard,...
 * * @author dani
 * * TODO: Add the variuos buttons, ecc...
 */
public class MenuPanel extends JPanel {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    public MenuPanel(final MainController controller) {
        this.setPreferredSize(new Dimension(960, 540)); // TODO: Unify the size of the panels, and create a file where you can change costants easely
        this.setBackground(Color.GREEN);
        this.setLayout(new GridBagLayout());

        JButton playButton = new JButton("START GAME");
        playButton.setPreferredSize(new Dimension(200, 50));
        
        
        playButton.addActionListener(e -> controller.startGame());

        this.add(playButton);
    }

}
