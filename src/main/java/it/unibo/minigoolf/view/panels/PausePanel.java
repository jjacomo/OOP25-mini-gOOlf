package it.unibo.minigoolf.view.panels;

import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.view.elements.UserInterfaceFactory;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.Image;
import java.io.Serial;

/**
 * Overlay panel for the pause menu. 
 * Rendered as a glass pane over the active game session rather than a CardLayout scene.
 *
 * @author dbakko
 */
public final class PausePanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final int TINT = 150;
    private static final int TUTORIAL_WIDTH = 800;
    private static final int TUTORIAL_HEIGHT = 450;

    // Tutorial image
    private static final String TUTORIAL_IMG_PATH = "/tutorial/tutorial_img.jpeg";

    /**
     * Constructs the pause menu panel, displaying the resume, tutorial, skip, and quit options.
     * 
     * @param navController the navigation controller
     */
    public PausePanel(final NavigationController navController) {
        this.setOpaque(false);
        this.setLayout(new GridBagLayout());

        final JPanel menuBox = new JPanel();
        menuBox.setLayout(new BoxLayout(menuBox, BoxLayout.Y_AXIS));
        menuBox.setOpaque(false);

        final JButton resumeButton = UserInterfaceFactory.createButton("RESUME");
        resumeButton.setAlignmentX(CENTER_ALIGNMENT);
        resumeButton.addActionListener(e -> navController.resumeGame());
        menuBox.add(resumeButton);

        final JButton tutorialButton = UserInterfaceFactory.createButton("TUTORIAL");
        tutorialButton.setAlignmentX(CENTER_ALIGNMENT);
        tutorialButton.addActionListener(e -> showTutorialPopup());
        menuBox.add(tutorialButton);

        final JButton skipButton = UserInterfaceFactory.createButton("SKIP MAP");
        skipButton.setAlignmentX(CENTER_ALIGNMENT);
        skipButton.addActionListener(e -> navController.skipCurrentMap());
        menuBox.add(skipButton);

        final JButton quitButton = UserInterfaceFactory.createButton("QUIT");
        quitButton.setAlignmentX(CENTER_ALIGNMENT);
        quitButton.addActionListener(e -> {
            final int choice = UserInterfaceFactory.showConfirmDialog(
                this, "Do you want to save before quitting?", "Quit Game");

            if (choice == JOptionPane.YES_OPTION) {
                navController.saveGame();
                navController.quitToMenu();
            } else if (choice == JOptionPane.NO_OPTION) {
                navController.quitToMenu();
            }
        });
        menuBox.add(quitButton);

        this.add(menuBox, new GridBagConstraints());
        // Block mouse clicks from reaching the game while paused.
        this.addMouseListener(new MouseAdapter() { });
    }

    /**
     * Displays a popup dialog containing the tutorial image, scaled to fit the screen.
     */
    private void showTutorialPopup() {
        final java.net.URL imgUrl = getClass().getResource(TUTORIAL_IMG_PATH);
        if (imgUrl != null) {

            final ImageIcon originalIcon = new ImageIcon(imgUrl);

            final Image scaledImg = originalIcon.getImage().getScaledInstance(
                TUTORIAL_WIDTH, TUTORIAL_HEIGHT, Image.SCALE_SMOOTH);

            final ImageIcon scaledIcon = new ImageIcon(scaledImg);

            JOptionPane.showMessageDialog(
                this, 
                new JLabel(scaledIcon), 
                "How to Play", 
                JOptionPane.PLAIN_MESSAGE
            );
        } else {

            JOptionPane.showMessageDialog(
                this, 
                "Tutorial image not found!\nPlease add it to: " + TUTORIAL_IMG_PATH, 
                "Missing Resource", 
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    /** {@inheritDoc} */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0, TINT));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
