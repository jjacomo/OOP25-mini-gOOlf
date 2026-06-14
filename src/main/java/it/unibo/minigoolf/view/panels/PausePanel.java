package it.unibo.minigoolf.view.panels;

import it.unibo.minigoolf.controller.navigationcontroller.NavigationController;
import it.unibo.minigoolf.view.elements.UserInterfaceFactory;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.io.Serial;

/**
 * The pause menu overlay.
 * It is NOT a panel within the CardLayout — it is a glass pane over GamePanel.
 *
 * @author @dbakko
 */
public final class PausePanel extends JPanel {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final int TINT = 150;

    /**
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
        // Block mouse eclicks from reaching the game while paused.
        this.addMouseListener(new MouseAdapter() { });
    }

    /** {@inheritDoc} */
    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0, TINT));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}
