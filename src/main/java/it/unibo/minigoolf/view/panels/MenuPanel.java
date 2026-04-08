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
public final class MenuPanel extends JPanel {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    private static final int START_WIDTH = 960;
    private static final int START_HEIGHT = 540;

    public MenuPanel(final MainController controller) {
        // TODO: Unify the size of the panels, and create a file where you can change costants easely
        this.setPreferredSize(new Dimension(START_WIDTH, START_HEIGHT)); 
        this.setBackground(Color.GREEN);
        this.setLayout(new GridBagLayout());

        final JButton playButton = new JButton("START GAME");
        playButton.setPreferredSize(new Dimension(200, 50));
        playButton.addActionListener(e -> controller.startGame());
        this.add(playButton);
    }
}

