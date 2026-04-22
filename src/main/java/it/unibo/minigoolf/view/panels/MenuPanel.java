package it.unibo.minigoolf.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.minigoolf.view.elements.UserInterfaceFactory;

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
        this.setBackground(Color.DARK_GRAY);
        this.setLayout(new GridBagLayout());
        // This helps using GridBagLayout so everything is allignerd vertically, will be used in menus
        final java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();

        gbc.gridx = 0;    
        gbc.insets = new java.awt.Insets(0, 0, 0, 0); // Margin settings
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        // To import the image for the title
        final ImageIcon logoIcon = new ImageIcon(getClass().getResource("/title.png"));

        // To scale the image of the title logo, tho maybe it's not necessary
        final Image scaledImage = logoIcon.getImage().getScaledInstance(400, 150, Image.SCALE_SMOOTH);
        final JLabel titleLabel = new JLabel(new ImageIcon(scaledImage));

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new java.awt.Insets(0, 0, 50, 0);
        gbc.anchor = java.awt.GridBagConstraints.CENTER;
        this.add(titleLabel, gbc);

        // TODO: Implement something to unify the design of the buttons across all the UI, so no magic numbers are needed!

        // New game button
        final JButton newGameButton = UserInterfaceFactory.createButton("NEW GAME");
        //newGameButton.setPreferredSize(new Dimension(200, 50));
        newGameButton.addActionListener(e -> controller.goToNewGameMenu());
        gbc.gridy = 1;
        this.add(newGameButton, gbc);
        
        // Start game button: for testing purpose, it starts the game immediately, to be removed!
        final JButton playButton = UserInterfaceFactory.createButton("START GAME");
        //playButton.setPreferredSize(new Dimension(200, 50));
        playButton.addActionListener(e -> controller.startGame());
        gbc.gridy = 2;
        this.add(playButton, gbc);
    }
}

