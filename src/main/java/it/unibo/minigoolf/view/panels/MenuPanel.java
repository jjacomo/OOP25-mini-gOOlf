package it.unibo.minigoolf.view.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.minigoolf.view.elements.UserInterfaceFactory;

import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;

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
    private static final int MARGINS = 10;

    public MenuPanel(final NavigationController navigationController) {

        this.setPreferredSize(new Dimension(START_WIDTH, START_HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        this.setLayout(new GridBagLayout());
        // This helps using GridBagLayout so everything is alligned vertically, will be used in menus
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(MARGINS, MARGINS, MARGINS, MARGINS);
        gbc.fill = GridBagConstraints.NONE;
        // To import the image for the title
        final ImageIcon logoIcon = new ImageIcon(getClass().getResource("/title.png"));

        // To scale the image of the title logo, tho maybe it's not necessary
        final Image scaledImage = logoIcon.getImage().getScaledInstance(400, 150, Image.SCALE_SMOOTH);
        final JLabel titleLabel = new JLabel(new ImageIcon(scaledImage));

        // Title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(MARGINS, MARGINS, MARGINS, MARGINS);
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(titleLabel, gbc);

        // Start game button, test for simulitang loading game or new game
        final JButton playButton = UserInterfaceFactory.createButton("PLAY");
        playButton.addActionListener(e -> {
            final int choice = UserInterfaceFactory.showConfirmDialog(
                    this, "Do you want to load the last game you played?", "Load Game");

            if (choice == javax.swing.JOptionPane.YES_OPTION) {
                // TODO: Implementare il caricamento della partita!
            }
            else if (choice == javax.swing.JOptionPane.NO_OPTION) {
                navigationController.goToNewGameMenu();
            }
        });
        gbc.gridy++;
        this.add(playButton, gbc);

        // Test game button: for testing purpose, it starts the game immediately, to be removed!
        // final JButton testButton = UserInterfaceFactory.createButton("TEST GAME");
        // testButton.addActionListener(e -> navigationController.showGameScene());
        //gbc.gridy++;
        // this.add(testButton, gbc); 

        // TEST FOR NOW
        final JButton leaderboardButton = UserInterfaceFactory.createButton("LEADERBOARD");
        gbc.gridy++;
        this.add(leaderboardButton, gbc);

        // TEST FOR NOW
        final JButton creditsButton = UserInterfaceFactory.createButton("CREDITS");
        gbc.gridy++;
        this.add(creditsButton, gbc);

        // This is just so the padding between the buttons is reduced
        gbc.gridy++;
        gbc.weighty = 1.0;
        final JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        this.add(spacer, gbc);

    }
}

