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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.Serial;

/**
 * The main menu panel.
 *
 * @author dani
 */
public final class MenuPanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final int START_WIDTH = 960;
    private static final int START_HEIGHT = 540;
    private static final int MARGINS = 10;

    /**
     * @param navigationController the navigation controller
     */
    public MenuPanel(final NavigationController navigationController) {
        this.setPreferredSize(new Dimension(START_WIDTH, START_HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        this.setLayout(new GridBagLayout());

        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(MARGINS, MARGINS, MARGINS, MARGINS);
        gbc.fill = GridBagConstraints.NONE;

        final ImageIcon logoIcon = new ImageIcon(getClass().getResource("/title.png"));
        final Image scaledImage = logoIcon.getImage().getScaledInstance(400, 150, Image.SCALE_SMOOTH);
        final JLabel titleLabel = new JLabel(new ImageIcon(scaledImage));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(titleLabel, gbc);

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
                // CANCEL: do nothing
            } else {
                navigationController.goToNewGameMenu();
            }
        });
        gbc.gridy++;
        this.add(playButton, gbc);

        final JButton leaderboardButton = UserInterfaceFactory.createButton("LEADERBOARD");
        gbc.gridy++;
        this.add(leaderboardButton, gbc);

        final JButton creditsButton = UserInterfaceFactory.createButton("CREDITS");
        gbc.gridy++;
        this.add(creditsButton, gbc);

        gbc.gridy++;
        gbc.weighty = 1.0;
        final JPanel spacer = new JPanel();
        spacer.setOpaque(false);
        this.add(spacer, gbc);
    }
}
