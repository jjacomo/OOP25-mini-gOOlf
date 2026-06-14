package it.unibo.minigoolf.view.panels;

import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.view.elements.UserInterfaceFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import java.io.Serial;


/**
 * The main menu panel.
 *
 * @author @dbakko
 */
public final class MenuPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int START_WIDTH = 960;
    private static final int START_HEIGHT = 540;
    private static final int MARGINS = 10;
    private transient Image backgroundImage;

    /**
     * @param navigationController the navigation controller
     */
    public MenuPanel(final NavigationController navigationController) {

        // Background image loading
        try {
            final ImageIcon bgIcon = new ImageIcon(getClass().getResource("/background/menu_bg.png"));
            this.backgroundImage = bgIcon.getImage();
        } catch (Exception e) {
            System.err.println("Background image not found!");
            this.setBackground(Color.DARK_GRAY); 
        }

        this.setPreferredSize(new Dimension(START_WIDTH, START_HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        this.setLayout(new GridBagLayout());

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(MARGINS, MARGINS, MARGINS, MARGINS);
        gbc.fill = GridBagConstraints.NONE;

        // Custom title "minigOOlf"
        final ImageIcon logoIcon = new ImageIcon(getClass().getResource("/title.png"));
        final Image scaledImage = logoIcon.getImage().getScaledInstance(400, 150, Image.SCALE_SMOOTH);
        final JLabel titleLabel = new JLabel(new ImageIcon(scaledImage));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(titleLabel, gbc);

        // Play button, to start a new match
        final JButton playButton = UserInterfaceFactory.createButton("PLAY");
        playButton.addActionListener(e -> {
            if (navigationController.hasSave()) {
                final int choice = UserInterfaceFactory.showConfirmDialog(
                    this, "Do you want to load the last game you played?", "Load Game");
                if (choice == JOptionPane.YES_OPTION) {
                    navigationController.loadGame();
                } else if (choice == JOptionPane.NO_OPTION) {
                    navigationController.goToNewGameMenu();
                }
            } else {
                navigationController.goToNewGameMenu();
            }
        });
        gbc.gridy++;
        this.add(playButton, gbc);

        // LeaderBoardbutton, changes panel to LeaderBoardPanel
        final JButton leaderboardButton = UserInterfaceFactory.createButton("LEADERBOARD");
        gbc.gridy++;
        this.add(leaderboardButton, gbc);
        leaderboardButton.addActionListener(e -> navigationController.goToLeaderBoard());
        gbc.gridy++;
        gbc.weighty = 1.0;

        // This is just so the button are evenly spaced
        final JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        this.add(spacer, gbc);

    }

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        if (this.backgroundImage != null) {
            g.drawImage(this.backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }
}
